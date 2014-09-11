<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>


<div id="content_create" style="display: none">
	<form id="contentForm" name="contentForm" data-abide="ajax">
		<fieldset>
			<legend>Content Setup</legend>
			<input type="hidden" id="content_id" name="content_id" /> <input
				type="hidden" id="application_id" name="application_id" /> <input
				type="hidden" id="contentgroup_id" name="contentgroup_id" /><input
				type="hidden" id="content_uri" name="content_uri" /><input
				type="hidden" id="content_type" name="content_type" />

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
							id="content_name" name="content_name" required="required"
							placeholder="Geronimo" />
						</label> 
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
						<label>End Date<span data-tooltip class="has-tip"
							title="Specify an End Date only if you want the content to expire after a certain date, or else leave it empty."><img
								alt="question_mark" src="/resources/images/question_mark.png"></span>
							<div id="content_end_datepicker">
								<input type="text" id="content_end_date" name="content_end_date"
									pattern="month_day_year" /> <br /> <small class="error">Please
									enter a valid end date MM/DD/YYYY</small>
							</div>
						</label>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="large-12 columns">
					<label>Enabled<span data-tooltip class="hastip"
						title="Specify if the content is enabled. This content is enabled by default."><img
							alt="question_mark" src="/resources/images/question_mark.png"></span></label>
				</div>
			</div>

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
					<a id="upload_content" href="javascript:void(0);">Click here to
						upload content</a>
					<div id="content_dropbox">
						<span class="secondary radius label">Drop content here to
							upload</span>
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