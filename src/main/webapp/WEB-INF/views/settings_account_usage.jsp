
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
	src="/resources/javascripts/cm/cm.account.settings.js"></script>
<!-- End Custom -->
<script type="text/javascript" src="https://js.stripe.com/v2/"></script>
</head>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<script type="text/javascript">
	google.load("visualization", "1", {
		packages : [ "gauge" ]
	});
	google.load("visualization", "1", {packages:["corechart"]});
	//setupAccountUsageGoogCharts() is defined in cm.account.settings.js
	google.setOnLoadCallback(setupAccountUsageGoogCharts);
</script>
<body>



	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>
	<section>
		<div class="row full-width">
			<h2 class="text-center gray">Account Usage</h2>
			<div class="line">
				<img src="/resources/images/box/line.png" alt="line" />
			</div>
			<div class="content" id="account_usage">
				<div id="progress_bar" style="display: none">
					<div class="progress radius">
						<span class="meter" style="width: 40%; background-color: #5cb85c;">Loading...</span>
					</div>
				</div>
				<div id="account_usage_details"></div>
			</div>

		</div>
	</section>

	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>


</body>
</html>


