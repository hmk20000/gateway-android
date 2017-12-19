package org.kccc.gateway;

import android.app.Application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ming on 2017. 12. 12..
 */

public class App extends Application{
    public static final String md5(byte[] rawData) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        digest.reset();
        digest.update(rawData);
        byte[] msgDigest = digest.digest();

        StringBuffer hexPass = new StringBuffer();

        for (int i = 0; i < msgDigest.length; i++) {
            String h = Integer.toHexString(0xFF & msgDigest[i]);
            while (h.length() < 2) {
                h = "0" + h;
            }

            hexPass.append(h);
        }

        return hexPass.toString();
    }
}
