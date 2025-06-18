package com.homework.shopapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class StoreDetailActivity extends AppCompatActivity {

    public static final String EXTRA_STORE_ID = "store_id";

    private TextView tvStoreName, tvStoreIntro, tvStoreRating, tvStoreQual;
    private ImageView ivStoreImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        tvStoreName = findViewById(R.id.tvStoreName);
        tvStoreIntro = findViewById(R.id.tvStoreIntro);
        tvStoreRating = findViewById(R.id.tvStoreRating);
        tvStoreQual = findViewById(R.id.tvStoreQualifications);
        ivStoreImage = findViewById(R.id.ivStoreImage);

        int storeId = getIntent().getIntExtra(EXTRA_STORE_ID, 1);

        if (storeId == 1) {
            tvStoreName.setText("科技装备商店");
            tvStoreIntro.setText("专为技术爱好者打造的装备补给站！聚焦前沿科技，严选优质相机捕捉极致光影、智能手表解码数据生活、潮流运动鞋融合科技性能，满足你对科技与品质的双重追求，探索热爱从未设限。");
            ivStoreImage.setImageResource(R.drawable.store1); // add store1.png in drawable
        } else {
            tvStoreName.setText("小工具中心");
            tvStoreIntro.setText("这里是技术爱好者专属的宝藏据点，更是您寻觅耳机、扬声器、灯与背包的首选中心！我们精挑细选每一款产品，耳机用纯粹音质传递热爱，扬声器以饱满音效点燃氛围，灯具借智能设计点亮灵感，背包凭硬核实力承载装备，用专业与品质，陪您奔赴每一场技术探索之旅～");
            ivStoreImage.setImageResource(R.drawable.store2); // add store2.png in drawable
        }

        // Random rating
        Random rand = new Random();
        float rating = 3.5f + rand.nextFloat() * 1.5f; // Range: 3.5 to 5.0
        tvStoreRating.setText(String.format("评分: %.1f ★", rating));

        tvStoreQual.setText("授权零售商:\n- 5年以上经验\n- 5+ Years Experience\n- 优秀的客户评价");
    }
}
