package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

public class Cordyceps extends Disease{
    private long moveTime;
    private long spreadTime;
    private long aliveTime;
    private long fightTime;
    public Cordyceps(int x,int y){
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
    //precondition: move a cordyceps
    //postcondition: cordyceps victim will only move towards other diseases
    public void moveCordyceps(int[][] gameGrid){
        int tempx = x;
        int tempy = y;
        int minI = 0;
        int minJ = 0;
        int minCount = 100;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 && j != 0) {//will not check 0 x and 0 y direction as that is not a direction
                    int f = findDirection(gameGrid, tempx, tempy, i, j, 3, 2);
                    if (f != -1) {//there is a valid place to move
                        if (f < minCount) {//the cordyceps will only move in the direction with the shortest distance that will reach another disease
                           minCount = f;//finds the minimum increments to move
                           minI = i;//saves the direction of the minimum increments
                           minJ = j;
                        }
                    }
                }
            }
        }
        if (minCount != 100) {//this means that a valid spot has been found
            gameGrid[tempx + minI][tempy + minJ] = 3;//cordyceps will move in the valid direction
            gameGrid[x][y] = 0;//the previous location is now empty as the cordyceps moved
            x = tempx + minI;//the cordyceps now has a new x y coordinate
            y = tempy + minJ;
        }
    }
    //precondition: gets the current x and y coordinates of the object, the direction the spaces will be checked, and how many increments to check
    //postcondition: returns the increments needed to find a spot that is empty and has a disease one space next to it that is not itself
    public int findDirection(int[][] grid, int currentX, int currentY, int moveX, int moveY, int diseaseType, int count) {
        if (currentX + count * moveX > 19 || currentY + count * moveY > 19 || currentX + count * moveX < 0 || currentY + count * moveY < 0) {//checks for out of bounds
            return -1;
        }
        else if (grid[currentX + count * moveX][currentY + count * moveY] != diseaseType &&
                grid[currentX + count * moveX][currentY + count * moveY] != 0 &&
                grid[(currentX + (count - 1) * moveX)][currentY + (count - 1) * moveY] == 0) {//checks for a spot two places away that is a disease and a spot one place away that is empty
            return count;
        }
        else {
            return findDirection(grid, currentX, currentY, moveX, moveY, diseaseType, count + 1);
        }
    }
    public List<Cordyceps> spreadCordyceps(int[][] gameGrid) {//will check all 8 directions around the cordyceps for it to spread
        List<Cordyceps> cordycepsList = new ArrayList<>();
        if (y + 1 < 20 && gameGrid[x][y + 1] == 0 && Math.random() > 0.5) {//cordyceps does not care about the diseases around it as it will always take over the other diseases
            gameGrid[x][y + 1] = 3;//creates cordyceps on grid
            cordycepsList.add(new Cordyceps(x, y + 1));//creates new cordyceps object and adds to list of created cordyceps
        }
        if (x + 1 < 20 && y + 1 < 20 && gameGrid[x + 1][y + 1] == 0 && Math.random() > 0.5) {
            gameGrid[x + 1][y + 1] = 3;
            cordycepsList.add(new Cordyceps(x + 1, y+1));
        }
        if (x + 1 < 20 && gameGrid[x + 1][y] == 0 && Math.random() > 0.5) {
            gameGrid[x + 1][y] = 3;
            cordycepsList.add(new Cordyceps(x + 1, y));
        }
        if (x + 1 < 20 && y - 1 > -1 && gameGrid[x + 1][y - 1] == 0 && Math.random() > 0.5) {
            gameGrid[x + 1][y - 1] = 3;
            cordycepsList.add(new Cordyceps(x + 1, y - 1));
        }
        if (y - 1 > -1 && gameGrid[x][y - 1] == 0 && Math.random() > 0.5) {
            gameGrid[x][y - 1] = 3;
            cordycepsList.add(new Cordyceps(x, y - 1));
        }
        if (x - 1 > -1 && y - 1 > -1 && gameGrid[x - 1][y - 1] == 0 && Math.random() > 0.5) {
            gameGrid[x - 1][y - 1] = 3;
            cordycepsList.add(new Cordyceps(x - 1, y - 1));
        }
        if (x - 1 > -1 && gameGrid[x - 1][y] == 0 && Math.random() > 0.5) {
            gameGrid[x - 1][y] = 3;
            cordycepsList.add(new Cordyceps(x - 1, y));
        }
        if (x - 1 > -1 && y + 1 < 20 && gameGrid[x - 1][y + 1] == 0 && Math.random() > 0.5) {
            gameGrid[x - 1][y + 1] = 3;
            cordycepsList.add(new Cordyceps(x - 1, y + 1));
        }
        return cordycepsList;
    }
}

