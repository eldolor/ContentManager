
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
<script type="text/javascript" src="resources/js/cm.content.group.js"></script>
<!-- End Custom -->


</head>
<body>


	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="header.jsp"></jsp:include>
	<br>
	<jsp:include page="breadcrumbs.jsp"></jsp:include>
	<br>
	<div class="row">

		<div class="large-9 push-3 columns" id="content_groups_list">
			<div class="row">
				<div class="large-6 columns">
					<p>
						<strong>Some Person said:</strong> Bacon ipsum dolor sit amet
						nulla ham qui sint exercitation eiusmod commodo, chuck duis velit.
						Aute in reprehenderit, dolore aliqua non est magna in labore pig
						pork biltong.
					</p>
					<ul class="inline-list">
						<li><a href="">Reply</a></li>
						<li><a href="">Share</a></li>
					</ul>
				</div>
			</div>
		</div>
		<jsp:include page="left_nav_bar.jsp" flush="true"></jsp:include>

	</div>

	<br>
	<jsp:include page="footer.jsp" flush="false"></jsp:include>
	<!-- Begin Content Group -->
	<div class="reveal-modal medium" id="content_group_create_modal"
		data-reveal>
		<h3 id="contentGroupModalLabel">Content Group Setup</h3>
		<form id="contentGroupForm" name="contentGroupForm">
			<input type="hidden" id="contentgroup_id" name="contentgroup_id" />
			<input type="hidden" />

			<div class="row">
				<div class="large-12 columns">
					<label>Name: <input type="text" id="contentgroup_name"
						name="contentgroup_name" />
					</label>
				</div>
			</div>
			<div class="row">
				<div class="large-12 columns">
					<label>Description: <textarea rows="5"
							id="contentgroup_description" name="contentgroup_description"></textarea>
					</label>
				</div>
			</div>
			<div class="row">
				<div class="large-12 columns">
					<label>Start Date:
						<div id="contentgroup_start_datepicker">
							<input type="text" id="contentgroup_start_date"
								name="contentgroup_start_date" /> <br />
						</div>
					</label>
				</div>
			</div>

			<div class="row">
				<div class="large-12 columns">
					<label><span data-tooltip class="has-tip"
						title="Specify an End Date only if you want the content to expire after a certain date, or else leave it empty.">End
							Date:</span>
						<div id="contentgroup_end_datepicker">
							<input type="text" id="contentgroup_end_date"
								name="contentgroup_end_date" /> <br />
						</div> </label>
				</div>
			</div>

			<div class="row">
				<div class="large-12 columns">
					<label> Content Group <span class="label label-info"
						id="contentgroup_status">Enabled</span>
					</label><input type="checkbox" id="contentgroup_enabled"
						name="contentgroup_enabled" checked="checked" />
				</div>
			</div>
			<div>&nbsp;</div>
		</form>
		<span id="contentgroup_errors" class="label label-important"></span>
		<button id="contentgroup_save_button" class="button">create</button>
		<a class="close-reveal-modal">&#215;</a>
	</div>
	<!-- End Ad Group -->

	<!-- At the bottom of your page but inside of the body tag -->
	<ol class="joyride-list" data-joyride>
		<li data-id="breadcrumb_content_groups" data-text="Next"
			data-options="tip_location: bottom;timer:2000;tip_animation:fade">
			<p>You will first need to create a Content Group</p>
		</li>
		<li data-id="left_nav_bar_link_1"
			data-options="tip_location:bottom;tip_animation:fade"
			data-text="Next">
			<p>Click here to create a new Content Group</p>
		</li>
	</ol>
</body>
</html>