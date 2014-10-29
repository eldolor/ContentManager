<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>



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

	<section id="price">
		<h2 class="text-center gray">Plans and Pricing</h2>
		<p class="text-center page_sub_heading">
			Please <a
				href="mailto:anshu@skok.co?Subject=Plans%20and%20Pricing">Contact Us</a> if you need
			additional Storage or Bandwidth
		</p>
<!-- 		<div class="line">
			<img src="/resources/images/cm/line.png" alt="line" />
		</div>
 -->		<br>
		<c:choose>
			<c:when test="${isError == true}">
				<div id="subscription_errors" style="display: none">
					<ul id="vision">
						<li><div>
								<i class="fi-lightbulb"></i>
							</div> <span>${errors}</span>
							<p class="clearfix"></p></li>
					</ul>
				</div>
			</c:when>
		</c:choose>

		<div class="row full-width">
			<div id="progress_bar" style="display: none">
				<div class="progress radius">
					<span class="meter" style="width: 40%; background-color: #5cb85c;">Updating...</span>
				</div>
			</div>
			<div class="large-2 text-center columns" id="free_plan">
				<div class="price_item" id="free">
					<div class="price_cost">
						<div class="package_name">BASIC</div>
						<h1>FREE</h1>
						<div class="package_rate">Per month</div>
					</div>
				</div>
				<div class="package_details">
					<div class="package_feature">
						<span>Bandwidth</span>
						<div>100GB per month</div>
					</div>
					<br />
					<div class="package_feature">
						<span>Storage</span> <i class="fa fa-cloud"></i>
						<div>20GB</div>
					</div>
					<br />
					<c:choose>
						<c:when
							test="${isSubscribed == false || subscribedCanonicalPlanName == canonicalPlanNameFree}">
							<img alt="free" src="/resources/images/cm/done.png" />
						</c:when>
						<c:otherwise>
							<!-- display the button-->
							<a href="javascript:void(0);"
								class="button radius small btn-default" id="update_plan_button"
								onclick="javascript: planUpdate('${canonicalPlanNameFree}', 'progress_bar', '$0');">downgrade</a>
						</c:otherwise>
					</c:choose>
					<div class="row" id="plan_progress_bar_free" style="display: none;">
						<div class="large-12 columns">
							<label>Loading...</label><br>
							<div class="progress radius">
								<span class="meter" style="width: 40%"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="large-2 columns text-center" id="micro_plan">
				<div class="price_item " id="micro">
					<div class="price_cost">
						<div class="package_name">MICRO</div>
						<h1>$50.00</h1>
						<div class="package_rate">Per month</div>
					</div>
				</div>
				<div class="package_details">
					<div class="package_feature">
						<span>Bandwidth</span>
						<div>500GB per month</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span> <i class="fa fa-cloud green"></i>
						<div>40GB</div>
					</div>
					<br>
					<c:choose>
						<c:when test="${isUpdateCCInfo == true || isSubscribed == false}">
							<form id="subscribePlanForm" name="subscribePlanForm"
								action="/stripe/subscribe" method="POST"
								onclick="javascript: $('#plan_progress_bar_micro').show(); return true;">
								<input type="hidden" id="canonicalPlanName"
									name="canonicalPlanName" value="${canonicalPlanNameMicro}" />
								<script src="https://checkout.stripe.com/checkout.js"
									class="stripe-button"
									data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ" data-amount="5000"
									data-name="Skok"
									data-description="500MB Plan @ $50/month"
									data-image="/resources/images/cm/logo-128x128.png" data-zip-code="true"
									data-panel-label="Subscribe" data-label="Get Started">
									
								</script>
							</form>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${subscribedCanonicalPlanName == canonicalPlanNameMicro}">
									<img alt="free" src="/resources/images/cm/done.png" />
								</c:when>
								<c:otherwise>
									<!-- display the button-->
									<a href="javascript:void(0);"
										class="button radius small btn-default"
										id="update_plan_button"
										onclick="javascript: planUpdate('${canonicalPlanNameMicro}', 'progress_bar', '$50');">
										<c:choose>
											<c:when
												test="${subscribedCanonicalPlanName == canonicalPlanNameFree}">upgrade</c:when>
											<c:otherwise>downgrade</c:otherwise>
										</c:choose>
									</a>
								</c:otherwise>

							</c:choose>
						</c:otherwise>
					</c:choose>


				</div>
			</div>
			<div class="large-2 columns text-center" id="small_plan">
				<div class="price_item" id="small">
					<div class="price_cost">
						<div class="package_name">SMALL</div>
						<h1>$100.00</h1>
						<div class="package_rate">Per month</div>
					</div>
				</div>
				<div class="package_details">
					<div class="package_feature">
						<span>Bandwidth</span>
						<div>1TB per month</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span> <i class="fa fa-cloud"></i>
						<div>80GB</div>
					</div>
					<br>
					<c:choose>
						<c:when test="${isUpdateCCInfo == true||isSubscribed == false}">
							<form id="subscribePlanForm" name="subscribePlanForm"
								action="/stripe/subscribe" method="POST"
								onclick="javascript: $('#plan_progress_bar_small').show(); return true;">
								<input type="hidden" id="canonicalPlanName"
									name="canonicalPlanName" value="${canonicalPlanNameSmall}" />
								<script src="https://checkout.stripe.com/checkout.js"
									class="stripe-button"
									data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ" data-amount="10000"
									data-name="Skok"
									data-description="1GB Plan @ $100/month"
									data-image="/resources/images/cm/logo-128x128.png" data-zip-code="true"
									data-panel-label="Subscribe" data-label="Get Started">
									
								</script>
							</form>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${subscribedCanonicalPlanName == canonicalPlanNameSmall}">
									<img alt="free" src="/resources/images/cm/done.png" />
								</c:when>
								<c:otherwise>
									<!-- display the button-->
									<a href="javascript:void(0);"
										class="button radius small btn-default"
										id="update_plan_button"
										onclick="javascript: planUpdate('${canonicalPlanNameSmall}', 'progress_bar', '$100');">
										<c:choose>
											<c:when
												test="${subscribedCanonicalPlanName == canonicalPlanNameFree || subscribedCanonicalPlanName == canonicalPlanNameMicro}">upgrade</c:when>
											<c:otherwise>downgrade</c:otherwise>
										</c:choose>
									</a>
								</c:otherwise>

							</c:choose>
						</c:otherwise>
					</c:choose>

				</div>
			</div>
			<div class="large-2 columns text-center price_item" id="medium_plan">
				<div class="price_item" id="medium">
					<div class="price_cost">
						<div class="package_name">MEDIUM</div>
						<h1>$150.00</h1>
						<div class="package_rate">Per month</div>
					</div>
				</div>
				<div class="package_details">
					<div class="package_feature">
						<span>Bandwidth</span>
						<div>1.5TB per month</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span>
						<div>160GB</div>
					</div>
					<br>
					<c:choose>
						<c:when test="${isUpdateCCInfo == true || isSubscribed == false}">
							<form id="subscribePlanForm" name="subscribePlanForm"
								action="/stripe/subscribe" method="POST"
								onclick="javascript: $('#plan_progress_bar_medium').show(); return true;">
								<input type="hidden" id="canonicalPlanName"
									name="canonicalPlanName" value="${canonicalPlanNameMedium}" />
								<script src="https://checkout.stripe.com/checkout.js"
									class="stripe-button"
									data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ" data-amount="15000"
									data-name="Skok"
									data-description="2GB Plan @ $150/month"
									data-image="/resources/images/cm/logo-128x128.png" data-zip-code="true"
									data-panel-label="Subscribe" data-label="Get Started">
									
								</script>
							</form>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${subscribedCanonicalPlanName == canonicalPlanNameMedium}">
									<img alt="free" src="/resources/images/cm/done.png" />
								</c:when>
								<c:otherwise>
									<!-- display the button-->
									<a href="javascript:void(0);"
										class="button radius small btn-default"
										id="update_plan_button"
										onclick="javascript: planUpdate('${canonicalPlanNameMedium}', 'progress_bar', '$150');">
										<c:choose>
											<c:when
												test="${subscribedCanonicalPlanName == canonicalPlanNameFree || subscribedCanonicalPlanName == canonicalPlanNameMicro  || subscribedCanonicalPlanName == canonicalPlanNameSmall}">upgrade</c:when>
											<c:otherwise>downgrade</c:otherwise>
										</c:choose>
									</a>
								</c:otherwise>

							</c:choose>
						</c:otherwise>
					</c:choose>

				</div>
			</div>
			<div class="large-2 columns text-center price_item" id="large_plan">
				<div class="price_item" id="large">
					<div class="price_cost">
						<div class="package_name">LARGE</div>
						<h1>$200.00</h1>
						<div class="package_rate">Per month</div>
					</div>
				</div>
				<div class="package_details">
					<div class="package_feature">
						<span>Bandwidth</span>
						<div>2TB per month</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span>
						<div>320GB</div>
					</div>
					<br>
					<c:choose>
						<c:when test="${isUpdateCCInfo == true || isSubscribed == false}">
							<form id="subscribePlanForm" name="subscribePlanForm"
								action="/stripe/subscribe" method="POST"
								onclick="javascript: $('#plan_progress_bar_large').show(); return true;">
								<input type="hidden" id="canonicalPlanName"
									name="canonicalPlanName" value="${canonicalPlanNameLarge}" />
								<script src="https://checkout.stripe.com/checkout.js"
									class="stripe-button"
									data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ" data-amount="20000"
									data-name="Skok"
									data-description="4GB Plan @ $200/month"
									data-image="/resources/images/cm/logo-128x128.png" data-zip-code="true"
									data-panel-label="Subscribe" data-label="Get Started">
									
								</script>
							</form>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${subscribedCanonicalPlanName == canonicalPlanNameLarge}">
									<img alt="free" src="/resources/images/cm/done.png" />
								</c:when>
								<c:otherwise>
									<!-- display the button-->
									<a href="javascript:void(0);"
										class="button radius small btn-default"
										id="update_plan_button"
										onclick="javascript: planUpdate('${canonicalPlanNameLarge}', 'progress_bar', '$200');">upgrade</a>
								</c:otherwise>

							</c:choose>
						</c:otherwise>
					</c:choose>

				</div>
			</div>
			<div class="clearfix"></div>
		</div>

	</section>

	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

	<script type="text/javascript">
		var lSubscribedCanonicalPlanName = ${subscribedCanonicalPlanName};
		var lCanonicalPlanNameLarge = ${canonicalPlanNameLarge};
		var lCanonicalPlanNameMedium = ${canonicalPlanNameMedium};
		var lCanonicalPlanNameSmall = ${canonicalPlanNameSmall};
		var lCanonicalPlanNameMicro = ${canonicalPlanNameMicro};
		var lCanonicalPlanNameFree = ${canonicalPlanNameFree};
		if (lSubscribedCanonicalPlanName == lCanonicalPlanNameLarge) {
			$('#large_plan').addClass('recommended');
		} else if (lSubscribedCanonicalPlanName == lCanonicalPlanNameMedium) {
			$('#medium_plan').addClass('recommended');
		} else if (lSubscribedCanonicalPlanName == lCanonicalPlanNameSmall) {
			$('#small_plan').addClass('recommended');
		} else if (lSubscribedCanonicalPlanName == lCanonicalPlanNameMicro) {
			$('#micro_plan').addClass('recommended');
		} else if (lSubscribedCanonicalPlanName == lCanonicalPlanNameFree) {
			$('#free_plan').addClass('recommended');
		}
	</script>
</body>
</html>





