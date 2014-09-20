jQuery(function($) {
	try {
		log("function($)", "Entering");
		setup();
		// call this post setup
		// $(document).foundation('joyride', 'start');
	} catch (err) {
		handleError("function($)", err);
	} finally {
		log("function($)", "Exiting");
	}
});

function setup() {
	try {
		log("setup", "Entering");
		$(document).foundation();

		var doc = document.documentElement;
		doc.setAttribute('data-useragent', navigator.userAgent);
		// enable abide form validation
		$(document).foundation('abide', 'events');
		setupContextNavBar();
		setupBreadcrumbs();

		$("#contentgroup_start_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#contentgroup_start_date'
		});
		$("#contentgroup_end_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#contentgroup_end_date'
		});
		// set application and content group name
		$('#application_name').html(
				'Application:&nbsp;' + mSelectedApplication.name);

		// $('#cm_action').bind('click', alert('clicked'));
		getContentGroups(mSelectedApplication.id);
		$("#cm_errors_container").addClass("fadeInUp animated");
	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}

function setupContextNavBar() {
	log("setupContextNavBar", "Entering");
	try {
		$('#create_contentgroup').unbind();
		$('#create_contentgroup').click(function() {
			// $('#content_group_create_modal').foundation('reveal', 'open');
			$('#content_groups_list').hide();
			$('#content_group_create').show();
			newContentGroup();
		});

	} catch (err) {
		handleError("setupContextNavBar", err);
	} finally {
		log("setupContextNavBar", "Exiting");
	}
}

function setupBreadcrumbs() {
	log("setupBreadcrumbs", "Entering");
	try {
		var lHtml = $('#breadcrumbs').html();
		$('#breadcrumbs')
				.html(
						lHtml
								+ "<a id=\"breadcrumb_applications\" href=\"/applications\">Applications</a>"
								+ "<a id=\"breadcrumb_content_groups\" href=\"/"
								+ mSelectedApplication.id
								+ "/contentgroups\">Content Groups</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}
