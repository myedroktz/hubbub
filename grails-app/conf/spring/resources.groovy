import com.grailsinaction.MarshallerRegistrar
//import com.grailsinaction.mail.*
//Imports broker factories
import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.jms.connection.SingleConnectionFactory

// Place your Spring DSL code here
beans = {
    jmsConnectionFactory(SingleConnectionFactory){
        //Defines broker connection
        targetConnectionFactory = {ActiveMQConnectionFactory cf ->
            //Configures broker endpoint
            brokerURL = "tcp://localhost:61616"
        }
    }

/*  DECLARES REGISTRAR AS SPRING BEAN
    This short bit of code guarantees that all your custom marshalers will be registered
    when the application starts up. At least it should. Check issue GRAILS-111163 to see
    whether your version of Grails supports this.

    NOT WORKING FOR 2.3.7
*/
    //hubbubMarshallerRegistrar(MarshallerRegistrar)


/*    if (System.getProperty("cloud.deployed")) {

        mailClient(HttpMailClient) {
            emailServiceUrl = "http://my.server/mail"
        }

    *//*    Your own mailService bean overrides the mailService bean provided by the Mail
        plugin. Now, any time your application calls the sendMail() method, the email is sent
        by your dummy HTTP mail service.*//*
        mailService(HttpMailService) {
            client = ref("mailClient")
        }
    }*/

}
