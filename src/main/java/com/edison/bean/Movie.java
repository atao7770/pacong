package com.edison.bean;

public class Movie {

    private String movName;
    private String url;
    private String picUrl1;
    private String picUrl2;
    private String picUrl3;
    private String picUrl4;
    private String picUrl5;
    private String torrentUrl;
    private int type;

    public Movie() {

    }

    public Movie(String movName, String url, String picUrl1, String picUrl2,String picUrl3,String picUrl4,String picUrl5,String torrentUrl, int type) {
        this.movName = movName;
        this.url = url;
        this.picUrl1 = picUrl1;
        this.picUrl2 = picUrl2;
        this.picUrl3 = picUrl3;
        this.picUrl4 = picUrl4;
        this.picUrl5 = picUrl5;
        this.torrentUrl = torrentUrl;
        this.type = type;
    }

    public String getMovName() {
        return movName;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getTorrentUrl() {
        return torrentUrl;
    }

    public void setTorrentUrl(String torrentUrl) {
        this.torrentUrl = torrentUrl;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPicUrl1() {
        return picUrl1;
    }

    public void setPicUrl1(String picUrl1) {
        this.picUrl1 = picUrl1;
    }

    public String getPicUrl2() {
        return picUrl2;
    }

    public void setPicUrl2(String picUrl2) {
        this.picUrl2 = picUrl2;
    }

    public String getPicUrl3() {
        return picUrl3;
    }

    public void setPicUrl3(String picUrl3) {
        this.picUrl3 = picUrl3;
    }

    public String getPicUrl4() {
        return picUrl4;
    }

    public void setPicUrl4(String picUrl4) {
        this.picUrl4 = picUrl4;
    }

    public String getPicUrl5() {
        return picUrl5;
    }

    public void setPicUrl5(String picUrl5) {
        this.picUrl5 = picUrl5;
    }
}
