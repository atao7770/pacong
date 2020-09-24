package com.edison.util;

import java.sql.*;

public class DbUtil {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://111.229.153.193:3306/pcdemo?serverTimezone=UTC&useSSL=false";
    static final String USERNAME = "root";
    static final String PASSWORD = "oneinstack";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        Connection connection = getConnection();
//        try {
//            if(!connection.isClosed()){
//                System.out.println("---数据库链接成功---");
//                String sql = "select * from pcmov";
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ResultSet resultSet = ps.executeQuery();
//                while(resultSet.next()){
//                    // 通过字段检索
//                    int id  = resultSet.getInt("id");
//                    String movName = resultSet.getString("movname");
//                    String movUrl = resultSet.getString("movurl");
//
//                    // 输出数据
//                    System.out.print("ID: " + id);
//                    System.out.print(", 电影名称: " + movName);
//                    System.out.print(", 电影URL: " + movUrl);
//                    System.out.print("\n");
//                }
//                closeAll(connection , ps,resultSet);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


}
