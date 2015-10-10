package com.sapo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter
{
	public String MD5(String password)
	{
		/*try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(password.getBytes("UTF-8"));
			String md5 = "";
			for (int i = 0; i < hash.length; i++)
			{
					md5 += Integer.toHexString((hash[i] >> 4) & 0xf);
					md5 += Integer.toHexString(hash[i] & 0xf);
			
			} 
			return md5;
		}
		catch(NoSuchAlgorithmException ex)
		{
			return "";
		}
		catch(UnsupportedEncodingException ex)
		{
			return "";
		}*/
		
		//EN ESTA CLASE HABIA OTRA CODIGO DE ENCRIPTACION PERO COPIE EL DE VIC
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] hashedBytes = digest.digest((password).getBytes());
			
			StringBuffer stringBuffer = new StringBuffer();
	        for (int i = 0; i < hashedBytes.length; i++) {
	            stringBuffer.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16)
	                    .substring(1));
	        }
			String md5Hash = stringBuffer.toString();
	
			return md5Hash;
		}
		catch(NoSuchAlgorithmException ex)
		{
			return "";
		}
				
	}
}
