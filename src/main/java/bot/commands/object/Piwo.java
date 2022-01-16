package bot.commands.object;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class Piwo extends FunMessage{

    public Piwo(){
        super("https://carlsbergpolska.pl/media/28690/harnas-puszka.png");
    }

    public Piwo(String piwoURL){
        super(piwoURL);
    }

    @Override
    public MessageEmbed asMessageEmbed(){
        return new EmbedBuilder().setImage(getImageURL()).setColor(Color.RED).build();
    }
}
