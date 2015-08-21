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
		// ��ʼ��MessageDigest,SHA��SHA-1�ļ��
		MessageDigest md = MessageDigest.getInstance("SHA");
		// ִ��ժҪ����
		byte[] digest = md.digest(data);
		return new HexBinaryAdapter().marshal(digest);
	}
}
