package bot.player.consumables;

public interface Consumable {
    void add(double amountToAdd);
    void subtract(double amountToSubtract);
    double get();
}
