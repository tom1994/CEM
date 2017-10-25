package io.cem.common.utils.excel.factory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 为兼容不同类型的Excel文件而设置的工厂接口
 * Created by tomxie on 2017/4/6 22:26.
 */
public interface WorkbookFactory<T, K extends InputStream> {
    T create(K is) throws IOException;
}
