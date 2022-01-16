package bot.player.hero.attributes;

public class Defence {
    private static final double DEXTERITY_MULTIPLIER = 0.2;
    private static final double STRENGTH_MULTIPLIER = 0.4;

    private double armor;
    private double dodge;

    public Defence(Statistics statistics) {
        this.armor = statistics.getStrength() * STRENGTH_MULTIPLIER;
        this.dodge = statistics.getDexterity() * DEXTERITY_MULTIPLIER;
    }

    public double getArmor() {
        return armor;
    }

    public double getDodge() {
        return dodge;
    }
}
