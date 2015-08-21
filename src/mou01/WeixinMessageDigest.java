package mou01;

import java.security.*;
import java.util.Arrays;

/***
 * ΢����Ϣ�ӿ���֤tokenժҪ��
 * 
 * ���ժҪ��ʵ��Ϊ������У��һ��ǩ���Ƿ�Ϸ�����������
 * 
 * <pre>
 * WeixinMessageDigest wxDigest = WeixinMessageDigest.getInstance();
 * boolean bValid = wxDigest.validate(signature, timestamp, nonce);
 * </pre>
 * 
 * 
 * @author liguocai
 */
public final class WeixinMessageDigest {

	/**
	 * ����������
	 * 
	 * @author liguocai
	 * 
	 */
	private static class SingletonHolder {
		static final WeixinMessageDigest INSTANCE = new WeixinMessageDigest();
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public static WeixinMessageDigest getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private MessageDigest digest;

	private WeixinMessageDigest() {
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			throw new InternalError("init MessageDigest error:"
					+ e.getMessage());
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

	private String encrypt(String strSrc) {
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		digest.update(bt);
		strDes = byte2hex(digest.digest());
		return strDes;
	}

	/**
	 * У�������ǩ���Ƿ�Ϸ�
	 * 
	 * ����/У�����̣� 1. ��token��timestamp��nonce�������������ֵ������� 2. �����������ַ���ƴ�ӳ�һ���ַ�������sha1����
	 * 3. �����߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public boolean validate(String signature, String timestamp, String nonce) {
		// 1. ��token��timestamp��nonce�������������ֵ�������
		String token = getToken();
		String[] arrTmp = { token, timestamp, nonce };
		Arrays.sort(arrTmp);
		StringBuffer sb = new StringBuffer();
		// 2.�����������ַ���ƴ�ӳ�һ���ַ�������sha1����
		for (int i = 0; i < arrTmp.length; i++) {
			sb.append(arrTmp[i]);
		}
		String expectedSignature = encrypt(sb.toString());
		// 3. �����߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
		if (expectedSignature.equals(signature)) {
			return true;
		}
		return false;
	}

	private String getToken() {
		return "111111";
	}

	public static void main(String[] args) {

		String signature = "f86944503c10e7caefe35d6bc19a67e6e8d0e564";// ������Ҫ��֤��ǩ��
		String timestamp = "1371608072";// ʱ���
		String nonce = "ewqwewq";// �����

		WeixinMessageDigest wxDigest = WeixinMessageDigest.getInstance();
		boolean bValid = wxDigest.validate(signature, timestamp, nonce);
		if (bValid) {
			System.out.println("token ��֤�ɹ�!");
		} else {
			System.out.println("token ��֤ʧ��!");
		}
	}

}
