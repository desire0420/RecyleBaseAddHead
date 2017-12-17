package cn.wangwei;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.wangwei.scroll.ColorPointHintView;
import cn.wangwei.scroll.RollPagerView;
import cn.wangwei.scroll.StaticPagerAdapter;
import cn.wangwei.scroll.Utils;

public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    private Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        add_button = (Button) findViewById(R.id.add_button);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //    mLayoutManager = new GridLayoutManager(this, 2);
//        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new GridItemDecoration(this, true));

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addDatas(generateData());
        setHeader(mRecyclerView);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<JSONObject>() {
            @Override
            public void onItemClick(int position, JSONObject data) {
                Toast.makeText(MainActivity.this, position + "," + Utils.parseJson(data, "ha"), Toast.LENGTH_SHORT).show();

            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private class BannerAdapter extends StaticPagerAdapter {
        private List<Ad> list;

        public BannerAdapter() {
            list = DataProvider.getAdList();
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //加载图片
            Glide.with(MainActivity.this)
                    .load(list.get(position).getImage())
                    .placeholder(R.drawable.ic_menu_gallery)
                    .into(imageView);
            //点击事件
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getUrl())));
                }
            });
            return imageView;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    private void setHeader(RecyclerView view) {
        // View header = LayoutInflater.from(this).inflate(R.layout.header, view, false);
      //  mAdapter.setHeaderView(header);
    }

    private ArrayList<JSONObject> generateData() {
        ArrayList<JSONObject> data = new ArrayList<JSONObject>();
        for (int i = 0; i < 10; i++) {
            JSONObject object = new JSONObject();
            try {
                object.put("haha", "JSONObject" + i);
                object.put("ha", "你好啊" + i);
                data.add(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return data;
    }


}
