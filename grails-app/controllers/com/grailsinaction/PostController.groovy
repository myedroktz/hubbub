package com.grailsinaction


class PostController {
    static scaffold = true
    static defaultAction = "home"

    //Injects PostService instance into the controller
    def postService
    def springSecurityService

    static navigation = [
            [group:'tabs', action: 'personal', title: 'My Timeline', order: 0],
            [action: 'global', title: 'Global Timeline', order: 1]
    ]

    def home(){
        if(!params.id){
            params.id = "chuck_norris"
        }
        //Passes params when redirecting
        redirect(action: 'timeline', params: params)
    }

    def timeline(String id){
        def user = User.findByLoginId(id) //Retrieves user based on id parameter
        if(!user){
            response.sendError(404) // Sends error code for nonexistent user
        }else{
            [user : user] //Passes matched user to view. Last line is considered the 'return'
        }
    }

    def global() {
        def user = springSecurityService.currentUser //Fetches current user from Spring Security
        [ posts : Post.list(params), postCount : Post.count(), user : user ]
    }

    def addPost(String content){
        def user = springSecurityService.currentUser
        try {
            def newPost = postService.createPost(user.loginId, content)
            flash.message = "Added new post: ${newPost.content}"
        } catch(PostException pe) {
            flash.message = pe.message
        }
        redirect(action: "timeline", id: user.loginId)
    }

    def personal(){
        def user = springSecurityService.currentUser //Fetches current user from Spring Security
        render view: "timeline", model: [ user : user ]
    }

    def addPostAjax(String content) {
        def user = springSecurityService.currentUser
        try {
            def newPost = postService.createPost(user.loginId, content)
            def recentPosts = Post.findAllByUser(user,[sort: 'dateCreated', order: 'desc', max: 20])

            //Renders postEntry template for each query result
            render template: 'postEntry', collection: recentPosts, var: 'post'

        } catch (PostException pe) {
            render {
                div(class:"errors", pe.message)
            }
        }
    }

    def tinyUrl(String fullUrl) {
        def origUrl = fullUrl?.encodeAsURL()
        def tinyUrl =
                new URL("http://tinyurl.com/api-create.php?url=${origUrl}").text
        render(contentType:"application/json") {
            urls(small: tinyUrl, full:fullUrl)
        }
    }

    def singlepage() {
        def user = params.id ? User.findByLoginId(params.id) : springSecurityService.currentUser
        if (!user) {
            response.sendError(404)
        } else {
            [ user : user ]
        }
    }


}
