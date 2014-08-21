
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
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Content Manager</title>
<jsp:include page="resources.jsp" flush="true"></jsp:include>

<!-- Begin Custom -->
<script type="text/javascript" src="/resources/js/cm.application.js"></script>
<!-- End Custom -->


</head>
<body>


	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="header.jsp"></jsp:include>
	<br>
	<jsp:include page="message.jsp"></jsp:include>
	<jsp:include page="breadcrumbs.jsp"></jsp:include>
	<br>
	<div class="row">

		<jsp:include page="left_nav_bar.jsp" flush="true"></jsp:include>

		<div class="large-6 columns" id="content_area">
			<div id="applications_list"></div>
			<!-- Begin Content Group -->
			<div id="application_create" style="display: none">
				<form id="applicationForm" name="applicationForm" data-abide="ajax">
					<fieldset>
						<legend>Application Setup</legend>
						<input type="hidden" id="application_id" name="application_id" />
						<input type="hidden" />

						<div class="row">
							<div class="large-12 columns">
								<div class="name-field">
									<label>Name <small>required</small><input type="text"
										id="application_name" name="application_name"
										required="required" placeholder="My First Application" />
									</label> <small class="error">Name is required</small>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<div class="input-wrapper">
									<label>Description: <textarea rows="5"
											id="application_description" name="application_description"
											placeholder="A short description of the application"></textarea>
									</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<label><span data-tooltip class="has-tip"
									title="Specify if you like the content to be auto-updated to devices over Wi-Fi Only. This is enabled by default.">Update
										content over Wi-Fi only</span></label>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<div class="switch radius">
									<input id="application_update_over_wifi_only" type="checkbox"
										checked="checked"> <label
										for="application_update_over_wifi_only">Update content
										over Wi-Fi only</label>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="large-12 columns">
								<label><span data-tooltip class="has-tip"
									title="Specify if the application is enabled. This application is enabled by default.">Enabled</span></label>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<!-- <input id="application_enabled" type="checkbox"> -->
								<div class="switch radius">
									<input id="application_enabled" type="checkbox"
										checked="checked"> <label for="application_enabled">Enabled</label>
								</div>
							</div>
						</div>
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<span id="application_errors" class="alert radius label"
									style="display: none"></span><br>
							</div>
						</div>
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<button id="application_save_button" class="button radius">create</button>
								<a href="javascript:void(0);" id="application_cancel_button">cancel</a>
							</div>
						</div>
						<div class="row" id="progress_bar" style="display: none">
							<div class="large-12 columns">
								<div class="progress radius">
									<span class="meter" style="width: 40%"></span>
								</div>
							</div>
						</div>
					</fieldset>

				</form>
			</div>
			<!-- End Content Group -->
		</div>

		<!-- Instructions on how to use the page -->
		<aside class="large-3 columns hide-for-small" id="right_aside">
			<p>Place instructions on how to use the page here.</p>
			<dl>
				<dt>Application</dt>
				<dd>Describe what is an application here</dd>
				<dt>Enabled</dt>
				<dd>Describe what is enabling or disabling an application here.</dd>
			</dl>
		</aside>

	</div>

	<br>
	<jsp:include page="footer.jsp" flush="false"></jsp:include>


	<!-- At the bottom of your page but inside of the body tag -->
	<ol class="joyride-list" data-joyride>
		<li data-id="breadcrumb_applications" data-text="Next"
			data-options="tip_location: bottom;timer:2000;tip_animation:fade">
			<p>You will first need to create a Application</p>
		</li>
		<li data-id="left_nav_bar_link_1"
			data-options="tip_location:bottom;tip_animation:fade"
			data-text="Next">
			<p>Click here to create a new Application</p>
		</li>
	</ol>
</body>
</html>