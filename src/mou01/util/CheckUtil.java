package mou01.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CheckUtil {

	private static final String token = "moumou";

	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		try {

			for (String str : arr) {
				System.out.print(str);
			}
			Arrays.sort(arr);

			StringBuffer sb = new StringBuffer();
			for (String str : arr) {
				sb.append(str);
			}

			String content = sb.toString();

			String shastr = encrypt(content);
			return shastr.equals(signature);
		} catch (Exception e) {
			e.printStackTrace();

			for (String str : arr) {
				System.out.print(str);
			}
			return false;
		}
	}

	/**
	 * ���ֽ�����ת����16�����ַ���
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder sbDes = new StringBuilder();
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				sbDes.append("0");
			}
			sbDes.append(tmp);
		}
		return sbDes.toString();
	}

	private static String encrypt(String strSrc)
			throws NoSuchAlgorithmException {

		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		byte[] bt = strSrc.getBytes();
		digest.update(bt);
		String strDes = byte2hex(digest.digest());
		return strDes;
	}

	public static void main(String[] args) {

		String signature = "f86944503c10e7caefe35d6bc19a67e6e8d0e564";// ������Ҫ��֤��ǩ��
		String timestamp = "1371608072";// ʱ���
		String nonce = "1372170854";// �����

		boolean bValid = CheckUtil.checkSignature(signature, timestamp, nonce);
		if (bValid) {
			System.out.println("token ��֤�ɹ�!");
		} else {
			System.out.println("token ��֤ʧ��!");
		}
	}
}
