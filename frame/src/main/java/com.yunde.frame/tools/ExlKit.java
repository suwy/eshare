package com.yunde.frame.tools;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.easy.excel.ExcelDefinitionReader;
import org.easy.excel.config.ExcelDefinition;
import org.easy.excel.config.FieldValue;
import org.easy.excel.parsing.ExcelExport;
import org.easy.excel.util.ExcelUtil;
import org.easy.excel.xml.XMLExcelDefinitionReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExlKit {

    private static final Logger LOG  = LoggerFactory.getLogger(ExlKit.class);

    public static void createMoreSheet(ServletOutputStream outputStream, String locations, Map<String, List> beans) throws Exception {
        Workbook workbook = new SXSSFWorkbook();
        ExcelDefinitionReader definitionReader = new XMLExcelDefinitionReader(locations);
        ExcelExport excelExport = new ExcelExport(definitionReader);
        Iterator iterator = beans.keySet().iterator();
        try {
            while (iterator.hasNext()) {
                String excelId = iterator.next().toString();
                List data = beans.get(excelId);
                ExcelDefinition excelDefinition = definitionReader.getRegistry().get(excelId);
                Sheet sheet = workbook.createSheet(excelDefinition.getSheetname());
                Row titleRow = createTitle(excelDefinition,sheet,workbook);
                LOG.info("excel excelId="+excelDefinition.getSheetname());
                excelExport.createRows(excelDefinition, sheet, data,workbook,titleRow);
            }
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        } finally {
            outputStream.close();
            workbook.close();
        }
    }

    private static Row createTitle(ExcelDefinition excelDefinition,Sheet sheet,Workbook workbook) {
        //标题索引号
        int titleIndex = sheet.getPhysicalNumberOfRows();
        Row titleRow = sheet.createRow(titleIndex);
        List<FieldValue> fieldValues = excelDefinition.getFieldValues();
        for(int i=0;i<fieldValues.size();i++){
            FieldValue fieldValue = fieldValues.get(i);
            //设置单元格宽度
            if(fieldValue.getColumnWidth() !=null){
                sheet.setColumnWidth(i, fieldValue.getColumnWidth());
            }
            //如果默认的宽度不为空,使用默认的宽度
            else if(excelDefinition.getDefaultColumnWidth()!=null){
                sheet.setColumnWidth(i, excelDefinition.getDefaultColumnWidth());
            }
            Cell cell = titleRow.createCell(i);
            if(excelDefinition.getEnableStyle()){
                if(fieldValue.getAlign()!=null || fieldValue.getTitleBgColor()!=null || fieldValue.getTitleFountColor() !=null || excelDefinition.getDefaultAlign()!=null){
                    cell.setCellStyle(workbook.createCellStyle());
                    //设置cell 对齐方式
                    setAlignStyle(fieldValue, workbook, cell,excelDefinition);
                    //设置标题背景色
                    setTitleBgColorStyle(fieldValue, workbook, cell);
                    //设置标题字体色
                    setTitleFountColorStyle(fieldValue, workbook, cell);
                }
            }
            setCellValue(cell,fieldValue.getTitle());
        }
        return titleRow;
    }

    //设置cell 对齐方式
    private static void setAlignStyle(FieldValue fieldValue,Workbook workbook,Cell cell,ExcelDefinition excelDefinition){
        if(fieldValue.getAlign()!=null){
            CellStyle cellStyle = cell.getCellStyle();
            cellStyle.setAlignment( HorizontalAlignment.forInt( fieldValue.getAlign() ) );
            cell.setCellStyle(cellStyle);
        }else if(excelDefinition.getDefaultAlign()!=null){
            CellStyle cellStyle = cell.getCellStyle();
            cellStyle.setAlignment( HorizontalAlignment.forInt( excelDefinition.getDefaultAlign() ) );
            cell.setCellStyle(cellStyle);
        }
    }

    //设置cell 背景色方式
    private static void setTitleBgColorStyle(FieldValue fieldValue,Workbook workbook,Cell cell){
        if(fieldValue.getTitleBgColor()!=null){
            CellStyle cellStyle = cell.getCellStyle();
            cellStyle.setFillForegroundColor(fieldValue.getTitleBgColor());
//            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        }
    }

    //设置cell 字体颜色
    private static void setTitleFountColorStyle(FieldValue fieldValue,Workbook workbook,Cell cell){
        if(fieldValue.getTitleFountColor()!=null){
            CellStyle cellStyle = cell.getCellStyle();
            Font font = workbook.createFont();
            font.setColor(fieldValue.getTitleFountColor());
            cellStyle.setFont(font);
        }
    }

    private static void setCellValue(Cell cell, Object value) {
        ExcelUtil.setCellValue(cell, value);
    }
}
