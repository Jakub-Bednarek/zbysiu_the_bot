package bot.player.hero.attributes;

import org.jetbrains.annotations.NotNull;

public class Points {
    private static final double INTELLIGENCE_MULTIPLIER = 2.4;
    private static final double STRENGTH_MULTIPLIER = 5.5;
    private static final double DEXTERITY_MULTIPLIER = 2.5;
    private static final double STAMINA_PER_ROUND = 2.0;

    private double health;
    private double mana;
    private double stamina;

    public Points(@NotNull Statistics statistics) {
        this.health = statistics.getStrength() * STRENGTH_MULTIPLIER;
        this.mana = statistics.getIntelligence() * INTELLIGENCE_MULTIPLIER;
        this.stamina = statistics.getDexterity() * DEXTERITY_MULTIPLIER;
    }

    public double getHealth() {
        return health;
    }

    public double getMana() {
        return mana;
    }

    public double getStamina() {
        return stamina;
    }

    public void subtractHp(double amount){
        health -= amount;
    }

    public void subtractMana(double amount){
        mana -= amount;
    }

    public void subtractAttackStamina(){
        stamina -= STAMINA_PER_ROUND;
    }
}
