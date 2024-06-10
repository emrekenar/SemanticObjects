package simulink;

import com.mathworks.engine.MatlabEngine;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class ConvertSimulink {
    private String path;
    private String baseDir;
    private String modelName;

    public ConvertSimulink(String path) {
        this.path = path;

        String[] pathElements = path.split("[/]");
        String baseDir = "";
        for (int i = 0; i < pathElements.length - 1; ++i) {
            baseDir += pathElements[i];
            baseDir += "\\";
        }
        this.baseDir = baseDir;

        this.modelName = pathElements[pathElements.length - 1].split("[.]")[0];
    }

    public boolean run() {
        try {
            //Start MATLAB engine
            MatlabEngine ml = MatlabEngine.startMatlab();
            System.out.println("Engine started.");

            // Change directory
            /*if (baseDir.length() > 0) {
                ml.eval("cd %s".format(baseDir));
                System.out.println("Directory changed to %s.".format(baseDir));
            }*/

            // load the simulink model
            ml.eval("load_system('%s')".format(modelName));
            System.out.println("System %s loaded.".format(modelName));

            // set fixed-step solver
            ml.eval("set_param('%s', 'SolverType', 'Fixed-step')".format(modelName));
            System.out.println("Solver type is set.");

            // export to fmu
            ml.eval("exportToFMU('%s', 'FMIVersion', '2.0', 'FMUType', 'CS')".format(modelName));
            System.out.println("Model exported.");

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
