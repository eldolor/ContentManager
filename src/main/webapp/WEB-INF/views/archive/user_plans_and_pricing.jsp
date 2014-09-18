<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>





<div class="large-9 columns content active" id="plans_and_pricing"
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
					<form id="subscribePlanForm" name="subscribePlanForm"
						action="/stripe/subscribe" method="POST"
						onclick="javascript: $('#plan_progress_bar_large').show(); return true;">
						<input type="hidden" id="canonicalPlanName"
							name="canonicalPlanName" value="${canonicalPlanNameLarge}" />
						<script src="https://checkout.stripe.com/checkout.js"
							class="stripe-button" data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ"
							data-amount="5000" data-name="Content Manager"
							data-description="4GB Plan @ $50/month"
							data-image="/resources/images/done.png" data-zip-code="true"
							data-panel-label="Subscribe" data-label="Get Started">
							
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
							<a href="javascript:void(0);" class="button radius"
								id="update_plan_button"
								onclick="javascript: planUpdate('${canonicalPlanNameLarge}', 'plan_progress_bar_large', '$50');">upgrade</a>
						</c:otherwise>

					</c:choose>
				</c:otherwise>
			</c:choose>
			<div class="row" id="plan_progress_bar_large" style="display: none;">
				<div class="large-12 columns">
					<label>Loading...</label><br>
					<div class="progress radius">
						<span class="meter" style="width: 40%"></span>
					</div>
				</div>
			</div></li>
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
					<form id="subscribePlanForm" name="subscribePlanForm"
						action="/stripe/subscribe" method="POST"
						onclick="javascript: $('#plan_progress_bar_medium').show(); return true;">
						<input type="hidden" id="canonicalPlanName"
							name="canonicalPlanName" value="${canonicalPlanNameMedium}" />
						<script src="https://checkout.stripe.com/checkout.js"
							class="stripe-button" data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ"
							data-amount="2500" data-name="Content Manager"
							data-description="2GB Plan @ $25/month"
							data-image="/resources/images/done.png" data-zip-code="true"
							data-panel-label="Subscribe" data-label="Get Started">
							
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
							<a href="javascript:void(0);" class="button radius"
								id="update_plan_button"
								onclick="javascript: planUpdate('${canonicalPlanNameMedium}', 'plan_progress_bar_medium', '$25');">
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
			<div class="row" id="plan_progress_bar_medium" style="display: none;">
				<div class="large-12 columns">
					<label>Loading...</label><br>
					<div class="progress radius">
						<span class="meter" style="width: 40%"></span>
					</div>
				</div>
			</div></li>
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
					<form id="subscribePlanForm" name="subscribePlanForm"
						action="/stripe/subscribe" method="POST"
						onclick="javascript: $('#plan_progress_bar_small').show(); return true;">
						<input type="hidden" id="canonicalPlanName"
							name="canonicalPlanName" value="${canonicalPlanNameSmall}" />
						<script src="https://checkout.stripe.com/checkout.js"
							class="stripe-button" data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ"
							data-amount="1500" data-name="Content Manager"
							data-description="1GB Plan @ $15/month"
							data-image="/resources/images/done.png" data-zip-code="true"
							data-panel-label="Subscribe" data-label="Get Started">
							
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
							<a href="javascript:void(0);" class="button radius"
								id="update_plan_button"
								onclick="javascript: planUpdate('${canonicalPlanNameSmall}', 'plan_progress_bar_small', '$15');">
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
			<div class="row" id="plan_progress_bar_small" style="display: none;">
				<div class="large-12 columns">
					<label>Loading...</label><br>
					<div class="progress radius">
						<span class="meter" style="width: 40%"></span>
					</div>
				</div>
			</div></li>
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
					<form id="subscribePlanForm" name="subscribePlanForm"
						action="/stripe/subscribe" method="POST"
						onclick="javascript: $('#plan_progress_bar_micro').show(); return true;">
						<input type="hidden" id="canonicalPlanName"
							name="canonicalPlanName" value="${canonicalPlanNameMicro}" />
						<script src="https://checkout.stripe.com/checkout.js"
							class="stripe-button" data-key="pk_test_4aEi34FWLvjmVHc14fQoUQPZ"
							data-amount="700" data-name="Content Manager"
							data-description="500MB Plan @ $7/month"
							data-image="/resources/images/done.png" data-zip-code="true"
							data-panel-label="Subscribe" data-label="Get Started">
							
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
							<a href="javascript:void(0);" class="button radius"
								id="update_plan_button"
								onclick="javascript: planUpdate('${canonicalPlanNameMicro}', 'plan_progress_bar_micro', '$7');">
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
			<div class="row" id="plan_progress_bar_micro" style="display: none;">
				<div class="large-12 columns">
					<label>Loading...</label><br>
					<div class="progress radius">
						<span class="meter" style="width: 40%"></span>
					</div>
				</div>
			</div></li>
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
					<a href="javascript:void(0);" class="button radius"
						id="update_plan_button"
						onclick="javascript: planUpdate('${canonicalPlanNameFree}', 'plan_progress_bar_free', '$0');">downgrade</a>
				</c:otherwise>
			</c:choose>
			<div class="row" id="plan_progress_bar_free" style="display: none;">
				<div class="large-12 columns">
					<label>Loading...</label><br>
					<div class="progress radius">
						<span class="meter" style="width: 40%"></span>
					</div>
				</div>
			</div></li>
	</ul>

</div>