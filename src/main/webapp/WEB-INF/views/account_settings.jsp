
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
<script type="text/javascript"
	src="/resources/js/cm.account.settings.js"></script>
<!-- End Custom -->
<script type="text/javascript">
	
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

		<div class="large-9 columns" id="change_password">
			<form id="changePasswordForm" name="changePasswordForm"
				data-abide="ajax">
				<fieldset>
					<legend>Change Password</legend>

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
							<span id="user_message" class="success radius label"
								style="display: none">Password has been updated</span>
						</div>
					</div>

					<div>&nbsp;</div>
					<div class="row">
						<div class="large-12 columns">
							<div class="password-field">
								<label>Old Password <small>required</small><input
									type="password" id="user_old_password" name="user_old_password"
									required="required" />
								</label> <small class="error">Old password is required</small>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<div class="password-field">
								<label>New Password <small>required</small><input
									type="password" id="user_new_password" name="user_new_password"
									required="required" required
									pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" />
								</label> <small class="error">New password is required</small>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<div class="password-confirmation-field">
								<label>Confirm New Password <small>required</small><input
									type="password" id="user_confirm_new_password"
									name="user_confirm_new_password"
									data-equalto="user_new_password" required="required" required
									pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" />
								</label> <small class="error">The password did not match</small>
							</div>
						</div>
					</div>

					<div>&nbsp;</div>
					<div class="row">
						<div class="large-12 columns">
							<span id="change_password_errors" class="alert radius label"></span>
						</div>
					</div>

					<div>&nbsp;</div>
					<div class="row">
						<div class="large-12 columns">
							<button id="user_password_change_button" class="button radius">change
								password</button>
							<div class="right">
								<a href="javascript:void(0);" id="user_forgot_password">forgot
									my password</a>
							</div>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<!-- End Content  -->
	</div>


	<br>
	<jsp:include page="footer.jsp" flush="false"></jsp:include>


	<!-- At the bottom of your page but inside of the body tag -->
	<ol class="joyride-list" data-joyride>
		<li data-id="left_nav_bar_link_1"
			data-options="tip_location:bottom;tip_animation:fade" data-text="End">
			<p>Click here to create a new Content</p>
		</li>
	</ol>
</body>
</html>