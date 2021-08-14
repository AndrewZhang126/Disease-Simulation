package sample;

import java.util.ArrayList;
import java.util.List;

public class Disease {
    int x;
    int y;
    public Disease(int x,int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    //precondition: gets the x and y coordinate of the disease
    //postcondition: moves the disease one space in a random direction
    public void moveDisease(int[][] gameGrid, int d){
        int tempx = x;
        int tempy = y;
        double random = Math.random();
        if(random < 0.125 && tempy + 1 < 20){//checks all 8 directions, uses Math.random so that the disease can only move in one direction at a time
            tempy++;
        }
        else if (random < 0.25 && tempx + 1 < 20 && tempy + 1 < 20){
            tempx++;
            tempy++;
        }
        else if(random < 0.375 && tempx + 1 < 20){
            tempx++;
        }
        else if (random < 0.5 && tempx + 1 < 20 && tempy - 1 > -1){
            tempx++;
            tempy--;
        }
        else if (random < 0.625 && tempy - 1 > -1){
            tempy--;
        }
        else if (random < 0.75 && tempx - 1 > -1 && tempy - 1 > -1){
            tempx--;
            tempy--;
        }
        else if (random < 0.875 && tempx - 1 > -1){
            tempx--;
        }
        else if (random < 1 && tempx - 1 > - 1 && tempy + 1 < 20){
            tempx--;
            tempy++;
        }
        else {
            tempx += 0;//disease will not move if none of above conditions are met
            tempy += 0;
        }
        if (gameGrid[tempx][tempy] == 0){//if the randomly selected spot is empty, the disease will move there
            gameGrid[tempx][tempy] = d;//moves the disease on the grid
            gameGrid[x][y] = 0;//previous location of disease is now empty
            x=tempx;//disease has new location coordinates
            y=tempy;
        }
    }
    //precondition: gets the type of disease that will fight
    //postcondition: returns the disease that dies in the fight
    public int fightDisease(int[][] gameGrid, int d) {
        double random = Math.random();//ensures the disease only fights in one direction
        if (y + 1 < 20 && gameGrid[x][y + 1] != d && gameGrid[x][y + 1] != 0 && random < 0.125) {//checks if the space next to disease is another disease
            if (Math.random() > 0.5) {
                diseaseDead(gameGrid, x, y + 1);//this disease has killed the other disease
                return gameGrid[x][y + 1];//returns the disease that died
            }
            else {
                diseaseDead(gameGrid, x, y);//this disease got killed
                return d;//returns this disease
            }
        }
        else if (x + 1 < 20 && y + 1 < 20 && gameGrid[x + 1][y + 1] != d && gameGrid[x + 1][y + 1] != 0 && random < 0.25) {
            if (Math.random() > 0.5) {
                diseaseDead(gameGrid, x + 1, y + 1);
                return gameGrid[x + 1][y + 1];
            }
            else {
                diseaseDead(gameGrid, x, y);
                return d;
            }
        }
        else if (x + 1 < 20 && gameGrid[x + 1][y] != d && gameGrid[x + 1][y] != 0 && random < 0.375) {
            if (Math.random() > 0.5) {
                diseaseDead(gameGrid, x + 1, y);
                return gameGrid[x + 1][y];
            }
            else {
                diseaseDead(gameGrid, x, y);
                return d;
            }
        }
        else if (x + 1 < 20 && y - 1 > -1 && gameGrid[x + 1][y - 1] != d && gameGrid[x + 1][y - 1] != 0 && random < 0.5) {
            if (Math.random() > 0.5) {
                diseaseDead(gameGrid, x + 1, y - 1);
                return gameGrid[x + 1][y - 1];
            }
            else {
                diseaseDead(gameGrid, x, y);
                return d;
            }
        }
        else if (y - 1 > -1 && gameGrid[x][y - 1] != d && gameGrid[x][y - 1] != 0 && random < 0.625) {
            if (Math.random() > 0.5) {
                diseaseDead(gameGrid, x, y - 1);
                return gameGrid[x][y - 1];
            }
            else {
                diseaseDead(gameGrid, x , y);
                return d;
            }
        }
        else if (x - 1 > -1 && y - 1 > -1 && gameGrid[x - 1][y - 1] != d && gameGrid[x - 1][y - 1] != 0 && random < 0.75) {
            if (Math.random() > 0.5) {
                diseaseDead(gameGrid, x - 1, y - 1);
                return gameGrid[x - 1][y - 1];
            }
            else {
                diseaseDead(gameGrid, x, y);
                return d;
            }
        }
        else if (x - 1 > -1 && gameGrid[x - 1][y] != d && gameGrid[x - 1][y] != 0 && random < 0.875) {
            if (Math.random() > 0.5) {
                diseaseDead(gameGrid, x - 1, y);
                return gameGrid[x - 1][y];
            }
            else {
                diseaseDead(gameGrid, x, y);
                return d;
            }
        }
        else if (x - 1 > -1 && y + 1 < 20 && gameGrid[x - 1][y + 1] != d && gameGrid[x - 1][y + 1] != 0 && random < 1.0) {
            if (Math.random() > 0.5) {
                diseaseDead(gameGrid, x - 1, y + 1);
                return gameGrid[x - 1][y + 1];
            }
            else {
                diseaseDead(gameGrid, x, y);
                return d;
            }
        }
        else {
            return 0;
        }
    }
    //precondition: gets the x and y coordinates of a disease
    //postcondition: removes the disease from the grid
    public void diseaseDead(int[][] gameGrid, int x, int y) {
        gameGrid[x][y] = 0;//sets the location to be 0
    }
}
