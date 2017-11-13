package com.example.juhwan.pokeflight;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GameOver extends AppCompatActivity  {

    String name;
    int score;

//    SQLiteDatabase db;
    private ListView myListView;
    DBHelper mydb;
    ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_over);

        Intent fromIntent = getIntent();

        score = fromIntent.getIntExtra("score",0);

        name = fromIntent.getStringExtra("name");

        mydb = new DBHelper(this);

        ArrayList array_List = mydb.getAllScores();

        mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array_List);

        myListView = (ListView)findViewById(R.id.listView1);

        myListView.setAdapter(mAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int id = arg2 + 1;
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id",id);
                Intent intent = new Intent(getApplicationContext(),DisplayScore.class);
                intent.putExtras(dataBundle);
                intent.putExtra("score",score);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
        mAdapter.addAll(mydb.getAllScores());
        mAdapter.notifyDataSetChanged();
    }

    public void OnClick(View target){
        Bundle bundle = new Bundle();
        bundle.putInt("id",0);
        Intent intent = new Intent(getApplicationContext(),DisplayScore.class);
        intent.putExtras(bundle);
        intent.putExtra("score",score);
        intent.putExtra("name",name);
        startActivity(intent);
    }


}
