package bot.player.consumables;

public class Coins implements Consumable{
    private double amount;

    public Coins(double amount) {
        this.amount = amount;
    }

    @Override
    public void add(double amountToAdd) {
        if(amountToAdd > 0){
            amount += amountToAdd;
        }
    }

    @Override
    public void subtract(double amountToSubtract) {
        if(amountToSubtract > 0){
            amount -= amountToSubtract;
        }
    }

    @Override
    public double get() {
        return 0;
    }
}
