<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method='POST' action='fileUploaderServlet?upload=0&k=10&rf=1'>     
<%@page import="com.imageSim.shared.Image"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<table border = 1>
<% List<Image> results = (List<Image>)request.getAttribute("images");
Image img = (Image)request.getAttribute("imageQuery");
request.getSession().setAttribute("iq",img);
for(int i=0;i<results.size();++i){%>
<tr>
	<td>
		<img src='../images/<%= results.get(i).getFilename() %>' alt='id: <%= String.valueOf(results.get(i).getId()) %> filename: <%= results.get(i).getFilename()%>' width=100 height=100 >
	</td>
	<td>
		<input type='checkbox' name='positiveFeedback' value='<%= String.valueOf(results.get(i).getId()) %>'>
		<input type="hidden" name="imageQuery" value="<%= request.getAttribute("imageQuery") %>" />
	</td>
</tr>
<% }%>
<tr>
	<td collspan=2>
		<input type='submit' value='Refresh'>
	</td>
</tr>
</table>
</form>
</body>
</html>