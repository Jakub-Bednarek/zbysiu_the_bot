package bot.commands.entertainment;

import bot.commands.ICommand;
import config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class Help implements ICommand {
    @Override
    public String getName(){
        return "pomocy";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args){
        EmbedBuilder builder = new EmbedBuilder();
        String prefix = Config.get("PREFIX");

        builder.addField("<:PepeHappy:618186012044034070><:pepeOK:618186013608640555>NA DOBRY HUMOREK<:pepeOK:618186013608640555><:PepeHappy:618186012044034070>",
                                "<:TranSwastika:618185030958710849> " + prefix + "pomocy\n"
                                     + "<:TranSwastika:618185030958710849> " + prefix + "pwr\n"
                                     + "<:TranSwastika:618185030958710849> " + prefix + "dab\n"
                                     + "<:TranSwastika:618185030958710849> " + prefix + "koń\n"
                                     + "<:TranSwastika:618185030958710849> " + prefix + "żarcik\n"
                                     + "<:TranSwastika:618185030958710849> " + prefix + "dajMema\n"
                                     + "<:TranSwastika:618185030958710849> " + prefix + "aleDupa\n"
                                     + "<:TranSwastika:618185030958710849> " + prefix + "witoj\n",
                                false);

        builder.addField(":notes: MUZYKA :notes:",
                          "<:TranSwastika:618185030958710849> " + prefix + "z    <YouTube link> / <YouTube video name>\n"
                              + "<:TranSwastika:618185030958710849> " + prefix + "goNext\n"
                              + "<:TranSwastika:618185030958710849> " + prefix + "clear\n"
                              + "<:TranSwastika:618185030958710849> " + prefix + "current\n",
                          false);

        builder.addField(":up: LEVELING :up:",
                "<:TranSwastika:618185030958710849> " + prefix + "xp\n" +
                      "<:TranSwastika:618185030958710849> " + prefix + "ranking <ilość osób do wyświetlenia>\n" +
                      "<:TranSwastika:618185030958710849> " + prefix + "pvp <nick przeciwnika>\n",
                false);

        builder.setColor(Color.RED);

        event.getChannel().sendMessage(builder.build()).queue();
    }
}
