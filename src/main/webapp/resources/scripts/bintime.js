angular.module("bintime", [])
    .controller("home", function($scope, $http){

        $scope.sendRequest = function(){
            var postData = {
                mpn: $scope.mpnField
            }
            console.log($scope.mpnField);
            $http({
                method: 'GET',
                url: '/restful.api/getprice',
                data: postData
            }).success(function(data){
                $scope.responceData = data;
            })
        }
    })


