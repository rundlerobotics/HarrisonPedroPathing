package org.firstinspires.ftc.teamcode.pedroPathing;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.util.Timer;

public class BlueSpecimenAuto extends OpMode {
    private Follower follower;
    private Timer pathTimer;

    private int pathState;

    private final Pose startPose = new Pose(8, 48, Math.toRadians(0));
    private final Pose scorePose1 = new Pose(39, 65, Math.toRadians(0));
    private final Pose grabBrick1 = new Pose(35, 19, Math.toRadians(0));

    // im not gonna make the whole auto when the new game is in 3 days
    private PathChain scorePreload, gotoBrick1;
    public void buildPaths() {
        scorePreload = follower.pathBuilder() // straight line example
                .addPath(new BezierLine(startPose, scorePose1))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose1.getHeading())
                .build();
        gotoBrick1 = follower.pathBuilder() //bezier curve example
                .addPath(new BezierCurve(
                        scorePose1,
                        new Pose(68, 110), // control point
                        grabBrick1)
                )
                .setLinearHeadingInterpolation(scorePose1.getHeading(), grabBrick1.getHeading())
                .build();
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload, true); // second parameter is whether to holdEnd after done
                setPathState(1);
                break;
            case 1:
                // wait until first movement is finished
                if (!follower.isBusy()) {
                    // done so score the specimen
                    setPathState(2);
                }
                break;
            case 2:
                // 1 seconds after the first movement is done the specimen should be scored
                if (pathTimer.getElapsedTimeSeconds() > 1.0) {
                    follower.followPath(gotoBrick1, true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    // done
                    setPathState(-1);
                }
        }
    }

    // just copy pasted this its all basic boilerplate
    @Override
    public void loop() {
        follower.update();

        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }
    /** This method is called once at the start of the OpMode. It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        setPathState(0);
    }


}
