package bot.commands.object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ResourcesLoader {
    public static ArrayList<FunMessage> loadResource(FunType type) {
        ArrayList<FunMessage> output = new ArrayList<>();
        String filePath = null;

        if(type == FunType.PIWO){
            filePath = "/piwoURLs";
        } else if(type == FunType.DUPA){
            filePath = "/dupaURLs";
        }

        try(BufferedReader inputReader = new BufferedReader(new InputStreamReader(ResourcesLoader.class.getResourceAsStream(filePath)))) {
            String line;

            while((line = inputReader.readLine()) != null){
                if(type == FunType.PIWO){
                    output.add(new Piwo(line));
                } else {
                    output.add(new Dupa(line));
                }
            }
        } catch(IOException exception){
            if(type == FunType.PIWO){
                output.add(new Piwo());
            } else {
                output.add(new Dupa());
            }
        }

        return output;
    }
}
