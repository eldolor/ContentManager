<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>


<div id="application_create" style="display: none">
	<form id="applicationForm" name="applicationForm" data-abide="ajax">
		<fieldset>
			<legend>Application Setup</legend>
			<input type="hidden" id="application_id" name="application_id" /> <input
				type="hidden" />

			<div class="row" id="progress_bar_top" style="display: none">
				<div class="large-12 columns">
					<div class="progress radius">
						<span class="meter" style="width: 40%"></span>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="large-12 columns">
					<div class="name-field">
						<label>Name <small>required</small><input type="text"
							id="application_name" name="application_name" required="required"
							placeholder="My First Application" />
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
						<input id="application_enabled" type="checkbox" checked="checked">
						<label for="application_enabled">Enabled</label>
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
			<div class="row" id="progress_bar_bottom" style="display: none">
				<div class="large-12 columns">
					<label>Loading...</label><br>
					<div class="progress radius">
						<span class="meter" style="width: 40%"></span>
					</div>
				</div>
			</div>
		</fieldset>

	</form>
</div>
<!-- End Content Group -->