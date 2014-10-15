
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
	<jsp:include page="message.jsp"></jsp:include>
	<section id="blog">
		<div class="row full-width">

			<h2 class="text-center gray">Applications</h2>

			<p class="text-center page_sub_heading">Create an application to
				manage your rich content</p>
			<div class="line">
				<img src="/resources/images/box/line.png" alt="line" />
			</div>
			<br />
			<div class="row">
				<div class="large-7 columns">
					<jsp:include page="breadcrumbs.jsp"></jsp:include>
					<div class="clearfix"></div>
					<jsp:include page="progress_bar.jsp"></jsp:include>
					<div id="applications_list"></div>
					<jsp:include page="create_application.jsp"></jsp:include>

				</div>
				<div class="large-4 columns col-md-offset-1">
					<div id="category">
						<h3>Options</h3>
						<ul>
							<li><i class="fi-arrow-right"></i><a id="create_application"
								href="javascript:void(0);">&nbsp;Create New Application</a></li>
						</ul>
					</div>
					<br> <br />
					<dl class="tabs" data-tab>
						<dd class="active">
							<a id="application_tips" href="#panel2-1">Application Tips</a>
						</dd>
					</dl>
					<div class="tabs-content">
						<div class="content active" id="panel2-1">
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Wi-Fi</span> <span class="date">You
										can restrict the rich content to be auto-updated to devices
										over Wi-Fi Only. This helps conserve the cellular data usage.
										It is enabled by default.</span>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="blog_snippet">
								<div class="blog_details float_left">
									<span class="title green">Enabled</span> <span class="date">Disabling
										an application will prevent all the rich content within the
										application, from being downloaded</span>
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
		<li data-id="breadcrumb_applications" data-text="Next"
			data-options="tip_location: top; prev_button: false">
			<h4>Product Tour</h4> <br>
			<p>Content is organized by Applications.</p>
			<p>
				<input id="product_tour" type="checkbox"
					onclick="javascript: setProductTour();">&nbsp;Do not show
				this to me again.
			</p>
		</li>
		<li data-id="create_application" data-text="Next"
			data-prev-text="Prev">
			<h4>Create an Application</h4> <br>
			<p>To start managing your content, you first need to create an
				application.</p>
		</li>
		<li data-id="application_tips" data-text="Next" data-prev-text="Prev">
			<h4>Tips</h4> <br>
			<p>This section provides you information on any questions that
				you might have, when creating an application.</p>
			<p>For instance, what it means by Enabling an application, or
				Updating content over Wi-Fi.</p>
		</li>
		<li data-id="first_application_id" data-text="Next"
			data-prev-text="Prev">
			<h4>Content Id</h4> <br>
			<p>Use the application id to initialize the SDK, and to access
				any content for display on mobile devices</p>
		</li>
		<li data-id="first_contentgroup"
			data-text="Click on &apos;content groups&apos; to continue"
			data-prev-text="Prev" data-options="next_button: false;">
			<h4>Access Content Groups</h4> <br>
			<p>You can access all the content groups within this application
				here.</p>
		</li>
	</ol>
</body>
</html>