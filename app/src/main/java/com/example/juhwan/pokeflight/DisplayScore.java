package com.example.juhwan.pokeflight;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayScore extends AppCompatActivity {
   private DBHelper mydb;
    EditText editTextName,editTextScore;

    int id = 0;

    String name;

    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_score);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextScore = (EditText)findViewById(R.id.editTextScore);


        Intent fromIntent = getIntent();

        score = fromIntent.getIntExtra("score",0);

        name = fromIntent.getStringExtra("name");

        String result = String.valueOf(score);


        editTextName.setText(name);
        editTextScore.setText(result);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int Value = extras.getInt("id");
            if(Value > 0){
                Cursor rs = mydb.getData(Value);
                id = Value;
                rs.moveToFirst();

                if(!rs.isClosed()){
                    rs.close();
                }

            }
        }
    }

    public void insert(View view){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int Value = extras.getInt("id");
            if(Value > 0){
                if(mydb.updateScores(id,editTextName.getText().toString(),editTextScore.getText().toString())){
                    Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(getApplicationContext(),GameOver.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"수정되지않았습니다.",Toast.LENGTH_SHORT).show();
                }
            }else{
                if(mydb.insertScore(editTextName.getText().toString(), editTextScore.getText().toString())){
                    Toast.makeText(getApplicationContext(),"추가 되었습니다.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"추가 되지 않았습니다.",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }

    public void delete(View view){
        Bundle extras = getIntent().getExtras();

        name = editTextName.getText().toString();

        if(extras != null){
            int Value = extras.getInt("id");
            if(Value > 0){
                mydb.deleteScores(name);

//                mydb.deleteScores(id);
                Toast.makeText(getApplicationContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"삭제되지않았습니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void deleteAll(View view){
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int Value = extras.getInt("id");
            if(Value > 0){
                mydb.deleteAll(id);
                Toast.makeText(getApplicationContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"삭제되지않았습니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void edit(View view){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int value = extras.getInt("id");
            if(value>0){
                if(mydb.updateScores(id,editTextName.getText().toString(),editTextScore.getText().toString())){
                    Toast.makeText(getApplicationContext(),"수정되었습니다",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"수정되지않았습니다",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
