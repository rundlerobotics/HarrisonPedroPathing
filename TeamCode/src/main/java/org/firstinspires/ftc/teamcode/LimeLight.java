package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class LimeLight extends OpMode {
    private Limelight3A limelight3A;

    public void initLimelight() {
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(0); // switch to whatever pipeline (lets say 0 is blue 1 is red)
    }

    public void startLimelight() {
        limelight3A.start();
    }

    public LLResult updatelimeLight() {
        LLResult llResult = limelight3A.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            telemetry.addData("Target X offset", llResult.getTx());
            telemetry.addData("Target Y offset", llResult.getTx());
            telemetry.addData("Target Area", llResult.getTa());
        }
        return llResult;
    }
}
