
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
	<section id="home">
		<div class="overlay">
			<div id="intro">
				<div class="container text_center">
					<img src="/resources/images/cm/Cube_big.png" alt="big logo" />
					<h1 class="white">
						Advanced Content Management and Delivery platform for your Mobile
						Apps</span>
					</h1>
				</div>
			</div>
			<div class="container text_center" id="learn_more">
				<a class="link" href="#sticky_navigation">Learn More</a>
			</div>
		</div>
	</section>


	<jsp:include page="top_bar.jsp"></jsp:include>

	<br>
	<section id="services" class="display-table">
		<div class="container va-align">
			<h2 class="text-center">&lt;app name here&gt;</h2>
			<p class="text-center page_sub_heading">Advanced Content
				Management and Delivery platform for your Mobile Apps</p>
<!-- 			<div class="line">
				<img src="/resources/images/cm/line.png" alt="line" />
			</div>
 -->			<br> <br>
			<div class="visible text-center">
				<div class="row full-width">
					<div class="large-4 columns">
						<div id="one">
							<div class="box">
								<i class="fi-folder"></i>
							</div>
							<h5>Advanced Content Management Platform</h5>
							<div class="service-content">Enables you to continually
								add, update, or remove content on your Mobile Apps, without
								degrading the mobile experience of the users at any point, or
								having to release a new version of your Mobile App.</div>
						</div>
					</div>
					<div class="large-4 columns">
						<div id="two">
							<div class="box">
								<i class="fi-cloud"></i>
							</div>
							<h5>Streamlined Content Delivery</h5>
							<div class="service-content">Streamlined content delivery
								platform that handles spotty networks and dropped connections.
								Elevates user experience by allowing users of your Mobile Apps
								to engage with your rich content, even if they lose their data
								connection.</div>
						</div>
					</div>
					<div class="large-4 columns">
						<div id="three">
							<div class="box">
								<i class="fi-graph-trend"></i>
							</div>
							<h5>Increased Developer Productivity</h5>
							<div class="service-content">Manages the download and
								storage of content on your Mobile Apps, without requiring to
								write any code, or having to release a new version of your
								Mobile App</div>
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
		<h2 class="text-center">Powerful New Features</h2>
		<p class="text-center page_sub_heading">&nbsp;</p>
<!-- 		<div class="line">
			<img src="/resources/images/cm/line.png" alt="line" />
		</div>
 -->		<div id="owl-demo" class="owl-carousel owl-theme visible">
			<div class="item">
				<img src="/resources/images/cm/Letter-C-orange-icon.png" alt="owl" />
				<h3 class="gray">Cloud-driven Architecture</h3>
				<div class="gray ">Cloud-driven architecture that is more
					efficient and cost-effective. Powered by Google AppEngine and
					Amazon Web Services</div>
			</div>

			<div class="item">
				<img src="/resources/images/cm/Letter-S-orange-icon.png" alt="img" />
				<h3 class="gray">Streamlined Content Delivery</h3>
				<div class="gray ">Streamlined content delivery platform that
					handles spotty networks and dropped connections. Elevates user
					experience by allowing users of your Mobile Apps to engage with
					your rich content, even if they lose their data connection.</div>
			</div>

			<div class="item">
				<img src="/resources/images/cm/Letter-G-orange-icon.png" alt="img" />

				<h3 class="gray">Say Goodbye to Google Play APK Expansion Files</h3>
				<div class="gray ">Liberates you from having to bundle all
					your rich content into monolithic expansion files, and to release a
					new application update, all to just update your content</div>
			</div>
			<div class="item">
				<img src="/resources/images/cm/Letter-C-orange-icon.png" alt="img" />
				<h3 class="gray">Continuous Content Updates</h3>
				<div class="gray ">Continuous content updates with no downtime
					or performance reduction of your Mobile Apps, or having to release
					a new version of your Mobile App</div>
			</div>

			<div class="item">
				<img src="/resources/images/cm/Letter-N-orange-icon.png" alt="img" />
				<h3 class="gray">No Extra Coding Required</h3>
				<div class="gray">Downloads and stores content on your Mobile
					Apps, without requiring to write any code.</div>
			</div>
			<div class="item">
				<img src="/resources/images/cm/Letter-E-orange-icon.png" alt="img" />
				<h3 class="gray">Easily-pluggable &amp; Feature-rich SDK</h3>
				<div class="gray ">SDK that provides a simple API to access
					content within your Mobile Apps</div>
			</div>
			<div class="item">
				<img src="/resources/images/cm/Letter-M-orange-icon.png" alt="img" />
				<h3 class="gray">Mobile Device Storage</h3>
				<div class="gray ">Notifies users if the device does not have
					enough storage, or if the external storage is not accessible</div>
			</div>
			<div class="item">
				<img src="/resources/images/cm/Letter-A-orange-icon.png" alt="img" />
				<h3 class="gray">Advanced Caching on Device</h3>
				<div class="gray ">Advanced in-memory caching that results in
					faster content display, and minimizes memory footprint</div>
			</div>
			<div class="item">
				<img src="/resources/images/cm/Letter-N-orange-icon.png" alt="img" />
				<h3 class="gray">Non-Blocking Content Downloads</h3>
				<div class="gray ">Content downloads occur in the background,
					and do not block user interaction</div>
			</div>
			<div class="item">
				<img src="/resources/images/cm/Letter-N-orange-icon.png" alt="img" />
				<h3 class="gray">Network Connectivity</h3>
				<div class="gray ">Network connectivity can change during the
					download. Handles such changes, and if interrupted, resumes the
					download when possible.</div>
			</div>
			<div class="item">
				<img src="/resources/images/cm/Letter-D-orange-icon.png" alt="img" />
				<h3 class="gray">Download Notifications</h3>
				<div class="gray ">Notifies users while downloading content in
					the background, and when the download process is complete.</div>
			</div>
			<div class="item">
				<img src="/resources/images/cm/Letter-A-orange-icon.png" alt="img" />
				<h3 class="gray">Analytics</h3>
				<div class="gray ">Collects analytics to track usage
					statistics of your content, which can be viewed online.</div>
			</div>


		</div>

	</section>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>

	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>