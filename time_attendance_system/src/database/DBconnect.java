package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {

    private static Connection con;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        //ドライバのクラスをJava上で読み込む
        Class.forName("com.mysql.jdbc.Driver");
        //DBと接続する
        con = DriverManager.getConnection(
            "jdbc:mysql://localhost/time_attendance_system?useSSL=false",
            "timemaster",
            "timepass"
        );

        return con;
    }

    public static void close() {
        //接続を閉じる
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
