package me.nice.view.inter;

import me.nice.view.widget.NiceWheelPicker;

public interface OnItemSelectedListener {

    void onItemSelected(Object data, int position);

    void onCurrentItemOfScroll(int position);
}
