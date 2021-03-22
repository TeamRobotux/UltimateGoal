package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareMap.MainHardware;

@TeleOp(name="Main Teleop", group="Iterative Opmode")
public class MainTeleop extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    MainHardware robot = new MainHardware();

    double pow = 1;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        robot.drive360(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad1.right_bumper){
            robot.intake.setPower(pow);
        }
        else if (gamepad1.left_bumper) {
            robot.intake.setPower(pow * -1);
        }
        else {
            robot.intake.setPower(0);
        }

        if (gamepad1.dpad_down && pow >= .1){
            pow -= .1;
        }

        if (gamepad1.dpad_up && pow <= .9){
            pow += .1;
        }

        if(gamepad1.left_bumper){
            robot.shooter.setPower(1);
        }
        else{
            robot.shooter.setPower(0);
        }

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
