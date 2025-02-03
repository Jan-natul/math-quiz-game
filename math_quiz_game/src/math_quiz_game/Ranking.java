package math_quiz_game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Ranking {
    private SimpleIntegerProperty rank;
    private SimpleStringProperty name;
    private SimpleIntegerProperty coins;
    private SimpleLongProperty time;

    public Ranking(int rank, String name, int coins, long time) {
        this.rank = new SimpleIntegerProperty(rank);
        this.name = new SimpleStringProperty(name);
        this.coins = new SimpleIntegerProperty(coins);
        this.time = new SimpleLongProperty(time);
    }

    public int getRank() {
        return rank.get();
    }

    public String getName() {
        return name.get();
    }

    public int getCoins() {
        return coins.get();
    }

    public long getTime() {
        return time.get();
    }
}
