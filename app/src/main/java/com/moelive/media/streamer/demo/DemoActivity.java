package com.moelive.media.streamer.demo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends Activity {
    private static final String TAG = "DemoActivity";

    @BindView(com.moelive.live.demo.R.id.connectBT)
    protected Button mConnectButton;
    @BindView(com.moelive.live.demo.R.id.rtmpUrl)
    protected EditText mUrlEditText;

    protected DemoFragment mDemoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.moelive.live.demo.R.layout.demo_activity);
        ButterKnife.bind(this);

        Spinner demoTypeSpinner = (Spinner) findViewById(com.moelive.live.demo.R.id.demo_type);
        String[] items = new String[]{"基础Demo", "标准Demo", "悬浮窗Demo", "纯音频推流"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        demoTypeSpinner.setAdapter(adapter);
        demoTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        mDemoFragment = new BaseDemoFragment();
                        break;
                    case 1:
                        mDemoFragment = new StdDemoFragment();
                        break;
                    case 2:
                        mDemoFragment = new FloatDemoFragment();
                        break;
                    case 3:
                        mDemoFragment = new AudioDemoFragment();
                        break;
                    default:
                        break;
                }
                getFragmentManager().beginTransaction().replace(com.moelive.live.demo.R.id.fragment_layout,
                        mDemoFragment).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        mDemoFragment = new BaseDemoFragment();
        getFragmentManager().beginTransaction().replace(com.moelive.live.demo.R.id.fragment_layout,
                mDemoFragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(com.moelive.live.demo.R.id.connectBT)
    public void onClick() {
        doStart();
    }

    protected void doStart() {
        if (!TextUtils.isEmpty(mUrlEditText.getText())) {
            String url = mUrlEditText.getText().toString();
            if (url.startsWith("rtmp")) {
                mDemoFragment.start(url);
            }
        }
    }
}
