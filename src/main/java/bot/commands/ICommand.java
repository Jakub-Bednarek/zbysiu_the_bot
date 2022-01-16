package bot.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public interface ICommand {
    String getName();

    void execute(GuildMessageReceivedEvent event, List<String> args);
}
