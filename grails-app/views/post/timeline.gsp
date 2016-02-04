<%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 12/01/2016
  Time: 11:09
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title> Timeline for ${ user.profile ? user.profile.fullName : user.loginId } </title>
        <meta name="layout" content="main"/>
        <g:javascript library="jquery"/>

        <g:if test="${user.profile?.skin}">
            <g:external dir="css" file="${user.profile.skin}.css"/>
        </g:if>
    </head>

    <body>
        <h1>Timeline for ${ user.profile ? user.profile.fullName : user.loginId }</h1>

        <g:if test="${flash.message}">
            <div class="flash">
                ${flash.message}
            </div>
        </g:if>

        <hub:certainBrowser userAgent="Chrome">
            <p>Best viewed in Internet Explorer. Just kidding, you hardcore Linux
            user! Lynx rocks! </p>
        </hub:certainBrowser>

        <g:render template="newPost" model="[user : user]"/>

        <br/>

        <div id="allPosts">
            <g:render template="postEntry" collection="${user.posts}" var="post"/>
        </div>

    </body>
</html>