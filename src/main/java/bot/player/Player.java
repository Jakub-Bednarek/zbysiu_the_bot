package bot.player;

import bot.player.hero.Hero;
import bot.player.hero.attributes.Statistics;
import bot.player.hero.experience.Level;
import bot.player.consumables.Coins;
import bot.player.utility.Activity;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Player implements Comparable<Player>{
    private final String memberID;
    private final Hero hero;
    private final Level level;
    private final Coins coins;
    private final Activity activity;

    public Player(String memberID, Statistics statistics, Level level, Coins coins, Activity activity){
        this.memberID = memberID;
        this.hero = new Hero(statistics);
        this.level = level;
        this.coins = coins;
        this.activity = activity;
    }

    public String getName(GuildMessageReceivedEvent event){
        return event.getGuild().getMemberById(memberID).getEffectiveName();
    }

    public String getMemberID() {
        return memberID;
    }

    public Statistics getStatistics(){
        return hero.getStatistics();
    }

    public double getHealthPoints(){
        return hero.getPoints().getHealth();
    }

    public double getManaPoints(){
        return hero.getPoints().getMana();
    }

    public double getStaminaPoints(){
        return hero.getPoints().getStamina();
    }

    public double getArmor(){
        return hero.getDefence().getArmor();
    }

    public double getDodge(){
        return hero.getDefence().getDodge();
    }

    public int getLevel(){
        return level.getLevel();
    }

    public int getXp(){
        return level.getXp();
    }

    public double getAttackDamage(){
        return hero.getDamage().getAttackDamage();
    }

    public double getAbilityPower(){
        return hero.getDamage().getAbilityPower();
    }

    public double getCoins(){
        return coins.get();
    }

    public int getVoiceTime(){
        return activity.getVoiceTime();
    }

    public int getMessages(){
        return activity.getMessages();
    }

    public boolean isDailyGrabbed(){
        return activity.isGrabbedDaily();
    }

    public void takeHit(double damage){
        hero.takeHit(damage);
    }

    public void incrementMessages(){
        activity.incrementMessages();
    }

    public void addVoiceTime(double timeToAdd){
        activity.addVoiceTime(timeToAdd);
    }

    public void addCoins(double coinsToAdd){
        coins.add(coinsToAdd);
    }

    public boolean addXp(int xpToAdd){
        return  level.addXp(xpToAdd);
    }

    @Override
    public int compareTo(Player other) {
        int levelC = Integer.compare(other.getLevel(), level.getLevel());

        return levelC == 0 ? Integer.compare(other.getXp(), level.getXp()) : levelC;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(obj.getClass() != this.getClass()) return false;

        Player other = (Player) obj;

        return this.memberID.equals(other.memberID);
    }

    @Override
    public String toString() {
        return "Member: " + memberID + " xp: " + level.getXp() + " Level: " + level.getLevel();
    }
}
