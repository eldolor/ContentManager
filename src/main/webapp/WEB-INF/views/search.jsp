
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
<!--Note: Place this above cm.search.js -->
	var mSearchTerm = '${searchTerm}';
</script>
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.search.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.application.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.content.group.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.content.js"></script>
<script>
	$(function() {
		$("#portfolio_page .container").addClass("fadeInUp animated");
		$("#Grid").mixitup(); // needed for our work section
		//$(".btn").addClass("bounceInUp animated");
	});
	$('#our_work').waypoint(function() {
		$("#our_work .visible").css('visibility', 'visible');
		$("#our_work .visible").addClass("fadeInUp animated");
	}, {
		offset : 335
	});

	//$(document).foundation();
</script>

<!-- End Custom -->


</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>
	<%-- <section id="portfolio_page">

		<div id="our_work">
			<div class="row full-width">
				<h2 class="text-center gray">Search Results</h2>

				<p class="text-center page_sub_heading">Lorem ipsum dolor sit
					amet, consectetur adipisicing elit.</p>
 				<div class="line">
					<img src="/resources/images/cm/line.png" alt="line" />
				</div>
 				<br />
				<div class="visible">
					<ul>
						<li class="filter active gray"
							data-filter="applications content_groups content">All /</li>
						<li class="filter gray" data-filter="applications">Applications
							/</li>
						<li class="filter gray" data-filter="content_groups">Content
							Groups /</li>
						<li class="filter gray" data-filter="content">Content /</li>
					</ul>
					<div class="clearfix"></div>

					<div class="row">
						<ul id="Grid">
							<li class="mix Web large-4 medium-4 columns"><div
									id="applications_list"></div> <jsp:include
									page="create_application.jsp"></jsp:include></li>
							<li class="mix Web large-4 medium-4 columns"><div
									id="content_groups_list"></div> <jsp:include
									page="create_content_group.jsp"></jsp:include></li>
							<li class="mix Web large-4 medium-4 columns"><div
									id="content_list"></div> <jsp:include page="create_content.jsp"></jsp:include></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section> --%>

	<!-- old -->
	 <section id="blog">
		<div class="row full-width">
			<h2 class="text-center gray">Search Results</h2>
<!-- 			<div class="line">
				<img src="/resources/images/cm/line.png" alt="line" />
			</div>
 -->			<br />
			<div class="row">
				<div class="large-4 columns">
					<h2 class="text-center gray">Applications</h2>
<!-- 					<div class="line">
						<img src="/resources/images/cm/line.png" alt="line" />
					</div>
 -->					<div id="applications_list"></div>
					<jsp:include page="create_application.jsp"></jsp:include>
				</div>
				<div class="large-4 columns">
					<h2 class="text-center gray">Content Groups</h2>
<!-- 					<div class="line">
						<img src="/resources/images/cm/line.png" alt="line" />
					</div>
 -->					<div id="content_groups_list"></div>
					<jsp:include page="create_content_group.jsp"></jsp:include>
				</div>
				<div class="large-4 columns">
					<h2 class="text-center gray">Content</h2>
<!-- 					<div class="line">
						<img src="/resources/images/cm/line.png" alt="line" />
					</div>
 -->					<div id="content_list"></div>
					<jsp:include page="create_content.jsp"></jsp:include>
				</div>

			</div>
		</div>
	</section> 
	<br>
	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>