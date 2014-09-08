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
		$(document).foundation('abide', 'events');

		var doc = document.documentElement;
		doc.setAttribute('data-useragent', navigator.userAgent);

		setupBreadcrumbs();
		setupContentGroup();
		setupContent();

		getSearchResults(mSearchTerm);
	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}

function setupBreadcrumbs() {
	log("setupBreadcrumbs", "Entering");
	try {
		var lHtml = $('#breadcrumbs').html();
		$('#breadcrumbs')
				.html(
						lHtml
								+ "<a id=\"breadcrumb_search_results\" href=\"#\">Search Results</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}

function setupContent() {
	log("setupContent", "Entering");
	try {
		$("#content_start_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#content_start_date'
		});
		$("#content_end_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#content_end_date'
		});

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

	} catch (err) {
		handleError("setupContent", err);
	} finally {
		log("setupContent", "Exiting");
	}
}
function setupContentGroup() {
	log("setupContentGroup", "Entering");
	try {

		$("#contentgroup_start_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#contentgroup_start_date'
		});
		$("#contentgroup_end_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#contentgroup_end_date'
		});

	} catch (err) {
		handleError("setupContentGroup", err);
	} finally {
		log("setupContentGroup", "Exiting");
	}
}

function getSearchResults(pSearchCriteria) {
	log("getSearchResults", "Entering");
	try {
		$('#content_progress_bar').show();
		var jqxhr = $
				.ajax({
					url : "/secured/search/" + pSearchCriteria,
					type : "GET",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function(searchResults) {
							handleDisplaySearchResults_Callback(searchResults);
							// Google Analytics
							ga('send', {
								'hitType' : 'pageview',
								'page' : '/secured/search',
								'title' : PageTitle.SEARCH_RESULTS
							});
							// End Google Analytics
						},
						503 : function() {
							$('#search_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#search_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#search_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#search_errors').show();
					}
				});

	} catch (err) {
		handleError("getSearchResults", err);
		// close wait div
		;
	} finally {
		log("getSearchResults", "Exiting");
	}
}

function handleDisplaySearchResults_Callback(pSearchResults) {
	log("handleDisplaySearchResults_Callback", "Entering");
	try {
		// displayMessage(JSON.stringify(pSearchResults, null, 2));
		handleDisplayApplications_Callback(pSearchResults.application);
		handleDisplayContentGroups_Callback(pSearchResults.content_group);
		handleDisplayContent_Callback(pSearchResults.content);
	} catch (err) {
		handleError("handleDisplaySearchResults_Callback", err);
	} finally {

		log("handleDisplaySearchResults_Callback", "Exiting");
	}
}

/** *End Application***************************************** */
