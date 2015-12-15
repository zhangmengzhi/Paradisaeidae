package org.zhangmz.ui.paradisaeidae.constant;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * 定义一些配置
 * @author 张孟志
 * 版本：V1.0
 * 日期：2015-05-21
 * 说明：定义一些配置，例如：窗体大小、边框宽度
 *
 */
public class Configurations {
	
	public static final int MAIN_FRAME_WIDTH = 880;
    public static final int MAIN_FRAME_HEIGHT = 640;
    public static final int DEMO_SELECTOR_WIDTH = 186;
    public static final int DEMO_PANEL_HEIGHT = 480;
    public static final int DEMO_PANEL_WIDTH = MAIN_FRAME_WIDTH - DEMO_SELECTOR_WIDTH;

    public static final Border EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
    public static final Border PANEL_BORDER = new EmptyBorder(10, 10, 10, 10);
    
    public static final String DEMO_LIST_FILE = "/META-INF/demolist";
    public static final String DEMO_LIST_ALL_FILE = "/META-INF/demolist4all";
}
