package com.grailsinaction


import spock.lang.*

/**
 *
 */
class JabberServiceIntegrationSpec extends Specification {

    def jabberService
    def jmsService

    def jmsOutputQueue = "jabberOutQ"
    static transactional = false

    def "First send to a queue"(){
        given: "Some sample queue data"
        def post = [user: [userId:'chuck_norris'],
                    content: 'is backstroking across the atlantic']
        def jabberIds = ["glen@grailsinaction.com", "peter@grailsinaction.com"]

        expect:
        jabberService.sendMessage(post,jabberIds)
    }

    def "Send message to the jabber queue"(){
        given: "Some sample queue data"
        def post = [user: [userId:'chuck_norris'],
                    content: 'is backstroking across the atlantic']

        def jabberIds = ["glen@grailsinaction.com", "peter@grailsinaction.com"]

        //Browse lets you peek at messages on queue
        def msgListBeforeSend = jmsService.browse(jabberService.sendQueue)

        when:
        jabberService.sendMessage(post,jabberIds)

        then:
        //Confirms message queue is now one message longer
        jmsService.browse(jabberService.sendQueue).size() == msgListBeforeSend.size()+1
    }

}
