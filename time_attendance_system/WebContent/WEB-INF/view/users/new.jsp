<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%String error = (String)request.getAttribute("error"); %>
<%String top = ""; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規ユーザー登録</title>
</head>
<body>
<form action = "<%= request.getContextPath()%>/new" method = "post">
    ユーザーコード<input type="text" name = "code"><br>
    名前<input type="text" name = "name"><br>
    パスワード<input type="text" name = "password"><br>
    <input type="submit" value="送信">
    </form>

    <%if (error != null){ %>
    <%= error %>
    <%}else%>
    <%= top%>
</body>
</html>