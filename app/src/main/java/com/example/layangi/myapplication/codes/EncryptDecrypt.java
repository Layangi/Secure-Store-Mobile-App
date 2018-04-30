package com.example.layangi.myapplication.codes;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Lakshika on 26/03/2018.
 */

public class EncryptDecrypt {

    String AES = "AES";
    KeyStore keyStore;

    public String decrypt(String outputString, String alise) throws Exception {

        SecretKeySpec key = generateKey(alise);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedVal = Base64.decode(outputString,Base64.DEFAULT);
        byte[] decVal = c.doFinal(decodedVal);
        String decryptVal = new String(decVal);
        return  decryptVal;

    }

    public String encrypt(String text, String alise) throws Exception {
//        keyStore = new KeyStore(alise);
//        SecretKeySpec key = keyStore.getSecretKey();
        SecretKeySpec key = generateKey(alise);//get the secret key
        Cipher c = Cipher.getInstance(AES);//create a cipher object using cryptography algorithm AES which we need to proceed
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] enVal = c.doFinal(text.getBytes());
        String encryptedVal = Base64.encodeToString(enVal,Base64.DEFAULT);
        return encryptedVal;
    }

    private SecretKeySpec generateKey(String alise) throws  Exception{

        //message digest is used to check weather the data has modified during data transport
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");//one way hashing
        //calculate message digest of multiple block of data
        byte[] bytes = alise.getBytes("UTF-8");//string converts into bytes
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest();//hash the bytes
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");//get secret key using AES algorithm
        return secretKeySpec;


    }
}
