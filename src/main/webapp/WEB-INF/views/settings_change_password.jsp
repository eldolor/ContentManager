
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
	src="/resources/javascripts/cm/cm.account.settings.js"></script>
<!-- End Custom -->
<script type="text/javascript" src="https://js.stripe.com/v2/"></script>

</head>
<body>


	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>
	<section>
		<div class="row full-width">
			<h2 class="text-center gray">Change Password</h2>
<!-- 			<div class="line">
				<img src="/resources/images/cm/line.png" alt="line" />
			</div>
 -->			<div class="large-12 columns" id="change_password">
				<form role="form" id="changePasswordForm" name="changePasswordForm"
					data-abide="ajax">
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
					<div id="user_message" style="display: none">
						<ul id="vision">
							<li><div>
									<i class="fi-lightbulb"></i>
								</div> <span>Password has been updated</span>
								<p class="clearfix"></p></li>
						</ul>
					</div>
					<div>
						<label>Old Password <small>required</small><input
							type="password" id="user_old_password" name="user_old_password"
							required="required" />
						</label>
					</div>
					<div>

						<label>New Password <small>required</small><input
							type="password" id="user_new_password" name="user_new_password"
							required="required" required
							pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" />
						</label><small class="error">Your password must match the
							requirements: Must include upper-case, lower-case, number/special
							character, and be minimum of 8 characters</small>
					</div>
					<div>

						<label>Confirm New Password <small>required</small><input
							type="password" id="user_confirm_new_password"
							name="user_confirm_new_password" data-equalto="user_new_password"
							required="required" required
							pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" />
						</label> <small class="error">The password did not match</small>
					</div>
					<div id="cm_errors_container" style="display: none">
						<ul id="vision">
							<li><div>
									<i class="fi-alert"></i>
								</div> <span id="change_password_errors"></span>
								<p class="clearfix"></p></li>
						</ul>
					</div>
					<div>
						<button id="user_password_change_button"
							class="button radius btn-default">change password</button>
						<div class="right">
							<a href="javascript:void(0);" id="user_forgot_password">forgot
								my password</a>
						</div>
					</div>
					<div id="progress_bar" style="display: none">
						<div class="progress radius">
							<span class="meter"
								style="width: 40%; background-color: #5cb85c;">Updating...</span>
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


</body>
</html>


