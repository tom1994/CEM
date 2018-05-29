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
        String msg = "[123,345]";
        String[] failIds = msg.substring(1, msg.length() - 1).split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (String id : failIds) {
            stringBuilder.append(Integer.parseInt(id)).append(",");
        }
        String probeName = stringBuilder.toString();
        System.out.println(probeName.substring(0,probeName.length()-1));
    }
}


