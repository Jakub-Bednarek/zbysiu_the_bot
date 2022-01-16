package bot.player.hero.attributes;

public class Statistics {
    private int intelligence;
    private int strength;
    private int dexterity;

    public Statistics(int intelligence, int strength, int dexterity) {
        this.intelligence = intelligence;
        this.strength = strength;
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void addIntelligence(int intelligenceToAdd) {
        intelligence += intelligenceToAdd;
    }

    public void addStrength(int strengthToAdd) {
        strength += strengthToAdd;
    }

    public void addDexterity(int dexterityToAdd) {
        dexterity += dexterityToAdd;
    }
}
