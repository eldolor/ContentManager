
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
	src="/resources/javascripts/cm/cm.login.js"></script>

<script type="text/javascript">
	function setRememberMe() {
		if ($('#remember_me').attr('checked')) {
			$('#_spring_security_remember_me').val('on');
		} else {
			$('#_spring_security_remember_me').val('off');
		}
	}
</script>
<!-- End Custom -->
<jsp:include page="og_tags.jsp"></jsp:include>


</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>

	<jsp:include page="top_bar.jsp"></jsp:include>
	<br>

	<section id="sign_up_detail">
		<div class="row full-width">
			<div class="large-12 columns">
				<h3 class="gray">Sign In</h3>
				<br>
				<form role="form" id="loginForm" action="j_spring_security_check"
					name="loginForm" method="post" data-abide>
					<div id="forgot_password_request_submitted_message"
						style="display: none">
						<ul id="vision">
							<li><div>
									<i class="fi-lightbulb"></i>
								</div> <span>We have sent an email containing a temporary link
									that will allow you to reset your password for the next 24
									hours.</span>
								<p class="clearfix"></p></li>
						</ul>
					</div>
					<div>
						<label for="j_username">Email <small>required</small> <input
							class="form-control" type="email" id="j_username"
							name="j_username" placeholder="your email address"
							required="required" /></label> <small class="error">A valid
							email address is required.</small>
					</div>
					<div>
						<label for="j_password">Password <small>required</small><input
							class="form-control" type="password" id="j_password"
							name="j_password" placeholder="password" required="required" /></label>
					</div>
					<div>
						<label>Remember me</label> <input id="remember_me" type="checkbox"
							checked="checked" onclick="javascript: setRememberMe();">
					</div>
					<input type="hidden" value="on" id="_spring_security_remember_me"
						name="_spring_security_remember_me" style="display: none" />
					<div id="cm_errors_container" style="display: none">
						<ul id="vision">
							<li><div>
									<i class="fi-alert"></i>
								</div> <span id="login_errors"></span>
								<p class="clearfix"></p></li>
						</ul>
					</div>
					<div>
						<button id="user_sign_in_submit_button"
							class="button radius btn-default">sign in</button>
						<a href="javascript:void(0);" id="user_sign_in_cancel_button">cancel</a>
						<div class="right">
							<a href="javascript:void(0);" id="user_forgot_password">forgot
								my password</a>
						</div>
					</div>
					<div id="progress_bar" style="display: none">
						<div class="progress radius">
							<span class="meter"
								style="width: 40%; background-color: #5cb85c;">Authenticating...</span>
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>
	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

	<script type="text/javascript">
		try {
			var lErrorText = '${error}';
			var lError = JSON.parse(lErrorText);
			if (typeof lError === 'undefined') {
			} else {
				log('init', lError.description);
				$('#login_errors').html(lError.description);
				$('#cm_errors_container').show();
			}
		} catch (e) {
			//do nothing
		}
	</script>
</body>
</html>