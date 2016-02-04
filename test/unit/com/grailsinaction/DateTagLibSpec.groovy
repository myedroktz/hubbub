package com.grailsinaction

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll


/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(DateTagLib)
class DateTagLibSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }
    @Unroll
    void "Conversion of #testName matches #expectedNiceDate"() {
        expect:
        applyTemplate('<hub:dateFromNow date = "${date}" />', [date: testDate]) == expectedNiceDate

        where:
        testName                        |   testDate                            |   expectedNiceDate
        "Current Time"                  |   new Date()                          |   "Right now"
        "Now - 1 day"                   |   new Date().minus(1)                 |   "1 day ago"
        "Now - 2 day"                   |   new Date().minus(2)                 |   "2 days ago"
        //"Now - 2 day - 5 minutes"       |   new Date().minus(2) - 5.minute     |   "1 day 23 hours 55 minutes ago"
    }
}
