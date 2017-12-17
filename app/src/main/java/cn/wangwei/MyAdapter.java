package cn.wangwei;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import cn.wangwei.scroll.Utils;

/**
 * Created by qibin on 2015/11/7.
 */
public class MyAdapter extends BaseRecyclerAdapter<JSONObject> {

    public static final int ITEM_BILLING = 0; //开单金额明细
    public static final int ITEM_SERVICE = 1; // 服务客数明细

    @Override
    public RecyclerView.ViewHolder onCreateItemView(ViewGroup parent, int viewType) {
        if (viewType == ITEM_BILLING) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyHolder(layout);
        } else if (viewType == ITEM_SERVICE) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            return new MyHolder1(layout);

        }
        return null;

    }

    @Override
    public int getItemType(int position) {
        if (position % 2 == 0) {
            return ITEM_BILLING;
        } else {
            return ITEM_SERVICE;
        }

    }


    @Override
    public void onBindItemView(RecyclerView.ViewHolder viewHolder, int RealPosition, JSONObject data) {
        if (viewHolder instanceof MyHolder) {
            ((MyHolder) viewHolder).text.setText(Utils.parseJson(data, "haha"));
            ((MyHolder) viewHolder).name.setText(Utils.parseJson(data, "ha"));
        }

    }

    class MyHolder extends BaseRecyclerAdapter.Holder {
        TextView text, name;

        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    class MyHolder1 extends BaseRecyclerAdapter.Holder {
        TextView text, name;

        public MyHolder1(View itemView) {
            super(itemView);

        }
    }
}
