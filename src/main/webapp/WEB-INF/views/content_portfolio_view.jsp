
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
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.content.setup.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.content.js"></script>
<!-- End Custom -->
<script type="text/javascript">
setSelectedApplication(${applicationId});
setSelectedContentGroup(${contentGroupId});
var mDisplayAsGrid = true;

</script>

</head>
<body>
	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>
	<section id="portfolio_page">
		<div class="row full-width">
			<h2 class="text-center gray">Content</h2>

			<p class="text-center page_sub_heading">Manage your rich content
				here</p>
			<!-- 			<div class="line">
				<img src="/resources//resources/images/box/cm/line.png" alt="line" />
			</div>
 -->
			<br />
			<div class="row">
				<div class="large-7 columns">
					<jsp:include page="breadcrumbs.jsp"></jsp:include>
					<div class="clearfix"></div>
					<jsp:include page="progress_bar.jsp"></jsp:include>
					<div id="our_work">
						<div>
							<ul>
								<li class="filter active gray" data-filter="image video">All
									/</li>
								<li class="filter gray" data-filter="image">Images /</li>
								<li class="filter gray" data-filter="video">Videos</li>
							</ul>
							<div class="clearfix"></div>

							<div id="content_list" class="row"></div>

						</div>
					</div>
					<jsp:include page="create_content.jsp"></jsp:include>

				</div>
				<div class="large-4 columns col-md-offset-1">
					<div id="category">
						<h3>Options</h3>
						<ul>
							<li><i class="fi-arrow-right"></i><a id="create_content"
								href="javascript:void(0);">&nbsp;Create New Content</a></li>
							<li><i class="fi-arrow-right"></i><a id="restore_content"
								href="javascript:void(0);">&nbsp;Restore Deleted Content</a></li>
						</ul>
					</div>
					<br> <br />
					<dl class="tabs" data-tab>
						<dd class="active">
							<a id="content_tips" href="#panel2-1">Content Tips</a>
						</dd>
					</dl>
					<div class="tabs-content">
						<div class="content active" id="panel2-1">
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Content Types</span> <span
										class="date">We currently support images and videos</span>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Auto-sizing of Images</span> <span
										class="date">Skok auto-sizes your images, to match the
										screen dimensions of the mobile device, before being
										downloaded. This greatly reduces the storage and memory
										footprint on mobile devices, allows for faster downloads, and
										saves bandwidth utilization.</span>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Tags</span> <span class="date">You
										can tag your content by creating custom tags. The tags are
										used within the SDK, to access this content for display on
										mobile devices. This allows for a lot of flexibility in
										accessing your content. The content can be accessed
										dynamically, instead of accessing it using individual content
										id. Adding or removing tags affects what gets displayed on
										mobile devices, without having to write any additional code.</span>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Start Date</span> <span class="date">A
										future start date will allow you to make the content
										effective, as of that date</span>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">End Date</span> <span class="date">Specify
										an End Date only if you want the content to expire after a
										certain date, or else leave it empty.</span>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Enabled</span> <span class="date">Disabling
										a content will prevent it from being downloaded</span>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Uploading Content</span> <span
										class="date">Drag-n-drop the rich content files to the
										drop location</span>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<br>
	<br>


	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>
	<!-- At the bottom of your page but inside of the body tag -->
	<ol class="joyride-list" data-joyride>
		<li data-id="breadcrumb_content" data-text="Next"
			data-options="tip_location: top; prev_button: false">
			<h4>Product Tour continued...</h4> <br>
			<p>We currently support images and videos</p>
		</li>
		<li data-id="list_view_option" data-text="Next" data-prev-text="Prev">
			<h4>List View</h4> <br>
			<p>Click here to view contents as a List</p>
		</li>
		<li data-id="create_content" data-class="custom so-awesome"
			data-text="Next" data-prev-text="Prev">
			<h4>Create Content</h4> <br>
			<p>Click here to create your rich content.</p>
		</li>
		<li data-id="content_tips" data-text="Next" data-prev-text="Prev">
			<h4>Tips</h4> <br>
			<p>This section provides you information on any questions that
				you might have, when creating a content group.</p>
			<p>For instance, what it means by Enabling a content, or Start
				and End dates.</p>
		</li>
		<li data-id="first_content_id" data-text="Next" data-prev-text="Prev">
			<h4>Content Id</h4> <br>
			<p>Use the content id with the SDK, to access this content for
				display on mobile devices</p>
		</li>
		<li data-button="End" data-prev-text="Prev">
			<h4>Access Content</h4> <br>
			<p>You can click on any image or video to view it.</p>
			<p>Please check out the documentation for more information.</p>
		</li>
	</ol>


	<div class="reveal-modal small" id="move_content_modal" data-reveal>
		<h3 class="gray" id="moveContentModalLabel">Move Content</h3>
		<div class="row">
			<div id="content_group_list"></div>
		</div>
		<button id="move_confirm_button" class="button radius btn-default">move</button>
		<a class="close-reveal-modal">&#215;</a>
	</div>
	<div class="reveal-modal small" id="restore_modal" data-reveal>
		<h3 class="gray" id="modalLabel">Restore Content</h3>
		<div class="row">
			<div id="select_from_deleted_list"></div>
		</div>
		<button id="restore_confirm_button" class="button radius btn-default">restore</button>
		<a class="close-reveal-modal">&#215;</a>
	</div>
	<script>
        $(function () {
            $("#portfolio_page .container").addClass("fadeInUp animated");
            $('#Grid').mixitup(); // needed for our work section
            $(".btn").addClass("bounceInUp animated");
        });
		
		$(document).foundation();
    </script>

</body>
</html>