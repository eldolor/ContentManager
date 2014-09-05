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

		setupLeftNavBar();
		setupBreadcrumbs();
		getSearchResults(mSearchTerm);
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
						'<li><a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >TODO</a></li>');
		$('#left_nav_bar_link_1').unbind();
		$('#left_nav_bar_link_1').click(function() {
			// $('#application_create_modal').foundation('reveal',
			// 'open');
			newApplication();
			// Google Analytics
			ga('send', 'event', Category.APPLICATION, Action.CREATE_NEW);
			// End Google Analytics
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
								+ "<a id=\"breadcrumb_search_results\" href=\"#\">Search Results</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}
function getSearchResults(pSearchCriteria) {
	log("getSearchResults", "Entering");
	try {
		$('#content_progress_bar').show();
		var jqxhr = $
				.ajax({
					url : "/secured/search/"+ pSearchCriteria,
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
		displayMessage(JSON.stringify(pSearchResults, null, 2));
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
