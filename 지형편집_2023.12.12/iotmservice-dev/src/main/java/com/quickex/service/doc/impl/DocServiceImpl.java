package com.quickex.service.doc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quickex.config.AppConfig;
import com.quickex.core.result.R;
import com.quickex.service.doc.IDocService;
import com.quickex.utils.CommonUtils;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.apache.xmlbeans.XmlCursor;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Service
public class DocServiceImpl implements IDocService {

    @Autowired
    private AppConfig appConfig;

    public R download1(JSONObject object) {

        try {

            XWPFDocument document = new XWPFDocument();

            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("분석결과(문화재 구역 기준)");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);

            //
            XWPFTable infoTable = document.createTable();

            //
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));

            //
            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("구분");
            infoTableRowOne.addNewTableCell().setText("분석결과");

            //
            infoTableRowOne.getCell(0).setColor("D9D9D9");
            infoTableRowOne.getCell(1).setColor("D9D9D9");

            //
            infoTableRowOne.setHeight(400);

            //
            CTTcPr tcpr = infoTableRowOne.getCell(0).getCTTc().addNewTcPr();
            CTTblWidth cellw = tcpr.addNewTcW();
            cellw.setType(STTblWidth.DXA);
            cellw.setW(BigInteger.valueOf(360*5));

            //
            CTTcPr tcpr2 = infoTableRowOne.getCell(0).getCTTc().addNewTcPr();
            CTTblWidth cellw2 = tcpr2.addNewTcW();
            cellw2.setType(STTblWidth.DXA);
            cellw2.setW(BigInteger.valueOf(8000));

            JSONArray list1 = object.getJSONArray("list");
            for (int j = 0; j < list1.size(); j++) {
                //
                XWPFTableRow infoTableRowTwo = infoTable.createRow();
                infoTableRowTwo.getCell(0).setText("구역" + (j + 1));

                // infoTableRowTwo.getCell(1).setText(" ");

                //
                XWPFParagraph xwpfParagraph = infoTableRowTwo.getCell(1).addParagraph();

                XWPFRun rt1 = xwpfParagraph.createRun();
                rt1.addBreak();
                rt1.addBreak();
                rt1.setFontSize(10);
                rt1.setBold(true);
                rt1.setText("1.총면적:["+list1.getJSONObject(j).getString("value1")+"]평방미터".trim());
                rt1.addBreak();
                rt1.setText("2.구역내필지:총["+list1.getJSONObject(j).getString("value2")+"]필지".trim());
                rt1.addBreak();
                rt1.setText("3.구역내건물:총["+list1.getJSONObject(j).getString("value3")+"]동".trim());
                rt1.addBreak();
                rt1.setText("4.허용기준위반건물:["+list1.getJSONObject(j).getString("value4")+"]동");
                rt1.addBreak();

                //
                XmlCursor cursor = xwpfParagraph.getCTP().newCursor();
                cursor.isEnd();
                //
                XWPFTable xwpfTable = infoTableRowTwo.getCell(1).insertNewTbl(cursor);


                //
                CTTblWidth xwpfTableWidth = xwpfTable.getCTTbl().addNewTblPr().addNewTblW();
                xwpfTableWidth.setType(STTblWidth.DXA);
                xwpfTableWidth.setW(BigInteger.valueOf(8000));

                //
                CTTblBorders borders = xwpfTable.getCTTbl().getTblPr().addNewTblBorders();
                CTBorder hBorder = borders.addNewInsideH();
                hBorder.setVal(STBorder.Enum.forString("single"));  //
                hBorder.setSz(new BigInteger("1")); //
                hBorder.setColor("000000"); //

                CTBorder vBorder = borders.addNewInsideV();
                vBorder.setVal(STBorder.Enum.forString("single"));
                vBorder.setSz(new BigInteger("1"));
                vBorder.setColor("000000");

                CTBorder lBorder = borders.addNewLeft();
                lBorder.setVal(STBorder.Enum.forString("single"));
                lBorder.setSz(new BigInteger("1"));
                lBorder.setColor("000000");

                CTBorder rBorder = borders.addNewRight();
                rBorder.setVal(STBorder.Enum.forString("single"));
                rBorder.setSz(new BigInteger("1"));
                rBorder.setColor("000000");

                CTBorder tBorder = borders.addNewTop();
                tBorder.setVal(STBorder.Enum.forString("single"));
                tBorder.setSz(new BigInteger("1"));
                tBorder.setColor("000000");

                CTBorder bBorder = borders.addNewBottom();
                bBorder.setVal(STBorder.Enum.forString("single"));
                bBorder.setSz(new BigInteger("1"));
                bBorder.setColor("000000");

                XWPFTableRow xwpfTableRow = xwpfTable.createRow();

                xwpfTableRow.setHeight(300); //

                //
                xwpfTableRow.addNewTableCell().setText("문화제명");
                xwpfTableRow.addNewTableCell().setText("구역번호");
                xwpfTableRow.addNewTableCell().setText("기준높이");
                xwpfTableRow.addNewTableCell().setText("위반 높이");
                //xwpfTableRow.addNewTableCell().setText("용도지역/지구");


                //
                xwpfTableRow.getCell(0).setColor("D9D9D9");
                xwpfTableRow.getCell(1).setColor("D9D9D9");
                xwpfTableRow.getCell(2).setColor("D9D9D9");
                xwpfTableRow.getCell(3).setColor("D9D9D9");
                //xwpfTableRow.getCell(4).setColor("D9D9D9");

                JSONArray list2 = list1.getJSONObject(j).getJSONArray("list");
                //
                for (int i = 0; i < list2.size(); i++) {
                    XWPFTableRow row2 = xwpfTable.createRow();
                    row2.setHeight(300);  //
                    row2.getCell(0).setText(list2.getJSONObject(i).getString("c1"));
                    row2.getCell(1).setText(list2.getJSONObject(i).getString("c2"));
                    row2.getCell(2).setText(list2.getJSONObject(i).getString("c3"));
                    row2.getCell(3).setText(list2.getJSONObject(i).getString("c4"));
                    //row2.getCell(4).setText(list2.getJSONObject(i).getString("c5"));
                }
            }

            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();

            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + docName + ".docx";

            //
            FileOutputStream out = new FileOutputStream(new File(docSavePath));
            document.write(out);
            out.close();

            JSONObject result = new JSONObject();
            result.put("url", "/" + docName + ".docx");
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R download2(JSONObject object) {
        try {

            XWPFDocument document = new XWPFDocument();

            //
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("분석결과(신규배치 건물 기준)");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);

            //
            XWPFTable infoTable = document.createTable();

            //
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));

            //
            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("구분");
            infoTableRowOne.addNewTableCell().setText("분석결과");

            //
            infoTableRowOne.getCell(0).setColor("D9D9D9");
            infoTableRowOne.getCell(1).setColor("D9D9D9");

            //
            infoTableRowOne.setHeight(400);

            //
            CTTcPr tcpr = infoTableRowOne.getCell(0).getCTTc().addNewTcPr();
            CTTblWidth cellw = tcpr.addNewTcW();
            cellw.setType(STTblWidth.DXA);
            cellw.setW(BigInteger.valueOf(360*5));

            //
            CTTcPr tcpr2 = infoTableRowOne.getCell(0).getCTTc().addNewTcPr();
            CTTblWidth cellw2 = tcpr2.addNewTcW();
            cellw2.setType(STTblWidth.DXA);
            cellw2.setW(BigInteger.valueOf(8000));


            JSONArray list1 = object.getJSONArray("list");

            for (int j = 0; j < list1.size(); j++) {
                //
                XWPFTableRow infoTableRowTwo = infoTable.createRow();
                infoTableRowTwo.getCell(0).setText("신규건물" + (j + 1));

                infoTableRowTwo.getCell(1).setText(" ");

                //
                XWPFParagraph xwpfParagraph = infoTableRowTwo.getCell(1).addParagraph();

                XWPFRun rt1 = xwpfParagraph.createRun();
                rt1.addBreak();
                rt1.addBreak();
                rt1.setFontSize(10);
                rt1.setTextPosition(0);
                rt1.setBold(true);
                rt1.setText("1.적용문화재구역:"+list1.getJSONObject(j).getString("value1").toString().trim());
                rt1.addBreak();
                rt1.setText("2.분석결과:허용기준이상건축물".trim());
                rt1.addBreak();


                //
                XmlCursor cursor = xwpfParagraph.getCTP().newCursor();
                cursor.isEnd();
                //
                XWPFTable xwpfTable = infoTableRowTwo.getCell(1).insertNewTbl(cursor);


                //
                CTTblWidth xwpfTableWidth = xwpfTable.getCTTbl().addNewTblPr().addNewTblW();
                xwpfTableWidth.setType(STTblWidth.DXA);
                xwpfTableWidth.setW(BigInteger.valueOf(8000));

                //
                CTTblBorders borders = xwpfTable.getCTTbl().getTblPr().addNewTblBorders();
                CTBorder hBorder = borders.addNewInsideH();
                hBorder.setVal(STBorder.Enum.forString("single"));  //
                hBorder.setSz(new BigInteger("1")); //
                hBorder.setColor("000000"); //

                CTBorder vBorder = borders.addNewInsideV();
                vBorder.setVal(STBorder.Enum.forString("single"));
                vBorder.setSz(new BigInteger("1"));
                vBorder.setColor("000000");

                CTBorder lBorder = borders.addNewLeft();
                lBorder.setVal(STBorder.Enum.forString("single"));
                lBorder.setSz(new BigInteger("1"));
                lBorder.setColor("000000");

                CTBorder rBorder = borders.addNewRight();
                rBorder.setVal(STBorder.Enum.forString("single"));
                rBorder.setSz(new BigInteger("1"));
                rBorder.setColor("000000");

                CTBorder tBorder = borders.addNewTop();
                tBorder.setVal(STBorder.Enum.forString("single"));
                tBorder.setSz(new BigInteger("1"));
                tBorder.setColor("000000");

                CTBorder bBorder = borders.addNewBottom();
                bBorder.setVal(STBorder.Enum.forString("single"));
                bBorder.setSz(new BigInteger("1"));
                bBorder.setColor("000000");

                XWPFTableRow xwpfTableRow = xwpfTable.createRow();

                xwpfTableRow.setHeight(300); //

                //
                xwpfTableRow.addNewTableCell().setText("지번주소");
                xwpfTableRow.addNewTableCell().setText("건물종류");
                xwpfTableRow.addNewTableCell().setText("허용기준");
                xwpfTableRow.addNewTableCell().setText("건물높이");
                xwpfTableRow.addNewTableCell().setText("분석결과");
                xwpfTableRow.addNewTableCell().setText("용도지역/지구");

                //
                xwpfTableRow.getCell(0).setColor("D9D9D9");
                xwpfTableRow.getCell(1).setColor("D9D9D9");
                xwpfTableRow.getCell(2).setColor("D9D9D9");
                xwpfTableRow.getCell(3).setColor("D9D9D9");
                xwpfTableRow.getCell(4).setColor("D9D9D9");
                xwpfTableRow.getCell(5).setColor("D9D9D9");

                JSONArray list2 = list1.getJSONObject(j).getJSONArray("list");
                //
                for (int i = 0; i < list2.size(); i++) {
                    XWPFTableRow row2 = xwpfTable.createRow();
                    row2.setHeight(300);  //
                    row2.getCell(0).setText(list2.getJSONObject(i).getString("c1"));
                    row2.getCell(1).setText(list2.getJSONObject(i).getString("c2"));
                    row2.getCell(2).setText(list2.getJSONObject(i).getString("c3"));
                    row2.getCell(3).setText(list2.getJSONObject(i).getString("c4"));
                    row2.getCell(4).setText(list2.getJSONObject(i).getString("c5"));
                    row2.getCell(5).setText(list2.getJSONObject(i).getString("c6"));
                }
            }

            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();


            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + docName + ".docx";

            //
            FileOutputStream out = new FileOutputStream(new File(docSavePath));
            document.write(out);
            out.close();

            JSONObject result = new JSONObject();
            result.put("url", "/" + docName + ".docx");
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R download3(
            MultipartFile file11,
            MultipartFile file12,
            MultipartFile file21,
            MultipartFile file22,
            MultipartFile file31,
            MultipartFile file32,
            MultipartFile file41,
            MultipartFile file42
    ) {
        try {

            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + File.separator + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdir();
            }

            String path11, path12, path21, path22, path31, path32, path41, path42 = "";

            path11="";
            path12="";
            path21="";
            path22="";
            path31="";
            path32="";
            path41="";
            path42="";

            if(file11!=null){
                path11 =  folder + "/" + CommonUtils.getUUID() + file11.getOriginalFilename().substring(file11.getOriginalFilename().lastIndexOf("."));
            }
            if(file12!=null){
                path12 =  folder + "/" + CommonUtils.getUUID() + file12.getOriginalFilename().substring(file12.getOriginalFilename().lastIndexOf("."));
            }
            if(file21!=null){
                path21 =  folder + "/" + CommonUtils.getUUID() + file21.getOriginalFilename().substring(file21.getOriginalFilename().lastIndexOf("."));
            }
            if(file22!=null){
                path22 =  folder + "/" + CommonUtils.getUUID() + file22.getOriginalFilename().substring(file22.getOriginalFilename().lastIndexOf("."));
            }
            if(file31!=null){
                path31 =  folder + "/" + CommonUtils.getUUID() + file31.getOriginalFilename().substring(file31.getOriginalFilename().lastIndexOf("."));
            }
            if(file32!=null){
                path32 =  folder + "/" + CommonUtils.getUUID() + file32.getOriginalFilename().substring(file32.getOriginalFilename().lastIndexOf("."));
            }
            if(file41!=null){
                path41 =  folder + "/" + CommonUtils.getUUID() + file41.getOriginalFilename().substring(file41.getOriginalFilename().lastIndexOf("."));
            }
            if(file42!=null){
                path42 =  folder + "/" + CommonUtils.getUUID() + file42.getOriginalFilename().substring(file42.getOriginalFilename().lastIndexOf("."));
            }

            if(!path11.isEmpty()){
                File saveFile11 = new File(path11);
                file11.transferTo(saveFile11);
            }
            if(!path12.isEmpty()){
                File saveFile12 = new File(path12);
                file12.transferTo(saveFile12);
            }
            if(!path21.isEmpty()){
                File saveFile21 = new File(path21);
                file21.transferTo(saveFile21);
            }
            if(!path22.isEmpty()){
                File saveFile22 = new File(path22);
                file22.transferTo(saveFile22);
            }
            if(!path31.isEmpty()){
                File saveFile31 = new File(path31);
                file31.transferTo(saveFile31);
            }
            if(!path32.isEmpty()){
                File saveFile32 = new File(path32);
                file32.transferTo(saveFile32);
            }
            if(!path41.isEmpty()){
                File saveFile41 = new File(path41);
                file41.transferTo(saveFile41);
            }
            if(!path42.isEmpty()){
                File saveFile42 = new File(path42);
                file42.transferTo(saveFile42);
            }

            //   ---    ----
            //
            XWPFDocument document = new XWPFDocument();
            XWPFTable infoTable = document.createTable();

            //
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));


            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("구분");
            infoTableRowOne.addNewTableCell().setText("분석결과1");
            infoTableRowOne.addNewTableCell().setText("분석결과2");


            CTTblWidth cellw = infoTableRowOne.getCell(0).getCTTc().addNewTcPr().addNewTcW();
            cellw.setType(STTblWidth.DXA);
            cellw.setW(BigInteger.valueOf(60));

            CTTblWidth cellw1 = infoTableRowOne.getCell(1).getCTTc().addNewTcPr().addNewTcW();
            cellw1.setType(STTblWidth.DXA);
            cellw1.setW(BigInteger.valueOf(600));

            CTTblWidth cellw2 = infoTableRowOne.getCell(2).getCTTc().addNewTcPr().addNewTcW();
            cellw2.setType(STTblWidth.DXA);
            cellw2.setW(BigInteger.valueOf(600));


            infoTableRowOne.getCell(0).setColor("D9D9D9");
            infoTableRowOne.getCell(1).setColor("D9D9D9");
            infoTableRowOne.getCell(2).setColor("D9D9D9");


            infoTableRowOne.setHeight(400);


            XWPFTableRow infoTableRow1 = infoTable.createRow();
            infoTableRow1.setHeight(2600);
            infoTableRow1.getCell(0).setText("북동 방향");
            infoTableRow1.getCell(1).setText("");
            infoTableRow1.getCell(2).setText("");


            XWPFTableRow infoTableRow2 = infoTable.createRow();
            infoTableRow2.setHeight(2600);
            infoTableRow2.getCell(0).setText("동남 방향");
            infoTableRow2.getCell(1).setText("");
            infoTableRow2.getCell(2).setText("");

            //
            XWPFTableRow infoTableRow3 = infoTable.createRow();
            infoTableRow3.setHeight(2600);
            infoTableRow3.getCell(0).setText("남서 방향");
            infoTableRow3.getCell(1).setText("");
            infoTableRow3.getCell(2).setText("");

            //
            XWPFTableRow infoTableRow4 = infoTable.createRow();
            infoTableRow4.setHeight(2600);
            infoTableRow4.getCell(0).setText("서북 방향");
            infoTableRow4.getCell(1).setText("");
            infoTableRow4.getCell(2).setText("");

            //1-1
            if (!path11.isEmpty()) {
                InputStream stream11 = new FileInputStream(path11);
                XWPFParagraph p11 = infoTable.getRow(1).getCell(1).addParagraph();
                XWPFRun r11 = p11.createRun();
                r11.addPicture(stream11, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //1-2
            if (!path12.isEmpty()) {
                InputStream stream12 = new FileInputStream(path12);
                XWPFParagraph p12 = infoTable.getRow(1).getCell(2).addParagraph();
                XWPFRun r12 = p12.createRun();
                r12.addPicture(stream12, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //2-1
            if (!path21.isEmpty()) {
                InputStream stream21 = new FileInputStream(path21);
                XWPFParagraph p21 = infoTable.getRow(2).getCell(1).addParagraph();
                XWPFRun r21 = p21.createRun();
                r21.addPicture(stream21, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //2-2
            if (!path22.isEmpty()) {
                InputStream stream22 = new FileInputStream(path22);
                XWPFParagraph p22 = infoTable.getRow(2).getCell(2).addParagraph();
                XWPFRun r22 = p22.createRun();
                r22.addPicture(stream22, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //3-1
            if (!path31.isEmpty()) {
                InputStream stream31 = new FileInputStream(path31);
                XWPFParagraph p31 = infoTable.getRow(3).getCell(1).addParagraph();
                XWPFRun r31 = p31.createRun();
                r31.addPicture(stream31, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //3-2
            if (!path32.isEmpty()) {
                InputStream stream32 = new FileInputStream(path32);
                XWPFParagraph p32 = infoTable.getRow(3).getCell(2).addParagraph();
                XWPFRun r32 = p32.createRun();
                r32.addPicture(stream32, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //4-1
            if (!path41.isEmpty()) {
                InputStream stream41 = new FileInputStream(path41);
                XWPFParagraph p41 = infoTable.getRow(4).getCell(1).addParagraph();
                XWPFRun r41 = p41.createRun();
                r41.addPicture(stream41, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //4-2
            if (!path42.isEmpty()) {
                InputStream stream42 = new FileInputStream(path42);
                XWPFParagraph p42 = infoTable.getRow(4).getCell(2).addParagraph();
                XWPFRun r42 = p42.createRun();
                r42.addPicture(stream42, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            //XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + docName + ".docx";

            //
            FileOutputStream out = new FileOutputStream(new File(docSavePath));
            document.write(out);
            out.close();

            JSONObject result = new JSONObject();
            result.put("url","/" + docName + ".docx");

            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }

    }

    public R download4(
            MultipartFile file11,
            MultipartFile file12,
            MultipartFile file21,
            MultipartFile file22,
            MultipartFile file31,
            MultipartFile file32,
            MultipartFile file41,
            MultipartFile file42
    ){
        try {

            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + File.separator + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdir();
            }

            String path11, path12, path21, path22, path31, path32, path41, path42 = "";

            path11="";
            path12="";
            path21="";
            path22="";
            path31="";
            path32="";
            path41="";
            path42="";

            if(file11!=null && file11.getSize()!=0){
                path11 =  folder + "/" + CommonUtils.getUUID() + file11.getOriginalFilename().substring(file11.getOriginalFilename().lastIndexOf("."));
            }
            if(file12!=null && file12.getSize()!=0){
                path12 =  folder + "/" + CommonUtils.getUUID() + file12.getOriginalFilename().substring(file12.getOriginalFilename().lastIndexOf("."));
            }
            if(file21!=null && file21.getSize()!=0){
                path21 =  folder + "/" + CommonUtils.getUUID() + file21.getOriginalFilename().substring(file21.getOriginalFilename().lastIndexOf("."));
            }
            if(file22!=null && file22.getSize()!=0){
                path22 =  folder + "/" + CommonUtils.getUUID() + file22.getOriginalFilename().substring(file22.getOriginalFilename().lastIndexOf("."));
            }
            if(file31!=null && file31.getSize()!=0){
                path31 =  folder + "/" + CommonUtils.getUUID() + file31.getOriginalFilename().substring(file31.getOriginalFilename().lastIndexOf("."));
            }
            if(file32!=null && file32.getSize()!=0){
                path32 =  folder + "/" + CommonUtils.getUUID() + file32.getOriginalFilename().substring(file32.getOriginalFilename().lastIndexOf("."));
            }
            if(file41!=null && file41.getSize()!=0){
                path41 =  folder + "/" + CommonUtils.getUUID() + file41.getOriginalFilename().substring(file41.getOriginalFilename().lastIndexOf("."));
            }
            if(file42!=null && file42.getSize()!=0){
                path42 =  folder + "/" + CommonUtils.getUUID() + file42.getOriginalFilename().substring(file42.getOriginalFilename().lastIndexOf("."));
            }

            if(!path11.isEmpty()){
                File saveFile11 = new File(path11);
                file11.transferTo(saveFile11);
            }
            if(!path12.isEmpty()){
                File saveFile12 = new File(path12);
                file12.transferTo(saveFile12);
            }
            if(!path21.isEmpty()){
                File saveFile21 = new File(path21);
                file21.transferTo(saveFile21);
            }
            if(!path22.isEmpty()){
                File saveFile22 = new File(path22);
                file22.transferTo(saveFile22);
            }
            if(!path31.isEmpty()){
                File saveFile31 = new File(path31);
                file31.transferTo(saveFile31);
            }
            if(!path32.isEmpty()){
                File saveFile32 = new File(path32);
                file32.transferTo(saveFile32);
            }
            if(!path41.isEmpty()){
                File saveFile41 = new File(path41);
                file41.transferTo(saveFile41);
            }
            if(!path42.isEmpty()){
                File saveFile42 = new File(path42);
                file42.transferTo(saveFile42);
            }


            XWPFDocument document = new XWPFDocument();



            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("가시역 분석 보고");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);




            XWPFTable infoTable = document.createTable();


            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));


            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("구분");
            infoTableRowOne.addNewTableCell().setText("분석결과1");
            infoTableRowOne.addNewTableCell().setText("분석결과2");


            CTTblWidth cellw = infoTableRowOne.getCell(0).getCTTc().addNewTcPr().addNewTcW();
            cellw.setType(STTblWidth.DXA);
            cellw.setW(BigInteger.valueOf(60));

            CTTblWidth cellw1 = infoTableRowOne.getCell(1).getCTTc().addNewTcPr().addNewTcW();
            cellw1.setType(STTblWidth.DXA);
            cellw1.setW(BigInteger.valueOf(600));

            CTTblWidth cellw2 = infoTableRowOne.getCell(2).getCTTc().addNewTcPr().addNewTcW();
            cellw2.setType(STTblWidth.DXA);
            cellw2.setW(BigInteger.valueOf(600));


            infoTableRowOne.getCell(0).setColor("D9D9D9");
            infoTableRowOne.getCell(1).setColor("D9D9D9");
            infoTableRowOne.getCell(2).setColor("D9D9D9");


            infoTableRowOne.setHeight(400);


            XWPFTableRow infoTableRow1 = infoTable.createRow();
            infoTableRow1.setHeight(2600);
            infoTableRow1.getCell(0).setText("북동 방향");
            infoTableRow1.getCell(1).setText("");
            infoTableRow1.getCell(2).setText("");


            XWPFTableRow infoTableRow2 = infoTable.createRow();
            infoTableRow2.setHeight(2600);
            infoTableRow2.getCell(0).setText("동남 방향");
            infoTableRow2.getCell(1).setText("");
            infoTableRow2.getCell(2).setText("");


            XWPFTableRow infoTableRow3 = infoTable.createRow();
            infoTableRow3.setHeight(2600);
            infoTableRow3.getCell(0).setText("남서 방향");
            infoTableRow3.getCell(1).setText("");
            infoTableRow3.getCell(2).setText("");


            XWPFTableRow infoTableRow4 = infoTable.createRow();
            infoTableRow4.setHeight(2600);
            infoTableRow4.getCell(0).setText("서북 방향");
            infoTableRow4.getCell(1).setText("");
            infoTableRow4.getCell(2).setText("");

            //1-1
            if (!path11.isEmpty()) {
                InputStream stream11 = new FileInputStream(path11);
                XWPFParagraph p11 = infoTable.getRow(1).getCell(1).addParagraph();
                XWPFRun r11 = p11.createRun();
                r11.addPicture(stream11, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //1-2
            if (!path12.isEmpty()) {
                InputStream stream12 = new FileInputStream(path12);
                XWPFParagraph p12 = infoTable.getRow(1).getCell(2).addParagraph();
                XWPFRun r12 = p12.createRun();
                r12.addPicture(stream12, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //2-1
            if (!path21.isEmpty()) {
                InputStream stream21 = new FileInputStream(path21);
                XWPFParagraph p21 = infoTable.getRow(2).getCell(1).addParagraph();
                XWPFRun r21 = p21.createRun();
                r21.addPicture(stream21, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //2-2
            if (!path22.isEmpty()) {
                InputStream stream22 = new FileInputStream(path22);
                XWPFParagraph p22 = infoTable.getRow(2).getCell(2).addParagraph();
                XWPFRun r22 = p22.createRun();
                r22.addPicture(stream22, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //3-1
            if (!path31.isEmpty()) {
                InputStream stream31 = new FileInputStream(path31);
                XWPFParagraph p31 = infoTable.getRow(3).getCell(1).addParagraph();
                XWPFRun r31 = p31.createRun();
                r31.addPicture(stream31, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //3-2
            if (!path32.isEmpty()) {
                InputStream stream32 = new FileInputStream(path32);
                XWPFParagraph p32 = infoTable.getRow(3).getCell(2).addParagraph();
                XWPFRun r32 = p32.createRun();
                r32.addPicture(stream32, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //4-1
            if (!path41.isEmpty()) {
                InputStream stream41 = new FileInputStream(path41);
                XWPFParagraph p41 = infoTable.getRow(4).getCell(1).addParagraph();
                XWPFRun r41 = p41.createRun();
                r41.addPicture(stream41, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //4-2
            if (!path42.isEmpty()) {
                InputStream stream42 = new FileInputStream(path42);
                XWPFParagraph p42 = infoTable.getRow(4).getCell(2).addParagraph();
                XWPFRun r42 = p42.createRun();
                r42.addPicture(stream42, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            //XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + docName + ".docx";

            //
            FileOutputStream out = new FileOutputStream(new File(docSavePath));
            document.write(out);
            out.close();

            JSONObject result = new JSONObject();
            result.put("url","/" + docName + ".docx");

            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R download5(
            MultipartFile file11,
            MultipartFile file12,
            MultipartFile file21,
            MultipartFile file22,
            MultipartFile file31,
            MultipartFile file32,
            MultipartFile file41,
            MultipartFile file42
    ){
        try {

            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + File.separator + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdir();
            }

            String path11, path12, path21, path22, path31, path32, path41, path42 = "";

            path11="";
            path12="";
            path21="";
            path22="";
            path31="";
            path32="";
            path41="";
            path42="";

            if(file11!=null && file11.getSize()!=0){
                path11 =  folder + "/" + CommonUtils.getUUID() + file11.getOriginalFilename().substring(file11.getOriginalFilename().lastIndexOf("."));
            }
            if(file12!=null && file12.getSize()!=0){
                path12 =  folder + "/" + CommonUtils.getUUID() + file12.getOriginalFilename().substring(file12.getOriginalFilename().lastIndexOf("."));
            }
            if(file21!=null && file21.getSize()!=0){
                path21 =  folder + "/" + CommonUtils.getUUID() + file21.getOriginalFilename().substring(file21.getOriginalFilename().lastIndexOf("."));
            }
            if(file22!=null && file22.getSize()!=0){
                path22 =  folder + "/" + CommonUtils.getUUID() + file22.getOriginalFilename().substring(file22.getOriginalFilename().lastIndexOf("."));
            }
            if(file31!=null && file31.getSize()!=0){
                path31 =  folder + "/" + CommonUtils.getUUID() + file31.getOriginalFilename().substring(file31.getOriginalFilename().lastIndexOf("."));
            }
            if(file32!=null && file32.getSize()!=0){
                path32 =  folder + "/" + CommonUtils.getUUID() + file32.getOriginalFilename().substring(file32.getOriginalFilename().lastIndexOf("."));
            }
            if(file41!=null && file41.getSize()!=0){
                path41 =  folder + "/" + CommonUtils.getUUID() + file41.getOriginalFilename().substring(file41.getOriginalFilename().lastIndexOf("."));
            }
            if(file42!=null && file42.getSize()!=0){
                path42 =  folder + "/" + CommonUtils.getUUID() + file42.getOriginalFilename().substring(file42.getOriginalFilename().lastIndexOf("."));
            }

            if(!path11.isEmpty()){
                File saveFile11 = new File(path11);
                file11.transferTo(saveFile11);
            }
            if(!path12.isEmpty()){
                File saveFile12 = new File(path12);
                file12.transferTo(saveFile12);
            }
            if(!path21.isEmpty()){
                File saveFile21 = new File(path21);
                file21.transferTo(saveFile21);
            }
            if(!path22.isEmpty()){
                File saveFile22 = new File(path22);
                file22.transferTo(saveFile22);
            }
            if(!path31.isEmpty()){
                File saveFile31 = new File(path31);
                file31.transferTo(saveFile31);
            }
            if(!path32.isEmpty()){
                File saveFile32 = new File(path32);
                file32.transferTo(saveFile32);
            }
            if(!path41.isEmpty()){
                File saveFile41 = new File(path41);
                file41.transferTo(saveFile41);
            }
            if(!path42.isEmpty()){
                File saveFile42 = new File(path42);
                file42.transferTo(saveFile42);
            }


            XWPFDocument document = new XWPFDocument();

            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("전망 분석 보고");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);



            XWPFTable infoTable = document.createTable();


            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));

            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("구분");
            infoTableRowOne.addNewTableCell().setText("분석결과1");
            infoTableRowOne.addNewTableCell().setText("분석결과2");


            CTTblWidth cellw = infoTableRowOne.getCell(0).getCTTc().addNewTcPr().addNewTcW();
            cellw.setType(STTblWidth.DXA);
            cellw.setW(BigInteger.valueOf(60));

            CTTblWidth cellw1 = infoTableRowOne.getCell(1).getCTTc().addNewTcPr().addNewTcW();
            cellw1.setType(STTblWidth.DXA);
            cellw1.setW(BigInteger.valueOf(600));

            CTTblWidth cellw2 = infoTableRowOne.getCell(2).getCTTc().addNewTcPr().addNewTcW();
            cellw2.setType(STTblWidth.DXA);
            cellw2.setW(BigInteger.valueOf(600));


            infoTableRowOne.getCell(0).setColor("D9D9D9");
            infoTableRowOne.getCell(1).setColor("D9D9D9");
            infoTableRowOne.getCell(2).setColor("D9D9D9");


            infoTableRowOne.setHeight(400);


            XWPFTableRow infoTableRow1 = infoTable.createRow();
            infoTableRow1.setHeight(2600);
            infoTableRow1.getCell(0).setText("북동 방향");
            infoTableRow1.getCell(1).setText("");
            infoTableRow1.getCell(2).setText("");


            XWPFTableRow infoTableRow2 = infoTable.createRow();
            infoTableRow2.setHeight(2600);
            infoTableRow2.getCell(0).setText("동남 방향");
            infoTableRow2.getCell(1).setText("");
            infoTableRow2.getCell(2).setText("");


            XWPFTableRow infoTableRow3 = infoTable.createRow();
            infoTableRow3.setHeight(2600);
            infoTableRow3.getCell(0).setText("남서 방향");
            infoTableRow3.getCell(1).setText("");
            infoTableRow3.getCell(2).setText("");


            XWPFTableRow infoTableRow4 = infoTable.createRow();
            infoTableRow4.setHeight(2600);
            infoTableRow4.getCell(0).setText("서북 방향");
            infoTableRow4.getCell(1).setText("");
            infoTableRow4.getCell(2).setText("");


            if (!path11.isEmpty()) {
                InputStream stream11 = new FileInputStream(path11);
                XWPFParagraph p11 = infoTable.getRow(1).getCell(1).addParagraph();
                XWPFRun r11 = p11.createRun();
                r11.addPicture(stream11, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //1-2
            if (!path12.isEmpty()) {
                InputStream stream12 = new FileInputStream(path12);
                XWPFParagraph p12 = infoTable.getRow(1).getCell(2).addParagraph();
                XWPFRun r12 = p12.createRun();
                r12.addPicture(stream12, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //2-1
            if (!path21.isEmpty()) {
                InputStream stream21 = new FileInputStream(path21);
                XWPFParagraph p21 = infoTable.getRow(2).getCell(1).addParagraph();
                XWPFRun r21 = p21.createRun();
                r21.addPicture(stream21, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //2-2
            if (!path22.isEmpty()) {
                InputStream stream22 = new FileInputStream(path22);
                XWPFParagraph p22 = infoTable.getRow(2).getCell(2).addParagraph();
                XWPFRun r22 = p22.createRun();
                r22.addPicture(stream22, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //3-1
            if (!path31.isEmpty()) {
                InputStream stream31 = new FileInputStream(path31);
                XWPFParagraph p31 = infoTable.getRow(3).getCell(1).addParagraph();
                XWPFRun r31 = p31.createRun();
                r31.addPicture(stream31, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //3-2
            if (!path32.isEmpty()) {
                InputStream stream32 = new FileInputStream(path32);
                XWPFParagraph p32 = infoTable.getRow(3).getCell(2).addParagraph();
                XWPFRun r32 = p32.createRun();
                r32.addPicture(stream32, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //4-1
            if (!path41.isEmpty()) {
                InputStream stream41 = new FileInputStream(path41);
                XWPFParagraph p41 = infoTable.getRow(4).getCell(1).addParagraph();
                XWPFRun r41 = p41.createRun();
                r41.addPicture(stream41, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //4-2
            if (!path42.isEmpty()) {
                InputStream stream42 = new FileInputStream(path42);
                XWPFParagraph p42 = infoTable.getRow(4).getCell(2).addParagraph();
                XWPFRun r42 = p42.createRun();
                r42.addPicture(stream42, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            //XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + docName + ".docx";

            FileOutputStream out = new FileOutputStream(new File(docSavePath));
            document.write(out);
            out.close();

            JSONObject result = new JSONObject();
            result.put("url","/" + docName + ".docx");

            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    public R download6(
            MultipartFile file11,
            MultipartFile file12,
            MultipartFile file21,
            MultipartFile file22,
            MultipartFile file31,
            MultipartFile file32,
            MultipartFile file41,
            MultipartFile file42,
            MultipartFile file51,
            MultipartFile file52,
            MultipartFile file61,
            MultipartFile file62,
            MultipartFile file71,
            MultipartFile file72,
            MultipartFile file81,
            MultipartFile file82
    ){
        try {

            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + File.separator + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdir();
            }

            String path11="";
            String path12="";
            String path21="";
            String path22="";
            String path31="";
            String path32="";
            String path41="";
            String path42="";
            String path51="";
            String path52="";
            String path61="";
            String path62="";
            String path71="";
            String path72="";
            String path81="";
            String path82="";

            if(file11!=null && file11.getSize()!=0){
                path11 =  folder + "/" + CommonUtils.getUUID() + file11.getOriginalFilename().substring(file11.getOriginalFilename().lastIndexOf("."));
            }
            if(file12!=null && file12.getSize()!=0){
                path12 =  folder + "/" + CommonUtils.getUUID() + file12.getOriginalFilename().substring(file12.getOriginalFilename().lastIndexOf("."));
            }
            if(file21!=null && file21.getSize()!=0){
                path21 =  folder + "/" + CommonUtils.getUUID() + file21.getOriginalFilename().substring(file21.getOriginalFilename().lastIndexOf("."));
            }
            if(file22!=null && file22.getSize()!=0){
                path22 =  folder + "/" + CommonUtils.getUUID() + file22.getOriginalFilename().substring(file22.getOriginalFilename().lastIndexOf("."));
            }
            if(file31!=null && file31.getSize()!=0){
                path31 =  folder + "/" + CommonUtils.getUUID() + file31.getOriginalFilename().substring(file31.getOriginalFilename().lastIndexOf("."));
            }
            if(file32!=null && file32.getSize()!=0){
                path32 =  folder + "/" + CommonUtils.getUUID() + file32.getOriginalFilename().substring(file32.getOriginalFilename().lastIndexOf("."));
            }
            if(file41!=null && file41.getSize()!=0){
                path41 =  folder + "/" + CommonUtils.getUUID() + file41.getOriginalFilename().substring(file41.getOriginalFilename().lastIndexOf("."));
            }
            if(file42!=null && file42.getSize()!=0){
                path42 =  folder + "/" + CommonUtils.getUUID() + file42.getOriginalFilename().substring(file42.getOriginalFilename().lastIndexOf("."));
            }
            if(file51!=null && file51.getSize()!=0){
                path51 =  folder + "/" + CommonUtils.getUUID() + file51.getOriginalFilename().substring(file51.getOriginalFilename().lastIndexOf("."));
            }
            if(file52!=null && file52.getSize()!=0){
                path52 =  folder + "/" + CommonUtils.getUUID() + file52.getOriginalFilename().substring(file52.getOriginalFilename().lastIndexOf("."));
            }
            if(file61!=null && file61.getSize()!=0){
                path61 =  folder + "/" + CommonUtils.getUUID() + file61.getOriginalFilename().substring(file61.getOriginalFilename().lastIndexOf("."));
            }
            if(file62!=null && file62.getSize()!=0){
                path62 =  folder + "/" + CommonUtils.getUUID() + file62.getOriginalFilename().substring(file62.getOriginalFilename().lastIndexOf("."));
            }
            if(file71!=null && file71.getSize()!=0){
                path71 =  folder + "/" + CommonUtils.getUUID() + file71.getOriginalFilename().substring(file71.getOriginalFilename().lastIndexOf("."));
            }
            if(file72!=null && file72.getSize()!=0){
                path72 =  folder + "/" + CommonUtils.getUUID() + file72.getOriginalFilename().substring(file72.getOriginalFilename().lastIndexOf("."));
            }
            if(file81!=null && file81.getSize()!=0){
                path81 =  folder + "/" + CommonUtils.getUUID() + file81.getOriginalFilename().substring(file81.getOriginalFilename().lastIndexOf("."));
            }
            if(file82!=null && file82.getSize()!=0){
                path82 =  folder + "/" + CommonUtils.getUUID() + file82.getOriginalFilename().substring(file82.getOriginalFilename().lastIndexOf("."));
            }

            if(!path11.isEmpty()){
                File saveFile11 = new File(path11);
                file11.transferTo(saveFile11);
            }
            if(!path12.isEmpty()){
                File saveFile12 = new File(path12);
                file12.transferTo(saveFile12);
            }
            if(!path21.isEmpty()){
                File saveFile21 = new File(path21);
                file21.transferTo(saveFile21);
            }
            if(!path22.isEmpty()){
                File saveFile22 = new File(path22);
                file22.transferTo(saveFile22);
            }
            if(!path31.isEmpty()){
                File saveFile31 = new File(path31);
                file31.transferTo(saveFile31);
            }
            if(!path32.isEmpty()){
                File saveFile32 = new File(path32);
                file32.transferTo(saveFile32);
            }
            if(!path41.isEmpty()){
                File saveFile41 = new File(path41);
                file41.transferTo(saveFile41);
            }
            if(!path42.isEmpty()){
                File saveFile42 = new File(path42);
                file42.transferTo(saveFile42);
            }
            if(!path51.isEmpty()){
                File saveFile51 = new File(path51);
                file51.transferTo(saveFile51);
            }
            if(!path52.isEmpty()){
                File saveFile52 = new File(path52);
                file52.transferTo(saveFile52);
            }
            if(!path61.isEmpty()){
                File saveFile61 = new File(path61);
                file61.transferTo(saveFile61);
            }
            if(!path62.isEmpty()){
                File saveFile62 = new File(path62);
                file62.transferTo(saveFile62);
            }
            if(!path71.isEmpty()){
                File saveFile71 = new File(path71);
                file71.transferTo(saveFile71);
            }
            if(!path72.isEmpty()){
                File saveFile72 = new File(path72);
                file72.transferTo(saveFile72);
            }
            if(!path81.isEmpty()){
                File saveFile81 = new File(path81);
                file81.transferTo(saveFile81);
            }
            if(!path82.isEmpty()){
                File saveFile82 = new File(path82);
                file82.transferTo(saveFile82);
            }



            XWPFDocument document = new XWPFDocument();


            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("전체 분석 보고");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);


            XWPFParagraph titleParagraph1 = document.createParagraph();
            titleParagraph1.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun titleParagraphRun1 = titleParagraph1.createRun();
            titleParagraphRun1.addBreak();
            titleParagraphRun1.addBreak();
            titleParagraphRun1.addBreak();
            titleParagraphRun1.setText("1.가시역 분석 보고");
            titleParagraphRun1.setColor("000000");
            titleParagraphRun1.setFontSize(12);


            XWPFTable infoTable = document.createTable();


            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));


            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("구분");
            infoTableRowOne.addNewTableCell().setText("분석결과1");
            infoTableRowOne.addNewTableCell().setText("분석결과2");


            CTTblWidth cellw = infoTableRowOne.getCell(0).getCTTc().addNewTcPr().addNewTcW();
            cellw.setType(STTblWidth.DXA);
            cellw.setW(BigInteger.valueOf(60));

            CTTblWidth cellw1 = infoTableRowOne.getCell(1).getCTTc().addNewTcPr().addNewTcW();
            cellw1.setType(STTblWidth.DXA);
            cellw1.setW(BigInteger.valueOf(600));

            CTTblWidth cellw2 = infoTableRowOne.getCell(2).getCTTc().addNewTcPr().addNewTcW();
            cellw2.setType(STTblWidth.DXA);
            cellw2.setW(BigInteger.valueOf(600));


            infoTableRowOne.getCell(0).setColor("D9D9D9");
            infoTableRowOne.getCell(1).setColor("D9D9D9");
            infoTableRowOne.getCell(2).setColor("D9D9D9");

            infoTableRowOne.setHeight(400);


            XWPFTableRow infoTableRow1 = infoTable.createRow();
            infoTableRow1.setHeight(2600);
            infoTableRow1.getCell(0).setText("북동 방향");
            infoTableRow1.getCell(1).setText("");
            infoTableRow1.getCell(2).setText("");


            XWPFTableRow infoTableRow2 = infoTable.createRow();
            infoTableRow2.setHeight(2600);
            infoTableRow2.getCell(0).setText("동남 방향");
            infoTableRow2.getCell(1).setText("");
            infoTableRow2.getCell(2).setText("");

            XWPFTableRow infoTableRow3 = infoTable.createRow();
            infoTableRow3.setHeight(2600);
            infoTableRow3.getCell(0).setText("남서 방향");
            infoTableRow3.getCell(1).setText("");
            infoTableRow3.getCell(2).setText("");

            XWPFTableRow infoTableRow4 = infoTable.createRow();
            infoTableRow4.setHeight(2600);
            infoTableRow4.getCell(0).setText("서북 방향");
            infoTableRow4.getCell(1).setText("");
            infoTableRow4.getCell(2).setText("");

            //1-1
            if (!path11.isEmpty()) {
                InputStream stream11 = new FileInputStream(path11);
                XWPFParagraph p11 = infoTable.getRow(1).getCell(1).addParagraph();
                XWPFRun r11 = p11.createRun();
                r11.addPicture(stream11, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //1-2
            if (!path12.isEmpty()) {
                InputStream stream12 = new FileInputStream(path12);
                XWPFParagraph p12 = infoTable.getRow(1).getCell(2).addParagraph();
                XWPFRun r12 = p12.createRun();
                r12.addPicture(stream12, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //2-1
            if (!path21.isEmpty()) {
                InputStream stream21 = new FileInputStream(path21);
                XWPFParagraph p21 = infoTable.getRow(2).getCell(1).addParagraph();
                XWPFRun r21 = p21.createRun();
                r21.addPicture(stream21, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //2-2
            if (!path22.isEmpty()) {
                InputStream stream22 = new FileInputStream(path22);
                XWPFParagraph p22 = infoTable.getRow(2).getCell(2).addParagraph();
                XWPFRun r22 = p22.createRun();
                r22.addPicture(stream22, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //3-1
            if (!path31.isEmpty()) {
                InputStream stream31 = new FileInputStream(path31);
                XWPFParagraph p31 = infoTable.getRow(3).getCell(1).addParagraph();
                XWPFRun r31 = p31.createRun();
                r31.addPicture(stream31, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //3-2
            if (!path32.isEmpty()) {
                InputStream stream32 = new FileInputStream(path32);
                XWPFParagraph p32 = infoTable.getRow(3).getCell(2).addParagraph();
                XWPFRun r32 = p32.createRun();
                r32.addPicture(stream32, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //4-1
            if (!path41.isEmpty()) {
                InputStream stream41 = new FileInputStream(path41);
                XWPFParagraph p41 = infoTable.getRow(4).getCell(1).addParagraph();
                XWPFRun r41 = p41.createRun();
                r41.addPicture(stream41, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //4-2
            if (!path42.isEmpty()) {
                InputStream stream42 = new FileInputStream(path42);
                XWPFParagraph p42 = infoTable.getRow(4).getCell(2).addParagraph();
                XWPFRun r42 = p42.createRun();
                r42.addPicture(stream42, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }


            XWPFParagraph titleParagraph2 = document.createParagraph();
            titleParagraph2.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun titleParagraphRun2 = titleParagraph2.createRun();
            titleParagraphRun2.addBreak();
            titleParagraphRun2.addBreak();
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setText("2.전망 분석 보고");
            titleParagraphRun2.setColor("000000");
            titleParagraphRun2.setFontSize(12);

            XWPFTable infoTable1 = document.createTable();

            CTTblWidth infoTableWidth1 = infoTable1.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth1.setType(STTblWidth.DXA);
            infoTableWidth1.setW(BigInteger.valueOf(9072));

            XWPFTableRow infoTableRowOne1 = infoTable1.getRow(0);
            infoTableRowOne1.getCell(0).setText("구분");
            infoTableRowOne1.addNewTableCell().setText("분석결과1");
            infoTableRowOne1.addNewTableCell().setText("분석결과2");

            CTTblWidth cellwT2 = infoTableRowOne1.getCell(0).getCTTc().addNewTcPr().addNewTcW();
            cellwT2.setType(STTblWidth.DXA);
            cellwT2.setW(BigInteger.valueOf(60));

            CTTblWidth cellwT21 = infoTableRowOne1.getCell(1).getCTTc().addNewTcPr().addNewTcW();
            cellwT21.setType(STTblWidth.DXA);
            cellwT21.setW(BigInteger.valueOf(600));

            CTTblWidth cellwT22 = infoTableRowOne1.getCell(2).getCTTc().addNewTcPr().addNewTcW();
            cellwT22.setType(STTblWidth.DXA);
            cellwT22.setW(BigInteger.valueOf(600));

            infoTableRowOne1.getCell(0).setColor("D9D9D9");
            infoTableRowOne1.getCell(1).setColor("D9D9D9");
            infoTableRowOne1.getCell(2).setColor("D9D9D9");


            infoTableRowOne1.setHeight(400);

            XWPFTableRow infoTableRowT21 = infoTable1.createRow();
            infoTableRowT21.setHeight(2600);
            infoTableRowT21.getCell(0).setText("북동 방향");
            infoTableRowT21.getCell(1).setText("");
            infoTableRowT21.getCell(2).setText("");


            XWPFTableRow infoTableRowT22 = infoTable1.createRow();
            infoTableRowT22.setHeight(2600);
            infoTableRowT22.getCell(0).setText("동남 방향");
            infoTableRowT22.getCell(1).setText("");
            infoTableRowT22.getCell(2).setText("");

            XWPFTableRow infoTableRowT23 = infoTable1.createRow();
            infoTableRowT23.setHeight(2600);
            infoTableRowT23.getCell(0).setText("남서 방향");
            infoTableRowT23.getCell(1).setText("");
            infoTableRowT23.getCell(2).setText("");

            XWPFTableRow infoTableRowT24 = infoTable1.createRow();
            infoTableRowT24.setHeight(2600);
            infoTableRowT24.getCell(0).setText("서북 방향");
            infoTableRowT24.getCell(1).setText("");
            infoTableRowT24.getCell(2).setText("");

            //5-1
            if (!path51.isEmpty()) {
                InputStream stream11 = new FileInputStream(path51);
                XWPFParagraph p11 = infoTable1.getRow(1).getCell(1).addParagraph();
                XWPFRun r11 = p11.createRun();
                r11.addPicture(stream11, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //5-2
            if (!path52.isEmpty()) {
                InputStream stream12 = new FileInputStream(path52);
                XWPFParagraph p12 = infoTable1.getRow(1).getCell(2).addParagraph();
                XWPFRun r12 = p12.createRun();
                r12.addPicture(stream12, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //6-1
            if (!path61.isEmpty()) {
                InputStream stream21 = new FileInputStream(path61);
                XWPFParagraph p21 = infoTable1.getRow(2).getCell(1).addParagraph();
                XWPFRun r21 = p21.createRun();
                r21.addPicture(stream21, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //6-2
            if (!path62.isEmpty()) {
                InputStream stream22 = new FileInputStream(path62);
                XWPFParagraph p22 = infoTable1.getRow(2).getCell(2).addParagraph();
                XWPFRun r22 = p22.createRun();
                r22.addPicture(stream22, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //7-1
            if (!path71.isEmpty()) {
                InputStream stream31 = new FileInputStream(path71);
                XWPFParagraph p31 = infoTable1.getRow(3).getCell(1).addParagraph();
                XWPFRun r31 = p31.createRun();
                r31.addPicture(stream31, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //7-2
            if (!path72.isEmpty()) {
                InputStream stream32 = new FileInputStream(path72);
                XWPFParagraph p32 = infoTable1.getRow(3).getCell(2).addParagraph();
                XWPFRun r32 = p32.createRun();
                r32.addPicture(stream32, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //8-1
            if (!path81.isEmpty()) {
                InputStream stream41 = new FileInputStream(path81);
                XWPFParagraph p41 = infoTable1.getRow(4).getCell(1).addParagraph();
                XWPFRun r41 = p41.createRun();
                r41.addPicture(stream41, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //8-2
            if (!path82.isEmpty()) {
                InputStream stream42 = new FileInputStream(path82);
                XWPFParagraph p42 = infoTable1.getRow(4).getCell(2).addParagraph();
                XWPFRun r42 = p42.createRun();
                r42.addPicture(stream42, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }



            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            //XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + docName + ".docx";

            FileOutputStream out = new FileOutputStream(new File(docSavePath));
            document.write(out);
            out.close();

            JSONObject result = new JSONObject();
            result.put("url","/" + docName + ".docx");

            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }

    //region

    public R download7(MultipartFile file, String position, String height, String distance, String createUser) {

        try {
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + File.separator + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdir();
            }

            String path = "";
            if (file != null && file.getSize() != 0) {
                path = folder + "/" + CommonUtils.getUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            if (!path.isEmpty()) {
                File saveFile11 = new File(path);
                file.transferTo(saveFile11);
            }


            XWPFDocument document = new XWPFDocument();

            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("가시권 분석결과(조망점)");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);


            XWPFTable infoTable = document.createTable();


            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));


            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.setHeight(600);
            infoTableRowOne.getCell(0).setText("거리 분석결과 (조망점)");
            infoTableRowOne.addNewTableCell().setText("");


            mergeCellsHorizontal(infoTable,0,0,1);


            CTTblWidth cellw = infoTableRowOne.getCell(0).getCTTc().addNewTcPr().addNewTcW();
            cellw.setType(STTblWidth.DXA);
            cellw.setW(BigInteger.valueOf(60));

            CTTblWidth cellw1 = infoTableRowOne.getCell(1).getCTTc().addNewTcPr().addNewTcW();
            cellw1.setType(STTblWidth.DXA);
            cellw1.setW(BigInteger.valueOf(600));


//            infoTableRowOne.getCell(0).setColor("D9D9D9");
//            infoTableRowOne.getCell(1).setColor("D9D9D9");


            XWPFTableRow infoTableRow1 = infoTable.createRow();
            infoTableRow1.setHeight(600);
            infoTableRow1.getCell(0).setText("시작 위치");
            infoTableRow1.getCell(1).setText(position);


            XWPFTableRow infoTableRow2 = infoTable.createRow();
            infoTableRow2.setHeight(600);
            infoTableRow2.getCell(0).setText("시야 높이（m）");
            infoTableRow2.getCell(1).setText(height);


            XWPFTableRow infoTableRow3 = infoTable.createRow();
            infoTableRow3.setHeight(600);
            infoTableRow3.getCell(0).setText("대상점까지 거리（m）");
            infoTableRow3.getCell(1).setText(distance);


            XWPFTableRow infoTableRow4 = infoTable.createRow();
            infoTableRow4.setHeight(2600);
            infoTableRow4.getCell(0).setText("조망분석 결과");
            infoTableRow4.getCell(1).setText("");

            //5-2
            if (!path.isEmpty()) {
                InputStream stream = new FileInputStream(path);
                XWPFParagraph p = infoTable.getRow(4).getCell(1).addParagraph();
                XWPFRun r = p.createRun();
                r.addPicture(stream, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            String folderName1 = CommonUtils.currentDateToStr(7);  //yyyymmdd
            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + createUser + "/" +folderName1 +"/"+ docName + ".docx";


            File savDoc = new File(appConfig.getUavModelPath() + "/" + createUser + "/" +folderName1);
            if (!savDoc.exists() && !savDoc.isDirectory()) {
                savDoc.mkdirs();
            }

            FileOutputStream out = new FileOutputStream(new File(docSavePath));
            document.write(out);
            out.close();

            JSONObject result = new JSONObject();
            result.put("url", "/" + createUser + "/" +folderName1+ "/" + docName + ".docx");
            return R.success(result);
        } catch (Exception ex) {
            return R.error();
        }
    }


    public  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if ( cellIndex == fromCell ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
    //endregion

    //region EPSG Convert


    public R epsgConvertUpload(MultipartFile file){
        try {

            String fileDesc = file.getOriginalFilename();
            String suffixName = fileDesc.substring(fileDesc.lastIndexOf("."));  //.xxx
            String newName = CommonUtils.getUUID();
            String folderName = CommonUtils.currentDateToStr(7);  //yyyymmdd
            String folder = appConfig.getUavModelPath() + "/" + folderName;
            String filePath = folder + "/" + newName + suffixName;
            String url = "/" + folderName + "/" + newName + suffixName;

            File fileDir = new File(filePath);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdirs();
            }
            File zipFile = new File(filePath);
            file.transferTo(zipFile);

            JSONArray titleList = new JSONArray();

            File xlsFile = new File(filePath);
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsFile));
            XSSFSheet sheet = wb.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();

            if(rows<2){
                return R.error("no data!");
            }


            XSSFRow row0 = sheet.getRow(0);
            short row0nums = row0.getLastCellNum();


            for (int j = 0; j < row0nums; j++) {
                Cell cell = row0.getCell(j);
                JSONObject item = new JSONObject();
                item.put("index",j);
                item.put("name",cell.toString());
                titleList.add(item);
            }

            JSONObject result = new JSONObject();
            result.put("filePath", url);
            result.put("titleList", titleList);
            return R.success(result);

        } catch (Exception ex) {
            return R.error();
        }
    }

    public R epsgConvertDownload(JSONObject entity) {
        try {
            String beforeEpsg = entity.getString("beforeEpsg");
            String afterEpsg = entity.getString("afterEpsg");
            String filePath = entity.getString("filePath");
            Integer wktIndex = entity.getInteger("index");

            File xlsFile = new File(appConfig.getUavModelPath() + filePath);
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsFile));
            XSSFSheet sheet = wb.getSheetAt(0);


            XSSFRow row0 = sheet.getRow(0);
            short row0nums = row0.getLastCellNum();

            row0.createCell(row0nums).setCellValue("beforeEpsg");
            row0.createCell(row0nums + 1).setCellValue("afterEpsg");
            row0.createCell(row0nums + 2).setCellValue("afterWKT");

            int rows = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);
                String oldWkt = row.getCell(wktIndex).toString();
                String newWkt = epsgConvert(beforeEpsg, afterEpsg, oldWkt);
                row.createCell(row0nums).setCellValue(beforeEpsg);
                row.createCell(row0nums + 1).setCellValue(afterEpsg);
                row.createCell(row0nums + 2).setCellValue(newWkt);
            }

            //save
            FileOutputStream outputStream = new FileOutputStream(appConfig.getUavModelPath() + filePath);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            wb.close();

            JSONObject result = new JSONObject();
            result.put("url", filePath);
            return R.success(result);
        } catch (Exception ex) {
            return R.error();
        }
    }

    private String epsgConvert(String beforeEpsg, String afterEpsg, String data) {
        String result = "";
        try {
            WKTReader reader = new WKTReader();
            Geometry geometry = reader.read(data);
            CoordinateReferenceSystem crsresource = CRS.decode("EPSG:" + beforeEpsg);
            CoordinateReferenceSystem crsTarget = CRS.decode("EPSG:" + afterEpsg);
            MathTransform transform = CRS.findMathTransform(crsresource, crsTarget, true);
            Geometry transform1 = JTS.transform(geometry, transform);
            result = transform1.toString();
            return result;
        } catch (Exception ex) {
            return result;
        }
    }

    //endregion



    public R download8(JSONObject object){

       try{


           XWPFDocument document = new XWPFDocument();

           XWPFParagraph titleParagraph = document.createParagraph();
           titleParagraph.setAlignment(ParagraphAlignment.CENTER);
           XWPFRun titleParagraphRun = titleParagraph.createRun();
           titleParagraphRun.setText("LX 디지털트윈 문화재 현상변경 분석");
           titleParagraphRun.setColor("000000");
           titleParagraphRun.setFontSize(20);

           XWPFParagraph titleParagraph2 = document.createParagraph();
           titleParagraph2.setAlignment(ParagraphAlignment.LEFT);
           XWPFRun titleParagraphRun2 = titleParagraph2.createRun();
           titleParagraphRun2.addBreak();
           titleParagraphRun2.addBreak();
           titleParagraphRun2.addBreak();
           titleParagraphRun2.setText("문화재명：" + object.getString("nameOfCulturalHeritage")); //
           titleParagraphRun2.addBreak();
           titleParagraphRun2.setText("총면적：" + object.getString("totalArea")); //
           titleParagraphRun2.addBreak();
           titleParagraphRun2.setText("지정번호："+ object.getString("specifyNumber")); //
           titleParagraphRun2.addBreak();
           titleParagraphRun2.setText("건축물 개수："+ object.getString("numberOfBuildings")); //
           titleParagraphRun2.addBreak();
           titleParagraphRun2.setText("불법 건축물 개수："+ object.getString("numberOfIllegalBuildings")); //
           titleParagraphRun2.addBreak();
           titleParagraphRun2.setText("구역 이미지："); //
           //
           List<String> fileList = object.getJSONArray("fileList").toJavaList(String.class);
           for (int i = 0; i < fileList.size(); i++) {
               InputStream stream = new FileInputStream(appConfig.getUavModelPath() + fileList.get(i));
               titleParagraphRun2.addPicture(stream, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(400), Units.toEMU(200));
               titleParagraphRun2.addBreak();
           }
           titleParagraphRun2.addBreak();
           titleParagraphRun2.setColor("000000");
           titleParagraphRun2.setFontSize(11);
           titleParagraphRun2.addBreak();
           titleParagraphRun2.addBreak();

           XWPFParagraph title3 = document.createParagraph();
           title3.setAlignment(ParagraphAlignment.LEFT);
           XWPFRun titleRun3 = title3.createRun();
           titleRun3.setText("불법건축물 자세히보기 (동)");
           titleRun3.setColor("000000");
           titleRun3.setFontSize(11);

           XWPFTable infoTable = document.createTable();

           //
           CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
           infoTableWidth.setType(STTblWidth.DXA);
           infoTableWidth.setW(BigInteger.valueOf(9072));


           XWPFTableRow infoTableRowOne = infoTable.getRow(0);
           infoTableRowOne.setHeight(600);
           infoTableRowOne.getCell(0).setText("건물명칭"); //
           infoTableRowOne.addNewTableCell().setText("기본주소");//
           infoTableRowOne.addNewTableCell().setText("건물높이");//
           infoTableRowOne.addNewTableCell().setText("불법건축 초과높이");//
           infoTableRowOne.addNewTableCell().setText("적용 문화재 구역");//
           infoTableRowOne.addNewTableCell().setText("구역");//


           infoTableRowOne.getCell(0).setColor("D9D9D9");
           infoTableRowOne.getCell(1).setColor("D9D9D9");
           infoTableRowOne.getCell(2).setColor("D9D9D9");
           infoTableRowOne.getCell(3).setColor("D9D9D9");
           infoTableRowOne.getCell(4).setColor("D9D9D9");
           infoTableRowOne.getCell(5).setColor("D9D9D9");


           JSONArray list = object.getJSONArray("list");
           for (int i = 0; i < list.size(); i++) {

               XWPFTableRow infoTableRowItem = infoTable.createRow();
               infoTableRowItem.getCell(0).setText(list.getJSONObject(i).getString("buildingName"));
               infoTableRowItem.getCell(1).setText(list.getJSONObject(i).getString("baseAddress"));
               infoTableRowItem.getCell(2).setText(list.getJSONObject(i).getString("buildingHeight"));
               infoTableRowItem.getCell(3).setText(list.getJSONObject(i).getString("slabOverHeight"));
               infoTableRowItem.getCell(4).setText(list.getJSONObject(i).getString("appliedCulturalHeritageArea"));
               infoTableRowItem.getCell(5).setText(list.getJSONObject(i).getString("area"));
           }


           String createUser = object.getString("createUser");
           String folderName1 = CommonUtils.currentDateToStr(7);  //yyyymmdd
           String docName = CommonUtils.getUUID();
           String docSavePath = appConfig.getUavModelPath() + "/" + createUser + "/" +folderName1 +"/"+ docName + ".docx";

           File savDoc = new File(appConfig.getUavModelPath() + "/" + createUser + "/" +folderName1);
           if (!savDoc.exists() && !savDoc.isDirectory()) {
               savDoc.mkdirs();
           }


           FileOutputStream out = new FileOutputStream(new File(docSavePath));
           document.write(out);
           out.close();

           JSONObject result = new JSONObject();
           result.put("url", "/" + createUser + "/" +folderName1+ "/" + docName + ".docx");
           return R.success(result);
       }catch (Exception ex){
           return R.error();
       }

    }



    public R download9(String files, JSONObject object) {
        try {


            XWPFDocument document = new XWPFDocument();


            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("현상변경 분석 (신규 건축물)");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);


            XWPFParagraph titleParagraph2 = document.createParagraph();
            titleParagraph2.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun titleParagraphRun2 = titleParagraph2.createRun();
            titleParagraphRun2.addBreak();
            titleParagraphRun2.addBreak();
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setText("건축물 높이：" + object.getString("height")); //
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setText("구역이미지："); //
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setColor("000000");
            titleParagraphRun2.setFontSize(11);

            if(files!=null&&!files.equals("")){
                List<String> fileList = Arrays.asList(files.split(","));
                for (int i = 0; i < fileList.size(); i++) {
                    InputStream stream = new FileInputStream(appConfig.getUavModelPath() + fileList.get(i));
                    titleParagraphRun2.addPicture(stream, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(400), Units.toEMU(200));
                    titleParagraphRun2.addBreak();
                }
            }


            titleParagraphRun2.addBreak();
            titleParagraphRun2.addBreak();


            XWPFTable infoTable = document.createTable();


            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));


            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.setHeight(350);
            infoTableRowOne.getCell(0).setText("문화재명"); //
            infoTableRowOne.addNewTableCell().setText("구역번호");//
            infoTableRowOne.addNewTableCell().setText("평슬라브");//
            infoTableRowOne.addNewTableCell().setText("경사지붕");//
            infoTableRowOne.addNewTableCell().setText("초과여부");//


            infoTableRowOne.getCell(0).setColor("D9D9D9");
            infoTableRowOne.getCell(1).setColor("D9D9D9");
            infoTableRowOne.getCell(2).setColor("D9D9D9");
            infoTableRowOne.getCell(3).setColor("D9D9D9");
            infoTableRowOne.getCell(4).setColor("D9D9D9");


            JSONArray list = object.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                XWPFTableRow infoTableRowItem = infoTable.createRow();
                infoTableRowItem.getCell(0).setText(list.getJSONObject(i).getString("name"));
                infoTableRowItem.getCell(1).setText(list.getJSONObject(i).getString("number"));
                infoTableRowItem.getCell(2).setText(list.getJSONObject(i).getString("plane"));
                infoTableRowItem.getCell(3).setText(list.getJSONObject(i).getString("tilt"));
                infoTableRowItem.getCell(4).setText(list.getJSONObject(i).getString("c5"));
            }


            String createUser = object.getString("createUser");
            String folderName1 = CommonUtils.currentDateToStr(7);  //yyyymmdd
            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + createUser + "/" + folderName1 + "/" + docName + ".docx";

            File savDoc = new File(appConfig.getUavModelPath() + "/" + createUser + "/" + folderName1);
            if (!savDoc.exists() && !savDoc.isDirectory()) {
                savDoc.mkdirs();
            }


            FileOutputStream out = new FileOutputStream(new File(docSavePath));
            document.write(out);
            out.close();

            JSONObject result = new JSONObject();
            result.put("url", "/" + createUser + "/" + folderName1 + "/" + docName + ".docx");
            return R.success(result);
        } catch (Exception ex) {
            return R.error();
        }
    }



    public R download10(
            MultipartFile file11,
            MultipartFile file12,
            MultipartFile file21,
            MultipartFile file22,
            MultipartFile file31,
            MultipartFile file32,
            MultipartFile file41,
            MultipartFile file42,
            String startPosition,
            String visualHeight,
            String visibleDistance,
            String horizontalViewingAngle,
            String verticalViewingAngle,
            String createUser
    ){
        try {

            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + File.separator + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdir();
            }

            String path11, path12, path21, path22, path31, path32, path41, path42 = "";

            path11="";
            path12="";
            path21="";
            path22="";
            path31="";
            path32="";
            path41="";
            path42="";

            if(file11!=null && file11.getSize()!=0){
                path11 =  folder + "/" + CommonUtils.getUUID() + file11.getOriginalFilename().substring(file11.getOriginalFilename().lastIndexOf("."));
            }
            if(file12!=null && file12.getSize()!=0){
                path12 =  folder + "/" + CommonUtils.getUUID() + file12.getOriginalFilename().substring(file12.getOriginalFilename().lastIndexOf("."));
            }
            if(file21!=null && file21.getSize()!=0){
                path21 =  folder + "/" + CommonUtils.getUUID() + file21.getOriginalFilename().substring(file21.getOriginalFilename().lastIndexOf("."));
            }
            if(file22!=null && file22.getSize()!=0){
                path22 =  folder + "/" + CommonUtils.getUUID() + file22.getOriginalFilename().substring(file22.getOriginalFilename().lastIndexOf("."));
            }
            if(file31!=null && file31.getSize()!=0){
                path31 =  folder + "/" + CommonUtils.getUUID() + file31.getOriginalFilename().substring(file31.getOriginalFilename().lastIndexOf("."));
            }
            if(file32!=null && file32.getSize()!=0){
                path32 =  folder + "/" + CommonUtils.getUUID() + file32.getOriginalFilename().substring(file32.getOriginalFilename().lastIndexOf("."));
            }
            if(file41!=null && file41.getSize()!=0){
                path41 =  folder + "/" + CommonUtils.getUUID() + file41.getOriginalFilename().substring(file41.getOriginalFilename().lastIndexOf("."));
            }
            if(file42!=null && file42.getSize()!=0){
                path42 =  folder + "/" + CommonUtils.getUUID() + file42.getOriginalFilename().substring(file42.getOriginalFilename().lastIndexOf("."));
            }

            if(!path11.isEmpty()){
                File saveFile11 = new File(path11);
                file11.transferTo(saveFile11);
            }
            if(!path12.isEmpty()){
                File saveFile12 = new File(path12);
                file12.transferTo(saveFile12);
            }
            if(!path21.isEmpty()){
                File saveFile21 = new File(path21);
                file21.transferTo(saveFile21);
            }
            if(!path22.isEmpty()){
                File saveFile22 = new File(path22);
                file22.transferTo(saveFile22);
            }
            if(!path31.isEmpty()){
                File saveFile31 = new File(path31);
                file31.transferTo(saveFile31);
            }
            if(!path32.isEmpty()){
                File saveFile32 = new File(path32);
                file32.transferTo(saveFile32);
            }
            if(!path41.isEmpty()){
                File saveFile41 = new File(path41);
                file41.transferTo(saveFile41);
            }
            if(!path42.isEmpty()){
                File saveFile42 = new File(path42);
                file42.transferTo(saveFile42);
            }


            XWPFDocument document = new XWPFDocument();

            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("가시권 분석 결과(전체 결과)");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);


            XWPFParagraph titleParagraph2 = document.createParagraph();
            titleParagraph2.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun titleParagraphRun2 = titleParagraph2.createRun();
            titleParagraphRun2.addBreak();
            titleParagraphRun2.addBreak();
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setText("360º 가시권 분석 결과(전체 결과)" ); //
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setText("시작 위치："+ startPosition); //
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setText("가시 높이："+ visualHeight); //
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setText("가시거리 (m)："+ visibleDistance); //(m)
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setText("수평 시야각(º)"+ horizontalViewingAngle); //(º)
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setText("수직 시야각(º)："+ verticalViewingAngle); //(º)
            titleParagraphRun2.addBreak();
            titleParagraphRun2.setColor("000000");
            titleParagraphRun2.setFontSize(11);
            titleParagraphRun2.addBreak();
            titleParagraphRun2.addBreak();


            XWPFTable infoTable = document.createTable();


            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));


            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.getCell(0).setText("방향"); //
            infoTableRowOne.addNewTableCell().setText("가시점 분석"); //
            infoTableRowOne.addNewTableCell().setText("전망점 분석"); //


            CTTblWidth cellw = infoTableRowOne.getCell(0).getCTTc().addNewTcPr().addNewTcW();
            cellw.setType(STTblWidth.DXA);
            cellw.setW(BigInteger.valueOf(60));

            CTTblWidth cellw1 = infoTableRowOne.getCell(1).getCTTc().addNewTcPr().addNewTcW();
            cellw1.setType(STTblWidth.DXA);
            cellw1.setW(BigInteger.valueOf(600));

            CTTblWidth cellw2 = infoTableRowOne.getCell(2).getCTTc().addNewTcPr().addNewTcW();
            cellw2.setType(STTblWidth.DXA);
            cellw2.setW(BigInteger.valueOf(600));

            infoTableRowOne.getCell(0).setColor("D9D9D9");
            infoTableRowOne.getCell(1).setColor("D9D9D9");
            infoTableRowOne.getCell(2).setColor("D9D9D9");


            infoTableRowOne.setHeight(400);


            XWPFTableRow infoTableRow1 = infoTable.createRow();
            infoTableRow1.setHeight(2600);
            infoTableRow1.getCell(0).setText("북동 방향(0 90º)");
            infoTableRow1.getCell(1).setText("");
            infoTableRow1.getCell(2).setText("");


            XWPFTableRow infoTableRow2 = infoTable.createRow();
            infoTableRow2.setHeight(2600);
            infoTableRow2.getCell(0).setText("동남 방향(90 180º)");
            infoTableRow2.getCell(1).setText("");
            infoTableRow2.getCell(2).setText("");


            XWPFTableRow infoTableRow3 = infoTable.createRow();
            infoTableRow3.setHeight(2600);
            infoTableRow3.getCell(0).setText("남서 방향(180 270º)");
            infoTableRow3.getCell(1).setText("");
            infoTableRow3.getCell(2).setText("");


            XWPFTableRow infoTableRow4 = infoTable.createRow();
            infoTableRow4.setHeight(2600);
            infoTableRow4.getCell(0).setText("서북 방향(270 360º)");
            infoTableRow4.getCell(1).setText("");
            infoTableRow4.getCell(2).setText("");

            //1-1
            if (!path11.isEmpty()) {
                InputStream stream11 = new FileInputStream(path11);
                XWPFParagraph p11 = infoTable.getRow(1).getCell(1).addParagraph();
                XWPFRun r11 = p11.createRun();
                r11.addPicture(stream11, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //1-2
            if (!path12.isEmpty()) {
                InputStream stream12 = new FileInputStream(path12);
                XWPFParagraph p12 = infoTable.getRow(1).getCell(2).addParagraph();
                XWPFRun r12 = p12.createRun();
                r12.addPicture(stream12, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //2-1
            if (!path21.isEmpty()) {
                InputStream stream21 = new FileInputStream(path21);
                XWPFParagraph p21 = infoTable.getRow(2).getCell(1).addParagraph();
                XWPFRun r21 = p21.createRun();
                r21.addPicture(stream21, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //2-2
            if (!path22.isEmpty()) {
                InputStream stream22 = new FileInputStream(path22);
                XWPFParagraph p22 = infoTable.getRow(2).getCell(2).addParagraph();
                XWPFRun r22 = p22.createRun();
                r22.addPicture(stream22, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //3-1
            if (!path31.isEmpty()) {
                InputStream stream31 = new FileInputStream(path31);
                XWPFParagraph p31 = infoTable.getRow(3).getCell(1).addParagraph();
                XWPFRun r31 = p31.createRun();
                r31.addPicture(stream31, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //3-2
            if (!path32.isEmpty()) {
                InputStream stream32 = new FileInputStream(path32);
                XWPFParagraph p32 = infoTable.getRow(3).getCell(2).addParagraph();
                XWPFRun r32 = p32.createRun();
                r32.addPicture(stream32, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //4-1
            if (!path41.isEmpty()) {
                InputStream stream41 = new FileInputStream(path41);
                XWPFParagraph p41 = infoTable.getRow(4).getCell(1).addParagraph();
                XWPFRun r41 = p41.createRun();
                r41.addPicture(stream41, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            //4-2
            if (!path42.isEmpty()) {
                InputStream stream42 = new FileInputStream(path42);
                XWPFParagraph p42 = infoTable.getRow(4).getCell(2).addParagraph();
                XWPFRun r42 = p42.createRun();
                r42.addPicture(stream42, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(200), Units.toEMU(200));
            }

            String folderName1 = CommonUtils.currentDateToStr(7);  //yyyymmdd
            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + createUser + "/" + folderName1 + "/" + docName + ".docx";

            File savDoc = new File(appConfig.getUavModelPath() + "/" + createUser + "/" + folderName1);
            if (!savDoc.exists() && !savDoc.isDirectory()) {
                savDoc.mkdirs();
            }

            FileOutputStream out = new FileOutputStream(new File(docSavePath));
            document.write(out);
            out.close();

            JSONObject result = new JSONObject();
            result.put("url", "/" + createUser + "/" + folderName1 + "/" + docName + ".docx");
            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }


    public R download11(JSONObject object){

        try{


            XWPFDocument document = new XWPFDocument();

            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText(" LX 디지털트윈 문화재 현상변경 분석");
            titleParagraphRun.setColor("000000");
            titleParagraphRun.setFontSize(20);

            JSONArray titleList = object.getJSONArray("list");

            for (int i = 0; i < titleList.size(); i++) {

                JSONObject item = titleList.getJSONObject(i);

                XWPFParagraph titleParagraph2 = document.createParagraph();
                titleParagraph2.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun titleParagraphRun2 = titleParagraph2.createRun();
                titleParagraphRun2.addBreak();
                titleParagraphRun2.addBreak();
                titleParagraphRun2.setText("문화재명：" + item.getString("nameOfCulturalHeritage")); //
                titleParagraphRun2.addBreak();
                titleParagraphRun2.setText("총면적：" + item.getString("totalArea")); //
                titleParagraphRun2.addBreak();
                titleParagraphRun2.setText("지정번호："+ item.getString("specifyNumber")); //
                titleParagraphRun2.addBreak();
                titleParagraphRun2.setText("건축물 개수："+ item.getString("numberOfBuildings")); //
                titleParagraphRun2.addBreak();
                titleParagraphRun2.setText("불법 건축물 개수："+ item.getString("numberOfIllegalBuildings")); //
                titleParagraphRun2.addBreak();
                titleParagraphRun2.setText("구역 이미지："); //
                //
                List<String> fileList = item.getJSONArray("fileList").toJavaList(String.class);
                for (int j = 0; j < fileList.size(); j++) {
                    InputStream stream = new FileInputStream(appConfig.getUavModelPath() + fileList.get(j));
                    titleParagraphRun2.addPicture(stream, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(400), Units.toEMU(200));
                    titleParagraphRun2.addBreak();
                }
                titleParagraphRun2.setColor("000000");

                titleParagraphRun2.setFontSize(11);
                titleParagraphRun2.addBreak();
                titleParagraphRun2.addBreak();
            }

            XWPFParagraph title3 = document.createParagraph();
            title3.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun titleRun3 = title3.createRun();
            titleRun3.setText("불법건축물 자세히보기 (동) ");
            titleRun3.setColor("000000");
            titleRun3.setFontSize(11);

            XWPFTable infoTable = document.createTable();

            //
            CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            infoTableWidth.setType(STTblWidth.DXA);
            infoTableWidth.setW(BigInteger.valueOf(9072));


            XWPFTableRow infoTableRowOne = infoTable.getRow(0);
            infoTableRowOne.setHeight(600);
            infoTableRowOne.getCell(0).setText("건물명칭"); //
            infoTableRowOne.addNewTableCell().setText("기본주소");//
            infoTableRowOne.addNewTableCell().setText("건물높이");//
            infoTableRowOne.addNewTableCell().setText("불법건축 초과높이");//
            infoTableRowOne.addNewTableCell().setText("적용 문화재 구역");//
            infoTableRowOne.addNewTableCell().setText("구역");//
            infoTableRowOne.addNewTableCell().setText("문화재 명칭");//

            infoTableRowOne.getCell(0).setColor("D9D9D9");
            infoTableRowOne.getCell(1).setColor("D9D9D9");
            infoTableRowOne.getCell(2).setColor("D9D9D9");
            infoTableRowOne.getCell(3).setColor("D9D9D9");
            infoTableRowOne.getCell(4).setColor("D9D9D9");
            infoTableRowOne.getCell(5).setColor("D9D9D9");
            infoTableRowOne.getCell(6).setColor("D9D9D9");

            //
            JSONArray list = object.getJSONArray("list1");
            for (int i = 0; i < list.size(); i++) {
                //
                XWPFTableRow infoTableRowItem = infoTable.createRow();
                infoTableRowItem.getCell(0).setText(list.getJSONObject(i).getString("buildingName"));
                infoTableRowItem.getCell(1).setText(list.getJSONObject(i).getString("baseAddress"));
                infoTableRowItem.getCell(2).setText(list.getJSONObject(i).getString("buildingHeight"));
                infoTableRowItem.getCell(3).setText(list.getJSONObject(i).getString("slabOverHeight"));
                infoTableRowItem.getCell(4).setText(list.getJSONObject(i).getString("appliedCulturalHeritageArea"));
                infoTableRowItem.getCell(5).setText(list.getJSONObject(i).getString("area"));
                infoTableRowItem.getCell(6).setText(list.getJSONObject(i).getString("nameOfCulturalHeritage"));
            }

            //
            String createUser = object.getString("createUser");
            String folderName1 = CommonUtils.currentDateToStr(7);  //yyyymmdd
            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + createUser + "/" +folderName1 +"/"+ docName + ".docx";

            File savDoc = new File(appConfig.getUavModelPath() + "/" + createUser + "/" +folderName1);
            if (!savDoc.exists() && !savDoc.isDirectory()) {
                savDoc.mkdirs();
            }

            FileOutputStream out = new FileOutputStream(new File(docSavePath));
            document.write(out);
            out.close();

            JSONObject result = new JSONObject();
            result.put("url", "/" + createUser + "/" +folderName1+ "/" + docName + ".docx");
            return R.success(result);
        }catch (Exception ex){
            return R.error();
        }

    }


    private String getString(JSONObject data,String key){
        if(data.getString(key)==null){
            return "";
        }
        return data.getString(key);
    }

    //data
    public R downloadExampleOfOccupationLicenseLedger(JSONObject data) {

        try {

            File xlsFile = new File(appConfig.getWordTemplate() + "/ExampleOfOccupationLicenseLedger.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsFile));
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row = sheet.getRow(5);  //data
            XSSFCell cell = null;

            cell = row.getCell(0);
            cell.setCellValue(this.getString(data,"aupmSect"));
            cell = row.getCell(1);
            cell.setCellValue(this.getString(data,"aupmNoTmpr"));
            cell = row.getCell(2);
            cell.setCellValue(this.getString(data,"aupmNoPrmanent"));
            cell = row.getCell(3);
            cell.setCellValue(this.getString(data,"lcnse"));
            cell = row.getCell(4);
            cell.setCellValue(this.getString(data,"rrno"));
            cell = row.getCell(5);
            cell.setCellValue(this.getString(data,"blwnChange"));
            cell = row.getCell(7);
            cell.setCellValue(this.getString(data,"rightDutySuccs"));
            cell = row.getCell(8);
            cell.setCellValue(this.getString(data,"bgncstDate"));
            cell = row.getCell(9);
            cell.setCellValue(this.getString(data,"cmcnDate"));
            cell = row.getCell(10);
            cell.setCellValue(this.getString(data,"phoneNum"));


            cell = row.getCell(11);
            cell.setCellValue(this.getString(data,"aupmDate"));
            cell = row.getCell(12);
            cell.setCellValue(this.getString(data,"aupmPurpsTmpr"));
            cell = row.getCell(13);
            cell.setCellValue(this.getString(data,"aupmPurpsPrmanent"));
            cell = row.getCell(14);
            cell.setCellValue(this.getString(data,"aupmPeriod_month"));
            cell = row.getCell(15);
            cell.setCellValue(this.getString(data,"aupmPeriodDay"));
            cell = row.getCell(16);
            String str16 = this.getString(data,"bldgLcDong") + this.getString(data,"bldgLcLi") + this.getString(data,"bldgLcMno") + this.getString(data,"bldgLcSno");
            cell.setCellValue(str16);  //----------
            cell = row.getCell(18);
            String str17 = this.getString(data,"ocpatDong") + this.getString(data,"ocpatLi") + this.getString(data,"ocpat_mno") + this.getString(data,"ocpatSno");
            cell.setCellValue(str17);  //----------
            cell = row.getCell(18+2);
            cell.setCellValue(this.getString(data,"ocpatTmprLen"));
            cell = row.getCell(19+2);
            cell.setCellValue(this.getString(data,"ocpatTmprWih"));
            cell = row.getCell(20+2);
            cell.setCellValue(this.getString(data,"ocpatTmprAr"));

            cell = row.getCell(21+2);
            cell.setCellValue(this.getString(data,"ocpatPrmanentLen"));
            cell = row.getCell(22+2);
            cell.setCellValue(this.getString(data,"ocpatPrmanentWih"));
            cell = row.getCell(23+2);
            cell.setCellValue(this.getString(data,"ocpatPrmanentAr"));
            cell = row.getCell(24+2);
            cell.setCellValue(this.getString(data,"prposHouseAr"));
            cell = row.getCell(25+2);
            cell.setCellValue(this.getString(data,"prposHouseElseAr"));
            cell = row.getCell(26+2);
            cell.setCellValue(this.getString(data,"prposHouseTotAr"));
            cell = row.getCell(27+2);
            cell.setCellValue(this.getString(data,"aupmPriceTmpr"));
            cell = row.getCell(28+2);
            cell.setCellValue(this.getString(data,"aupmPriceOalp"));
            cell = row.getCell(29+2);
            cell.setCellValue(this.getString(data,"aupmPricePrmanent"));
            cell = row.getCell(30+2);
            cell.setCellValue(this.getString(data,"aupmLevy_rateTmpr"));

            cell = row.getCell(31+2);
            cell.setCellValue(this.getString(data,"aupmLevy_ratePrmanent"));
            cell = row.getCell(32+2);
            cell.setCellValue(this.getString(data,"aupmCalcPriceTmpr"));
            cell = row.getCell(33+2);
            cell.setCellValue(this.getString(data,"aupmCalcPricePrmanent"));
            cell = row.getCell(34+2);
            cell.setCellValue(this.getString(data,"aupmCalcPricePrmanentOcpat"));
            cell = row.getCell(35+2);
            cell.setCellValue(this.getString(data,"aupmLevyPriceTmpr"));
            cell = row.getCell(36+2);
            cell.setCellValue(this.getString(data,"aupmLevyPricePrmanent"));
            cell = row.getCell(37+2);
            cell.setCellValue(this.getString(data,"aupmLevyPriceFdrm"));
            cell = row.getCell(38+2);
            cell.setCellValue(this.getString(data,"payTmpr"));
            cell = row.getCell(39+2);
            cell.setCellValue(this.getString(data,"payPrmanent"));
            cell = row.getCell(40+2);
            cell.setCellValue(this.getString(data,"bgncstDclr"));

            cell = row.getCell(41+2);
            cell.setCellValue(this.getString(data,"useApproval"));
            cell = row.getCell(42+2);
            cell.setCellValue(this.getString(data,"rm"));


            String createUser = "wordTemplate";
            String folderName1 = CommonUtils.currentDateToStr(7);  //yyyymmdd
            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + createUser + "/" + folderName1 + "/" + docName + ".xlsx";
            JSONObject result = new JSONObject();
            result.put("url", "/" + createUser + "/" + folderName1 + "/" + docName + ".xlsx");

            File savDoc = new File(appConfig.getUavModelPath() + "/" + createUser + "/" + folderName1);
            if (!savDoc.exists() && !savDoc.isDirectory()) {
                savDoc.mkdirs();
            }

            FileOutputStream outputStream = new FileOutputStream(docSavePath);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            wb.close();

            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }

    }


    //list
    public R downloadExampleOfOccupationLicenseLedger1(JSONObject par) {

        try {

            File xlsFile = new File(appConfig.getWordTemplate() + "/ExampleOfOccupationLicenseLedger1.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsFile));
            XSSFSheet sheet = wb.getSheetAt(0);
            int startRows = 5;
            XSSFRow row = null;
            XSSFCell cell = null;

            XSSFCellStyle style = wb.createCellStyle();
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);



            JSONArray list = par.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                JSONObject data = list.getJSONObject(i);

                sheet.createRow(startRows);
                row = sheet.getRow(startRows);  //data
                row.setHeightInPoints(20);
                for (int j = 0; j < 45; j++) {
                    cell = row.createCell(j);
                    cell.setCellStyle(style);

                }



                cell = row.getCell(0);
                cell.setCellValue(this.getString(data, "aupmSect"));
                cell = row.getCell(1);
                cell.setCellValue(this.getString(data, "aupmNoTmpr"));
                cell = row.getCell(2);
                cell.setCellValue(this.getString(data, "aupmNoPrmanent"));
                cell = row.getCell(3);
                cell.setCellValue(this.getString(data, "lcnse"));
                cell = row.getCell(4);
                cell.setCellValue(this.getString(data, "rrno"));
                cell = row.getCell(5);
                cell.setCellValue(this.getString(data, "blwnChange"));
                cell = row.getCell(7);
                cell.setCellValue(this.getString(data, "rightDutySuccs"));
                cell = row.getCell(8);
                cell.setCellValue(this.getString(data, "bgncstDate"));
                cell = row.getCell(9);
                cell.setCellValue(this.getString(data, "cmcnDate"));
                cell = row.getCell(10);
                cell.setCellValue(this.getString(data, "phoneNum"));


                cell = row.getCell(11);
                cell.setCellValue(this.getString(data, "aupmDate"));
                cell = row.getCell(12);
                cell.setCellValue(this.getString(data, "aupmPurpsTmpr"));
                cell = row.getCell(13);
                cell.setCellValue(this.getString(data, "aupmPurpsPrmanent"));
                cell = row.getCell(14);
                cell.setCellValue(this.getString(data, "aupmPeriod_month"));
                cell = row.getCell(15);
                cell.setCellValue(this.getString(data, "aupmPeriodDay"));
                cell = row.getCell(16);
                String str16 = this.getString(data, "bldgLcDong") + this.getString(data, "bldgLcLi") + this.getString(data, "bldgLcMno") + this.getString(data, "bldgLcSno");
                cell.setCellValue(str16);  //----------
                cell = row.getCell(18);
                String str17 = this.getString(data, "ocpatDong") + this.getString(data, "ocpatLi") + this.getString(data, "ocpat_mno") + this.getString(data, "ocpatSno");
                cell.setCellValue(str17);  //----------
                cell = row.getCell(18 + 2);
                cell.setCellValue(this.getString(data, "ocpatTmprLen"));
                cell = row.getCell(19 + 2);
                cell.setCellValue(this.getString(data, "ocpatTmprWih"));
                cell = row.getCell(20 + 2);
                cell.setCellValue(this.getString(data, "ocpatTmprAr"));

                cell = row.getCell(21 + 2);
                cell.setCellValue(this.getString(data, "ocpatPrmanentLen"));
                cell = row.getCell(22 + 2);
                cell.setCellValue(this.getString(data, "ocpatPrmanentWih"));
                cell = row.getCell(23 + 2);
                cell.setCellValue(this.getString(data, "ocpatPrmanentAr"));
                cell = row.getCell(24 + 2);
                cell.setCellValue(this.getString(data, "prposHouseAr"));
                cell = row.getCell(25 + 2);
                cell.setCellValue(this.getString(data, "prposHouseElseAr"));
                cell = row.getCell(26 + 2);
                cell.setCellValue(this.getString(data, "prposHouseTotAr"));
                cell = row.getCell(27 + 2);
                cell.setCellValue(this.getString(data, "aupmPriceTmpr"));
                cell = row.getCell(28 + 2);
                cell.setCellValue(this.getString(data, "aupmPriceOalp"));
                cell = row.getCell(29 + 2);
                cell.setCellValue(this.getString(data, "aupmPricePrmanent"));
                cell = row.getCell(30 + 2);
                cell.setCellValue(this.getString(data, "aupmLevy_rateTmpr"));

                cell = row.getCell(31 + 2);
                cell.setCellValue(this.getString(data, "aupmLevy_ratePrmanent"));
                cell = row.getCell(32 + 2);
                cell.setCellValue(this.getString(data, "aupmCalcPriceTmpr"));
                cell = row.getCell(33 + 2);
                cell.setCellValue(this.getString(data, "aupmCalcPricePrmanent"));
                cell = row.getCell(34 + 2);
                cell.setCellValue(this.getString(data, "aupmCalcPricePrmanentOcpat"));
                cell = row.getCell(35 + 2);
                cell.setCellValue(this.getString(data, "aupmLevyPriceTmpr"));
                cell = row.getCell(36 + 2);
                cell.setCellValue(this.getString(data, "aupmLevyPricePrmanent"));
                cell = row.getCell(37 + 2);
                cell.setCellValue(this.getString(data, "aupmLevyPriceFdrm"));
                cell = row.getCell(38 + 2);
                cell.setCellValue(this.getString(data, "payTmpr"));
                cell = row.getCell(39 + 2);
                cell.setCellValue(this.getString(data, "payPrmanent"));
                cell = row.getCell(40 + 2);
                cell.setCellValue(this.getString(data, "bgncstDclr"));

                cell = row.getCell(41 + 2);
                cell.setCellValue(this.getString(data, "useApproval"));
                cell = row.getCell(42 + 2);
                cell.setCellValue(this.getString(data, "rm"));

                startRows++;
            }

          //  sheet.setst

            String createUser = "wordTemplate";
            String folderName1 = CommonUtils.currentDateToStr(7);  //yyyymmdd
            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + createUser + "/" + folderName1 + "/" + docName + ".xlsx";
            JSONObject result = new JSONObject();
            result.put("url", "/" + createUser + "/" + folderName1 + "/" + docName + ".xlsx");

            File savDoc = new File(appConfig.getUavModelPath() + "/" + createUser + "/" + folderName1);
            if (!savDoc.exists() && !savDoc.isDirectory()) {
                savDoc.mkdirs();
            }

            FileOutputStream outputStream = new FileOutputStream(docSavePath);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            wb.close();

            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }

    }


    //region RoadOccupationPermitAccountTemplate  2022.6.20
    private String getExcelStringValue(XSSFCell cell) {
        String res = "";
        if (cell != null) {
            return cell.getStringCellValue();
        }
        return res;
    }
    public R readRoadOccupationPermitAccountTemplate(MultipartFile file) {

        try {
            String folderName = CommonUtils.currentDateToStr(7);
            String folder = appConfig.getUavModelPath() + File.separator + folderName;

            File fileDir = new File(folder);
            if (!fileDir.exists() && !fileDir.isDirectory()) {
                fileDir.mkdir();
            }

            String path = "";
            if (file != null && file.getSize() != 0) {
                path = folder + "/" + CommonUtils.getUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            if (!path.isEmpty()) {
                File saveFile11 = new File(path);
                file.transferTo(saveFile11);
            }

            JSONArray result = new JSONArray();

            File xlsFile = new File(path);
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsFile));
            XSSFSheet sheet = wb.getSheetAt(0);

            int indexRow = 3;
            int rows = sheet.getPhysicalNumberOfRows();

            for (int i = 3; i <= rows; i++) {
                JSONObject data = new JSONObject();
                XSSFRow row = sheet.getRow(i);


                if(row == null || row.getCell(0)==null){
                    continue;
                }

                for (int j = 0; j <= 52; j++) {
                    if (row.getCell(j) != null) {
                        row.getCell(j).setCellType(HSSFCell.CELL_TYPE_STRING);
                    }
                }

                data.put("rowNo",getExcelStringValue(row.getCell(0)));
                data.put("aupmSect",getExcelStringValue(row.getCell(1)));
                data.put("aupmNoTmpr",getExcelStringValue(row.getCell(2)));
                data.put("aupmNoPrmanent",getExcelStringValue(row.getCell(3)));
                data.put("lcnse",getExcelStringValue(row.getCell(4)));
                data.put("rrno",getExcelStringValue(row.getCell(5)));
                data.put("blwnChange",getExcelStringValue(row.getCell(6)));
                data.put("rightDutySuccs",getExcelStringValue(row.getCell(7)));
                data.put("bgncstDate",getExcelStringValue(row.getCell(8)));
                data.put("cmcnDate",getExcelStringValue(row.getCell(9)));
                data.put("phoneNum",getExcelStringValue(row.getCell(10)));
                data.put("aupmDate",getExcelStringValue(row.getCell(11)));
                data.put("aupmPurpsTmpr",getExcelStringValue(row.getCell(12)));
                data.put("aupmPurpsPrmanent",getExcelStringValue(row.getCell(13)));
                data.put("aupmPeriod_month",getExcelStringValue(row.getCell(14)));
                data.put("aupmPeriodDay",getExcelStringValue(row.getCell(15)));
                data.put("bldgLcDong",getExcelStringValue(row.getCell(16)));
                data.put("bldgLcLi",getExcelStringValue(row.getCell(17)));
                data.put("ocpatShan",getExcelStringValue(row.getCell(18)));
                data.put("bldgLc_mno",getExcelStringValue(row.getCell(19)));
                data.put("bldgLcSno",getExcelStringValue(row.getCell(20)));
                data.put("bldgLcEtc",getExcelStringValue(row.getCell(21)));
                data.put("ocpatDong",getExcelStringValue(row.getCell(22)));
                data.put("ocpatLi",getExcelStringValue(row.getCell(23)));
                data.put("bldgLcShan",getExcelStringValue(row.getCell(24)));
                data.put("ocpat_mno",getExcelStringValue(row.getCell(25)));
                data.put("ocpatSno",getExcelStringValue(row.getCell(26)));
                data.put("ocpatEtc",getExcelStringValue(row.getCell(27)));
                data.put("ocpatTmprLen",getExcelStringValue(row.getCell(28)));
                data.put("ocpatTmprWih",getExcelStringValue(row.getCell(29)));
                data.put("ocpatTmprAr",getExcelStringValue(row.getCell(30)));
                data.put("ocpatPrmanentLen",getExcelStringValue(row.getCell(31)));
                data.put("ocpatPrmanentWih",getExcelStringValue(row.getCell(32)));
                data.put("ocpatPrmanentAr",getExcelStringValue(row.getCell(33)));
                data.put("prposHouseAr",getExcelStringValue(row.getCell(34)));
                data.put("prposHouseElseAr",getExcelStringValue(row.getCell(35)));
                data.put("prposHouseTotAr",getExcelStringValue(row.getCell(36)));
                data.put("aupmPriceTmpr",getExcelStringValue(row.getCell(37)));
                data.put("aupmPriceOalp",getExcelStringValue(row.getCell(38)));
                data.put("aupmPricePrmanent",getExcelStringValue(row.getCell(39)));
                data.put("aupmLevy_rateTmpr",getExcelStringValue(row.getCell(40)));
                data.put("aupmLevy_ratePrmanent",getExcelStringValue(row.getCell(41)));
                data.put("aupmCalcPriceTmpr",getExcelStringValue(row.getCell(42)));
                data.put("aupmCalcPricePrmanent",getExcelStringValue(row.getCell(43)));
                data.put("aupmCalcPricePrmanentOcpat",getExcelStringValue(row.getCell(44)));
                data.put("aupmLevyPriceTmpr",getExcelStringValue(row.getCell(45)));
                data.put("aupmLevyPricePrmanent",getExcelStringValue(row.getCell(46)));
                data.put("aupmLevyPriceFdrm",getExcelStringValue(row.getCell(47)));
                data.put("payTmpr",getExcelStringValue(row.getCell(48)));
                data.put("payPrmanent",getExcelStringValue(row.getCell(49)));
                data.put("bgncstDclr",getExcelStringValue(row.getCell(50)));
                data.put("useApproval",getExcelStringValue(row.getCell(51)));
                data.put("rm",getExcelStringValue(row.getCell(52)));

                result.add(data);
            }
            wb.close();
            return R.success(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }

    }
    public R downloadRoadOccupationPermitAccount(JSONObject par) {
        try {

            File xlsFile = new File(appConfig.getWordTemplate() + "/RoadOccupationPermitAccountTemplate.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsFile));
            XSSFSheet sheet = wb.getSheetAt(0);
            int startRows = 3;
            XSSFRow row = null;
            XSSFCell cell = null;

            //add border
            XSSFCellStyle style = wb.createCellStyle();
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);

            JSONArray list = par.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                JSONObject data = list.getJSONObject(i);

                sheet.createRow(startRows);
                row = sheet.getRow(startRows);  //data
                row.setHeightInPoints(20);
                for (int j = 0; j <= 52; j++) {
                    cell = row.createCell(j);
                    cell.setCellStyle(style);

                }

                cell = row.getCell(0);
                cell.setCellValue(this.getString(data, "rowNo"));
                cell = row.getCell(1);
                cell.setCellValue(this.getString(data, "aupmSect"));
                cell = row.getCell(2);
                cell.setCellValue(this.getString(data, "aupmNoTmpr"));
                cell = row.getCell(3);
                cell.setCellValue(this.getString(data, "aupmNoPrmanent"));
                cell = row.getCell(4);
                cell.setCellValue(this.getString(data, "lcnse"));
                cell = row.getCell(5);
                cell.setCellValue(this.getString(data, "rrno"));
                cell = row.getCell(6);
                cell.setCellValue(this.getString(data, "blwnChange"));
                cell = row.getCell(7);
                cell.setCellValue(this.getString(data, "rightDutySuccs"));
                cell = row.getCell(8);
                cell.setCellValue(this.getString(data, "bgncstDate"));
                cell = row.getCell(9);
                cell.setCellValue(this.getString(data, "cmcnDate"));
                cell = row.getCell(10);
                cell.setCellValue(this.getString(data, "phoneNum"));


                cell = row.getCell(11);
                cell.setCellValue(this.getString(data, "aupmDate"));
                cell = row.getCell(12);
                cell.setCellValue(this.getString(data, "aupmPurpsTmpr"));
                cell = row.getCell(13);
                cell.setCellValue(this.getString(data, "aupmPurpsPrmanent"));
                cell = row.getCell(14);
                cell.setCellValue(this.getString(data, "aupmPeriod_month"));
                cell = row.getCell(15);
                cell.setCellValue(this.getString(data, "aupmPeriodDay"));
                cell = row.getCell(16);
                cell.setCellValue(this.getString(data, "bldgLcDong"));
                cell = row.getCell(17);
                cell.setCellValue(this.getString(data, "bldgLcLi"));
                cell = row.getCell(18);
                cell.setCellValue(this.getString(data, "ocpatShan"));
                cell = row.getCell(19 );
                cell.setCellValue(this.getString(data, "bldgLc_mno"));
                cell = row.getCell(20);
                cell.setCellValue(this.getString(data, "bldgLcSno"));

                cell = row.getCell(21);
                cell.setCellValue(this.getString(data, "bldgLcEtc"));
                cell = row.getCell(22);
                cell.setCellValue(this.getString(data, "ocpatDong"));
                cell = row.getCell(23);
                cell.setCellValue(this.getString(data, "ocpatLi"));
                cell = row.getCell(24);
                cell.setCellValue(this.getString(data, "bldgLcShan"));
                cell = row.getCell(25);
                cell.setCellValue(this.getString(data, "ocpat_mno"));
                cell = row.getCell(26);
                cell.setCellValue(this.getString(data, "ocpatSno"));
                cell = row.getCell(27);
                cell.setCellValue(this.getString(data, "ocpatEtc"));
                cell = row.getCell(28);
                cell.setCellValue(this.getString(data, "ocpatTmprLen"));
                cell = row.getCell(29);
                cell.setCellValue(this.getString(data, "ocpatTmprWih"));
                cell = row.getCell(30);
                cell.setCellValue(this.getString(data, "ocpatTmprAr"));

                cell = row.getCell(31);
                cell.setCellValue(this.getString(data, "ocpatPrmanentLen"));
                cell = row.getCell(32);
                cell.setCellValue(this.getString(data, "ocpatPrmanentWih"));
                cell = row.getCell(33);
                cell.setCellValue(this.getString(data, "ocpatPrmanentAr"));
                cell = row.getCell(34);
                cell.setCellValue(this.getString(data, "prposHouseAr"));
                cell = row.getCell(35);
                cell.setCellValue(this.getString(data, "prposHouseElseAr"));
                cell = row.getCell(36);
                cell.setCellValue(this.getString(data, "prposHouseTotAr"));
                cell = row.getCell(37);
                cell.setCellValue(this.getString(data, "aupmPriceTmpr"));
                cell = row.getCell(38);
                cell.setCellValue(this.getString(data, "aupmPriceOalp"));
                cell = row.getCell(39);
                cell.setCellValue(this.getString(data, "aupmPricePrmanent"));
                cell = row.getCell(40);
                cell.setCellValue(this.getString(data, "aupmLevy_rateTmpr"));

                cell = row.getCell(41);
                cell.setCellValue(this.getString(data, "aupmLevy_ratePrmanent"));
                cell = row.getCell(42);
                cell.setCellValue(this.getString(data, "aupmCalcPriceTmpr"));
                cell = row.getCell(43);
                cell.setCellValue(this.getString(data, "aupmCalcPricePrmanent"));
                cell = row.getCell(44);
                cell.setCellValue(this.getString(data, "aupmCalcPricePrmanentOcpat"));
                cell = row.getCell(45);
                cell.setCellValue(this.getString(data, "aupmLevyPriceTmpr"));
                cell = row.getCell(46);
                cell.setCellValue(this.getString(data, "aupmLevyPricePrmanent"));
                cell = row.getCell(47);
                cell.setCellValue(this.getString(data, "aupmLevyPriceFdrm"));
                cell = row.getCell(48);
                cell.setCellValue(this.getString(data, "payTmpr"));
                cell = row.getCell(49);
                cell.setCellValue(this.getString(data, "payPrmanent"));
                cell = row.getCell(50);
                cell.setCellValue(this.getString(data, "bgncstDclr"));

                cell = row.getCell(51);
                cell.setCellValue(this.getString(data, "useApproval"));
                cell = row.getCell(52);
                cell.setCellValue(this.getString(data, "rm"));

                startRows++;
            }


            String createUser = "wordTemplate";
            String folderName1 = CommonUtils.currentDateToStr(7);  //yyyymmdd
            String docName = CommonUtils.getUUID();
            String docSavePath = appConfig.getUavModelPath() + "/" + createUser + "/" + folderName1 + "/" + docName + ".xlsx";
            JSONObject result = new JSONObject();
            result.put("url", "/" + createUser + "/" + folderName1 + "/" + docName + ".xlsx");

            File savDoc = new File(appConfig.getUavModelPath() + "/" + createUser + "/" + folderName1);
            if (!savDoc.exists() && !savDoc.isDirectory()) {
                savDoc.mkdirs();
            }

            FileOutputStream outputStream = new FileOutputStream(docSavePath);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            wb.close();

            return R.success(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.error();
        }
    }
    public R downloadRoadOccupationPermitAccountTemplate() {
        JSONObject result = new JSONObject();
        result.put("url", "/wordTemplate/RoadOccupationPermitAccountTemplate.xlsx");
        return R.success(result);
    }
    //endregion


    public R download12(JSONObject data) {
        try {


            return R.success();
        } catch (Exception ex) {
            return R.error();
        }
    }











}
