<%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 11/01/2016
  Time: 15:58
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Advanced Search Results</title>
        <meta name="layout" content="main"/>
    </head>
    <body>

        <h1>Advanced Results</h1>
        <p>Searched
        for items matching <em>${term}</em>.
        Found <strong>${profiles.size()}</strong> hits.
        </p>
        <ul>
            <g:each var="profile" in="${profiles}">
                <li><g:link controller="profile" action="show" id="${profile.id}">${profile.fullName}</g:link></li>
            </g:each>
        </ul>

        <g:link action='advSearch'>Search Again</g:link>
    </body>
</html>