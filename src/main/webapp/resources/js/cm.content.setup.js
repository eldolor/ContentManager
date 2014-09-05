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
		setupLeftNavBar();
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
											"<span class=\"message\">Drop content here to upload</span>");
						});

		getContent(mSelectedApplication.id, mSelectedContentGroup.id);

		// set the available storage quota per plan
		setAvailableStorageQuota(mSelectedApplication.id);
	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}

function setupLeftNavBar() {
	log("setupLeftNavBar", "Entering");
	try {
		$('#left_nav_bar')
				.empty()
				.html(
						'<li><a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Create Content</a></li>');
		$('#left_nav_bar_link_1').unbind();
		$('#left_nav_bar_link_1').click(function() {
			$('#contents_list').hide();
			$('#content_create').show();
			newContent();
		});

	} catch (err) {
		handleError("setupLeftNavBar", err);
	} finally {
		log("setupLeftNavBar", "Exiting");
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
								+ "<a id=\"breadcrumb_content_groups\" href=\"/"
								+ mSelectedApplication.id + "/"
								+ mSelectedContentGroup.id
								+ "/content\">Content</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}
