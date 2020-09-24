package com.edison.wca;


import com.edison.bean.Movie;
import com.edison.util.GetAndPost;
import com.edison.util.ListBatchToDb;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HtmlJx {


    public static void jxHtml(String htmlTxt , String fileName,int type) throws IOException {
        List<Movie> list = new ArrayList<Movie>();
        Document doc = Jsoup.parse(htmlTxt);
        Elements eles = doc.select("div[class=thread thread_agrees_0 ]");
        Movie movie ;
        for (Element ele : eles){

            String movieUrl = "https://x.mcaoav.com/"+ele.select("a").get(1).attr("href");
            try {
                //从父页面获取子页面链接请求获取返回
                String sonHtmlTxt = GetAndPost.httpGet(movieUrl);
//            System.out.println(sonHtmlTxt);
                System.out.println("正在解析" + movieUrl + "的资源");
                //解析子页面获取图片和种子链接
                movie = getSonUrl(sonHtmlTxt);
            }catch(Exception e){
                continue;
            }
            if(movie.getPicUrl1()==null){
                continue;
            }
            movie.setMovName(ele.select("a").get(1).text());
//            System.out.println(movie.getMovName());
            movie.setUrl(movieUrl);
            movie.setType(type);
//            System.out.println(picUrl);
//            System.out.println(movie.getMovName()+movie.getPicUrl()+movie.getTorrentUrl());
            list.add(movie);
        }
        //写入数据库
        ListBatchToDb.insertList(list);
        //写入excel
//        WriteExcel writeExcel = new WriteExcel();
//        try {
//            writeExcel.getExcel(fileName,list);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


//    测试方法
    public static void main(String[] args) throws IOException {
        String sonHtmlTxt =GetAndPost.httpGet("https://x.mcaoav.com/thread-974265.htm");
        getSonUrl(sonHtmlTxt);
//        Movie movie = getSonUrl(sonHtmlTxt);
//        System.out.println(movie.getTorrentUrl()+movie.getPicUrl());
    }

    //解析子页面获取图片和种子url
    public static Movie getSonUrl(String htmlTxt) {
        Movie movie = new Movie();
        String torrentUrl = null;
        String cont = "http";
        Document document = Jsoup.parse(htmlTxt);
        Elements eles = document.select("div[align=center]");
        if(eles.select("img").isEmpty()){
            System.out.println("无图跳出");
            return movie;
        }
//        System.out.println(eles);
        //包含的图片数量
        int imgNum = eles.select("img").size();
//        System.out.println("@@"+imgNum);
        switch (imgNum){
            case 1:
                movie.setPicUrl1(eles.select("img").get(0).attr("src"));
                break;
            case 2:
                movie.setPicUrl1(eles.select("img").get(0).attr("src"));
                movie.setPicUrl2(eles.select("img").get(1).attr("src"));
                break;
            case 3:
                movie.setPicUrl1(eles.select("img").get(0).attr("src"));
                movie.setPicUrl2(eles.select("img" ).get(1).attr("src"));
                movie.setPicUrl3(eles.select("img").get(2).attr("src"));
                break;
            case 4:
                movie.setPicUrl1(eles.select("img").get(0).attr("src"));
                movie.setPicUrl2(eles.select("img").get(1).attr("src"));
                movie.setPicUrl3(eles.select("img").get(2).attr("src"));
                movie.setPicUrl4(eles.select("img").get(3).attr("src"));
                break;
            default:
                movie.setPicUrl1(eles.select("img").get(0).attr("src"));
                movie.setPicUrl2(eles.select("img").get(1).attr("src"));
                movie.setPicUrl3(eles.select("img").get(2).attr("src"));
                movie.setPicUrl4(eles.select("img").get(3).attr("src"));
                movie.setPicUrl5(eles.select("img").get(4).attr("src"));
                break;
        }
        if("".equals(movie.getPicUrl1())){
            switch (imgNum){
                case 1:
                    movie.setPicUrl1(eles.select("img").get(0).attr("ess-data"));
                    break;
                case 2:
                    movie.setPicUrl1(eles.select("img").get(0).attr("ess-data"));
                    movie.setPicUrl2(eles.select("img").get(1).attr("ess-data"));
                    break;
                case 3:
                    movie.setPicUrl1(eles.select("img").get(0).attr("ess-data"));
                    movie.setPicUrl2(eles.select("img" ).get(1).attr("ess-data"));
                    movie.setPicUrl3(eles.select("img").get(2).attr("ess-data"));
                    break;
                case 4:
                    movie.setPicUrl1(eles.select("img").get(0).attr("ess-data"));
                    movie.setPicUrl2(eles.select("img").get(1).attr("ess-data"));
                    movie.setPicUrl3(eles.select("img").get(2).attr("ess-data"));
                    movie.setPicUrl4(eles.select("img").get(3).attr("ess-data"));
                    break;
                default:
                    movie.setPicUrl1(eles.select("img").get(0).attr("ess-data"));
                    movie.setPicUrl2(eles.select("img").get(1).attr("ess-data"));
                    movie.setPicUrl3(eles.select("img").get(2).attr("ess-data"));
                    movie.setPicUrl4(eles.select("img").get(3).attr("ess-data"));
                    movie.setPicUrl5(eles.select("img").get(4).attr("ess-data"));
                    break;
            }
        }

        //截取下载地址后的字符串
        String subStr = eles.toString().substring(eles.toString().indexOf("地址")+1);
//        System.out.println(subStr);
        if(subStr.contains("地址")){
            subStr = subStr.substring(subStr.indexOf("地址")+1);
        }
//        System.out.println(subStr);
        document = Jsoup.parse(subStr);
        if(!document.select("a").isEmpty()){
            torrentUrl = document.select("a").attr("href");
            if(torrentUrl.contains(cont)){
                movie.setTorrentUrl(torrentUrl);
            }else{
                try{
                movie.setTorrentUrl(eles.select("a[target=_blank]").get(1).attr("href"));
                }catch (Exception e){
                    movie.setPicUrl1(null);
                }
            }
        }
        return movie;
    }


}