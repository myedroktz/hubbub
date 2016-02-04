<%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 11/01/2016
  Time: 9:09
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Search Hubbub</title>
        <meta name="layout" content="main"/>
    </head>
    <body>
        <formset>
            <legend>Search for Friends</legend>
            <g:form action="results">
                <label for="loginId">Login ID</label>
                <g:textField name="loginId" />
                <g:submitButton name="search" value="Search"/>
            </g:form>
        </formset>
    </body>
</html>

