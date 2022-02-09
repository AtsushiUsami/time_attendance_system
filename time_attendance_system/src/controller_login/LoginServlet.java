package controller_login;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ログイン画面を表示
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login/login.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String Code = request.getParameter("code");
        String Password = request.getParameter("password");
        String error = "必須項目が未入力か、ユーザーネームまたはパスワードが間違っています。";

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

        //入力された情報に合致するユーザー数をカウントする
                   try {
                        Connection con = DBconnect.getConnection();
                        String sql = "select count((code = '"+Code+"' AND password = '"+realpass+"') or null) as c from users";

                        pstmt = con.prepareStatement(sql);
                        rs = pstmt.executeQuery();
                        rs.next();

                        count = rs.getInt("c");

                    } catch (ClassNotFoundException e) {
                        // TODO 自動生成された catch ブロック
                        e.printStackTrace();
                    } catch (SQLException e) {
                        // TODO 自動生成された catch ブロック
                        e.printStackTrace();
                    }

      //カウントが1の時ログイン、それ以外の時はログイン画面に戻す
      if(count == 1){
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login/toppage.jsp");
         rd.forward(request, response);
         request.getSession().setAttribute("code", Code);
      }else{
         request.setAttribute("error", error);
         RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login/login.jsp");
         rd.forward(request, response);


        }
    }}


