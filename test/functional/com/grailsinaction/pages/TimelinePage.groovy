package com.grailsinaction.pages
import geb.Page

//All page objects must extend geb.Page
class TimelinePage extends Page {

    //Specifies the URL for this page relative to the application's base URL
    static url = "users"

    //Defines the page model, usually via the $() function.
    static content = {

        whatHeading { $("#newPost h3") }

        newPostContent { $("#postContent") }

        submitPostButton { $("#newPost").find("input", type: "button") }

        posts { content ->
            if (content) $("div.postText", text: content).parent()
            else $("div.postEntry")
        }
    }

    //Specifies the conditions that an HTML page must satisfy to be interpreted as this page.
    static at = {
        title.startsWith("Hubbub » Timeline for")
        $("#allPosts")
    }
}