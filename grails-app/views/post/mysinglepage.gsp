<%--
  Created by IntelliJ IDEA.
  User: myedro
  Date: 01/02/16
  Time: 16:34
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Timeline for ${user.profile ? user.profile.fullName : user.loginId}</title>
        <meta name="layout" content="main"/>

        %{--Defines the value of the htmlAttrs element for merging--}%
        <content tag="htmlAttrs">ng-app=Hubbub</content>

        <r:require module="core"/> %{--Pulls in supporting JavaScript modules--}%
    </head>

    <body>

        <form ng-controller="NewPostCtrl">
            <textarea id='postContent' ng-model="postContent"></textarea>
            <button ng-click="newPost()">Post</button>
        </form>


        %{--Links this block to Angular controller object--}%
        <div ng-controller="PostsCtrl">
            <div id="newPost">
                <h3>
                    What is ${ user.profile ? user.profile.fullName : user.loginId } hacking on right now?
                </h3>
            </div>

            %{--Prevents browser from displaying unstyled markup--}%
            <div class="allPost" ng-cloak>
                %{--Iterates over allPosts list of this controller--}%
                <div class="PostEntry" ng-repeat="post in allPosts">

                    %{--Renders attributes of each post to browser--}%
                    <div class="postText">
                        {{post.message}}
                    </div>

                    <div class="postDate">
                        {{post.published}} by {{post.user}}
                    </div>

                </div>
            </div>
        </div>
    </body>

</html>