package bot.commands.member;

import bot.commands.ICommand;
import bot.player.Player;
import database.DatabaseManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Daily implements ICommand {
    @Override
    public String getName() {
        return "daily";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        Player player = DatabaseManager.getMember("ExperiencePoints", event.getGuild().getId(), event.getMember().getId());

        if(player.isDailyGrabbed()){
            event.getChannel().sendMessage("Codzienny przelewik od prezesa: 25 :coin:");
            DatabaseManager.updateMember("ExperiencePoints", event.getGuild().getId(), player);
        } else {
            event.getChannel().sendMessage("A Ty chujku, nie ma 2 razy, czekaj do jutra.");
        }
    }
}
