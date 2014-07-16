<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>
<head>


<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<link href="resources/css/main.css" media="screen" rel="stylesheet"
	type="text/css" />

<title>Campaign Management Login</title>

<!-- JQuery related includes -->
<script src="resources/js/jquery/jquery-1.8.1.min.js"></script>
<script src="resources/js/jquery/jquery-ui-1.10.0.custom.min.js"></script>
<link href="resources/css/jquery-ui-1.10.0.custom.min.css"
	rel="stylesheet" type="text/css" />
<!--  End JQuery includes -->

<!-- Bootstrap  -->
<script type="text/javascript"
	src="resources/js/jquery/bootstrap.min.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<!-- Bootstrap  -->

<script type="text/javascript" src="resources/js/mavin.user.js"></script>
<script type="text/javascript" src="resources/js/mavin.globals.js"></script>
<script type="text/javascript" src="resources/js/mavin.utilities.js"></script>
<script type="text/javascript" src="resources/js/json2.js"></script>


</head>

<body onload="openPopup('login_div');">
	<!-- Begin Login -->
	<div class="modal hide fade" id="login_div" aria-hidden="true">

		<div class="modal-header">
			<h3 id="loginModalLabel">Sign In</h3>
		</div>
		<div class="modal-body">
			<form action="j_spring_security_check" name="loginForm" method="post"
				onsubmit="wait('login_wait')">


				<div class="popupFieldLabel">Username:</div>
				<div class="popupFieldInput">
					<input type="text" name="j_username" />
				</div>

				<div class="popupFieldLabel">Password:</div>
				<div class="popupFieldInput">
					<input type="password" name="j_password" />
				</div>

				<input type="hidden" value="on" name="_spring_security_remember_me"
					style="display: none" />
			</form>
		</div>
		<div class="modal-footer">
			<span id="login_errors" class="label label-important"></span> <img
				src="resources/images/wait.gif" style="display: none; float: right"
				id="login_wait" />  <a
				class="btn btn-primary" href="javascript:void(0)"
				onclick="document.loginForm.submit()">sign in</a><button id="user_sign_in_cancel_button" class="btn" data-dismiss="modal"
				aria-hidden="true" onclick="window.location.href = '/'">cancel</button>
		</div>

	</div>
	<!-- End Login -->


	<!-- Begin Wait Div -->
	<div id="wait_div" class="popupViewWait">
		<img src="resources/images/wait_large.gif" id="wait" />
	</div>
	<!-- End Wait Div -->

</body>
</html>