<%@page contentType="text/html;charset=UTF-8" language="java"%>

<%@page import ="java.util.*"%>
<%@page import ="com.SearchEnginePackage.SearchResult"%>
<%@page import ="com.SearchEnginePackage.Search"%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <title>ResultQuery</title>
</head>
<body>
<h1>Search Engine</h1>
    <form action="Search">
        <input type="text" name="keyword" />
        <button type="submit">Search</button>
    </form>
    <form action="History">
        <button type="submit">History</button>
    </form>

<table border="2" class="resultTable">
    <tr>
        <th>Title</th>
        <th>Links</th>
    </tr>
    <%
        ArrayList<SearchResult> results = (ArrayList<SearchResult>)request.getAttribute("results");
        for(SearchResult result : results){
    %>
    <tr>
        <td><%out.println(result.getTitle());%></td>
        <td><a href = "<%out.println(result.getLink());%>"><% out.println(result.getLink()); %></a></td>
    </tr>
        <%
            }
        %>
</table>
</body>
</html>