package bot.commands.entertainment;

import bot.commands.ICommand;
import bot.commands.object.Joke;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MakeJoke implements ICommand {
    @Override
    public String getName() {
        return "żarcik";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        Joke joke = new Joke();
        TextChannel channel = event.getChannel();

        channel.sendMessage(joke.getSetup()).queue();
        channel.sendMessage(joke.getPunchline()).queueAfter(2, TimeUnit.SECONDS);
    }
}
