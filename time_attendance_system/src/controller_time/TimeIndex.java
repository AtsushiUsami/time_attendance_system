package controller_time;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBconnect;


@WebServlet("/timeindex")
public class TimeIndex extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TimeIndex() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreparedStatement pstmt;
        ResultSet rs1 , rs2 , rs3 , rs4;
        String Code = (String)request.getSession().getAttribute("code");
        Timestamp btime , ftime , brest , frest;

        List<String> beginlist = new ArrayList<String>();
        List<String> finishlist = new ArrayList<String>();
        List<String> brestlist = new ArrayList<String>();
        List<String> frestlist = new ArrayList<String>();

            try {
                Connection con = DBconnect.getConnection();

                //勤務時間と休憩時間をリストに格納する
                String sql1 = "select begin_time from begintime where code = ?";

                pstmt = con.prepareStatement(sql1);
                pstmt.setString(1, Code);
                rs1 = pstmt.executeQuery();

                while (rs1.next()) {
                    btime = rs1.getTimestamp("begin_time");
                    SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd  HH:mm");
                    String str1 = sdf1.format(btime);
                    beginlist.add(str1);
                }

                String sql2 = "select finish_time from finishtime where code = ?";

                pstmt = con.prepareStatement(sql2);
                pstmt.setString(1, Code);
                rs2 = pstmt.executeQuery();

                while (rs2.next()) {
                    ftime = rs2.getTimestamp("finish_time");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd  HH:mm");
                    String str2 = sdf2.format(ftime);
                    finishlist.add(str2);
                }

                String sql3 = "select begin_rest from beginrest where code = ?";

                pstmt = con.prepareStatement(sql3);
                pstmt.setString(1, Code);
                rs3 = pstmt.executeQuery();

                while (rs3.next()) {
                    brest = rs3.getTimestamp("begin_rest");
                    SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd  HH:mm");
                    String str3 = sdf3.format(brest);
                    brestlist.add(str3);
                }

                String sql4 = "select finish_rest from finishrest where code = ?";

                pstmt = con.prepareStatement(sql4);
                pstmt.setString(1, Code);
                rs4 = pstmt.executeQuery();

                while (rs4.next()) {
                    frest = rs4.getTimestamp("finish_rest");
                    SimpleDateFormat sdf4 = new SimpleDateFormat("MM-dd  HH:mm");
                    String str4 = sdf4.format(frest);
                    frestlist.add(str4);
                }
            } catch (ClassNotFoundException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }

        //時間を格納したリストをjspに渡す
        request.setAttribute("beginlist", beginlist);
        request.setAttribute("finishlist", finishlist);
        request.setAttribute("brestlist", brestlist);
        request.setAttribute("frestlist", frestlist);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/time/monthsum.jsp");
        rd.forward(request, response);

    }

}



