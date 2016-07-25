package com.example.qzl.tou_bu_shi_cha;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {

    private ParallaxListView listview;
    private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ParallaxListView) findViewById(R.id.listview);

        listview.setOverScrollMode(ListView.OVER_SCROLL_NEVER);//永远不显示蓝色阴影
        //添加head
        View headView = View.inflate(this,R.layout.layout_head,null);
        ImageView iv_layout_head_icon = (ImageView) headView.findViewById(R.id.iv_layout_head_icon);

        listview.setParallaxImageView(iv_layout_head_icon);
        listview.addHeaderView(headView);
        listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,indexArr));
    }
}
