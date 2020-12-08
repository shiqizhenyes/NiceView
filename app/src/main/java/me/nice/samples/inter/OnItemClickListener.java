package me.nice.samples.inter;

import android.view.View;



public interface OnItemClickListener<T> {

    void onItemClick(View v, int poison, T item);

}
