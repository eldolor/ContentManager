
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


</head>
<body>


	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="header.jsp"></jsp:include>
	<br>

	<div class="row">

		<div class="large-12 columns" >
			<div id="terms_of_service">
				<form>
					<fieldset>
						<legend>Terms of Service</legend>

						<div class="row">
							<div class="large-12 columns">
								<div class="name-field">
									<label>By using the &lt;&nbsp;&gt;.com web site ("Service"), or
										any services of &lt;&nbsp;&gt;, Inc ("&lt;&nbsp;&gt;"), you are agreeing to be
										bound by the following terms and conditions ("Terms of
										Service"). IF YOU ARE ENTERING INTO THIS AGREEMENT ON BEHALF
										OF A COMPANY OR OTHER LEGAL ENTITY, YOU REPRESENT THAT YOU
										HAVE THE AUTHORITY TO BIND SUCH ENTITY, ITS AFFILIATES AND ALL
										USERS WHO ACCESS OUR SERVICES THROUGH YOUR ACCOUNT TO THESE
										TERMS AND CONDITIONS, IN WHICH CASE THE TERMS "YOU" OR "YOUR"
										SHALL REFER TO SUCH ENTITY, ITS AFFILIATES AND USERS
										ASSOCIATED WITH IT. IF YOU DO NOT HAVE SUCH AUTHORITY, OR IF
										YOU DO NOT AGREE WITH THESE TERMS AND CONDITIONS, YOU MUST NOT
										ACCEPT THIS AGREEMENT AND MAY NOT USE THE SERVICES. </label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<div class="name-field">
									<label>If &lt;&nbsp;&gt; makes material changes to these Terms,
										we will notify you by email or by posting a notice on our site
										before the changes are effective. Any new features that
										augment or enhance the current Service, including the release
										of new tools and resources, shall be subject to the Terms of
										Service. Continued use of the Service after any such changes
										shall constitute your consent to such changes. You can review
										the most current version of the Terms of Service at any time
										at: https://&lt;&nbsp;&gt;.com/site/terms </label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<div class="name-field">
									<label>Violation of any of the terms below will result
										in the termination of your Account. While &lt;&nbsp;&gt; prohibits
										such conduct and Content on the Service, you understand and
										agree that &lt;&nbsp;&gt; cannot be responsible for the Content posted
										on the Service and you nonetheless may be exposed to such
										materials. You agree to use the Service at your own risk.</label>
								</div>
							</div>
						</div>
						<fieldset>
							<legend>Account Terms</legend>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>1. You must be 13 years or older to use this
											Service.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>2. You must be a human. Accounts registered by
											"bots" or other automated methods are not permitted.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>3. You must provide a valid email address, and
											any other information requested in order to complete the
											signup process.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>4. Your login may only be used by one person -
											a single login shared by multiple people is not permitted.
											You may create separate logins for as many people as your
											plan allows</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>5. You are responsible for maintaining the
											security of your account and password. &lt;&nbsp;&gt; cannot and will
											not be liable for any loss or damage from your failure to
											comply with this security obligation.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>6. You are responsible for all Content posted
											and activity that occurs under your account (even when
											Content is posted by others who have accounts under your
											account).</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>7. One person or legal entity may not maintain
											more than one free account.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>8. You may not use the Service for any illegal
											or unauthorized purpose. You must not, in the use of the
											Service, violate any laws in your jurisdiction (including but
											not limited to copyright or trademark laws).</label>
									</div>
								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend>SDK Terms</legend>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>Any use of the SDK, including use of the SDK
											through a third-party product that accesses &lt;&nbsp;&gt;, is bound
											by these Terms of Service plus the following specific terms:</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>1. You expressly understand and agree that
											&lt;&nbsp;&gt; shall not be liable for any direct, indirect,
											incidental, special, consequential or exemplary damages,
											including but not limited to, damages for loss of profits,
											goodwill, use, data or other intangible losses (even if
											&lt;&nbsp;&gt; has been advised of the possibility of such damages),
											resulting from your use of the SDK or third-party products
											that access data via the SDK.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>2. Abuse or excessively frequent requests to
											&lt;&nbsp;&gt; via the SDK may result in the temporary or permanent
											suspension of your account's access to the API. &lt;&nbsp;&gt;, in
											its sole discretion, will determine abuse or excessive usage
											of the API. &lt;&nbsp;&gt; will make a reasonable attempt via email
											to warn the account owner prior to suspension.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>3. &lt;&nbsp;&gt; reserves the right at any time to
											modify or discontinue, temporarily or permanently, your
											access to the API (or any part thereof) with or without
											notice.</label>
									</div>
								</div>
							</div>

						</fieldset>
						<fieldset>
							<legend>Payment, Refunds, Upgrading and Downgrading
								Terms</legend>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>1. All paid plans must enter a valid payment
											account. Free accounts are not required to provide payment
											account information.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>2. An upgrade from the free plan to any paying
											plan will immediately bill you.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>3. For monthly payment plans, the Service is
											billed in advance on a monthly basis and is non-refundable.
											There will be no refunds or credits for partial months of
											service, upgrade/downgrade refunds, or refunds for months
											unused with an open account. In order to treat everyone
											equally, no exceptions will be made.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>4. When changing from a monthly billing cycle
											to a yearly billing cycle, &lt;&nbsp;&gt; will bill for a full year
											at the next monthly billing date.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>5. All fees are exclusive of all taxes, levies,
											or duties imposed by taxing authorities, and you shall be
											responsible for payment of all such taxes, levies, or duties,
											excluding only United States (federal or state) taxes.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>6. For any upgrade or downgrade in plan level
											while on a monthly billing cycle, the credit card that you
											provided will automatically be charged the new rate on your
											next billing cycle. For upgrades or downgrades while on a
											yearly plan, &lt;&nbsp;&gt; will immediately charge or refund the
											difference in plan cost, prorated for the remaining time in
											your yearly billing cycle.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>7. Downgrading your Service may cause the loss
											of Content, features, or capacity of your Account. &lt;&nbsp;&gt;
											does not accept any liability for such loss.</label>
									</div>
								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend>Cancellation and Termination</legend>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>1. You are solely responsible for properly
											canceling your account. An email or phone request to cancel
											your account is not considered cancellation. You can cancel
											your account at any time by clicking on the Account link in
											the global navigation bar at the top of the screen. The
											Account screen provides a simple no questions asked
											cancellation link.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>2. All of your Content will be immediately
											deleted from the Service upon cancellation. This information
											can not be recovered once your account is cancelled.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>3. If you cancel the Service before the end of
											your current paid up month, your cancellation will take
											effect immediately and you will not be charged again.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>4. &lt;&nbsp;&gt;, in its sole discretion, has the
											right to suspend or terminate your account and refuse any and
											all current or future use of the Service, or any other &lt;&nbsp;&gt;
											service, for any reason at any time. Such termination of the
											Service will result in the deactivation or deletion of your
											Account or your access to your Account, and the forfeiture
											and relinquishment of all Content in your Account. &lt;&nbsp;&gt;
											reserves the right to refuse service to anyone for any reason
											at any time.</label>
									</div>
								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend>Modifications to the Service and Prices</legend>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>1. &lt;&nbsp;&gt; reserves the right at any time and
											from time to time to modify or discontinue, temporarily or
											permanently, the Service (or any part thereof) with or
											without notice.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>2. Prices of all Services, including but not
											limited to monthly subscription plan fees to the Service, are
											subject to change upon 30 days notice from us. Such notice
											may be provided at any time by posting the changes to the
											&lt;&nbsp;&gt; Site (&lt;&nbsp;&gt;.com) or the Service itself.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>3. &lt;&nbsp;&gt; shall not be liable to you or to any
											third-party for any modification, price change, suspension or
											discontinuance of the Service.</label>
									</div>
								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend>Copyright and Content Ownership</legend>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>1. We claim no intellectual property rights
											over the material you provide to the Service. Your profile
											and materials uploaded remain yours. However, by setting your
											pages to be viewed publicly, you agree to allow others to
											view your Content. By setting your repositories to be viewed
											publicly, you agree to allow others to view and fork your
											repositories.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>2. &lt;&nbsp;&gt; does not pre-screen Content, but
											&lt;&nbsp;&gt; and its designee have the right (but not the
											obligation) in their sole discretion to refuse or remove any
											Content that is available via the Service.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>3. You shall defend &lt;&nbsp;&gt; against any claim,
											demand, suit or proceeding made or brought against &lt;&nbsp;&gt; by
											a third-party alleging that Your Content, or Your use of the
											Service in violation of this Agreement, infringes or
											misappropriates the intellectual property rights of a
											third-party or violates applicable law, and shall indemnify
											&lt;&nbsp;&gt; for any damages finally awarded against, and for
											reasonable attorney&apos;s fees incurred by, &lt;&nbsp;&gt; in connection
											with any such claim, demand, suit or proceeding; provided,
											that &lt;&nbsp;&gt; (a) promptly gives You written notice of the
											claim, demand, suit or proceeding; (b) gives You sole control
											of the defense and settlement of the claim, demand, suit or
											proceeding (provided that You may not settle any claim,
											demand, suit or proceeding unless the settlement
											unconditionally releases &lt;&nbsp;&gt; of all liability); and (c)
											provides to You all reasonable assistance, at Your expense.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>4. The look and feel of the Service is
											copyright �2010 &lt;&nbsp;&gt; Inc. All rights reserved. You may not
											duplicate, copy, or reuse any portion of the HTML/CSS,
											Javascript, or visual design elements or concepts without
											express written permission from &lt;&nbsp;&gt;.</label>
									</div>
								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend>General Conditions</legend>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>1. Your use of the Service is at your sole
											risk. The service is provided on an "as is" and "as
											available" basis.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>2. Support for &lt;&nbsp;&gt; services is only
											available in English, via email.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>3. You understand that &lt;&nbsp;&gt; uses third-party
											vendors and hosting partners to provide the necessary
											hardware, software, networking, storage, and related
											technology required to run the Service.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>4. You must not modify, adapt or hack the
											Service or modify another website so as to falsely imply that
											it is associated with the Service, &lt;&nbsp;&gt;, or any other
											&lt;&nbsp;&gt; service.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>5. You may use the &lt;&nbsp;&gt; Pages static hosting
											service solely as permitted and intended to host your
											organization pages, personal pages, or project pages, and for
											no other purpose. You may not use &lt;&nbsp;&gt; Pages in violation
											of &lt;&nbsp;&gt;'s trademark or other rights or in violation of
											applicable law. &lt;&nbsp;&gt; reserves the right at all times to
											reclaim any &lt;&nbsp;&gt; subdomain without liability to you.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>6. You agree not to reproduce, duplicate, copy,
											sell, resell or exploit any portion of the Service, use of
											the Service, or access to the Service without the express
											written permission by &lt;&nbsp;&gt;.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>7. We may, but have no obligation to, remove
											Content and Accounts containing Content that we determine in
											our sole discretion are unlawful, offensive, threatening,
											libelous, defamatory, pornographic, obscene or otherwise
											objectionable or violates any party's intellectual property
											or these Terms of Service.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>8. Verbal, physical, written or other abuse
											(including threats of abuse or retribution) of any &lt;&nbsp;&gt;
											customer, employee, member, or officer will result in
											immediate account termination.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>9. You understand that the technical processing
											and transmission of the Service, including your Content, may
											be transferred unencrypted and involve (a) transmissions over
											various networks; and (b) changes to conform and adapt to
											technical requirements of connecting networks or devices.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>10. You must not upload, post, host, or
											transmit unsolicited email, SMSs, or "spam" messages.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>11. You must not transmit any worms or viruses
											or any code of a destructive nature.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>12. If your bandwidth usage significantly
											exceeds the average bandwidth usage (as determined solely by
											&lt;&nbsp;&gt;) of other &lt;&nbsp;&gt; customers, we reserve the right to
											immediately disable your account or throttle your file
											hosting until you can reduce your bandwidth consumption.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>13. &lt;&nbsp;&gt; does not warrant that (i) the
											service will meet your specific requirements, (ii) the
											service will be uninterrupted, timely, secure, or error-free,
											(iii) the results that may be obtained from the use of the
											service will be accurate or reliable, (iv) the quality of any
											products, services, information, or other material purchased
											or obtained by you through the service will meet your
											expectations, and (v) any errors in the Service will be
											corrected.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>14. You expressly understand and agree that
											&lt;&nbsp;&gt; shall not be liable for any direct, indirect,
											incidental, special, consequential or exemplary damages,
											including but not limited to, damages for loss of profits,
											goodwill, use, data or other intangible losses (even if
											&lt;&nbsp;&gt; has been advised of the possibility of such damages),
											resulting from: (i) the use or the inability to use the
											service; (ii) the cost of procurement of substitute goods and
											services resulting from any goods, data, information or
											services purchased or obtained or messages received or
											transactions entered into through or from the service; (iii)
											unauthorized access to or alteration of your transmissions or
											data; (iv) statements or conduct of any third-party on the
											service; (v) or any other matter relating to the service</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>15. The failure of &lt;&nbsp;&gt; to exercise or
											enforce any right or provision of the Terms of Service shall
											not constitute a waiver of such right or provision. The Terms
											of Service constitutes the entire agreement between you and
											&lt;&nbsp;&gt; and govern your use of the Service, superseding any
											prior agreements between you and &lt;&nbsp;&gt; (including, but not
											limited to, any prior versions of the Terms of Service). You
											agree that these Terms of Service and Your use of the Service
											are governed under California law.</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="large-12 columns">
									<div class="name-field">
										<label>16. Questions about the Terms of Service should
											be sent to support@&lt;&nbsp;&gt;.com.</label>
									</div>
								</div>
							</div>
						</fieldset>

						<div class="row">
							<div class="large-12 columns">
								<button id="done_button" class="button radius">done</button>
							</div>
						</div>
					</fieldset>
				</form>
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