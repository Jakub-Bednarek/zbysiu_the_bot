package bot.commands.music;

import bot.commands.ICommand;
import bot.music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Clear implements ICommand {
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        PlayerManager playerManager = PlayerManager.getInstance();

        if(!playerManager.getGuildMusicManager(event.getGuild()).scheduler.isQueueEmpty()
                || playerManager.getGuildMusicManager(event.getGuild()).scheduler.isPlaying()){

            playerManager.createNewGuildMusicManager(event.getGuild());
            event.getGuild().getAudioManager().closeAudioConnection();
        } else {
            event.getChannel().sendMessage("Co ja mam Ci usunąć? Pryszcza z mordy? Tu nic nie ma.").queue();
        }
    }
}
