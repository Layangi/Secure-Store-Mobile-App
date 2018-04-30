package com.example.layangi.myapplication.codes;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 * Created by Lakshika on 31/03/2018.
 */

public class KeyStore{

    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static String DEFAULT_KEY = "";
    private java.security.KeyStore keystore;
    private String TAG = "Key store";


    @RequiresApi(api = Build.VERSION_CODES.M)
    public KeyStore(String alias) {
        this.DEFAULT_KEY = alias;
        Log.w(TAG,"start key store:"+ DEFAULT_KEY);
        loadKeystore();
        if (getSecretKey() == null) {
            createSecretKey();
        }
    }

    private void loadKeystore() {
        try {
            keystore = java.security.KeyStore.getInstance(ANDROID_KEY_STORE);
            keystore.load(null);
        } catch (IOException | NoSuchAlgorithmException |
                CertificateException | KeyStoreException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createSecretKey() {
        Log.w(TAG,"inside generate key");
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA,ANDROID_KEY_STORE);
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec
                    .Builder(DEFAULT_KEY, KeyProperties.PURPOSE_ENCRYPT |
                    KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .setUserAuthenticationRequired(true);
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    SecretKey getSecretKey() {
        try {
            return (SecretKey) keystore.getKey(DEFAULT_KEY, null);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
