package com.grailsinaction

class User {

    transient springSecurityService

    String loginId
    String passwordHash
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    Date dateCreated //Special field


    static hasOne = [profile : Profile] //Declares Profile part of User
    static hasMany = [posts : Post, tags : Tag, following : User]
    static transients = ['springSecurityService']

    static constraints = {
        loginId size: 3..20, unique: true, blank: false
        tags()
        posts()
        profile nullable: true
    }

    static mapping = {
        //This form of the mapping tells Grails that you want to sort by dateCreated
        // when accessing the posts collection via a user.
        posts sort:'dateCreated', order: "desc"
    }

    static searchable = {
        /*
            Two common operations on the searchable DSL are except (index all fields except
            these) and only (only index these).
        */
        except = ['password'] // Makes passwords never get indexed
    }

    String getDisplayString() { return loginId }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

   /*
   def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }
    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
    */
}
