package com.example.assigment1;

import android.graphics.HardwareRenderer;
import android.view.View;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class GameManager {
    private int life;

    private boolean[][] booleanObstacles;
    private int numRows = 7; // Number of rows in the matrix
    private int numCols = 3; // Number of columns in the matrix
    private int numOfCollision = 0;

    public GameManager(int life) {
        this.life = life;
        booleanObstacles= new boolean[numRows][numCols];
        booleanObstacles= updateArrayAtFirst();
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life){
        this.life=life;
    }

    public boolean[][] updateArrayAtFirst() {
        for (int i=0; i<numRows-1; i++){
            for (int j=0 ; j<numCols ; j++){
                booleanObstacles[i][j]=false;
            }
        }
        booleanObstacles[6][1]=true;
        return booleanObstacles;
    }
    public boolean[][] moveobstacles(){
        for (int i = numRows-2; i > 0; i--) {
            for (int j = 0; j < numCols; j++) {
                booleanObstacles[i][j] = booleanObstacles[i-1][j];
            }
        }
        int randomNum= (int) (Math.random()*3);
        for (int j = 0; j < numCols; j++) {
            if (j == randomNum)
                booleanObstacles[0][j]=true;
            else
                booleanObstacles[0][j]=false;
        }
        return booleanObstacles;
    }

    //0 to right , 1 to left
    public boolean[][] playerMovement(int sideMove){
        int playerLocation = checkLocate();
        if (sideMove==0){
            if (playerLocation==2)
                return booleanObstacles;
            else{
                booleanObstacles[6][playerLocation]=false;
                booleanObstacles[6][playerLocation+1]=true;
            }
        }
        if (sideMove==1){
            if (playerLocation==0)
                return booleanObstacles;
            else{
                booleanObstacles[6][playerLocation]=false;
                booleanObstacles[6][playerLocation-1]=true;
            }
        }
        return booleanObstacles;
    }

    public boolean hasCollision(){
        for (int i=0 ; i<numCols ; i++){
            if (numOfCollision==3)
                numOfCollision=0;
            if(booleanObstacles[numRows-2][i] && booleanObstacles[numRows-1][i]) {
                numOfCollision++;
                return true;
            }
        }
        return false;
    }

    //0 to left, 1 to center and 2 to right  .'
    public int checkLocate(){
        if(booleanObstacles[6][0]==true)
            return 0;
        if(booleanObstacles[6][1]==true)
            return 1;
        return 2;
    }

    public int getNumOfCollision() {
        return numOfCollision;
    }

}
