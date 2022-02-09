<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%int timesum = (int)request.getAttribute("timesum"); %>
    <%int timetotal = (int)request.getAttribute("timetotal"); %>
    <%double resttotal = (double)request.getAttribute("resttotal"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    今月の勤務時間の合計は<%= timetotal %>時間です。
    今月の休憩時間の合計は<%= resttotal %>時間です。
    今月の給料は<%= timesum %>円です。
    <p><a href="<%=request.getContextPath() %>/timeindex">今月の出勤記録</a></p>

</body>
</html>