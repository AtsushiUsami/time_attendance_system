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

/**
 * Servlet implementation class BeginRest
 */
@WebServlet("/brest")
public class BeginRest extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public BeginRest() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreparedStatement pstmt;
        ResultSet rs1;
        ResultSet rs2;
        int count1 = 0;
        int count2 = 0;
        String error = "既に休憩しているか、出勤していません。";

        String Code = (String)request.getSession().getAttribute("code");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            try {
                Connection con = DBconnect.getConnection();

                //rest_checkとattendance_checkの値を確認する(1が格納されている場合は既に出勤、休憩開始している)
                String sql1 =  "select count((code = '"+Code+"' AND check_flag = 1) or null) as c from rest_check";
                pstmt = con.prepareStatement(sql1);
                rs1= pstmt.executeQuery();
                rs1.next();

                count1 = rs1.getInt("c");

                String sql2 =  "select count((code = '"+Code+"' AND check_flag = 1) or null) as c2 from attendance_check";
                pstmt = con.prepareStatement(sql2);
                rs2 = pstmt.executeQuery();
                rs2.next();

                count2 = rs2.getInt("c2");

                //既に休憩している、もしくはまだ出勤していない場合エラーをトップページに返す
                if(count1 == 1 || count2 !=1){

                    request.setAttribute("error", error);
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login/toppage.jsp");
                    rd.forward(request, response);

   }else{
       //beginrestテーブルに休憩開始時間を記録する
      String sql3 = "insert into beginrest (code, begin_rest) values (?,?)";

      pstmt = con.prepareStatement(sql3);
      pstmt.setString(1,Code);
      pstmt.setTimestamp(2,timestamp);

      pstmt.executeUpdate();

      //rest_checkに1を格納する
      String sql4 = "insert into rest_check (code, check_flag) value (?,?)";

      pstmt = con.prepareStatement(sql4);
      pstmt.setString(1,Code);
      pstmt.setInt(2,1);

      pstmt.executeUpdate();

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
