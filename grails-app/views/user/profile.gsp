<%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 14/01/2016
  Time: 9:29
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Profile for ${profile.fullName}</title>
        <meta name="layout" content="main"/>

        <style>
        .profilePic {
            border: 1px dotted gray;
            background: lightyellow;
            padding: 1em;
            font-size: 1em;
        }
        </style>
    </head>
    <body>
        <div class="profilePic">
            <g:if test="${profile.photo}">
                <img src="${createLink(controller: 'image', action: 'renderImage', id: profile.user.loginId)}"/>
            </g:if>
            <p>Profile for <strong>${profile.fullName}</strong></p>
            <p>Bio: ${profile.bio}</p>
        </div>
        <div>
            <g:each var="followUser" in="${profile.user.following}">
                <hub:tinyThumbnail loginId="${followUser.loginId}"/>
            </g:each>
        </div>

    </body>
</html>