/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package math_quiz_game;

/**
 *
 * @author HP
 */
public class leaderBoard {
    private int rank;
    private String name;
    private int coins;
    private long time;

    public leaderBoard(int rank, String name, int coins, long time) {
        this.rank = rank;
        this.name = name;
        this.coins = coins;
        this.time = time;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    
    
    
}
