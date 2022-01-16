package bot.player.hero;

import bot.player.hero.attributes.Damage;
import bot.player.hero.attributes.Defence;
import bot.player.hero.attributes.Points;
import bot.player.hero.attributes.Statistics;

public class Hero {
    private Statistics statistics;
    private Points points;
    private Damage damage;
    private Defence defence;

    public Hero(Statistics statistics) {
        this.statistics = statistics;
        this.points = new Points(statistics);
        this.damage = new Damage(statistics);
        this.defence = new Defence(statistics);
    }

    public Statistics getStatistics(){
        return statistics;
    }

    public Points getPoints(){
        return points;
    }

    public Damage getDamage(){
        return damage;
    }

    public Defence getDefence(){
        return defence;
    }

    public void takeHit(double hitDamage){
        points.subtractHp(hitDamage);
    }
}
