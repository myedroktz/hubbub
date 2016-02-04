<%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 15/01/2016
  Time: 10:18
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>
            Global Timeline
        </title>
        <meta name="layout" content="main"/>

        <g:javascript>
            function clearPost(e) {
                $('#postContent').val('');
            }
            function showSpinner(visible) {
                if (visible) $('#spinner').show();
                else $('#spinner').hide();
            }
            function addTinyUrl(data) {
                var tinyUrl = data.urls.small;
                var postBox = $("#postContent")
                postBox.val(postBox.val() + tinyUrl);
                toggleTinyUrl();
                $("#tinyUrl input[name='fullUrl']").val('');
            }
        </g:javascript>
    </head>

    <body>
        <h1>Global Timeline</h1>

        <g:if test="${flash.message}">
            <div class="flash">
                ${flash.message}
            </div>
        </g:if>

        <g:render template="newPost" model="[user : user]"/>

        <div id="allPosts">
           <g:render template="postEntry" collection="${posts}"/>
        </div>
        <g:paginate action="global" total="${postCount}" max="5" />
    </body>
</html>