package com.yanxuwen.retrofit;

import android.content.Context;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 作者：严旭文 on 2017/2/9 11:49
 * 邮箱：420255048@qq.com
 */
public class MySSLSocketFactory {
    private  static String CLIENT_PRI_KEY = "client.bks";//客户端证书
    private  static String TRUSTSTORE_PUB_KEY = "truststore.bks";
    private  static String CLIENT_BKS_PASSWORD = "123456";//客户端密码
    private  static String TRUSTSTORE_BKS_PASSWORD = "123456";
    private  static String CLIENT_KEYSTORE_TYPE = "BKS";
    private  static String TRUSTSTORE_KEYSTORE_TYPE = "BKS";
    private  static String PROTOCOL_TYPE = "TLS";//暂时定死
    private  static String CERTIFICATE_FORMAT = "X509";//暂时定死
    private  static String single_cer="srca.cer";//单向认证的证书名字

    /**
     * 实例化双向证书
     * @param client_key          客户端证书key
     * @param truststore_key      服务端证书key
     * @param client_pwd          客户端密码
     * @param truststore_pwd      服务密码
     * @param client_key_type     客户端类型
     * @param truststore_key_type 服务端类型
     */
    public static void init(String client_key,String truststore_key,String client_pwd,String truststore_pwd,String client_key_type,
                       String truststore_key_type){
        CLIENT_PRI_KEY=client_key;
        TRUSTSTORE_PUB_KEY=truststore_key;
        CLIENT_BKS_PASSWORD=client_pwd;
        TRUSTSTORE_BKS_PASSWORD=truststore_pwd;
        CLIENT_KEYSTORE_TYPE=client_key_type;
        TRUSTSTORE_KEYSTORE_TYPE=truststore_key_type;
    }

    /**
     * @param cer
     * 单向认证证书
     */
    public static void init(String cer){
        single_cer=cer;
    }
    /**
     * 双向,还没测试，暂时不清楚是否能用
     */
    public static SSLSocketFactory getSSLCertifcation(Context context) {
        SSLSocketFactory sslSocketFactory = null;
        try {
            // 服务器端需要验证的客户端证书，其实就是客户端的keystore
            KeyStore keyStore = KeyStore.getInstance(CLIENT_KEYSTORE_TYPE);// 客户端信任的服务器端证书
            KeyStore trustStore = KeyStore.getInstance(TRUSTSTORE_KEYSTORE_TYPE);//读取证书
            InputStream ksIn = context.getAssets().open(CLIENT_PRI_KEY);
            InputStream tsIn = context.getAssets().open(TRUSTSTORE_PUB_KEY);//加载证书
            keyStore.load(ksIn, CLIENT_BKS_PASSWORD.toCharArray());
            trustStore.load(tsIn, TRUSTSTORE_BKS_PASSWORD.toCharArray());
            ksIn.close();
            tsIn.close();
            //初始化SSLContext
            SSLContext sslContext = SSLContext.getInstance(PROTOCOL_TYPE);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(CERTIFICATE_FORMAT);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(CERTIFICATE_FORMAT);
            trustManagerFactory.init(trustStore);
            keyManagerFactory.init(keyStore, CLIENT_BKS_PASSWORD.toCharArray());
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
        }//省略各种异常处理，请自行添加
        return sslSocketFactory;
    }

    /**
     * 单向，已用12306测试
     */
    public static SSLSocketFactory getSSLCertifcation2(Context context) {
        SSLSocketFactory sslSocketFactory = null;
        try {
            InputStream in;
            in = context.getAssets().open(single_cer);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            keyStore.setCertificateEntry("0", certificateFactory.generateCertificate(in));

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            sslSocketFactory=  sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    /**
     * @return
     * 信任所有的证书，建议不要。
     */
    public static SSLSocketFactory getSSLCertifcation3() {
        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }
    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}