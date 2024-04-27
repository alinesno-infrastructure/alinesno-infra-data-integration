package com.alinesno.infra.data.integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClickHouseConfig {

    private static String clickhouseAddress;

    private static String clickhouseUsername;

    private static String clickhousePassword;

    private static String clickhouseDB;

    private static Integer clickhouseSocketTimeout;

    @Value("${spring.clickhouse.address}")
    public  void setClickhouseAddress(String address) {
        ClickHouseConfig.clickhouseAddress = address;
    }
    @Value("${spring.clickhouse.username}")
    public  void setClickhouseUsername(String username) {
        ClickHouseConfig.clickhouseUsername = username;
    }
    @Value("${spring.clickhouse.password}")
    public  void setClickhousePassword(String password) {
        ClickHouseConfig.clickhousePassword = password;
    }
    @Value("${spring.clickhouse.db}")
    public  void setClickhouseDB(String db) {
        ClickHouseConfig.clickhouseDB = db;
    }
    @Value("${spring.clickhouse.socketTimeout}")
    public  void setClickhouseSocketTimeout(Integer socketTimeout) {
        ClickHouseConfig.clickhouseSocketTimeout = socketTimeout;
    }

    public static Connection getConn() {

        ClickHouseConnection conn = null;
        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setUser(clickhouseUsername);
        properties.setPassword(clickhousePassword);
        properties.setDatabase(clickhouseDB);
        properties.setSocketTimeout(clickhouseSocketTimeout);
        ClickHouseDataSource clickHouseDataSource = new ClickHouseDataSource(clickhouseAddress,properties);
        try {
            conn = clickHouseDataSource.getConnection();
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Map<String,String>> exeSql(String sql){
        Connection connection = getConn();
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();
            List<Map<String,String>> list = new ArrayList<>();
            while(results.next()){
                Map<String,String> row = new HashMap<>();
                for(int i = 1;i<=rsmd.getColumnCount();i++){
                    row.put(rsmd.getColumnName(i),results.getString(rsmd.getColumnName(i)));
                }
                list.add(row);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
