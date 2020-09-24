package com.edison.util;

import com.edison.bean.Movie;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListBatchToDb {


//    public static void main(String[] args) {
//        Movie m1 = new Movie("2","2","2","2",1);
//        Movie m2 = new Movie("3","3","3","3",1);
//        List<Movie> list = new ArrayList<Movie>(){};
//        list.add(m1);
//        list.add(m2);
//        insertList(list);
//
//    }


    public static void insertList(List<Movie> list){
        Object[][] params = new Object[list.size()][];
        for(int i = 0; i<list.size(); i++){
//            System.out.println(i+list.get(0).getMovName()+"@"+list.size());
            params[i] = new Object[]{list.get(i).getMovName(),list.get(i).getUrl(),list.get(i).getPicUrl1(),list.get(i).getPicUrl2(),list.get(i).getPicUrl3(),list.get(i).getPicUrl4(),list.get(i).getPicUrl5(),list.get(i).getTorrentUrl(),list.get(i).getType()};
        }
        batch(params);
    }


    public static void batch(Object[][] params){
        QueryRunner queryRunner = new QueryRunner(true);
        String sql = "INSERT INTO newpcdata (movname,movurl,picurl1,picurl2,picurl3,picurl4,picurl5,torrenturl,type) VALUES (?,?,?,?,?,?,?,?,?)";
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String time = dateFormat.format(date);
        try {
            Connection connection = DbUtil.getConnection();
            System.out.println(time + "正在将"+params.length+"条数据插入数据库");
            queryRunner.batch(connection , sql , params );
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
