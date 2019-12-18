package me.nice.customview.inter;

import android.view.View;



public interface OnItemClickListener<T> {

    void onItemClick(View v, int poison, T item);

}
