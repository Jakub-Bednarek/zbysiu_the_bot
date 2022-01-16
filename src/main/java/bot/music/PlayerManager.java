package bot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import messagetemplates.MessageEmbedTemplate;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public PlayerManager() {
        this.playerManager = new DefaultAudioPlayerManager();
        this.musicManagers = new HashMap<>();

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild){
        long guildID = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildID);

        if(musicManager == null){
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildID, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(GuildMessageReceivedEvent event, String musicSource){
        TextChannel channel = event.getChannel();
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, musicSource, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage(MessageEmbedTemplate.buildTrackInfo(event, track)).queue();

                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                List<AudioTrack> tracks = playlist.getTracks();

                channel.sendMessage(MessageEmbedTemplate.buildPlaylistInfo(event, playlist, musicSource)).queue();

                if(tracks.get(0) != null){

                    for(AudioTrack track : tracks){
                        play(musicManager, track);
                    }
                }
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Chyba Ci sie linki pojebały bo ja nic nie znalazłem.").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Coś sie zjebało, nie ma muzyki, nie ma biby.").queue();
            }
        });
    }

    public void skip(GuildMusicManager musicManager){ musicManager.scheduler.nextTrack(); }

    public static synchronized PlayerManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    public void createNewGuildMusicManager(Guild guild){
        long guildID = guild.getIdLong();

        if(musicManagers.get(guildID) != null){
            musicManagers.remove(guildID);

            GuildMusicManager newManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildID, newManager);
        }
    }

    private void play(GuildMusicManager musicManager, AudioTrack track){
        musicManager.scheduler.queue(track);
    }
}