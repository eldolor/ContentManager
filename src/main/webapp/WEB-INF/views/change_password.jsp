
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
<title>Content Manager</title>
<jsp:include page="resources.jsp" flush="true"></jsp:include>

<!-- Begin Custom -->
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.change.password.js"></script>
<!-- End Custom -->
<script type="text/javascript">
	var lIsRequestExpired = $
	{
		isRequestExpired
	};
</script>

</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>
	<section>
		<div class="row full-width">
			<h3 class="gray">Change Password</h3>
			<div class="large-12 columns" id="change_password"
				style="display: none">

				<form role="form" id="changePasswordForm" name="changePasswordForm"
					data-abide="ajax">
					<input type="hidden" id="user_forgot_password_request_guid"
						name="user_forgot_password_request_guid" value="${guid}" />

					<div id="user_message" style="display: none">
						<ul id="vision">
							<li><div>
									<i class="fi-lightbulb"></i>
								</div> <span>Password has been updated</span>
								<p class="clearfix"></p></li>
						</ul>
					</div>

					<div>
						<label>Password <small>required</small><input
							type="password" id="user_new_password" name="user_new_password"
							required="required" required
							pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" />
						</label><small class="error">Your password must match the
							requirements: Must include upper-case, lower-case, number/special
							character, and be minimum of 8 characters</small>
					</div>
					<div>
						<label>Confirm Password <small>required</small><input
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
								</div> <span id="user_errors"></span>
								<p class="clearfix"></p></li>
						</ul>
					</div>
					<div>
						<button id="user_password_change_button" class="button radius">update
							password</button>
					</div>
					<div id="progress_bar" style="display: none">
						<div class="progress radius">
							<span class="meter"
								style="width: 40%; background-color: #5cb85c;">Updating...</span>
						</div>
					</div>
				</form>
			</div>
			<!-- End Content  -->
		</div>
	</section>
	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>
</body>
</html>