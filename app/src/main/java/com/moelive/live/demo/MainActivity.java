package com.moelive.live.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.moelive.live.demo.player.activity.PlayerActivity;
import com.moelive.media.streamer.demo.DemoActivity;


public class MainActivity extends AppCompatActivity {

    private Button btn_stream;
    private Button btn_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
////        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        btn_player = (Button)findViewById(R.id.btn_player);
        btn_stream = (Button)findViewById(R.id.btn_stream);

        btn_stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DemoActivity.class);
                startActivity(intent);
            }
        });
        btn_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PlayerActivity.class);
                startActivity(intent);
            }
        });
    }
}
