
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
<!-- End Custom -->


</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>

	<section id="blog">
	<div class="row full-width">

		<h2 class="text-center gray">SDK Guide</h2>

<!-- 		<div class="line">
			<img src="/resources/images/cm/line.png" alt="line" />
		</div>
 -->		<br />
		<div class="row">
			<div class="large-12 columns">
				<div data-magellan-expedition="fixed">
					<dl class="sub-nav">
						<dd data-magellan-arrival="getting_started">
							<a href="#getting_started">Getting Started</a>
						</dd>
						<dd data-magellan-arrival="android_requirements">
							<a href="#android_requirements">Android Requirements</a>
						</dd>
						<dd data-magellan-arrival="project_setup">
							<a href="#project_setup">Project Setup</a>
						</dd>
						<dd data-magellan-arrival="android_manifest">
							<a href="#android_manifest">Android Manifest</a>
						</dd>
						<dd data-magellan-arrival="downloader_service">
							<a href="#downloader_service">Downloader Service</a>
						</dd>
						<dd data-magellan-arrival="initialize">
							<a href="#initialize">Initialize</a>
						</dd>
						<dd data-magellan-arrival="access_contents">
							<a href="#access_contents">Access Contents</a>
						</dd>
					</dl>
				</div>
				<div class="clearfix"></div>
				<h3 data-magellan-destination="getting_started"
					class="text-left gray">Getting Started</h3>
				<a name="getting_started"></a>
				<ol>
					<li>Create an <a href="/applications">Application</a> on the
						platform, and upload your content.
					</li>
					<li>Create a <a href="/account/clientkeys">Client Key</a>. We
						highly recommend that you create a new Client Key for each
						Application.
					</li>
				</ol>
				<div class="clearfix"></div>
				<div class="clearfix"></div>
				<h3 data-magellan-destination="android_requirements"
					class="text-left gray">Android Requirements</h3>
				<a name="getting_started"></a>
				<p class="text-left">You need the following to get started with
					a new Android project.</p>
				<ol>
					<li>Java Development Kit (JDK) 1.6 or higher.</li>
					<li>Android SDK: Android 3.2 Honeycomb (API level 13) or
						higher.</li>
					<li>Java IDE such as Eclipse or Android Studio, with ADT
						installed.</li>
					<li>Google account configured on the mobile device. Content
						synchronization is performed over Google Cloud Messaging, which
						requires a Google account.</li>
					<li>Google Play Store installed on the mobile device.</li>
				</ol>
				<div class="clearfix"></div>
				<div class="clearfix"></div>
				<h3 data-magellan-destination="project_setup" class="text-left gray">Eclipse
					Project Setup</h3>
				<a name="project_setup"></a>
				<ol>
					<li>Download the latest Android <a
						href="/resources/api/current/skok_sdk_1_1.jar">Skok API</a> library.
					</li>
					<li><a href="/resources/api/current/javadoc/index.html"
						target="_blank">Android API Reference</a></li>
					<li>Copy the <b>API library</b> to the <b>libs</b> folder of
						your project.
					</li>
					<li>Check your project properties to ensure that the <b>API
							library</b> is included in your project&quot;s <b>Android Private
							Libraries.</b></li>
				</ol>
				<div class="clearfix"></div>
				<div class="clearfix"></div>
				<h3 data-magellan-destination="android_manifest"
					class="text-left gray">Android Manifest</h3>
				<div class="panel radius">
					<a name="android_manifest"></a>
					<h4>Add Permissions</h4>
					<p>Add the following permissions to your project&quot;s
						AndroidManifest.xml</p>
					<pre>

    &lt;!-- Begin: SDK related --&gt;
    &lt;!-- Required to download files --&gt;
   <code>&lt;uses-permission android:name="android.permission.INTERNET" /&gt;</code>
    &lt;!-- Content synchronization is performed over Google Cloud Messaging, which requires a Google account --&gt;
   <code>&lt;uses-permission android:name="android.permission.GET_ACCOUNTS" /&gt;</code>
    &lt;!-- Required to keep CPU alive while downloading files (NOT to keep screen awake) --&gt;
    <code>&lt;uses-permission android:name="android.permission.WAKE_LOCK" /&gt;
    &lt;uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" /&gt;</code>

    &lt;!-- An applicationPackage + ".permission.C2D_MESSAGE" permission to prevent other Android applications 
        from registering and receiving the Android application's messages. The permission name must exactly match 
        this pattern otherwise the Android application will not receive the messages. --&gt;
   <code>&lt;permission
        android:name="com.cm.contentmanagerdemo.permission.C2D_MESSAGE"
        android:protectionLevel="signature" /&gt;

    &lt;uses-permission android:name="com.cm.contentmanagerdemo.permission.C2D_MESSAGE" /&gt;</code>

    &lt;!-- Required to read and write the content on shared storage --&gt;
   <code>&lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /&gt;</code>
    &lt;!-- Required to capture the usage statistics of the content --&gt;
    <code>&lt;uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /&gt;
    &lt;uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" /&gt;</code>

    &lt;!-- Required to poll the state of the network connection  and respond to changes --&gt;
    <code>&lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /&gt;</code>

    &lt;!-- Required to check whether Wi-Fi is enabled --&gt;
    <code>&lt;uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /&gt; 
    &lt;uses-permission android:name="android.permission.READ_PHONE_STATE" /&gt;</code>
    &lt;!-- End: SDK related --&gt;

					</pre>
				</div>
				<div class="panel radius">
					<h4>Configure the Downloader Service</h4>
					<p>Add the following to the &lt;application/&gt; node of your
						project&quot;s AndroidManifest.xml</p>
					<p>
						<b>NOTE: </b> The &quot;category&quot; name of <b>com.google.android.c2dm.intent.RECEIVE</b>
						action must match the value in the &quot;package&quot; attribute,
						defined in the &lt;manifest/&gt; element
					</p>
					<pre>

    &lt;!-- Begin: SDK related --&gt;
    <code>&lt;meta-data android:name="com.google.android.gms.version"
	android:value="@integer/google_play_services_version" /&gt;</code>
    &lt;!-- A receiver with the category set as applicationPackage.  The receiver should 
        require  the com.google.android.c2dm.SEND permission --&gt;
    <code>&lt;receiver android:name="com.cm.contentmanager.android.api.download.DownloaderBroadcastReceiver"
	android:permission="com.google.android.c2dm.permission.SEND"&gt;
        &lt;intent-filter&gt;
            &lt;action android:name="com.google.android.c2dm.intent.RECEIVE" /&gt;
            &lt;category android:name="my_android_application_package_name" /&gt;</code>
            &lt;!-- The &quot;category&quot; name of <b>com.google.android.c2dm.intent.RECEIVE</b>
						action must match the value in the &quot;package&quot; attribute,
						defined in the &lt;manifest/&gt; element --&gt;
             
       <code>&lt;/intent-filter&gt;
        &lt;intent-filter&gt;
            &lt;action android:name="android.net.conn.CONNECTIVITY_CHANGE" /&gt;
        &lt;/intent-filter&gt;
    &lt;/receiver&gt;</code>

    &lt;!-- Handles downloading of content --&gt;
    <code>&lt;service android:name="com.cm.contentmanager.android.api.download.DownloaderService" android:exported="true" /&gt;</code>
    &lt;!-- End: SDK related --&gt;

					</pre>
				</div>
				<div class="clearfix"></div>
				<div class="clearfix"></div>
				<h3 data-magellan-destination="downloader_service"
					class="text-left gray">Downloader Service</h3>
				<a name="downloader_service"></a>
				<div class="panel radius">
					<h4>Notifications</h4>
					<p>The Downloader Service will display any of the following
						Notifications</p>
					<p>
						<b>NOTE: </b>If the mobile device does not have either Network or
						Wi-Fi connectivity, the download process will pause and resume
						automatically, once the device regains connectivity.
					</p>
					<ol>
						<li>SD card is not accessible</li>
						<li>The device does not have enough storage</li>
						<li>The device is not connected to the Internet</li>
						<li>Waiting for a network connection</li>
						<li>The device is not connected to a Wi-Fi Network</li>
						<li>Waiting for a Wi-Fi connection</li>
						<li>Google Play Store is not installed</li>
						<li>Unable to validate the credentials of the Google Account
							on the device</li>
						<li>Download in progress</li>
						<li>Download complete</li>
						<li>Only some of the files were downloaded</li>
						<li>Unable to download</li>
						<li>Unable to download even after several retries</li>
					</ol>
				</div>
				<div class="panel radius">
					<h4>Status Codes</h4>
					<p>The Downloader Service broadcasts the result of download
						process. You can configure a Broadcast Receiver, to receive, and
						to process the result.</p>
					<p>For instance, if the Downloader Service is unable to
						download the contents on account of Wi-Fi being disabled, it will
						display a Wi-Fi Disconnected Notification on the Notifications
						Bar, and respond with the appropriate status code. For better user
						experience, you can process the status code, and prompt the user
						to enable Wi-Fi.</p>
					<p>The Downloader Service will broadcast any of the following
						status codes.</p>
					<ol>
						<li>DOWNLOAD_FAILED_REASON_UNKNOWN</li>
						<li>FAILED</li>
						<li>PARTIAL_SUCCESS</li>
						<li>SUCCESS</li>
						<li>BAD_REQUEST</li>
						<li>NETWORK_DISCONNECTED</li>
						<li>WIFI_DISCONNECTED</li>
						<li>INSUFFICIENT_STORAGE</li>
						<li>REQUEST_FAILED_AFTER_RETRIES</li>
						<li>EXTERNAL_STORAGE_NOT_READY</li>
					</ol>
					<p>Configure your Broadcast Receiver with the following intent
						filter, to receive the Status Codes from the Downloader Service.</p>
					<pre>
