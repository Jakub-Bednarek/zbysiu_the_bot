package bot.managers.activity;

import bot.player.Player;
import bot.player.hero.attributes.Statistics;
import database.DatabaseManager;
import messagetemplates.MessageEmbedTemplate;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ActivityManager extends ListenerAdapter {
    private static final long MINUTE_MILLIS = 60000;
    private static final String DATABASE_NAME = "ExperiencePoints";
    private final Map<String, Long> memberJoinTimes = new HashMap<>();

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(!event.getAuthor().isBot()){
            Player player = DatabaseManager.getMember(DATABASE_NAME, event.getGuild().getId(), event.getAuthor().getId());
            player.incrementMessages();

            boolean leveledUp = addXp(event.getGuild().getId(), player, 1);

            if(leveledUp){
                event.getChannel().sendMessage(MessageEmbedTemplate.buildLevelUpNotification(event, player.getLevel())).queue();
            }
        }
    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        long joinTime = System.currentTimeMillis();
        String memberID = event.getMember().getId();

        memberJoinTimes.put(memberID, joinTime);
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        String guildID = event.getGuild().getId();
        String memberID = event.getMember().getId();
        Player player = DatabaseManager.getMember("ExperiencePoints", guildID, memberID);
        double voiceTime = (double)((System.currentTimeMillis() - memberJoinTimes.get(memberID)) / MINUTE_MILLIS);

        memberJoinTimes.remove(memberID);
        player.addVoiceTime((int)voiceTime);
        player.addCoins((double)(voiceTime / 5));

        DatabaseManager.updateMember("ExperiencePoints", guildID, player);
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        DatabaseManager.removeMember("ExperiencePoints", event.getGuild().getId(), event.getMember().getId());
    }

    public List<Player> getSortedMembers(String guildID){
        List<Player> players = DatabaseManager.getMembersList("ExperiencePoints", guildID);
        Collections.sort(players);

        return players;
    }

    public int getMemberRank(String guildID, String memberID){
        List<Player> players = getSortedMembers(guildID);

        return players.indexOf(new Player(memberID, new Statistics(0, 0, 0), null, null, null)) + 1;
    }

    private boolean addXp(String guildID, Player player, int xpToAdd){
        boolean leveledUp = player.addXp(xpToAdd);
        DatabaseManager.updateMember(DATABASE_NAME, guildID, player);

        return leveledUp;
    }
}