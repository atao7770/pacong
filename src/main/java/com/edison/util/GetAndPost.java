package com.edison.util;

import com.edison.acmov.JxAc;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GetAndPost {


    public static void main(String[] args) {
        String url = "https://acbt.dasemm.com/bt/htm_data/16/2001/1014656.html";
        String htmlTxt = gbkGet(url);
        System.out.println(htmlTxt);
//        JxAc.jxAcList(htmlTxt);
    }

    public static String  gbkGet(String url) {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        String string = null;
        try {
            CloseableHttpResponse response = httpClient.execute(get);
            if(response.getStatusLine().getStatusCode()==200) {
                HttpEntity entity = response.getEntity();
                //将实体装成字符串
                string = EntityUtils.toString(entity, "GBK");
            }
            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static String httpGet(String url){
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        CloseableHttpClient closeableHttpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        String string = null;
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity entity = response.getEntity();
                //使用工具类EntityUtils，从响应中取出实体表示的内容并转换成字符串
                string = EntityUtils.toString(entity, "utf-8");
            }
            response.close();
            closeableHttpClient.close();
        } catch ( ConnectTimeoutException ex) {
            System.out.println("请求连接超时");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("请求异常，异常信息：" + ex.getMessage());
        }
//        System.out.println(string);
        return string;
    }



}
