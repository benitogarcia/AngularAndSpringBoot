package com.spring.boot.angular.crud.config;

//RSAUtils.java
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.HashMap;
import java.util.Map;

/**
 * https://programmerclick.com/article/7940524229/
 * https://codepen.io/ryantriangles/pen/RyPgEW
 * 
 * @author benito
 *
 */
public class RSAUtils {

	private static final Logger LOG = LoggerFactory.getLogger(RSAUtils.class);

	private static final String CHARSET = "UTF-8";
	private static final String RSA_ALGORITHM = "RSA";

	public static Map<String, String> createKeys(int keySize) {
		// Crear un objeto KeyPairGenerator para el algoritmo RSA
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
		}

		// Inicializa el objeto KeyPairGenerator, longitud de la clave
		kpg.initialize(keySize);
		// Genera un par de claves
		KeyPair keyPair = kpg.generateKeyPair();
		// Obtener la clave pública
		Key publicKey = keyPair.getPublic();
		String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
		// Obtenga la clave privada
		Key privateKey = keyPair.getPrivate();
		System.out.println("Formato de clave privada:" + privateKey.getFormat());
		String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
		Map<String, String> keyPairMap = new HashMap<String, String>();
		keyPairMap.put("publicKey", publicKeyStr);
		keyPairMap.put("privateKey", privateKeyStr);
		LOG.info("publicKey:", publicKeyStr);
		LOG.info("privateKey:", privateKeyStr);

		return keyPairMap;
	}

	/**
	 * Obtener clave pública Cadena de clave @param publicKey (codificada en base64)
	 * 
	 * @throws Exception
	 */
	public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Obtenga el objeto de clave pública a través de la instrucción de clave
		// codificada X509
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
		RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
		return key;
	}

	/**
	 * Obtener clave privada
	 * 
	 * @param cadena de clave privateKey (codificada en base64)
	 * @throws Exception
	 */
	public static RSAPrivateKey getPrivateKey(String privateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Obtenga el objeto de clave privada a través de la instrucción de clave
		// codificada PKCS # 8
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
		RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
		return key;
	}

	/**
	 * Cifrado de clave pública
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 */
	public static String publicEncrypt(String data, RSAPublicKey publicKey) {

		Cipher cipher;
		try {
			cipher = Cipher.getInstance(RSA_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
					publicKey.getModulus().bitLength()));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| UnsupportedEncodingException e) {
			LOG.info("Error grave:" + e.getMessage());
		}
		return null;

	}

	/**
	 * Descifrado de clave privada
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 */
	public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
					privateKey.getModulus().bitLength()), CHARSET);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| UnsupportedEncodingException e) {
			LOG.info("Error grave:" + e.getMessage());
		}
		return null;

	}

	/**
	 * Cifrado de clave privada
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 */

	public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
					privateKey.getModulus().bitLength()));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| UnsupportedEncodingException e) {
			LOG.info("Error grave:" + e.getMessage());
		}
		return null;

	}

	/**
	 * Descifrado de clave pública
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 */

	public static String publicDecrypt(String data, RSAPublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
					publicKey.getModulus().bitLength()), CHARSET);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| UnsupportedEncodingException e) {
			LOG.info("Error grave:" + e.getMessage());
		}
		return null;
	}

	private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {

		int maxBlock = 0;
		if (opmode == Cipher.DECRYPT_MODE) {
			maxBlock = keySize / 8;
		} else {
			maxBlock = keySize / 8 - 11;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] buff;
		int i = 0;
		try {
			while (datas.length > offSet) {
				if (datas.length - offSet > maxBlock) {
					buff = cipher.doFinal(datas, offSet, maxBlock);
				} else {
					buff = cipher.doFinal(datas, offSet, datas.length - offSet);
				}
				out.write(buff, 0, buff.length);
				i++;
				offSet = i * maxBlock;
			}
		} catch (Exception e) {

		}
		byte[] resultDatas = out.toByteArray();
		IOUtils.closeQuietly(out);
		return resultDatas;
	}
}
