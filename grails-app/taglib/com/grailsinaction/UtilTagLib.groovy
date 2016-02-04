package com.grailsinaction

import org.springframework.web.util.HtmlUtils

class UtilTagLib {
    //static defaultEncodeAs = 'html'
    //static encodeAsForTags = [tagName: 'raw']
    static namespace = "hub"

    def certainBrowser = { attrs, body ->
        //Checks that User-Agent header matches tag attribute
        if (request.getHeader('User-Agent') =~ attrs.userAgent ) {
            out << body() //Displays any content that was inside original tag
        }
    }

    def eachFollower = { attrs, body ->
        def followers = attrs.followers
        followers?.each { follower ->
            body(followUser: follower)
        }

    }

    def tinyThumbnail = { attrs ->
        def loginId = attrs.loginId
        out << "<img src='"
        out << g.createLink(action: "tiny", controller: "image", id: loginId)
        out << "' alt='${loginId}'"
        out << "/>"
    }

}