<code>
        &lt;receiver android:name="com.cm.contentmanagerdemo.DemoBroadcastReceiver"&gt;
            &lt;intent-filter&gt;
                &lt;action android:name="com.cm.contentmanager.android.api.download.DownloaderService.RESULT" /&gt;
            &lt;/intent-filter&gt;
        &lt;/receiver&gt;
</code>
					</pre>
				</div>

				<div class="panel radius">
					<h4>Status Codes</h4>
					<p>Configure your Broadcast Receiver to process the results</p>
					<pre>
<code>
	public class DemoBroadcastReceiver extends WakefulBroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			try {	
				if (intent.getAction().equalsIgnoreCase(DownloaderService.RESULT)) {
					// Depending on the result code, you can trigger application
					// specific actions.
					int lStatusCode = intent.getIntExtra(
							DownloaderService.STATUS_CODE, 0);
					switch (lStatusCode) {
					case StatusCode.SUCCESS:
						//do something
					case StatusCode.ABORTED:
						//do something
					case StatusCode.BAD_REQUEST:
						//do something
					case StatusCode.CANCELLED:
						//do something
					case StatusCode.DOWNLOAD_FAILED_REASON_UNKNOWN:
						//do something
					case StatusCode.EXTERNAL_STORAGE_NOT_READY:
						//do something
					case StatusCode.FAILED:
						//do something
					case StatusCode.INSUFFICIENT_STORAGE:
						//do something
					case StatusCode.NETWORK_DISCONNECTED:
						//do something
					case StatusCode.PARTIAL_SUCCESS:
						//do something
					case StatusCode.REQUEST_FAILED_AFTER_RETRIES:
						//do something
					case StatusCode.WIFI_DISCONNECTED:
						//do something
					}
				}
			} finally {
				//
			}
	
		}
	}
