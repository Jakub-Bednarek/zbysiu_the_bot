package bot.commands;

import bot.commands.object.ObjectsManager;
import config.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandsManager extends ListenerAdapter {
    private final List<ICommand> commands = new ArrayList<>();
    protected final static ObjectsManager MANAGER = new ObjectsManager();

    public void add(ICommand command) throws IllegalArgumentException{
        boolean exists = commands.stream().anyMatch( (c -> c.getName().equalsIgnoreCase(command.getName()) ));

        if(exists){
            throw new IllegalArgumentException("Command exists");
        }

        commands.add(command);

        System.out.println("Added new " + command.getName() + " command.");
    }

    public static ObjectsManager getObjectsManager(){
        return MANAGER;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        if(event.getAuthor().isBot()) return;
        if(!event.getMessage().getContentRaw().startsWith(Config.get("PREFIX"))) return;

        List<String> args = new LinkedList<String>(Arrays.asList(event.getMessage().getContentRaw().split("\\s+")));
        String commandName = trimArg(args.get(0));

        ICommand command = get(commandName);

        if(command != null){
            args.remove(0);
            command.execute(event, args);
        }
    }

    @Nullable
    private ICommand get(String commandName){
        String commandNameLower = commandName.toLowerCase();

        for(ICommand c : commands){
            if(c.getName().equals(commandNameLower)){
                return c;
            }
        }

        return null;
    }

    private String trimArg(String arg){
        String prefix = Config.get("PREFIX");

        return arg.replace(prefix, "");
    }
}
