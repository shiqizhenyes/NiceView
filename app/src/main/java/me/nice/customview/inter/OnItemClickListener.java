package app.zc.com.base.inter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import app.zc.com.base.holder.BaseViewHolder;


public interface OnItemClickListener {

    void onItemClick(BaseViewHolder viewHolder, View v, int poison);

}
