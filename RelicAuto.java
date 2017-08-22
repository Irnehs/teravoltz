package org.firstinspires.ftc.robotcontroller.internal.OpsModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file illustrates the concept of driving a path based on time.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backwards for 1 Second
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Autonomous", group="Pushbot")

public class RelicAuto extends RedPosition2{

    /* Declare OpMode members. */
    RelicRecoveryHardware robot = new RelicRecoveryHardware();

    @Override
    public void runOpMode() throws InterruptedException {
    // Variables
        int stage = 0;
    //Setup
        robot.init(hardwareMap);
        gyroCalibrate();

        while(stage == 0){
            if(gamepad1.dpad_up) {
                say("Color: ", "Blue");
                while (stage == 0) {
                    if(gamepad1.dpad_left) {
                        BP1();
                        stage++;
                    }
                    else if(gamepad1.dpad_right) {
                        BP2();
                        stage++;
                    }
                }
            }

            else if (gamepad1.dpad_down) {
                say("Color: ", "Red");
                while(stage == 0) {

                }
                if(gamepad1.dpad_left) {
                    RP1();
                    stage++;
                }
                else if(gamepad1.dpad_right) {
                    RP2();
                    stage++;
                }
            }
        }
    }


    private ElapsedTime runtime = new ElapsedTime();
};



