package com.grace.www.qrcode.config;

import android.graphics.Color;

import com.grace.www.qrcode.R;
import com.grace.www.qrcode.util.Util;

/**
 * 要重新定义扫描页面的UI，到CaptureActivity里去处理，布局为 activity_qrcode_capture_layout
 */
public class Config {
    /**
     * 定义扫描框宽高,一般按照屏幕占比计算
     * 默认取的是正方形，屏幕宽度的0.75
     * 如果扫描条形码，你可以在这里把宽高定义矩形
     */

    public static final float ratio = 0.7f;
    public static final int MIN_FRAME_WIDTH = (int) (Util.getWindowWidthPix() * ratio);
    public static final int MIN_FRAME_HEIGHT = (int) (Util.getWindowWidthPix() * ratio);
    public static final int MAX_FRAME_WIDTH = (int) (Util.getWindowWidthPix() * ratio);
    public static final int MAX_FRAME_HEIGHT = (int) (Util.getWindowWidthPix() * ratio);


    /**
     * 扫描框距左边的距离，取值为2时，水平方向居中,大于2，靠左
     */
    public static final float LEFT_OFFSIDE_RATIO = 2;
    /**
     * 扫描框距顶部的距离，取值为2时，竖直方向居中，大于2，靠上
     */
    public static final float TOP_OFFSIDE_RATIO = 2.5f;


    /**
     * 扫描时是否显示雷达扫描点效果，颜色值可以在资源文件里修改
     */

    public static final boolean isShowPossibleResultPoints = true;
    /**
     * 是不是要开启全屏幕取景，打开后，会加快一点扫描速率
     */
    public static final boolean isFullScreenShot = true;//开启全屏幕取景时，建议isShowPossibleResultPoints设为false，因为扫描点会在取景框外，显得怪


    /**
     * 移动的扫描线条的资源文件ID
     */
    public static final int SCAN_LINE_RES_ID = R.drawable.fle;
    /**
     * 扫描线的移动速度
     */
    public static final int SPEEN_DISTANCE = 7;


    /**
     * 二维码扫描框的颜色
     */
    public static final int BORDER_COLOR = Color.parseColor("#f05797");

    /**
     * 二维码扫描边框线条的宽度
     */
    public static int CORNER_WIDTH = 3;

    /**
     * 二维码扫描边框线条的长度
     */
    public static final int CORNER_LENGTH = 15;


    /**
     * 文本提示内容
     */
    public static final String TEXT_TIP = "放入框中即可进行二维码扫描";
    /**
     * 文本提示字体大小
     */
    public static final int TEXT_SIZE = 16;
    /**
     * 文本提示内容距离扫描框距离
     */
    public static final int TEXT_PADDING_TOP = 30;

}






