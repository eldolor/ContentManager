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
	<form id="contentGroupForm" name="contentGroupForm" data-abide="ajax">
		<fieldset>
			<legend>Content Group Setup</legend>
			<input type="hidden" id="application_id" name="application_id" /> <input
				type="hidden" id="contentgroup_id" name="contentgroup_id" /> <input
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
							id="contentgroup_name" name="contentgroup_name"
							required="required" placeholder="My First Content Group" />
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
							<div id="contentgroup_end_datepicker">
								<input type="text" id="contentgroup_end_date"
									name="contentgroup_end_date" pattern="month_day_year" /> <br />
								<small class="error">Please enter a valid end date
									MM/DD/YYYY</small>
							</div>
						</label>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="large-12 columns">
					<label>Enabled<span data-tooltip class="has-tip"
						title="Specify if the content group is enabled. This content group is enabled by default."><img
							alt="question_mark" src="/resources/images/question_mark.png"></span></label>
				</div>
			</div>
			<div class="row">
				<div class="large-12 columns">
					<!-- <input id="contentgroup_enabled" type="checkbox"> -->
					<div class="switch radius">
						<input id="contentgroup_enabled" type="checkbox" checked="checked">
						<label for="contentgroup_enabled">Enabled</label>
					</div>
				</div>
			</div>
			<div>&nbsp;</div>
			<div class="row">
				<div class="large-12 columns">
					<span id="contentgroup_errors" class="alert radius label"
						style="display: none"></span>
				</div>
			</div>
			<div>&nbsp;</div>
			<div class="row">
				<div class="large-12 columns">
					<button id="contentgroup_save_button" class="button radius">create</button>
					<a href="javascript:void(0);" id="contentgroup_cancel_button">cancel</a>
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