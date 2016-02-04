package com.grailsinaction

class SearchController {

    def index() {}

    def search(){
        def query = params.q

        if(!query){
            return [:]
        }
        try{
            /*
                Introduces withHighlighter closure which takes a
                highlighter object (used to hold the word that was highlighted along with its surrounding text),
                an index counter (used to track the hit number),
                and the search result object itself
            */
            params.withHighlighter = {highlighter, index, sr ->
                // lazy-init the list of highlighted search results
                //Provides empty highlights collection if no matches
                if (!sr.highlights) {
                    sr.highlights = []
                }
                // store highlighted text;
                // "content" is a searchable-property of the
                // Post domain class
                def matchedFragment = highlighter.fragment("content")
                sr.highlights[index] = "..." + (matchedFragment ?: "") + "..."
            }

            /*Invokes new search method (searchable plugin)
            The search()  method returns a map containing metadata about the search,
            along with a list of domain classes matching the criteria*/
            if (params.justMine) {
                query += " +loginId:${session.user.loginId}"
            }

            params.max = params.max?.toInteger() ?: 500
            params.max = Math.min(params.max, 500)
            def searchResult = Post.search(query, params)

            return [searchResult: searchResult]
        } catch (e){
            return [searchError: true]
        }
    }
}
