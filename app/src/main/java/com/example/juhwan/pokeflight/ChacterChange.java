package com.example.juhwan.pokeflight;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.SurfaceHolder;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.example.juhwan.pokeflight.adapter.JobAdapter;
        import com.example.juhwan.pokeflight.model.Job;

        import java.util.ArrayList;
        import java.util.List;

public class ChacterChange extends AppCompatActivity implements AdapterView.OnItemClickListener,Runnable {

    public static Context mContext;
    private SurfaceHolder mHolder;

    Handler mHandler;
    GameView gameView;
    List<Job> list;
    JobAdapter adapter;
    ListView listView;
    public static final int pikachu = 1000;
    public static final int pyri = 1001;
    public static final int ggobugi = 1002;
    public static final int leesang = 1003;
    public static final int restart = 1004;

    //String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chacter_change);

        list = new ArrayList<Job>();
        adapter = new JobAdapter(this,R.layout.list_item,list);
        listView = (ListView)findViewById(R.id.listView);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        addData();

    }



    public void connectHandler(Handler mHandler){
        this.mHandler = mHandler;
    }

    public void addData(){
        adapter.add(new Job("피카츄","전기를 특기로 사용하는 포켓몬",R.drawable.pikachu0));
        adapter.add(new Job("파이리","불을 특기로 사용하는 포켓몬",R.drawable.pyri));
        adapter.add(new Job("꼬부기","물을 특기로 사용하는 포켓몬",R.drawable.ggobugi));
        adapter.add(new Job("이상해씨","풀을 특기로 사용하는 포켓몬",R.drawable.leesang));
    }

    public void selectPikaMessage() {

        Intent intent = new Intent();

        intent.putExtra("resultSetting","pika");
        this.setResult(RESULT_OK,intent);

        finish();
    }

    public void selectPyMessage() {

        Intent intent = new Intent();
        intent.putExtra("resultSetting","pyri");
        this.setResult(RESULT_OK,intent);
        finish();

    }

    public void selectGgobukMessage() {

        Intent intent = new Intent();
        intent.putExtra("resultSetting","ggobuk");
        this.setResult(RESULT_OK,intent);
        finish();
    }

    public void selectLeesangMessage() {

        Intent intent = new Intent();
        intent.putExtra("resultSetting","leesang");
        this.setResult(RESULT_OK,intent);
        finish();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(this,"게임으로 돌아갑니다.",Toast.LENGTH_SHORT).show();

        finish();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.listView:
                if(position ==0) {
                    Job item = adapter.getItem(position);
                    Toast.makeText(this, item.getTitle() + " 너로 정했다", Toast.LENGTH_SHORT).show();
                    selectPikaMessage();
                    onBackPressed();

                }
                else if(position == 1){// 파이리
                    Job item = adapter.getItem(position);
                    Toast.makeText(this, item.getTitle() + " 너로 정했다", Toast.LENGTH_SHORT).show();

                    selectPyMessage();
//                    onBackPressed();
                    finish();
                }
                else if(position == 2){//꼬부기
                    Job item = adapter.getItem(position);
                    Toast.makeText(this, item.getTitle() + " 너로 정했다", Toast.LENGTH_SHORT).show();

                    selectGgobukMessage();
                    onBackPressed();
                }
                else if(position == 3){//이상해씨
                    Job item = adapter.getItem(position);
                    Toast.makeText(this, item.getTitle() + " 너로 정했다", Toast.LENGTH_SHORT).show();

                    selectLeesangMessage();
                    onBackPressed();
                }
                break;
        }
    }

    @Override
    public void run() {
        while (true){

        }
    }
}