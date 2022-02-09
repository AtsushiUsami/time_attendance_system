package controller_time;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBconnect;

@WebServlet("/beginrecord")
public class BeginRecord extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BeginRecord() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;
        String error = "既に出勤しています。";
        String Code = (String)request.getSession().getAttribute("code");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            try {
                //データベースに接続して既に出勤しているかどうかを判断する(countが1の時は既に出勤している)
                Connection con = DBconnect.getConnection();

                String sql1 =  "select count((code = '"+Code+"' AND check_flag = 1) or null) as c from attendance_check";
                pstmt = con.prepareStatement(sql1);
                rs = pstmt.executeQuery();
                rs.next();

                count = rs.getInt("c");

                //countが1の時はエラーをトップページに返す
                if(count == 1){

                    request.setAttribute("error", error);
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login/toppage.jsp");
                    rd.forward(request, response);

   }else{
       //countが1ではない時、begintimeテーブルに出勤時間を記録する
      String sql2 = "insert into begintime (code, begin_time) values (?,?)";

      pstmt = con.prepareStatement(sql2);
      pstmt.setString(1,Code);
      pstmt.setTimestamp(2,timestamp);

      pstmt.executeUpdate();

      //出勤時間を記録した後、attendance_checkに1を格納する
      String sql3 = "insert into attendance_check (code, check_flag) value (?,?)";

      pstmt = con.prepareStatement(sql3);
      pstmt.setString(1,Code);
      pstmt.setInt(2,1);

      pstmt.executeUpdate();

      //トップページに戻る
      RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login/toppage.jsp");
      rd.forward(request, response);

   }
            } catch (ClassNotFoundException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
            }

    }

