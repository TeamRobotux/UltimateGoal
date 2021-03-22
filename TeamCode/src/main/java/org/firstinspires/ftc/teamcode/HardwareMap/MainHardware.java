package org.firstinspires.ftc.teamcode.HardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MainHardware {
    public DcMotor driveFrontR = null;
    public DcMotor driveFrontL = null;
    public DcMotor driveRearR = null;
    public DcMotor driveRearL = null;
    public DcMotor intake = null;
    public DcMotor shooter = null;
    public Servo lilChippy = null;
    public RobotGyro gyro = null;

    /* local OpMode members. */
    HardwareMap hwMap =  null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public MainHardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;

        driveFrontR = hwMap.get(DcMotor.class,"driveFrontR");
        driveFrontL = hwMap.get(DcMotor.class,"driveFrontL");
        driveFrontL.setDirection(DcMotorSimple.Direction.REVERSE);
        driveRearR = hwMap.get(DcMotor.class,"driveRearR");
        driveRearL = hwMap.get(DcMotor.class,"driveRearL");
        driveRearL.setDirection(DcMotorSimple.Direction.REVERSE);
        intake = hwMap.get(DcMotor.class, "intake");
        shooter = hwMap.get(DcMotor.class, "shooter");
        lilChippy = hwMap.get(Servo.class, "lilChippy");
        gyro = new RobotGyro(hwMap);

    }

    public void setDrivetrainMode(DcMotor.RunMode mode) {
        this.driveFrontR.setMode(mode);
        this.driveFrontL.setMode(mode);
        this.driveRearR.setMode(mode);
        this.driveRearL.setMode(mode);
    }

    public void drive360(double x, double y, double turn) {
        driveRearR.setPower(y + x - turn);
        driveFrontR.setPower(y - x - turn);
        driveRearL.setPower(y - x + turn);
        driveFrontL.setPower(y + x + turn);
    }

    public void turn (double power) {
        setDrivetrainMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFrontR.setPower(-power);
        driveFrontL.setPower(power);
        driveRearR.setPower(-power);
        driveRearL.setPower(power);
    }

    public void turnDegreesTWOTWO (int degrees){
        double originalAngle = gyro.imu.getAngularOrientation().firstAngle;
        double deltaAngle = 0.0;
        double angleAdd = 0.0;

        if (degrees > 0){
            while (degrees > deltaAngle+3){

                if(deltaAngle < -1){
                    angleAdd = 360;
                }

                deltaAngle = gyro.imu.getAngularOrientation().firstAngle - originalAngle + angleAdd;

                turn((-.6*2*(degrees-deltaAngle)/degrees)-1.2);
            }
        }
        else if (degrees < 0){
            while (degrees < deltaAngle-3){

                if (deltaAngle > 1){
                    angleAdd = 360;
                }

                deltaAngle = gyro.imu.getAngularOrientation().firstAngle - originalAngle - angleAdd;

                turn((.6*2*(degrees-deltaAngle)/degrees)+1.2);
            }
        }

        turn(0);
        setDrivetrainMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveInches(double numInches) {
        double ticPerInch40 = 66.85;
        //double ticPerInch402 = 89.127;
        //double ticPerInch40 = 1.0;
        double inches = numInches;

        driveFrontL.setTargetPosition((int) (ticPerInch40 * inches + 0.5));
        driveFrontR.setTargetPosition((int) (ticPerInch40 * inches + 0.5));
        driveRearL.setTargetPosition((int) (ticPerInch40 * inches + 0.5));
        driveRearR.setTargetPosition((int) (ticPerInch40 * inches + 0.5));

        driveFrontL.setPower(.4);
        driveFrontR.setPower(.4);
        driveRearL.setPower(.4);
        driveRearR.setPower(.4);

        setDrivetrainMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

}