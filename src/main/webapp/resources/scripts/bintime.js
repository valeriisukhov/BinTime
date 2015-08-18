var app = angular.module("App", []);
    app.controller("Home", function($scope, $http, $document, $log){
        $scope.data = {
            mpn: '',
            id: null,
            offers: [],
            status: ''
        };

        $scope.update = function() {
            var target = $document.find('#csrf');
            $http({
                method: 'GET',
                url: "restful.api/getprice",
                params: {mpn: $scope.mpn, available: $scope.available, sortKey: $scope.sortKey},
                headers: {'X-CSRF-TOKEN': target.val()
                }
            }).success(function (data){
                $scope.data = data;
                $log.debug($scope.data);
            })
        };
        $scope.getError = function (error) {
            if (angular.isDefined(error)) {
                if (error.required) {
                    return "Cannot be empty!";
                } if (error.pattern){
                    return "Pattern";
                } if (error.maxlength){
                    return "Max length"
                } if (error.minlength){
                    return "Min length"
                }
            }
        };
    });