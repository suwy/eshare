package com.yunde.frame.tools;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;

/**
 * @author laisy
 * @date 2019/6/29
 * @description
 */
public class ReadFile {

    public void readCsv(String filePath) {
        StringBuilder createSql = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            String line = null;
            String tableNameCn = null;
            while((line=reader.readLine())!=null){
                String item[] = line.split(",");
                String tableName = item[0];
                String colum = item[2];
                String comment = item[3];
                if (!tableName.equals("")) {
                    if (createSql.length() != 0) {
                        createSql.deleteCharAt(createSql.length()-2).append(")COMMENT='").append(tableNameCn).append("';\n");
                    }
                    createSql.append("DROP TABLE IF EXISTS ").append(tableName)
                            .append(";\nCREATE TABLE ").append(tableName).append("(\n");
                }
                createSql.append(colum).append(" varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '")
                        .append(comment.replaceAll(" ","").replaceAll("　","")).append("',\n");
                tableNameCn = (item[1].equals("") ? tableNameCn: item[1]);
            }
            createSql.deleteCharAt(createSql.length()-2).append(")COMMENT='").append(tableNameCn).append("';\n");
            System.out.println(createSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readTxtContent(String filePath){
        File file = new File(filePath);
        StringBuilder result = new StringBuilder();
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            //使用readLine方法，一次读一行
            while((s = br.readLine())!=null){
//                result.append(System.lineSeparator()+s);
                result.append(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    public String readTxt(String filePath) {
        StringBuilder context = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            String line = null;
            while((line=reader.readLine())!=null){
                context.append(line).append(",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.toString();
    }

    public void readExl(String filePath) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
            POIFSFileSystem fs = new POIFSFileSystem(in);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFCell cell = null;
            int ignoreRows = 1;
            for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
                HSSFSheet st = wb.getSheetAt(sheetIndex);
                // 第一行为标题，不取
                for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                    HSSFRow row = st.getRow(rowIndex);
                    for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                        String value = "";
                        cell = row.getCell(columnIndex);
                        if (cell != null) {
                            // 注意：一定要设成这个，否则可能会出现乱码,后面版本默认设置
                            //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                            System.out.println(cell.getStringCellValue());
                        }
                    }
                    in.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}