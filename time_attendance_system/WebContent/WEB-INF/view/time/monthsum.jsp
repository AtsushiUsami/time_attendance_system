<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List" %>
<%ArrayList<String> beginlist = (ArrayList<String>)request.getAttribute("beginlist");%>
<%ArrayList<String> finishlist = (ArrayList<String>)request.getAttribute("finishlist");%>
<%ArrayList<String> brestlist = (ArrayList<String>)request.getAttribute("brestlist");%>
<%ArrayList<String> frestlist = (ArrayList<String>)request.getAttribute("frestlist");%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/timeindex.css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>今月の勤怠状況</title>
</head>
<body>
<div class = timelist>

<div><table class = timetable>
<th>出勤時間</th>
<% for(int i=0; i<beginlist.size(); i++){ %>
<tr><td><%= beginlist.get(i) %></td></tr>
<%} %>
</table></div>

<div><table class = timetable>
<th>退勤時間</th>
<% for(int i=0; i<finishlist.size(); i++){ %>
<tr><td><%= finishlist.get(i) %></td></tr>
<%} %>
</table></div>

<div><table class = timetable>
<th>休憩開始時間</th>
<% for(int i=0; i<brestlist.size(); i++){ %>
<tr><td><%= brestlist.get(i) %></td></tr>
<%} %>
</table></div>

<div><table class = timetable>
<th>休憩終了時間</th>
<% for(int i=0; i<frestlist.size(); i++){ %>
<tr><td><%= frestlist.get(i) %></td></tr>
<%} %>
</table></div>
</div>

<form action = "<%= request.getContextPath()%>/sum" method = "GET">
    時給<input type="text" name = "wage">
    <input type="submit" value="合計勤務時間と現在の給料">
    </form>


</body>
</html>