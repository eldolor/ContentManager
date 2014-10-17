
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

<!-- End Custom -->


</head>
<body>
	<jsp:include page="top_bar.jsp"></jsp:include>

	<section id="mission_culture">
	<div class="container">
		<div id="vision_mission">
			<div class="row full-width">
				<h2 class="text-center">Overview</h2>
				<div class="line">
					<img src="/resources/images/box/line.png" alt="line" />
				</div>

				<div class="large-7 columns">
					<p class="clearfix"></p>
					<div>
						<h3 class="gray">Mobile Content Requirements</h3>
						<p>Mobile is pushing ageing web architectures to the brink.
							The three-tier web architecture won&apos;t deliver any more. At
							the same time, the content requirements of Mobile Apps continue
							to expand. The performance unknowns of the last wireless mile has
							further exacerbated the problem of delivering rich content,
							images and videos, to mobile devices.</p>
						<br>
					</div>
					<div>
						<h3 class="gray">Reliable Content Delivery</h3>
						<p>A delivery tier that manages the performance unknowns of
							the last wireless mile, is key to providing great mobile
							experiences. &lt;&gt; is a content management and delivery
							platform that reliably delivers your content to Mobile devices.
							It handles spotty networks, dropped connections, and long pauses
							between service requests.</p>
						<p>This allows users of your Mobile Apps, to engage with your
							rich content, even if they lose their data connection.</p>
						<p>
					</div>
					<p class="clearfix"></p>
					<div>
						<h3 class="gray">Manage Content as Bite-Sized Chunks</h3>
						<p>The platform enables you to manage and deliver your content
							as bite-sized chunks. It enables you to continually update your
							content, with no downtime or performance reduction on Mobile or
							connected devices. You can add, update, or remove individual
							content, without degrading the mobile experience at any point, or
							having to release a new version of your Mobile App.</p>
						<p>In contrast, Google Play restricts APK file to 50MB, and
							constrains you to bundle all your rich content into 2 monolithic
							expansion files. Any updates to your rich content requires you to
							release a new version of your Mobile App</p>
					</div>
					<p class="clearfix"></p>
					<div>
						<h3 class="gray">No Extra Coding Required</h3>
						<p>The platform reliably downloads and stores content on
							Mobile or connected devices, without requiring to write any code.</p>
						<p>In contrast, Google Play requires application hooks within
							your Mobile Apps, to handle situations where expansion files have
							not yet been downloaded and processed.</p>
					</div>
					<p class="clearfix"></p>
					<div>
						<h3 class="gray">Easily-pluggable &amp; Feature-rich SDK</h3>
						<p>SDK that provides a simple API to access content on
							Mobile or connected devices</p>
					</div>
					<p class="clearfix"></p>
					<div>
						<h3 class="gray">Mobile Device Storage</h3>
						<p>Notifies users if the device does not have enough storage,
							or if the external storage is not accessible</p>
					</div>
					<p class="clearfix"></p>
					<div>
						<h3 class="gray">Non-Blocking Content Downloads</h3>
						<p>Content downloads occur in the background, and do not block
							user interaction</p>
					</div>
					<p class="clearfix"></p>
					<div>
						<h3 class="gray">Network Connectivity</h3>
						<p>Network connectivity can change during the download.
							Handles such changes, and if interrupted, resumes the download
							when possible.</p>
					</div>
					<p class="clearfix"></p>
					<div>
						<h3 class="gray">Download Notifications</h3>
						<p>Notifies the user while downloading content in the
							background, and when the download process is complete.</p>
					</div>
					<p class="clearfix"></p>
					<div>
						<h3 class="gray">Analytics</h3>
						<p>Collects analytics to track usage statistics of your
							content, which can be viewed online.</p>
					</div>
					<p class="clearfix"></p>
				</div>
				<div class="large-5 columns">
					<p class="clearfix"></p>
					<h3 class="gray">Features</h3>
					<ul id="vision">
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>Cloud-driven Architecture</span>
							<p class="clearfix"></p>
						</li>
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>Reliable Content Delivery</span>
							<p class="clearfix"></p>
						</li>
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>Say Goodbye to Google Play APK Expansion Files</span>
							<p class="clearfix"></p>
						</li>
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>Enables Continuous Content Updates</span>
							<p class="clearfix"></p>
						</li>
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>No Extra Coding Required</span>
							<p class="clearfix"></p>
						</li>
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>Easily-pluggable &amp; Feature-rich SDK</span>
							<p class="clearfix"></p>
						</li>
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>Mobile Device Storage</span>
							<p class="clearfix"></p>
						</li>
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>Non-Blocking Content Downloads</span>
							<p class="clearfix"></p>
						</li>
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>Manages Content Downloads over Spotty Networks</span>
							<p class="clearfix"></p>
						</li>
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>Download Notifications</span>
							<p class="clearfix"></p>
						</li>
						<li>
							<div>
								<i class="fi-checkbox"></i>
							</div> <span>Analytics to Track Usage Statistics of your Content</span>
							<p class="clearfix"></p>
						</li>
					</ul>
					<br />
				</div>
			</div>
			<div class="clearfix"></div>
			<br>
		</div>
		<div id="more_about_details" class="row full-width"></div>
	</div>
	</section>

	<section id="footer"> <jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>