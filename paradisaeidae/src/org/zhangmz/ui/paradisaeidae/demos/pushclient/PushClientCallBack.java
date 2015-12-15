package org.zhangmz.ui.paradisaeidae.demos.pushclient;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.zhangmz.ui.paradisaeidae.demos.pushclient.PushClientDemo;

public class PushClientCallBack implements MqttCallback {
	private PushClientDemo mqttSwing;	
	
	public PushClientCallBack(PushClientDemo mqttSwing) {
		super();
		this.mqttSwing = mqttSwing;
	}

	@Override
	public void connectionLost(Throwable cause) {
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String arg0, MqttMessage message) throws Exception {
		System.out.println("messageArrived...." 
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		String messageStr=StringEscapeUtils.unescapeHtml(new String(message.getPayload()));
		System.out.println("message:"+messageStr);
		this.mqttSwing.receiveMessage(messageStr);		
	}

}
