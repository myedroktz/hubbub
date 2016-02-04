package com.grailsinaction

class Profile {
//    User user // Declares Profile is attached to a User object
    byte[] photo // Models binary data in a byte[]
    String fullName
    String bio
    String homepage
    String email
    String timezone
    String country
    String jabberAddress
    String skin
    //String twitter_name //new field

    static belongsTo = [user : User]
    static constraints = {
        fullName blank: false
        bio nullable: true, maxSize: 1000
        homepage url: true, nullable: true
        email email: true, blank: false
        photo nullable: true, maxSize: 2 * 1024 * 1024 //Photo can be up to 2MB in file size
        country nullable: true
        timezone nullable: true
        jabberAddress email: true, nullable: true
        skin(nullable: true, blank: true, inList: ['blues', 'nighttime'])
        //twitter_name nullable: true
    }

    //String toString() { return "Profile of $fullName (id: $id)" }
    String getDisplayString() { return fullName }
}
