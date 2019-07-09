package me.nice.view.inter;

public interface SingleDateAndTimeConstants {
    public static final int MIN_HOUR_DEFAULT = 0;
    public static final int MAX_HOUR_DEFAULT = 23;
    public static final int MAX_HOUR_AM_PM = 12;
    public static final int STEP_HOURS_DEFAULT = 1;

    public static final int DAYS_PADDING = 364;

    public static final int MIN_MINUTES = 0;
    public static final int MAX_MINUTES = 59;
    public static final int STEP_MINUTES_DEFAULT = 5;

    public static final int MIN_YEAR_DIFF = 100;
    public static final int MAX_YEAR_DIFF = 100;


    public static final boolean IS_CYCLIC_DEFAULT = true;
    public static final boolean IS_CURVED_DEFAULT = true;
    public static final boolean MUST_BE_ON_FUTUR_DEFAULT = false;
    public static final int DELAY_BEFORE_CHECK_PAST = 200;
    static final int VISIBLE_ITEM_COUNT_DEFAULT = 7;
    static final int PM_HOUR_ADDITION = 12;

    static final CharSequence FORMAT_24_HOUR = "EEE d MMM H:mm";
    static final CharSequence FORMAT_12_HOUR = "EEE d MMM h:mm a";





    public static final int COMMON_DIALOG = 102;
    public static final int BOTTOM_SHEET = 103;


    static final String DAY_FORMAT_EEE_D_MMM= "EEE d MMM";

    static final String DAY_FORMAT_MMM_D= "MMM d";

    static final String DAY_FORMAT_MM_D= "MM d";

    static final String DAY_FORMAT_M_D= "M d";

    static final String DAY_FORMAT_M_D_EE= "MMM d EEE";



}
