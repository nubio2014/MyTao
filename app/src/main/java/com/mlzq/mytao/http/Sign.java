package com.mlzq.mytao.http;

import com.taobao.api.internal.util.RequestParametersHolder;
import com.taobao.api.internal.util.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static com.taobao.api.internal.util.TaobaoUtils.byte2hex;
import static com.taobao.api.internal.util.TaobaoUtils.encryptMD5;

/**
 * Created by Dev on 2018/8/27.
 * desc :
 */

public class Sign {
    public static String signTopRequest(RequestParametersHolder requestHolder, String secret, String signMethod) throws IOException {
        return signTopRequest(requestHolder.getAllParams(), secret, signMethod);
    }

    public static String signTopRequest(Map<String, String> params, String secret, String signMethod) throws IOException {
        String[] keys = (String[])params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder query = new StringBuilder();
        if("md5".equals(signMethod)) {
            query.append(secret);
        }

        String[] bytes = keys;
        int len$ = keys.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String key = bytes[i$];
            String value = (String)params.get(key);
            if(StringUtils.areNotEmpty(new String[]{key, value})) {
                query.append(key).append(value);
            }
        }

//        if(body != null) {
//            query.append(body);
//        }

        byte[] var11;
        if("hmac".equals(signMethod)) {
            var11 = encryptHMAC(query.toString(), secret);
        } else if("hmac-sha256".equals(signMethod)) {
            var11 = encryptHMACSHA256(query.toString(), secret);
        } else {
            query.append(secret);
            var11 = encryptMD5(query.toString());
        }

        return byte2hex(var11);
    }

    private static byte[] encryptHMACSHA256(String data, String secret) throws IOException {
        Object bytes = null;

        try {
            SecretKeySpec gse = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance(gse.getAlgorithm());
            mac.init(gse);
            byte[] bytes1 = mac.doFinal(data.getBytes("UTF-8"));
            return bytes1;
        } catch (GeneralSecurityException var5) {
            throw new IOException(var5.toString());
        }
    }

    private static byte[] encryptHMAC(String data, String secret) throws IOException {
        Object bytes = null;

        try {
            SecretKeySpec gse = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance(gse.getAlgorithm());
            mac.init(gse);
            byte[] bytes1 = mac.doFinal(data.getBytes("UTF-8"));
            return bytes1;
        } catch (GeneralSecurityException var5) {
            throw new IOException(var5.toString());
        }
    }
}
