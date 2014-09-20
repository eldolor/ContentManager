<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>

<!-- Begin Content Group -->
<div id="content_group_create" style="display: none">
	<div class="large-12 columns">
		<h3 class="gray">Content Group Setup</h3>
		<form role="form" id="contentGroupForm" name="contentGroupForm"
			data-abide="ajax">
			<input type="hidden" id="application_id" name="application_id" /> <input
				type="hidden" id="contentgroup_id" name="contentgroup_id" /> <input
				type="hidden" />

			<div id="progress_bar_top" style="display: none">
				<div class="progress radius">
					<span class="meter" style="width: 40%; background-color: #5cb85c;">creating/updating...</span>
				</div>
			</div>
			<div>
				<label>Name <small>required</small><input type="text"
					class="form-control" id="contentgroup_name"
					name="contentgroup_name" required="required"
					placeholder="name of the content group" />
				</label>
			</div>
			<div>
				<label>Description <textarea rows="5" class="form-control"
						id="contentgroup_description" name="contentgroup_description"
						placeholder="short description of the content group"></textarea>
				</label>
			</div>
			<div>
				<label>Start Date <small>required</small>
					<div id="contentgroup_start_datepicker">
						<input type="text" id="contentgroup_start_date"
							class="form-control" name="contentgroup_start_date"
							required="required" pattern="month_day_year" /> <br /> <small
							class="error">Please enter a valid start date MM/DD/YYYY</small>
					</div>
				</label>
			</div>

			<div>
				<label>End Date<span data-tooltip class="has-tip"
					title="Specify an End Date only if you want the content to expire after a certain date, or else leave it empty.">&nbsp;<i
						class="fi-info light_gray"></i></span>
					<div id="contentgroup_end_datepicker">
						<input type="text" id="contentgroup_end_date" class="form-control"
							name="contentgroup_end_date" pattern="month_day_year" /> <br />
						<small class="error">Please enter a valid end date
							MM/DD/YYYY</small>
					</div>
				</label>
			</div>

			<div>
				<label><span data-tooltip class="has-tip"
					title="Specify if the application is enabled. This content group is enabled by default.">Enabled&nbsp;<i
						class="fi-info light_gray"></i></span></label>
			</div>
			<div>
				<!-- <input id="contentgroup_enabled" type="checkbox"> -->
				<div class="switch radius">
					<input id="contentgroup_enabled" class="form-control"
						type="checkbox" checked="checked"> <label
						for="contentgroup_enabled">Enabled</label>
				</div>
			</div>
			<div id="cm_errors_container" style="display: none">
				<ul id="vision">
					<li><div>
							<i class="fi-alert"></i>
						</div> <span id="contentgroup_errors"></span>
						<p class="clearfix"></p></li>
				</ul>
			</div>
			<div>
				<button id="contentgroup_save_button"
					class="button radius btn-default">create</button>
				<a href="javascript:void(0);" id="contentgroup_cancel_button">cancel</a>
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