<%--
  Created by IntelliJ IDEA.
  User: Marcos
  Date: 26/01/2016
  Time: 08:46 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Sign into Hubbub</title>
        <meta name="layout" content="main">
        <g:external dir="css" file="twitter-auth.css"/>
    </head>
    <body>
    <h1>Sign in</h1>

    %{--Submits user credentials to /j_spring_security_check URI. No other URI works.--}%
    <g:form uri="/j_spring_security_check" method="POST">

        <fieldset class="form">
            %{--The username field must be called j_username.--}%
            <div class="fieldcontain required">
                <label for="j_username">Login ID</label>
                <g:textField name="j_username" value="${loginId}"/>
            </div>

            %{--The password field must be called j_password--}%
            <div class="fieldcontain required">
                <label for="j_password">Password</label>
                <g:passwordField name="j_password"/>
            </div>

            <div class="fieldcontain required">
                <label for="_spring_security_remember_me">Remember me</label>
                <g:checkBox name="_spring_security_remember_me"/>
            </div>
        </fieldset>

        <fieldset class="buttons">
            <g:submitButton name="signIn" value="Sign in"/>
            <twitterAuth:button/>
        </fieldset>

    </g:form>

    </body>
</html>