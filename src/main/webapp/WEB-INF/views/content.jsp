
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

		<div class="large-9 push-3 columns" id="content_area">
			<div class="row">
				<span id="application_name" class="secondary radius label"></span>
				<span id="contentgroup_name" class="secondary radius label"></span>
			</div>
			<div>&nbsp;</div>
			<div id="content_list"></div>
			<!-- Begin Content  -->
			<div id="content_create" style="display: none">
				<h3 id="contentModalLabel">Content Setup</h3>
				<form id="contentForm" name="contentForm">
					<input type="hidden" id="content_id" name="content_id" /> <input
						type="hidden" id="application_id" name="application_id" /> <input
						type="hidden" id="contentgroup_id" name="contentgroup_id" /><input
						type="hidden" id="content_uri" name="content_uri" /><input
						type="hidden" id="content_type" name="content_type" />

					<div class="row">
						<div class="large-12 columns">
							<label>Name: <input type="text" id="content_name"
								name="content_name" />
							</label>
						</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<label>Description: <textarea rows="5"
									id="content_description" name="content_description"></textarea>
							</label>
						</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<label>Content Type:</label>
						</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<!-- Using radio buttons each switch turns off the other two -->
							<label>Image: <input id="content_type_image" type="radio"
								checked name="content_type_group"></label><label>Video:
								<input id="content_type_video" type="radio"
								name="content_type_group">
							</label>
						</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<label>Start Date:
								<div id="content_start_datepicker">
									<input type="text" id="content_start_date"
										name="content_start_date" /> <br />
								</div>
							</label>
						</div>
					</div>

					<div class="row">
						<div class="large-12 columns">
							<label><span data-tooltip class="has-tip"
								title="Specify an End Date only if you want the content to expire after a certain date, or else leave it empty.">End
									Date:</span>
								<div id="content_end_datepicker">
									<input type="text" id="content_end_date"
										name="content_end_date" /> <br />
								</div> </label>
						</div>
					</div>

					<div class="row">
						<div class="large-12 columns">
							<label>Enabled:</label>
						</div>
					</div>
					<div class="row">
						<div class="large-12 columns">
							<input id="content_enabled" type="checkbox">
						</div>
					</div>
					<!-- 					<div class="row">
						<div class="large-12 columns">
							<div class="switch radius">
								<input id="content_enabled" type="checkbox" checked="checked">
								<label for="content_enabled">Enabled</label>
							</div>
						</div>
					</div> -->
					<div class="row">
						<div class="large-12 columns">
							<a id="upload_content" href="javascript:void(0);">Click here
								to upload content</a>
							<div id="content_dropbox">
								<span class="secondary radius label">Drop content here to
									upload</span>
							</div>
						</div>
					</div>

				</form>
				<button id="content_save_button" class="button radius">create</button>
				<button id="content_cancel_button" class="button radius">cancel</button>
				<span id="content_errors" class="alert radius label"></span>
			</div>
			<!-- End Content  -->
		</div>

		<jsp:include page="left_nav_bar.jsp" flush="true"></jsp:include>


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