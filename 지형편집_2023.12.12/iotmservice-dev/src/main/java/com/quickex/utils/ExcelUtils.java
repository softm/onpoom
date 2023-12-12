package com.quickex.utils;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExcelUtils {
    private static final String REG = "\\{([a-zA-Z_]+)\\}";
    //private static final String REG_LIST = "\\{\\.([a-zA-Z_]+)\\}";
    private static final String REG_LIST = "\\{\\.([a-zA-Z0-9_]+)\\}";
    private static final Pattern PATTERN = Pattern.compile(REG);
    private static final Pattern PATTERN_LIST = Pattern.compile(REG_LIST);

    private ExcelUtils() { }

    public static byte[] writeExcel(File templateFile, Map<String, Object> context,
                                    List<Map<String, Object>> dataList) {
        try (Workbook workbook = WorkbookFactory.create(FileUtil.getInputStream(templateFile))) {
            Sheet sheet = workbook.getSheetAt(0);
            int listStartRowNum = -1;
            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null && cell.getCellType() == CellType.STRING.getCode()) {
                            String cellValue = cell.getStringCellValue();

                            if (listStartRowNum == -1 && cellValue.matches(REG_LIST)) {
                                listStartRowNum = i;
                            }

                            Object newValue = cellValue;
                            Matcher matcher = PATTERN.matcher(cellValue);
                            while (matcher.find()) {
                                String replaceExp = matcher.group();
                                String key = matcher.group(1);
                                Object replaceValue = context.get(key);
                                if (replaceValue == null) {
                                    replaceValue = "";
                                }
                                if (replaceExp.equals(cellValue)) {
                                    newValue = replaceValue;
                                } else {
                                    newValue = ((String) newValue).replace(replaceExp, replaceValue.toString());
                                }
                            }
                            setCellValue(cell, newValue);

                        }

                    }

                }
            }
            if (-1 != listStartRowNum) {
                Row listStartRow = sheet.getRow(listStartRowNum);
                if (CollectionUtils.isEmpty(dataList)) {
                    for (int i = 0; i < listStartRow.getLastCellNum(); i++) {
                        Cell cell = listStartRow.getCell(i);
                        if (cell != null) {
                            cell.setCellValue("");
                        }
                    }
                } else {
                    int lastCellNum = listStartRow.getLastCellNum();
                    if (listStartRowNum + 1 <= sheet.getLastRowNum()) {
                        sheet.shiftRows(listStartRowNum + 1, sheet.getLastRowNum(), dataList.size(), true, false);
                    }
                    for (int i = 0; i < dataList.size(); i++) {
                        Map<String, Object> map = dataList.get(i);
                        int newRowNum = listStartRowNum + i + 1;
                        Row newRow = sheet.createRow(newRowNum);
                        for (int j = 0; j < lastCellNum; j++) {
                            Cell cell = listStartRow.getCell(j);

                            if (cell != null) {
                                Cell newCell = newRow.createCell(j);
                                newCell.setCellStyle(cell.getCellStyle());

                                if (cell.getCellType() == CellType.STRING.getCode()
                                        && cell.getStringCellValue().matches(REG_LIST)) {
                                    String cellExp = cell.getStringCellValue();
                                    Matcher matcher = PATTERN_LIST.matcher(cellExp);
                                    matcher.find();
                                    String key = matcher.group(1);
                                    Object newValue = map.get(key);
                                    if (newValue == null) {
                                        newValue = "";
                                    }
                                    setCellValue(newCell, newValue);
                                } else {
                                    int cellType = cell.getCellType();
                                    if (cellType == CellType.NUMERIC.getCode()) {
                                        newCell.setCellValue(cell.getNumericCellValue());
                                    } else if (cellType == CellType.BOOLEAN.getCode()) {
                                        newCell.setCellValue(cell.getBooleanCellValue());
                                    } else if (cellType == CellType.STRING.getCode()) {
                                        newCell.setCellValue(cell.getStringCellValue());
                                    } else if (cellType == CellType.FORMULA.getCode()) {

                                    } else {
                                        newCell.setCellValue(cell.getStringCellValue());
                                    }
                                }
                            }
                        }
                    }
                    sheet.removeRow(listStartRow);
                    sheet.shiftRows(listStartRowNum + 1, sheet.getLastRowNum(), -1, true, false);

                    for (int i = 0; i < lastCellNum; i++) {
                        CellRangeAddress mergedRangeAddress = getMergedRangeAddress(sheet, listStartRowNum, i);
                        if (mergedRangeAddress != null) {
                            i = mergedRangeAddress.getLastColumn();
                            for (int j = 1; j < dataList.size(); j++) {
                                int newRowNum = listStartRowNum + j;
                                sheet.addMergedRegionUnsafe(new CellRangeAddress(newRowNum, newRowNum,
                                        mergedRangeAddress.getFirstColumn(), mergedRangeAddress.getLastColumn()));
                            }
                        }
                    }
                }
            }

            sheet.setForceFormulaRecalculation(true);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new ExcelException("excel fail!", e);
        }
    }

    private static void setCellValue(Cell cell, Object value) {
        if (value instanceof Number) {
            cell.setCellValue(Double.parseDouble(value.toString()));
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(value.toString());
        }
    }

    private static CellRangeAddress getMergedRangeAddress(Sheet sheet, int row, int column) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress cellAddresses : mergedRegions) {
            if (row >= cellAddresses.getFirstRow() && row <= cellAddresses.getLastRow()
                    && column >= cellAddresses.getFirstColumn() && column <= cellAddresses.getLastColumn()) {
                return cellAddresses;
            }
        }
        return null;
    }

    public static byte[] writeMultiList(File templateFile, Map<String, Object> context,
                                        List<List<Map<String, Object>>> dataLists) {
        try {
            File temp = templateFile;
            for (List<Map<String, Object>> dataList : dataLists) {
                byte[] tempBytes = writeExcel(temp, context, dataList);
                temp = File.createTempFile("multi_excel", ".excel");
                FileUtils.writeByteArrayToFile(temp, tempBytes);
            }
            return FileUtils.readFileToByteArray(temp);
        } catch (ExcelException e) {
            throw e;
        } catch (Exception e) {
            throw new ExcelException("Excel fail！", e);
        }
    }

    static class ExcelException extends RuntimeException {

        private static final long serialVersionUID = -2772261598232964002L;

        public ExcelException(String msg, Throwable e) {
            super(msg, e);
        }

        public ExcelException(String msg) {
            super(msg);
        }
    }
}
