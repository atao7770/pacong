package com.edison.main;

import com.edison.acmov.JxAc;
import com.edison.util.GetAndPost;
import com.edison.wca.HtmlJx;

import java.io.IOException;

public class Main {


//    public static void main(String[] args) {
//        int type = 16;
//        for (int i=1177;i<1500;i++){
//            System.out.println("第" +i + "页");
//            String url = "https://acbt.dasemm.com/bt/thread.php?fid="+type+"&page="+i;
//            String htmlTxt = GetAndPost.gbkGet(url);
//            JxAc.jxAcList(htmlTxt , type);
//        }
//    }

    public static void main(String[] args) throws IOException {
        String fileName = "/users/edison/down/movie.xls";
        int type = 8;
        for (int i = 1264; i <= 2000; i++){
            String url = "https://x.mcaoav.com/forum-"+type+"-"+i+"-tid.htm";
            System.out.println("第"+i+"页"+url);
            String responStr = GetAndPost.httpGet(url);
            if(responStr != null){
                HtmlJx.jxHtml(responStr,fileName,type);
            }
        }
    }


}
