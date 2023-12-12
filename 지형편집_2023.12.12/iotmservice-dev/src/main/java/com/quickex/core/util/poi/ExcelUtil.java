package com.quickex.core.util.poi;


import com.quickex.core.annotation.Excel;
import com.quickex.core.annotation.Excels;
import com.quickex.core.config.KoConfig;
import com.quickex.core.exception.UtilException;
import com.quickex.core.result.R;
import com.quickex.core.text.Convert;
import com.quickex.core.util.DateUtils;
import com.quickex.core.util.DictUtils;
import com.quickex.core.util.StringUtils;
import com.quickex.core.util.file.FileTypeUtils;
import com.quickex.core.util.file.FileUtils;
import com.quickex.core.util.file.ImageUtils;
import com.quickex.core.util.reflect.ReflectUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Excel info
 * 
 * @author ffzh
 */
public class ExcelUtil<T>
{
//    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
//
//
//    public static final int sheetSize = 65536;
//
//
//    private String sheetName;
//
//
//    private Excel.Type type;
//
//
//    private Workbook wb;
//
//
//    private Sheet sheet;
//
//
//    private Map<String, CellStyle> styles;
//
//
//    private List<T> list;
//
//
//    private List<Object[]> fields;
//
//
//    private short maxHeight;
//
//
//    private final Map<Integer, Double> statistics = new HashMap<Integer, Double>();
//
//
//    private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("######0.00");
//
//
//    public Class<T> clazz;
//
//    public ExcelUtil(Class<T> clazz)
//    {
//        this.clazz = clazz;
//    }
//
//    public void init(List<T> list, String sheetName, Excel.Type type)
//    {
//        if (list == null)
//        {
//            list = new ArrayList<T>();
//        }
//        this.list = list;
//        this.sheetName = sheetName;
//        this.type = type;
//        createExcelField();
//        createWorkbook();
//    }
//
//
//    public List<T> importExcel(InputStream is) throws Exception
//    {
//        return importExcel(StringUtils.EMPTY, is);
//    }
//
//
//    public List<T> importExcel(String sheetName, InputStream is) throws Exception
//    {
//        this.type = Excel.Type.IMPORT;
//        this.wb = WorkbookFactory.create(is);
//        List<T> list = new ArrayList<T>();
//
//        Sheet sheet = StringUtils.isNotEmpty(sheetName) ? wb.getSheet(sheetName) : wb.getSheetAt(0);
//        if (sheet == null)
//        {
//            throw new IOException("sheet not find");
//        }
//        boolean isXSSFWorkbook = !(wb instanceof HSSFWorkbook);
//        Map<String, PictureData> pictures;
//        if (isXSSFWorkbook)
//        {
//            pictures = getSheetPictures07((XSSFSheet) sheet, (XSSFWorkbook) wb);
//        }
//        else
//        {
//            pictures = getSheetPictures03((HSSFSheet) sheet, (HSSFWorkbook) wb);
//        }
//
//        int rows = sheet.getLastRowNum();
//
//        if (rows > 0)
//        {
//
//            Map<String, Integer> cellMap = new HashMap<String, Integer>();
//
//            Row heard = sheet.getRow(0);
//            for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++)
//            {
//                Cell cell = heard.getCell(i);
//                if (StringUtils.isNotNull(cell))
//                {
//                    String value = this.getCellValue(heard, i).toString();
//                    cellMap.put(value, i);
//                }
//                else
//                {
//                    cellMap.put(null, i);
//                }
//            }
//
//            Field[] allFields = clazz.getDeclaredFields();
//
//            Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
//            for (int col = 0; col < allFields.length; col++)
//            {
//                Field field = allFields[col];
//                Excel attr = field.getAnnotation(Excel.class);
//                if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type))
//                {
//                    field.setAccessible(true);
//                    Integer column = cellMap.get(attr.name());
//                    if (column != null)
//                    {
//                        fieldsMap.put(column, field);
//                    }
//                }
//            }
//            for (int i = 1; i <= rows; i++)
//            {
//
//                Row row = sheet.getRow(i);
//
//                if (isRowEmpty(row))
//                {
//                    continue;
//                }
//                T entity = null;
//                for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet())
//                {
//                    Object val = this.getCellValue(row, entry.getKey());
//
//
//                    entity = (entity == null ? clazz.newInstance() : entity);
//
//                    Field field = fieldsMap.get(entry.getKey());
//
//                    Class<?> fieldType = field.getType();
//                    if (String.class == fieldType)
//                    {
//                        String s = Convert.toStr(val);
//                        if (StringUtils.endsWith(s, ".0"))
//                        {
//                            val = StringUtils.substringBefore(s, ".0");
//                        }
//                        else
//                        {
//                            String dateFormat = field.getAnnotation(Excel.class).dateFormat();
//                            if (StringUtils.isNotEmpty(dateFormat))
//                            {
//                                val = DateUtils.parseDateToStr(dateFormat, (Date) val);
//                            }
//                            else
//                            {
//                                val = Convert.toStr(val);
//                            }
//                        }
//                    }
//                    else if ((Integer.TYPE == fieldType || Integer.class == fieldType) && StringUtils.isNumeric(Convert.toStr(val)))
//                    {
//                        val = Convert.toInt(val);
//                    }
//                    else if (Long.TYPE == fieldType || Long.class == fieldType)
//                    {
//                        val = Convert.toLong(val);
//                    }
//                    else if (Double.TYPE == fieldType || Double.class == fieldType)
//                    {
//                        val = Convert.toDouble(val);
//                    }
//                    else if (Float.TYPE == fieldType || Float.class == fieldType)
//                    {
//                        val = Convert.toFloat(val);
//                    }
//                    else if (BigDecimal.class == fieldType)
//                    {
//                        val = Convert.toBigDecimal(val);
//                    }
//                    else if (Date.class == fieldType)
//                    {
//                        if (val instanceof String)
//                        {
//                            val = DateUtils.parseDate(val);
//                        }
//                        else if (val instanceof Double)
//                        {
//                            val = DateUtil.getJavaDate((Double) val);
//                        }
//                    }
//                    else if (Boolean.TYPE == fieldType || Boolean.class == fieldType)
//                    {
//                        val = Convert.toBool(val, false);
//                    }
//                    if (StringUtils.isNotNull(fieldType))
//                    {
//                        Excel attr = field.getAnnotation(Excel.class);
//                        String propertyName = field.getName();
//                        if (StringUtils.isNotEmpty(attr.targetAttr()))
//                        {
//                            propertyName = field.getName() + "." + attr.targetAttr();
//                        }
//                        else if (StringUtils.isNotEmpty(attr.readConverterExp()))
//                        {
//                            val = reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
//                        }
//                        else if (StringUtils.isNotEmpty(attr.dictType()))
//                        {
//                            val = reverseDictByExp(Convert.toStr(val), attr.dictType(), attr.separator());
//                        }
//                        else if (Excel.ColumnType.IMAGE == attr.cellType() && StringUtils.isNotEmpty(pictures))
//                        {
//                            PictureData image = pictures.get(row.getRowNum() + "_" + entry.getKey());
//                            if (image == null)
//                            {
//                                val = "";
//                            }
//                            byte[] data = image.getData();
//                            val = FileUtils.writeImportBytes(data);
//                        }
//                        ReflectUtils.invokeSetter(entity, propertyName, val);
//                    }
//                }
//                list.add(entity);
//            }
//        }
//        return list;
//    }
//
//
//    public R exportExcel(List<T> list, String sheetName)
//    {
//        this.init(list, sheetName, Excel.Type.EXPORT);
//        return exportExcel();
//    }
//
//
//    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName) throws IOException
//    {
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setCharacterEncoding("utf-8");
//        this.init(list, sheetName, Excel.Type.EXPORT);
//        exportExcel(response.getOutputStream());
//    }
//
//
//    public R importTemplateExcel(String sheetName)
//    {
//        this.init(null, sheetName, Excel.Type.IMPORT);
//        return exportExcel();
//    }
//
//
//    public void importTemplateExcel(HttpServletResponse response, String sheetName) throws IOException
//    {
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setCharacterEncoding("utf-8");
//        this.init(null, sheetName, Excel.Type.IMPORT);
//        exportExcel(response.getOutputStream());
//    }
//
//
//    public void exportExcel(OutputStream out)
//    {
//        try
//        {
//            writeSheet();
//            wb.write(out);
//        }
//        catch (Exception e)
//        {
//            log.error("Exception{}", e.getMessage());
//        }
//        finally
//        {
//            IOUtils.closeQuietly(wb);
//            IOUtils.closeQuietly(out);
//        }
//    }
//
//
//    public R exportExcel()
//    {
//        OutputStream out = null;
//        try
//        {
//            writeSheet();
//            String filename = encodingFilename(sheetName);
//            out = new FileOutputStream(getAbsoluteFile(filename));
//            wb.write(out);
//            return R.success(filename);
//        }
//        catch (Exception e)
//        {
//            throw new UtilException("Exception！");
//        }
//        finally
//        {
//            IOUtils.closeQuietly(wb);
//            IOUtils.closeQuietly(out);
//        }
//    }
//
//
//    public void writeSheet()
//    {
//
//        double sheetNo = Math.ceil(list.size() / sheetSize);
//        for (int index = 0; index <= sheetNo; index++)
//        {
//            createSheet(sheetNo, index);
//
//
//            Row row = sheet.createRow(0);
//            int column = 0;
//
//            for (Object[] os : fields)
//            {
//                Excel excel = (Excel) os[1];
//                this.createCell(excel, row, column++);
//            }
//            if (Excel.Type.EXPORT.equals(type))
//            {
//                fillExcelData(index, row);
//                addStatisticsRow();
//            }
//        }
//    }
//
//
//    public void fillExcelData(int index, Row row)
//    {
//        int startNo = index * sheetSize;
//        int endNo = Math.min(startNo + sheetSize, list.size());
//        for (int i = startNo; i < endNo; i++)
//        {
//            row = sheet.createRow(i + 1 - startNo);
//
//            T vo = list.get(i);
//            int column = 0;
//            for (Object[] os : fields)
//            {
//                Field field = (Field) os[0];
//                Excel excel = (Excel) os[1];
//
//                field.setAccessible(true);
//                this.addCell(excel, row, vo, field, column++);
//            }
//        }
//    }
//
//
//    private Map<String, CellStyle> createStyles(Workbook wb)
//    {
//
//        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
//        CellStyle style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        style.setBorderRight(BorderStyle.THIN);
//        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setBorderTop(BorderStyle.THIN);
//        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        Font dataFont = wb.createFont();
//        dataFont.setFontName("Arial");
//        dataFont.setFontHeightInPoints((short) 10);
//        style.setFont(dataFont);
//        styles.put("data", style);
//
//        style = wb.createCellStyle();
//        style.cloneStyleFrom(styles.get("data"));
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        Font headerFont = wb.createFont();
//        headerFont.setFontName("Arial");
//        headerFont.setFontHeightInPoints((short) 10);
//        headerFont.setBold(true);
//        headerFont.setColor(IndexedColors.WHITE.getIndex());
//        style.setFont(headerFont);
//        styles.put("header", style);
//
//        style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        Font totalFont = wb.createFont();
//        totalFont.setFontName("Arial");
//        totalFont.setFontHeightInPoints((short) 10);
//        style.setFont(totalFont);
//        styles.put("total", style);
//
//        style = wb.createCellStyle();
//        style.cloneStyleFrom(styles.get("data"));
//        style.setAlignment(HorizontalAlignment.LEFT);
//        styles.put("data1", style);
//
//        style = wb.createCellStyle();
//        style.cloneStyleFrom(styles.get("data"));
//        style.setAlignment(HorizontalAlignment.CENTER);
//        styles.put("data2", style);
//
//        style = wb.createCellStyle();
//        style.cloneStyleFrom(styles.get("data"));
//        style.setAlignment(HorizontalAlignment.RIGHT);
//        styles.put("data3", style);
//
//        return styles;
//    }
//
//
//    public Cell createCell(Excel attr, Row row, int column)
//    {
//
//        Cell cell = row.createCell(column);
//
//        cell.setCellValue(attr.name());
//        setDataValidation(attr, row, column);
//        cell.setCellStyle(styles.get("header"));
//        return cell;
//    }
//
//
//    public void setCellVo(Object value, Excel attr, Cell cell)
//    {
//        if (Excel.ColumnType.STRING == attr.cellType())
//        {
//            cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
//        }
//        else if (Excel.ColumnType.NUMERIC == attr.cellType())
//        {
//            if (StringUtils.isNotNull(value))
//            {
//                cell.setCellValue(StringUtils.contains(Convert.toStr(value), ".") ? Convert.toDouble(value) : Convert.toInt(value));
//            }
//        }
//        else if (Excel.ColumnType.IMAGE == attr.cellType())
//        {
//            ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(), cell.getRow().getRowNum(), (short) (cell.getColumnIndex() + 1), cell.getRow().getRowNum() + 1);
//            String imagePath = Convert.toStr(value);
//            if (StringUtils.isNotEmpty(imagePath))
//            {
//                byte[] data = ImageUtils.getImage(imagePath);
//                getDrawingPatriarch(cell.getSheet()).createPicture(anchor,
//                        cell.getSheet().getWorkbook().addPicture(data, getImageType(data)));
//            }
//        }
//    }
//
//
//    public static Drawing<?> getDrawingPatriarch(Sheet sheet)
//    {
//        if (sheet.getDrawingPatriarch() == null)
//        {
//            sheet.createDrawingPatriarch();
//        }
//        return sheet.getDrawingPatriarch();
//    }
//
//
//    public int getImageType(byte[] value)
//    {
//        String type = FileTypeUtils.getFileExtendName(value);
//        if ("JPG".equalsIgnoreCase(type))
//        {
//            return Workbook.PICTURE_TYPE_JPEG;
//        }
//        else if ("PNG".equalsIgnoreCase(type))
//        {
//            return Workbook.PICTURE_TYPE_PNG;
//        }
//        return Workbook.PICTURE_TYPE_JPEG;
//    }
//
//
//    public void setDataValidation(Excel attr, Row row, int column)
//    {
//        if (attr.name().indexOf("x：") >= 0)
//        {
//            sheet.setColumnWidth(column, 6000);
//        }
//        else
//        {
//
//            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
//        }
//
//        if (StringUtils.isNotEmpty(attr.prompt()))
//        {
//
//            setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
//        }
//
//        if (attr.combo().length > 0)
//        {
//
//            setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
//        }
//    }
//
//
//    public Cell addCell(Excel attr, Row row, T vo, Field field, int column)
//    {
//        Cell cell = null;
//        try
//        {
//
//            row.setHeight(maxHeight);
//
//            if (attr.isExport())
//            {
//
//                cell = row.createCell(column);
//                int align = attr.align().value();
//                cell.setCellStyle(styles.get("data" + (align >= 1 && align <= 3 ? align : "")));
//
//
//                Object value = getTargetValue(vo, field, attr);
//                String dateFormat = attr.dateFormat();
//                String readConverterExp = attr.readConverterExp();
//                String separator = attr.separator();
//                String dictType = attr.dictType();
//                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value))
//                {
//                    cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) value));
//                }
//                else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value))
//                {
//                    cell.setCellValue(convertByExp(Convert.toStr(value), readConverterExp, separator));
//                }
//                else if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotNull(value))
//                {
//                    cell.setCellValue(convertDictByExp(Convert.toStr(value), dictType, separator));
//                }
//                else if (value instanceof BigDecimal && -1 != attr.scale())
//                {
//                    cell.setCellValue((((BigDecimal) value).setScale(attr.scale(), attr.roundingMode())).toString());
//                }
//                else
//                {
//
//                    setCellVo(value, attr, cell);
//                }
//                addStatisticsData(column, Convert.toStr(value), attr);
//            }
//        }
//        catch (Exception e)
//        {
//            log.error("import Excel file{}", e);
//        }
//        return cell;
//    }
//
//    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
//            int firstCol, int endCol)
//    {
//        DataValidationHelper helper = sheet.getDataValidationHelper();
//        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
//        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
//        DataValidation dataValidation = helper.createValidation(constraint, regions);
//        dataValidation.createPromptBox(promptTitle, promptContent);
//        dataValidation.setShowPromptBox(true);
//        sheet.addValidationData(dataValidation);
//    }
//
//
//    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol)
//    {
//        DataValidationHelper helper = sheet.getDataValidationHelper();
//
//        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
//
//        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
//
//        DataValidation dataValidation = helper.createValidation(constraint, regions);
//
//        if (dataValidation instanceof XSSFDataValidation)
//        {
//            dataValidation.setSuppressDropDownArrow(true);
//            dataValidation.setShowErrorBox(true);
//        }
//        else
//        {
//            dataValidation.setSuppressDropDownArrow(false);
//        }
//
//        sheet.addValidationData(dataValidation);
//    }
//
//
//    public static String convertByExp(String propertyValue, String converterExp, String separator)
//    {
//        StringBuilder propertyString = new StringBuilder();
//        String[] convertSource = converterExp.split(",");
//        for (String item : convertSource)
//        {
//            String[] itemArray = item.split("=");
//            if (StringUtils.containsAny(separator, propertyValue))
//            {
//                for (String value : propertyValue.split(separator))
//                {
//                    if (itemArray[0].equals(value))
//                    {
//                        propertyString.append(itemArray[1] + separator);
//                        break;
//                    }
//                }
//            }
//            else
//            {
//                if (itemArray[0].equals(propertyValue))
//                {
//                    return itemArray[1];
//                }
//            }
//        }
//        return StringUtils.stripEnd(propertyString.toString(), separator);
//    }
//
//
//    public static String reverseByExp(String propertyValue, String converterExp, String separator)
//    {
//        StringBuilder propertyString = new StringBuilder();
//        String[] convertSource = converterExp.split(",");
//        for (String item : convertSource)
//        {
//            String[] itemArray = item.split("=");
//            if (StringUtils.containsAny(separator, propertyValue))
//            {
//                for (String value : propertyValue.split(separator))
//                {
//                    if (itemArray[1].equals(value))
//                    {
//                        propertyString.append(itemArray[0] + separator);
//                        break;
//                    }
//                }
//            }
//            else
//            {
//                if (itemArray[1].equals(propertyValue))
//                {
//                    return itemArray[0];
//                }
//            }
//        }
//        return StringUtils.stripEnd(propertyString.toString(), separator);
//    }
//
//
//    public static String convertDictByExp(String dictValue, String dictType, String separator)
//    {
//        return DictUtils.getDictLabel(dictType, dictValue, separator);
//    }
//
//
//    public static String reverseDictByExp(String dictLabel, String dictType, String separator)
//    {
//        return DictUtils.getDictValue(dictType, dictLabel, separator);
//    }
//
//
//    private void addStatisticsData(Integer index, String text, Excel entity)
//    {
//        if (entity != null && entity.isStatistics())
//        {
//            Double temp = 0D;
//            if (!statistics.containsKey(index))
//            {
//                statistics.put(index, temp);
//            }
//            try
//            {
//                temp = Double.valueOf(text);
//            }
//            catch (NumberFormatException e)
//            {
//            }
//            statistics.put(index, statistics.get(index) + temp);
//        }
//    }
//
//
//    public void addStatisticsRow()
//    {
//        if (statistics.size() > 0)
//        {
//            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
//            Set<Integer> keys = statistics.keySet();
//            Cell cell = row.createCell(0);
//            cell.setCellStyle(styles.get("total"));
//            cell.setCellValue("sum");
//
//            for (Integer key : keys)
//            {
//                cell = row.createCell(key);
//                cell.setCellStyle(styles.get("total"));
//                cell.setCellValue(DOUBLE_FORMAT.format(statistics.get(key)));
//            }
//            statistics.clear();
//        }
//    }
//
//
//    public String encodingFilename(String filename)
//    {
//        filename = UUID.randomUUID() + "_" + filename + ".xlsx";
//        return filename;
//    }
//
//
//    public String getAbsoluteFile(String filename)
//    {
//        String downloadPath = KoConfig.getDownloadPath() + filename;
//        File desc = new File(downloadPath);
//        if (!desc.getParentFile().exists())
//        {
//            desc.getParentFile().mkdirs();
//        }
//        return downloadPath;
//    }
//
//
//    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception
//    {
//        Object o = field.get(vo);
//        if (StringUtils.isNotEmpty(excel.targetAttr()))
//        {
//            String target = excel.targetAttr();
//            if (target.indexOf(".") > -1)
//            {
//                String[] targets = target.split("[.]");
//                for (String name : targets)
//                {
//                    o = getValue(o, name);
//                }
//            }
//            else
//            {
//                o = getValue(o, target);
//            }
//        }
//        return o;
//    }
//
//
//    private Object getValue(Object o, String name) throws Exception
//    {
//        if (StringUtils.isNotNull(o) && StringUtils.isNotEmpty(name))
//        {
//            Class<?> clazz = o.getClass();
//            Field field = clazz.getDeclaredField(name);
//            field.setAccessible(true);
//            o = field.get(o);
//        }
//        return o;
//    }
//
//
//    private void createExcelField()
//    {
//        this.fields = new ArrayList<Object[]>();
//        List<Field> tempFields = new ArrayList<>();
//        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
//        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
//        for (Field field : tempFields)
//        {
//
//            if (field.isAnnotationPresent(Excel.class))
//            {
//                putToField(field, field.getAnnotation(Excel.class));
//            }
//
//
//            if (field.isAnnotationPresent(Excel.class))
//            {
//                Excels attrs = field.getAnnotation(Excels.class);
//                Excel[] excels = attrs.value();
//                for (Excel excel : excels)
//                {
//                    putToField(field, excel);
//                }
//            }
//        }
//        this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort())).collect(Collectors.toList());
//        this.maxHeight = getRowHeight();
//    }
//
//
//    public short getRowHeight()
//    {
//        double maxHeight = 0;
//        for (Object[] os : this.fields)
//        {
//            Excel excel = (Excel) os[1];
//            maxHeight = maxHeight > excel.height() ? maxHeight : excel.height();
//        }
//        return (short) (maxHeight * 20);
//    }
//
//
//    private void putToField(Field field, Excel attr)
//    {
//        if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type))
//        {
//            this.fields.add(new Object[] { field, attr });
//        }
//    }
//
//
//    public void createWorkbook()
//    {
//        this.wb = new SXSSFWorkbook(500);
//    }
//
//
//    public void createSheet(double sheetNo, int index)
//    {
//        this.sheet = wb.createSheet();
//        this.styles = createStyles(wb);
//
//        if (sheetNo == 0)
//        {
//            wb.setSheetName(index, sheetName);
//        }
//        else
//        {
//            wb.setSheetName(index, sheetName + index);
//        }
//    }
//
//
//    public Object getCellValue(Row row, int column)
//    {
//        if (row == null)
//        {
//            return row;
//        }
//        Object val = "";
//        try
//        {
//            Cell cell = row.getCell(column);
//            if (StringUtils.isNotNull(cell))
//            {
//                if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA)
//                {
//                    val = cell.getNumericCellValue();
//                    if (DateUtil.isCellDateFormatted(cell))
//                    {
//                        val = DateUtil.getJavaDate((Double) val); //
//                    }
//                    else
//                    {
//                        if ((Double) val % 1 != 0)
//                        {
//                            val = new BigDecimal(val.toString());
//                        }
//                        else
//                        {
//                            val = new DecimalFormat("0").format(val);
//                        }
//                    }
//                }
//                else if (cell.getCellType() == CellType.STRING)
//                {
//                    val = cell.getStringCellValue();
//                }
//                else if (cell.getCellType() == CellType.BOOLEAN)
//                {
//                    val = cell.getBooleanCellValue();
//                }
//                else if (cell.getCellType() == CellType.ERROR)
//                {
//                    val = cell.getErrorCellValue();
//                }
//
//            }
//        }
//        catch (Exception e)
//        {
//            return val;
//        }
//        return val;
//    }
//
//
//    private boolean isRowEmpty(Row row)
//    {
//        if (row == null)
//        {
//            return true;
//        }
//        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
//        {
//            Cell cell = row.getCell(i);
//            if (cell != null && cell.getCellType() != CellType.BLANK)
//            {
//                return false;
//            }
//        }
//        return true;
//    }
//
//
//    public static Map<String, PictureData> getSheetPictures03(HSSFSheet sheet, HSSFWorkbook workbook)
//    {
//        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
//        List<HSSFPictureData> pictures = workbook.getAllPictures();
//        if (!pictures.isEmpty())
//        {
//            for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren())
//            {
//                HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
//                if (shape instanceof HSSFPicture)
//                {
//                    HSSFPicture pic = (HSSFPicture) shape;
//                    int pictureIndex = pic.getPictureIndex() - 1;
//                    HSSFPictureData picData = pictures.get(pictureIndex);
//                    String picIndex = anchor.getRow1() + "_" + String.valueOf(anchor.getCol1());
//                    sheetIndexPicMap.put(picIndex, picData);
//                }
//            }
//            return sheetIndexPicMap;
//        }
//        else
//        {
//            return sheetIndexPicMap;
//        }
//    }
//
//
//    public static Map<String, PictureData> getSheetPictures07(XSSFSheet sheet, XSSFWorkbook workbook)
//    {
//        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
//        for (POIXMLDocumentPart dr : sheet.getRelations())
//        {
//            if (dr instanceof XSSFDrawing)
//            {
//                XSSFDrawing drawing = (XSSFDrawing) dr;
//                List<XSSFShape> shapes = drawing.getShapes();
//                for (XSSFShape shape : shapes)
//                {
//                    if (shape instanceof XSSFPicture)
//                    {
//                        XSSFPicture pic = (XSSFPicture) shape;
//                        XSSFClientAnchor anchor = pic.getPreferredSize();
//                        CTMarker ctMarker = anchor.getFrom();
//                        String picIndex = ctMarker.getRow() + "_" + ctMarker.getCol();
//                        sheetIndexPicMap.put(picIndex, pic.getPictureData());
//                    }
//                }
//            }
//        }
//        return sheetIndexPicMap;
//    }
}
