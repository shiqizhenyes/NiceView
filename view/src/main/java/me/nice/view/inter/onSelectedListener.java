package me.nice.view.inter;

import me.nice.view.widget.wheel.NiceWheelPicker;

public interface onSelectedListener<PICKER extends NiceWheelPicker, V> {
    
    void onSelected(PICKER picker, int position, V value);
    void onCurrentScrolled(PICKER picker, int position, V value);
}
