
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
<script type="text/javascript" src="/resources/js/cm.content.js"></script>
<!-- End Custom -->
<script type="text/javascript">
setSelectedApplication(${applicationId});
setSelectedContentGroup(${contentGroupId});
</script>

</head>
<body>


	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="header.jsp"></jsp:include>
	<br>
	<jsp:include page="breadcrumbs.jsp"></jsp:include>
	<br>

	<div class="row">

		<jsp:include page="left_nav_bar.jsp" flush="true"></jsp:include>
		<div class="large-6 columns" id="content_area">
			<div class="row">
				<span id="application_name" class="radius label"></span> <span
					id="contentgroup_name" class="radius label"></span>
			</div>
			<div>&nbsp;</div>
			<div id="content_list"></div>
			<!-- Begin Content  -->
			<div id="content_create" style="display: none">
				<form id="contentForm" name="contentForm" data-abide="ajax">
					<fieldset>
						<legend>Content Setup</legend>
						<input type="hidden" id="content_id" name="content_id" /> <input
							type="hidden" id="application_id" name="application_id" /> <input
							type="hidden" id="contentgroup_id" name="contentgroup_id" /><input
							type="hidden" id="content_uri" name="content_uri" /><input
							type="hidden" id="content_type" name="content_type" />

						<div class="row">
							<div class="large-12 columns">
								<div class="name-field">
									<label>Name <small>required</small><input type="text"
										id="content_name" name="content_name" required="required"
										placeholder="Geronimo" />
									</label> <small class="error">Name is required</small>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<div class="input-wrapper">
									<label>Description <textarea rows="5"
											id="content_description" name="content_description"
											placeholder="A short description of the content"></textarea>
									</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<fieldset>
									<legend>Content Type</legend>
									<!-- Using radio buttons each switch turns off the other two -->
									<!-- <label>Image: <input id="content_type_image"
										type="radio" checked name="content_type_group"></label><label>Video:
										<input id="content_type_video" type="radio"
										name="content_type_group">
									</label> -->
									<ul class="inline-list">
										<!-- Using radio buttons each switch turns off the other -->
										<li><fieldset>
												<legend>Image</legend>
												<div class="switch radius">
													<input id="content_type_image" type="radio" checked
														name="content_type_group"> <label
														for="content_type_image">Image</label>
												</div>
											</fieldset></li>

										<li>
											<fieldset>
												<legend>Video</legend>
												<div class="switch radius">
													<input id="content_type_video" type="radio"
														name="content_type_group"> <label
														for="content_type_video">Video</label>
												</div>
											</fieldset>
										</li>
									</ul>
								</fieldset>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<div class="input-wrapper">
									<label>Start Date <small>required</small>
										<div id="content_start_datepicker">
											<input type="text" id="content_start_date"
												name="content_start_date" required="required"
												pattern="month_day_year" /> <br /> <small class="error">Please
												enter a valid start date MM/DD/YYYY</small>
										</div>
									</label>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="large-12 columns">
								<div class="input-wrapper">
									<label><span data-tooltip class="has-tip"
										title="Specify an End Date only if you want the content to expire after a certain date, or else leave it empty.">End
											Date</span>
										<div id="content_end_datepicker">
											<input type="text" id="content_end_date"
												name="content_end_date" pattern="month_day_year" /> <br />
											<small class="error">Please enter a valid end date
												MM/DD/YYYY</small>
										</div> </label>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="large-12 columns">
								<label>Enabled:</label>
							</div>
						</div>
						<!-- 						<div class="row">
							<div class="large-12 columns">
								<input id="content_enabled" type="checkbox">
							</div>
						</div>
 -->
						<div class="row">
							<div class="large-12 columns">
								<div class="switch radius">
									<input id="content_enabled" type="checkbox" checked="checked">
									<label for="content_enabled">Enabled</label>
								</div>
							</div>
						</div>
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<a id="upload_content" href="javascript:void(0);">Click here
									to upload content</a>
								<div id="content_dropbox">
									<span class="secondary radius label">Drop content here
										to upload</span>
								</div>
							</div>
						</div>
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<span id="content_errors" class="alert radius label"
									style="display: none"></span>
							</div>
						</div>
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<button id="content_save_button" class="button radius">create</button>
								<a href="javascript:void(0);" id="content_cancel_button">cancel</a>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<!-- End Content  -->
		</div>
		<!-- Instructions on how to use the page -->
		<aside class="large-3 columns hide-for-small" id="right_aside">
			<p>Place instructions on how to use the page here.</p>
			<dl>
				<dt>Content</dt>
				<dd>Describe what is content here</dd>
				<dt>Content Type</dt>
				<dd>Describe content types here</dd>
				<dt>Start Date</dt>
				<dd>...</dd>
				<dt>End Date</dt>
				<dd>...</dd>
				<dt>Enabled</dt>
				<dd>Describe what is enabling or disabling a content group
					here.</dd>
				<dt>Uploading Content</dt>
				<dd>Describe how to upload content here.</dd>
			</dl>
		</aside>


	</div>

	<br>
	<jsp:include page="footer.jsp" flush="false"></jsp:include>


	<!-- At the bottom of your page but inside of the body tag -->
	<ol class="joyride-list" data-joyride>
		<li data-id="left_nav_bar_link_1"
			data-options="tip_location:bottom;tip_animation:fade" data-text="End">
			<p>Click here to create a new Content</p>
		</li>
	</ol>
</body>
</html>