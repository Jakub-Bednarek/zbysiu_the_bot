package bot.commands.music;

import bot.commands.ICommand;
import bot.exceptions.NoVideoFoundException;
import bot.music.PlayerManager;
import bot.music.YouTubeDataFetcher;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Play implements ICommand {
    @Override
    public String getName() {
        return "z";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        if(args.size() < 1){
            event.getChannel().sendMessage("Chyba link dostał nóżek i spierdolił").queue();
            return;
        }

        if(joinChannel(event)){
            PlayerManager playerManager = PlayerManager.getInstance();

            if(!event.getMessage().getAuthor().isBot()){
                try{
                    event.getMessage().delete().queue();
                } catch(Exception e){
                    System.out.println("No permission to delete message");
                }
            }

            try{
                String videoLink = getLink(args);
                playerManager.getGuildMusicManager(event.getGuild()).player.setVolume(30);
                playerManager.loadAndPlay(event, videoLink);
            } catch(Exception e){
                event.getChannel().sendMessage("Ni chuja nie ma filmiku z taką nazwą.");
            }
        }
    }

    private boolean joinChannel(GuildMessageReceivedEvent event){
        VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
        VoiceChannel authorChannel = event.getMember().getVoiceState().getChannel();

        if(authorChannel == null){
            event.getChannel().sendMessage("Dołącz do jakiegoś kanału debilu.").queue();
            return false;
        } else if(connectedChannel != null && connectedChannel != authorChannel){
            event.getChannel().sendMessage("Nie rozdwoje sie do chuja pana.").queue();
            return false;
        }

        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.openAudioConnection(authorChannel);

        return true;
    }

    private boolean isLink(String argument){
        try{
            URL url = new URL(argument);
        } catch(MalformedURLException e){
            return false;
        }

        return true;
    }

    private String getLink(List<String> argument) throws IOException, NoVideoFoundException {
        String videoName = argument.get(0);

        if(!isLink(videoName)){
            videoName = buildVideoName(argument);
            videoName = YouTubeDataFetcher.getYouTubeVideoLink(videoName);
        }

        return videoName;
    }

    private String buildVideoName(List<String> args){
        String videoName = "";

        for(int i = 0; i < args.size(); i++){
            videoName += args.get(i) + " ";
        }

        return videoName;
    }
}
