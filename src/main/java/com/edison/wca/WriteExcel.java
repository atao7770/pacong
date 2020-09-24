package com.edison.wca;

import com.edison.bean.Movie;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.ClientAnchor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WriteExcel {

    public void getExcel(String fileName , List<Movie> list) throws IOException {
        boolean flag = fileExist(fileName);
        HSSFWorkbook workbook = new HSSFWorkbook();
        if(flag == true){
            addWriteExcel(workbook , fileName , list);
        }else{
            writeExcel(workbook , fileName , list);
        }
    }

    public static  boolean fileExist(String fileName){
        boolean flag = false;
        File file = new File(fileName);
        flag = file.exists();
        return flag;
    }


    public void writeExcel(HSSFWorkbook workbook ,String fileName , List<Movie> list){
        HSSFSheet sheet = workbook.createSheet("movie");
        sheet.setDefaultRowHeight((short) (100*20));
        sheet.setColumnWidth(0, 256*80+184);
        sheet.setColumnWidth(1, 256*40+184);
        sheet.setColumnWidth(2, 256*40+184);
        sheet.setColumnWidth(3, 256*100+184);
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("名称");
        headRow.createCell(1).setCellValue("电影url");
        headRow.createCell(2).setCellValue("图片url");
        headRow.createCell(3).setCellValue("种子url");
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        int row = 1;
        for (Movie movie : list){
            createCell(movie , sheet);
            picExcel(workbook , patriarch , row, movie.getPicUrl1());
            row ++;
        }
        File file = new File(fileName);
        try {
            workbook.write(file);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 创建Excel的一行数据。
    private void createCell(Movie movie, HSSFSheet sheet) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue(movie.getMovName());
        dataRow.createCell(1).setCellValue(movie.getUrl());
        dataRow.createCell(2).setCellValue(movie.getPicUrl1());
        dataRow.createCell(3).setCellValue(movie.getTorrentUrl());
    }

    public void addWriteExcel(HSSFWorkbook workbook ,String fileName , List<Movie> list) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(fileInputStream);
        workbook = new HSSFWorkbook(poifsFileSystem);
        HSSFSheet sheet = workbook.getSheetAt(0);
//        HSSFRow hssfRow = sheet.getRow(0);
        System.out.println(sheet.getLastRowNum());

        FileOutputStream outputStream = new FileOutputStream(fileName);
//        hssfRow = sheet.createRow(sheet.getLastRowNum()+1);
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        int row = sheet.getLastRowNum()+1;
        for (Movie movie : list){
            createCell(movie , sheet);
            picExcel(workbook , patriarch , row, movie.getPicUrl1());
            row ++;
        }
        outputStream.flush();
        workbook.write(outputStream);
        outputStream.close();

    }



    public  void picExcel (HSSFWorkbook wb, HSSFPatriarch patriarch, int rowIndex,String picUrl){
        try {
            URL url = new URL(picUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20 * 1000);
//            System.out.println(connection.getContentLength());
            if(connection.getResponseCode() ==200 && connection.getContentLength()!= -1){
                InputStream inputStream = connection.getInputStream();
                byte[] data = readInputStream(inputStream);
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 250,(short) 5, rowIndex, (short) 9, rowIndex);
                anchor.setAnchorType(ClientAnchor.AnchorType.byId(0));
                patriarch.createPicture(anchor, wb.addPicture(data, wb.PICTURE_TYPE_JPEG));
                inputStream.close();
            }
            connection.disconnect();
        } catch (Exception e) {
            System.out.println("图片下载流有问题"+picUrl);
//            e.printStackTrace();
        }
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        outStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

}
