package controller_time;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBconnect;
import model.TimeCalculation;

/**
 * Servlet implementation class MonthSum
 */
@WebServlet("/sum")
public class MonthSum extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MonthSum() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreparedStatement pstmt;
        ResultSet rs1 , rs2 , rs3 , rs4;
        String Code = (String)request.getSession().getAttribute("code");
        Timestamp btime , ftime , brest , frest , bdate , fdate;
        long result1 , result2;
        long timetotal = 0 , resttotal = 0;
        int total1 , total2;
        List<Long> timelist = new ArrayList<Long>();
        List<Long> restlist = new ArrayList<Long>();
        String Wage = (String)request.getParameter("wage");
        int wage2 = Integer.parseInt(Wage);
        int timesum;

        //今月のデータを取得するために今月の初日と末日をtimestamp型で取得
        LocalDate now = LocalDate.now();
        LocalDate firstdate = now.withDayOfMonth(1);
        LocalDateTime firstDateTime = firstdate.atStartOfDay();
        int length = now.lengthOfMonth();
        LocalDate lastdate = now.withDayOfMonth(length);
        LocalDateTime lastDateTime = LocalDateTime.of(lastdate, LocalTime.MAX);
        bdate = Timestamp.valueOf(firstDateTime);
        fdate = Timestamp.valueOf(lastDateTime);

        try {
            Connection con = DBconnect.getConnection();

            //勤務時間と休憩時間を今月分のみ指定して取得する
            String sql1 = "select begin_time from begintime where code = '"+Code+"' and begin_time >= '"+bdate+"' and begin_time <= '"+fdate+"'";
            pstmt = con.prepareStatement(sql1);
            rs1 = pstmt.executeQuery();

            String sql2 = "select finish_time from finishtime where code = '"+Code+"' and finish_time >= '"+bdate+"' and finish_time <= '"+fdate+"'";
            pstmt = con.prepareStatement(sql2);
            rs2 = pstmt.executeQuery();

            String sql3 = "select begin_rest from beginrest where code = '"+Code+"' and begin_rest >= '"+bdate+"' and begin_rest <= '"+fdate+"'";
            pstmt = con.prepareStatement(sql3);
            rs3 = pstmt.executeQuery();

            String sql4 = "select finish_rest from finishrest where code = '"+Code+"' and finish_rest >= '"+bdate+"' and finish_rest <= '"+fdate+"'";
            pstmt = con.prepareStatement(sql4);
            rs4 = pstmt.executeQuery();

            while (rs1.next() && rs2.next()) {
                //String型に変換して、TimeCalculationを利用して勤務時間を計算しリストに格納する
                btime = rs1.getTimestamp("begin_time");
                SimpleDateFormat sdf1 = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                        Locale.ENGLISH);
                String str1 = sdf1.format(btime);

                ftime = rs2.getTimestamp("finish_time");
                SimpleDateFormat sdf2 = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                        Locale.ENGLISH);
                String str2 = sdf2.format(ftime);

                TimeCalculation timecalculation = new TimeCalculation();
                result1 = timecalculation.timecal(str1,str2);
                timelist.add(result1);
            }

            //String型に変換して、TimeCalculationを利用して休憩時間を計算しリストに格納する
            while (rs3.next() && rs4.next()) {
                brest = rs3.getTimestamp("begin_rest");
                SimpleDateFormat sdf3 = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                        Locale.ENGLISH);
                String str3 = sdf3.format(brest);

                frest = rs4.getTimestamp("finish_rest");
                SimpleDateFormat sdf4 = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                        Locale.ENGLISH);
                String str4 = sdf4.format(frest);

                TimeCalculation timecalculation = new TimeCalculation();
                result2 = timecalculation.timecal(str3,str4);
                restlist.add(result2);
            }

        } catch (ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        //勤務時間と休憩時間の合計を求める
        for(int i = 0; i<timelist.size(); i++){
            timetotal+=timelist.get(i);
        }

        for(int i = 0; i<restlist.size(); i++){
            resttotal+=restlist.get(i);
        }

        //出勤時間と退勤時間は時間単位で管理する
        total1 = (int) timetotal/60;

        //休憩時間は0.5時間単位で管理する
        total2 = (int) resttotal;
        int total2b = total2 * 10/60;
        double t2 = (double)total2b;
        double total2a = t2/10;

        //給料計算を行う
        double wage3 = (double)wage2;
        double timesumb = total2a * wage3;
        timesum = total1*wage2 - (int)timesumb ;

        request.setAttribute("timesum", timesum);
        request.setAttribute("timetotal", total1);
        request.setAttribute("resttotal", total2a);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/time/wage.jsp");
        rd.forward(request, response);

    }
}
