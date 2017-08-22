package org.firstinspires.ftc.robotcontroller.internal.OpsModes;

/**
 * Created by Henri on 8/21/2017.
 */
public abstract class BluePosition1 extends RelicBaseAuto {
    public void BP1() {
        //Variables

        //Setup Termination and waitForStart()
        setupDone();

        //After start
        target(10, 1);

        turn(45, 1);

    }

}
