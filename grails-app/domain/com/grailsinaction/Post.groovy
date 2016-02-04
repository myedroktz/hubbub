package com.grailsinaction

import grails.rest.Resource

//@Resource(uri="/posts")//Adds JSON and XML REST endpoints at theURL /hubbub/posts
class Post {
    String content
    Date dateCreated

    static constbraints = {
        content blank: false
    }
    static belongsTo = [user : User] // Points to the owning object
    static hasMany = [tags : Tag] //Models a Post with many Tags
    static mapping = {
        sort dateCreated:"desc" //Specifies sort order for Post
    }

    static searchable = {
        //content: spellCheck "include"
        user(component:true)
    }

    String getDisplayString() { return content }
}
