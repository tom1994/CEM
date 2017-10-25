package io.cem.common.utils.excel.factory.impl;

import io.cem.common.utils.excel.factory.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tomxie on 2017/4/6 22:35.
 */
public class XSSFWorkbookFactory implements WorkbookFactory<XSSFWorkbook, InputStream> {

    @Override
    public XSSFWorkbook create(InputStream is) throws IOException {
        return new XSSFWorkbook(is);
    }
}
