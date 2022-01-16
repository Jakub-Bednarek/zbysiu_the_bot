package bot.commands.entertainment;

import bot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class PWR implements ICommand {
    @Override
    public String getName(){
        return "pwr";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args){
        EmbedBuilder builder = new EmbedBuilder();
        MessageChannel source = event.getChannel();

        builder.setColor(Color.RED);
        builder.setThumbnail("https://i.imgur.com/qsz41MP.jpg");
        builder.setTitle("PODSTAWOWE PRAWA PWR");
        builder.addField(":poop:PRAWO NUMER 1:poop:", "Jebać W4", false);
        builder.addField(":poop:PRAWO NUMER 2:poop:", "Lepiej chujem trzeć po żwirze, niż studiować na AiRze", false);

        source.sendMessage(builder.build()).queue();
    }
}
