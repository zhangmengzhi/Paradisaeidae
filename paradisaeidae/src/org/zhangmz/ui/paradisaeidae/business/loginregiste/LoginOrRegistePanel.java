package org.zhangmz.ui.paradisaeidae.business.loginregiste;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.zhangmz.ui.paradisaeidae.Paradisaeidae;
import org.zhangmz.ui.paradisaeidae.demos.ResourceManager;
import org.zhangmz.ui.paradisaeidae.utilities.Utilities;

/**
 * 
 * @ClassName:LoginOrRegistePanel 
 * @Description:登录/注册的窗口Tab面板
 * @author:张孟志
 * @date:2015-5-31 上午8:24:32 
 * @version V1.0
 * 说明：登录/注册的窗口Tab面板
 * 		实现一个登录/注册的窗口Tab面板
 * 		登录需要注意，这个地方实现了权限控制
 * 		即刷新功能模块树
 * 		
 * 		目前只是一个原型，未实现与服务器交互
 * 		后续需要集成HTTPClient，已实现数据交互
 * 		注册暂未实现
 */
public class LoginOrRegistePanel extends JPanel {	
	private final ResourceManager resourceManager = new ResourceManager(this.getClass());

	private Paradisaeidae paradisaeidae;
    private final JTabbedPane tabbedpane;
    
    private JLabel user = null;
    private JTextField username = new JTextField(10);
    private JLabel pwd = null;
    private JPasswordField password = new JPasswordField(10);
    private JCheckBox rememberPassword = null;
    private JCheckBox autologin = null;
    private JButton login = null;
    private JButton reset = null;
    
