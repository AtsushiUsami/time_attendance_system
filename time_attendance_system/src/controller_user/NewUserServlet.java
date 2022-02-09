package controller_user;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBconnect;
import model.PassEncryption;

/**
 * Servlet implementation class NewUserServlet
 */
@WebServlet("/new")
public class NewUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //新規ユーザー登録ページを表示する
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/users/new.jsp");
        rd.forward(request, response);
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreparedStatement pstmt;
        ResultSet rs;
        String Code = request.getParameter("code");
        String Name = request.getParameter("name");
        String Password = request.getParameter("password");
        String error = "このユーザーコードは既に登録されています。";

      //パスワードを暗号化する
        ServletContext context = this.getServletContext();
        String path = context.getRealPath("/META-INF/SecurityPepper");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        StringBuffer sb = new StringBuffer();
        int c;
        while ((c = br.read()) != -1){
            sb.append((char) c);
        }
        String security = sb.toString();
        br.close();
        PassEncryption pn = new PassEncryption();
        String realpass = pn.PassEncrypt(Password,security);

            try {
                Connection con = DBconnect.getConnection();

                //既に登録されているユーザーコードかどうかを確認する
                String sql1 = "select count( * ) as c from users where code = '"+Code+"'";
                pstmt = con.prepareStatement(sql1);
                rs = pstmt.executeQuery();
                rs.next();
                int count = rs.getInt("c");

                if(count == 0){
                //新規登録を行う
                String sql = "insert into users (code, name, password) values (?,?,?)";

                pstmt = con.prepareStatement(sql);
                pstmt.setString(1,Code);
                pstmt.setString(2,Name);
                pstmt.setString(3,realpass);
                pstmt.executeUpdate();

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/users/registration.jsp");
                rd.forward(request, response);
      }else{
                request.setAttribute("error", error);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/users/new.jsp");
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
