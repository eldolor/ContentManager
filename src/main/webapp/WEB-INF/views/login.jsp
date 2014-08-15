
<!doctype html>
<!--[if IE 9]><html class="lt-ie10" lang="en" > <![endif]-->
<html class="no-js" lang="en"
	data-useragent="Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>


<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Content Manager</title>

<jsp:include page="resources.jsp" flush="true"></jsp:include>


<!-- Begin Custom -->
<script type="text/javascript" src="resources/js/cm.login.js"></script>
<!-- End Custom -->
<script type="text/javascript">
setErrors(${errors});

</script>



</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>

	<jsp:include page="header.jsp"></jsp:include>
	<br>
	<jsp:include page="breadcrumbs.jsp"></jsp:include>
	<br>

	<div class="row">
		<jsp:include page="left_nav_bar.jsp" flush="true"></jsp:include>
		<div class="large-6 columns">

			<div class="row">
				<form id="loginForm" action="j_spring_security_check"
					name="loginForm" method="post" data-abide>
					<fieldset>
						<legend>Sign In</legend>
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<span id="forgot_password_request_submitted_message"
									class="success radius label" style="display: none">We
									have sent an email containing a temporary link that will allow
									you to reset your password for the next 24 hours.</span>
							</div>
						</div>
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<label for="j_username">Email <small>required</small> <input
									type="email" id="j_username" name="j_username"
									placeholder="bruce.almighty@gmail.com" required="required" /></label>
								<small class="error">A valid email address is required.</small>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<label for="j_password">Password <small>required</small><input
									type="password" id="j_password" name="j_password"
									placeholder="LittleW0men." required="required" /></label>
							</div>
						</div>
						<input type="hidden" value="on" id="_spring_security_remember_me"
							name="_spring_security_remember_me" style="display: none" />
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<span id="login_errors" class="alert radius label" style="display: none"></span><br>
							</div>
						</div>
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<button id="user_sign_in_submit_button" class="button">sign
									in</button>

								<a href="javascript:void(0);" id="user_sign_in_cancel_button">cancel</a>
								<div class="right">
									<a href="javascript:void(0);" id="user_forgot_password">forgot
										my password</a>
								</div>
							</div>
						</div>
					</fieldset>
				</form>

			</div>



		</div>

		<!-- Instructions on how to use the page -->
		<aside class="large-3 columns hide-for-small" id="right_aside">
			<p>Place instructions on how to use the page here.</p>
		</aside>
	</div>






	<jsp:include page="footer.jsp" flush="false"></jsp:include>


</body>
</html>