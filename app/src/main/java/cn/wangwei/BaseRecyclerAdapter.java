package cn.wangwei;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wangwei on 2017/11/15.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NO_MORE = 2; //  底部的  我是有底线的布局  （列表数据全部拉取完成后的布局）
    private boolean hasFooter = false;
    private ArrayList<T> mDatas = new ArrayList<>();

    private View mHeaderView;
    private int mHeaderCount = 0;//头部View个数

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        mHeaderCount = mHeaderCount + 1;
        notifyItemInserted(0);
    }

    public void setNoMore(boolean hasFooter) {
        if (this.hasFooter != hasFooter) {
            this.hasFooter = hasFooter;
            //  notifyDataSetChanged();
        }
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(ArrayList<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public T getPosData(int position) {
        return mDatas.get(position);
    }

    //判断是否是Footer
    public boolean isFooter(int position) {
        return position == mHeaderCount + getContentItemCount();
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderCount != 0 && position < mHeaderCount) {
            return TYPE_HEADER;
        } else if (hasFooter && position >= mHeaderCount + getContentItemCount()) {
            return TYPE_NO_MORE;
        }
        return getItemType(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (mHeaderCount != 0 && viewType == TYPE_HEADER) {
            return new Holder(mHeaderView);
        } else if (viewType == TYPE_NO_MORE) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_no_more, parent, false);
            return new FooterViewHolder(layout);
        }
        return onCreateItemView(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isHeaderView(position)) {
            return;
        } else if (viewHolder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
            //没有更多数据
            if (!hasFooter) {
                Log.e("", "111111111");

                //  footerViewHolder.loadMore.setText(hint);
            }
        } else {
            final int pos = getRealPosition(viewHolder);
            final T data = mDatas.get(pos);
            onBindItemView(viewHolder, pos, data);
            if (mListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(pos, data);
                    }
                });
            }
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    //内容长度
    public int getContentItemCount() {
        return mDatas.size();
    }

    // 总长度  包含内容Item   header   footer
    @Override
    public int getItemCount() {
        return mHeaderView == null ? getContentItemCount() + (hasFooter ? 1 : 0) : getContentItemCount() + 1 + (hasFooter ? 1 : 0);
    }

    public abstract RecyclerView.ViewHolder onCreateItemView(ViewGroup parent, final int viewType);

    //
    public abstract int getItemType(int position);

    public abstract void onBindItemView(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);

    //Footer ViewHolder
    public static class FooterViewHolder extends RecyclerView.ViewHolder {


        private final TextView loadMore;

        public FooterViewHolder(View view) {
            super(view);
            loadMore = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }


    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
