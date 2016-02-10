package com.grailsinaction
import org.springframework.security.authentication.event.AuthenticationSuccessEvent


class AuditService {
    static transactional = false //Ensures no participation in existing transaction

    //Matches method name and type to raised event topic and content
    @grails.events.Listener
    def onNewPost(Post newPost) {
        log.error "New Post from: ${newPost.user.loginId} : ${newPost.shortContent}"
    }

    @grails.events.Listener(namespace = "security")
    def onUserLogin(AuthenticationSuccessEvent loginEvent){
        log.error "Ẅe appeared to have logged in a user: ${loginEvent.authentication.principal.username}"
    }
}
