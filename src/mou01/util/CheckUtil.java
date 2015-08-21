package mou01.util;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class CheckUtil {

	private static final String token = "moumou";

	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {

		String[] arr = new String[] { token, timestamp, signature };
		Arrays.sort(arr);

		StringBuffer sb = new StringBuffer();
		for (String str : arr) {
			sb.append(str);
		}

		String content = sb.toString();

		String shastr;
		try {
			shastr = encodeSHA(content.getBytes());
			return shastr.equals(signature);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/****
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encodeSHA(byte[] data) throws Exception {
		// 初始化MessageDigest,SHA即SHA-1的简称
		MessageDigest md = MessageDigest.getInstance("SHA");
		// 执行摘要方法
		byte[] digest = md.digest(data);
		return new HexBinaryAdapter().marshal(digest);
	}
}
