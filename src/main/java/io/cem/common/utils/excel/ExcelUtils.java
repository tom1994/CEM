package io.cem.common.utils.excel;

import io.cem.common.utils.excel.annotation.ExcelIgnore;
import io.cem.common.utils.excel.annotation.ExportName;
import io.cem.common.utils.excel.factory.WorkbookFactory;
import io.cem.common.utils.excel.factory.impl.HSSFWorkbookFactory;
import io.cem.common.utils.excel.factory.impl.XSSFWorkbookFactory;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Excel工具类
 * Created by tomxie on 2017/4/8 13:15.
 */
public class ExcelUtils {
    // 对应xls文件和xlsx的工厂类集合
    private static List<WorkbookFactory<? extends Workbook, InputStream>> workbookFactories = new ArrayList<>();
    // 输出Excel表格的列宽
    private static final int FIELD_WIDTH = 15;

    static {
        workbookFactories.add(new HSSFWorkbookFactory());
        workbookFactories.add(new XSSFWorkbookFactory());
    }

    /**
     * 将excel文件转换成集合
     *
     * @param inputStream 输入文件流
     * @param type        输入文件类型
     * @param entity      实体类
     * @return 实体类集合
     * @throws IOException 异常
     */
    public static Collection readExcel(InputStream inputStream, String type, Class entity) throws IOException {
        // InputStream is = new FileInputStream(path + fileName);//EXCEL_PATH存放路径
        Collection dist = new ArrayList();
        try {

            // 得到目标目标类的所有的字段列表
            Field fields[] = entity.getDeclaredFields();
            Map<String, Method> fieldSetMap = new HashMap<String, Method>();
            for (Field f : fields) {
                ExcelIgnore ignore = f.getAnnotation(ExcelIgnore.class);
                // 去除由序列化导致的serialVersionUID以及含有ExcelIgnore注解的field
                if (!isFieldFinal(f) && ignore == null) {
                    // 构造Setter方法
                    String fieldName = f.getName();
                    String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    // 构造调用的method
                    Method setMethod = entity.getMethod(setMethodName, f.getType());
                    // 将这个method以field名字的小写为key来存入
                    fieldSetMap.put(fieldName.toLowerCase(), setMethod);
                }
            }
            Workbook book;
            switch (type) {
                case "xls":
                    book = workbookFactories.get(0).create(inputStream);
                    break;
                case "xlsx":
                    book = workbookFactories.get(1).create(inputStream);
                    break;
                default:
                    return null;
            }
            // // 得到工作表
            // HSSFWorkbook book = new HSSFWorkbook(inputStream);
            // 得到第一页
            Sheet sheet = book.getSheetAt(0);
            // 得到第一面的所有行
            Iterator<Row> row = sheet.rowIterator();
            // 得到第一行，也就是标题行
            Row title = row.next();
            // 得到第一行的所有列
            Iterator<Cell> cellTitle = title.cellIterator();
            // 将标题的文字内容放入到一个map中。
            Map<Integer, String> titleMap = new HashMap<Integer, String>();
            // 从标题第一列开始
            int i = 0;
            // 循环标题所有的列
            while (cellTitle.hasNext()) {
                Cell cell = cellTitle.next();
                String value = cell.getStringCellValue();
                titleMap.put(i, value);
                i = i + 1;
            }
            while (row.hasNext()) {
                // 标题下的第一行
                Row rown = row.next();
                // 行的所有列
                Iterator<Cell> cellbody = rown.cellIterator();
                // 得到传入类的实例
                Constructor constructor = entity.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object tObject = constructor.newInstance();
                int k = 0;
                // 遍历一行的列
                while (cellbody.hasNext()) {
                    Cell cell = cellbody.next();
                    // 这里得到此列的对应的标题
                    String titleString = (String) titleMap.get(k);
                    // 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
                    if (fieldSetMap.containsKey(titleString)) {
                        Method setMethod = (Method) fieldSetMap.get(titleString);
                        // 得到setter方法的参数
                        Type[] ts = setMethod.getGenericParameterTypes();
                        // 只要一个参数
                        String xclass = ts[0].toString();
                        // 判断参数类型
                        switch (xclass) {
                            case "class java.lang.String":
                                // 先设置Cell的类型，然后就可以把纯数字作为String类型读进来了：
                                cell.setCellType(CellType.STRING);
                                setMethod.invoke(tObject, cell.getStringCellValue());
                                break;
                            case "class java.util.Date":
                                Date cellDate = null;
                                if (CellType.NUMERIC == cell.getCellTypeEnum()) {
                                    // 日期格式
                                    cellDate = cell.getDateCellValue();
                                } else {    // 全认为是  Cell.CELL_TYPE_STRING: 如果不是 yyyy-mm-dd hh:mm:ss 的格式就不对(wait to do:有局限性)
                                    cellDate = stringToDate(cell.getStringCellValue());
                                }
                                setMethod.invoke(tObject, cellDate);
                                //// --------------------------------------------------------------------------------------------
                                //String cellValue = cell.getStringCellValue();
                                //Date theDate = stringToDate(cellValue);
                                //setMethod.invoke(tObject, theDate);
                                //// --------------------------------------------------------------------------------------------
                                break;
                            case "class java.lang.Boolean":
                                boolean valBool;
                                if (CellType.BOOLEAN == cell.getCellTypeEnum()) {
                                    valBool = cell.getBooleanCellValue();
                                } else {// 全认为是  Cell.CELL_TYPE_STRING
                                    valBool = cell.getStringCellValue().equalsIgnoreCase("true")
                                            || (!cell.getStringCellValue().equals("0"));
                                }
                                setMethod.invoke(tObject, valBool);
                                break;
                            case "class java.lang.Integer":
                                Integer valInt = null;
                                try {
                                    if (CellType.NUMERIC == cell.getCellTypeEnum()) {
                                        valInt = (new Double(cell.getNumericCellValue())).intValue();
                                    } else {// 全认为是  Cell.CELL_TYPE_STRING
                                        valInt = new Integer(cell.getStringCellValue());
                                    }
                                } catch (NumberFormatException ignored) {
                                }
                                setMethod.invoke(tObject, valInt);
                                break;
                            case "class java.lang.Long":
                                Long valLong = null;
                                try {
                                    if (CellType.NUMERIC == cell.getCellTypeEnum()) {
                                        valLong = (new Double(cell.getNumericCellValue())).longValue();
                                    } else {// 全认为是  Cell.CELL_TYPE_STRING
                                        valLong = new Long(cell.getStringCellValue());
                                    }
                                } catch (NumberFormatException ignored) {
                                }
                                setMethod.invoke(tObject, valLong);
                                break;
                            case "class java.math.BigDecimal":
                                BigDecimal valDecimal = null;
                                try {
                                    if (CellType.NUMERIC == cell.getCellTypeEnum()) {
                                        valDecimal = new BigDecimal(cell.getNumericCellValue());
                                    } else {// 全认为是  Cell.CELL_TYPE_STRING
                                        valDecimal = new BigDecimal(cell.getStringCellValue());
                                    }
                                } catch (NumberFormatException ignored) {
                                }
                                setMethod.invoke(tObject, valDecimal);
                                break;
                            case "class java.lang.Double":
                                Double valDouble = null;
                                try {
                                    valDouble = new Double(cell.getStringCellValue());
                                } catch (NumberFormatException ignored) {
                                }
                                setMethod.invoke(tObject, valDouble);
                                break;
                            case "float":
                                Float valFloat = null;
                                try {
                                    valFloat = (float) cell.getNumericCellValue();
                                } catch (NumberFormatException ignored) {
                                }
                                setMethod.invoke(tObject, valFloat);
                                break;
                        }

                    }
                    // 下一列
                    k = k + 1;
                }
                dist.add(tObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return dist;
    }

    /**
     * 将一个Collection转化成Workbook
     *
     * @param title   sheet名
     * @param entity  对应的Entity类型
     * @param dataSet Entity类型对象组成的集合
     * @param <T>     Entity类
     * @return 已经填充数据以及样式的Workbook
     */
    public static <T> XSSFWorkbook exportExcel(String title, Class<T> entity,
                                               Collection<T> dataSet) {
        // 声明一个工作薄
        XSSFWorkbook workbook = null;
        try {
            // 声明一个工作薄
            workbook = new XSSFWorkbook();
            // 生成一个表格
            Sheet sheet = workbook.createSheet(title);

            // 标题
            List<String> exportFieldTitle = new ArrayList<String>();
            List<Integer> exportFieldWidth = new ArrayList<Integer>();
            // 拿到所有列名，以及导出的字段的get方法
            List<Method> methodList = new ArrayList<Method>();
            Class superClass = null;
            Field fields[] = new Field[0];
            boolean flag = true;
            while (flag) {
                if (superClass != null) {
                    superClass = superClass.getSuperclass();
                } else {
                    superClass = entity.getSuperclass();
                }
                if (superClass.isInstance(Object.class)) {
                    flag = false;
                } else {
                    Field[] sf = superClass.getDeclaredFields();
                    if (sf != null && sf.length > 0) {
                        fields = (Field[]) ArrayUtils.addAll(fields, sf);
                    }
                }

            }
            // 得到所有字段
            Field cfileds[] = entity.getDeclaredFields();
            if (cfileds != null && cfileds.length > 0) {
                fields = (Field[]) ArrayUtils.addAll(fields, cfileds);
            }
            // 遍历整个field
            for (Field field : fields) {
                ExcelIgnore ignore = field.getAnnotation(ExcelIgnore.class);
                // 如果field不为final并且不含有ExcelIgnore注解
                if (!isFieldFinal(field) && ignore == null) {
                    // 添加到标题
                    // TODO:此处需要添加一个field name和export name之间的转换
                    ExportName export = field.getAnnotation(ExportName.class);
                    if (export == null){
                        exportFieldTitle.add(field.getName());
                    }else {
                        exportFieldTitle.add(export.exportName());
                    }
                    // 添加标题的列宽
                    exportFieldWidth.add(FIELD_WIDTH);
                    // 添加到需要导出的字段的方法
                    String fieldname = field.getName();
                    // System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
                    String getMethodName = "get" + fieldname.substring(0, 1)
                            .toUpperCase() +
                            fieldname.substring(1);

                    Method getMethod = entity.getMethod(getMethodName);

                    methodList.add(getMethod);
                }
            }
            int index = 0;
            // 产生表格标题行
            Row row = sheet.createRow(index);
            row.setHeight((short) 450);
            CellStyle titleStyle = getTitleStyle(workbook);
            for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
                Cell cell = row.createCell(i);
                // cell.setCellStyle(style);
                RichTextString text = new XSSFRichTextString(exportFieldTitle
                        .get(i));
                cell.setCellValue(text);
                cell.setCellStyle(titleStyle);
            }

            // 设置每行的列宽
            for (int i = 0; i < exportFieldWidth.size(); i++) {
                // 256=65280/255
                sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
            }
            Iterator its = dataSet.iterator();
            // 循环插入剩下的集合
            while (its.hasNext()) {
                // 从第二行开始写，第一行是标题
                index++;
                row = sheet.createRow(index);
                row.setHeight((short) 350);
                Object t = its.next();
                for (int k = 0, methodObjSize = methodList.size(); k < methodObjSize; k++) {
                    Cell cell = row.createCell(k);
                    Method getMethod = methodList.get(k);
                    Object value = null;
                    value = getMethod.invoke(t);
                    cell.setCellValue(value == null ? "" : value.toString());

                    if (index % 2 == 0)
                        cell.setCellStyle(getTwoStyle(workbook));
                    else
                        cell.setCellStyle(getOneStyle(workbook));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 导出Excel的表头样式
     *
     * @param workbook 输入的Workbook
     * @return Excel表头样式
     */
    private static XSSFCellStyle getTitleStyle(XSSFWorkbook workbook) {
        // 产生Excel表头
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setBorderBottom(BorderStyle.DOUBLE);    //设置边框样式
        titleStyle.setBorderLeft(BorderStyle.MEDIUM);     //左边框
        titleStyle.setBorderRight(BorderStyle.MEDIUM);    //右边框
        titleStyle.setBorderTop(BorderStyle.MEDIUM);     //左边框
        titleStyle.setBorderBottom(BorderStyle.MEDIUM);    //右边框
        titleStyle.setBorderTop(BorderStyle.DOUBLE);    //顶边框
        titleStyle.setFillForegroundColor(new XSSFColor(new Color(	70,130,180)));    //填充的背景颜色
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);    //填充图案

        return titleStyle;
    }

    /**
     * 导出Excel偶数行样式
     *
     * @param workbook 输入的Workbook
     * @return Excel偶数行样式
     */
    private static XSSFCellStyle getTwoStyle(XSSFWorkbook workbook) {
        // 产生Excel表头
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderLeft(BorderStyle.THIN);     //左边框
        style.setBorderRight(BorderStyle.THIN);    //右边框
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setFillForegroundColor(new XSSFColor(new Color(	176,224,230)));    //填充的背景颜色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);    //填充图案
        return style;
    }

    /**
     * 导出Excel奇数行样式
     *
     * @param workbook 输入的Workbook
     * @return Excel奇数行样式
     */
    private static XSSFCellStyle getOneStyle(XSSFWorkbook workbook) {
        // 产生Excel表头
        // 产生Excel表头
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderLeft(BorderStyle.THIN);     //左边框
        style.setBorderRight(BorderStyle.THIN);    //右边框
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        return style;
    }

    /**
     * 判断一个Field是否含有final修饰符
     *
     * @param field 输入Field
     * @return 是否含有final
     */
    private static boolean isFieldFinal(Field field) {
        return (field.getModifiers() & java.lang.reflect.Modifier.FINAL) == java.lang.reflect.Modifier.FINAL;
    }

    /**
     * 字符串转换为Date类型数据（限定格式      YYYY-MM-DD hh:mm:ss）或（YYYY/MM/DD hh:mm:ss）
     *
     * @param cellValue : 字符串类型的日期数据
     * @return 日期类型对象
     */
    private static Date stringToDate(String cellValue) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d  H:m:s");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        try {
            return format.parse(cellValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
