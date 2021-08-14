package sample;

import java.util.ArrayList;
import java.util.List;

public class Hiv extends Disease{
    private long moveTime;
    private long spreadTime;
    private long aliveTime;
    private long fightTime;
    public Hiv(int x,int y){
        super(x, y);
        moveTime = System.nanoTime();
        spreadTime = System.nanoTime();
        aliveTime = System.nanoTime();
        fightTime = System.nanoTime();
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
    public List<Hiv> spreadHiv(int[][] gameGrid) {//checks in all 8 directions for hiv to spread
        List<Hiv> hivList = new ArrayList<>();
        if (y + 1 < 20 && gameGrid[x][y + 1] == 0 && Math.random() > 0.9) {//hiv can only spread if empty space, very low chance of spreading
            gameGrid[x][y + 1] = 2;//creates new hiv on grid
            hivList.add(new Hiv(x, y + 1));//creates hiv object and adds to list of new hiv
        }
        else if (x + 1 < 20 && y + 1 < 20 && gameGrid[x + 1][y + 1] == 0 && Math.random() > 0.9) {
            gameGrid[x + 1][y + 1] = 2;
            hivList.add(new Hiv(x + 1, y+1));
        }
        else if (x + 1 < 20 && gameGrid[x + 1][y] == 0 && Math.random() > 0.9) {
            gameGrid[x + 1][y] = 2;
            hivList.add(new Hiv(x + 1, y));
        }
        else if (x + 1 < 20 && y - 1 > -1 && gameGrid[x + 1][y - 1] == 0 && Math.random() > 0.9) {
            gameGrid[x + 1][y - 1] = 2;
            hivList.add(new Hiv(x + 1, y - 1));
        }
        else if (y - 1 > -1 && gameGrid[x][y - 1] == 0 && Math.random() > 0.9) {
            gameGrid[x][y - 1] = 2;
            hivList.add(new Hiv(x, y - 1));
        }
        else if (x - 1 > -1 && y - 1 > -1 && gameGrid[x - 1][y - 1] == 0 && Math.random() > 0.9) {
            gameGrid[x - 1][y - 1] = 2;
            hivList.add(new Hiv(x - 1, y - 1));
        }
        else if (x - 1 > -1 && gameGrid[x - 1][y] == 0 && Math.random() > 0.9) {
            gameGrid[x - 1][y] = 2;
            hivList.add(new Hiv(x - 1, y));
        }
        else if (x - 1 > -1 && y + 1 < 20 && gameGrid[x - 1][y + 1] == 0 && Math.random() > 0.9) {
            gameGrid[x - 1][y + 1] = 2;
            hivList.add(new Hiv(x - 1, y + 1));
        }
        return hivList;
    }
}
