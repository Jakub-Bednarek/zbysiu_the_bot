package bot.commands.entertainment;

import bot.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class JerkOff implements ICommand {
    @Override
    public String getName() {
        return "koń";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        for(int i = 0; i < 3; i++){
            event.getChannel().sendMessage("Fap, fap").queueAfter(i + 1, TimeUnit.SECONDS);
        }
        for(int i = 0; i < 3; i++){
            event.getChannel().sendMessage("Fap, fap").queueAfter((i / 2) + 4, TimeUnit.SECONDS);
        }
        for(int i = 0; i < 3; i++){
            event.getChannel().sendMessage("Fap, fap").queueAfter(7, TimeUnit.SECONDS);
        }

        event.getChannel().sendMessage("Właśnie ospermiłeś sobie gacie").queueAfter(8, TimeUnit.SECONDS);

        event.getChannel().sendMessage(new EmbedBuilder().setImage("https://i.imgur.com/G4Pn1Hp.jpg").build()).queueAfter(9, TimeUnit.SECONDS);
    }
}
