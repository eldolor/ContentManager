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
		$('#product_create')
				.on(
						'hidden',
						function() {
							$(this).removeData();
							$("#product_dropbox").empty();
							$("#product_dropbox")
									.html(
											"<span class=\"message\">Drag-n-Drop product here to upload</span>");
						});

		getContent(mSelectedApplication.id, mSelectedContentGroup.id);

		// set the available storage quota per plan
		setAvailableStorageQuota(mSelectedApplication.id, true);
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
		$('#create_product').unbind();
		$('#create_product').click(function() {
			$('#products_list').hide();
			$('#product_create').show();
			newContent();
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

		if (mDisplayAsGrid) {
			lHtml += "<a id=\"list_view_option\" href=\"/"
					+ mSelectedApplication.id + "/" + mSelectedContentGroup.id
					+ "/product/list\">List View</a>";
		} else {
			lHtml += "<a id=\"grid_view_option\" href=\"/"
					+ mSelectedApplication.id + "/" + mSelectedContentGroup.id
					+ "/product\">Grid View</a>";
		}
		$('#breadcrumbs').html(lHtml);

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}
