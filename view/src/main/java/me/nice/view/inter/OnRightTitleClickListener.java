package me.nice.view.inter;

import android.view.View;

import java.util.Date;

import me.nice.view.dialog.NiceDateAndTimePickerDialog;

public interface OnRightTitleClickListener {
    void onClick(View v, NiceDateAndTimePickerDialog dialog, Date date);
}
