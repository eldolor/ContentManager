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
<script type="text/javascript">
	//utilized in cm.account.settings.js
	var mStripeKey = ${stripePublicKey};
	Stripe.setPublishableKey(mStripeKey);

</script>
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
			Try paid plans FREE for 30 days. Please <a
				href="mailto:support@skok.co?Subject=Plans%20and%20Pricing">Contact Us</a> if you need
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
						<h1>${canonicalPlanFreePrice}</h1>
						<div class="package_rate">Per month</div>
					</div>
				</div>
				<div class="package_details">
					<div class="package_feature">
						<span>FREE</span>
						<div>&nbsp;</div>
					</div>
					<div class="package_feature">
						<span>Bandwidth</span>
						<div>${canonicalPlanFreeNetworkBandwidth}&nbsp;per month</div>
					</div>
					<br />
					<div class="package_feature">
						<span>Storage</span> <i class="fa fa-cloud"></i>
						<div>${canonicalPlanFreeStorage}</div>
					</div>
					<br />
					<c:choose>
						<c:when
							test="${isSubscribed == false || subscribedCanonicalPlanId == canonicalPlanIdFree}">
							<img alt="free" src="/resources/images/cm/done.png" />
						</c:when>
						<c:otherwise>
							<!-- display the button-->
							<a href="javascript:void(0);"
								class="button radius small btn-default" id="update_plan_button"
								onclick="javascript: planUpdate('${canonicalPlanIdFree}', 'progress_bar', '$0');">downgrade</a>
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
						<h1>${canonicalPlanMicroPrice}</h1>
						<div class="package_rate">Per month</div>
					</div>
				</div>
				<div class="package_details">
					<div class="package_feature">
						<span>FREE for 30 days</span>
						<div>&nbsp;</div>
					</div>
					<div class="package_feature">
						<span>Bandwidth</span>
						<div>${canonicalPlanMicroNetworkBandwidth}&nbsp;per month</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span> <i class="fa fa-cloud green"></i>
						<div>${canonicalPlanMicroStorage}</div>
					</div>
					<br>
					<c:choose>
						<c:when test="${isUpdateCCInfo == true || isSubscribed == false}">
							<form id="subscribePlanForm" name="subscribePlanForm"
								action="/stripe/subscribe" method="POST"
								onclick="javascript: $('#plan_progress_bar_micro').show(); return true;">
								<input type="hidden" id="canonicalPlanId"
									name="canonicalPlanId" value="${canonicalPlanIdMicro}" />
								<script src="https://checkout.stripe.com/checkout.js"
									class="stripe-button"
									data-key="${stripePublicKey}" data-amount="${canonicalPlanMicroPriceInCents}"
									data-name="Skok"
									data-description="${canonicalPlanMicroNetworkBandwidth}&nbsp;Plan @ ${canonicalPlanMicroPrice}/month"
									data-image="/resources/images/cm/logo-128x128.png" data-zip-code="true"
									data-panel-label="Subscribe" data-label="Get Started">
									
								</script>
							</form>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${subscribedCanonicalPlanId == canonicalPlanIdMicro}">
									<img alt="free" src="/resources/images/cm/done.png" />
								</c:when>
								<c:otherwise>
									<!-- display the button-->
									<a href="javascript:void(0);"
										class="button radius small btn-default"
										id="update_plan_button"
										onclick="javascript: planUpdate('${canonicalPlanIdMicro}', 'progress_bar', '${canonicalPlanMicroPrice}');">
										<c:choose>
											<c:when
												test="${subscribedCanonicalPlanId == canonicalPlanIdFree}">upgrade</c:when>
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
						<h1>${canonicalPlanSmallPrice}</h1>
						<div class="package_rate">Per month</div>
					</div>
				</div>
				<div class="package_details">
					<div class="package_feature">
						<span>FREE for 30 days</span>
						<div>&nbsp;</div>
					</div>
					<div class="package_feature">
						<span>Bandwidth</span>
						<div>${canonicalPlanSmallNetworkBandwidth}&nbsp;per month</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span> <i class="fa fa-cloud"></i>
						<div>${canonicalPlanSmallStorage}</div>
					</div>
					<br>
					<c:choose>
						<c:when test="${isUpdateCCInfo == true||isSubscribed == false}">
							<form id="subscribePlanForm" name="subscribePlanForm"
								action="/stripe/subscribe" method="POST"
								onclick="javascript: $('#plan_progress_bar_small').show(); return true;">
								<input type="hidden" id="canonicalPlanId"
									name="canonicalPlanId" value="${canonicalPlanIdSmall}" />
								<script src="https://checkout.stripe.com/checkout.js"
									class="stripe-button"
									data-key="${stripePublicKey}" data-amount="${canonicalPlanSmallPriceInCents}"
									data-name="Skok"
									data-description="${canonicalPlanSmallNetworkBandwidth}&nbsp;Plan @ ${canonicalPlanSmallPrice}/month"
									data-image="/resources/images/cm/logo-128x128.png" data-zip-code="true"
									data-panel-label="Subscribe" data-label="Get Started">
									
								</script>
							</form>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${subscribedCanonicalPlanId == canonicalPlanIdSmall}">
									<img alt="free" src="/resources/images/cm/done.png" />
								</c:when>
								<c:otherwise>
									<!-- display the button-->
									<a href="javascript:void(0);"
										class="button radius small btn-default"
										id="update_plan_button"
										onclick="javascript: planUpdate('${canonicalPlanIdSmall}', 'progress_bar', '${canonicalPlanSmallPrice}');">
										<c:choose>
											<c:when
												test="${subscribedCanonicalPlanId == canonicalPlanIdFree || subscribedCanonicalPlanId == canonicalPlanIdMicro}">upgrade</c:when>
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
						<h1>${canonicalPlanMediumPrice}</h1>
						<div class="package_rate">Per month</div>
					</div>
				</div>
				<div class="package_details">
					<div class="package_feature">
						<span>FREE for 30 days</span>
						<div>&nbsp;</div>
					</div>
					<div class="package_feature">
						<span>Bandwidth</span>
						<div>${canonicalPlanMediumNetworkBandwidth}&nbsp;per month</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span>
						<div>${canonicalPlanMediumStorage}</div>
					</div>
					<br>
					<c:choose>
						<c:when test="${isUpdateCCInfo == true || isSubscribed == false}">
							<form id="subscribePlanForm" name="subscribePlanForm"
								action="/stripe/subscribe" method="POST"
								onclick="javascript: $('#plan_progress_bar_medium').show(); return true;">
								<input type="hidden" id="canonicalPlanId"
									name="canonicalPlanId" value="${canonicalPlanIdMedium}" />
								<script src="https://checkout.stripe.com/checkout.js"
									class="stripe-button"
									data-key="${stripePublicKey}" data-amount="${canonicalPlanMediumPriceInCents}"
									data-name="Skok"
									data-description="${canonicalPlanMediumNetworkBandwidth}&nbsp;Plan @ ${canonicalPlanMediumPrice}/month"
									data-image="/resources/images/cm/logo-128x128.png" data-zip-code="true"
									data-panel-label="Subscribe" data-label="Get Started">
									
								</script>
							</form>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${subscribedCanonicalPlanId == canonicalPlanIdMedium}">
									<img alt="free" src="/resources/images/cm/done.png" />
								</c:when>
								<c:otherwise>
									<!-- display the button-->
									<a href="javascript:void(0);"
										class="button radius small btn-default"
										id="update_plan_button"
										onclick="javascript: planUpdate('${canonicalPlanIdMedium}', 'progress_bar', '${canonicalPlanMediumPrice}');">
										<c:choose>
											<c:when
												test="${subscribedCanonicalPlanId == canonicalPlanIdFree || subscribedCanonicalPlanId == canonicalPlanIdMicro  || subscribedCanonicalPlanId == canonicalPlanIdSmall}">upgrade</c:when>
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
						<h1>${canonicalPlanLargePrice}</h1>
						<div class="package_rate">Per month</div>
					</div>
				</div>
				<div class="package_details">
					<div class="package_feature">
						<span>FREE for 30 days</span>
						<div>&nbsp;</div>
					</div>
					<div class="package_feature">
						<span>Bandwidth</span>
						<div>${canonicalPlanLargeNetworkBandwidth}&nbsp;per month</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span>
						<div>${canonicalPlanLargeStorage}</div>
					</div>
					<br>
					<c:choose>
						<c:when test="${isUpdateCCInfo == true || isSubscribed == false}">
							<form id="subscribePlanForm" name="subscribePlanForm"
								action="/stripe/subscribe" method="POST"
								onclick="javascript: $('#plan_progress_bar_large').show(); return true;">
								<input type="hidden" id="canonicalPlanId"
									name="canonicalPlanId" value="${canonicalPlanIdLarge}" />
								<script src="https://checkout.stripe.com/checkout.js"
									class="stripe-button"
									data-key="${stripePublicKey}" data-amount="${canonicalPlanLargePriceInCents}"
									data-name="Skok"
									data-description="${canonicalPlanLargeNetworkBandwidth}&nbsp;Plan @ ${canonicalPlanLargePrice}/month"
									data-image="/resources/images/cm/logo-128x128.png" data-zip-code="true"
									data-panel-label="Subscribe" data-label="Get Started">
									
								</script>
							</form>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${subscribedCanonicalPlanId == canonicalPlanIdLarge}">
									<img alt="free" src="/resources/images/cm/done.png" />
								</c:when>
								<c:otherwise>
									<!-- display the button-->
									<a href="javascript:void(0);"
										class="button radius small btn-default"
										id="update_plan_button"
										onclick="javascript: planUpdate('${canonicalPlanIdLarge}', 'progress_bar', '${canonicalPlanLargePrice}');">upgrade</a>
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
		var lSubscribedCanonicalPlanId = ${subscribedCanonicalPlanId};
		var lCanonicalPlanIdLarge = ${canonicalPlanIdLarge};
		var lCanonicalPlanIdMedium = ${canonicalPlanIdMedium};
		var lCanonicalPlanIdSmall = ${canonicalPlanIdSmall};
		var lCanonicalPlanIdMicro = ${canonicalPlanIdMicro};
		var lCanonicalPlanIdFree = ${canonicalPlanIdFree};
		if (lSubscribedCanonicalPlanId == lCanonicalPlanIdLarge) {
			$('#large_plan').addClass('recommended');
		} else if (lSubscribedCanonicalPlanId == lCanonicalPlanIdMedium) {
			$('#medium_plan').addClass('recommended');
		} else if (lSubscribedCanonicalPlanId == lCanonicalPlanIdSmall) {
			$('#small_plan').addClass('recommended');
		} else if (lSubscribedCanonicalPlanId == lCanonicalPlanIdMicro) {
			$('#micro_plan').addClass('recommended');
		} else if (lSubscribedCanonicalPlanId == lCanonicalPlanIdFree) {
			$('#free_plan').addClass('recommended');
		}
	</script>
</body>
</html>





