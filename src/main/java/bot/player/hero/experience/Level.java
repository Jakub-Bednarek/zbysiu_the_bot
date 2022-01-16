package bot.player.hero.experience;

public class Level {
    private static final int BASE = 10;
    private int level;
    private int xp;

    public Level(int level, int xp){
        this.level = level;
        this.xp = xp;
    }

    public static int getRequiredXp(int levelToCheck){
        return (int)(BASE + (BASE * levelToCheck * 7/6 * (0.03 * levelToCheck)));
    }

    public int getLevel(){
        return level;
    }

    public int getXp(){
        return xp;
    }

    public boolean addXp(int xpToAdd){
        xp += xpToAdd;

        return checkForLevelUp();
    }

    public boolean checkForLevelUp(){
        int xpNeeded = getRequiredXp(level + 1);

        if(xp >= xpNeeded){
            level += 1;
            xp -= xpNeeded;

            return true;
        }

        return false;
    }
}
