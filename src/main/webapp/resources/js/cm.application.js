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
		setupLeftNavBar();
		setupBreadcrumbs();

		getApplications();
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
						'<a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Create Application</a></li>');
		$('#left_nav_bar_link_1').unbind();
		$('#left_nav_bar_link_1').click(function() {
			// $('#application_create_modal').foundation('reveal', 'open');
			$('#applications_list').hide();
			$('#application_create').show();
			newApplication();
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
								+ "<a id=\"breadcrumb_applications\" href=\"/applications\">Applications</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}
function getApplications() {
	log("getApplications", "Entering");
	try {
		// open wait div
		openWait();

		var jqxhr = $.ajax({
			url : "/secured/applications",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(applications) {
					handleDisplayApplications_Callback(applications);
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

	} catch (err) {
		handleError("getApplications", err);
		// close wait div
		;
	} finally {
		log("getApplications", "Exiting");
	}
}

function handleDisplayApplications_Callback(pApplications) {
	log("handleDisplayApplications_Callback", "Entering");
	try {
		var lInnerHtml = "<div class=\"row\"> <div class=\"large-6 columns\">";
		for (var int = 0; int < pApplications.length; int++) {
			var lApplication = pApplications[int];
			lInnerHtml += "<p><span data-tooltip class=\"has-tip\" title=\"Click here to view this Application\"><a href=\"javascript:void(0)\" onclick=\"displayContentGroups("
					+ lApplication.id + ")\"><strong>";
			lInnerHtml += lApplication.name;
			lInnerHtml += "</strong></a></span></p>";
			lInnerHtml += "<p><span id=\"application_trackingid\" class=\"secondary radius label\">Application Id: "
					+ lApplication.trackingId + "</span></p>";
			lInnerHtml += "<ul class=\"inline-list\"> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"editApplication("
					+ lApplication.id
					+ ")\">edit</a></li> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"deleteApplication("
					+ lApplication.id + ")\">delete</a></li></ul>";
			lInnerHtml += "</div></div><hr>";
		}

		$('#applications_list').empty().html(lInnerHtml);
	} catch (err) {
		handleError("handleDisplayApplications_Callback", err);
	} finally {

		log("handleDisplayApplications_Callback", "Exiting");
	}
}

function displayContentGroups(pApplicationId) {
	// load entry info via ajax
	log("displayContentGroups", "Entering");
	try {

		window.location.href = '/' + pApplicationId + '/contentgroups';
	} catch (err) {
		handleError("displayContent", err);
	} finally {

		log("displayContentGroups", "Exiting");
	}

}

function updateApplicationEnabled(pApplicationId, pApplicationEnabled,
		pElementName) {
	openWait();
	log("updateApplicationEnabled", "Entering");
	var lElementName = "#" + pElementName;

	try {
		var lDate = new Date();
		var lApplicationObj = {
			id : pApplicationId,
			enabled : pApplicationEnabled,
			timeUpdatedMs : lDate.getTime(),
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)
		};
		var lApplicationObjString = JSON.stringify(lApplicationObj, null, 2);
		var jqxhr = $.ajax({
			url : "/secured/application/enabled",
			type : "PUT",
			data : lApplicationObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					// getApplications();
					window.location.href = '/applications';
				},
				400 : function(text) {
					try {
						$('#application_errors').html(
								'<p>' + getErrorMessages(text) + '</p>');
					} catch (err) {
						handleError("updateApplicationEnabled", err);
					}
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

		return false;
	} catch (err) {
		handleError("updateApplicationEnabled", err);
	} finally {
		log("updateApplicationEnabled", "Exiting");
	}
}
function displayApplicationStats(id, name) {
	log("displayApplicationStats", "Entering");
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/applicationadstats/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(adStats) {
					$("#applicationAccordianGroup" + id).popover(
							{
								selector : '#applicationAccordianGroup' + id,
								content : 'Clicks: ' + adStats.clicks
										+ ', Impressions: '
										+ adStats.impressions
							});
					$("#applicationAccordianGroup" + id).popover('toggle');
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("displayApplicationStats", err);
		;
	} finally {
		log("displayApplicationStats", "Exiting");
	}
}

function editApplication(id) {
	log("editApplication", "Entering");
	try {
		$('#application_errors').hide();

		$('#application_cancel_button').unbind();
		$('#application_cancel_button').click(function() {
			$('#application_create').hide();
			$('#applications_list').show();
		});
		$('#applications_list').hide();
		$('#application_create').show();
		// load entry info via ajax
		var url = "/secured/application/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(application) {

					$('#application_id').val(application.id);
					$('#application_name').val(application.name);
					$('#application_description').val(application.description);
					// add more
					$('#application_userid').val(application.sponsoredUserId);

					log("editApplication", "Application enabled: "
							+ application.enabled);
					if (application.enabled == true) {
						$('#application_enabled').attr('checked', 'checked');
					} else {
						$('#application_enabled').removeAttr('checked');
					}
					$('#application_save_button').html('update');

					// unbind click listener to reset
					$('#application_save_button').unbind();
					$('#application_save_button').bind('click',
							updateApplication);

					$('#application_cancel_button').unbind();
					$('#application_cancel_button').click(function() {
						$('#application_save_button').unbind();
						$('#application_create').hide();
						$('#applications_list').show();
					});

					$('#application_errors').empty();
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editApplication", err);
	} finally {
		log("editApplication", "Exiting");
	}
}

function newApplication() {
	log("newApplication", "Entering");
	try {
		$('#application_errors').hide();

		$('#application_save_button').html('create');
		// unbind click listener to reset
		$('#application_save_button').unbind();
		$('#application_save_button').click(createApplication);
		$('#application_cancel_button').unbind();
		$('#application_cancel_button').click(function() {
			$('#application_save_button').unbind();
			$('#application_create').hide();
			$('#applications_list').show();
		});

		$('#application_id').val('');
		$('#application_name').val('');
		$('#application_description').val('');
		$('#application_userid').val('');

		// set default
		$('#application_enabled').attr('checked', 'checked');

		$('#application_errors').empty();
	} catch (err) {
		handleError("newApplication", err);
	} finally {
		log("newApplication", "Exiting");
	}
}

function createApplication() {
	log("createApplication", "Entering");
	try {
		openWait();
		var _enabled;
		if ($('#application_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var _date = new Date();
		var _timeCreated = _date.getTime();

		var applicationObj = {
			id : $('#application_id').val(),
			name : $('#application_name').val(),
			description : $('#application_description').val(),
			enabled : _enabled,
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)

		};
		var applicationObjString = JSON.stringify(applicationObj, null, 2);
		// alert(applicationObjString);
		// create via sync call
		var jqxhr = $.ajax({
			url : "/secured/application",
			type : "POST",
			data : applicationObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#application_create').hide();
					getApplications();
					$('#applications_list').show();
				},
				400 : function(text) {
					try {
						$('#application_errors').html(
								'<p>' + getErrorMessages(text) + '</p>');
						$('#application_errors').show();
					} catch (err) {
						handleError("submitApplication", err);
					}
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

		return false;
	} catch (err) {
		handleError("submitApplication", err);
	} finally {
		log("createApplication", "Exiting");
	}
}

function updateApplication() {
	openWait();
	log("updateApplication", "Entering");
	var _enabled;

	if ($('#application_enabled').is(':checked')) {
		_enabled = true;
	} else {
		_enabled = false;
	}

	try {
		var _date = new Date();
		var applicationObj = {
			id : $('#application_id').val(),
			name : $('#application_name').val(),
			description : $('#application_description').val(),
			enabled : _enabled,
			timeUpdatedMs : _date.getTime(),
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var applicationObjString = JSON.stringify(applicationObj, null, 2);
		var jqxhr = $.ajax({
			url : "/secured/application",
			type : "PUT",
			data : applicationObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function() {
					$('#application_create').hide();
					getApplications();
					$('#applications_list').show();
				},
				400 : function(text) {
					try {
						$('#application_errors').html(
								'<p>' + getErrorMessages(text) + '</p>');
						$('#application_errors').show();
					} catch (err) {
						handleError("updateApplication", err);
					}
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

		return false;
	} catch (err) {
		handleError("updateApplication", err);
	} finally {
		log("updateApplication", "Exiting");
	}
}

function deleteApplication(id) {
	log("deleteApplication", "Entering");
	try {
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayConfirm("Are you sure you want to delete this Application?",
				function() {
					wait("confirm_wait");
					var url = "/secured/application/" + id + "/"
							+ _timeUpdatedMs + "/"
							+ _timeUpdatedTimeZoneOffsetMs;
					var jqxhr = $.ajax({
						url : url,
						type : "DELETE",
						contentType : "application/json",
						statusCode : {
							200 : function() {
								getApplications();
							}
						}
					});
					jqxhr.always(function(msg) {
						clearWait("confirm_wait");
					});
				});
	} catch (err) {
		handleError("deleteApplication", err);
	} finally {
		log("deleteApplication", "Exiting");
	}

}

/** *End Application***************************************** */