    /**
     * 测试环境用的所有模块
     * 发布时请不要使用
     */
    private final String[] strArry = {
    		"org.zhangmz.ui.paradisaeidae.demos.frame.FrameDemo",
    		"org.zhangmz.ui.paradisaeidae.demos.dialog.DialogDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.window.WindowDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.internalframe.InternalFrameDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.tabbedpane.TabbedPaneDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.scrollpane.ScrollPaneDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.splitpane.SplitPaneDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.gridbaglayout.GridBagLayoutDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.table.TableDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.tree.TreeDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.list.ListDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.togglebutton.ToggleButtonDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.button.ButtonDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.combobox.ComboBoxDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.progressbar.ProgressBarDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.slider.SliderDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.textfield.TextFieldDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.editorpane.EditorPaneDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.filechooser.FileChooserDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.colorchooser.ColorChooserDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.optionpane.OptionPaneDemo", 
    		"org.zhangmz.ui.paradisaeidae.demos.pushclient.PushClientDemo"
    }; 
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new LoginOrRegistePanel(null));
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * 
     * <p>Title: LoginOrRegistePanel</p> 
     * <p>Description: 注册/登录Tab页面版</p> 
     * @param paradisaeidae 登录成功后需要刷新Paradisaeidae
     */
    public LoginOrRegistePanel(final Paradisaeidae paradisaeidae) {
    	this.paradisaeidae = paradisaeidae;
    	
        setLayout(new BorderLayout());       

        // create tab 
        tabbedpane = new JTabbedPane();
        add(tabbedpane, BorderLayout.CENTER);

        // modify by zhangmz 2015-05-31 简单实现一个登录方案 begin
        // String name = resourceManager.getString("LoginOrRegistePanel.tab1.name");
        // JLabel pix = new JLabel();
        JPanel tab1Panel = new JPanel();
        
        user = new JLabel(resourceManager.getString("LoginOrRegistePanel.tab1.user"));
        username.setText("guest");
        pwd = new JLabel(resourceManager.getString("LoginOrRegistePanel.tab1.pwd"));
        password.setText("guest");
        rememberPassword = new JCheckBox(resourceManager.getString("LoginOrRegistePanel.tab1.rememberPassword"));
        autologin = new JCheckBox(resourceManager.getString("LoginOrRegistePanel.tab1.autologin"));
        login = new JButton(resourceManager.getString("LoginOrRegistePanel.tab1.login"));
        reset = new JButton(resourceManager.getString("LoginOrRegistePanel.tab1.reset"));
        
        login.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        login.setForeground(Color.white);
        reset.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        reset.setForeground(Color.white);
        
        login.addMouseListener(new MouseListener() {
			
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
				if(null == username.getText() || "".equals(username.getText())) {
					JOptionPane.showMessageDialog(null, 
							resourceManager.getString("LoginOrRegistePanel.tab1.username.null"));
					return;
				}
				
				if(null == password.getText() || "".equals(password.getText())) {
		        	JOptionPane.showMessageDialog(null, 
		        			resourceManager.getString("LoginOrRegistePanel.tab1.password.null"));
		        	return;
		        }
				
				// TODO 这里要发起一个POST请求到服务器
				if(null != username.getText() 
						&& "guest".equals(username.getText()) 
						&& null != password.getText() 
						&& "guest".equals(password.getText())) {
		        	// JOptionPane.showMessageDialog(null, "登录成功，欢迎您：" + username.getText());
		        	// 登录成功，根据服务器返回的报文刷新模块列表（权限控制）
					// flashView()为测试使用方法，发布生产时请不要使用
					flashView();
		        	
					// 关闭窗口
					paradisaeidae.loginOrRegisteFrame.dispose();
					
		        } else {
		        	JOptionPane.showMessageDialog(null, 
		        			resourceManager.getString("LoginOrRegistePanel.tab1.username.password.wrong"));
		        }
			}
		});
        
        reset.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				username.setText("");
				password.setText("");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });        	

        //条件
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 2));
        //按钮
        JPanel bt = new JPanel();
        bt.setLayout(new GridLayout(1, 2));
        
        p.add(user);
        p.add(username);
        p.add(pwd);
        p.add(password);
        p.add(rememberPassword);
        p.add(autologin);
        
        bt.add(login);
        bt.add(reset);
        
        tab1Panel.setLayout(new BorderLayout());
        tab1Panel.add(p, BorderLayout.NORTH);
        tab1Panel.add(bt, BorderLayout.SOUTH);
        tab1Panel.setBorder(BorderFactory.createEmptyBorder(30,30,20,30));
        
        String name = resourceManager.getString("LoginOrRegistePanel.tab1.name");        
        tabbedpane.add(name, tab1Panel);

        // modify by zhangmz 2015-05-31 简单实现一个登录方案 end
        
        name = resourceManager.getString("LoginOrRegistePanel.tab2.name");
        // pix = new JLabel();
        JLabel pix = new JLabel();
        tabbedpane.add(name, pix);
    
        tabbedpane.setTabPlacement(JTabbedPane.TOP);
    }

    /**
     * 
     * @Title: flashView 
     * @Description: 动态刷新页面视图
     * @throws 
     * 增加人:张孟志
     * 增加日期:2015-5-26 下午4:01:42
     * 说明： 测试方法，发布时请不要使用
     * 		为了重新加载模块列表，动态刷新页面视图
     */
    public void flashView() {    	
    	flashView(strArry);
    }
    
    /**
     * 
     * @Title: flashView 
     * @Description: 动态刷新页面视图
     * @param strArry String[]
     * @throws 
     * 增加人:张孟志
     * 增加日期:2015-5-26 下午4:01:42
     * 说明：为了重新加载模块列表，动态刷新页面视图
     */
    private void flashView(String[] strArry) {    	
    	flashView(Utilities.arry2list(strArry));
    }
    
    /**
     * 
     * @Title: flashView 
     * @Description: 动态刷新页面视图
     * @param demoClassNamesList List<String>
     * @throws 
     * 增加人:张孟志
     * 增加日期:2015-5-26 下午4:01:42
     * 说明：为了重新加载模块列表，动态刷新页面视图
     */
    private void flashView(List<String> demoClassNamesList) {
    	if(this.paradisaeidae == null){
    		return ;
    	}
    	this.paradisaeidae.flashView(demoClassNamesList);
    }
}

