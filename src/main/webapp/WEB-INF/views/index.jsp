
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
	src="/resources/javascripts/cm/cm.index.js"></script>
<!-- End Custom -->

</head>
<body>
	<jsp:include page="top_bar.jsp"></jsp:include>

	<br>
	<section id="services" class="display-table">
		<div class="container va-align">
			<h2 class="text-center">&lt;app name here&gt;</h2>
			<p class="text-center page_sub_heading">Content management and
				delivery platform that reliably delivers rich content (images and
				videos) to your Mobile Apps</p>
			<div class="line">
				<img src="/resources/images/box/line.png" alt="line" />
			</div>
			<br> <br>
			<div class="visible text-center">
				<div class="row full-width">
					<div class="large-4 columns">
						<div id="one">
							<div class="box">
								<i class="fi-folder"></i>
							</div>
							<h5>Flexible Content Management Platform</h5>
							<div class="service-content">Manage your rich content as
								bite-sized chunks, instead of trying to deliver the full content
								payload in one shot. Continually update your
								rich content, with no downtime or performance reduction.</div>
						</div>
					</div>
					<div class="large-4 columns">
						<div id="two">
							<div class="box">
								<i class="fi-cloud"></i>
							</div>
							<h5>Reliable Content Delivery</h5>
							<div class="service-content">Manage the performance
								unknowns of the last wireless mile, to deliver rich content to
								Mobile devices. Allows users of your Mobile Apps, to engage with
								your rich content, even if they lose their data connection.</div>
						</div>
					</div>
					<div class="large-4 columns">
						<div id="three">
							<div class="box">
								<i class="fi-graph-trend"></i>
							</div>
							<h5>Increased Developer Productivity</h5>
							<div class="service-content">Manages the download and
								storage of rich content on Mobile devices, without requiring to
								write any code or having to upload a new APK to Google Play</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</section>
	<br>
	<br>
	<br>
	<section id="message">
		<div class="overlay display-table">
			<div class="container text-center va-align">
				<div class="visible">
					<h2 class="white">Sign Up Now! It's easy and free.</h2>
					<br> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
				</div>
			</div>
		</div>
	</section>
	<section id="portfolio">
		<br> <br>
		<h2 class="text-center">Features</h2>
		<p class="text-center page_sub_heading">&nbsp;</p>
		<div class="line">
			<img src="/resources/images/box/line.png" alt="line" />
		</div>
		<div id="owl-demo" class="owl-carousel owl-theme visible">
			<div class="item">
				<img src="/resources/images/cm/cloud_driven.jpg" alt="owl" />
				<h3 class="gray">Cloud-driven Architecture</h3>
				<div class="gray text_left">Cloud-driven architecture that is
					more efficient and cost-effective. Powered by Google AppEngine and
					Amazon Web Services</div>
			</div>

			<div class="item">
				<img src="/resources/images/cm/reliable_platform.jpg" alt="img" />
				<h3 class="gray">Reliable Content Delivery</h3>
				<div class="gray text_left">Reliable content delivery platform
					that can handle spotty networks, dropped connections, and long
					pauses between service requests</div>
			</div>

			<div class="item">
				<img src="/resources/images/cm/expansion_files.png" alt="img" />

				<h3 class="gray">Say Goodbye to Google Play APK Expansion Files</h3>
				<div class="gray text_left">You are no longer forced to bundle
					all your rich content into expansion files, or required to release
					a new application update, just to update your rich content</div>
			</div>
			<div class="item">
				<img src="/resources/images/cm/continuous_updates.jpg" alt="img" />
				<h3 class="gray">Enables Continuous Updates</h3>
				<div class="gray text_left">Continuous rich content updates
					with no downtime or performance reduction on Mobile devices</div>
			</div>

			<div class="item">
				<img src="/resources/images/cm/android_sdk.jpg" alt="img" />
				<h3 class="gray">Easily Pluggable SDK</h3>
				<div class="gray text_left">SDK that provides a simple API to
					access rich content</div>
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