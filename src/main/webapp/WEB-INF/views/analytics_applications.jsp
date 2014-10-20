
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
	src="/resources/javascripts/cm/cm.analytics.application.js"></script>
<!-- End Custom -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<script type="text/javascript">
	google.load("visualization", "1.1", {
		packages : [ "calendar" ]
	});
</script>
</head>
<body>



	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>
	<section>
		<div class="row full-width">
			<h2 class="text-center gray">Content Usage</h2>
			<p class="text-center page_sub_heading">These charts show the
				aggregate daily content impressions on mobile devices, for each
				application.</p>
<!-- 			<div class="line">
				<img src="/resources/images/cm/line.png" alt="line" />
			</div>
 -->			<div class="content" id="analytics">
				<div id="progress_bar" style="display: none">
					<div class="progress radius">
						<span class="meter" style="width: 40%; background-color: #5cb85c;">Loading...</span>
					</div>
				</div>
				<div id="analytics_details"></div>
			</div>

		</div>
	</section>

	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

	<!-- At the bottom of your page but inside of the body tag -->
	<ol class="joyride-list" data-joyride>
		<li data-id="first_drill_down" data-button="End" data-options="prev_button: false">
			<p>You can drill down to see content usage data for each content
				group within the application</p>
			<p>
				<input id="product_tour" type="checkbox"
					onclick="javascript: setReportsTour();">&nbsp;Do not show
				this to me again.
			</p>
		</li>
	</ol>

</body>
</html>


