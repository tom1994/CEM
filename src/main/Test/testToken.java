import com.alibaba.fastjson.JSONObject;
import io.cem.common.exception.RRException;
import io.cem.common.utils.PropertiesUtils;
import org.junit.Test;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.util.Properties;


public class testToken {
    @Test
    public void Test() {
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
//            con.setRequestProperty("grant_type", "password");
//            con.setRequestProperty("username", "test");
//            con.setRequestProperty("password", "test");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


