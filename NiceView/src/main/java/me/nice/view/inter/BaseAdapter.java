package me.nice.view.inter;

public interface BaseAdapter<V> {

    int getItemCount();

    V getItem(int position);

    String getItemText(int position);

}
