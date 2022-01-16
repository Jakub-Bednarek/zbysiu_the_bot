package bot.commands.music;

import bot.commands.ICommand;
import bot.music.GuildMusicManager;
import bot.music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Skip implements ICommand {
    @Override
    public String getName() {
        return "gonext";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        PlayerManager manager = PlayerManager.getInstance();
        GuildMusicManager musicManager = manager.getGuildMusicManager(event.getGuild());

        manager.skip(musicManager);

        if(manager.getGuildMusicManager(event.getGuild()).scheduler.isQueueEmpty() && !manager.getGuildMusicManager(event.getGuild()).scheduler.isPlaying()){
            event.getGuild().getAudioManager().closeAudioConnection();
        }
    }
}
