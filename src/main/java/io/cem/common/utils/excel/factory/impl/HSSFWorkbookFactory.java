package io.cem.common.utils.excel.factory.impl;

import io.cem.common.utils.excel.factory.WorkbookFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tomxie on 2017/4/6 22:30.
 */
public class HSSFWorkbookFactory implements WorkbookFactory<HSSFWorkbook, InputStream> {

    @Override
    public HSSFWorkbook create(InputStream is) throws IOException {
        return new HSSFWorkbook(is);
    }
}
