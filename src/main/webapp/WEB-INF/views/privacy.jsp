
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


</head>
<body>

	<jsp:include page="common.jsp"></jsp:include>

	<jsp:include page="top_bar.jsp"></jsp:include>
	<br>

	<section id="privacy">
		<div class="row full-width">
			<div class="large-12 columns">
				<h3 class="gray">General Information</h3>
				<div>
					<label>We collect the e-mail addresses of those who
						communicate with us via e-mail, aggregate information on what
						pages consumers access or visit, and information volunteered by
						the consumer (such as survey information and/or site
						registrations). The information we collect is used to improve the
						content of our Web pages and the quality of our service, and is
						not shared with or sold to other organizations for commercial
						purposes, except to provide products or services you've requested,
						when we have your permission, or under the following
						circumstances: </label>
				</div>
				<div class="clearfix"></div>
				<div class="separator"></div>
				<div>
					<label> It is necessary to share information in order to
						investigate, prevent, or take action regarding illegal activities,
						suspected fraud, situations involving potential threats to the
						physical safety of any person, violations of Terms of Service, or
						as otherwise required by law. We transfer information about you if
						Coconut Martini is acquired by or merged with another company. In
						this event, Coconut Martini will notify you before information
						about you is transferred and becomes subject to a different
						privacy policy. Information Gathering and Usage </label>
				</div>
				<div class="clearfix"></div>
				<div class="separator"></div>
				<div>
					<label>When you register for Coconut Martini we ask for
						information such as your name, email address, billing address, or
						payment information. Members who sign up for the free account are
						not required to enter any payment details. Coconut Martini uses
						collected information for the following general purposes: products
						and services provision, billing, identification and
						authentication, services improvement, contact, and research.</label>
				</div>
				<div class="clearfix"></div>
				<div class="separator"></div>
				<h3 class="gray">Cookies</h3>
				<div>
					<label>A cookie is a small amount of data, which often
						includes an anonymous unique identifier, that is sent to your
						browser from a web site's computers and stored on your computer's
						hard drive. Cookies are required to use the Coconut Martini
						service.</label>
				</div>
				<div class="clearfix"></div>
				<div class="separator"></div>
				<div>
					<label>We use cookies to record current session
						information, but do not use permanent cookies. You are required to
						re-login to your Coconut Martini account after a certain period of
						time has elapsed to protect you against others accidentally
						accessing your account contents. Data Storage</label>
				</div>
				<div class="clearfix"></div>
				<div class="separator"></div>
				<div>
					<label>Coconut Martini uses third-party vendors and hosting
						partners to provide the necessary hardware, software, networking,
						storage, and related technology required to run Coconut Martini.
						Although Coconut Martini owns the code, databases, and all rights
						to the Coconut Martini application, you retain all rights to your
						data.</label>
				</div>

				<div class="clearfix"></div>
				<div class="separator"></div>
				<h3 class="gray">Disclosure</h3>
				<div>
					<label>Coconut Martini may disclose personally identifiable
						information under special circumstances, such as to comply with
						subpoenas or when your actions violate the Terms of Service.</label>
				</div>
				<div class="clearfix"></div>
				<div class="separator"></div>
				<h3 class="gray">EU and Swiss Safe Harbor</h3>
				<div>
					<label>If you choose to provide Coconut Martini with your
						information, you consent to the transfer and storage of that
						information on our servers located in the United States.</label>
				</div>
				<div class="clearfix"></div>
				<div class="separator"></div>
				<div>
					<label>Coconut Martini adheres to the US-EU and US-Swiss
						Safe Harbor Privacy Principles of Notice, Choice, Onward Transfer,
						Security, Data Integrity, Access and Enforcement, and is
						registered with the U.S. Department of Commerce's Safe Harbor
						Program http://www.export.gov/safeharbor/. .</label>
				</div>
				<div class="clearfix"></div>
				<div class="separator"></div>
				<div>
					<label>For European Union and Swiss residents, any
						questions or concerns regarding the use or disclosure of your
						information should be directed to Coconut Martini by sending an
						email to privacy@Coconut Martini.com. We will investigate and
						attempt to resolve complaints and disputes regarding use and
						disclosure of your information in accordance with this Privacy
						Policy. For complaints that cannot be resolved, and consistent
						with the Safe Harbor Enforcement Principle, we have committed to
						cooperate with data protection authorities located within
						Switzerland or the European Union (or their authorized
						representatives).</label>
				</div>
				<div class="clearfix"></div>
				<div class="separator"></div>
				<h3 class="gray">Changes</h3>
				<div>
					<label>Coconut Martini may periodically update this policy.
						We will notify you about significant changes in the way we treat
						personal information by sending a notice to the primary email
						address specified in your Coconut Martini primary account holder
						account or by placing a prominent notice on our site.</label>
				</div>
				<div class="clearfix"></div>
				<div class="separator"></div>
				<h3 class="gray">Questions</h3>
				<div>
					<label>Any questions about this Privacy Policy should be
						addressed to anshu@CoconutMartini.com.</label>
				</div>
			</div>

		</div>
	</section>

	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>