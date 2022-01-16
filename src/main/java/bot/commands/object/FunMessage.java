package bot.commands.object;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public abstract class FunMessage {
    private String imageURL;

    public FunMessage(String imageURL){
        this.imageURL = imageURL;
    }

    public MessageEmbed asMessageEmbed(){
        return new EmbedBuilder().setImage(imageURL).build();
    }

    public String getImageURL(){
        return this.imageURL;
    }
}
