package com.grailsinaction



class DailyDigestJob {
    def dailyDigestService

    static triggers = {
      /*
        simple startDelay: 60 * 1000, // Initial wait period before the plugin invokes your job
        repeatInterval: 24 * 60 * 60 * 1000 // Runs job once per day
      */
        //Supplies cron-style expression to Quartz job
        //Run every weekday at 1 a.m.
        cron cronExpression: "0 0 1 ? * MON-FRI"

    }

    def execute() {
        // execute job
        log.debug("Starting the Daily Digest job")
        dailyDigestService.sendDailyDigests()
        log.debug("Finished the Daily Digest job")
    }
}
