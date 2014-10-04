<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>


<div id="content_create" style="display: none">
	<div class="large-12 columns">
		<h3 class="gray">Content Setup</h3>
		<form role="form" id="contentForm" name="contentForm"
			data-abide="ajax">
			<input type="hidden" id="content_id" name="content_id" /> <input
				type="hidden" id="application_id" name="application_id" /> <input
				type="hidden" id="contentgroup_id" name="contentgroup_id" /><input
				type="hidden" id="content_uri" name="content_uri" /><input
				type="hidden" id="content_type" name="content_type" />

			<div id="progress_bar_top" style="display: none">
				<div class="progress radius">
					<span class="meter" style="width: 40%; background-color: #5cb85c;">creating/updating...</span>
				</div>
			</div>
			<div>
				<label>Name <small>required</small><input type="text"
					class="form-control" id="content_name" name="content_name"
					required="required" placeholder="name of the content" />
				</label>
			</div>
			<div class="clearfix"></div>
			<div>
				<label>Description <textarea rows="5" class="form-control"
						id="content_description" name="content_description"
						placeholder="short description of the content"></textarea>
				</label>
			</div>
			<div>
				<fieldset>
					<legend>Tags</legend>
					<input name="content_tags" id="content_tags" />
				</fieldset>
			</div>
			<div>
				<fieldset>
					<legend>Content Type</legend>
					<ul class="inline-list">
						<!-- Using radio buttons each switch turns off the other -->
						<li><fieldset>
								<legend>Image</legend>
								<div class="switch radius">
									<input id="content_type_image" type="radio" checked="checked"
										class="form-control" name="content_type_group"> <label
										for="content_type_image">Image</label>
								</div>
							</fieldset></li>

						<li>
							<fieldset>
								<legend>Video</legend>
								<div class="switch radius">
									<input id="content_type_video" type="radio"
										class="form-control" name="content_type_group"> <label
										for="content_type_video">Video</label>
								</div>
							</fieldset>
						</li>
					</ul>
				</fieldset>
			</div>
			<div>
				<label>Start Date <small>required</small>
					<div id="content_start_datepicker">
						<input type="text" id="content_start_date"
							name="content_start_date" required="required"
							class="form-control" pattern="month_day_year" /> <br /> <small
							class="error">Please enter a valid start date MM/DD/YYYY</small>
					</div>
				</label>
			</div>
			<div>
				<label>End Date<span data-tooltip class="has-tip"
					title="Specify an End Date only if you want the content to expire after a certain date, or else leave it empty.">&nbsp;<i
						class="fi-info light_gray"></i></span>
					<div id="content_end_datepicker">
						<input type="text" id="content_end_date" name="content_end_date"
							class="form-control" pattern="month_day_year" /> <br /> <small
							class="error">Please enter a valid end date MM/DD/YYYY</small>
					</div>
				</label>
			</div>

			<div>
				<label>Enabled<span data-tooltip class="hastip"
					title="Specify if the content is enabled. This content is enabled by default.">&nbsp;<i
						class="fi-info light_gray"></i></span></label>
			</div>

			<div>
				<div class="switch radius">
					<input id="content_enabled" type="checkbox" checked="checked"
						class="form-control"> <label for="content_enabled">Enabled</label>
				</div>
			</div>
			<div>
				<a id="upload_content" href="javascript:void(0);">Click here to
					upload content</a>
				<div id="content_dropbox">
					<span class="secondary radius label">Drop content here to
						upload</span>
				</div>
			</div>
			<div id="cm_errors_container" style="display: none">
				<ul id="vision">
					<li><div>
							<i class="fi-alert"></i>
						</div> <span id="content_errors"></span>
						<p class="clearfix"></p></li>
				</ul>
			</div>
			<div>
				<button id="content_save_button" class="button radius btn-default">create</button>
				<a href="javascript:void(0);" id="content_cancel_button">cancel</a>
			</div>
			<div id="progress_bar_bottom" style="display: none">
				<div class="progress radius">
					<span class="meter" style="width: 40%; background-color: #5cb85c;">creating/updating...</span>
				</div>
			</div>
		</form>
	</div>
</div>