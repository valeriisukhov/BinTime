<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>RESTful Service</title>
</head>
<body>
<div class="container" ng-controller="Home">
	<div class="page-header text-center">
		<h1>BinTime RESTful App</h1>
	</div>
	<div class="panel">
		<div class="input-group">
			<form name="frm" novalidate>
			<div class="col-lg-4">
				<label for="mpn">MPN:</label>
				<input id="mpn"
					   type="text"
					   required
					   ng-minlength="3"
					   name="mpn"
					   placeholder="SA-#KP"
					   class="form-control ng-pristine ng-valid ng-touched"
					   ng-model="mpn"
					   ng-keyup="$event.keyCode == 13 ? update() : null">
				<div class="error" ng-show="frm.mpn.$invalid && frm.mpn.$dirty">
					{{getError(frm.mpn.$error)}}
				</div>
			</div>

			<div class="col-lg-3">
				<label for="sel1">Select list:</label>
				<select class="form-control" id="sel1" ng-model="available">
					<option value="0" selected >No Filter</option>
					<option value="1">Less & Enough</option>
					<option value="2">Enough</option>
				</select>
			</div>
			<div class="col-lg-3">
				<label for="sel2">Select list:</label>
				<select class="form-control" id="sel2" ng-model="sortKey">
					<option value="0" selected>No Sort</option>
					<option value="1">Price up</option>
					<option value="2">Price down</option>
				</select>
			</div>
			<div><input type="hidden"
						id="csrf"
						name="${_csrf.parameterName}"
						value="${_csrf.token}"/></div>
			<div class="col-lg-2">
				<span class="input-btn">
				<button class="btn btn-default"
						id="sendButton"
						ng-click="update()"
						ng-disabled="frm.$invalid">Send</button>
			</span>
			</div>
			</form>
		</div>
		<table class="table table-hover">
			<thead>
			<tr>
				<th>ID</th>
				<th>MPN</th>
				<th>Offers</th>
			</tr>
			</thead>
			<tbody>
			<tr ng-model="data">
				<td>{{data.id}}</td>
				<td>{{data.mpn}}</td>
				<td>
					<ul ng-repeat="item in data.offers">
						<li>Shop ID: {{item.id}}</li>
						<li>Price: {{item.price}}</li>
						<li>Available: {{item.available}}</li>
					</ul>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>