package cn.taijiweibu.hdl.util;

import java.util.Random;

import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class ToolKit {

	public static final Log log =Logs.get();
	
	public static String passwordEncode(String password, String salt){
		String string = salt + password + salt + password.substring(5);
		return Lang.digest("SHA-512", string);
	}
	
	public static String saltEcode(int length){
		String stringBank = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		
		for (int i = 0; i < length; i++){
			int index = random.nextInt(62);
			stringBuffer.append(stringBank.charAt(index));
		}
		return stringBuffer.toString();
	}
	
	public static String saltEcode(){
		String stringBank = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		
		for (int i = 0; i < 6; i++){
			int index = random.nextInt(62);
			stringBuffer.append(stringBank.charAt(index));
		}
		return stringBuffer.toString();
	}
	
}
