package bot.source;

import bot.commands.CommandsManager;
import bot.commands.entertainment.*;
import bot.commands.member.GetExp;
import bot.commands.member.PvP;
import bot.commands.member.Ranking;
import bot.commands.music.Clear;
import bot.commands.music.Current;
import bot.commands.music.Play;
import bot.commands.music.Skip;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import config.Config;
import bot.managers.activity.ActivityManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Zbyszek {
    private static final CommandsManager COMMANDS_MANAGER = new CommandsManager();
    private static final ActivityManager ACTIVITY_MANAGER = new ActivityManager();
    private static final EventWaiter EVENT_WAITER = new EventWaiter();

    public static void main(String[] args){
        buildBot();
        addCommands();
    }

    public static ActivityManager getActivityManagerInstance(){
        return ACTIVITY_MANAGER;
    }

    private static void buildBot(){
        try{
            JDA botAPI = JDABuilder.createDefault(Config.get(Config.get("BOTMODE")),
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.GUILD_EMOJIS,
                    GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.GUILD_VOICE_STATES)
                    .addEventListeners(COMMANDS_MANAGER)
                    .addEventListeners(ACTIVITY_MANAGER)
                    .addEventListeners(EVENT_WAITER)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setActivity(Activity.listening(Config.get("PREFIX") + "pomocy"))
                    .setStatus(OnlineStatus.ONLINE)
                    .build();

            System.out.println("Build succeeded");
        } catch(LoginException e){
            System.out.println("Failed to login with" + Config.get("BOTMODE") + "token.");
            e.printStackTrace();
        }
    }

    private static void addCommands(){
        try{
            //entertainment
            COMMANDS_MANAGER.add(new Help());
            COMMANDS_MANAGER.add(new PWR());
            COMMANDS_MANAGER.add(new Dab());
            COMMANDS_MANAGER.add(new JerkOff());
            COMMANDS_MANAGER.add(new MakeJoke());
            COMMANDS_MANAGER.add(new GiveMeme());
            COMMANDS_MANAGER.add(new Witoj());
            COMMANDS_MANAGER.add(new AleDupa());

            //music
            COMMANDS_MANAGER.add(new Play());
            COMMANDS_MANAGER.add(new Skip());
            COMMANDS_MANAGER.add(new Clear());
            COMMANDS_MANAGER.add(new Current());

            //leveling
            COMMANDS_MANAGER.add(new GetExp());
            COMMANDS_MANAGER.add(new Ranking());
            COMMANDS_MANAGER.add(new PvP(EVENT_WAITER));
        } catch(IllegalArgumentException e){
            System.out.println("Possible duplicate of commands to add");
            e.printStackTrace();
        }
    }
}
