package com.grailsinaction

class StatsService {
    static transactional = false

    //Injects redisService for Redis integration
    def redisService

    @grails.events.Listener
    def onNewPost(Post newPost) {
        String dateToday = new Date().format("yy-MM-dd")

        //Works out daily unique Redis key for caching
        String redisTotalsKey = "daily.stat.totalPosts.${dateToday}"
        log.debug("New Post from: ${newPost.user.loginId}")

        //Increments post count for today
        redisService.incr(redisTotalsKey)

        //Works out daily unique Redis key for caching daily sorted set
        String redisTotalByUserKey = "daily.stat.totalsByUser.${dateToday}"

        //Increments post count for user's daily tally
        redisService.zincrby(redisTotalByUserKey, 1, newPost.user.loginId)

        //Fetches current daily post count for user
        int usersPostsToday = redisService.zscore(redisTotalByUserKey, newPost.user.loginId)
        log.debug("Incremented daily stat for ${newPost.user.loginId} to ${usersPostsToday}")

        //Logs out current totals
        log.debug("Total Posts at ${redisService.get(redisTotalsKey)}")
    }

    def getTodaysTopPosters(){
        String dateToday = new Date().format("yy-MM-dd")
        String redisTotalsByUserKey = "daily.stat.totalsByUser.${dateToday}"

        //Fetches ordered list of top posters highest to lowest
        def tuples = redisService.zrevrangeWithScores(redisTotalsByUserKey, 0, 1000)

        tuples.each{ tuple->
            log.debug("Posts for ${tuple.element} -> ${tuple.score}")
        }
        return tuples
    }
}
