package com.grailsinaction



import spock.lang.*

/**
 *
 */
class PostIntegrationSpec extends Specification {

    def searchableService

    def setup() {
        // The Searchable plugin breaks the second test if we don't disable
        // mirroring before it runs.
        searchableService.stopMirroring()
    }

    def "Adding post to user links post to user"() {
        given: "A brand new user"
        def user = new User(loginId: 'joe', passwordHash: 'secret')//Creates User to hold Posts
        user.save(failOnError: true)

        when: "Several posts are added to the user"
        user.addToPosts(new Post(content: "First post... W00t!"))//Persists Post by adding to a User. Don't need to call save
        user.addToPosts(new Post(content: "Second post..."))
        user.addToPosts(new Post(content: "Third post..."))

        then: "The user has a list of post attached"
        User.get(user.id).posts.size() == 3
    }

    def "Ensure post linked to a user can be retrieved"(){
        given: "A user with several posts"
        def user= new User(loginId: 'joe', passwordHash: 'secret')
        user.addToPosts(new Post(content: "First"))
        user.addToPosts(new Post(content:"Second"))
        user.addToPosts(new Post(content:"Third"))
        user.save(failOnError: true)

        when: "The user is retrieved by their id"
        def foundUser = User.get(user.id)
        def sortedPostContent = foundUser.posts.collect{it.content}.sort()//Iterates through User's posts

        then: "The post appear on the retrieved user"
        sortedPostContent == ['First', 'Second', 'Third']
    }

    def "Exercise tagging several posts with various tags"() {

        given: "A user with a set of tags"
        def user = new User(loginId: 'joe', passwordHash: 'secret')
        def tagGroovy = new Tag(name: 'groovy')
        def tagGrails = new Tag(name: 'grails')
        user.addToTags(tagGroovy)
        user.addToTags(tagGrails)
        user.save(failOnError: true)

        when: "The user tags two fresh posts"
        def groovyPost = new Post(content: "A groovy post")
        user.addToPosts(groovyPost)
        groovyPost.addToTags(tagGroovy)
        def bothPost = new Post(content: "A groovy and grails post")
        user.addToPosts(bothPost)
        bothPost.addToTags(tagGroovy)
        bothPost.addToTags(tagGrails)

        then:
        user.tags*.name.sort() == [ 'grails', 'groovy']
        1 == groovyPost.tags.size()
        2 == bothPost.tags.size()
    }
}
