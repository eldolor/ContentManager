
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
<script>
	$(document).foundation();
	$(function() {
		$("#error .visible").css('visibility', 'visible');
		$("#error .visible").addClass("fadeInDown animated");
		$("#error .btn").addClass("fadeInUp animated");
	});
</script>
</head>
<body>


	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>
	<section id="error">
		<div class="overlay display-table" style="height: 536px;">
			<div class="row text-center va-align visible">
				<br> <i class="fa fa-chain-broken green"
					style="font-size: 145px;"></i>
				<h1 class="green">Uh-Oh!</h1>

				<div style="width: 50%; margin: auto;">
					<h5 class="white" style="line-height: 30px;">Something&apos;s
						not right!</h5>
					<a href="/" class="link">Go Home</a>
				</div>

			</div>
		</div>
		<br> <br>
	</section>

	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>


</body>
</html>


