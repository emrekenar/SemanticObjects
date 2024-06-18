package simulink;

import com.mathworks.engine.MatlabEngine;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class ConvertSimulink {
    private String path;
    private String modelName;

    public ConvertSimulink(String path, String modelName) {
        this.path = path;
        this.modelName = modelName;
    }

    public boolean run() {
        try {
            //Start MATLAB engine
            MatlabEngine ml = MatlabEngine.startMatlab();
            System.out.println("Engine started.");

            // define model name and run convert.m script
            String command = String.format("model_name='%s';convert", modelName);
            ml.eval(command);

            // Disconnect from the MATLAB session
            ml.disconnect();
            System.out.println("Disconnected.");

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
