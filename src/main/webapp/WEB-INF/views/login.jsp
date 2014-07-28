
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



<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Content Manager</title>

<jsp:include page="resources.jsp" flush="true"></jsp:include>


<!-- Begin Custom -->
<script type="text/javascript" src="resources/js/cm.login.js"></script>
<!-- End Custom -->



</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>

	<jsp:include page="header.jsp"></jsp:include>
	<br>
	<jsp:include page="breadcrumbs.jsp"></jsp:include>
	<br>

	<div class="row">
		<div class="large-9 push-3 columns">

			<div class="row">
				<div class="large-6 columns">
					<h3 id="contentGroupModalLabel">Sign In</h3>
					<form action="j_spring_security_check" name="loginForm"
						method="post">
						<div class="row">
							<div class="large-12 columns">
								<label>Username: <input type="text" id="j_username"
									name="j_username" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<label>Password: <input type="password" id="j_password"
									name="j_password" />
								</label>
							</div>
						</div>
						<input type="hidden" value="on" id="_spring_security_remember_me"
							name="_spring_security_remember_me" style="display: none" />
						<div>&nbsp;</div>
					</form>
					<div class="row">
						<div class="large-12 columns">
							<span id="login_errors" class="alert radius label"></span>
						</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<button id="user_sign_in_submit_button" class="button">sign
								in</button>
							<button id="user_sign_in_cancel_button" class="button">cancel</button>
							<br> <span id="login_errors" class="alert radius label"></span>
						</div>
					</div>
				</div>
			</div>


		</div>

		<jsp:include page="left_nav_bar.jsp" flush="true"></jsp:include>
	</div>






	<jsp:include page="footer.jsp" flush="false"></jsp:include>


</body>
</html>