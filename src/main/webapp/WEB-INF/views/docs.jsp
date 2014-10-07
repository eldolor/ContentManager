
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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

	<section id="blog">
	<div class="row full-width">

		<h2 class="text-center gray">Overview</h2>

		<!-- <p class="text-center page_sub_heading">Create an application to
				manage its associated rich media content (images &amp; videos)</p> -->
		<div class="line">
			<img src="/resources/images/box/line.png" alt="line" />
		</div>
		<br />
		<div class="row">
			<div class="large-12 columns">
				<div class="clearfix"></div>
				<p class="text-left">The Content Manager API synchronizes your
					content to mobile devices. Content updates can be pushed down to
					mobile devices, almost instantaneously.</p>
				<p class="text-left">
					The API also collects <a href="/analytics/applications">Usage
						Statistics</a> of your content, which can be viewed online.
				</p>

				<!-- end 12 columns -->
			</div>
		</div>
	</div>
	</section>
	<br>
	<br>
	<section id="footer"> <jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>