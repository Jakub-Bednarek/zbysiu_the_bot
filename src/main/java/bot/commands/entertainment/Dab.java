package bot.commands.entertainment;

import bot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class Dab implements ICommand {
    @Override
    public String getName(){
        return "dab";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        MessageChannel source = event.getChannel();
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.RED);
        builder.setImage("https://media1.tenor.com/images/37d783add41d40d1353f7d6484d529c9/tenor.gif?itemid=14051784");

        source.sendMessage(builder.build()).queue();
    }
}
