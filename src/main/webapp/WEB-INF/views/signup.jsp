
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
<jsp:include page="meta_tags.jsp"></jsp:include>
<title>Skok</title>

<jsp:include page="resources.jsp" flush="true"></jsp:include>


<!-- Begin Custom -->
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.signup.js"></script>

<!-- End Custom -->


</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>

	<jsp:include page="top_bar.jsp"></jsp:include>
	<br>

	<section id="sign_up_detail">
		<div class="row full-width">
			<div class="large-12 columns">
				<h3 class="gray">Sign Up</h3>
				<br>
				<form role="form" name="signupForm" id="signupForm"
					data-abide="ajax">
					<div>
						<label for="userName">Email <small>required</small> <input
							class="form-control" type="email" id="userName" name="userName"
							class="form-control" placeholder="Your email address"
							required="required" /></label> <small class="error">A valid
							email address is required.</small>
					</div>
					<div>
						<label for="password">Password <small>required</small><input
							class="form-control" type="password" id="password"
							name="password" placeholder="password" required="required"
							required
							pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" />
						</label><small class="error">Your password must match the
							requirements: Must include upper-case, lower-case, number/special
							character, and be minimum of 8 characters</small>
						<!-- / generic password: upper-case, lower-case, number/special character, and min 8 characters -->
					</div>
					<div id="cm_errors_container" style="display: none">
						<ul id="vision">
							<li><div>
									<i class="fi-alert"></i>
								</div> <span id="signup_errors"></span>
								<p class="clearfix"></p></li>
						</ul>
					</div>
					<div>
						<button id="user_sign_up_submit_button"
							class="button radius btn-default">sign up</button>
						<a href="javascript:void(0);" id="user_sign_up_cancel_button">cancel</a>
					</div>
					<div id="progress_bar_sign_up" style="display: none">
						<div class="progress radius">
							<span class="meter"
								style="width: 40%; background-color: #5cb85c;">Registering...</span>
						</div>
					</div>
					<div id="progress_bar_sign_in" style="display: none">
						<div class="progress radius">
							<span class="meter"
								style="width: 40%; background-color: #5cb85c;">Logging in...</span>
						</div>
					</div>
				</form>

			</div>

			<!-- hidden login form for post signup auto-login -->
			<div style="display: none">
				<div class="large-6 columns">
					<h3 id="contentGroupModalLabel">Sign In</h3>
					<form action="j_spring_security_check" name="loginForm"
						method="post">
						<div>
							<label>Username: <input type="text" id="j_username"
								name="j_username" />
							</label>
						</div>
						<div>
							<label>Password: <input type="password" id="j_password"
								name="j_password" />
							</label>
						</div>
						<input type="hidden" value="on" id="_spring_security_remember_me"
							name="_spring_security_remember_me" style="display: none" />
						<div>&nbsp;</div>
					</form>
					<div>
						<span id="login_errors" class="alert radius label"
							style="display: none"></span>
					</div>
					<div>
						<button id="user_sign_in_submit_button" class="button">sign
							in</button>
						<a href="javascript:void(0);" id="user_sign_in_cancel_button">cancel</a>
					</div>
				</div>
			</div>
		</div>
	</section>
	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>