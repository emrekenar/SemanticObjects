package omc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;

public class ConvertModelica {
    private String path;
    private String modelName;

    public ConvertModelica(String path, String modelName) {
        this.path = path;
        this.modelName = modelName;
    }

    public boolean run() {
        try {
            // read template file
            File templateFile = new File("omc/generate_fmu_template.mos");
            Scanner myReader = new Scanner(templateFile);
            String template = "";
            while (myReader.hasNextLine())
                template += myReader.nextLine() + "\n";
            myReader.close();

            // pass arguments for path name and model name
            String mosFile = template.replace("$1", path).replace("$2", modelName);

            // write with arguments
            FileWriter writer = new FileWriter("generate_fmu.mos");
            writer.write(mosFile);
            writer.close();

            // convert to fmu by openmodelica with the omc command
            ProcessBuilder pb = new ProcessBuilder("omc", "generate_fmu.mos");
            pb.inheritIO();
            Process p = pb.start();
            int exitStatus = p.waitFor();
            System.out.println(exitStatus);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
