package bot.commands.object;

import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.ArrayList;
import java.util.Random;

public class ObjectsManager {
    private static ArrayList<FunMessage> dupy;
    private static ArrayList<FunMessage> piwa;
    private static Random generator = new Random();

    public ObjectsManager(){
        dupy = ResourcesLoader.loadResource(FunType.DUPA);
        piwa = ResourcesLoader.loadResource(FunType.PIWO);
    }

    public MessageEmbed getRandomDupa(){
        int index = generator.nextInt(dupy.size());

        return dupy.get(index).asMessageEmbed();
    }

    public MessageEmbed getRandomPiwo(){
        int index = generator.nextInt(piwa.size());

        return piwa.get(index).asMessageEmbed();
    }
}
