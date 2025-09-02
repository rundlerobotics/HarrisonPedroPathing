package org.firstinspires.ftc.teamcode.pedroPathing;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.util.Timer;

public class BlueSpecimenAuto extends OpMode {
    private Follower follower;
    private Timer pathTimer;

    private int pathState;

    private final Pose startPose = new Pose(8, 48, Math.toRadians(0));

    private PathChain scorePreload = follower.pathBuilder()
            .addPath(new BezierLine())

}