</code>
					</pre>
				</div>
				<div class="clearfix"></div>
				<div class="clearfix"></div>
				<h3 data-magellan-destination="initialize" class="text-left gray">Initialize
					the Content Manager Factory</h3>
				<a name="initialize"></a>
				<p class="text-left"></p>
				<div class="panel radius">
					<h4></h4>
					<p>Initialize the Content Manager factory in the onCreate()
						method of your Android View or Fragment. If this the first time,
						this will automatically register the Content Manager Client, and
						start downloading the contents, in the background.</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	...
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		...
		mContentManager = ContentManagerFactory.getInstance(this, "your_client_key", "your_application_id");
	}
</code>
					</pre>
				</div>


				<div class="clearfix"></div>
				<div class="clearfix"></div>
				<h3 data-magellan-destination="access_contents"
					class="text-left gray">Access Contents</h3>
				<a name="access_contents"></a>
				<p class="text-left"></p>
				<div class="panel radius">
					<h4>ContentManager</h4>
					<p>The ContentManager class provides several different methods
						to access your contents.</p>
				</div>
				<div class="panel radius">
					<h4>getAllContentsByTag(String tag)</h4>
					<p>To get a list of all your contents that have a specific tag.
					</p>
					<p>Contents are tagged on the server. Tags allows for dynamic
						access of content, instead of accessing content by content id.</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	private List<Content> mContentList;
	...
	private void loadContent() {

		mContentList = mContentManager.getAllContentsByTag("your_tag");
		
		//Iterate through the content list
		Content lContent = mContentList.get(position);
		long lContentId = lContent.getId();
		
		if (lContent.getType().equals(ContentType.IMAGE.getType())) {

			//For each image
			ImageView lImage = (ImageView) mRootView.findViewById(R.id.content_image);
			Bitmap lBitmap = mContentManager.getScaledImage(lContentId, width);
			lImage.setImageBitmap(lBitmap);

		} else if (mContent.getType().equals(ContentType.VIDEO.getType())) {
			
			//For each video
			VideoView lVideoView = (VideoView) mRootView.findViewById(R.id.content_video);
			Uri lUri = mContentManager.getVideo(lContentId);
			lVideoView.setVideoURI(lUri);

		}
		
	}

						
						
						
						</code>
					</pre>
				</div>

				<div class="panel radius">
					<h4>getAllContentsByType(ContentType contentType)</h4>
					<p>To get a list of all your contents by type. This is for
						instances where you would want to display all of the contents in a
						list or grid.</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	private List<Content> mContentList;
	...
	private void loadContent() {

		mContentList = mContentManager.getAllContentsByType(ContentType.IMAGE);
		
		//Iterate through the content list
		Content lContent = mContentList.get(position);
		long lContentId = lContent.getId();
		
		if (lContent.getType().equals(ContentType.IMAGE.getType())) {

			//For each image
			ImageView lImage = (ImageView) mRootView.findViewById(R.id.content_image);
			Bitmap lBitmap = mContentManager.getScaledImage(lContentId, width);
			lImage.setImageBitmap(lBitmap);

		} else if (mContent.getType().equals(ContentType.VIDEO.getType())) {
			
			//For each video
			VideoView lVideoView = (VideoView) mRootView.findViewById(R.id.content_video);
			Uri lUri = mContentManager.getVideo(lContentId);
			lVideoView.setVideoURI(lUri);

		}

	}

						
						
						
						</code>
					</pre>
				</div>
				<div class="panel radius">
					<h4>getAllContents()</h4>
					<p>To get a list of all your contents. This is for instances
						where you would want to display all of the contents in a list or
						grid.</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	private List<Content> mContentList;
	...
	private void loadContent() {

		mContentList = mContentManager.getAllContents();
		
		//Iterate through the content list
		Content lContent = mContentList.get(position);
		long lContentId = lContent.getId();
		
		if (lContent.getType().equals(ContentType.IMAGE.getType())) {

			//For each image
			ImageView lImage = (ImageView) mRootView.findViewById(R.id.content_image);
			Bitmap lBitmap = mContentManager.getScaledImage(lContentId, width);
			lImage.setImageBitmap(lBitmap);

		} else if (mContent.getType().equals(ContentType.VIDEO.getType())) {
			
			//For each video
			VideoView lVideoView = (VideoView) mRootView.findViewById(R.id.content_video);
			Uri lUri = mContentManager.getVideo(lContentId);
			lVideoView.setVideoURI(lUri);

		}

	}

						
						
						
						</code>
					</pre>
				</div>

				<div class="panel radius">
					<h4>getAllContentsByType(ContentType contentType)</h4>
					<p>To get a list of all your contents by type. This is for
						instances where you would want to display all of the contents in a
						list or grid.</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	private List<Content> mContentList;
	...
	private void loadContent() {

		mContentList = mContentManager.getAllContentsByType(ContentType.IMAGE);
		
		//Iterate through the content list
		Content lContent = mContentList.get(position);
		long lContentId = lContent.getId();
		
		//For each image
		ImageView lImage = (ImageView) mRootView.findViewById(R.id.content_image);
		Bitmap lBitmap = mContentManager.getScaledImage(lContentId, width);
		lImage.setImageBitmap(lBitmap);
		
	}

						
						
						
						</code>
					</pre>
				</div>


				<div class="panel radius">
					<h4>getAllContentsFromContentGroup(long contentGroupId)</h4>
					<p>To get a list of all your contents from a content group.
						This is for instances where you would want to display all of the
						contents in a list or grid.</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	private List<Content> mContentList;
	...
	private void loadContent() {

		mContentList = mContentManager.getAllContentsFromContentGroup(lContentGroupId);
		
		//Iterate through the content list
		Content lContent = mContentList.get(position);
		long lContentId = lContent.getId();
		
		if (lContent.getType().equals(ContentType.IMAGE.getType())) {

			//For each image
			ImageView lImage = (ImageView) mRootView.findViewById(R.id.content_image);
			Bitmap lBitmap = mContentManager.getScaledImage(lContentId, width);
			lImage.setImageBitmap(lBitmap);

		} else if (mContent.getType().equals(ContentType.VIDEO.getType())) {
			
			//For each video
			VideoView lVideoView = (VideoView) mRootView.findViewById(R.id.content_video);
			Uri lUri = mContentManager.getVideo(lContentId);
			lVideoView.setVideoURI(lUri);

		}

	}

						
						
						
						</code>
					</pre>
				</div>
				<div class="panel radius">
					<h4>getAllContentsFromContentGroupByType( long contentGroupId,
						ContentType contentType)</h4>
					<p>To get a list of all your contents from a content group.
						This is for instances where you would want to display all of the
						contents in a list or grid.</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	private List<Content> mContentList;
	...
	private void loadContent() {

		mContentList = mContentManager.getAllContentsFromContentGroupByType(lContentGroupId,
								ContentType.IMAGE);
		
		//Iterate through the content list
		Content lContent = mContentList.get(position);
		long lContentId = lContent.getId();
		
		//For each image
		ImageView lImage = (ImageView) mRootView.findViewById(R.id.content_image);
		Bitmap lBitmap = mContentManager.getScaledImage(lContentId, width);
		lImage.setImageBitmap(lBitmap);
		

	}

						
						
						
						</code>
					</pre>
				</div>
				<div class="panel radius">
					<h4>getContent(long contentId)</h4>
					<p>To access a specific content</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	...
	private void loadContent() {

		Content lContent = getContent(lContentId);
		
		if (lContent.getType().equals(ContentType.IMAGE.getType())) {

			//For each image
			ImageView lImage = (ImageView) mRootView.findViewById(R.id.content_image);
			Bitmap lBitmap = mContentManager.getScaledImage(lContentId, width);
			lImage.setImageBitmap(lBitmap);

		} else if (mContent.getType().equals(ContentType.VIDEO.getType())) {
			
			//For each video
			VideoView lVideoView = (VideoView) mRootView.findViewById(R.id.content_video);
			Uri lUri = mContentManager.getVideo(lContentId);
			lVideoView.setVideoURI(lUri);

		}

	}
