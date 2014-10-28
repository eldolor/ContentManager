jQuery(function($) {
	try {
		log("function($)", "Entering");
		setup();
		// call this post setup
		var lPathName = window.location.pathname;

		if (lPathName.lastIndexOf("tour") != -1) {
			$(document).foundation('joyride', 'start');
		} else if (typeof (Storage) !== "undefined") {
			// Code for localStorage/sessionStorage.
			// Store
			var lProductTour = localStorage.productTour;
			if ((typeof (lProductTour) === "undefined")
					|| (lProductTour == "N")) {
				// set
				localStorage.setItem("productTour", "N");
				$(document).foundation('joyride', 'start');
			}
		} else {
			// Sorry! No Web Storage support..
			$(document).foundation('joyride', 'start');
		}
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
		});
		$('#restore_applications').unbind();
		$('#restore_applications').click(function() {
			// $('#application_create_modal').foundation('reveal',
			// 'open');
			restoreApplications();
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

function setProductTour() {
	log("setProductTour", "Entering");
	try {
		localStorage.setItem("productTour", "Y");

	} catch (err) {
		handleError("setNoMoreProductTour", err);
	} finally {
		log("setProductTour", "Exiting");
	}

}
/** *End Application***************************************** */
