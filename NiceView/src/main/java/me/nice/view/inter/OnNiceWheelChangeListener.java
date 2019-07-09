package me.nice.view.inter;

import me.nice.view.widget.NiceWheelPicker;

public interface OnNiceWheelChangeListener {

    void onWheelScrolled(int offset);


    void onWheelSelected(int position);

    /**
     *
     * @param state {@link NiceWheelPicker#SCROLL_STATE_IDLE}
     *              {@link NiceWheelPicker#SCROLL_STATE_DRAGGING}
     *              {@link NiceWheelPicker#SCROLL_STATE_SCROLLING}
     *              <p>
     *              State of WheelPicker, only one of the following
     *              {@link NiceWheelPicker#SCROLL_STATE_IDLE}
     *              Express WheelPicker in state of idle
     *              {@link NiceWheelPicker#SCROLL_STATE_DRAGGING}
     *              Express WheelPicker in state of dragging
     *              {@link NiceWheelPicker#SCROLL_STATE_SCROLLING}
     *              Express WheelPicker in state of scrolling
     */
    void onNiceWheelScrollStateChanged(int state);

}
