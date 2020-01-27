package me.nice.view.util;

public class DensityUtil {

    private static float density;
    private static float scaledDensity;
    private static DensityUtil densityUtil;

    private DensityUtil() {}

    public static DensityUtil getInstance() {
        if (densityUtil == null) {
            synchronized (DensityUtil.class) {
                if (densityUtil == null) {
                    densityUtil = new DensityUtil();
                }
            }
        }
        return densityUtil;
    }

    public DensityUtil setDensity(float density) {
        DensityUtil.density = density;
        return this;
    }

    public DensityUtil setScaledDensity(float scaledDensity) {
        DensityUtil.scaledDensity = scaledDensity;
        return this;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return (int) (0.5f + dpValue * density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dp(int pxValue) {
        return (pxValue / density);
    }


    public static int sp2px(float spValue) {
        return (int) (0.5f + spValue * scaledDensity);
    }

    public static float px2sp(int pxValue) {
        return pxValue / scaledDensity;
    }

}
