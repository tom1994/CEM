package io.cem.common.utils;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesUtils {
   private static Properties prop = new Properties();
   private static final String PATH = "evamodule.properties";
    private static final String PATH2 = "cem.properties";

    public PropertiesUtils() throws IOException {
        InputStream in =new BufferedInputStream(new FileInputStream(PropertiesUtils.class.getClassLoader().getResource(PATH).getPath())) ;
        prop.load(in);     ///加载属性列表
        in.close();
    }

    public static String getValue(String key) throws IOException {
       String result = prop.getProperty(key);
       return result;
   }
   public static Object setValue(String key,String value) throws IOException {
       ///保存属性到evamodule.properties文件
       FileOutputStream oFile = new FileOutputStream(PropertiesUtils.class.getClassLoader().getResource(PATH).getPath());
       prop.setProperty(key, value);
       prop.store(oFile, "weight set by user");
       oFile.close();
       return prop;
    }
    public static Object setValue(String key,String value,String path) throws IOException {
        ///保存属性到其它文件
        FileOutputStream oFile = new FileOutputStream(PropertiesUtils.class.getClassLoader().getResource(PATH2).getPath());
        prop.setProperty(key, value);
        prop.store(oFile, "set by user");
        oFile.close();
        return prop;
    }
}