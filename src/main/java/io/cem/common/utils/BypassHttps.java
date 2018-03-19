package io.cem.common.utils;

import javax.net.ssl.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;


public class BypassHttps {
    public static int sendRequestIgnoreSSL(String type, String url)  {
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
            con = (HttpsURLConnection)myurl.openConnection();
            con.setRequestMethod(type);
            con.setRequestProperty("Authorization","Bearer e2fba2c5-ab1d-417f-ad9d-c91c9993f04b");
            con.setDoInput(true);
            DataInputStream input = new DataInputStream( con.getInputStream() );
            for( int c = input.read(); c != -1; c = input.read() )
                System.out.print( (char)c );
            input.close();
            System.out.println("Resp Code:"+con .getResponseCode());
            System.out.println("Resp Message:"+ con .getResponseMessage());
            return con.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if(con!=null&&con.getResponseCode()==400){
                    System.out.println("请求400了");
                }
            } catch (IOException e1) {

            }
            return 404;
        }
    }
}
