/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.maxkey.client.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.codec.binary.Base64;


/**
 * Base64 Utils
 *
 * @author hugoDD
 *
 */
public final class Base64Utils {



	public static String encodeBase64(byte[] simple){
		String cipher=BytesUtils.bytes2String(Base64.encodeBase64(simple));
		return cipher;
	}

	public static byte[] decoderBase64(String cipher){
		byte[] simple=Base64.decodeBase64(cipher);
		return simple;
	}

	public static String  encode(String simple){
		return  encodeBase64(simple.getBytes());
	}

	public static String  encoder(byte[] simple){
		return  encodeBase64(simple);
	}

	public static String  decode(String cipher){
		return BytesUtils.bytes2String(decoderBase64(cipher));
	}

	public static byte[]  decoder(String cipher){
		return decoderBase64(cipher);
	}


	 /**
	  *encode file to  base64 Code String
	  * @param fileName file path
	  * @return  *
	  * @throws Exception
	  */

	 public static String fileToBase64(String fileName) throws Exception {
	  File file = new File(fileName);;
	  FileInputStream inputFile = new FileInputStream(file);
	  byte[] buffer = new byte[(int) file.length()];
	  inputFile.read(buffer);
	  inputFile.close();
	  return encodeBase64(buffer);

	 }

	 /**
	  * base64 Code decode String save to targetPath
	  * @param base64Code
	  * @param targetPath
	  * @throws Exception
	  */

	 public static void decodeBase64ToFile(String base64Code, String targetPath)
	   throws Exception {
	  byte[] buffer = decoderBase64(base64Code);
	  FileOutputStream out = new FileOutputStream(targetPath);
	  out.write(buffer);
	  out.close();

	 }

	 /**
	  * base64 code save to file
	  * @param base64Code
	  * @param targetPath
	  * @throws Exception
	  */

	 public static void base64ToFile(String base64Code, String targetPath)
	   throws Exception {
	  byte[] buffer = base64Code.getBytes();
	  FileOutputStream out = new FileOutputStream(targetPath);
	  out.write(buffer);
	  out.close();
	 }

		public static String base64UrlEncode(byte[] simple) {
			String s = new String(Base64.encodeBase64(simple)); // Regular base64
																// encoder
			s = s.split("=")[0]; // Remove any trailing '='s
			s = s.replace('+', '-'); // 62nd char of encoding
			s = s.replace('/', '_'); // 63rd char of encoding
			return s;
		}

		public static byte[] base64UrlDecode(String cipher) {
			String s = cipher;
			s = s.replace('-', '+'); // 62nd char of encoding
			s = s.replace('_', '/'); // 63rd char of encoding
			switch (s.length() % 4) { // Pad with trailing '='s
				case 0:
					break; // No pad chars in this case
				case 2:
					s += "==";
					break; // Two pad chars
				case 3:
					s += "=";
					break; // One pad char
				default:
					System.err.println("Illegal base64url String!");
			}
			return Base64.decodeBase64(s); // Standard base64 decoder
		}
}
