<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>


<div id="application_create" style="display: none">
	<div class="large-12 columns">
		<h3 class="gray">Application Setup</h3>
		<form role="form" id="applicationForm" name="applicationForm"
			data-abide="ajax">
			<input type="hidden" id="application_id" name="application_id" /> <input
				type="hidden" />

			<div id="progress_bar_top" style="display: none">
				<div class="progress radius">
					<span class="meter" style="width: 40%; background-color: #5cb85c;">creating/updating...</span>
				</div>
			</div>
			<div>
				<label>Name <small>required</small><input type="text"
					class="form-control" id="application_name" name="application_name"
					required="required" placeholder="name of the application" />
				</label>
			</div>
			<div>
				<label>Description: <textarea rows="5" class="form-control"
						id="application_description" name="application_description"
						placeholder="short description of the application"></textarea>
				</label>
			</div>
			<div>
				<label>Update content over Wi-Fi only&nbsp;<span
					data-tooltip class="has-tip"
					title="Specify if you like the content to be auto-updated to devices over Wi-Fi Only. This is enabled by default."><i
						class="fi-info light_gray"></i> </span></label>
			</div>
			<div>
				<div class="switch radius">
					<input id="application_update_over_wifi_only" type="checkbox"
						checked="checked" class="form-control switch-default"> <label
						for="application_update_over_wifi_only">Update content
						over Wi-Fi only</label>
				</div>
			</div>
			<div>
				<label>Collect Usage Data &amp; Geolocation&nbsp;<span data-tooltip
					class="has-tip"
					title="Skok collects usage data, including geolocation, of all your managed and unmanaged content. This information is used to provide Usage Reports.You can restrict collection of usage statistics for this application."><i
						class="fi-info light_gray"></i></span></label>
			</div>
			<div>
				<div class="switch radius ">
					<input id="collect_usage_data" type="checkbox" checked="checked"
						class="form-control switch-default"> <label
						for="collect_usage_data">Usage Data &amp; Geolocation</label>
				</div>
			</div>

			<div>
				<label>Enabled&nbsp;<span data-tooltip class="has-tip"
					title="Specify if the application is enabled. This application is enabled by default."><i
						class="fi-info light_gray"></i></span></label>
			</div>
			<div>
				<!-- <input id="application_enabled" type="checkbox"> -->
				<div class="switch radius ">
					<input id="application_enabled" type="checkbox" checked="checked"
						class="form-control switch-default"> <label
						for="application_enabled">Enabled</label>
				</div>
			</div>
			<div id="cm_errors_container" style="display: none">
				<ul id="vision">
					<li><div>
							<i class="fi-alert"></i>
						</div> <span id="application_errors"></span>
						<p class="clearfix"></p></li>
				</ul>
			</div>
			<div>
				<button id="application_save_button"
					class="button radius btn-default">create</button>
				<a href="javascript:void(0);" id="application_cancel_button">cancel</a>
			</div>
			<div id="progress_bar_bottom" style="display: none">
				<div class="progress radius">
					<span class="meter" style="width: 40%; background-color: #5cb85c;">creating/updating...</span>
				</div>
			</div>
		</form>
	</div>
</div>
<!-- End Content Group -->