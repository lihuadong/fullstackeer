/**
 * 包打听全知道-微信H5版本
 * demo
 * MinappTest.java
 * Ver0.0.1
 * 2017年5月6日-下午8:24:27
 *  2017全智道(北京)科技有限公司-版权所有
 * 
 */
package demo;


import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;  
/**
 * 
 * MinappTest
 * 
 * 李华栋
 * 李华栋
 * 2017年5月6日 下午8:24:27
 * 
 * @version 0.0.1
 * 
 */
public class MinappTest {

public static boolean initialized = false;  
	
	/**
	 * AES解密
	 * @param content 密文
	 * @return
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchProviderException 
	 */
	public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
		initialize();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(keyByte, "AES");
			
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化 
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();  
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();  
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}  
	
	public static void initialize(){  
        if (initialized) return;  
        Security.addProvider(new BouncyCastleProvider());  
        initialized = true;  
    }
	//生成iv  
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception{  
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");  
        params.init(new IvParameterSpec(iv));  
        return params;  
    }  

    public static String getUserInfo(String encryptedData,String iv,String sessionKey){
    	
    	    String userInfo = null; 
		try {
			
	        byte[] resultByte = decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
	        if(null != resultByte && resultByte.length > 0){
	            userInfo = new String(resultByte, "UTF-8");
	        }
	    } catch (InvalidAlgorithmParameterException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
		return userInfo;
    }
	
	/**
	 * main(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param args 
	 *void
	 * @exception 
	 * @since  0.0.1
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//{"openId":"oDWH50ET2p9WQvV7XWu6qJk1D3Lc","nickName":"悟空来 |  Arthur李华栋","gender":1,"language":"zh_CN","city":"Haidian","province":"Beijing","country":"CN","avatarUrl":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83er0kFrHHQHHHF6V4OGkACFnBibYVqT2wmjvUZO4gXHYWmsfe8abKZUby7iaurmptS4zU7arYn5B5iajw/0","unionId":"oEoEjwSkoftwsyyicEAJAU4vKMiE","watermark":{"timestamp":1494078442,"appid":"wx86dfbc2c131a5b8f"}}

		String encryptedData ="fy1ryK0yf8fiH5YlGjF0wpCbxPOnfOJrsYbVcdP7DSDRxHvrgy9u+InFBum0OmBSaZ7QPWxmK8KINDVaWFqrAV/lOGBkbQDCfH+pYTTRL5KcNgofHRTZapjWnr4oaBBpsxYFchUUoILpocjuolbxUDcLpRkUqePKdpELHampihROz0RBIe/dih8O3Axu2+K5Fp9c33o8c4PTU0vi5V9Vaz0qfeQzOahsg3+JlDkXbgPRtfizaHkrCEFNmFkG/LQaVv910iVM2IXzIPJAxpa/JUA5pQWuZFAhEotu+fDDdFNxBSTnMLC2dZnfmo1zG1jpKSpoMVE9QaHpA2QTSt8DR9NWlvcl2qPPJcUZOyMNTtdJRT/4JYgjtcX6nc9g6er1hUbWirM3vE4IaK2gDpSvhonHa6oy4LIHwNucssOIAtQt6u60FES3GzgYNMunkSGDcD9eva4rt65T5PGO9NJY5hCxGRi8SwXkLYcFGLXkXZXmvZrr2hGO3LL/w34NQNgvvgrizKwjU6op3UNUCDi3nHMCauKVWXyo5qD1jCsFKzs=";
		String iv ="pw6omspmd8gIowRu2QICcA==";
		String sessionKey="ba1DGdsllAK3sRaH0HRqmg==";
		
		String str   = getUserInfo(encryptedData,iv,sessionKey);
		System.out.println(str);
	}

}
