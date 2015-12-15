package org.zhangmz.ui.paradisaeidae;

import org.zhangmz.ui.paradisaeidae.utilities.AnimatingSplitPane;
import org.zhangmz.ui.paradisaeidae.utilities.Utilities;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.View;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.zhangmz.ui.paradisaeidae.business.loginregiste.LoginOrRegistePanel;
import org.zhangmz.ui.paradisaeidae.codeview.CodeViewer;
import org.zhangmz.ui.paradisaeidae.constant.Configurations;
import org.zhangmz.ui.paradisaeidae.constant.Keys;
import org.zhangmz.ui.paradisaeidae.demos.tipwindow.TipWindowWorker;
import org.zhangmz.ui.paradisaeidae.utilities.RoundedBorder;
import org.zhangmz.ui.paradisaeidae.utilities.RoundedPanel;

/**
 * 天堂鸟(Birds of Paradise)，被称为极乐鸟科(Paradisaeidae) 的天堂鸟,又称为燕雀目类。
 * 因人们被其美丽的羽毛所感染，认为它是从天堂来的鸟，因而得名天堂鸟。
 * @author 张孟志
 * 版本：V1.0
 * 日期：2015-05-20
 * 说明：
 * 		Eclipse就是用JAVA编写的客户端，还是开源的；但是NetBean更进一步提出了简单的jdesktop框架。
 * 		开源总是生生不息，当然商业热情更加高涨。
 * 		看看开源的努力，不仅jdesktop，SwingX、TimingFramework……
 * 		但使用起来还是不是很容易，找个集成各种优秀框架/项目的项目才好。
 * 		Sun公司开发的SwingSets3就是个优秀的项目。
 * 		有一天，意外地在开源社区发现BeautyEye，非常抢眼的一个用户界面项目。
 * 		因此编写了这个“Paradisaeidae”……
 * 		想了很久也不知道为什么要编写这个“Paradisaeidae”，大概是因为我有这个能力。
 * 
 * 		这个项目根据Sun公司的SwingSets3框架修改所得。
 * 		吐槽一句：SwingSets3那只企鹅实在是太丑了。
 *      BeautyEye是一款Java Swing跨平台外观（look and feel）实现；
 *      得益于Android的GUI基础技术，BeautyEye的实现完全不同于其它外观；
 *      BeautyEye是免费的，您可以研究、学习甚至商业用途。
 * 		https://github.com/JackJiang2011/beautyeye
 * 
 * 		export 为一个可执行jar时发现乱码，需要设置虚拟机参数
 * 		以后打包为exe（exe4j）时也要注意这个参数设置
 * 		java -Dfile.encoding=utf-8 -jar Paradisaeidae.jar
 * 
 * 版本：V1.1
 * 修改人：张孟志
 * 修改日期：2015-05-22
 * 修改说明：
 * 			1、完整地模仿SwingSets3功能；
 * 			2、对类进行重构，剥离静态内容到Configuration/Keys/Utilities
 * 			3、修改jdesktop源码（AppFramework.jar）中对"quit"类型Action的实现，语言支持改为中文UTF-8
 * 			4、增加一个帮助--关于我们的对话框
 * 
 * 版本：V1.2
 * 修改人：张孟志
 * 修改日期：2015-05-26
 * 修改说明：
 * 			1、关闭“关闭”功能，最下化到托盘，实现常驻内存；
 * 			  这是个流氓功能，不开启。一般只使用确认关闭方式。
 * 			2、完成另外一个流氓功能——弹窗；同样不建议使用。
 * 			使用方法参考TipWindowWorker.main的测试。
 * 			3、测试动态加载模块。在关于我们中增加“重新加载模块”按钮
 * 			实现权限控制的必要手段
 * 
 * 应用程序生命周期:
 * Launch(发启)       你必须使用这个方法
 * Initialize(初始化)  框架会调用这个可选的方法
 * Startup(启动)      框架会调用这个方法
 * Ready(就绪)        框架会调用这个可选的方法
 * Exit(退出)         你必须使用这个方法
 * Shutdown(关闭)     框架会调用这个可选的方法
 * 
 */
public class Paradisaeidae extends SingleFrameApplication  {
    static final Logger logger = Logger.getLogger(Paradisaeidae.class.getName());
    
    private static final ServiceLoader<LookAndFeel> LOOK_AND_FEEL_LOADER = ServiceLoader.load(LookAndFeel.class); 
    
    // 标题
    public static String title;    

