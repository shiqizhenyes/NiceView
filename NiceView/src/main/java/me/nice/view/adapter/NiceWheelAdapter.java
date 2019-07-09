package me.nice.view.adapter;

import java.util.ArrayList;
import java.util.List;

import me.nice.view.inter.BaseAdapter;

public class NiceWheelAdapter<V> implements BaseAdapter {

    private List<V> data;

    public NiceWheelAdapter() {
        this(new ArrayList<V>());
    }

    public NiceWheelAdapter(List<V> data) {
        this.data = new ArrayList<V>();
        this.data.addAll(data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public V getItem(int position) {
        final int itemCount = getItemCount();
        return itemCount == 0 ? null : data.get((position + itemCount) % itemCount);
    }

    @Override
    public String getItemText(int position) {
        return String.valueOf(data.get(position));
    }

    public List<V> getData() {
        return data;
    }

    public void setData(List<V> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    public void addData(List<V> data) {
        this.data.addAll(data);
    }

    public int getItemPosition(V value) {
        int position = -1;
        if (data != null) {
            return data.indexOf(value);
        }
        return position;
    }
}
