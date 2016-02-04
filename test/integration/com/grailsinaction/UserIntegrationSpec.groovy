package com.grailsinaction



import spock.lang.*

/**
 *
 */
class UserIntegrationSpec extends Specification {


    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    def "Saving our first user to the database"(){
        given: "A brand new user"
        def joe = new User(loginId: 'joe', passwordHash: 'secret')
        // def joe = new User(loginId: 'joe', password: 'secret', homepage: 'http://www.grailsinaction.com')

        when: "the user is saved"
        joe.save() //Calls save to persist object

        then:"it saved successfully and can be found in the database"
        joe.errors.errorCount == 0 // Ensures save() was error free
        joe.id != null // Confirms save() set database ID
        User.get(joe.id).loginId==joe.loginId // Retrieves User object by ID. Use read to get a read-only copy of the object
    }


    def "Updating a saved user changes its properties"(){
        given: "An existing user"
        def existingUser = new User(loginId:'joe', passwordHash: 'secret')
        // def existingUser = new User(loginId:'joe', password: 'secret', homepage: 'http://www.grailsinaction.com')
        existingUser.save(failOnError: true)//Grails will throw an exception if the object fails any validation tests

        when: "A property is changed"
        def foundUser = User.get(existingUser.id)
        foundUser.loginId='jane' //Modifies retrieved User object directly
        foundUser.save(failOnError: true) //Updates database

        then: "The change is reflected in the database"
        User.get(existingUser.id).loginId=='jane' //Check that password has been persisted
    }

    def "Deleting an existing user removes it from the database"(){
        given: "An existing user"
        def user = new User(loginId: 'joe', passwordHash: 'secret')
        // def user = new User(loginId: 'joe', password: 'secret', homepage: 'http://www.grailsinaction.com')
        user.save(failOnError: true)

        when: "The user is deleted"
        def foundUser = User.get(user.id)
        foundUser.delete(flush: true) //Removes the user immediately

        then: "The user is removed from the database"
        !User.exists(foundUser.id) //Checks for object ID in database

    }

    def "saving a user with invalid properties causes an error"(){
        given: "A user which fails several field validations"
        def user = new User(loginId:'me', passwordHash: 'tiny')
        //def user = new User(loginId:'joe', password: 'tiny', homepage: 'not-a-url')

        when: "The user is validated"
        user.validate()

        then:
        user.hasErrors()

        //"size.toosmall" == user.errors.getFieldError("password").code
        //"tiny" == user.errors.getFieldError("password").rejectedValue
        //"url.invalid" == user.errors.getFieldError("homepage").code
        //"not-a-url" == user.errors.getFieldError("homepage").rejectedValue
        user.errors.getFieldError("loginId")

    }

    def "Recovering from a failed save by fixing invalid properties"(){
        given: "A user that has invalid properties"
        def chuck = new User(loginId: 'me', passwordHash: 'tiny')
        assert chuck.save() == null
        assert chuck.hasErrors()

        when: "We fix the invalid properties"
        chuck.loginId="joe"
        chuck.validate()

        then: "The user saves and validates fine"
        !chuck.hasErrors()
        chuck.save()
    }

    def "Ensure a user can follow other users"() {
        given: "A set of baseline users"
        def joe = new User(loginId: 'joe', passwordHash: 'password').save()
        def jane = new User(loginId: 'jane', passwordHash: 'password').save()
        def jill = new User(loginId: 'jill', passwordHash: 'password').save()

        when: "Joe follows Jane & Jill, and Jill follows Jane"
        joe.addToFollowing(jane)
        joe.addToFollowing(jill)
        jill.addToFollowing(jane)

        then: "Follower counts should match following people"
        2 == joe.following.size()
        1 == jill.following.size()
    }

    //Injects Dumbster Spring bean into test
    def dumbster

    def "Welcome email is generated and sent"() {
        given: "An empty inbox"
        dumbster.reset() //Clears existing mockc messages from Dumbster

        and: "a user controller"
        def userController = new UserController()

        when: "A welcome email is sent"
        userController.welcomeEmail("marcosyedro@gmail.com") //Sends email via UserController action

        then: "It appears in their inbox"
        dumbster.messageCount == 1 //Confirms inbox count rises to 1

        //Fetches first message and checks its details
        def msg = dumbster.getMessages().first()
        msg.subject == "Welcome to Hubbub!"
        msg.to == "marcosyedro@gmail.com"
        msg.body =~ /The Hubbub Team/
    }
}