    // 为使用LAFds(Nimbus)做的工作，Max OS X有一个bug
    static {
        // Property must be set *early* due to Apple Bug#3909714
        if (Utilities.runningOnMac()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true"); 
        }
        
        // temporary workaround for problem with Nimbus classname
        UIManager.LookAndFeelInfo lafInfo[] = UIManager.getInstalledLookAndFeels();
        for(int i = 0; i < lafInfo.length; i++) {
            if (lafInfo[i].getName().equals("Nimbus")) {
                lafInfo[i] = new UIManager.LookAndFeelInfo("Nimbus",
                        "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                break;
            }
        }
        UIManager.setInstalledLookAndFeels(lafInfo);
        UIManager.put("swing.boldMetal", Boolean.FALSE);
    }    
    
    /**
	 * 判断Swing的界面显示风格是否为Nimbus
	 * @return
	 */
    public static boolean usingNimbus() {
        return UIManager.getLookAndFeel().getName().equals("Nimbus");
    }
    
    /**
     * 读取配置文件中模块对于的类名配置
     * 配置文件为META-INF/demolist
     * 具体的文件处理方法
     * @param reader
     * @return
     * @throws IOException
     */
    private static List<String> readDemoClassNames(Reader reader) throws IOException {
        List<String> demoClassNames = new ArrayList<String>();
        
        BufferedReader breader = new BufferedReader(reader);
        String line;
        while((line = breader.readLine()) != null) {
            demoClassNames.add(line);
        }
        breader.close();
        return demoClassNames;
    }    
    
    /**
	 * J2ME?还是应该说JAVA的用户界面（客户端）开发更像是使用对象的面向过程编程。
	 * 开始我们追逐天堂鸟的里程吧。
	 * @param args
	 */
    public static void main(String[] args) {
        launch(Paradisaeidae.class, args);
    } 
    // 静态内容结束
    
    private ResourceMap resourceMap;
    
    // Application models
    private String demoListTitle;
    private List<Demo> demoList;
    private PropertyChangeListener demoPropertyChangeListener;
    private Map<String, DemoPanel> runningDemoCache;
    private Demo currentDemo;

    // GUI components
    private JPanel mainPanel;
    private DemoSelectorPanel demoSelectorPanel;
    private AnimatingSplitPane demoSplitPane;
    private JPanel demoContainer;
    private JComponent currentDemoPanel;
    private JComponent demoPlaceholder;
    private JPanel codeContainer;
    private CodeViewer codeViewer;    
    private JCheckBoxMenuItem sourceCodeCheckboxItem;
    private ButtonGroup lookAndFeelRadioGroup;
    
    private JPopupMenu popup;
    
    // add by zhangmz 2015-05-22 增加一个帮助--关于我们的对话框  
    // 同理可以增加一个“帮助--服务器地址设置”用于修改交互的服务器
    private JDialog aboutDialog;
    
    // add by zhangmz 2015-05-26 增加一个文件--登录&注册的Tab页窗体
    public JDialog loginOrRegisteFrame;
    
    // add by zhangmz 2015-05-24 关闭“关闭”功能，最下化到托盘，实现常驻内存
    // 托盘图标
    private TrayIcon trayIcon;
    // 系统托盘
	private SystemTray systemTray;
	
	// add by zhangmz 2015-05-24 弹窗
	// private TipWindowWorker tipWindowWorker;
	
    // Properties
    private String lookAndFeel;
    private boolean sourceVisible = true;
    
    /**
     * SingleFrameApplication初始化方法
     * 默认的界面风格不太友好，改为BeautyEye L&F的实现
     */
    @Override
    protected void initialize(String args[]) {        
        try {
        	// Use nimbus L&F by Oracle
        	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        	
        	// 改用 Beauty L&F
        	BeautyEyeLNFHelper.launchBeautyEyeLNF();
        	UIManager.put("RootPane.setupButtonVisible", false);
        	
        } catch (Exception ex) {
            // not catestrophic
        	System.exit(0);
        }
        
        // 读取配置文件，配置文件的名字与项目类名必须保持一致
        // org/zhangmz/ui/paradisaeidae/resources/Paradisaeidae.properties
        resourceMap = getContext().getResourceMap();
        
        title = resourceMap.getString("mainFrame.title");        
        
        runningDemoCache = new HashMap<String, DemoPanel>();
        
        // 需要加载的模块
        setDemoList(resourceMap.getString("demos.title"), getDemoClassNames(args));

        // 刚进入时展示的面板加载
        IntroPanel intro = new IntroPanel();
        setDemoPlaceholder(intro);

    }
    
    /**
     * 读取配置文件中模块对于的类名配置
     * 配置文件为META-INF/demolist
     * @param args
     * @return
     */
    private List<String>getDemoClassNames(String args[]) {
        List<String> demoList = new ArrayList<String>();
        boolean augment = false;
        Exception exception = null; 

        // First look for any demo list files specified on the command-line
        for(String arg : args) {
            if (arg.equals("-a") || arg.equals("-augment")) {
                augment = true;
            } else {
                // process argument as filename containing names of demo classes
                try {
                    demoList.addAll(readDemoClassNames(new FileReader(arg) /*filename*/));
                } catch (IOException ex) {
                    exception = ex;
                    logger.log(Level.WARNING, "unable to read demo class names from file: "+arg, ex);
                }
            }
        }

        if (demoList.isEmpty() || augment) {
            // Load default Demos
            try {
                demoList.addAll(readDemoClassNames(new InputStreamReader(getClass().getResourceAsStream(Configurations.DEMO_LIST_FILE))));
            } catch (IOException ex) {
                exception = ex;
                logger.log(Level.WARNING, "unable to read resource: " + Configurations.DEMO_LIST_FILE, ex);
            }
        }

        if (demoList.isEmpty()) {
            displayErrorMessage(resourceMap.getString("error.noDemosLoaded"), 
                    exception);
        }        
        return demoList;
  
    }

    public void setDemoList(String demoListTitle, List<String> demoClassNamesList) {              
        List<Demo> demoList = new ArrayList<Demo>();
        for(String demoClassName: demoClassNamesList) {
            Demo demo = createDemo(demoClassName);

            if (demo != null) {
                demoList.add(demo);
            }
        }
        this.demoListTitle = demoListTitle;
        this.demoList = demoList;
    }
        
    protected Demo createDemo(String demoClassName) {        
        Class<?> demoClass = null;
        Demo demo = null;
        try {
            demoClass = Class.forName(demoClassName);
        } catch (ClassNotFoundException cnfe) {
            logger.log(Level.WARNING, "demo class not found:"+ demoClassName);
        }        
        if (demoClass != null) {
            // Wrap Demo 
            demo = new Demo(demoClass);            
            demo.addPropertyChangeListener(getDemoPropertyChangeListener());          
        }
        return demo;
    }
    
    protected PropertyChangeListener getDemoPropertyChangeListener() {
        if (demoPropertyChangeListener == null) {
            demoPropertyChangeListener = new DemoPropertyChangeListener();
        }
        return demoPropertyChangeListener;
    }
    
    /**
	 * SingleFrameApplication的抽象方法
	 */
    @Override 
    protected void startup() {
        UIManager.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                if (event.getPropertyName().equals("lookAndFeel")) {
                    configureDefaults();
                }
            }
        });
        
        configureDefaults();
        
        View view = getMainView();
        view.setComponent(createMainPanel());
        view.setMenuBar(createMenuBar());        
        
        applyDefaults();
        
        // add by zhangmz 2015-05-24 关闭“关闭”功能，最下化到托盘，实现常驻内存
        // close2SystemTray();
        
        // 设置主窗体的ICON图标
        // 这句代码不能修改jdesktop框架产生的所有窗体
        // 修改所有窗体要重写configureWindow(Window root)，
        // 但测试发现也不好使。这就是个BUG
        getMainFrame().setIconImage(resourceMap.getImageIcon("Application.icon").getImage());
        
        show(view);     
    } 
    
