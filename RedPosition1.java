package org.firstinspires.ftc.robotcontroller.internal.OpsModes;

/**
 * Created by Henri on 8/21/2017.
 */
public abstract class RedPosition1 extends BluePosition2{
    public void RP1() {
        //Variables

        //Setup Termination and waitForStart()
        setupDone();

        //After start
        target(30, 1);

        turn(-90, 1);

    }

}

