package org.zhangmz.ui.paradisaeidae.utilities.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * @ClassName:HttpUtils 
 * @Description:发送HTTP请求的工具类
 * @author:张孟志
 * @date:2015-6-2 下午2:40:43 
 * @version V1.0
 * 说明：发送HTTP请求的工具类
 */
@SuppressWarnings("deprecation")
public class HttpUtils {

	/**
	 * 
	 * @Title: getHttp 
	 * @Description: 发送HTTP POST请求 
	 * @param url  请求地址
	 * @param dc   请求对象
	 * @return
	 * @throws Exception
	 * @throws 
	 * 增加人:张孟志
	 * 增加日期:2015-6-2 下午3:08:48
	 * 说明：发送HTTP POST请求 
	 */
	public static String getHttp(String url, DataCode dc) throws Exception {
		String strRtn = "";
		
		//1 得到浏览器
		HttpClient httpClient = new DefaultHttpClient();
		  
		//2 指定请求方式
		HttpPost httpPost = new HttpPost(url);
  
		//构建请求实体的数据
		List<NameValuePair> parameters = HttpUtils.getNameValuePairList(dc);		
		
		//4 构建实体
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
		  
		//5 把实体数据设置到请求对象
		httpPost.setEntity(entity);
		  
		//6 执行请求
		HttpResponse httpResponse = httpClient.execute(httpPost);
		  
		//7 判断请求是否成功
        strRtn = HttpUtils.getResponse(httpResponse);
  
        //8 释放连接    
        httpPost.releaseConnection();
        
        // System.out.println("服务请求返回内容utf8：");  
        // System.out.println(strRtn);
        
        return strRtn;
	}
	
	/**
	 * 根据对象动态获取变量并封装为HttpMethodParams
	 * @param f
	 * @return
	 */
	public static List<NameValuePair> getNameValuePairList (Object f) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		
		// 获取f对象对应类中的所有属性域 
        Field[] fields = f.getClass().getDeclaredFields(); 
        for(int i = 0 , len = fields.length; i < len; i++) { 
            // 对于每个属性，获取属性名 
            String varName = fields[i].getName(); 
            try { 
                // 获取原来的访问控制权限 
                boolean accessFlag = fields[i].isAccessible(); 
                // 修改访问控制权限 
                fields[i].setAccessible(true); 
                // 获取在对象f中属性fields[i]对应的对象中的变量 
                Object o = fields[i].get(f); 
                
                //设置参数到HttpMethodParams
                //System.out.println("传入的对象中包含一个如下的变量：" + varName +  " = " + o); 
                NameValuePair nvp = new BasicNameValuePair(varName, o.toString());
                parameters.add(nvp);
                
                // 恢复访问控制权限 
                fields[i].setAccessible(accessFlag); 
            } catch (IllegalArgumentException ex) { 
                ex.printStackTrace(); 
            } catch (IllegalAccessException ex) { 
                ex.printStackTrace(); 
            } 
        } 
    
		return parameters;
	}
	
	/**
	 * 获取请求返回的文本
	 * @param httpResponse
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String getResponse (HttpResponse httpResponse) throws Exception {
		String strRtn = "";
		
		//System.out.println("服务请求返回内容：");  
        //System.out.println(httpResponse.toString());  
		if(httpResponse.getStatusLine().getStatusCode() == 200){
			HttpEntity entity2 = httpResponse.getEntity();  
            if (entity2 != null) {              
                //start 读取整个页面内容  
                InputStream is = entity2.getContent();  
                BufferedReader in = new BufferedReader(new InputStreamReader(is));   
                StringBuffer buffer = new StringBuffer();   
                String line = "";  
                while ((line = in.readLine()) != null) {  
                    buffer.append(line);  
                }  
                //end 读取整个页面内容  
                strRtn = buffer.toString();  
            }  
		}
		
		return strRtn;
	}

}
