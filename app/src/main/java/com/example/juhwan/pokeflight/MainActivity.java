package com.example.juhwan.pokeflight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static GameView gameView;
    ImageButton imageButton;
    String name;



    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(new GameView(this,null));

        setContentView(R.layout.activity_main);

        FrameLayout frame = (FrameLayout)findViewById(R.id.surfaceframe);

        gameView = new GameView(this);

        frame.addView(gameView);

        imageButton = (ImageButton)findViewById(R.id.imagebutton);

        Intent fromintent = getIntent();

        name = fromintent.getStringExtra("name");

//        Intent intent = new Intent(this,GameView.class);
//
//        intent.putExtra("name",name);
//
//        startActivity(intent);

        imageButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imagebutton:

                // gameView.PauseGame();

//                gameView.control = true;
                Intent intent = new Intent(this, ChacterChange.class);
                //   Intent intent = new Intent(mContext, ChacterChange.class);
                startActivityForResult(intent,100);
//                startActivity(intent);
//                gameView.kill();
                gameView.StopGame();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 100){
            if(resultCode == RESULT_OK && data != null){

                String select = data.getStringExtra("resultSetting");

                if(select.equals("pika")){
                    gameView.SpaceShip.setCharImg(R.drawable.pikachu0);

                }
                else if(select.equals("pyri")){
                    gameView.SpaceShip.setCharImg(R.drawable.pyri);
                }
                else if(select.equals("ggobuk")){
                    gameView.SpaceShip.setCharImg(R.drawable.squirtle);
                }
                else if(select.equals("leesang")){
                    gameView.SpaceShip.setCharImg(R.drawable.leesang);
                }
                else{
                    gameView.SpaceShip.setCharImg(R.drawable.mystic);
                }



                //gameView.SpaceShip.setCharImg(R.drawable.ggobugi);

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quit:     // QuitGame
                GameView.StopGame();
                finish();
                break;
            case R.id.pause:      // PauseGame
                GameView.PauseGame();
                break;
            case R.id.resume:      // Resume
                GameView.ResumeGame();
                break;
            case R.id.restart:
                gameView.RestartGame();
            case R.id.music:      // Music On/off
                break;
            case R.id.vibrate:       // Sound On/Off
                break;
        }
        return true;
    }


}