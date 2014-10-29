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
		setupContextNavBar();
		setupBreadcrumbs();

		$("#content_start_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#content_start_date'
		});
		$("#content_end_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#content_end_date'
		});
		// set application and content group name
		$('#application_name').html(
				'Application:&nbsp;' + mSelectedApplication.name);
		$('#contentgroup_name').html(
				'Content Group:&nbsp;' + mSelectedContentGroup.name);

		$("#jquery_jplayer_1").jPlayer({
			swfPath : "/resources/js/jquery",
			supplied : "webmv, ogv, m4v",
			size : {
				width : "640px",
				height : "360px",
				cssClass : "jp-video-360p"
			},
			errorAlerts : true,
			warningAlerts : true
		});
		$('#content_create')
				.on(
						'hidden',
						function() {
							$(this).removeData();
							$("#content_dropbox").empty();
							$("#content_dropbox")
									.html(
											"<span class=\"message\">Drag-n-Drop content here to upload</span>");
						});

		getContent(mSelectedApplication.id, mSelectedContentGroup.id);

		// set the available storage quota per plan
		setAvailableStorageQuota(mSelectedApplication.id, true);
		$("#cm_errors_container").addClass("fadeInUp animated");

		// tags init
		$('#content_tags').tagsInput();

	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}

function setupContextNavBar() {
	log("setupContextNavBar", "Entering");
	try {
		$('#create_content').unbind();
		$('#create_content').click(function() {
			$('#contents_list').hide();
			$('#content_create').show();
			newContent();
		});
		$('#restore_content').unbind();
		$('#restore_content').click(function() {
			// $('#application_create_modal').foundation('reveal',
			// 'open');
			restoreContent(mSelectedApplication.id, mSelectedContentGroup.id);
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
								+ "/contentgroups\">Content Groups</a>"
								+ "<a id=\"breadcrumb_content\" href=\"/"
								+ mSelectedApplication.id + "/"
								+ mSelectedContentGroup.id
								+ "/content\">Content</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}
