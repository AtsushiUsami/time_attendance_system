<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログインページ</title>
</head>
<body>
    <form action = "<%= request.getContextPath()%>/login" method = "post">
    ユーザーコード<input type="text" name = "code"><br>
    パスワード<input type="text" name = "password"><br>
    <input type="submit" value="送信">
    </form>

    <p><a href="<%=request.getContextPath() %>/new">新規ユーザー登録</a></p>
</body>
</html>