package com.grailsinaction

class UserController {
    static scaffold = true
    static navigation = [
            [group:'tabs', action:'search', order: 90],
            [action: 'advSearch', title: 'Advanced Search', order: 95],
            [action: 'register', order: 99, isVisible: { true }]
    ]

    def mailService
    def springSecurityService

    def search(){} //The action for the “search” form page

    def results(String loginId) { //Argument name matches name of the text field in the form
        //Queries the DB for all users with a loginId that’s like the
        def users = User.where {
            //Multiple expression on separate lines are implicitly ANDed together
            loginId =~ "%${loginId}%"

        }.list()

        return [ users: users,
                 term: params.loginId,
                 totalUsers: User.count() ]
    }

    def advSearch() {}

    def advResults() {
        def profileProps = Profile.metaClass.properties*.name //Works out what properties Profile has
        def profiles = Profile.withCriteria {
            "${params.queryType}" { //Applies the conjunction from the queryType form field: and, or, or not

                params.each { field, value ->

                    if (profileProps.grep(field) && value) {
                        ilike(field, value) //Adds an ilike criterion for each submitted field, as long as it matches a property on Profile
                    }
                }

            }

        }
        [ profiles : profiles ]

    }

    def register() {
        if (request.method == "POST") {
            def user = new User(params)
            user.passwordHash = springSecurityService.encodePassword(params.password)
            if (user.validate()) {
                user.save()
                flash.message = "Successfully Created User"
                redirect(uri: '/')
            } else {
                flash.message = "Error Registering User"
                return [ user: user ]
            }
        }
    }

    def register2(UserRegistrationCommand urc){ //Binds data from params to command object
        if(urc.hasErrors()){//Uses hasErrors to check validation
            render view: "register", model:[user : urc]
        } else{
            def user = new User(urc.properties) //Binds data to new user object
            user.passwordHash = springSecurityService.encodePassword(urc.password)
            user.profile = new Profile(urc.properties)
            if(user.validate() && user.save()){
                flash.message = "Welcome aboard, ${urc.fullName ?: urc.loginId}"
                redirect(uri: '/')
            }else{
                //maybe not unique loginId?
                return [user : urc]
            }
        }
    }

    def profile(String id){
        def user = User.findByLoginId(id)

        if(!user){
            response.sendError(404)
        }else{
             if(!user.profile){
                 response.sendError(404)
             }else{
                 def profile = user.profile
                 return [profile : profile]
             }
        }
    }

    /*def profile(String id) {
        def user = User.findByLoginId(id)
        if (user) {
            return [profile: user.profile]
        } else {
            response.sendError(404)
        }
    }*/

    /*def welcomeEmail(){
        if(params.email){
            mailService.sendMail {
                to params.email
                subject "Welcome to Hubbub!"
                text """
                Hi, ${params.email}. Great to have you on board.
                The Hubbub Team.
                """
            }
            flash.message = "Welcome aboard"
        }
        redirect(uri: "/")
    }*/

    def welcomeEmail(String email) {
        if (email) {
            mailService.sendMail {
                to email
                subject "Welcome to Hubbub!"
                html view: "/user/welcomeEmail", model: [ email: email ]
            }
            flash.message = "Welcome aboard"
        }
        redirect(uri: "/")
    }
}

class UserRegistrationCommand{
    String loginId
    String password
    String passwordRepeat
    byte[] photo
    String fullName
    String bio
    String homepage
    String email
    String timezone
    String country
    String jabberAddress

    static constraints = {
        importFrom Profile //Reuses the same business constraints as our domain class
        importFrom User //Reuses the same business constraints as our domain class

        password(size: 6..8, blank: false,
                validator: { passwd, urc ->
                    return passwd != urc.loginId
                })
        passwordRepeat(nullable: false,
                validator: { passwd2, urc ->
                    return passwd2 == urc.password
                })
    }
}