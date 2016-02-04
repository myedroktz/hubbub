class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        /* My own permalinks*/
        "/users/$id"{
            controller = "post"
            action = "timeline"
        }

        "/timeline"{
            controller="post"
            action="personal"
        }

        "/login/form"{
            controller= "auth"
            action= "form"
        }

        "/api/posts"(resources: "postRest")


        "/"(view:"/index")
        "500"(view:'/error')


	}
}
