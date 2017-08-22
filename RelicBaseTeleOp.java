package org.firstinspires.ftc.robotcontroller.internal.OpsModes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.internal.OpsModes.RelicRecoveryHardware;
/**
 * Created by Henri on 8/14/2017.
 */
public abstract class RelicBaseTeleOp extends LinearOpMode {

    RelicRecoveryHardware robot = new RelicRecoveryHardware();

// Telemetry
    public void say(String text1, String text2) {
        telemetry.addData(text1, text2);
        telemetry.update();
    }

    public void dataLabel(String text1,double data){
        telemetry.addData(text1, "%" + data);
    }


// Configuration
    public void motorConfig() {
        robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        say("Motors: ", "Configured");
    }
    

//Power Drive
    public void leftPower(double power){
        robot.leftBackMotor.setPower(power);
        robot.leftFrontMotor.setPower(power);
        dataLabel("Left Power: ", power);
    }

    public void rightPower(double power){
        robot.rightBackMotor.setPower(power);
        robot.rightFrontMotor.setPower(power);
        dataLabel("Right Power: ", power);
    }
}