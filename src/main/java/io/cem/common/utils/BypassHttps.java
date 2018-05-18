package io.cem.common.utils;

import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.util.Properties;


public class BypassHttps {
    public static int sendRequestIgnoreSSL(String type, String url) {
        HttpsURLConnection con = null;
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("cem.properties").getPath()));
            prop.load(in);
        } catch (Exception e) {
            throw new RRException("配置文件配置有误，请重新配置");
        }
        // Install the all-trusting trust manager
        try {

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            // Now you can access an https URL without having the certificate in the truststore
            URL myurl = new URL(url);
            con = (HttpsURLConnection) myurl.openConnection();
            con.setRequestMethod(type);
            con.setRequestProperty("Authorization", "Bearer " + prop.getProperty("token"));
            con.setDoInput(true);
            DataInputStream input = new DataInputStream(con.getInputStream());
            for (int c = input.read(); c != -1; c = input.read())
                System.out.print((char) c);
            input.close();
            System.out.println("Resp Code:" + con.getResponseCode());
            System.out.println("Resp Message:" + con.getResponseMessage());
            return con.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null && con.getResponseCode() == 401) {
                    getNewToken();
                    return 401;
                } if (con != null) {
                    return con.getResponseCode();
                }
            } catch (IOException e1) {
                return 500;
            }
            return 404;
        }
    }

    private static Boolean getNewToken() {
        HttpsURLConnection con = null;
        byte[] requestBody = null;
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource("cem.properties").getPath()));
            prop.load(in);
        } catch (Exception e) {
            throw new RRException("配置文件配置有误，请重新配置");
        }
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            URL myurl = new URL(prop.getProperty("socketAddress") + "/oauth/token");
            con = (HttpsURLConnection) myurl.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Basic dGVzdDp0ZXN0");
            requestBody = new String("grant_type=password&username=test&password=test").getBytes("UTF-8");
            //将请求体写入到conn的输出流中
            OutputStream os = con.getOutputStream();
            os.write(requestBody);
            os.flush();
            os.close();
//            con.setDoInput(true);
            StringBuilder stringBuilder = new StringBuilder();
            DataInputStream input = new DataInputStream(con.getInputStream());
            for (int c = input.read(); c != -1; c = input.read())
                stringBuilder.append((char) c);
            input.close();
            String access_token = JSONObject.parseObject(stringBuilder.toString()).getString("access_token");
            PropertiesUtils.setValue("token", access_token, "cem.properties");
            PropertiesUtils.setValue("socketAddress", prop.getProperty("socketAddress"), "cem.properties");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
