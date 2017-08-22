package org.firstinspires.ftc.robotcontroller.internal.OpsModes;

/**
 * Created by Henri on 8/21/2017.
 */
public abstract class RedPosition2 extends RedPosition1{
    public void RP2() {
        //Variables

        //Setup Termination and waitForStart()
        setupDone();

        //After start
        target(40, 1);

        turn(180, 1);

    }

}

