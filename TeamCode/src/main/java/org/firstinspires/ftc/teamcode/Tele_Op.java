package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * This is a simple teleop routine for testing localization. Drive the robot around like a normal
 * teleop routine and make sure the robot's estimated pose matches the robot's actual pose (slight
 * errors are not out of the ordinary, especially with sudden drive motions). The goal of this
 * exercise is to ascertain whether the localizer has been configured properly (note: the pure
 * encoder localizer heading may be significantly off if the track width has not been tuned).
 */
@TeleOp(group = "drive")
public class Tele_Op extends LinearOpMode {
    private static final double CLAW_OPEN_POS = .12;
    private static final double CLAW_CLOSE_POS = .85;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Servo claw = hardwareMap.get(Servo.class, "wbg");


        waitForStart();

        while (!isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();

            if (gamepad1.a) {
                // close the claw
                claw.setPosition(CLAW_CLOSE_POS); // position 1 is closed
            } else if (gamepad1.b) {
                // open the claw
                claw.setPosition(CLAW_OPEN_POS); //position 1 is open
            }

            // update telemetry

            telemetry.addData("Claw Position","%.3f",claw.getPosition());
            telemetry.update();
        }
    }

    private void closeClaw() {
        claw.setPosition(CLAW_CLOSE_POS);
    }
    private void openClaw() {
        claw.setPosition(CLAW_OPEN_POS);
}

