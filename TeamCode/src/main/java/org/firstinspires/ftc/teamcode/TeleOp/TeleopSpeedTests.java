package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareMap.MainHardware;

@TeleOp(name="How to test your speeds (Teleop)", group="Iterative Opmode")
public class TeleopSpeedTests extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    MainHardware robot = new MainHardware();

    double pow = .5;
    double pow2 = .5;

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

        telemetry.addData("Instructions:", "To increase or decrease the shooter speed, use A button to decrease" +
                "speed and B button to increase speed. Use the right and left bumper to control the intake. To change " +
                "the intake speed, use d-pad down to decrease the speed and dpad up to increase the speed. The normal driving " +
                "should work as normal. Both speeds are initialized to .5, and you should be able to read that in the telemetry " +
                "once you start the program :) buena suerte");
        telemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        if (gamepad1.a && pow >= .01){
            pow -= .01;
        }

        if (gamepad1.b && pow <= .99){
            pow += .01;
        }
            robot.shooter.setPower(pow);

        telemetry.addData("Current Shooter Speed: ", pow);
        telemetry.update();

        //The following code should be for a normal drivetrain.

        robot.drive360(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad1.dpad_down && pow2 >= .01){
            pow2 -= .01;
        }

        if (gamepad1.dpad_up && pow2 <= .99){
            pow2 += .01;
        }

        //End normal drivetrain, the rest of this code is for varying the intake speed.

        if (gamepad1.right_bumper){
            robot.intake.setPower(pow2);
        }
        else if (gamepad1.left_bumper) {
            robot.intake.setPower(pow2 * -1);
        }
        else {
            robot.intake.setPower(0);
        }

        telemetry.addData("Current Intake Speed: ", pow2);
        telemetry.update();

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
