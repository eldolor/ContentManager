
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
	src="/resources/javascripts/cm/cm.application.setup.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.application.js"></script>
<!-- End Custom -->


</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>

	<section id="blog">
		<div class="row full-width">

			<h2 class="text-center gray">Documentation</h2>

			<!-- <p class="text-center page_sub_heading">Create an application to
				manage its associated rich media content (images &amp; videos)</p> -->
			<div class="line">
				<img src="/resources/images/box/line.png" alt="line" />
			</div>
			<br />
			<div class="row">
				<div class="large-12 columns">
					<div data-magellan-expedition="fixed">
						<dl class="sub-nav">
							<dd data-magellan-arrival="overview">
								<a href="#overview">Overview</a>
							</dd>
							<dd data-magellan-arrival="getting_started">
								<a href="#getting_started">Getting Started</a>
							</dd>
							<dd data-magellan-arrival="project_setup">
								<a href="#project_setup">Project Setup</a>
							</dd>
							<dd data-magellan-arrival="initialize">
								<a href="#initialize">Initialize</a>
							</dd>
							<dd data-magellan-arrival="access_contents">
								<a href="#access_contents">Access Contents</a>
							</dd>
							<dd data-magellan-arrival="advanced">
								<a href="#advanced">Advanced Topics</a>
							</dd>
						</dl>
					</div>
					<div class="clearfix"></div>
					<a name="overview"></a>
					<h3 data-magellan-destination="overview" class="text-left gray">Overview</h3>
					<p class="text-left">The Content Manager API synchronizes your
						content on mobile devices. Any content updates can be pushed down
						to mobile devices, almost instantaneously.</p>
					<p class="text-left">
						The API also collects <a href="/analytics/applications">Usage
							Statistics</a> of your content, which can be viewed online.
					</p>
					<div class="clearfix"></div>
					<div class="clearfix"></div>
					<a name="getting_started"></a>
					<h3 data-magellan-destination="getting_started"
						class="text-left gray">Getting Started</h3>
					<p class="text-left">You need the following to get started with
						a new Android project.</p>
					<ul>
						<li>Java Development Kit (JDK) 1.6 or higher.</li>
						<li>Android SDK: Android 3.2 Honeycomb (API level 13) or
							higher.</li>
						<li>Java IDE such as Eclipse or Android Studio, with ADT
							installed.</li>
					</ul>
					<div class="clearfix"></div>
					<div class="clearfix"></div>
					<a name="project_setup"></a>
					<h3 data-magellan-destination="project_setup"
						class="text-left gray">Eclipse Project Setup</h3>
					<ol>
						<li>Download the latest <a href="#">Content Manager API</a>
							library.
						</li>
						<li>Copy the <b>API library</b> to the <b>libs</b> folder of
							your project.
						</li>
						<li>Check your project properties to ensure that the <b>API
								library</b> is included in your project&quot;s <b>Android
								Private Libraries.</b></li>
					</ol>
					<div class="clearfix"></div>
					<div class="clearfix"></div>
					<a name="initialize"></a>
					<h3 data-magellan-destination="initialize" class="text-left gray">Initialize
						the Content Manager Factory</h3>
					<p class="text-left"></p>
					<div class="panel radius">
						<h4></h4>
						<p>Initialize the Content Manager factory in the onCreate()
							method of your Android View or Fragment. This will automatically
							register the Content Manager Client, and start downloading the
							contents.</p>
						<pre>
	Private ContentManager mContentManager;
	...
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		...
		try {
			mContentManager = ContentManagerFactory.getInstance(this, your_application_id);
		} catch (ExternalStorageNotReadyException e) {
			Logger.error(e.getMessage());
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}
</pre>
					</div>


					<div class="clearfix"></div>
					<div class="clearfix"></div>
					<a name="access_contents"></a>
					<h3 data-magellan-destination="access_contents"
						class="text-left gray">Access Contents</h3>
					<p class="text-left"></p>
					<div class="panel radius">
						<h4>ContentManager</h4>
						<p>The ContentManager class provides several different methods
							to access your contents.</p>
						<p>
							<b>getAllContents()</b> - to access all of your contents
						</p>
						<pre>
	Private ContentManager mContentManager;
	private List<Content> mContentList;
	...
	private void loadContent() {
		Logger.log("Entering");

		mContentList = mContentManager.getAllContents();

		Logger.log("Exiting");
	}

						
						
						
						</pre>

						<p>
							<b>getImage(contentId) or getVideo(contentId)</b> - to access a
							specific image or video content
						</p>
						<pre>
	Private ContentManager mContentManager;
	...
	private void loadContent() {
		Logger.log("Entering");

		Bitmap lImageBitmap = mContentManager.getImage(1234);
		
		Uri lVideoUri = mContentManager.getVideo(5678);

		Logger.log("Exiting");
	}
	
	
