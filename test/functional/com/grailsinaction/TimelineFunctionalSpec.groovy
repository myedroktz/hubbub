package com.grailsinaction
import geb.spock.GebReportingSpec

class TimelineFunctionalSpec extends GebReportingSpec {

    def "Check that timeline loads for user 'phil'"() {
        when: "we load phil's timeline"
        login "frankie", "testing"
        go "users/phil"

        then: "the page displays Phil's full name"
        $("#newPost h3").text() == "What is Phil Potts hacking on right now?"
    }

    def "Submitting a new post"(){
        given: "I log in and start at my timeline page" //Needs to log in before posting
        login "frankie", "testing"
        go "timeline"

        when: "I enter a new message and post it"
        //Sets the content of the text area (with ID postContent)
        $("#postContent").value("This is a test post from Geb")

        //Simulates pressing the Post button
        $("#newPost").find("input", type: "button").click()

        then: "I see the new post in the timeline"
        waitFor {!$("div.postText", text: "This is a test post from Geb").empty}

    }

    //Factors out common interactions into methods
    private login(String username, String password){
        go "login/form"
        $("input[name='j_username']").value(username)
        $("input[name='j_password']").value(password)
        $("input[type='submit']").click()
    }

}