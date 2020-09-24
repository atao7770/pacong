package com.edison.acmov;

import com.edison.bean.Movie;
import com.edison.util.GetAndPost;
import com.edison.util.ListBatchToDb;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class JxAc {


    public static void jxAcList(String htmlTxt,int type){
        List<Movie> list = new ArrayList<Movie>();
        Document document = Jsoup.parse(htmlTxt);
        Elements eles = document.select("tr[align=center]");
        Movie movie ;
        for (Element element : eles){
//            System.out.println(element);
            String movUrl = "https://acbt.dasemm.com/bt/" + element.select("h3 > a").attr("href");
            System.out.println("正在解析"+movUrl);
            movie = jxDetail(movUrl);
            movie.setUrl(movUrl);
            movie.setType(type);
            list.add(movie);
        }
        //写入数据库
        ListBatchToDb.insertList(list);
    }


//    public static void main(String[] args) {
//        String url = "https://acbt.dasemm.com/bt/htm_data/16/1607/853800.html";
//        Movie movie = jxDetail(url);
//        System.out.println(movie.getPicUrl()+"@@"+movie.getTorrentUrl()+movie.getMovName());
//
//    }

    public static Movie jxDetail(String movUrl){
        String detail = GetAndPost.gbkGet(movUrl);
        Document document = Jsoup.parse(detail);
        Movie movie = new Movie();
        movie.setMovName(document.getElementById("subject_tpc").text());
        Element element = document.getElementById("read_tpc");
        movie.setPicUrl1(element.select("img").attr("src"));
        String jqStr = element.toString().substring(element.toString().indexOf("地址")+1);
//        System.out.println(jqStr);
        document = Jsoup.parse(jqStr);
        String torrentUrl = null;
        if(!document.select("a").isEmpty()){
            torrentUrl = document.select("a").attr("href");
            movie.setTorrentUrl(torrentUrl);
        }
        return movie;
    }




}
