
import javax.net.ssl.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;


public class TestBypassHttps {

    public void sendRequestIgnoreSSL(String url)  {
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
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization","Bearer 8dd1cac5-7e95-4611-ac31-fc66d94eaefa");
            con.setDoInput(true);
            DataInputStream input = new DataInputStream( con.getInputStream() );
            for( int c = input.read(); c != -1; c = input.read() )
                System.out.print( (char)c );
            input.close();
            System.out.println("Resp Code:"+con .getResponseCode());
            System.out.println("Resp Message:"+ con .getResponseMessage());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if(con!=null&&con.getResponseCode()==400){
                    System.out.println("请求400了");
                }
            } catch (IOException e1) {

            }
        }
    }
}
