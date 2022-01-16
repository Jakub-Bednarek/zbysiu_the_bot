package bot.commands.entertainment;

import bot.commands.CommandsManager;
import bot.commands.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Witoj implements ICommand {

    @Override
    public String getName() {
        return "witoj";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        MessageEmbed message = CommandsManager.getObjectsManager().getRandomPiwo();

        event.getChannel().sendMessage(message).queue();
    }
}
