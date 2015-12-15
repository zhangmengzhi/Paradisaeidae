package org.zhangmz.ui.paradisaeidae.demos.pushclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.zhangmz.ui.paradisaeidae.demos.ResourceManager;
import org.zhangmz.ui.paradisaeidae.demos.tipwindow.TipWindowWorker;
import org.zhangmz.ui.paradisaeidae.utilities.LocalInformation;
import org.zhangmz.ui.paradisaeidae.DemoProperties;
import com.string.widget.util.RandomUtils;
import com.string.widget.util.ValueWidget;
import com.swing.component.AssistPopupTextField;
import com.swing.component.AssistPopupTextPane;
import com.swing.component.ComponentUtil;
import com.swing.dialog.DialogUtil;
import com.swing.messagebox.GUIUtil23;

/**
 * PushClient Demo
 *
 * @author 张孟志
 * @version V1.0
 * 日期：2015-05-24
 * 说明：演示应用如何接收服务器推送的消息
 * 		受到Android消息推送的启发，
 * 		使用MQTT协议走JMS
 * 
 * 修改人：张孟志
 * 修改日期：2015-05-26
 * 修改说明：将ClientID更改为MAC地址
 * 
 */
@DemoProperties(
        value = "PushClient Demo",
        category = "其它",
        description = "演示应用如何接收服务器推送的消息。",
        sourceFiles = {
                "org/zhangmz/ui/paradisaeidae/demos/pushclient/resources/PushClientDemo.properties",
                "org/zhangmz/ui/paradisaeidae/demos/pushclient/resources/images/PushClientDemo.png"
                }
)
public class PushClientDemo extends JPanel {
    private final ResourceManager resourceManager = new ResourceManager(this.getClass());
    
    /***
	 * activeMQ 的 ip
	 */
	private AssistPopupTextField ipTextField;
	/***
	 * activeMQ的端口
	 */
	private AssistPopupTextField portTextField;
	/**
	 * 客户端的clientId
	 */
	private AssistPopupTextField clientIdTextField;
	/***
	 * 订阅的主题
	 */
	private AssistPopupTextField topicTextField;
	/***
	 * 是否清空activeMQ的session
	 */
	private AssistPopupTextField cleanSessionTextField;
	/***
	 * 和activeMQ建立连接
	 */
	private JButton startButton;
	private MqttClient mqttClient;
	private AssistPopupTextPane resultTextPane;
	
	/***
	 * 断开与activeMQ的连接
	 */
	private JButton stopButton;
	private static SimpleAttributeSet HTML_RED = new SimpleAttributeSet();
	
	static {
		 StyleConstants.setForeground(HTML_RED, Color.red);
		 StyleConstants.setBold(HTML_RED, true);
		 StyleConstants.setFontSize(HTML_RED, 26);
	}	

	private JLabel lblUsername;
	private JLabel lblPassword;
	private AssistPopupTextField usernameTextField;
	private AssistPopupTextField passwordTextField;

	private JScrollPane scrollPane;
	
	private boolean isPrintException=true;    

