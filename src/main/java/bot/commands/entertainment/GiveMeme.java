package bot.commands.entertainment;

import bot.commands.ICommand;
import bot.commands.object.Meme;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class GiveMeme implements ICommand {
    @Override
    public String getName() {
        return "dajmema";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        EmbedBuilder builder = new EmbedBuilder();
        MessageChannel source = event.getChannel();
        Meme meme = new Meme();

        builder.setColor(Color.RED);
        builder.addField(meme.getTitle(), "Author: " + meme.getAuthor(), false);
        builder.setImage(meme.getURL());
        source.sendMessage(builder.build()).queue();
    }
}
