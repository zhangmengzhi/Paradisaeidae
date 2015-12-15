package org.zhangmz.ui.paradisaeidae.demos.tipwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

/**
 * 定义弹窗使用入口
 * @author 张孟志
 * 版本：V1.0
 * 日期：2015-05-24
 * 说明：定义弹窗使用入口
 * 		流氓功能——弹窗；同样不建议使用。
 * 		使用方法参考main的测试。
 *
 */
public class TipWindowWorker {

	private JFrame jframe = null;
	private Map<String, String> feaMap = null;
    private TipWindow tw = null;// 提示框
    // private ImageIcon img = null;// 图像组件
    // private JLabel imgLabel = null; // 背景图片标签
    private JPanel headPan = null;
    private JPanel feaPan = null;
    private JPanel btnPan = null;
    private JLabel head = null;
    private JTextArea feature = null;
    private JScrollPane jfeaPan = null;
    private JLabel releaseLabel = null;
    private SimpleDateFormat sdf = null;

    // add by zhangmz 2015-06-02
    // 点击弹窗时打开链接
    private String uri = "";
    
    public TipWindowWorker () {
    	super();
    }
    
    /**
     * 提供弹窗信息的构造函数
     * @param feaMap
     * 			Map类型，包括title、instruction、
     * 			feature、release四组数据
     */
    public TipWindowWorker (Map<String, String> feaMap) {
    	this(null, feaMap);
    }
    
    public TipWindowWorker (JFrame jframe, Map<String, String> feaMap) {
    	this.jframe = jframe;
    	this.feaMap = feaMap;
    }
    
    {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        feaMap = new HashMap<String, String>();
        feaMap.put("title", "流氓弹窗");
        feaMap.put("instruction", "出来耍流氓");
        feaMap.put("feature",
                "1.流氓弹窗\n2.开发公司:流氓公司\n3.联系方式:58110");
        feaMap.put("release", sdf.format(new Date()));
    }

    public void versionUtil() {
        init();
        tw.setAlwaysOnTop(true);
        tw.setUndecorated(true);
        tw.setResizable(false);
        tw.setDefaultCloseOperation(TipWindow.DISPOSE_ON_CLOSE);
        tw.setVisible(true);
        tw.run();
    }

    private void init() {   
//    	try {
//        	// Use nimbus L&F by Oracle
//        	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//        	
//        	// 改用 Beauty L&F
//        	BeautyEyeLNFHelper.launchBeautyEyeLNF();
//        	UIManager.put("RootPane.setupButtonVisible", false);
//        	
//        } catch (Exception ex) {
//            // not catestrophic
//        	System.exit(0);
//        }
        // 新建消息提示框
        // tw = new TipWindow(240, 180);
        // tw.setTitle(feaMap.get("title"));
    	if(this.jframe == null){
    		this.jframe = new JFrame();
    	}
    	tw = new TipWindow(this.jframe, feaMap.get("title"), false);
    	tw.setSize(240, 180);
    	// tw.setUri(feaMap.get("uri"));
    	this.uri = feaMap.get("uri");
        // img = new ImageIcon(ImageIO.read(new File("src/resources/XXX.png")));
        // imgLabel = new JLabel(img);
        // 设置各个面板的布局以及面板中控件的边界
        headPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        feaPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        head = new JLabel(feaMap.get("instruction"));
        feature = new JTextArea(feaMap.get("feature"));
        jfeaPan = new JScrollPane(feature);
        releaseLabel = new JLabel(feaMap.get("release"));

        // 将各个面板设置为透明，否则看不到背景图片
        ((JPanel) tw.getContentPane()).setOpaque(false);
        headPan.setOpaque(false);
        feaPan.setOpaque(false);
        btnPan.setOpaque(false);

        // 设置JDialog的整个背景图片
        // tw.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
        // imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        headPan.setPreferredSize(new Dimension(240, 48));

        // 设置提示框的边框,宽度和颜色
        tw.getRootPane().setBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));

        head.setPreferredSize(new Dimension(200, 28));
        head.setVerticalTextPosition(JLabel.CENTER);
        head.setHorizontalTextPosition(JLabel.CENTER);
        head.setFont(new Font("宋体", Font.PLAIN, 12));
        head.setForeground(Color.blue);

        feature.setEditable(false);
        feature.setForeground(Color.red);
        feature.setFont(new Font("宋体", Font.PLAIN, 13));
        feature.setBackground(new Color(210, 215, 220));
        // 设置文本域自动换行
        feature.setLineWrap(true);

        jfeaPan.setPreferredSize(new Dimension(200, 64));
        jfeaPan.setBorder(null);
        jfeaPan.setBackground(Color.black);

        releaseLabel.setForeground(Color.DARK_GRAY);
        releaseLabel.setFont(new Font("宋体", Font.PLAIN, 12));

        headPan.add(head);

        feaPan.add(jfeaPan);
        feaPan.add(releaseLabel);

        tw.add(headPan, BorderLayout.NORTH);
        tw.add(feaPan, BorderLayout.CENTER);
        tw.add(btnPan, BorderLayout.SOUTH);
        
        HrefListener hl = new HrefListener();
        
        // 增加鼠标点击时间，打开浏览器
        head.addMouseListener(hl);
        feature.addMouseListener(hl);
        tw.addMouseListener(hl);
    }

    public static void main(String args[]) {
    	// TipWindowWorker reminUtil = new TipWindowWorker();
    	
    	Map<String, String> fm = new HashMap<String, String>();
    	fm.put("title", "XXX");
    	fm.put("instruction", "CCC");
    	fm.put("feature", "AAA\nBBB");
    	fm.put("release", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    	fm.put("uri", "http://www.baidu.com");
    	TipWindowWorker reminUtil = new TipWindowWorker(fm);
    	
        reminUtil.versionUtil();
    }
    
    
    class HrefListener implements MouseListener {
    	
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				// System.out.println(uri);
				// 使用默认的浏览器打开链接
				if(uri != null && !"".equals(uri)){
					Desktop.getDesktop().browse(new URI(uri));
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}				
		}
}

}
