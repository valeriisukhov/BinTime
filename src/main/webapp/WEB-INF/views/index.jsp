<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html ng-app="bintime">

<head>
	<title>RESTful Service</title>
	<script>angular.module("bintime", [])
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
			})</script>
</head>
<body ng-controller="home">
<h1>RESTful ULR Map</h1>
<form ng-submit="sendRequest()">
	<input type="text" ng-model="mpnField">
	<button type="submit">Submit</button>
</form>
<table>
	<tr>
		<th>1</th>
		<th>2</th>
		<th>3</th>
	</tr>
	<tr ng-repeat="item in responce">
		<th>{{item.mpn}}</th>
		<th>{{item.status}}</th>
		<th>{{item.id}}</th>
	</tr>
</table>
</body>
</html>