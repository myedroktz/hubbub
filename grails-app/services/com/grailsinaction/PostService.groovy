package com.grailsinaction

import grails.transaction.Transactional

class PostException extends RuntimeException{ //Forces transaction to roll back if exception occur
    String message
    Post post
}

@Transactional //Rolls back database changes if errors occurs
class PostService {

    Post createPost(String loginId, String content){
        def user = User.findByLoginId(loginId)

        if (user){
            def post = new Post(content: content)
            user.addToPosts(post)

            //Validates Post object during save
            if(!post.validate() || !user.save(flush: true)){
                throw new PostException(
                        message: "Invalid or empty post", post: post)

            }
            //Finds "@..." in post
            def m = content =~ /@(\w+)/
            if(m){
                def targetUser = User.findByLoginId(m[0][1])
                if(targetUser){
                    new Reply(post: post, inReplyTo: targetUser).save()
                    event 'onNewPost', post //Raises new event
                    return post
                }
                else{
                    throw new PostException(
                        message: "Reply-to user not found", post: post)
                }
            }
        }

        throw new PostException(message: "Invalid User Id")

    }
}
