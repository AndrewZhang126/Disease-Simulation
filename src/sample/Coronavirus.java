package sample;

import java.util.ArrayList;
import java.util.List;

public class Coronavirus extends Disease {
    private long moveTime;
    private long spreadTime;
    private long aliveTime;
    private long fightTime;
    public Coronavirus(int x,int y) {
        super(x, y);
        moveTime = System.nanoTime();
        spreadTime = System.nanoTime();
        aliveTime = System.nanoTime();
        fightTime = System.nanoTime();
    }
    public long getMoveTime(){
        return moveTime;
    }
    public long getSpreadTime() {
        return spreadTime;
    }
    public long getAliveTime(){
        return aliveTime;
    }
    public long getFightTime() {
        return fightTime;
    }
    public void resetMoveTime(){
        moveTime = System.nanoTime();
    }
    public void resetSpreadTime() {
        spreadTime = System.nanoTime();
    }
    public void resetFightTime() {
        fightTime = System.nanoTime();
    }
    public List<Coronavirus> spreadCoronavirus(int[][] gameGrid, double prob) {//checks in all 8 directions for coronavirus to spread
        List<Coronavirus> coronavirusList = new ArrayList<>();
        if (y + 1 < 20 && gameGrid[x][y + 1] == 0 && Math.random() > prob) {//coronavirus can only spread when space is empty
            gameGrid[x][y + 1] = 4;//creates new coronavirus on grid
            coronavirusList.add(new Coronavirus(x, y + 1));//creates new coronavirus object and adds to list of new coronavirus
        }
        if (x + 1 < 20 && y + 1 < 20 && gameGrid[x + 1][y + 1] == 0 && Math.random() > prob) {
            gameGrid[x + 1][y + 1] = 4;
            coronavirusList.add(new Coronavirus(x + 1, y+1));
        }
        if (x + 1 < 20 && gameGrid[x + 1][y] == 0 && Math.random() > prob) {
            gameGrid[x + 1][y] = 4;
            coronavirusList.add(new Coronavirus(x + 1, y));
        }
        if (x + 1 < 20 && y - 1 > -1 && gameGrid[x + 1][y - 1] == 0 && Math.random() > prob) {
            gameGrid[x + 1][y - 1] = 4;
            coronavirusList.add(new Coronavirus(x + 1, y - 1));
        }
        if (y - 1 > -1 && gameGrid[x][y - 1] == 0 && Math.random() > prob) {
            gameGrid[x][y - 1] = 4;
            coronavirusList.add(new Coronavirus(x, y - 1));
        }
        if (x - 1 > -1 && y - 1 > -1 && gameGrid[x - 1][y - 1] == 0 && Math.random() > prob) {
            gameGrid[x - 1][y - 1] = 4;
            coronavirusList.add(new Coronavirus(x - 1, y - 1));
        }
        if (x - 1 > -1 && gameGrid[x - 1][y] == 0 && Math.random() > prob) {
            gameGrid[x - 1][y] = 4;
            coronavirusList.add(new Coronavirus(x - 1, y));
        }
        if (x - 1 > -1 && y + 1 < 20 && gameGrid[x - 1][y + 1] == 0 && Math.random() > prob) {
            gameGrid[x - 1][y + 1] = 4;
            coronavirusList.add(new Coronavirus(x - 1, y + 1));
        }
        return coronavirusList;
    }
}
