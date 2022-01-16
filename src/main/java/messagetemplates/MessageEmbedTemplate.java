package messagetemplates;

import bot.player.hero.experience.Level;
import bot.player.Player;
import bot.source.Zbyszek;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class MessageEmbedTemplate {
    public static MessageEmbed buildTrackInfo(GuildMessageReceivedEvent event, AudioTrack track){
        String songTitle = ":musical_note: " + track.getInfo().title;
        String songLink = track.getInfo().uri;

        return new MessageEmbed(songLink,
                songTitle,
                null,
                null,
                null,
                0xff0000,
                new MessageEmbed.Thumbnail("https://i.imgur.com/aTOtU1T.jpg", null, 192, 108),
                null,
                null,
                null,
                new MessageEmbed.Footer("Dodał: " + event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl(), null),
                null,
                buildSongFields(track));
    }

    public static MessageEmbed buildPlaylistInfo(GuildMessageReceivedEvent event, AudioPlaylist playlist, String playlistURL){
        String playlistName = "<:yt:794937590322429983> " + playlist.getName() + " <:yt:794937590322429983>";

        return new MessageEmbed(playlistURL,
                playlistName,
                null,
                null,
                null,
                0xff0000,
                new MessageEmbed.Thumbnail("https://i.imgur.com/K5aMQ5T.jpg", null, 192, 192),
                null,
                null,
                null,
                new MessageEmbed.Footer("Dodał: " + event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl(), null),
                null,
                buildPlaylistFields(playlist));
    }

    public static MessageEmbed buildLevelUpNotification(GuildMessageReceivedEvent event, int level){
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.RED);
        builder.setTitle(":up: LEVEL UP :up: " + event.getAuthor().getName() + " wchodzi na nowy level: " + EmojiConverter.convertDigitsToEmojiStrings(level));

        return builder.build();
    }

    public static MessageEmbed buildRanking(GuildMessageReceivedEvent event, List<Player> topPlayers, int nMembers){
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.RED);
        builder.setTitle(":partying_face: RANKING TOP " + nMembers + " SIMPÓW NA SERWERZE " + event.getGuild().getName() + " :partying_face:");
        int skipped = 0;

        for(int i = 0; (i < topPlayers.size()) && (i < nMembers + skipped); i++){
            String name;
            try{
                name = Objects.requireNonNull(event.getGuild().getMemberById(topPlayers.get(i).getMemberID())).getEffectiveName();
            } catch(NullPointerException e){
                skipped++;
                continue;
            }

            String memberInfo = ":fire: " + topPlayers.get(i).getLevel() + "  <:jmr_mellowyellow:805580327945895976> " + topPlayers.get(i).getXp() + " XP";

            if(i == 0){
                builder.addField(":first_place: " + name, memberInfo, true);
            } else if(i == 1){
                builder.addField(":second_place: " + name, memberInfo, true);
            } else if(i == 2){
                builder.addField(":third_place: " + name, memberInfo, true);
            } else {
                builder.addField((i + 1 - skipped) + "# " + name, memberInfo, true);
            }
        }

        return builder.build();
    }

    public static MessageEmbed buildMemberXpOverview(GuildMessageReceivedEvent event, Player player){
        EmbedBuilder builder = new EmbedBuilder();
        int xp = player.getXp();
        int level = player.getLevel();
        double coins = player.getCoins();
        int messages = player.getMessages();
        double activityTime = (double)player.getVoiceTime() / 60;
        int ranking = Zbyszek.getActivityManagerInstance().getMemberRank(event.getGuild().getId(), player.getMemberID());

        builder.setAuthor(event.getAuthor().getAsTag(), null, event.getAuthor().getAvatarUrl());
        builder.addField("XP <:jmr_mellowyellow:805580327945895976>", String.valueOf(xp), true);
        builder.addField("Level :fire:", String.valueOf(level), true);
        builder.addField("Coins :coin:", String.format(Locale.CANADA, "%.1f", coins), true);
        builder.addField("Voice :microphone2:", String.format(Locale.CANADA, "%.1f", activityTime), true);
        builder.addField("Messages :writing_hand:", String.valueOf(messages), true);
        builder.addField("Rank :trophy: ", String.valueOf(ranking), true);
        builder.addField(buildProgressBarField(xp, level));
        builder.addField(buildStatisticsField(player));
        builder.addBlankField(true);
        builder.addField(buildResourcesField(player));
        builder.addField(buildOffencesField(player));
        builder.addBlankField(true);
        builder.addField(buildDefencesField(player));

        return builder.build();
    }

    public static MessageEmbed buildPvPRoundStart(Player player1, Player player2, GuildMessageReceivedEvent event){
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.RED);
        builder.setTitle(player1.getName(event) + ":crossed_swords: VS :crossed_swords: " + player2.getName(event));
        builder.setImage("https://i.imgur.com/eZsSWOF.png");

        return builder.build();
    }

    public static MessageEmbed buildFightsOptions(Player player, GuildMessageReceivedEvent event){
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.RED);
        builder.setTitle(player.getName(event) + " PLAYER OVERVIEW :boom:");
        builder.addField(buildResourcesField(player));
        builder.addField(buildOffencesField(player));
        builder.addField(buildDefencesField(player));
        builder.addField("FIGHTING OPTIONS", "1. Attack \n2. Use spell", false);

        return builder.build();
    }

    private static String buildDuration(long songDuration){
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(songDuration);

        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        return formatter.format(date);
    }

    private static List<MessageEmbed.Field> buildSongFields(AudioTrack track){
        MessageEmbed.Field author = new MessageEmbed.Field("Author", track.getInfo().author, true);
        MessageEmbed.Field duration = new MessageEmbed.Field("Duration", buildDuration(track.getDuration()), true);

        List<MessageEmbed.Field> fields = new ArrayList<>();
        fields.add(author);
        fields.add(duration);

        return fields;
    }

    private static String playlistDuration(AudioPlaylist playlist){
        List<AudioTrack> tracks = playlist.getTracks();
        long duration = 0;

        for(AudioTrack t : tracks){
            duration += t.getDuration();
        }

        return buildDuration(duration);
    }

    private static List<MessageEmbed.Field> buildPlaylistFields(AudioPlaylist playlist){
        MessageEmbed.Field songs = new MessageEmbed.Field(":notes:Songs:notes:", String.valueOf(playlist.getTracks().size()), true);
        MessageEmbed.Field duration = new MessageEmbed.Field(":stopwatch:Duration:stopwatch:", playlistDuration(playlist), true);

        List<MessageEmbed.Field> fields = new ArrayList<>();
        fields.add(songs);
        fields.add(duration);

        return fields;
    }

    private static MessageEmbed.Field buildProgressBarField(int xp, int level){
        int requiredXp = Level.getRequiredXp(level + 1);
        String progressBar = buildProgressBar(xp, requiredXp);

        return new MessageEmbed.Field("Progress:\t" + xp + "/" + requiredXp, progressBar, false);
    }

    private static String buildProgressBar(int xp, int requiredXp){
        double percentage = (double)xp/(double)requiredXp * 100.0;
        int fullEmojis = (int)(percentage / 10);
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < 10; i++){
            if(i < fullEmojis){
               builder.append("<:simp_color:803386765461159986>");
            } else {
                builder.append("<:simp_gray:803386785127858227>");
            }
        }

        return builder.toString();
    }

    //player fields building

    private static MessageEmbed.Field buildStatisticsField(Player player){
        return new MessageEmbed.Field("Statistics", ":magic_wand: Int: " + player.getStatistics().getIntelligence() + "\n"
                + ":mechanical_arm: Str: " + player.getStatistics().getStrength() + "\n"
                + ":cloud_tornado: Dex: " + player.getStatistics().getDexterity(), true);
    }

    private static MessageEmbed.Field buildResourcesField(Player player){
        return new MessageEmbed.Field("Resources", ":drop_of_blood: HP: " + player.getHealthPoints() + "\n"
                + ":droplet: MP: " + player.getManaPoints() + "\n"
                + ":mechanical_leg: SP: " + player.getStaminaPoints(), true);
    }

    private static MessageEmbed.Field buildOffencesField(Player player){
        return new MessageEmbed.Field("Offence", ":crossed_swords: AD: " + player.getAttackDamage() + "\n"
                + ":comet:  AP: " + player.getAbilityPower(), true);
    }

    private static MessageEmbed.Field buildDefencesField(Player player){
        return new MessageEmbed.Field("Defence", ":shield: Armor: " + player.getArmor() + "\n"
                + ":dash: Dodge: " + player.getDodge(), true);
    }
}
