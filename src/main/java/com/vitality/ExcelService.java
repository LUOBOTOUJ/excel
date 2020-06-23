package com.vitality;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ExcelService {

    public static void parseExcel(String filePath) throws Exception {
        boolean isExcel2003 = filePath.toLowerCase().endsWith("xls") ? true : false;
        List<Object[]> datas = new ArrayList<Object[]>();//用来存数据

        if (isExcel2003) {
            datas = readXLS(filePath);
        } else {
            datas = readXLSX(filePath);
        }

        exportFile(datas, new File("/Users/egoshiten/Desktop/aaaa.xls"));



    }

    public static List<File> getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        List<File> filelist = new ArrayList<>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
//                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else {
//                    String strFileName = files[i].getAbsolutePath();
                    filelist.add(files[i]);
                }
            }
        }
        return filelist;
    }


    private static int exportFile(List<Object[]> datas, File file) throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));// 附加
        // 添加数据
        int index = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < datas.size(); i++) {
            Object[] data = datas.get(i);
            for (int j = 0; j < data.length; j++) {
                sb.append(data[j]);//\177
            }
            bw.write(sb.toString());
            sb.setLength(0);
            bw.newLine();
            if (index % 50 == 0) {
                bw.flush();
            }
        }
        bw.close();
        return index;
    }

    private static List<ExcelEntity> readXLS(String filePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(filePath);
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        List<ExcelEntity> datas = new ArrayList<>();//用来存数据



            /*** step2：取得所需工作区间(下标从0开始) */
            HSSFSheet sheet = workbook.getSheetAt(0);

            /*** step3：getPhysicalNumberOfRows获取总共有多少行数据因为中间空行的话，则读取出来的数据不准确 */
            /** 获取的是最后一行的编号（编号从0开始）。*/

            int hasRowNum = sheet.getPhysicalNumberOfRows();
            //去除第一列为空的行数
            int c = 0;
            for(int i = 0;i < hasRowNum;i++){
                HSSFRow row = sheet.getRow(i);
                HSSFCell cell = row.getCell(0);
                if (cell != null){
                    if (!cell.equals("")) {c++;}
                }
            }

            if (c == 0) {//sheet中所有行都没有内容
                return datas;
            } else {
            }
            for (int j = 3; j < c+1; j++) {
                /** step4: 获取每一行 */
                HSSFRow row = sheet.getRow(j);
                /** step5 : 去除空行 */
                if (row != null) {
                    /** step6: 获取每一行的长度 */
                    int length = row.getLastCellNum();
                    if (length > 0) {
                        ExcelEntity data = new ExcelEntity();//定义一个集合，装每一行的数值
                        for (int m = 0; m < length; m++) {

                            /** step7: 获取每一行的每一列的值 */
                            if (row.getCell(m).getCellType() == CellType.NUMERIC) {
                                data[m] = Double.valueOf(row.getCell(m).getNumericCellValue()).intValue();
                            } else {
                                if (row.getCell(m) != null){
                                    data[m] = row.getCell(m);
                                }else{
                                    data[m] = null;
                                }

                            }
                        }
                        /** step8: 存数据 */
                        datas.add(data);
                    }
                }
            }

        /** step9: 关闭输入流 */
        inputStream.close();
        /** step10: 返回数据 */
        return datas;
    }

    private static List<Object[]> readXLSX(String filePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(filePath));
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        List<Object[]> datas = new ArrayList<>();//定义一个list用来存数据
            /** step2: 获取某一工作区间 */
            XSSFSheet sheet = workbook.getSheetAt(0);
            if (sheet == null ) {
                return datas;
            }
            /*** step3：getPhysicalNumberOfRows获取总共有多少行数据因为中间空行的话，则读取出来的数据不准确 */
            /** 获取的是最后一行的编号（编号从0开始）。*/
            int hasRowNum = sheet.getPhysicalNumberOfRows();
            //默认从第二行读取(第一行表头或空行不读)
            int jj = 3;

            /** step4: 取每一行的数据 */
            for (int j = jj; j < hasRowNum; j++) {
                XSSFRow row = sheet.getRow(j);
                /** step5: 去空行 */
                if (row == null) {
                    continue;
                }
                /** step6: 取每一行的长度 */
                int length = row.getLastCellNum();

                Object[] data = new Object[length];//定义一个数组用来存数据
                /** step7: 取每一列的数据 */
                for (int k = 0; k < length; k++) {
                    XSSFCell cell = row.getCell(k);
                    if (cell.getCellType() == CellType.NUMERIC) {
                        data[k] = Double.valueOf(cell.getNumericCellValue()).intValue();
                    } else {
                        data[k] = cell;
                    }
                }
                /** step8: 存数据 */
                datas.add(data);
            }
            /** step9: 关闭输入流 */
            inputStream.close();

        /** step10: 返回数据 */
        return datas;
    }


    public void insertByExcel(String url) throws Exception{
        final List<File> files = getFileList(url);
        List<ExcelEntity> datas = new ArrayList<>();//用来存数据

        for (int i = 0; i < files.size(); i++) {
            boolean isExcel2003 = files.get(i).getPath().toLowerCase().endsWith("xls") ? true : false;
            List<ExcelEntity> data = new ArrayList<>();
            if (isExcel2003) {
                data = readXLS(files.get(i).getPath());
                System.out.println(data);
            } else {
               // data = readXLSX(files.get(i).getPath());
            }
            datas.addAll(data);
        }
        System.out.println(datas);
        exportFile(datas, new File("/Users/egoshiten/Desktop/aaaa.xls"));
        }


}
