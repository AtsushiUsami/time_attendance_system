<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String error = (String)request.getAttribute("error"); %>
<%String top = ""; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>トップページ</title>
</head>
<body>
    <form action = "<%= request.getContextPath()%>/beginrecord" method = "post">
    <input type="submit" value="出勤">
    </form>

    <form action = "<%= request.getContextPath()%>/finishrecord" method = "post">
    <input type="submit" value="退勤">
    </form>

    <form action = "<%= request.getContextPath()%>/brest" method = "post">
    <input type="submit" value="休憩開始">
    </form>

    <form action = "<%= request.getContextPath()%>/frest" method = "post">
    <input type="submit" value="休憩終了">
    </form>

    <form action = "<%= request.getContextPath()%>/logout" method = "get">
    <input type="submit" value="ログアウト">
    </form>

    <%if (error != null){ %>
    <%= error %>
    <%}else%>
    <%= top%>

     <p><a href="<%=request.getContextPath() %>/timeindex">今月の出勤記録</a></p>
</body>
</html>