</code>
					</pre>
				</div>

				<div class="panel radius">
					<h4>getImage(long contentId)</h4>
					<h4>getVideo(long contentId)</h4>
					<p>To access a specific image or video content</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	...
	private void loadContent() {

		Bitmap lImageBitmap = mContentManager.getImage(1234);
		
		Uri lVideoUri = mContentManager.getVideo(5678);

	}
</code>
					</pre>
				</div>
				<div class="panel radius">
					<h4>getAnyImage()</h4>
					<h4>getAnyVideo()</h4>
					<p>To access any random image or video content</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	...
	private void loadContent() {

		Bitmap lImageBitmap = mContentManager.getAnyImage();
		
		Uri lVideoUri = mContentManager.getAnyVideo();

	}
</code>
					</pre>
				</div>
				<div class="panel radius">
					<h4>getAnyImageFromContentGroup(long contentGroupId)</h4>
					<h4>getAnyVideoFromContentGroup(long contentGroupId)</h4>
					<p>To access any random image or video content, from within a
						specific Content Group</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	...
	private void loadContent() {

		Bitmap lImageBitmap = mContentManager.getAnyImageFromContentGroup(9876);
		
		Uri lVideoUri = mContentManager.getAnyVideoFromContentGroup(5432);

	}
</code>
					</pre>
				</div>
				<div class="panel radius">
					<h4>getScaledImage(long contentId, int newWidth)</h4>
					<h4>getAnyScaledImageFromContentGroup(long contentGroupId, int
						newWidth)</h4>
					<h4>getAnyScaledImage(newWidth)</h4>
					<p>Utility methods to access any random image that has been
						scaled to the new size</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	...
	private void loadContent() {

		Bitmap lScaledBitmap = mContentManager.getScaledImage(9876, 200);
		
		Bitmap lScaledBitmapFromContentGroup = mContentManager.getAnyScaledImageFromContentGroup(4280, 200);

		Bitmap lScaledBitmapAny = mContentManager.getAnyScaledImage(200);

	}