//    @Override
//	protected void configureWindow(Window root) {
//		super.configureWindow(root);
//    	root.setIconImage(resourceMap.getImageIcon("Application.icon").getImage()); 
//	}

	/**
	 * 界面风格的默认配置
	 * 主要是自定义一些合适的颜色充当调色板
	 */
    private void configureDefaults() {
        
        // 调色板方案采用Jasper Potts
        Color controlColor = UIManager.getColor("control");
        
        UIManager.put(Keys.CONTROL_VERY_LIGHT_SHADOW_KEY, 
                Utilities.deriveColorHSB(controlColor, 0, 0, -0.02f));
        UIManager.put(Keys.CONTROL_LIGHT_SHADOW_KEY, 
                Utilities.deriveColorHSB(controlColor, 0, 0, -0.06f));
        UIManager.put(Keys.CONTROL_MID_SHADOW_KEY, 
                Utilities.deriveColorHSB(controlColor, 0, 0, -0.16f));
        UIManager.put(Keys.CONTROL_VERY_DARK_SHADOW_KEY, 
                Utilities.deriveColorHSB(controlColor, 0, 0, -0.5f));
        UIManager.put(Keys.CONTROL_DARK_SHADOW_KEY, 
                Utilities.deriveColorHSB(controlColor, 0, 0, -0.32f));
        
        // 计算标题的渐变颜色
        // Swing画的界面（Java默认的显示风格）想变成本地（操作系统）的风格
        // Swing应用程序如果是在开源的Look&&Feel 之间切换很容易，
        // 但是如果 把应用程序在开源外观下切换到系统默认的或者JDK自带的外观时，
        // 不是没有标题栏，就是标题栏的外观没有改变，用的是系统的窗口装饰。
        Color titleColor = UIManager.getColor(usingNimbus()
						        				? "nimbusBase" 
						        				: "activeCaption");
        
        // 有一些LAFs (e.g. GTK/linux)是不包含"activeCaption"颜色类型的
        // 在这里就使用环境中的基准颜色（调色板）
        if (titleColor == null) {
            titleColor = controlColor;
        }
        
        // 为标题定义一些颜色
        float hsb[] = Color.RGBtoHSB(
                titleColor.getRed(), titleColor.getGreen(), titleColor.getBlue(), null);
        UIManager.put(Keys.TITLE_GRADIENT_COLOR1_KEY, 
                Color.getHSBColor(hsb[0]-.013f, .15f, .85f));
        UIManager.put(Keys.TITLE_GRADIENT_COLOR2_KEY, 
                Color.getHSBColor(hsb[0]-.005f, .24f, .80f));
        UIManager.put(Keys.TITLE_FOREGROUND_KEY, 
                Color.getHSBColor(hsb[0], .54f, .40f));
        
        // 计算页面上提示窗口中的高亮颜色
        UIManager.put(Keys.CODE_HIGHLIGHT_KEY,
                Color.getHSBColor(hsb[0]-.005f, .20f, .95f));
       
        Font labelFont = UIManager.getFont("Label.font");
        UIManager.put(Keys.TITLE_FONT_KEY, labelFont.deriveFont(Font.BOLD, labelFont.getSize()+4f));        
 
        Color panelColor = UIManager.getColor("Panel.background");
        UIManager.put(Keys.SUB_PANEL_BACKGROUND_KEY, 
                Utilities.deriveColorHSB(panelColor, 0, 0, -.06f));
        
        // 提示窗口颜色设置
        applyDefaults();
        
    } 
    
    protected void applyDefaults() {
        if (codeViewer != null) {
            codeViewer.setHighlightColor(UIManager.getColor(Keys.CODE_HIGHLIGHT_KEY));
        }        
    }
    
    /**
     * add by zhangmz 2015-05-24 关闭“关闭”功能，最下化到托盘，实现常驻内存
     */
    private void close2SystemTray() {
    	// 获得系统托盘的实例
    	systemTray = SystemTray.getSystemTray();
    	
    	addExitListener(new ExitListener() {
    		
			@Override
			public boolean canExit(EventObject eo) {				
				boolean bOkToExit = false;
				
				// 关闭“关闭”功能,不关闭应用。最小化到托盘
				// 这是耍流氓，建议不要使用。使用同时将下面的确认关闭功能注释掉即可。
				getMainFrame().dispose();
                
//				// 确认是否关闭应用
//                Component source = (Component) eo.getSource();
//                bOkToExit = JOptionPane.showConfirmDialog(source, resourceMap.getString("exit.ask.text")) 
//                						== JOptionPane.YES_OPTION;
                
                return bOkToExit;                
			}

			@Override
			public void willExit(EventObject eo) {
				
			}

        });
    	
    	try {
			// trayIcon = new TrayIcon(ImageIO.read(new File("src/resources/images/paradisaeidae_icon_old.png")));
			trayIcon = new TrayIcon(resourceMap.getImageIcon("tray.icon").getImage());
			systemTray.add(trayIcon);//设置托盘的图标，0.gif与该类文件同一目录
		} catch (AWTException e) {
			e.printStackTrace();
		}
    	
//    	// 最小化到托盘
//    	getMainFrame().addWindowListener(
//    			new WindowAdapter(){
//    			public void windowIconified(WindowEvent e){
//    				//窗口最小化时dispose该窗口
//    				getMainFrame().dispose();
//    			}
//    		});
    	
    	trayIcon.addMouseListener(new MouseAdapter(){
	    			public void mouseClicked(MouseEvent e){
	    			if(e.getClickCount() == 2)//双击托盘窗口再现
	    				getMainFrame().setExtendedState(Frame.NORMAL);
	    				getMainFrame().setVisible(true);
	    			}
    		});
    			
    }
    
    /**
     * 
     * @Title: createLoginOrRegisteFrame 
     * @Description: 创建登录/注册窗体
     * @return
     * @throws 
     * 增加人:张孟志
     * 增加日期:2015-5-26 下午3:21:52
     * 说明：创建登录/注册窗体
     */
    private JDialog createLoginOrRegisteFrame() {
    	JDialog frame = new JDialog(new JFrame(), resourceMap.getString("login&registe.text"), true);
 
        frame.getContentPane().add(new LoginOrRegistePanel(this));
        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();
    	frame.setLocationRelativeTo(demoPlaceholder);
    	frame.setVisible(false);
        
        return frame;
    }
    
    /**
     * 增加一个帮助--关于我们的对话框  add by zhangmz 2015-05-22
     * @return
     */
    private JDialog createAboutDialog() {    	 
        
        JDialog dialog = new JDialog(new JFrame(), resourceMap.getString("about.us.text"), true);
        
        JTextArea jTextArea = new JTextArea(resourceMap.getString("about.us.context.text"));
        jTextArea.setPreferredSize(new Dimension(300,180));
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
        // JLabel是默认透明的。setForeground方法会设为不透明。
        // jTextArea.setForeground(Color.GREEN);
        // jTextArea.setOpaque(false);
        jTextArea.setFont(new Font("Serif",0,16));
        // JLabel是默认透明的，只有先取消其透明度。才可以显示颜色，对其它控件也一样。
        jTextArea.setOpaque(true);
        jTextArea.setBackground(new Color(220, 230, 230));        

        // add by zhangmz 2015-05-26 测试动态加载模块  增加按钮 begin
        // dialog.add(jTextArea);   
        dialog.add(jTextArea, BorderLayout.CENTER);  

//        final JButton simpleButton = new JButton(resourceMap.getString("demos.dialog.reload.demos"));
//        simpleButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//            	/*
//            	// 更新demolist
//            	List<String> list = new ArrayList<String>();
//            	list.add("org.zhangmz.ui.paradisaeidae.demos.frame.FrameDemo");
//            	list.add("org.zhangmz.ui.paradisaeidae.demos.dialog.DialogDemo");
//            	
//            	// 需要加载的模块
//                setDemoList(resourceMap.getString("demos.title"), list);
//                */
//            	/*
//            	try {
//            		setDemoList(resourceMap.getString("demos.title"), 
//                			readDemoClassNames(new InputStreamReader(
//                									getClass().getResourceAsStream(
//                											Configurations.DEMO_LIST_ALL_FILE))));
//                } catch (Exception e) {
//					e.printStackTrace();
//				}
//            	
//            	View view = getMainView();
//                view.setComponent(createMainPanel());
//                show(view);  
//                */
//            	try {
//            		flashView(readDemoClassNames(new InputStreamReader(
//    						getClass().getResourceAsStream(
//    								Configurations.DEMO_LIST_ALL_FILE))));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}            	
//            }
//        });
//        dialog.add(simpleButton, BorderLayout.SOUTH);
//        // add by zhangmz 2015-05-26 测试动态加载模块  增加按钮 end
        
        dialog.pack();         
        dialog.setLocationRelativeTo(demoPlaceholder);
        dialog.setVisible(false);
        
        return dialog;
    }
    
    /**
     * 
     * @Title: flashView 
     * @Description: 动态刷新页面视图
     * @param demoClassNamesList
     * @throws 
     * 增加人:张孟志
     * 增加日期:2015-5-26 下午4:01:42
     * 说明：为了重新加载模块列表，动态刷新页面视图
     */
    public void flashView(List<String> demoClassNamesList) {
    	try {
    		setDemoList(resourceMap.getString("demos.title"), demoClassNamesList);
        } catch (Exception e) {
			e.printStackTrace();
		}
    	
    	View view = getMainView();
        view.setComponent(createMainPanel());
        show(view);                    
    }
    
    protected JComponent createMainPanel() {
        
        // Create main panel with demo selection on left and demo/source on right
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
       
        // Create demo selector panel on left
        demoSelectorPanel = new DemoSelectorPanel(demoListTitle, demoList);
        demoSelectorPanel.setPreferredSize(new Dimension(Configurations.DEMO_SELECTOR_WIDTH, Configurations.MAIN_FRAME_HEIGHT));
        demoSelectorPanel.addPropertyChangeListener(new DemoSelectionListener());
        mainPanel.add(demoSelectorPanel, BorderLayout.WEST);
        
        // Create splitpane on right to hold demo and source code
        demoSplitPane = new AnimatingSplitPane(JSplitPane.VERTICAL_SPLIT);
        demoSplitPane.setBorder(Configurations.EMPTY_BORDER);
        mainPanel.add(demoSplitPane, BorderLayout.CENTER);
        
        // Create panel to contain currently running demo
        demoContainer = new JPanel();
        demoContainer.setLayout(new BorderLayout());
        demoContainer.setBorder(Configurations.PANEL_BORDER);
        demoContainer.setPreferredSize(new Dimension(Configurations.DEMO_PANEL_WIDTH, Configurations.DEMO_PANEL_HEIGHT));
        demoSplitPane.setTopComponent(demoContainer);

        currentDemoPanel = demoPlaceholder;
        demoContainer.add(demoPlaceholder, BorderLayout.CENTER);
                
        // Create collapsible source code pane

        codeViewer = new CodeViewer();
        codeContainer = new JPanel(new BorderLayout());
        codeContainer.add(codeViewer);
        codeContainer.setBorder(Configurations.PANEL_BORDER);
        codeContainer.setMinimumSize(new Dimension(0,0));
        demoSplitPane.setBottomComponent(codeContainer);
        
        addPropertyChangeListener(new SwingSetPropertyListener());        
        
        // Create shareable popup menu for demo actions
        popup = new JPopupMenu();
        popup.add(new EditPropertiesAction());
        popup.add(new ViewCodeSnippetAction());

        return mainPanel;
    }
    
    protected JMenuBar createMenuBar() {
    
        JMenuBar menubar = new JMenuBar();
        menubar.setName("menubar");
        
        // File menu
        JMenu fileMenu = new JMenu();
        fileMenu.setName("file");
        menubar.add(fileMenu);
        
        // File -> Login&Registe
        // 增加一个帮助--关于我们的对话框  add by zhangmz 2015-05-22
        loginOrRegisteFrame = createLoginOrRegisteFrame();
        if (!Utilities.runningOnMac()) {
            JMenuItem loginOrRegisteItem = new JMenuItem();
            loginOrRegisteItem.setName("login&registe");
            loginOrRegisteItem.addActionListener(new LoginOrRegisteActionListener());
            fileMenu.add(loginOrRegisteItem);
        }                
        
        // File -> Quit
        // 注意：jdesktop提供了"quit"类型Action的实现，但是这个实现不支持国际化。
        //      这个地方已修改jdesktop源码（AppFramework.jar）实现对中文UTF-8支持
        if (!Utilities.runningOnMac()) {
            JMenuItem quitItem = new JMenuItem();
            quitItem.setName("quit");
            quitItem.setAction(getAction("quit"));
            fileMenu.add(quitItem);
        }
       
        // View menu
        JMenu viewMenu = new JMenu();
        viewMenu.setName("view");
        // View -> Look and Feel       
        viewMenu.add(createLookAndFeelMenu());
        // View -> Source Code Visible 显示提示区
        sourceCodeCheckboxItem = new JCheckBoxMenuItem();
        sourceCodeCheckboxItem.setSelected(isSourceCodeVisible());
        sourceCodeCheckboxItem.setName("sourceCodeCheckboxItem");
        sourceCodeCheckboxItem.addChangeListener(new SourceVisibilityChangeListener());
        viewMenu.add(sourceCodeCheckboxItem);
        menubar.add(viewMenu);

        // add by zhangmz 2015-05-22 增加帮助菜单
        // Help menu
        JMenu helpMenu = new JMenu();
        helpMenu.setName("help");
        
        // Help -> About us
        // 增加一个帮助--关于我们的对话框  add by zhangmz 2015-05-22
        aboutDialog = createAboutDialog();
        if (!Utilities.runningOnMac()) {
            JMenuItem aboutItem = new JMenuItem();
            aboutItem.setName("about");
            aboutItem.addActionListener(new AboutActionListener());
            helpMenu.add(aboutItem);
        }                
        menubar.add(helpMenu);
        
        return menubar;
    }
    
    protected JMenu createLookAndFeelMenu() {
        JMenu menu = new JMenu();
        menu.setName("lookAndFeel");
        
        // Look for toolkit look and feels first
        UIManager.LookAndFeelInfo lookAndFeelInfos[] = UIManager.getInstalledLookAndFeels();
        lookAndFeel = UIManager.getLookAndFeel().getClass().getName();
        lookAndFeelRadioGroup = new ButtonGroup();
        for(UIManager.LookAndFeelInfo lafInfo: lookAndFeelInfos) {
            menu.add(createLookAndFeelItem(lafInfo.getName(), lafInfo.getClassName()));
        }  
        // Now load any look and feels defined externally as service via java.util.ServiceLoader
        LOOK_AND_FEEL_LOADER.iterator();
        for (LookAndFeel laf : LOOK_AND_FEEL_LOADER) {           
            menu.add(createLookAndFeelItem(laf.getName(), laf.getClass().getName()));
        }
         
        return menu;
    }
    
    protected JRadioButtonMenuItem createLookAndFeelItem(String lafName, String lafClassName) {
        JRadioButtonMenuItem lafItem = new JRadioButtonMenuItem();

        lafItem.setSelected(lafClassName.equals(lookAndFeel));
        lafItem.setHideActionText(true);
        lafItem.setAction(getAction("setLookAndFeel"));
        lafItem.setText(lafName);
        lafItem.setActionCommand(lafClassName);
        lookAndFeelRadioGroup.add(lafItem);
        
        return lafItem;
    }
    
    private javax.swing.Action getAction(String actionName) {
        return getContext().getActionMap().get(actionName);
    }
       
    // For displaying error messages to user
    protected void displayErrorMessage(String message, Exception ex) {
        JPanel messagePanel = new JPanel(new BorderLayout());       
        JLabel label = new JLabel(message);
        messagePanel.add(label);
        if (ex != null) {
            RoundedPanel panel = new RoundedPanel(new BorderLayout());
            panel.setBorder(new RoundedBorder());
            
            // remind(aim): provide way to allow user to see exception only if desired
            StringWriter writer = new StringWriter();
            ex.printStackTrace(new PrintWriter(writer));
            JTextArea exceptionText = new JTextArea();
            exceptionText.setText("Cause of error:\n" +
                    writer.getBuffer().toString());
            exceptionText.setBorder(new RoundedBorder());
            exceptionText.setOpaque(false);
            exceptionText.setBackground(
                    Utilities.deriveColorHSB(UIManager.getColor("Panel.background"),
                    0, 0, -.2f));
            JScrollPane scrollpane = new JScrollPane(exceptionText);
            scrollpane.setBorder(Configurations.EMPTY_BORDER);
            scrollpane.setPreferredSize(new Dimension(600,240));
            panel.add(scrollpane);
            messagePanel.add(panel, BorderLayout.SOUTH);            
        }
        JOptionPane.showMessageDialog(getMainFrame(), messagePanel, 
                resourceMap.getString("error.title"),
                JOptionPane.ERROR_MESSAGE);
                
    }
    
    public void setDemoPlaceholder(JComponent demoPlaceholder) {
        JComponent oldDemoPlaceholder = this.demoPlaceholder;
        this.demoPlaceholder = demoPlaceholder;
        firePropertyChange("demoPlaceholder", oldDemoPlaceholder, demoPlaceholder);
    }
    
    public JComponent getDemoPlaceholder() {
        return demoPlaceholder;
    }
    
    public void setCurrentDemo(Demo demo) {
        if (currentDemo == demo) {
            return; // already there
        }
        Demo oldCurrentDemo = currentDemo;        
        currentDemo = demo;
        if (demo != null) {
            DemoPanel demoPanel = runningDemoCache.get(demo.getName());
            if (demoPanel == null || demo.getDemoComponent() == null) {
                demo.startInitializing();
                demoPanel = new DemoPanel(demo);  
                demoPanel.setPreferredSize(currentDemoPanel.getPreferredSize());
                runningDemoCache.put(demo.getName(), demoPanel);
            } 
            demoContainer.remove(currentDemoPanel);
            currentDemoPanel = demoPanel;
            demoContainer.add(currentDemoPanel, BorderLayout.CENTER);
            demoContainer.revalidate();
            demoContainer.repaint();
            getMainFrame().validate();
        }

        if (currentDemo == null) {
            demoContainer.add(demoPlaceholder, BorderLayout.CENTER);
        }
        
        if (isSourceCodeVisible()) {
                codeViewer.setSourceFiles(currentDemo != null?
                                          currentDemo.getSourceFiles() : null);
        }
                
        firePropertyChange("currentDemo", oldCurrentDemo, demo);
    }
   
    
    public Demo getCurrentDemo() {
        return currentDemo;
    }
    
    public void setLookAndFeel(String lookAndFeel) throws ClassNotFoundException,
        InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        
        String oldLookAndFeel = this.lookAndFeel;
        
	if (oldLookAndFeel != lookAndFeel) {
            UIManager.setLookAndFeel(lookAndFeel);
            this.lookAndFeel = lookAndFeel;
            updateLookAndFeel();
            firePropertyChange("lookAndFeel", oldLookAndFeel, lookAndFeel);                     
	}
    }
    
    @Action 
    public void setLookAndFeel() {
        ButtonModel model = lookAndFeelRadioGroup.getSelection();
        String lookAndFeelName = model.getActionCommand();
        try {
            setLookAndFeel(lookAndFeelName);
        } catch (Exception ex) {
            displayErrorMessage(resourceMap.getString("error.unableToChangeLookAndFeel") +
                    "to "+lookAndFeelName, ex);
        }
    }
    
    public String getLookAndFeel() {
        return lookAndFeel;
    }

    public void setSourceCodeVisible(boolean sourceVisible) {
        boolean oldSourceVisible = this.sourceVisible;
        this.sourceVisible = sourceVisible;
        firePropertyChange("sourceCodeVisible", oldSourceVisible, sourceVisible);
    }
    
    public boolean isSourceCodeVisible() {
        return sourceVisible;       
    } 
    
    private void updateLookAndFeel() {
        Window windows[] = Frame.getWindows();

        for(Window window : windows) {
            SwingUtilities.updateComponentTreeUI(window);
            for(DemoPanel demoPanel : runningDemoCache.values()) {
                SwingUtilities.updateComponentTreeUI(demoPanel);
            }
        }
    }

    // hook used to detect if any components in the demo have registered a
    // code snippet key for the their creation code inside the source
    private void registerPopups(Component component) {
        
        if (component instanceof Container) {
            Component children[] = ((Container)component).getComponents();
            for(Component child: children) {
                if (child instanceof JComponent) {
                    registerPopups(child);
                }
            }
        }
        if (component instanceof JComponent) {
            JComponent jcomponent = (JComponent)component;
            String snippetKey = (String)jcomponent.getClientProperty("snippetKey");
            if (snippetKey != null) {
                jcomponent.setComponentPopupMenu(popup);
            }
        }
    }    
    
    private class DemoSelectionListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent event) {
            if (event.getPropertyName().equals("selectedDemo")) {
                setCurrentDemo((Demo)event.getNewValue());
            }
        }
    }
           
    
    // registered on Demo to detect when the demo component is instantiated.
    // we need this because when we embed the demo inside an HTML description pane,
    // we don't have control over the demo component's instantiation
    private class DemoPropertyChangeListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (propertyName.equals("demoComponent")) {
                Demo demo = (Demo)e.getSource();
                JComponent demoComponent = (JComponent)e.getNewValue();
                if (demoComponent != null) {
                    demoComponent.putClientProperty("paradisaeidae.demo", demo);
                    demoComponent.addHierarchyListener(new DemoVisibilityListener());
                    registerPopups(demoComponent);
                }
            } 
        }
    }
    
    private class DemoVisibilityListener implements HierarchyListener {
        public void hierarchyChanged(HierarchyEvent event) {
            if ((event.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) > 0) {
                JComponent component = (JComponent)event.getComponent();
                final Demo demo = (Demo)component.getClientProperty("paradisaeidae.demo");
                if (!component.isShowing()) {
                    demo.stop();
                } else {
                    demoContainer.revalidate();
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            demo.start();
                        }
                    });
                }
            }            
        }        
    }
    
    /**
     * 
     * @ClassName:LoginOrRegisteActionListener 
     * @Description:菜单栏“文件--注册/登录”监听器 
     * @author:张孟志
     * @date:2015-5-26 下午3:23:48 
     * @version V1.0
     * 说明：菜单栏“文件--注册/登录”监听器 
     */
    private class LoginOrRegisteActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (loginOrRegisteFrame.isShowing()) {
				loginOrRegisteFrame.toFront();
            } else {
            	loginOrRegisteFrame.setVisible(true);            	
            }	

