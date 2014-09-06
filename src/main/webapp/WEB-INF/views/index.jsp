
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
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Content Manager</title>

<jsp:include page="resources.jsp" flush="true"></jsp:include>

<!-- Begin: Custom -->
<!-- use of cm.index.js has been deprecated -->

<!-- End: Custom -->



</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="row">
		<div class="large-12 columns">

			<div class="hide-for-small">
				<div id="featured">
					<!-- <img alt="slide image"
						src="http://placehold.it/1000x400&amp;text=Slide%20Image"> -->
					<img alt="slide image" src="/resources/images/index/index.jpg">
				</div>
			</div>

			<div class="row">
				<div class="small-12 show-for-small">
					<br>
					<!-- <img
						src="http://placehold.it/1000x600&amp;text=For%20Small%20Screens"> -->
					<img alt="slide image" src="/resources/images/index/index.jpg">s
				</div>
			</div>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="large-12 columns">
			<div class="row">

				<div class="large-3 small-6 columns">
					<!-- <img src="http://placehold.it/250x250&amp;text=Thumbnail"> -->
					<img src="/resources/images/index/thumbnail_1.jpg">
					<h6 class="panel">Description</h6>
				</div>
				<div class="large-3 small-6 columns">
					<!-- <img src="http://placehold.it/250x250&amp;text=Thumbnail"> -->
					<img src="/resources/images/index/thumbnail_2.jpg">
					<h6 class="panel">Description</h6>
				</div>
				<div class="large-3 small-6 columns">
					<!-- <img src="http://placehold.it/250x250&amp;text=Thumbnail"> -->
					<img src="/resources/images/index/thumbnail_3.jpg">
					<h6 class="panel">Description</h6>
				</div>
				<div class="large-3 small-6 columns">
					<!-- <img src="http://placehold.it/250x250&amp;text=Thumbnail"> -->
					<img src="/resources/images/index/thumbnail_4.jpg">
					<h6 class="panel">Description</h6>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="large-12 columns">
			<div class="row">

				<div class="large-8 columns">
					<div class="panel radius">
						<div class="row">
							<div class="large-6 small-6 columns">
								<h4>Content Manager</h4>
								<hr>
								<h5 class="subheader">Risus ligula, aliquam nec fermentum
									vitae, sollicitudin eget urna. Donec dignissim nibh fermentum
									odio ornare sagittis.</h5>
								<div class="show-for-small" style="text-align: center">
									<a class="small radius button" href="/login">Sign In!</a><br>
									<a class="small radius button" href="/signup">Sign Up Now! It's easy and free.</a>
								</div>
							</div>
							<div class="large-6 small-6 columns">
								<p>Suspendisse ultrices ornare tempor. Aenean eget ultricies
									libero. Phasellus non ipsum eros. Vivamus at dignissim massa.
									Aenean dolor libero, blandit quis interdum et, malesuada nec
									ligula. Nullam erat erat, eleifend sed pulvinar ac. Suspendisse
									ultrices ornare tempor. Aenean eget ultricies libero.</p>
							</div>
						</div>
					</div>
				</div>
				<div class="large-4 columns hide-for-small">
					<h4>Get In Touch!</h4>
					<hr>
					<a id="user_sign_in" href="/login">
						<div class="panel radius callout" style="text-align: center">
							<strong>Sign In!</strong>
						</div>
					</a> <a id="user_sign_up" href="/signup">
						<div class="panel radius callout" style="text-align: center">
							<strong>Sign Up Now! It's easy and free.</strong>
						</div>
					</a>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" flush="false"></jsp:include>
</body>
</html>