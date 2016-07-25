package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * ������֤signture,��Ϊ������
 * 
 * @author Runaway
 *
 */
public class SignUtil {
	//��֤sign
		public static boolean validSign(String signature, String tocken, String timestamp, String nonce) {
			String[] paramArr = new String[] { tocken, timestamp, nonce };
			//��token��timestamp��nonce �����ֵ����򣬲�ƴ�ӳ��ַ���
			Arrays.sort(paramArr);
			StringBuilder sb = new StringBuilder(paramArr[0]);
			sb.append(paramArr[1]).append(paramArr[2]);
			String ciphertext = null;
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				byte[] digest = md.digest(sb.toString().getBytes());// �ԽӺ���ַ�������sha1����
				ciphertext = byteToStr(digest);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			// ��sha1���ܺ���ַ�����  signature ���бȽ�
			return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
		}

		private static String byteToStr(byte[] byteArray) {
			String rst = "";
			for (int i = 0; i < byteArray.length; i++) {
				rst += byteToHex(byteArray[i]);
			}
			return rst;
		}
		
		private static String byteToHex(byte b) {
			char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
			char[] tempArr = new char[2];
			tempArr[0] = Digit[(b >>> 4) & 0X0F];
			tempArr[1] = Digit[b & 0X0F];
			String s = new String(tempArr);
			return s;
		}
}
