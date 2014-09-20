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
		$(document).foundation('alert', 'events');

		setupContextNavBar();
		setupBreadcrumbs();
		displayAnyMessage();
		getApplications();
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
		$('#create_application').unbind();
		$('#create_application').click(function() {
			// $('#application_create_modal').foundation('reveal',
			// 'open');
			newApplication();
			// Google Analytics
			ga('send', 'event', Category.APPLICATION, Action.CREATE_NEW);
			// End Google Analytics
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
								+ "<a id=\"breadcrumb_applications\" href=\"/applications\">Applications</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}

/** *End Application***************************************** */
