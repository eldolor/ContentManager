
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

		<div class="large-9 columns" id="user_billing" style="display: none">

			<fieldset>
				<legend>Available Plans</legend>
				<table>
					<tbody>
						<tr>
							<td><ul class="pricing-table">
									<li class="title">Large</li>
									<li class="price">$50/month</li>
									<li class="bullet-item">4GB per application</li>
									<li class="bullet-item">20 applications</li>
									<li class="cta-button"><form action="/stripe/token"
											method="POST">
											<input type="hidden" id="canonicalPlanName"
												name="canonicalPlanName" value="large" />
											<script src="https://checkout.stripe.com/checkout.js"
												class="stripe-button"
												data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ"
												data-amount="5000" data-name="Content Manager"
												data-description="4GB Plan @ $50/month"
												data-image="/resources/images/done.png">
												
											</script>
										</form></li>
								</ul></td>
							<td><ul class="pricing-table">
									<li class="title">Medium</li>
									<li class="price">$25/month</li>
									<li class="bullet-item">2GB per application</li>
									<li class="bullet-item">15 applications</li>
									<li class="cta-button"><form action="/stripe/token"
											method="POST"><input type="hidden" id="canonicalPlanName"
												name="canonicalPlanName" value="medium" />
											<script src="https://checkout.stripe.com/checkout.js"
												class="stripe-button"
												data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ"
												data-amount="2500" data-name="Content Manager"
												data-description="2GB Plan @ $25/month"
												data-image="/resources/images/done.png">
												
											</script>
										</form></li>
								</ul></td>
							<td><ul class="pricing-table">
									<li class="title">Small</li>
									<li class="price">$15/month</li>
									<li class="bullet-item">1GB per application</li>
									<li class="bullet-item">10 applications</li>
									<li class="cta-button"><form action="/stripe/token"
											method="POST"><input type="hidden" id="canonicalPlanName"
												name="canonicalPlanName" value="small" />
											<script src="https://checkout.stripe.com/checkout.js"
												class="stripe-button"
												data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ"
												data-amount="1500" data-name="Content Manager"
												data-description="1GB Plan @ $15/month"
												data-image="/resources/images/done.png">
												
											</script>
										</form></li>
								</ul></td>
						</tr>
						<tr>
							<td><ul class="pricing-table">
									<li class="title">Micro</li>
									<li class="price">$7/month</li>
									<li class="bullet-item">500MB per application</li>
									<li class="bullet-item">5 applications</li>
									<li class="cta-button"><form action="/stripe/token"
											method="POST"><input type="hidden" id="canonicalPlanName"
												name="canonicalPlanName" value="micro" />
											<script src="https://checkout.stripe.com/checkout.js"
												class="stripe-button"
												data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ"
												data-amount="700" data-name="Content Manager"
												data-description="500MB Plan @ $7/month"
												data-image="/resources/images/done.png">
												
											</script>
										</form></li>
								</ul></td>
							<td><ul class="pricing-table">
									<li class="title">Free</li>
									<li class="price">$0/month</li>
									<li class="bullet-item">100MB per application</li>
									<li class="bullet-item">1 application</li>
									<li class="cta-button"><img alt="free"
										src="/resources/images/done.png"></li>
								</ul></td>
							<td>&nbsp;</td>
						</tr>
					</tbody>
				</table>


			</fieldset>

		</div>
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