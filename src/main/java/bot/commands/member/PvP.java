package bot.commands.member;

import bot.commands.ICommand;
import bot.managers.pvp.PvPManager;
import bot.player.Player;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import database.DatabaseManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PvP implements ICommand {
    private final EventWaiter eventWaiter;
    private static final int FIGHT_ACCEPT_TIME = 15;

    public PvP(EventWaiter eventWaiter){
        this.eventWaiter = eventWaiter;
    }

    @Override
    public String getName() {
        return "pvp";
    }

    @Override
    public void execute(GuildMessageReceivedEvent event, List<String> args) {
        Player player1 = DatabaseManager.getMember("ExperiencePoints", event.getGuild().getId(), event.getMember().getId());

        try{
            String player2ID = event.getMessage().getMentionedMembers().get(0).getId();

//            if(player1.getMemberID().equals(player2ID)){
//                throw new IllegalArgumentException();
//            }

            Player player2 = DatabaseManager.getMember("ExperiencePoints", event.getGuild().getId(), player2ID);

            acceptFight(player1, player2, event);
        } catch(Exception e){
            event.getChannel().sendMessage("Wybrano niewłaściwego przeciwnika.").queue();
        }
    }

    private void acceptFight(Player ally, Player enemy, GuildMessageReceivedEvent event){
        AtomicBoolean accepted = new AtomicBoolean(false);

        event.getChannel().sendMessage(enemy.getName(event) + " masz " + FIGHT_ACCEPT_TIME + " sekund na zaakceptowanie walki! (Y/N)").queue();

        eventWaiter.waitForEvent(GuildMessageReceivedEvent.class, e -> e.getAuthor().getId().equals(enemy.getMemberID()) && e.getChannel().equals(event.getChannel()) , e -> {
            String reply = e.getMessage().getContentRaw().toLowerCase();

           //if(reply.equals("y")){
                accepted.set(true);
                PvPManager.initializeRound(ally, enemy, eventWaiter, event);
            //} else {
            //    event.getChannel().sendMessage( enemy.getName(event) + " nie zaakceptował walki.").queue();
            //}
        }, FIGHT_ACCEPT_TIME, TimeUnit.SECONDS, () -> {
            if(accepted.equals(new AtomicBoolean(false))){
                event.getChannel().sendMessage(enemy.getName(event) + " nie zaakceptował walki!").queue();
            }
        });

    }
}
