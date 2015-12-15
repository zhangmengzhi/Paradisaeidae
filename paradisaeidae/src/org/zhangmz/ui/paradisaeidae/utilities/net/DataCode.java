package org.zhangmz.ui.paradisaeidae.utilities.net;

import java.io.File;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

/**
 * 
 * @ClassName:DataCode 
 * @Description:数据编码对象
 * @author:张孟志
 * @date:2015-6-2 下午2:46:52 
 * @version V1.0
 * 说明：客户端发送接口传输的编码对象
 */
public class DataCode {

	private String _service_ =  "PCCLIENT"; //PC客户端服务
	private String _version_ = "V1.0";      //版本
	private String _clientid_ = "";         //ClientID
	private String _userid_ = "";           //用户
	private String _token_ = "";            //认证码
	private String _code_ = "";             //系统接口服务代码
	private String _data_ ;                 //上报数据  Base64加密，从文件读

	public DataCode(String clientid, String userid, 
					String token, String code, 
					String data) 
							throws Exception {
		this(clientid, userid, token, code, data, false);		
	}
	
	/**
	 * 
	 * <p>Title: 构造请求对象</p> 
	 * <p>Description: 构造一个一般的请求对象</p> 
	 * @param clientid
	 * @param userid
	 * @param token
	 * @param code
	 * @param data
	 * @param isEncode
	 * @throws Exception
	 */
	public DataCode(String clientid, String userid, 
			String token, String code, 
			String data, boolean isEncode) 
					throws Exception {
		super();
		this._clientid_ = clientid;
		this._userid_ = userid;
		this._token_ = token;
		this._code_ = code;		
		
		//做Base64加密
		if(isEncode){
			this._data_ = data;
		} else {
			this._data_ = new String(new Base64().encode(data.getBytes("UTF-8")), "UTF-8");
		}
	
	}
	
	/**
	 * 
	 * <p>Title: 构造请求对象</p> 
	 * <p>Description: 构造一个请求对象，测试用</p> 
	 * @param clientid
	 * @param userid
	 * @param code
	 * @param filepath
	 * @throws Exception
	 */
	public DataCode(String clientid, String userid, String code, String filepath) throws Exception {
		super();
		this._clientid_ = clientid;
		this._userid_ = userid;
		this._code_ = code;
		
		//读取_data_数据文件
		File file = new File(filepath);
		if(!file.exists()){
			throw new Exception(filepath + "不存在");
		}
		
		String strFile = FileUtils.readFileToString(file);
		// System.out.println("读取到文件" + filepath + ":");
		// System.out.println(strFile);
		
		//做Base64加密
		this._data_ = new String(new Base64().encode(strFile.getBytes("UTF-8")), "UTF-8");
		// System.out.println(this._data_);
		
	}
	
	public String get_service_() {
		return _service_;
	}
	public void set_service_(String _service_) {
		this._service_ = _service_;
	}
	public String get_token_() {
		return _token_;
	}
	public void set_token_(String _token_) {
		this._token_ = _token_;
	}
	public String get_version_() {
		return _version_;
	}
	public void set_version_(String _version_) {
		this._version_ = _version_;
	}
	public String get_clientid_() {
		return _clientid_;
	}
	public void set_clientid_(String _clientid_) {
		this._clientid_ = _clientid_;
	}
	public String get_userid_() {
		return _userid_;
	}
	public void set_userid_(String _userid_) {
		this._userid_ = _userid_;
	}
	public String get_code_() {
		return _code_;
	}
	public void set_code_(String _code_) {
		this._code_ = _code_;
	}
	public String get_data_() {
		return _data_;
	}
	public void set_data_(String _data_) {
		this._data_ = _data_;
	}
	
}
