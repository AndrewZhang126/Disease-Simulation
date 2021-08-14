package sample;

import java.util.ArrayList;
import java.util.List;

public class Flu extends Disease{
    private long moveTime;
    private long spreadTime;
    private long aliveTime;
    private long fightTime;
    public Flu(int x,int y){
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
    public List<Flu> spreadFlu(int[][] gameGrid, double prob) {//will check all 8 directions around flu for it to spread
        List<Flu> fluList = new ArrayList<>();
        if (y + 1 < 20 && gameGrid[x][y + 1] == 0 && Math.random() > prob) {//flu can only spread if the space is empty
            gameGrid[x][y + 1] = 1;//creates new flu on grid
            fluList.add(new Flu(x, y + 1));//creates new flu object and adds to list of new flu
        }
        if (x + 1 < 20 && y + 1 < 20 && gameGrid[x + 1][y + 1] == 0 && Math.random() > prob) {
            gameGrid[x + 1][y + 1] = 1;
            fluList.add(new Flu(x + 1, y+1));
        }
        if (x + 1 < 20 && gameGrid[x + 1][y] == 0 && Math.random() > prob) {
            gameGrid[x + 1][y] = 1;
            fluList.add(new Flu(x + 1, y));
        }
        if (x + 1 < 20 && y - 1 > -1 && gameGrid[x + 1][y - 1] == 0 && Math.random() > prob) {
            gameGrid[x + 1][y - 1] = 1;
            fluList.add(new Flu(x + 1, y - 1));
        }
        if (y - 1 > -1 && gameGrid[x][y - 1] == 0 && Math.random() > prob) {
            gameGrid[x][y - 1] = 1;
            fluList.add(new Flu(x, y - 1));
        }
        if (x - 1 > -1 && y - 1 > -1 && gameGrid[x - 1][y - 1] == 0 && Math.random() > prob) {
            gameGrid[x - 1][y - 1] = 1;
            fluList.add(new Flu(x - 1, y - 1));
        }
        if (x - 1 > -1 && gameGrid[x - 1][y] == 0 && Math.random() > prob) {
            gameGrid[x - 1][y] = 1;
            fluList.add(new Flu(x - 1, y));
        }
        if (x - 1 > -1 && y + 1 < 20 && gameGrid[x - 1][y + 1] == 0 && Math.random() > prob) {
            gameGrid[x - 1][y + 1] = 1;
            fluList.add(new Flu(x - 1, y + 1));
        }
        return fluList;
    }
}
