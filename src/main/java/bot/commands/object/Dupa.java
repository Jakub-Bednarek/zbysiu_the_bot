package bot.commands.object;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class Dupa extends FunMessage {

    public Dupa(){
        super("https://cdn.galleries.smcloud.net/t/galleries/gf-PQUy-2srs-Y1FT_morawiecki-upomina-ukraincow-664x442-nocrop.JPG");
    }

    public Dupa(String dupaURL){
        super(dupaURL);
    }

    @Override
    public MessageEmbed asMessageEmbed(){
        return new EmbedBuilder().setImage(getImageURL()).setColor(Color.RED).build();
    }
}
