
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

<title>Skok</title>
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
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.index.js"></script>

<!-- End Custom -->


</head>
<body>
	<jsp:include page="top_bar.jsp"></jsp:include>

	<section id="portfolio"> <br>
	<br>
	<h2 class="text-center">Overview</h2>
	<div class="line">
		<img src="/resources/images/cm/line.png" alt="line" />
	</div>
	<div id="owl-demo" class="owl-carousel owl-theme visible">
		<div class="item">
			<img src="/resources/images/cm/cloud_driven.jpg" alt="owl" />
			<h3 class="gray">Mobile Content Requirements</h3>
			<div class="gray text_left">Mobile is pushing ageing web
				architectures to the brink. The three-tier web architecture
				won&apos;t deliver any more. At the same time, the content
				requirements of Mobile Apps continue to expand. The performance
				unknowns of the last wireless mile has further exacerbated the
				problem of delivering rich content, images and videos, to mobile
				devices.</div>
		</div>

		<div class="item">
			<img src="/resources/images/cm/reliable_platform.jpg" alt="img" />
			<h3 class="gray">Reliable Content Delivery</h3>
			<div class="gray text_left">A delivery tier that manages the
				performance unknowns of the last wireless mile, is key to providing
				great mobile experiences. &lt;&gt; is a content management and
				delivery platform that reliably delivers your rich content to Mobile
				devices. It handles spotty networks, dropped connections, and long
				pauses between service requests. This allows users of your Mobile
				Apps, to engage with your rich content, even if they lose their data
				connection.</div>
		</div>

		<div class="item">
			<img src="/resources/images/cm/expansion_files.png" alt="img" />

			<h3 class="gray">Manage Content as Bite-Sized Chunks</h3>
			<div class="gray text_left">The platform enables you to manage
				and deliver your rich content as bite-sized chunks. It enables you
				to continually update your rich content, with no downtime or
				performance reduction on Mobile or connected devices. You can add,
				update, or remove individual rich content, without degrading the
				mobile experience at any point. In contrast, Google Play restricts
				APK file to 50MB, and constrains you to bundle all your rich content
				into 2 monolithic expansion files. Any updates to your rich content
				requires you to upload a new APK with an updated versionCode in the
				manifest.</div>
			<div class="gray text_left"></div>
		</div>
		<div class="item">
			<img src="/resources/images/cm/continuous_updates.jpg" alt="img" />
			<h3 class="gray">Requires No Extra Coding</h3>
			<div class="gray text_left">The platform manages the download
				and storage of rich content on Mobile devices, without requiring to
				write any code. In contrast, Google Play does not guarantee that the
				expansion files will be downloaded the same time a user downloads
				your application. To handle these situations, your App must be able
				to download the files itself when the main activity starts, using a
				URL provided by Google Play. Google Downloader Library still
				requires a few code hooks to your application.</div>
		</div>

		<div class="item">
			<img src="/resources/images/cm/android_sdk.jpg" alt="img" />
			<h3 class="gray">Easily Pluggable SDK</h3>
			<div class="gray text_left">SDK that provides a simple API to
				access rich content on Mobile or connected devices</div>
		</div>
		<div class="item">
			<img src="/resources/images/cm/android_sdk.jpg" alt="img" />
			<h3 class="gray">Mobile Device Storage</h3>
			<div class="gray text_left">The SDK notifies the user if the
				device does not have enough storage, or if the external storage is
				not accessible.</div>
		</div>
		<div class="item">
			<img src="/resources/images/cm/android_sdk.jpg" alt="img" />
			<h3 class="gray">Downloads are Non-Blocking</h3>
			<div class="gray text_left">File downloads occur in the
				background, in order to avoid blocking the user interaction</div>
		</div>
		<div class="item">
			<img src="/resources/images/cm/android_sdk.jpg" alt="img" />
			<h3 class="gray">Network Connectivity</h3>
			<div class="gray text_left">Network connectivity can change
				during the download. The SDK handles such changes, and if
				interrupted, resumes the download when possible.</div>
		</div>
		<div class="item">
			<img src="/resources/images/cm/android_sdk.jpg" alt="img" />
			<h3 class="gray">Download Notifications</h3>
			<div class="gray text_left">The SDK notifies the user while
				downloading rich content in the background, and when the download
				process is complete.</div>
		</div>
		<div class="item">
			<img src="/resources/images/cm/android_sdk.jpg" alt="img" />
			<h3 class="gray">Analytics</h3>
			<div class="gray text_left">The SDK collects analytics to track
				usage statistics of your rich content, which can be viewed online.</div>
		</div>


	</div>

	</section>
	<br>
	<br>
	<section id="blog">
	<div class="row full-width">
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
	</div>
	</section>
	<section id="footer"> <jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>