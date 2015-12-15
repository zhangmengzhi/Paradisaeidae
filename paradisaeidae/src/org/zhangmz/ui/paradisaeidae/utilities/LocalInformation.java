package org.zhangmz.ui.paradisaeidae.utilities;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 
 * @ClassName:LocalInformation 
 * @Description:本地资源信息
 * @author:张孟志
 * @date:2015-5-26 下午2:02:59 
 * @version V1.0
 * 说明：提供工具方法获取本地资源信息
 */
public class LocalInformation {

	public static void main(String[] args) throws UnknownHostException, SocketException {
		
		// 得到IP，输出ZhangMZ/10.10.10.1
		InetAddress ia = getLocalIP();
		System.out.println(ia);
		
		// 00-50-56-C0-00-01
		String mac4bar = getLocalMac();
		System.out.println(mac4bar);
		
		// 005056C00001
		String mac4nobar = getLocalMac(false);
		System.out.println(mac4nobar);
	}
	
	/**
	 * 
	 * @Title: getLocalIP 
	 * @Description: 获取本地IP地址
	 * @return
	 * @throws UnknownHostException
	 * @throws 
	 * 增加人:张孟志
	 * 增加日期:2015-5-26 下午2:09:22
	 * 说明：获取本地IP地址
	 */
	public static InetAddress getLocalIP() throws UnknownHostException {
		// 得到IP，输出PC-201309011313/10.10.10.1
		return InetAddress.getLocalHost();
	}
	
	/**
	 * 
	 * @Title: getLocalMac 
	 * @Description: 获取本机MAC地址 
	 * @throws SocketException
	 * @throws UnknownHostException 
	 * 增加人:张孟志
	 * 增加日期:2015-5-26 下午2:04:50
	 * 说明：获取本机MAC地址  有横杆
	 */
	public static String getLocalMac() throws SocketException, UnknownHostException {
		return getLocalMac(true);
	}
	
	/**
	 * 
	 * @Title: getLocalMac 
	 * @Description: 获取本机MAC地址 
	 * @param forBar  MAC地址是否带横杆
	 * @throws SocketException
	 * @throws UnknownHostException 
	 * @throws 
	 * 增加人:张孟志
	 * 增加日期:2015-5-26 下午2:04:50
	 * 说明：获取本机MAC地址  参数决定是否有横杆
	 */
	public static String getLocalMac(boolean forBar) throws SocketException, UnknownHostException {
		
		// UnknownHostException
		InetAddress ia = getLocalIP();
		
		// 获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(forBar && i!=0) {
				sb.append("-");
			}
			// 字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		return sb.toString().toUpperCase();
	}
}
