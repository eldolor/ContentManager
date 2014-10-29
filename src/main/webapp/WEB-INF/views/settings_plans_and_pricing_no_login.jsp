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
		<div class="row full-width">
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
					<br /> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
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
					<br> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
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
					<br> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
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
					<br> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
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





