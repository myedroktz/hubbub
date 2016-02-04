package com.grailsinaction

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PostRestController)
@Mock([User,Post])
class PostRestControllerSpec extends Specification {

    def setup() {
        /*Ensures springSecurityService bean is injected into User domain instances */
        defineBeans {
            springSecurityService(SpringSecurityService)
        }
    }

    void "GET a list of post as JSON"() {
        given: "A set of posts"
        initialiseUserAndPosts()

        when: "I invoke the index action"
        //response.format = "json"
        controller.index()

        /*
        Checks JSON response, where json property is map that replicates
        structure of JSON data
         */
        then: "I get the expected posts as JSON list"
        response.json*.content.sort() == [
                "A first post",
                "A second post",
                "Preparing for battle",
                "Soaking up the sun" ]

    }

    void "GET  a list of posts as XML"(){
            given: "A set of posts"
            initialiseUserAndPosts()

            /*Requests XML response (works withFormat() method)*/
            when: "I invoke the show action without an ID and requesting XML"
            response.format = "xml"
            controller.index()

            /*Checks XML response using GPath syntax*/
            then: "I get the expected posts as an XML document"
            response.xml.post.content*.text().sort() ==["A first post",
                                                         "A second post",
                                                         "Preparing for battle",
                                                         "Soaking up the sun"]
    }

    void "POST a single post as JSON"(){
        given: "A set of existing posts"
        def userId = initialiseUserAndPosts()

        /*Passes JSON string as request content*/
        when: "I invoke the save action with a JSON packet"
        def myPost= new Post(content: "A new post!", user: User.findById(userId) )
        if (myPost.hasErrors()){
            throw new Error("El post tiene errores")
        }

        request.json = '{"content":"A new post!","user":{"id":'+userId+'}}'
        controller.save()

        /*Tests HTTP status code of response; 201 indicates successful POST*/
        then: "I get a 201 JSON response with the ID of the new post"
        response.status == 201
        response.json.id != null
    }

    private initialiseUserAndPosts(){
        def chuck = new User(loginId: "chuck_norris", passwordHash: "password")
        chuck.addToPosts(content: "A first post")
        chuck.addToPosts(content: "A second post")
        chuck.save(failOnError: true)

        def bruce = new User(loginId: "bruce_lee", passwordHash: "iknowkungfu")
        bruce.addToPosts(content: "Soaking up the sun")
        bruce.addToPosts(content: "Preparing for battle")
        bruce.save(failOnError: true, flush: true)

        return chuck.id
    }


}
