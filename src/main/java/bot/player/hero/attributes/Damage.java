package bot.player.hero.attributes;

public class Damage {
    private static final double STRENGTH_MULTIPLIER = 1.4;
    private static final double INTELLIGENCE_MULTIPLIER = 1.2;

    private double attackDamage;
    private double abilityPower;

    public Damage(Statistics statistics){
        this.attackDamage = statistics.getStrength() * STRENGTH_MULTIPLIER;
        this.abilityPower = statistics.getIntelligence() * INTELLIGENCE_MULTIPLIER;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public double getAbilityPower() {
        return abilityPower;
    }
}
