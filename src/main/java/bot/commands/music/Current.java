package bot.commands.music;

import bot.commands.ICommand;
import bot.music.GuildMusicManager;
import messagetemplates.MessageEmbedTemplate;
import bot.music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Current implements ICommand {
    @Override
    public String getName() {
        return "current";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        AudioTrack currentTrack = getCurrentTrack(event.getGuild());

        if(currentTrack != null){
            MessageEmbed songInfo = MessageEmbedTemplate.buildTrackInfo(event, currentTrack);

            event.getChannel().sendMessage(songInfo).queue();
        } else {
            event.getChannel().sendMessage("Tak tu pusto, że wieje chujem.").queue();
        }
    }

    @Nullable
    private AudioTrack getCurrentTrack(Guild guild){
        GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(guild);
        AudioTrack currentTrack = musicManager.player.getPlayingTrack();

        return currentTrack;
    }
}
