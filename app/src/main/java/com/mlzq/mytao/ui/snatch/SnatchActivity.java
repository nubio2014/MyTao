package com.mlzq.mytao.ui.snatch;

import android.os.Bundle;

import com.mlzq.mytao.R;
import com.mlzq.nubiolib.app.BaseActivity;

/**
 * 抢购
 */
public class SnatchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_snatch);
        setTitleName("淘抢购");

    }
}
