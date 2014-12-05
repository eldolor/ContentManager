
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
<title>Skok Demo</title>
<jsp:include page="resources.jsp" flush="true"></jsp:include>
<script type="text/javascript">
	jQuery(function($) {
		try {
			log("function($)", "Entering");
			$(document).foundation();

			var doc = document.documentElement;
			doc.setAttribute('data-useragent', navigator.userAgent);
			var lList = ContentManagerJsi.getAllContentsByType('image');
			var lInnerHtml = '';
			for (var i = 0; int < lList.length; i++) {
				lInnerHtml += '<div class="row"><img alt="image" src="' + lList[i] + '" /></div>';
			}
			$('#all_images').empty().html(lInnerHtml);
		} catch (err) {
			handleError("function($)", err);
		} finally {
			log("function($)", "Exiting");
		}
	});
</script>

</head>
<body>
	<jsp:include page="top_bar.jsp"><jsp:param name="sticky"
			value="false" /></jsp:include>
	<br>

	<section id="privacy">
		<div class="row full-width">

			<h3 class="gray">All Images</h3>
			<div id="all_images"></div>
			<div class="clearfix"></div>
		</div>
	</section>

	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>