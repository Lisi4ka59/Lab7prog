package com.lisi4ka.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BdConnect {
    //static String url = "jdbc:postgresql://localhost/studs?user=s368570&password=pmxs4Oo3i0cqC3b5";

    static String url = "jdbc:postgresql://localhost/postgres?user=postgres&password=Misha";
    public static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}