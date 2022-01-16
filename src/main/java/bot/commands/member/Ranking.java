package bot.commands.member;

import bot.commands.ICommand;
import bot.player.Player;
import bot.source.Zbyszek;
import messagetemplates.MessageEmbedTemplate;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Ranking implements ICommand {
    private static final int DEFAULT_MEMBERS_COUNT = 10;
    @Override
    public String getName() {
        return "ranking";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        int nMembers;

        try{
            nMembers = Integer.parseInt(args.get(0));

            if(nMembers <= 0){
                throw new IllegalArgumentException();
            }
        } catch(Exception e){
            nMembers = DEFAULT_MEMBERS_COUNT;
        }

        List<Player> topPlayers = Zbyszek.getActivityManagerInstance().getSortedMembers(event.getGuild().getId());

        event.getChannel().sendMessage(MessageEmbedTemplate.buildRanking(event, topPlayers, nMembers)).queue();
    }
}
