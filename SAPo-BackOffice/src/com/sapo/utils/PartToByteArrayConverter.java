package com.sapo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

public class PartToByteArrayConverter {

	public static byte[] toByteArray(Part img) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {

			InputStream is = img.getInputStream();
			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
			is.close();

		} catch (IOException e) {
			// Error handling
		}
		return buffer.toByteArray();
	}
}
