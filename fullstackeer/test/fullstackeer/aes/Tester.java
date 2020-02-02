package fullstackeer.aes;

import jingubang.aes.AesException;
import jingubang.aes.WXBizMsgCrypt;

public class Tester {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt("QDG6eK",
				"jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C", "wx5823bf96d3bd56c7");
		String verifyMsgSig = "5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3";
		String timeStamp = "1409659589";
		String nonce = "263014780";
		String echoStr = "P9nAzCzyDtyTWESHep1vC5X9xho/qYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp+4RPcs8TgAE7OaBO+FZXvnaqQ==";
		
		wxcpt.verifyUrl(verifyMsgSig, timeStamp, nonce, echoStr);
	}

}
