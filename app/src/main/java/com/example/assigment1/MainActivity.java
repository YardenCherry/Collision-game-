package com.example.assigment1;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private ImageButton main_IMGBTN_right;
    private ImageButton main_IMGBTN_left;
    private ShapeableImageView[][] shapeableImageViewMatrix;
    final Handler handler = new Handler();
    private static final long DELAY = 1000;
    private int numRows = 7; // Number of rows in the matrix
    private int numCols = 3; // Number of columns in the matrix
    private ShapeableImageView[] main_IMG_hearts;
    private GameManager gameManager;
    private ShapeableImageView main_IMG_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        shapeableImageViewMatrix = new ShapeableImageView[numRows][numCols];
        findViews();
        gameManager = new GameManager(main_IMG_hearts.length);

        main_IMGBTN_left.setOnClickListener(view->playerMoveInUi(1));
        main_IMGBTN_right.setOnClickListener(view->playerMoveInUi(0));
        startGame();

    }
    private void startGame(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveObstaclesInUi();
                if(collision()){
                    vibrate();
                    toast("Oops Collision");
                }
                handler.postDelayed(this, DELAY);

            }
        }, DELAY);
    }
    private void toastAndVibrate(String text) {
        vibrate();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this,text, Toast.LENGTH_LONG).show();
    }

    private boolean collision(){
        if(gameManager.hasCollision()){
            main_IMG_hearts[3-gameManager.getNumOfCollision()].setVisibility(View.INVISIBLE);
            gameManager.setLife(gameManager.getLife()-1);
            if(gameManager.getLife()==0){
                gameManager.setLife(3);
                for(int i=0 ; i<3 ; i++)
                    main_IMG_hearts[i].setVisibility(View.VISIBLE);
            }

            return true;
        }

        return false;
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }



    private void moveObstaclesInUi(){
       boolean[][] booleanArray= gameManager.moveobstacles();
       for (int i=numRows-2; i>=0 ; i--){
           for (int j= 0 ; j<numCols ; j++){
               if(!booleanArray[i][j])
                   shapeableImageViewMatrix[i][j].setVisibility(View.INVISIBLE);
               else
                   shapeableImageViewMatrix[i][j].setVisibility(View.VISIBLE);
           }
       }
    }

    private void playerMoveInUi(int sideClicked){
        boolean[][] booleanArray= gameManager.playerMovement(sideClicked);
        for (int i=0; i<numCols; i++){
            if (booleanArray[numRows - 1][i])
                shapeableImageViewMatrix[numRows-1][i].setVisibility(View.VISIBLE);
            else
                shapeableImageViewMatrix[numRows-1][i].setVisibility(View.INVISIBLE);
        }
    }



    private void findViews() {
        main_IMGBTN_right = findViewById(R.id.main_IMGBTN_right);
        main_IMGBTN_left = findViewById(R.id.main_IMGBTN_left);
        // Initialize the 2D array with the specified number of rows and columns
        shapeableImageViewMatrix[0][0] = findViewById(R.id.main_IMG_image00);
        shapeableImageViewMatrix[0][1] = findViewById(R.id.main_IMG_image01);
        shapeableImageViewMatrix[0][2] = findViewById(R.id.main_IMG_image02);
        shapeableImageViewMatrix[1][0] = findViewById(R.id.main_IMG_image10);
        shapeableImageViewMatrix[1][1] = findViewById(R.id.main_IMG_image11);
        shapeableImageViewMatrix[1][2] = findViewById(R.id.main_IMG_image12);
        shapeableImageViewMatrix[2][0] = findViewById(R.id.main_IMG_image20);
        shapeableImageViewMatrix[2][1] = findViewById(R.id.main_IMG_image21);
        shapeableImageViewMatrix[2][2] = findViewById(R.id.main_IMG_image22);
        shapeableImageViewMatrix[3][0] = findViewById(R.id.main_IMG_image30);
        shapeableImageViewMatrix[3][1] = findViewById(R.id.main_IMG_image31);
        shapeableImageViewMatrix[3][2] = findViewById(R.id.main_IMG_image32);
        shapeableImageViewMatrix[4][0] = findViewById(R.id.main_IMG_image40);
        shapeableImageViewMatrix[4][1] = findViewById(R.id.main_IMG_image41);
        shapeableImageViewMatrix[4][2] = findViewById(R.id.main_IMG_image42);
        shapeableImageViewMatrix[5][0] = findViewById(R.id.main_IMG_image50);
        shapeableImageViewMatrix[5][1] = findViewById(R.id.main_IMG_image51);
        shapeableImageViewMatrix[5][2] = findViewById(R.id.main_IMG_image52);
        shapeableImageViewMatrix[6][0] = findViewById(R.id.main_IMG_image60);
        shapeableImageViewMatrix[6][1] = findViewById(R.id.main_IMG_image61);
        shapeableImageViewMatrix[6][2] = findViewById(R.id.main_IMG_image62);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
    }
}


