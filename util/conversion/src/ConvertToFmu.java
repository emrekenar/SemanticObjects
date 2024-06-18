import simulink.ConvertSimulink;
import omc.ConvertModelica;

public class ConvertToFmu {
    public static void main(String args[]) {
        // get file path to be converted into fmu
        String path = "tank.slx";
        if (args.length > 0)
            path = args[0];

        // extract model name
        String[] pathElements = path.split("[/]");
        String baseDir = "";
        for (int i = 0; i < pathElements.length - 1; ++i) {
            baseDir += pathElements[i];
            baseDir += "\\";
        }
        String modelName = pathElements[pathElements.length - 1].split("[.]")[0];

        // forward request to openmodelica or simulink converter by input model
        String[] split = path.split("[.]");
        String extension = split[split.length - 1];
        if (extension.equals("slx")) {
            boolean result = new ConvertSimulink(path, modelName).run();
            if (result) {
                System.out.println("success");
            }
        } else if (extension.equals("mo")) {
            boolean result = new ConvertModelica(path, modelName).run();
            if (result) {
                System.out.println("success");
            }
        } else {
            System.out.println("Invalid file: %s. Only .mo and .slx files are supported.".format(path));
        }
    }
}
