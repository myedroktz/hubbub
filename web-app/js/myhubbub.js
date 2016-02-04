//Registers Hubbub with AngularJS importing Restangular module
var hubbub = angular.module("Hubbub", ['restangular']);

hubbub.config(
    function(RestangularProvider) {
        //Defines PostCtrl injecting scope and Restangular
        RestangularProvider.setBaseUrl('/hubbub/api');
    }
);

//Configures Restangular URL with REST service endpoint URL
hubbub.controller("PostsCtrl", function($scope, Restangular) {
    // Points REST client at posts endpoint in API

    /** Requests list of posts **/
    $scope.allPosts = function(){
        var postsApi = Restangular.all("posts");
        postsApi.getList();
    }

    /** Posts a new post **/
    $scope.newPost = function() {
        //Sets up /hubbub/api/posts endpoint to receive a POST
        var postApi = Restangular.one.("posts");

        //Creates a new JSON content to send
        var newPost = { message: $scope.postContent };

        //Posts new post to back end
        postApi.post(null,newPost).then(function(response) {
            $scope.allPosts = postsApi.getList(); //Refreshes list when post succeeds
            $scope.postContent = "";
        }, function(errorResponse){ //Handles any error that occour
            alert("Error on creating post: " + errorResponse.status);
        });
    }
})
