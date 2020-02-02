package jingubang.aes.pkcs7padding;

import java.security.Security;
import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AES_CBC_PKCS7PaddingDemo {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//原始数据
		String data = "i am data";
	    //密钥
		String sessionKey = "i am strong key ";
	    //向量
		String iv = "i am iv i am iv ";
		
		//用Base64编码
		Base64.Encoder encoder = Base64.getEncoder();
		String baseData = encoder.encodeToString(data.getBytes());
		String baseSessionKey = encoder.encodeToString(sessionKey.getBytes());
		String baseIv = encoder.encodeToString(iv.getBytes());
		
	    //导入支持AES/CBC/PKCS7Padding的Provider
		Security.addProvider(new BouncyCastleProvider());
			
	    //获取加密数据
		String encryptedData = AES_CBC_PKCS7Padding.encrypt(baseData,baseSessionKey,baseIv);
	    //通过加密数据获得原始数据
		String dataReborn = AES_CBC_PKCS7Padding.decrypt(encryptedData,baseSessionKey,baseIv);
			
	    //打印解密出来的原始数据
		System.out.println(dataReborn);
		
		String encryptedData1 ="fy1ryK0yf8fiH5YlGjF0wpCbxPOnfOJrsYbVcdP7DSDRxHvrgy9u+InFBum0OmBSaZ7QPWxmK8KINDVaWFqrAV/lOGBkbQDCfH+pYTTRL5KcNgofHRTZapjWnr4oaBBpsxYFchUUoILpocjuolbxUDcLpRkUqePKdpELHampihROz0RBIe/dih8O3Axu2+K5Fp9c33o8c4PTU0vi5V9Vaz0qfeQzOahsg3+JlDkXbgPRtfizaHkrCEFNmFkG/LQaVv910iVM2IXzIPJAxpa/JUA5pQWuZFAhEotu+fDDdFNxBSTnMLC2dZnfmo1zG1jpKSpoMVE9QaHpA2QTSt8DR9NWlvcl2qPPJcUZOyMNTtdJRT/4JYgjtcX6nc9g6er1hUbWirM3vE4IaK2gDpSvhonHa6oy4LIHwNucssOIAtQt6u60FES3GzgYNMunkSGDcD9eva4rt65T5PGO9NJY5hCxGRi8SwXkLYcFGLXkXZXmvZrr2hGO3LL/w34NQNgvvgrizKwjU6op3UNUCDi3nHMCauKVWXyo5qD1jCsFKzs=";
		String baseIv1 ="pw6omspmd8gIowRu2QICcA==";
		String baseSessionKey1="ba1DGdsllAK3sRaH0HRqmg==";
		String dataReborn1 = AES_CBC_PKCS7Padding.decrypt(encryptedData1,baseSessionKey1,baseIv1);
		System.out.println(dataReborn1);
		
		
	}

}