</code>
					</pre>
				</div>
				<div class="panel radius">
					<h4>createThumbnail(long contentId, ThumbnailTypeVideo
						thumbnailTypeVideo)</h4>
					<h4>createThumbnail(long contentId, ThumbnailTypeImage
						thumbnailTypeImage)</h4>
					<h4>createThumbnail(String filePath, ThumbnailTypeVideo
						thumbnailTypeVideo)</h4>
					<h4>createThumbnail(String filePath, ThumbnailTypeImage
						thumbnailTypeImage)</h4>
					<p>Utility Methods to create Thumbnails for images and videos</p>
					<pre>
<code>
	Private ContentManager mContentManager;
	...
	private void loadContent() {

		Bitmap lImageThumbnail = mContentManager.createThumbnail(9876, ThumbnailTypeImage.THUMBNAIL_MICRO_KIND);
		
		Bitmap lVideoThumbnail = mContentManager.createThumbnail(4280, ThumbnailTypeVideo.THUMBNAIL_MICRO_KIND);

		Bitmap lScaledBitmapAny = mContentManager.getAnyScaledImage(200);

	}
</code>
					</pre>
				</div>

			</div>
			<!-- end 12 columns -->
		</div>
	</div>
	</section>
	<br>
	<br>
	<section id="footer"> <jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>