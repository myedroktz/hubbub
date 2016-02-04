package com.grailsinaction

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PostController)
@Mock([User,Post])
class PostControllerSpec extends Specification {

    def mockSecurityService

    def setup() {
        mockSecurityService = Mock(SpringSecurityService)
        mockSecurityService.encodePassword(_ as String) >> "kjsdfhkshfalhlkdshflas"
    }

    def cleanup() {
    }

    def "Get a users timeline given their id"() {
        given: "A user with posts in the db"
        User chuck = new User(loginId: "chuck_norris")
        chuck.passwordHash = "ksadhfkasjdfh"
        chuck.addToPosts(new Post(content: "A first post"))
        chuck.addToPosts(new Post(content: "A second Post"))
        chuck.save(failOnError: true) //save() method now available on object @Mock

        and: "A loginId parameter"
        params.id = chuck.loginId   //params property introduced by @TestFor

        when: "the timeline is invoked"
        def model = controller.timeline() //Controller property introduced by @TestFor

        then: "the user is in the returned model"
        model.user.loginId == "chuck_norris"
        model.user.posts.size() == 2
    }

    def "Check that non-existent users are handled with an error"() {
        given: "the id of a non-existent user"
        params.id = "this-user-id-does-not-exist"

        when: "the timeline is invoked"
        controller.timeline()

        then: "a 404 is sent to the browser"
        response.status == 404
    }

    /*
    def "Adding a valid new post to the timeline"(){
        given: "A user with posts in the db"
        User chuck = new User(
                loginId: "chuck_norris",
                password: "password").save(failOnError: true)

        and: "A loginId parameter"
        params.id=chuck.loginId

        and: "Some content for the post"
        params.content  = "Chuck Norris can unit test entire applications with a single assert"

        when: "addPost is invoked"
        def model= controller.addPost(params.id, params.content)

        then: "our flash message and redirect confirms the success"
        flash.message == "Successfully created Post"
        response.redirectedUrl == "/post/timeline/${chuck.loginId}"
        Post.countByUser(chuck) == 1
    }
    */
    @spock.lang.Unroll // Unroll is used to allow where clause
    def "Testing id of #suppliedId redirects to #expectedUrl"(){
        given:
        params.id=suppliedId

        when: "Controller is invoked"
        controller.home()

        then:
        response.redirectedUrl == expectedUrl

        where:
        suppliedId  |   expectedUrl
        'joe_cool'  |   '/users/joe_cool'
        null        |   '/users/chuck_norris'
    }

    def "Adding a valid new post to the timeline NEW"() {

        given: "a mock post and security services"
        /*lets you create an object that sits in for the real target object,
         while letting you test the number of times it’s invoked (classic mocking),
         and even control what values get returned when it’s
         invoked (typically called stubbing rather than mocking)*/
        def mockPostService = Mock(PostService)

        /*
         That 1 * prefix tells Spock that this mock object should be called
         only once from your code under test, so if it gets called 0 times, or
         more than once, Spock should throw an error

         createPost(_, _) Those are Spock placeholders. They mean “whenever
         createPost() is called with two arguments of any kind, return a new Post.”
         */
        1 * mockPostService.createPost(_,_) >> new Post(content: "Mock Post")
        controller.postService = mockPostService


        def securityService = Mock(SpringSecurityService)
        _ * securityService.getCurrentUser() >> new User(loginId: "joe_cool")
        controller.springSecurityService = securityService


        when: "controller is invoked"
        def result = controller.addPost("Mock Post")

        then: "redirected to timeline, flash message tells us all is well"
        flash.message ==~ /Added new post: Mock.*/
        response.redirectedUrl == '/users/joe_cool'
    }

    def "Adding an invalid new post to the timeline"() {
        given: "mock post and security services"
        def errorMsg = "Invalid or empty post"
        def mockPostService = Mock(PostService)
        1 * mockPostService.createPost(_, _) >> {
            throw new PostException(message: errorMsg)
        }
        controller.postService = mockPostService

        def securityService = Mock(SpringSecurityService)
        _ * securityService.getCurrentUser() >> new User(loginId: "chuck_norris")
        controller.springSecurityService = securityService

        when: "addPost is invoked"
        def model = controller.addPost(null)

        then: "our flash message and redirect confirms the failure"
        flash.message == errorMsg
        response.redirectedUrl == "/users/chuck_norris"

        // Without the custom URL mapping, the check would be this:
//        response.redirectedUrl == "/post/timeline/${chuck.loginId}"
    }

}
