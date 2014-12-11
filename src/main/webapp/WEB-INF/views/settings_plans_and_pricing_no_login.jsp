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
	jQuery(function($) {
		try {
			log("function($)", "Entering");
			$(document).foundation();

			var doc = document.documentElement;
			doc.setAttribute('data-useragent', navigator.userAgent);
		} catch (err) {
			handleError("function($)", err);
		} finally {
			log("function($)", "Exiting");
		}
	});
</script>

<!-- End Custom -->
<jsp:include page="facebook_generic_tags.jsp"></jsp:include>

</head>
<body>


	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>

	<section id="price">
		<h2 class="text-center gray">Plans and Pricing</h2>
		<p class="text-center page_sub_heading">
			Try paid plans FREE for 30 days. Please <a
				href="mailto:support@skok.co?Subject=Plans%20and%20Pricing">Contact
				Us</a> if you need additional Storage or Bandwidth
		</p>
		<!-- 		<div class="line">
			<img src="/resources/images/cm/line.png" alt="line" />
		</div>
 -->
		<br>
		<div class="row full-width">
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
						<div>${canonicalPlanFreeNetworkBandwidth}&nbsp;permonth</div>
					</div>
					<br />
					<div class="package_feature">
						<span>Storage</span> <i class="fa fa-cloud"></i>
						<div>${canonicalPlanFreeStorage}</div>
					</div>
					<br /> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
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
						<div>${canonicalPlanMicroNetworkBandwidth}&nbsp;permonth</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span> <i class="fa fa-cloud green"></i>
						<div>${canonicalPlanMicroStorage}</div>
					</div>
					<br> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
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
						<div>${canonicalPlanSmallNetworkBandwidth}&nbsp;permonth</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span> <i class="fa fa-cloud"></i>
						<div>${canonicalPlanSmallStorage}</div>
					</div>
					<br> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
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
						<div>${canonicalPlanMediumNetworkBandwidth}&nbsp;permonth</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span>
						<div>${canonicalPlanMediumStorage}</div>
					</div>
					<br> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
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
						<div>${canonicalPlanLargeNetworkBandwidth}&nbsp;permonth</div>
					</div>
					<br>
					<div class="package_feature">
						<span>Storage</span>
						<div>${canonicalPlanLargeStorage}</div>
					</div>
					<br> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>

	</section>

	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>


</body>
</html>





