package com.grailsinaction

import java.text.SimpleDateFormat
import javax.annotation.PostConstruct
/**
 * Created by myedro on 29/01/16.
 */
class MarshallerRegistrar {
    //@PostConstruct: declares that registerMarshallers() will be called immediately after objects's constructor
    @PostConstruct
    void registerMarshallers(){

        def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")

        /* Starts serialization for given Post instance */
        XML.registerObjectMarshaller(Post) { Post p, converter ->
            converter.attribute "id", p.id.toString()
            converter.attribute "published", dateFormatter.format(p.dateCreated)

            /* Uses Groovy markup builder syntax to generate XML*/
            converter.build{
                message p.content
                user p.user.loginId
                tags{
                    for (t in p.tags){
                        tag t.name
                    }
                }
            }
        }

        /*
        And because you aren’t using the converter to generate JSON (you rely on its default
        handling of maps), you don’t need the second argument on the closure for register-
        ObjectMarshaller().
        */
        JSON.registerObjectMarshaller(Post) { Post p ->
            return [ id: p.id,
                     published: dateFormatter.format(p.dateCreated),
                     message: p.content,
                     user: p.user.loginId,
                     tags: p.tags.collect { it.name } ]
        }


    }
}
