package org.firstinspires.ftc.robotcontroller.internal.OpsModes;

/**
 * Created by Henri on 8/21/2017.
 */
public abstract class BluePosition2 extends BluePosition1{
    public void BP2() {
        //Variables

        //Setup Termination and waitForStart()
        setupDone();

        //After start
        target(20, 1);

        turn(90, 1);


    }

}

