<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link type="text/css" rel="stylesheet" href="ImageSim.css">
<title>Insert title here</title>
</head>
<body>
<form method='POST' action='fileUploaderServlet?upload=0&rf=1' >
<input type="hidden" name="kapa" value="<%= request.getAttribute("kapa") %>" />     
<%@page import="com.imageSim.shared.Image"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<div class="resultTable">
<table border = "0">
<% List<Image> results = (List<Image>)request.getAttribute("images");
Image img = (Image)request.getAttribute("imageQuery");
int COLUMNS = 4;
boolean newRow=true;
request.getSession().setAttribute("iq",img);
for(int i=0;i<results.size();++i){%>
<%if(newRow){ newRow=false;%>
<tr>
<%} %>
	<td>
		<a href='./images/<%= results.get(i).getFilename() %>'> 
			<img src='./images/<%= results.get(i).getFilename() %>' alt='id: <%= String.valueOf(results.get(i).getId()) %> filename: <%= results.get(i).getFilename()%>' width=100 height=100 >
		</a> 
	</td>
	<td>
		<input type='checkbox' name='positiveFeedback' value='<%= String.valueOf(results.get(i).getId()) %>'>
		<input type="hidden" name="imageQuery" value="<%= request.getAttribute("imageQuery") %>" />
	</td>
<%if((i+1)%COLUMNS==0){ newRow=true;%>
</tr>
<%} %>
<% }%>
<tr>
	<td collspan=2>
		
		<input type='submit' value='Refresh' class='gwt-Button'>
	</td>
</tr>
</table>
</div>
</form>
</body>
</html>