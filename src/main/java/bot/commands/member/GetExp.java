package bot.commands.member;

import bot.commands.ICommand;
import bot.player.Player;
import database.DatabaseManager;
import messagetemplates.MessageEmbedTemplate;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class GetExp implements ICommand {
    @Override
    public String getName() {
        return "xp";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        Player player = DatabaseManager.getMember("ExperiencePoints", event.getGuild().getId(), event.getAuthor().getId());

        event.getChannel().sendMessage(MessageEmbedTemplate.buildMemberXpOverview(event, player)).queue();
    }
}
