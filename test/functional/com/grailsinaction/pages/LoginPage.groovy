package com.grailsinaction.pages

/**
 * Created by Marcos on 20/01/2016.
 */
class LoginPage extends geb.Page {
    static url = "login/form"

    static content = {
        loginIdField  {$("input[name='j_username']")}
        passwordField {$("input[name='j_password']")}
        signInButton {$("input[type='submit']")}
    }

    static at = {
        title.contains("Sign into Hubbub")
    }
}
