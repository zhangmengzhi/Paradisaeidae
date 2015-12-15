package org.zhangmz.ui.paradisaeidae.demos.tipwindow;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

import javax.swing.JDialog;

/**
 * 定义弹窗
 * @author 张孟志
 * 版本：V1.0
 * 日期：2015-05-24
 * 说明：定义弹窗
 *
 */
public class TipWindow extends JDialog {

	private static Dimension dim;
    private int x, y;
    private int width, height;
    private static Insets screenInsets;
    
    private static long tipTime = 3000;
    private static long outTime = 20;    
   
	public TipWindow(int width, int height) {
        this.width = width;
        this.height = height;
        this.setDefault();
        initComponents();
    }

    public TipWindow(Frame owner, String title, boolean modal) {
		super(owner, title, modal);

        this.width = 240;
        this.height = 180;
		this.setDefault();
	}
    
    /**
     * 针对屏幕大小做初始化设置
     */
    private void setDefault(){
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
                this.getGraphicsConfiguration());
        x = (int) (dim.getWidth() - width - 3);
        y = (int) (dim.getHeight() - screenInsets.bottom - 3);
        this.setSize(width, height);
    }

    public void run() {
        for (int i = 0; i <= height; i += 10) {
            try {
                this.setLocation(x, y - i);
                Thread.sleep(5);
            } catch (InterruptedException ex) {
            }
        }
        
        // 此处代码用来实现让消息提示框3秒后自动消失
        try {
            Thread.sleep(TipWindow.tipTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        close();
    }

    private void initComponents() {
        this.setSize(width, height);
        this.setLocation(x, y);
        this.setBackground(Color.white);        
    }

    /**
     * 缓慢的退出桌面
     */
    public void close() {
        x = this.getX();
        y = this.getY();
        int ybottom = (int) dim.getHeight() - screenInsets.bottom;
        for (int i = 0; i <= ybottom - y; i += 10) {
            try {
                setLocation(x, y + i);
                Thread.sleep(TipWindow.outTime);
            } catch (InterruptedException ex) {
            }
        }
        dispose();
    }

}