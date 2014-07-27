package com.karthikb351.vitinfo2.objects;

/**
 * Created by saurabh on 7/26/14.
 */

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Usage:
 * <pre>
 * String crypto = SimpleCrypto.encrypt(masterpassword, cleartext)
 * ...
 * String cleartext = SimpleCrypto.decrypt(masterpassword, crypto)
 * </pre>
 * @author ferenc.hechler
 */
public class SimpleCrypto {

    public static String encrypt(String seed, String cleartext) throws Exception {
        DESKeySpec keySpec = new DESKeySpec(seed.getBytes("UTF8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeToString(cipher.doFinal(cleartext.getBytes("UTF8")), Base64.DEFAULT);
    }

    public static String decrypt(String seed, String encrypted) throws Exception {
        DESKeySpec keySpec = new DESKeySpec(seed.getBytes("UTF8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance("DES");// cipher is not thread safe
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encrypedPwdBytes = Base64.decode(encrypted, Base64.DEFAULT);

        return new String(cipher.doFinal(encrypedPwdBytes));
    }

}
