package bot.commands.entertainment;

import bot.commands.CommandsManager;
import bot.commands.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class AleDupa implements ICommand {
    @Override
    public String getName() {
        return "aledupa";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        MessageEmbed message = CommandsManager.getObjectsManager().getRandomDupa();

        event.getChannel().sendMessage(message).queue();
    }
}
