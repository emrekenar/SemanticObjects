package omc;

public class ConvertModelica {
    private String path;

    public ConvertModelica(String path) {
        this.path = path;
    }

    public boolean run() {
        try {
            String[] commands = {
                "installPackage(Modelica);",
                "loadModel(Modelica);",
                "loadFile(%s);".format(path),
                "buildModelFMU(Tank, version=\"2.0\", fmuType=\"me_cs\");",
                "getErrorString()"
            };
            
            for (String command : commands) {
                Process proc = new ProcessBuilder(command).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
