package org.firstinspires.ftc.robotcontroller.internal.OpsModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcontroller.internal.OpsModes.RelicRecoveryHardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.internal.OpsModes.RelicRecoveryHardware;
import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbotMatrix;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
/**
 * Created by Henri on 8/13/2017.
 */
public abstract class RelicBaseAuto extends LinearOpMode{
    RelicRecoveryHardware robot           = new RelicRecoveryHardware();

//long speed;
    double brake = 30;
    double autocorrect = 3;
    int tpr = 1120;
    int circumference;
    
//Telemetry
    
    public void say(String text1, String text2) {
        telemetry.addData(text1, text2);
        telemetry.update();
    }

    public void dataLabel(String text1,double data){
        telemetry.addData(text1, "%" + data);
        telemetry.update();
    }


//Timing

    public void pause() {
        sleep(750);
        say("Pausing", "");
    }

//Setup

    public void setupDone() {
        say("Say", "It is working and you loaded the package.");

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
    }

//Sensors
    public void gyroCalibrate() {
        robot.gyroSensor.calibrate();
        while(robot.gyroSensor.isCalibrating()) {
            say("GYRO CALIBRATING", "DO NOT TOUCH!!!!");
            sleep(10);
        }
        say("Gyro Calibrated", "Good Luck");
    }


//THE FOLLOWING THREE ARE MUTUALLY EXCLUSIVE
    
    public void targetDrive() {
        robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        say("Motors: ", "Target");
        sleep(25);
    }

    public void encoderReset() {
        robot.rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        say("Encoders: ", "Reset");
        sleep(25);
    }

    public void powerDrive(){
        robot.leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        say("Motors: ", "Power");
        sleep(25);
    }


//powerDrive
    
    public void leftPower(double power){
        powerDrive();
        robot.leftBackMotor.setPower(power);
        robot.leftFrontMotor.setPower(power);
        dataLabel("Left Power: ", power);
    }

    public void rightPower(double power){
        powerDrive();
        robot.rightBackMotor.setPower(power);
        robot.rightFrontMotor.setPower(power);
        dataLabel("Right Power: ", power);
    }



//targetDrive
    
    public void leftTarget(int distance, double power) {
        targetDrive();
        int ticks = tpr*(distance/circumference);
        robot.leftFrontMotor.setTargetPosition(ticks);
        robot.leftFrontMotor.setPower(power);
        robot.leftBackMotor.setTargetPosition(ticks);
        robot.leftBackMotor.setPower(power);
        dataLabel("Left Target: ", distance);
        dataLabel("Left Power: ", power);

    }
    public void rightTarget(int distance, double power) {
        targetDrive();
        int ticks = tpr*(distance/circumference);
        robot.rightFrontMotor.setTargetPosition(ticks);
        robot.rightFrontMotor.setPower(power);
        robot.rightBackMotor.setTargetPosition(ticks);
        robot.rightBackMotor.setPower(power);
        dataLabel("Right Target: ", distance);
        dataLabel("Right Power: ", power);
    }



//Driving Functions
    
    public void turn(double degrees, double speed) {
        int loop = 0;
        double start = robot.gyroSensor.getHeading();
        double end = start+degrees;
        double endcalc = end;
        double lastPos;
        double power;
        double pos = start;
        if(end<0){end+=360;}
        if(end>359.9){end-=360;}
        say("Turn Status: ", "Calculating");
        sleep(100);
        while(loop<1) {
            lastPos = pos;
            pos = robot.gyroSensor.getHeading();
            double dif = pos-lastPos;
            if(Math.abs(dif) > 180) {
                endcalc = end;
            }
            if(pos<end+0.5&&pos>end-0.5){
                loop+=1;
            }

            power = (endcalc-pos)/brake;

            if(power>1) {
                power=1;
            }

            if(power<-1){
                power=-1;
            }

            leftPower(power*speed);
            rightPower(-power*speed);
            say("Turn Status: ", "Turning");
            dataLabel("Power: ", power*speed);
            sleep(10);
        }
        leftPower(0);
        rightPower(0);
        encoderReset();
    }


    public void target(int distance, double power) {
        encoderReset();
        double startD = robot.gyroSensor.getHeading();
        leftTarget(distance,power);
        rightTarget(distance,power);
        while(robot.leftBackMotor.isBusy()  ||
              robot.leftFrontMotor.isBusy() ||
              robot.leftFrontMotor.isBusy() ||
              robot.rightFrontMotor.isBusy() ) {
            double nowD = robot.gyroSensor.getHeading();
            double dif = nowD-startD;
            if(Math.abs(dif) > autocorrect) {
                turn(dif, power);;
            }
            sleep(10);
        }
    }
}