</pre>
						<p>
							<b>Random Access: getAnyImage() or getAnyVideo()</b> - to access
							any random image or video content
						</p>
						<pre>
	Private ContentManager mContentManager;
	...
	private void loadContent() {
		Logger.log("Entering");

		Bitmap lImageBitmap = mContentManager.getAnyImage();
		
		Uri lVideoUri = mContentManager.getAnyVideo();

		Logger.log("Exiting");
	}
	
	
</pre>
						<p>
							<b>Random Access: getAnyImageFromContentGroup(contentGroupId)
								or getAnyVideoFromContentGroup(contentGroupId)</b> - to access any
							random image or video content, from within a specific Content
							Group
						</p>
						<pre>
	Private ContentManager mContentManager;
	...
	private void loadContent() {
		Logger.log("Entering");

		Bitmap lImageBitmap = mContentManager.getAnyImageFromContentGroup(9876);
		
		Uri lVideoUri = mContentManager.getAnyVideoFromContentGroup(5432);

		Logger.log("Exiting");
	}
	
	
</pre>
						<p>
							<b>Utility Methods: getScaledImage(contentId, newWidth) or
								getAnyScaledImageFromContentGroup(contentGroupId, newWidth) or
								getAnyScaledImage(newWidth)</b> - to access any random image that
							has been scaled to the new size
						</p>
						<pre>
	Private ContentManager mContentManager;
	...
	private void loadContent() {
		Logger.log("Entering");

		Bitmap lScaledBitmap = mContentManager.getScaledImage(9876, 200);
		
		Bitmap lScaledBitmapFromContentGroup = mContentManager.getAnyScaledImageFromContentGroup(4280, 200);

		Bitmap lScaledBitmapAny = mContentManager.getAnyScaledImage(200);

		Logger.log("Exiting");
	}
	
	
</pre>
						<p>
							<b>Utility Methods to create Thumbnails: createThumbnail(long
								contentId, ThumbnailTypeVideo thumbnailTypeVideo) or
								createThumbnail(long contentId, ThumbnailTypeImage
								thumbnailTypeImage) 
						</p>
						<pre>
	Private ContentManager mContentManager;
	...
	private void loadContent() {
		Logger.log("Entering");

		Bitmap lImageThumbnail = mContentManager.createThumbnail(9876, ThumbnailTypeImage.THUMBNAIL_MICRO_KIND);
		
		Bitmap lVideoThumbnail = mContentManager.createThumbnail(4280, ThumbnailTypeVideo.THUMBNAIL_MICRO_KIND);

		Bitmap lScaledBitmapAny = mContentManager.getAnyScaledImage(200);

		Logger.log("Exiting");
	}
	
	
</pre>
					</div>
					<a name="advanced"></a>
					<h3 data-magellan-destination="overview" class="text-left gray">Advanced
						Topics</h3>
					<div class="panel radius">
						<h4>Notifications</h4>
						<p></p>
						<pre>
							</pre>
					</div>

				</div>
				<!-- end 12 columns -->
			</div>
	</section>
	<br>
	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>