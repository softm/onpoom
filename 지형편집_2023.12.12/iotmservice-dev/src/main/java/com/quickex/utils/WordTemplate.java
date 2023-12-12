package com.quickex.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.PositionInParagraph;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;


public class WordTemplate {

    private XWPFDocument document;

    public XWPFDocument getDocument() {
        return document;
    }

    public void setDocument(XWPFDocument document) {
        this.document = document;
    }

    public WordTemplate(InputStream inputStream) throws IOException {
        document = new XWPFDocument(inputStream);
    }

    public void write(OutputStream outputStream) throws IOException {
        document.write(outputStream);
    }

    public void replaceDocument(Map<String, Object> dataMap) {

        if (!dataMap.containsKey("parametersMap")) {
            System.out.println("Data source error -- missing data source (parametersmap)");
            return;
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> parametersMap = (Map<String, Object>) dataMap
                .get("parametersMap");

        List<IBodyElement> bodyElements = document.getBodyElements();
        int templateBodySize = bodyElements.size();
        int curT = 0;
        int curP = 0;
        for (int a = 0; a < templateBodySize; a++) {
            IBodyElement body = bodyElements.get(a);
            if (BodyElementType.TABLE.equals(body.getElementType())) {
                XWPFTable table = body.getBody().getTableArray(curT);
                List<XWPFTable> tables = body.getBody().getTables();
                table = tables.get(curT);
                if (table != null) {
                    List<XWPFTableCell> tableCells = table.getRows().get(0).getTableCells();
                    String tableText = table.getText();
                    if (tableText.indexOf("##{foreach") > -1) {
                        if (tableCells.size() != 2
                                || tableCells.get(0).getText().indexOf("##{foreach") < 0
                                || tableCells.get(0).getText().trim().length() == 0) {
                            System.out
                                    .println("Section in the document"
                                            + (curT + 1)
                                            + "Table template error, the first row of the template table needs to be set with 2 cells，"
                                            + "The first cell stores the table type(##{foreachTable}## or ##{foreachTableRow}##)，The second cell defines the data source。");
                            return;
                        }

                        String tableType = tableCells.get(0).getText();
                        String dataSource = tableCells.get(1).getText();
                        System.out.println("Read to data source："+dataSource);
                        if (!dataMap.containsKey(dataSource)) {
                            System.out.println("Section in the document" + (curT + 1) + "Table template data sources missing");
                            return;
                        }
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> tableDataList = (List<Map<String, Object>>) dataMap
                                .get(dataSource);
                        if ("##{foreachTable}##".equals(tableType)) {
                            addTableInDocFooter(table, tableDataList, parametersMap, 1);
                        } else if ("##{foreachTableRow}##".equals(tableType)) {
                            addTableInDocFooter(table, tableDataList, parametersMap, 2);
                        }
                    } else if (tableText.indexOf("{") > -1) {
                        addTableInDocFooter(table, null, parametersMap, 3);
                    } else {
                        addTableInDocFooter(table, null, null, 0);
                    }
                    curT++;
                }
            } else if (BodyElementType.PARAGRAPH.equals(body.getElementType())) {
                XWPFParagraph ph = body.getBody().getParagraphArray(curP);
                if (ph != null) {
                    addParagraphInDocFooter(ph, null, parametersMap, 0);
                    curP++;
                }
            }
        }
        for (int a = 0; a < templateBodySize; a++) {
            document.removeBodyElement(0);
        }
    }

    public void addTableInDocFooter(XWPFTable templateTable, List<Map<String, Object>> list, Map<String, Object> parametersMap, int flag) {

        if (flag == 1) {
            for (Map<String, Object> map : list) {
                List<XWPFTableRow> templateTableRows = templateTable.getRows();
                XWPFTable newCreateTable = document.createTable();
                for (int i = 1; i < templateTableRows.size(); i++) {
                    XWPFTableRow newCreateRow = newCreateTable.createRow();
                    CopyTableRow(newCreateRow, templateTableRows.get(i));
                }
                newCreateTable.removeRow(0);
                document.createParagraph();
                replaceTable(newCreateTable, map);
            }
        } else if (flag == 2) {
            XWPFTable newCreateTable = document.createTable();
            List<XWPFTableRow> TempTableRows = templateTable.getRows();
            int tagRowsIndex = 0;
            for (int i = 0, size = TempTableRows.size(); i < size; i++) {
                String rowText = TempTableRows.get(i).getCell(0).getText();
                if (rowText.indexOf("##{foreachRows}##") > -1) {
                    tagRowsIndex = i;
                    break;
                }
            }
            for (int i = 1; i < tagRowsIndex; i++) {
                XWPFTableRow newCreateRow = newCreateTable.createRow();
                CopyTableRow(newCreateRow, TempTableRows.get(i));
                replaceTableRow(newCreateRow, parametersMap);
            }
            XWPFTableRow tempRow = TempTableRows.get(tagRowsIndex + 1);
            for (int i = 0; i < list.size(); i++) {
                XWPFTableRow newCreateRow = newCreateTable.createRow();
                CopyTableRow(newCreateRow, tempRow);
                replaceTableRow(newCreateRow, list.get(i));
            }
            for (int i = tagRowsIndex + 2; i < TempTableRows.size(); i++) {
                XWPFTableRow newCreateRow = newCreateTable.createRow();
                CopyTableRow(newCreateRow, TempTableRows.get(i));
                replaceTableRow(newCreateRow, parametersMap);
            }
            newCreateTable.removeRow(0);
            document.createParagraph();
        } else if (flag == 3) {
            List<XWPFTableRow> templateTableRows = templateTable.getRows();
            XWPFTable newCreateTable = document.createTable();
            for (int i = 0; i < templateTableRows.size(); i++) {
                XWPFTableRow newCreateRow = newCreateTable.createRow();
                CopyTableRow(newCreateRow, templateTableRows.get(i));
            }
            newCreateTable.removeRow(0);
            document.createParagraph();
            replaceTable(newCreateTable, parametersMap);

        } else if (flag == 0) {
            List<XWPFTableRow> templateTableRows = templateTable.getRows();
            XWPFTable newCreateTable = document.createTable();
            for (int i = 0; i < templateTableRows.size(); i++) {
                XWPFTableRow newCreateRow = newCreateTable.createRow();
                CopyTableRow(newCreateRow, templateTableRows.get(i));
            }
            newCreateTable.removeRow(0);
            document.createParagraph();
        }

    }

    public void addParagraphInDocFooter(XWPFParagraph templateParagraph,List<Map<String, String>> list, Map<String, Object> parametersMap, int flag) {
        if (flag == 0) {
            XWPFParagraph createParagraph = document.createParagraph();
            createParagraph.getCTP().setPPr(templateParagraph.getCTP().getPPr());
            for (int pos = 0; pos < createParagraph.getRuns().size(); pos++) {
                createParagraph.removeRun(pos);
            }
            for (XWPFRun s : templateParagraph.getRuns()) {
                XWPFRun targetrun = createParagraph.createRun();
                CopyRun(targetrun, s);
            }
            replaceParagraph(createParagraph, parametersMap);
        } else if (flag == 1) {
            //
        }
    }

    public void replaceParagraph(XWPFParagraph xWPFParagraph, Map<String, Object> parametersMap) {
        List<XWPFRun> runs = xWPFParagraph.getRuns();
        String xWPFParagraphText = xWPFParagraph.getText();
        String regEx = "\\{.+?\\}";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(xWPFParagraphText);
        if (matcher.find()) {
            int beginRunIndex = xWPFParagraph.searchText("{", new PositionInParagraph()).getBeginRun();
            int endRunIndex = xWPFParagraph.searchText("}", new PositionInParagraph()).getEndRun();
            StringBuffer key = new StringBuffer();
            if (beginRunIndex == endRunIndex) {
                XWPFRun beginRun = runs.get(beginRunIndex);
                String beginRunText = beginRun.text();
                int beginIndex = beginRunText.indexOf("{");
                int endIndex = beginRunText.indexOf("}");
                int length = beginRunText.length();
                if (beginIndex == 0 && endIndex == length - 1) {
                    XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
                    insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
                    key.append(beginRunText.substring(1, endIndex));
                    insertNewRun.setText(getValueBykey(key.toString(),parametersMap));
                    xWPFParagraph.removeRun(beginRunIndex + 1);
                } else {
                    XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
                    insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
                    key.append(beginRunText.substring(beginRunText.indexOf("{")+1, beginRunText.indexOf("}")));
                    String textString=beginRunText.substring(0, beginIndex) + getValueBykey(key.toString(),parametersMap)
                            + beginRunText.substring(endIndex + 1);
                    insertNewRun.setText(textString);
                    xWPFParagraph.removeRun(beginRunIndex + 1);
                }
            }else {
                XWPFRun beginRun = runs.get(beginRunIndex);
                String beginRunText = beginRun.text();
                int beginIndex = beginRunText.indexOf("{");
                if (beginRunText.length()>1  ) {
                    key.append(beginRunText.substring(beginIndex+1));
                }
                ArrayList<Integer> removeRunList = new ArrayList<>();
                for (int i = beginRunIndex + 1; i < endRunIndex; i++) {
                    XWPFRun run = runs.get(i);
                    String runText = run.text();
                    key.append(runText);
                    removeRunList.add(i);
                }
                XWPFRun endRun = runs.get(endRunIndex);
                String endRunText = endRun.text();
                int endIndex = endRunText.indexOf("}");
                if (endRunText.length()>1 && endIndex!=0) {
                    key.append(endRunText.substring(0,endIndex));
                }
                if (beginRunText.length()==2 ) {
                    XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
                    insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
                    insertNewRun.setText(getValueBykey(key.toString(),parametersMap));
                    xWPFParagraph.removeRun(beginRunIndex + 1);
                }else {
                    XWPFRun insertNewRun = xWPFParagraph.insertNewRun(beginRunIndex);
                    insertNewRun.getCTR().setRPr(beginRun.getCTR().getRPr());
                    String textString=beginRunText.substring(0,beginRunText.indexOf("{"))+getValueBykey(key.toString(),parametersMap);
                    insertNewRun.setText(textString);
                    xWPFParagraph.removeRun(beginRunIndex + 1);
                }
                if (endRunText.length()==1 ) {
                    XWPFRun insertNewRun = xWPFParagraph.insertNewRun(endRunIndex);
                    insertNewRun.getCTR().setRPr(endRun.getCTR().getRPr());
                    insertNewRun.setText("");
                    xWPFParagraph.removeRun(endRunIndex + 1);
                }else {
                    XWPFRun insertNewRun = xWPFParagraph.insertNewRun(endRunIndex);
                    insertNewRun.getCTR().setRPr(endRun.getCTR().getRPr());
                    String textString=endRunText.substring(endRunText.indexOf("}")+1);
                    insertNewRun.setText(textString);
                    xWPFParagraph.removeRun(endRunIndex + 1);
                }
                for (int i = 0; i < removeRunList.size(); i++) {
                    XWPFRun xWPFRun = runs.get(removeRunList.get(i));
                    XWPFRun insertNewRun = xWPFParagraph.insertNewRun(removeRunList.get(i));
                    insertNewRun.getCTR().setRPr(xWPFRun.getCTR().getRPr());
                    insertNewRun.setText("");
                    xWPFParagraph.removeRun(removeRunList.get(i) + 1);
                }
            }
            replaceParagraph( xWPFParagraph, parametersMap);
        }
    }

    private void CopyTableRow(XWPFTableRow target, XWPFTableRow source) {
        int tempRowCellsize = source.getTableCells().size();
        for (int i = 0; i < tempRowCellsize - 1; i++) {
            target.addNewTableCell();
        }
        target.getCtRow().setTrPr(source.getCtRow().getTrPr());
        for (int i = 0; i < target.getTableCells().size(); i++) {
            copyTableCell(target.getCell(i), source.getCell(i));
        }
    }

    private void copyTableCell(XWPFTableCell newTableCell, XWPFTableCell templateTableCell) {
        newTableCell.getCTTc().setTcPr(templateTableCell.getCTTc().getTcPr());
        for (int pos = 0; pos < newTableCell.getParagraphs().size(); pos++) {
            newTableCell.removeParagraph(pos);
        }
        for (XWPFParagraph sp : templateTableCell.getParagraphs()) {
            XWPFParagraph targetP = newTableCell.addParagraph();
            copyParagraph(targetP, sp);
        }
    }

    private void copyParagraph(XWPFParagraph newParagraph, XWPFParagraph templateParagraph) {
        newParagraph.getCTP().setPPr(templateParagraph.getCTP().getPPr());
        for (int pos = 0; pos < newParagraph.getRuns().size(); pos++) {
            newParagraph.removeRun(pos);

        }
        for (XWPFRun s : templateParagraph.getRuns()) {
            XWPFRun targetrun = newParagraph.createRun();
            CopyRun(targetrun, s);
        }
    }

    private void CopyRun(XWPFRun newRun, XWPFRun templateRun) {
        newRun.getCTR().setRPr(templateRun.getCTR().getRPr());
        newRun.setText(templateRun.text());
    }

    public void replaceTableRow(XWPFTableRow tableRow, Map<String, Object> parametersMap) {
        List<XWPFTableCell> tableCells = tableRow.getTableCells();
        for (XWPFTableCell xWPFTableCell : tableCells) {
            List<XWPFParagraph> paragraphs = xWPFTableCell.getParagraphs();
            for (XWPFParagraph xwpfParagraph : paragraphs) {
                replaceParagraph(xwpfParagraph, parametersMap);
            }
        }
    }

    public void replaceTable(XWPFTable xwpfTable,Map<String, Object> parametersMap){
        List<XWPFTableRow> rows = xwpfTable.getRows();
        for (XWPFTableRow xWPFTableRow : rows ) {
            List<XWPFTableCell> tableCells = xWPFTableRow.getTableCells();
            for (XWPFTableCell xWPFTableCell : tableCells ) {
                List<XWPFParagraph> paragraphs2 = xWPFTableCell.getParagraphs();
                for (XWPFParagraph xWPFParagraph : paragraphs2) {
                    replaceParagraph(xWPFParagraph, parametersMap);
                }
            }
        }
    }

    private String getValueBykey(String key, Map<String, Object> map) {
        String returnValue="";
        if (key != null) {
            try {
                returnValue=map.get(key)!=null ? map.get(key).toString() : "";
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("key:"+key+"***"+e);
                returnValue="";
            }

        }
        return returnValue;
    }

    public void replaceDocumentImage(String key, List<FileInputStream> imageList, int width,int heigth) throws Exception{
        for (XWPFTable table : this.getDocument().getTables()) {
            List<XWPFTableRow> rows = table.getRows();
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> tableCells = row.getTableCells();
                for (XWPFTableCell cell : tableCells) {
                    if (cell.getText().equals(key)) {
                        cell.removeParagraph(0);   //delete key
                        XWPFParagraph p11 = cell.addParagraph();  //add pic
                        XWPFRun run = p11.createRun();
                        for (int i = 0; i < imageList.size(); i++) {
                            run.addPicture(imageList.get(i), XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(width), Units.toEMU(heigth));
                        }
                    }
                }
            }
        }
    }

    public void setStringKeyIsNull(String key) throws Exception {
        for (XWPFTable table : this.getDocument().getTables()) {
            List<XWPFTableRow> rows = table.getRows();
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> tableCells = row.getTableCells();
                for (XWPFTableCell cell : tableCells) {
                    if (cell.getText().equals(key)) {
                        //cell.removeParagraph(0);   //delete key
                        for (int i = 0; i < cell.getParagraphs().size(); i++) {
                            cell.removeParagraph(i);   //delete key
                        }
                    }
                }
            }
        }
    }

}
