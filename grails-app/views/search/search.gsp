<%--
  Created by IntelliJ IDEA.
  User: Marcos
  Date: 22/01/2016
  Time: 10:12 AM
--%>

<html>
    <head>
        <title>Find A Post</title>
        <meta name="layout" content="main"/>
    </head>
    <body>
        <h1>Search</h1>
        <g:form action="search">
            <g:textField name="q" value="${params.q}"/>
            <g:select name="max" from="${[1, 5, 10, 50]}" value="${params.max ?: 10}" />
            <g:submitButton name="search" value="Search"/>
        </g:form>
        <g:if test="${session.user}">
            Just My Stuff:
            <g:checkBox name="justMine" value="${params.justMine}"/>
        </g:if>

        <hr/>
        <br />

        <g:if test="${searchResult?.results}">
            <g:each var="result" in="${searchResult.results}" status="hitNum">
                <div class="searchPost">
                    <div class="searchFrom">
                        From
                        <g:link controller="users" action="${result.user.loginId}">
                            ${result.user.loginId}
                        </g:link>
                        ...
                    </div>
                    <div class="searchContent">
                        ${raw(searchResult.highlights[hitNum])}
                    </div>
                </div>
                <br />
            </g:each>
        </g:if>

        <g:if test="${searchResult}">
            <hr/>
            <br />
            Displaying hits
            <b>${searchResult.offset+1}-
                ${Math.min(searchResult.offset + searchResult.max,
                        searchResult.total)}</b> of
            <b>${searchResult.total}</b>:
            <g:set var="totalPages"
                   value="${Math.ceil(searchResult.total / searchResult.max)}"/>
            <g:if test="${totalPages == 1}">
                <span class="currentStep">1</span>
            </g:if>
            <g:else>
                <g:paginate controller="search" action="search"
                            params="[q: params.q]"
                            total="${searchResult.total}"
                            prev="&lt; previous" next="next &gt;"/>
            </g:else>
            <p/>
        </g:if>
    </body>
</html>