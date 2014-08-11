
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
<script type="text/javascript" src="/resources/js/cm.content.group.js"></script>
<!-- End Custom -->
<script type="text/javascript">
	setSelectedApplication(${applicationId});
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
				<span id="application_name" class="secondary radius label"></span>
			</div>
			<div>&nbsp;</div>
			<div id="content_groups_list"></div>
			<!-- Begin Content Group -->
			<div id="content_group_create" style="display: none">
				<form id="contentGroupForm" name="contentGroupForm" data-abide="ajax">
					<fieldset>
						<legend>Content Group Setup</legend>
						<input type="hidden" id="application_id" name="application_id" />
						<input type="hidden" id="contentgroup_id" name="contentgroup_id" />
						<input type="hidden" />

						<div class="row">
							<div class="large-12 columns">
								<div class="name-field">
									<label>Name <small>required</small><input type="text"
										id="contentgroup_name" name="contentgroup_name"
										required="required" 
										placeholder="My First Content Group" />
									</label> <small class="error">Name is required</small>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<div class="input-wrapper">
									<label>Description <textarea rows="5"
											id="contentgroup_description" name="contentgroup_description"
											placeholder="A short description of the content group"></textarea>
									</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<div class="input-wrapper">
									<label>Start Date <small>required</small>
										<div id="contentgroup_start_datepicker">
											<input type="text" id="contentgroup_start_date"
												name="contentgroup_start_date" required="required"
												pattern="month_day_year" /> <br />
												<small class="error">Please enter a valid start date MM/DD/YYYY</small>
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
										<div id="contentgroup_end_datepicker">
											<input type="text" id="contentgroup_end_date"
												name="contentgroup_end_date" pattern="month_day_year" /> <br />
												<small class="error">Please enter a valid end date MM/DD/YYYY</small>
										</div> </label>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="large-12 columns">
								<label>Enabled:</label>
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<input id="contentgroup_enabled" type="checkbox">
								<!-- <div class="switch radius">
								<input id="contentgroup_enabled" type="checkbox"
									checked="checked"> <label for="contentgroup_enabled">Enabled</label>
							</div> -->
							</div>
						</div>
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<span id="contentgroup_errors" class="alert radius label"></span>
							</div>
						</div>
						<div>&nbsp;</div>
						<div class="row">
							<div class="large-12 columns">
								<button id="contentgroup_save_button" class="button radius">create</button>
								<button id="contentgroup_cancel_button" class="button radius">cancel</button>
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
		</aside>


	</div>

	<br>
	<jsp:include page="footer.jsp" flush="false"></jsp:include>


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