	// add by zhangmz 2015-06-02 弹窗
	private TipWindowWorker tipWindowWorker;
	
    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(PushClientDemo.class.getAnnotation(DemoProperties.class).value());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PushClientDemo());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public PushClientDemo() {
        setLayout(new BorderLayout());

        initUI();
        
    }

    private void initUI() {
    	// 定义全局的布局
    	GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gbl_contentPane);
		
		// 加入服务器IP一行
		JLabel lblip = new JLabel(resourceManager.getString("PushClientDemo.serverip.text"));
		GridBagConstraints gbc_lblip = new GridBagConstraints();
		gbc_lblip.insets = new Insets(0, 0, 5, 5);
		gbc_lblip.gridx = 0;
		gbc_lblip.gridy = 0;
		add(lblip, gbc_lblip);
		
		ipTextField = new AssistPopupTextField();
		ipTextField.setText(resourceManager.getString("PushClientDemo.serverip.content"));
		GridBagConstraints gbc_ipTextField = new GridBagConstraints();
		gbc_ipTextField.insets = new Insets(0, 0, 5, 0);
		gbc_ipTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ipTextField.gridx = 2;
		gbc_ipTextField.gridy = 0;
		add(ipTextField, gbc_ipTextField);
		ipTextField.setColumns(10);
		
		JLabel label = new JLabel(resourceManager.getString("PushClientDemo.port.text"));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		add(label, gbc_label);
		
		portTextField = new AssistPopupTextField();
		portTextField.setText(resourceManager.getString("PushClientDemo.port.content"));
		portTextField.setEditable(false);
		
		//双击变为可以编辑
		portTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (!portTextField.isEditable()) {
						portTextField.setEditable(true);
						DialogUtil.focusSelectAllTF(portTextField);
					}
				}
				super.mouseClicked(e);
			}

		});
		GridBagConstraints gbc_portTextField = new GridBagConstraints();
		gbc_portTextField.insets = new Insets(0, 0, 5, 0);
		gbc_portTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_portTextField.gridx = 2;
		gbc_portTextField.gridy = 1;
		add(portTextField, gbc_portTextField);
		portTextField.setColumns(10);
		
		lblUsername = new JLabel(resourceManager.getString("PushClientDemo.username.text"));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 2;
		add(lblUsername, gbc_lblUsername);
		
		usernameTextField = new AssistPopupTextField(resourceManager.getString("PushClientDemo.username.content"));
		GridBagConstraints gbc_usernameTextField = new GridBagConstraints();
		gbc_usernameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_usernameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_usernameTextField.gridx = 2;
		gbc_usernameTextField.gridy = 2;
		add(usernameTextField, gbc_usernameTextField);
		usernameTextField.setColumns(10);
		
		lblPassword = new JLabel(resourceManager.getString("PushClientDemo.password.text"));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		add(lblPassword, gbc_lblPassword);
		
		passwordTextField = new AssistPopupTextField(resourceManager.getString("PushClientDemo.password.content"));
		GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
		gbc_passwordTextField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordTextField.gridx = 2;
		gbc_passwordTextField.gridy = 3;
		add(passwordTextField, gbc_passwordTextField);
		passwordTextField.setColumns(10);
		
		JLabel lblid = new JLabel(resourceManager.getString("PushClientDemo.clientid.text"));
		GridBagConstraints gbc_lblid = new GridBagConstraints();
		gbc_lblid.insets = new Insets(0, 0, 5, 5);
		gbc_lblid.gridx = 0;
		gbc_lblid.gridy = 4;
		add(lblid, gbc_lblid);
		
		// 随机产生客户端ID
		clientIdTextField = new AssistPopupTextField();
		
		// modify by zhangmz 2015-05-26 将ClientID更改为MAC地址 begin
		// clientIdTextField.setText("Paradisaeidae_"+RandomUtils.getRandomStr(10));
		String clientIdText = "";
		try {
			clientIdText = LocalInformation.getLocalMac(false);
		} catch (Exception e) {
			e.printStackTrace();
			clientIdText = "Paradisaeidae_"+RandomUtils.getRandomStr(12);
		}
		clientIdTextField.setText(clientIdText);
		// modify by zhangmz 2015-05-26 将ClientID更改为MAC地址 end
		
		GridBagConstraints gbc_clientIdTextField = new GridBagConstraints();
		gbc_clientIdTextField.insets = new Insets(0, 0, 5, 0);
		gbc_clientIdTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_clientIdTextField.gridx = 2;
		gbc_clientIdTextField.gridy = 4;
		add(clientIdTextField, gbc_clientIdTextField);
		clientIdTextField.setColumns(10);
		
		JLabel lblTopic = new JLabel(resourceManager.getString("PushClientDemo.topic.text"));
		GridBagConstraints gbc_lblTopic = new GridBagConstraints();
		gbc_lblTopic.insets = new Insets(0, 0, 5, 5);
		gbc_lblTopic.gridx = 0;
		gbc_lblTopic.gridy = 5;
		add(lblTopic, gbc_lblTopic);
		
		topicTextField = new AssistPopupTextField(resourceManager.getString("PushClientDemo.topic.content"));
		GridBagConstraints gbc_topicTextField = new GridBagConstraints();
		gbc_topicTextField.insets = new Insets(0, 0, 5, 0);
		gbc_topicTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_topicTextField.gridx = 2;
		gbc_topicTextField.gridy = 5;
		add(topicTextField, gbc_topicTextField);
		topicTextField.setColumns(10);
		
		JLabel label_1 = new JLabel(resourceManager.getString("PushClientDemo.iscleansession.text"));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 6;
		add(label_1, gbc_label_1);
		
		cleanSessionTextField = new AssistPopupTextField();
		cleanSessionTextField.setText(resourceManager.getString("PushClientDemo.iscleansession.content"));
		cleanSessionTextField.setEditable(false);
		//双击变为可以编辑
		cleanSessionTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (!cleanSessionTextField.isEditable()) {
						cleanSessionTextField.setEditable(true);
						DialogUtil.focusSelectAllTF(cleanSessionTextField);
					}
				}
				super.mouseClicked(e);
			}

		});
		GridBagConstraints gbc_cleanSessionTextField = new GridBagConstraints();
		gbc_cleanSessionTextField.insets = new Insets(0, 0, 5, 0);
		gbc_cleanSessionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_cleanSessionTextField.gridx = 2;
		gbc_cleanSessionTextField.gridy = 6;
		add(cleanSessionTextField, gbc_cleanSessionTextField);
		cleanSessionTextField.setColumns(10);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 7;
		add(panel, gbc_panel);
		
		startButton = new JButton(resourceManager.getString("PushClientDemo.start.text"));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!validate22()){
					return;
				}
				new Thread(new Runnable() {
					@Override
					public void run() {
						stopButton.setEnabled(true);
						
						String BROKER_URL="tcp://"+ ipTextField.getText().trim()+":"+portTextField.getText().trim();
						ComponentUtil.appendResult(resultTextPane, "BROKER_URL:"+BROKER_URL, false);
						String clientId=clientIdTextField.getText()/*+client_id_index++*/;
						String TOPIC=topicTextField.getText();
						
						ComponentUtil.appendResult(resultTextPane, "clientId:"+clientId, false);
						
						boolean isSuccess=connect(BROKER_URL.trim(), clientId.trim(), TOPIC,Boolean.parseBoolean(cleanSessionTextField.getText()));
						if(isSuccess){
							startButton.setEnabled(false);
						}
						
					}
				}).start();
			}
		});
		panel.add(startButton);
		
		stopButton=new JButton(resourceManager.getString("PushClientDemo.stop.text"));
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopButton.setEnabled(false);
				stop();
				startButton.setEnabled(true);
			}
		});
		panel.add(stopButton);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 9;
		add(scrollPane, gbc_scrollPane);
		
		//执行结果显示窗口
		resultTextPane = new AssistPopupTextPane();
		resultTextPane.setContentType("text/html; charset=UTF-8");
		resultTextPane.setEditable(false);
		DefaultCaret caret = (DefaultCaret)resultTextPane.getCaret();
    	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		scrollPane.setViewportView(resultTextPane);
    }
    
    /***
	 * 接收到推送消息
	 * @param message
	 */
	public void receiveMessage(String message){
		// TODO 推送消息处理
		ComponentUtil.appendResult(resultTextPane, "receive time:"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 
								false);
		ComponentUtil.appendResult( resultTextPane, message, PushClientDemo.HTML_RED , true);
		//保证文本域始终保持在底部
    	JScrollBar vertical = scrollPane.getVerticalScrollBar();
    	vertical.setValue( vertical.getMaximum() +200);
    	
    	// add by zhangmz 2015-06-02 增加一个弹窗 begin
		// 在关闭about us的时候弹窗，流氓功能，不建议使用
    	// System.out.println("流氓弹窗");
		Map<String, String> fm = new HashMap<String, String>();
    	fm.put("title", resourceManager.getString("PushClientDemo.tipwindow.title"));
    	fm.put("instruction", resourceManager.getString("PushClientDemo.tipwindow.instruction"));
    	// 假设消息为逗号分隔
    	fm.put("feature", message.replaceAll(",", "\n"));
    	fm.put("release", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    	// 需要一个超链接，从返回的报文中获取，这里写固定值www.baidu.com
    	fm.put("uri", "http://www.baidu.com");
    	
    	tipWindowWorker = new TipWindowWorker(fm);
    	tipWindowWorker.versionUtil();
    	// add by zhangmz 2015-06-02 增加一个弹窗 end
	}

	/**
	 * 断开连接
	 */
	private void stop(){
		if(mqttClient!=null && mqttClient.isConnected()){
			try {
				mqttClient.disconnect();
				mqttClient=null;
				String message = resourceManager.getString("PushClientDemo.close.connection.text");
				System.out.println(message);
				ComponentUtil.appendResult(resultTextPane,message , true);
			} catch (MqttException e) {
				e.printStackTrace();
			}
			
		}
	}
	/***
	 * 客户端和activeMQ服务器建立连接
	 * @param BROKER_URL
	 * @param clientId : 用于标识客户端,相当于ios中的device token
	 * @param TOPIC
	 * @param isCleanSession :false--可以接受离线消息;
	 * @return 是否启动成功 
	 */
	private boolean connect(String BROKER_URL,String clientId,String TOPIC,boolean isCleanSession){
		try {
			ComponentUtil.appendResult(resultTextPane, 
					"connect time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 
					true);
			
			// modify by zhangmz 2015-05-23 MqttClient没有这个构造函数
            // mqttClient = new MqttClient(BROKER_URL, clientId, new MemoryPersistence());
            mqttClient = new MqttClient(BROKER_URL, clientId);
            
            MqttConnectOptions options= new MqttConnectOptions();
            options.setCleanSession(isCleanSession);//mqtt receive offline message
            ComponentUtil.appendResult(resultTextPane, "isCleanSession:"+isCleanSession, true);
            options.setKeepAliveInterval(30);
            String username=usernameTextField.getText();
            String password=passwordTextField.getText();
            if(ValueWidget.isNullOrEmpty(username)){
            	username=null;
            }
            if(ValueWidget.isNullOrEmpty(password)){
            	password=null;
            }else{
            	options.setPassword(password.toCharArray());
            }
            options.setUserName(username);
            
            //推送回调类,在此类中处理消息,用于消息监听
            mqttClient.setCallback(new PushClientCallBack(PushClientDemo.this));
            boolean isSuccess=false;
            try {
				mqttClient.connect(options);//CLIENT ID CAN NOT BE SAME
				isSuccess=true;
			} catch (Exception e) {
				if(isPrintException){
					e.printStackTrace();
				}
			}
            if(!isSuccess){
            	String message = resourceManager.getString("PushClientDemo.connection.error.text");
            	ComponentUtil.appendResult(resultTextPane, message, true);
            	GUIUtil23.warningDialog(message);
            	return false;
            }else{
            //Subscribe to topics 
	            mqttClient.subscribe(new String[]{TOPIC,clientId});
	           
	            System.out.println("topic:"+TOPIC+",  "+(clientId));
	            ComponentUtil.appendResult(resultTextPane, "TOPIC:"+TOPIC+",  "+(clientId), true);
            }

        } catch (MqttException e) {
        	if(isPrintException){
            e.printStackTrace();}
            GUIUtil23.errorDialog(e.getMessage());
            return false;
        }
		return true;
	}

	private boolean validate22(){
		if(!DialogUtil.verifyTFEmpty(ipTextField, resourceManager.getString("PushClientDemo.serverip.text"))){
			return false;
		}
		if(!ValueWidget.isValidV4IP(ipTextField.getText())){
			GUIUtil23.warningDialog(resourceManager.getString("PushClientDemo.ipcheck.error.text"));
			return false;
		}
		return true;
	}
}
