import simulink.ConvertSimulink;
import omc.ConvertModelica;

public class ConvertToFmu {
    public static void main(String args[]) {
        // get file path to be converted into fmu
        String path = "tank.slx";
        if (args.length > 0)
            path = args[0];
        System.out.println(path);

        // forward request to openmodelica or simulink converter by input path
        String[] split = path.split("[.]");
        String extension = split[split.length - 1];
        if (extension.equals("slx")) {
            boolean result = new ConvertSimulink(path).run();
            if (result) {
                System.out.println("success");
            }
        } else if (extension.equals("mo")) {
            boolean result = new ConvertModelica(path).run();
            if (result) {
                System.out.println("success");
            }
        } else {
            System.out.println("Invalid file: %s. Only .mo and .slx files are supported.".format(path));
        }
    }
}