//			// 在关闭about us的时候弹窗，流氓功能，不建议使用
//			Map<String, String> fm = new HashMap<String, String>();
//	    	fm.put("title", "QQQ");
//	    	fm.put("instruction", "WWW");
//	    	fm.put("feature", "AAA\nBBB");
//	    	fm.put("release", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        	tipWindowWorker = new TipWindowWorker(fm);

//        	tipWindowWorker = new TipWindowWorker();
//        	tipWindowWorker.versionUtil();
	    	
		}
    }
    
    /**
     * 帮助--关于我们的监听器
     * @author 张孟志
     *
     */
    private class AboutActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (aboutDialog.isShowing()) {
            	aboutDialog.toFront();
            } else {
            	aboutDialog.setVisible(true);            	
            }	
		}
    }
    
    // activated when user selects/unselects checkbox menu item
    private class SourceVisibilityChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent event) {
            setSourceCodeVisible(sourceCodeCheckboxItem.isSelected());
        }
    }

    private class SwingSetPropertyListener implements PropertyChangeListener {       
        public void propertyChange(PropertyChangeEvent event) {
            String propertyName = event.getPropertyName();
            if (propertyName.equals("sourceCodeVisible")) {
                boolean sourceVisible = ((Boolean)event.getNewValue()).booleanValue();
                if (sourceVisible) {
                    // update codeViewer in case current demo changed while
                    // source was invisible
                    codeViewer.setSourceFiles(currentDemo != null?
                                                  currentDemo.getSourceFiles() : null);
                }
                demoSplitPane.setExpanded(!sourceVisible);
                sourceCodeCheckboxItem.setSelected(sourceVisible);
            } 
        }        
    }
 
    private class ViewCodeSnippetAction extends AbstractAction {
        public ViewCodeSnippetAction() {
            super("View Source Code");
        }
        public void actionPerformed(ActionEvent actionEvent) {
            Container popup = (JComponent)actionEvent.getSource();
            while(popup != null && !(popup instanceof JPopupMenu)) {
                popup = popup.getParent();
            }
            JComponent target = (JComponent)((JPopupMenu)popup).getInvoker();
            setSourceCodeVisible(true);
            
            String snippetKey = (String)target.getClientProperty("snippetKey");
            if (snippetKey != null) {
                codeViewer.highlightSnippetSet(snippetKey);
            } else {
                logger.log(Level.WARNING, "can't find source code snippet for:" + snippetKey);
            }                                    
        }
    }
    
    private static class EditPropertiesAction extends AbstractAction {
        public EditPropertiesAction() {
            super("Edit Properties");
        }
        public boolean isEnabled() {
            return false;
        }
        public void actionPerformed(ActionEvent actionEvent) {
            
        }
    } 
}
