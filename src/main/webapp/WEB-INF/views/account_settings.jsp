
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


</head>
<body>


	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="header.jsp"></jsp:include>
	<br>
	<jsp:include page="breadcrumbs.jsp"></jsp:include>
	<br>

	<div class="row">

		<%-- <jsp:include page="left_nav_bar.jsp" flush="true"></jsp:include> --%>
		<ul class="tabs vertical" data-tab>
			<li class="tab-title active"><a href="#user_billing">Billing</a></li>
			<li class="tab-title"><a href="#change_password">Change
					Password</a></li>
		</ul>
		<div class="tabs-content vertical">
			<div class="large-9 columns content" id="change_password">
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
										type="password" id="user_old_password"
										name="user_old_password" required="required" />
									</label> <small class="error">Old password is required</small>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<div class="password-field">
									<label>New Password <small>required</small><input
										type="password" id="user_new_password"
										name="user_new_password" required="required" required
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
								<span id="change_password_errors" class="alert radius label"
									style="display: none"></span>
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

			<div class="large-9 columns content active" id="user_billing"
				data-equalizer>
				<div data-magellan-expedition="fixed">
					<fieldset>
						<legend> Available Plans </legend>
						<dl class="sub-nav">
							<dd data-magellan-arrival="large">
								<a href="#large">Large</a>
							</dd>
							<dd data-magellan-arrival="medium">
								<a href="#medium">Medium</a>
							</dd>
							<dd data-magellan-arrival="small">
								<a href="#small">Small</a>
							</dd>
							<dd data-magellan-arrival="micro">
								<a href="#micro">Micro</a>
							</dd>
							<dd data-magellan-arrival="free">
								<a href="#free">Free</a>
							</dd>
						</dl>
					</fieldset>
				</div>
				<c:choose>
					<c:when test="${isError == true}">
						<span id="subscription_errors" class="alert radius label">${errors}</span>
						<div>&nbsp;</div>
					</c:when>
				</c:choose>
				<a name="large"></a>
				<h3 data-magellan-destination="large"></h3>
				<ul class="pricing-table" data-equalizer-watch>
					<li class="title">Large</li>
					<li class="price">$50/month</li>
					<li class="bullet-item">4GB per application</li>
					<li class="bullet-item">20 applications</li>
					<li class="bullet-item"><c:choose>
							<c:when test="${isUpdateCCInfo == true || isSubscribed == false}">
								<form id="contentForm" name="contentForm"
									action="/stripe/subscribe" method="POST">
									<input type="hidden" id="canonicalPlanName"
										name="canonicalPlanName" value="${canonicalPlanNameLarge}" />
									<script src="https://checkout.stripe.com/checkout.js"
										class="stripe-button"
										data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ" data-amount="5000"
										data-name="Content Manager"
										data-description="4GB Plan @ $50/month"
										data-image="/resources/images/done.png">
										
									</script>
								</form>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when
										test="${subscribedCanonicalPlanName == canonicalPlanNameLarge}">
										<img alt="free" src="/resources/images/done.png" />
									</c:when>
									<c:otherwise>
										<!-- display the button-->
										<form action="/stripe/subscribe/update" method="POST">
											<input type="hidden" id="canonicalPlanName"
												name="canonicalPlanName" value="${canonicalPlanNameLarge}" />
											<button id="subscribe" class="button radius">upgrade</button>
										</form>
									</c:otherwise>

								</c:choose>
							</c:otherwise>
						</c:choose></li>
					<c:choose>
						<c:when
							test="${isUpdateCCInfo == true && subscribedCanonicalPlanName == canonicalPlanNameLarge}">
							<li class="bullet-item"><img alt="free"
								src="/resources/images/done.png" /></li>
						</c:when>
					</c:choose>
				</ul>

				<a name="medium"></a>
				<h3 data-magellan-destination="medium"></h3>
				<ul class="pricing-table" data-equalizer-watch>
					<li class="title">Medium</li>
					<li class="price">$25/month</li>
					<li class="bullet-item">2GB per application</li>
					<li class="bullet-item">15 applications</li>
					<li class="bullet-item"><c:choose>
							<c:when test="${isUpdateCCInfo == true || isSubscribed == false}">
								<form id="contentForm" name="contentForm"
									action="/stripe/subscribe" method="POST">
									<input type="hidden" id="canonicalPlanName"
										name="canonicalPlanName" value="${canonicalPlanNameMedium}" />
									<script src="https://checkout.stripe.com/checkout.js"
										class="stripe-button"
										data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ" data-amount="2500"
										data-name="Content Manager"
										data-description="2GB Plan @ $25/month"
										data-image="/resources/images/done.png">
										
									</script>
								</form>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when
										test="${subscribedCanonicalPlanName == canonicalPlanNameMedium}">
										<img alt="free" src="/resources/images/done.png" />
									</c:when>
									<c:otherwise>
										<!-- display the button-->
										<form action="/stripe/subscribe/update" method="POST">
											<input type="hidden" id="canonicalPlanName"
												name="canonicalPlanName" value="${canonicalPlanNameMedium}" />
											<button id="subscribe" class="button radius">
												<c:choose>
													<c:when
														test="${subscribedCanonicalPlanName == canonicalPlanNameFree || subscribedCanonicalPlanName == canonicalPlanNameMicro  || subscribedCanonicalPlanName == canonicalPlanNameSmall}">upgrade</c:when>
													<c:otherwise>downgrade</c:otherwise>
												</c:choose>
											</button>
										</form>
									</c:otherwise>

								</c:choose>
							</c:otherwise>
						</c:choose></li>
					<c:choose>
						<c:when
							test="${isUpdateCCInfo == true && subscribedCanonicalPlanName == canonicalPlanNameMedium}">
							<li class="bullet-item"><img alt="free"
								src="/resources/images/done.png" /></li>
						</c:when>
					</c:choose>
				</ul>
				<a name="small"></a>
				<h3 data-magellan-destination="small"></h3>
				<ul class="pricing-table" data-equalizer-watch>
					<li class="title">Small</li>
					<li class="price">$15/month</li>
					<li class="bullet-item">1GB per application</li>
					<li class="bullet-item">10 applications</li>
					<li class="bullet-item"><c:choose>
							<c:when test="${isUpdateCCInfo == true||isSubscribed == false}">
								<form id="contentForm" name="contentForm"
									action="/stripe/subscribe" method="POST">
									<input type="hidden" id="canonicalPlanName"
										name="canonicalPlanName" value="${canonicalPlanNameSmall}" />
									<script src="https://checkout.stripe.com/checkout.js"
										class="stripe-button"
										data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ" data-amount="1500"
										data-name="Content Manager"
										data-description="1GB Plan @ $15/month"
										data-image="/resources/images/done.png">
										
									</script>
								</form>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when
										test="${subscribedCanonicalPlanName == canonicalPlanNameSmall}">
										<img alt="free" src="/resources/images/done.png" />
									</c:when>
									<c:otherwise>
										<!-- display the button-->
										<form action="/stripe/subscribe/update" method="POST">
											<input type="hidden" id="canonicalPlanName"
												name="canonicalPlanName" value="${canonicalPlanNameSmall}" />
											<button id="subscribe" class="button radius">
												<c:choose>
													<c:when
														test="${subscribedCanonicalPlanName == canonicalPlanNameFree || subscribedCanonicalPlanName == canonicalPlanNameMicro}">upgrade</c:when>
													<c:otherwise>downgrade</c:otherwise>
												</c:choose>
											</button>
										</form>
									</c:otherwise>

								</c:choose>
							</c:otherwise>
						</c:choose></li>
					<c:choose>
						<c:when
							test="${isUpdateCCInfo == true && subscribedCanonicalPlanName == canonicalPlanNameSmall}">
							<li class="bullet-item"><img alt="free"
								src="/resources/images/done.png" /></li>
						</c:when>
					</c:choose>
				</ul>
				<a name="micro"></a>
				<h3 data-magellan-destination="micro"></h3>
				<ul class="pricing-table" data-equalizer-watch>
					<li class="title">Micro</li>
					<li class="price">$7/month</li>
					<li class="bullet-item">500MB per application</li>
					<li class="bullet-item">5 applications</li>
					<li class="bullet-item"><c:choose>
							<c:when test="${isUpdateCCInfo == true || isSubscribed == false}">
								<form id="contentForm" name="contentForm"
									action="/stripe/subscribe" method="POST">
									<input type="hidden" id="canonicalPlanName"
										name="canonicalPlanName" value="${canonicalPlanNameMicro}" />
									<script src="https://checkout.stripe.com/checkout.js"
										class="stripe-button"
										data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ" data-amount="700"
										data-name="Content Manager"
										data-description="500MB Plan @ $7/month"
										data-image="/resources/images/done.png">
										
									</script>
								</form>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when
										test="${subscribedCanonicalPlanName == canonicalPlanNameMicro}">
										<img alt="free" src="/resources/images/done.png" />
									</c:when>
									<c:otherwise>
										<!-- display the button-->
										<form action="/stripe/subscribe/update" method="POST">
											<input type="hidden" id="canonicalPlanName"
												name="canonicalPlanName" value="${canonicalPlanNameMicro}" />
											<button id="subscribe" class="button radius">
												<c:choose>
													<c:when
														test="${subscribedCanonicalPlanName == canonicalPlanNameFree}">upgrade</c:when>
													<c:otherwise>downgrade</c:otherwise>
												</c:choose>
											</button>
										</form>
									</c:otherwise>

								</c:choose>
							</c:otherwise>
						</c:choose></li>
					<c:choose>
						<c:when
							test="${isUpdateCCInfo == true && subscribedCanonicalPlanName == canonicalPlanNameMicro}">
							<li class="bullet-item"><img alt="free"
								src="/resources/images/done.png" /></li>
						</c:when>
					</c:choose>

				</ul>
				<a name="free"></a>
				<h3 data-magellan-destination="free"></h3>
				<ul class="pricing-table" data-equalizer-watch>
					<li class="title">Free</li>
					<li class="price">$0/month</li>
					<li class="bullet-item">100MB per application</li>
					<li class="bullet-item">1 application</li>
					<li class="bullet-item"><c:choose>
							<c:when
								test="${isSubscribed == false || subscribedCanonicalPlanName == canonicalPlanNameFree}">
								<img alt="free" src="/resources/images/done.png" />
							</c:when>
							<c:otherwise>
								<!-- display the button-->
								<form action="/stripe/subscribe/update" method="POST">
									<input type="hidden" id="canonicalPlanName"
										name="canonicalPlanName" value="${canonicalPlanNameFree}" />
									<button id="subscribe" class="button radius">downgrade</button>
								</form>
							</c:otherwise>
						</c:choose></li>
				</ul>

			</div>
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