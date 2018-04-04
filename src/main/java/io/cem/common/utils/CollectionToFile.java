package io.cem.common.utils;

import io.cem.common.exception.RRException;
import io.cem.common.utils.excel.ExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CollectionToFile {
    public static <T> void collectionToFile(HttpServletResponse response, List<T> list, Class<T> c) throws RRException {
        InputStream is = null;
        ServletOutputStream out = null;
        try {
            XSSFWorkbook workbook = ExcelUtils.<T>exportExcel("sheet1", c, list);
            response.setContentType("application/octet-stream");
            // response.setCharacterEncoding("UTF-8");
            String fileName = c.getSimpleName().toLowerCase().replaceAll("entity", "") + ".xlsx";
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            // File outFile = new File("F://out.xlsx");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RRException("下载文件出错");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
