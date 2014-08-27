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

		setupLeftNavBar();
		setupBreadcrumbs();
		displayAnyMessage();
		getApplications();
	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}
function displayAnyMessage() {
	log("displayAnyMessage", "Entering");
	try {
		var jqxhr = $
				.ajax({
					url : "/secured/messages",
					type : "GET",
					contentType : "application/json",
					async : false,
					statusCode : {
						200 : function(pMessages) {
							if (pMessages === '') {
								log("displayAnyMessage", "no incoming messages")
							} else {
								// TODO: enhance to handle multiple messages
								log("displayAnyMessage", "display messages");
								var lMessagesHtml = '';
								for (var int = 0; int < pMessages.length; int++) {
									var lMessage = pMessages[int];
									if (lMessage.messageClass == 'com.cm.admin.message.class.SUCCESS')
										lMessagesHtml += "<div data-alert id=\"alert_message\" class=\"alert-box success radius\">";
									else if (lMessage.messageClass == 'com.cm.admin.message.class.INFO')
										lMessagesHtml += "<div data-alert id=\"alert_message\" class=\"alert-box info radius\">";
									else if (lMessage.messageClass == 'com.cm.admin.message.class.WARNING')
										lMessagesHtml += "<div data-alert id=\"alert_message\" class=\"alert-box warning radius\">";
									else if (lMessage.messageClass == 'com.cm.admin.message.class.ALERT')
										lMessagesHtml += "<div data-alert id=\"alert_message\" class=\"alert-box alert radius\">";

									lMessagesHtml += "<div id=\"message_id\" style=\"display: none\">"
											+ lMessage.id + "</div>";
									lMessagesHtml += lMessage.message;
									lMessagesHtml += "<a href=\"#\" class=\"close\">&times;</a>";
									lMessagesHtml += "</div>";

								}
								// alert(pMessages);
								$('#message_box').html(lMessagesHtml);
								$('#message_box').show();
							}
						}
					}
				});
		// notifies us when users close an message box.
		$(document)
				.on(
						'close.fndtn.alert-box',
						function(event) {
							console.info('An alert box has been closed!');
							var _date = new Date();
							var _timeCreated = _date.getTime();
							var obj = {
								id : $('#message_id').html(),
								timeViewedMs : _timeCreated,
								timeViewedTimeZoneOffsetMs : (_date
										.getTimezoneOffset() * 60 * 1000),
							};
							var objString = JSON.stringify(obj, null, 2);
							// alert(objString);
							var jqxhr = $.ajax({
								url : "/secured/message/read",
								type : "POST",
								data : objString,
								processData : false,
								dataType : "json",
								contentType : "application/json",
								async : false,
								statusCode : {
									200 : function() {

									},
									400 : function(text) {

									}
								}
							});
						});
	} catch (err) {
		handleError("displayAnyMessage", err);
	} finally {
		log("displayAnyMessage", "Exiting");
	}
}
function setupLeftNavBar() {
	log("setupLeftNavBar", "Entering");
	try {
		$('#left_nav_bar')
				.empty()
				.html(
						'<li><a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Create Application</a></li>');
		$('#left_nav_bar_link_1').unbind();
		$('#left_nav_bar_link_1').click(function() {
			// $('#application_create_modal').foundation('reveal',
			// 'open');
			$('#applications_list').hide();
			$('#application_create').show();
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
		var jqxhr = $
				.ajax({
					url : "/secured/applications",
					type : "GET",
					contentType : "application/json",
					async : false,
					statusCode : {
						200 : function(applications) {
							handleDisplayApplications_Callback(applications);
							// Google Analytics
							ga('send', {
								'hitType' : 'pageview',
								'page' : '/secured/applications',
								'title' : PageTitle.APPLICATIONS
							});
							// End Google Analytics
						},
						503 : function() {
							$('#application_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#application_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#application_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#application_errors').show();
					}
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
			lInnerHtml += "<ul class=\"inline-list\"> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"editApplication("
					+ lApplication.id
					+ ")\">edit</a></li> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"deleteApplication("
					+ lApplication.id + ")\">delete</a></li></ul>";
			lInnerHtml += "<p><span id=\"application_trackingid\" class=\"secondary radius label\">Application Id: "
					+ lApplication.trackingId + "</span></p>";
			// log(JSON.stringify(lApplication));
			if (lApplication.changesStaged) {
				lInnerHtml += "<p><a href=\"javascript:void(0);\" onclick=\"pushChangestoHandsets('"
						+ lApplication.id
						+ "')\" class=\"button tiny alert\">Push Changes to Handsets</a></p>";
			}
			lInnerHtml += "</div></div><hr>";
		}

		$('#applications_list').empty().html(lInnerHtml);
	} catch (err) {
		handleError("handleDisplayApplications_Callback", err);
	} finally {

		log("handleDisplayApplications_Callback", "Exiting");
	}
}
function pushChangestoHandsets(pApplicationId) {
	// load entry info via ajax
	log("pushChangestoHandsets", "Entering");
	try {
		var jqxhr = $
				.ajax({
					url : "/secured/pushchanges/" + pApplicationId,
					type : "POST",
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function() {
							location.reload();
						},
						400 : function(text) {
							try {
								log("No application found for tracking id "
										+ pTrackingId);
							} catch (err) {
								handleError("pushChangestoHandsets", err);
							}
						},
						503 : function() {
							$('#application_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#application_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
					},
					complete : function(xhr, textStatus) {
						log(xhr.status);
					}
				});

		return false;

	} catch (err) {
		handleError("pushChangestoHandsets", err);
	} finally {

		log("pushChangestoHandsets", "Exiting");
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
		var jqxhr = $
				.ajax({
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
										getErrorMessages(text));
							} catch (err) {
								handleError("updateApplicationEnabled", err);
							}
						},
						503 : function() {
							log(errorThrown);
							$('#application_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#application_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#application_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#application_errors').show();
					}
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
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(adStats) {
							$("#applicationAccordianGroup" + id).popover(
									{
										selector : '#applicationAccordianGroup'
												+ id,
										content : 'Clicks: ' + adStats.clicks
												+ ', Impressions: '
												+ adStats.impressions
									});
							$("#applicationAccordianGroup" + id).popover(
									'toggle');
						},
						503 : function() {
							$('#application_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#application_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#application_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#application_errors').show();
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
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(application) {

							$('#application_id').val(application.id);
							$('#application_name').val(application.name);
							$('#application_description').val(
									application.description);
							// add more
							$('#application_userid').val(
									application.sponsoredUserId);

							log("editApplication", "Application enabled: "
									+ application.enabled);
							if (application.updateOverWifiOnly == true) {
								$('#application_update_over_wifi_only').attr(
										'checked', 'checked');
							} else {
								$('#application_update_over_wifi_only')
										.removeAttr('checked');
							}

							if (application.enabled == true) {
								$('#application_enabled').attr('checked',
										'checked');
							} else {
								$('#application_enabled').removeAttr('checked');
							}
							$('#application_save_button').html('update');

							// not using valid.fndtn.abide & invalid.fndtn.abide
							// as it
							// causes the form to be submitted twice. Instead
							// use the
							// deprecated valid & invalid
							$('#applicationForm').on(
									'invalid',
									function() {
										var invalid_fields = $(this).find(
												'[data-invalid]');
										log(invalid_fields);
									}).on(
									'valid',
									function() {
										log('editApplication: valid!');
										updateApplication();
										// Google Analytics
										ga('send', 'event',
												Category.APPLICATION,
												Action.UPDATE);
										// End Google Analytics
									});

							// unbind click listener to reset
							$('#application_cancel_button').unbind();
							$('#application_cancel_button').click(
									function() {
										$('#application_create').hide();
										$('#applications_list').show();
										// Google Analytics
										ga('send', 'event',
												Category.APPLICATION,
												Action.CANCEL);
										// End Google Analytics
									});

							$('#application_errors').empty();
						},
						503 : function() {
							$('#application_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#application_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#application_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#application_errors').show();
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
		// validate quota
		var url = "//secured/quota/application/validate";
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(application) {

							$('#application_errors').hide();

							$('#application_save_button').html('create');
							// not using valid.fndtn.abide & invalid.fndtn.abide
							// as it
							// causes the form to be submitted twice. Instead
							// use the
							// deprecated valid & invalid
							// unbind click listener to reset
							$('#applicationForm').on(
									'invalid',
									function() {
										var invalid_fields = $(this).find(
												'[data-invalid]');
										log(invalid_fields);
									}).on(
									'valid',
									function() {
										log('newApplication: valid!');
										createApplication();
										// Google Analytics
										ga('send', 'event',
												Category.APPLICATION,
												Action.CREATE);
										// End Google Analytics
									});
							$('#application_cancel_button').unbind();
							$('#application_cancel_button').click(function() {
								$('#application_create').hide();
								$('#applications_list').show();
							});

							$('#application_id').val('');
							$('#application_name').val('');
							$('#application_description').val('');
							$('#application_userid').val('');

							$('#application_update_over_wifi_only').attr(
									'checked', 'checked');
							// set default
							$('#application_enabled')
									.attr('checked', 'checked');

							$('#application_errors').empty();
						},
						409 : function() {
							// insufficient quota
							displayConfirm(
									"Please upgrade your Plan to create more applications",
									null);
						},
						503 : function() {
							$('#application_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#application_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#application_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#application_errors').show();
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

	} catch (err) {
		handleError("newApplication", err);
	} finally {
		log("newApplication", "Exiting");
	}
}

function createApplication() {
	log("createApplication", "Entering");
	try {
		$('#progress_bar').show();
		$('.button').addClass('disabled');
		var _enabled;
		if ($('#application_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}
		var _updateOverWifiOnly;
		if ($('#application_update_over_wifi_only').is(':checked')) {
			_updateOverWifiOnly = true;
		} else {
			_updateOverWifiOnly = false;
		}

		var _date = new Date();
		var _timeCreated = _date.getTime();

		var applicationObj = {
			id : $('#application_id').val(),
			name : $('#application_name').val(),
			description : $('#application_description').val(),
			updateOverWifiOnly : _updateOverWifiOnly,
			enabled : _enabled,
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)

		};
		var applicationObjString = JSON.stringify(applicationObj, null, 2);
		// alert("If you see this alert twice then the form is being submitted
		// twice. There are multiple listeners on this form."
		// + applicationObjString);
		// create via sync call
		var jqxhr = $
				.ajax({
					url : "/secured/application",
					type : "POST",
					data : applicationObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						201 : function() {
							$('#application_create').hide();
							location.reload();
							// $('#application_create').hide();
							// getApplications();
							// $('#applications_list').show();
						},
						400 : function(text) {
							try {
								$('#application_errors').html(
										getErrorMessages(text));
								$('#application_errors').show();
							} catch (err) {
								handleError("submitApplication", err);
							}
						},
						503 : function() {
							$('#application_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#application_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#application_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#application_errors').show();
					},
					complete : function(xhr, textStatus) {
						$('.meter').css("width", "100%");
						$('.button').removeClass('disabled');
						log(xhr.status);
					}
				});

		return false;
	} catch (err) {
		handleError("submitApplication", err);
	} finally {
		log("createApplication", "Exiting");
	}
}

function updateApplication() {

	log("updateApplication", "Entering");
	$('#progress_bar').show();
	$('.button').addClass('disabled');
	var _enabled;

	if ($('#application_enabled').is(':checked')) {
		_enabled = true;
	} else {
		_enabled = false;
	}
	var _updateOverWifiOnly;
	if ($('#application_update_over_wifi_only').is(':checked')) {
		_updateOverWifiOnly = true;
	} else {
		_updateOverWifiOnly = false;
	}

	try {
		var _date = new Date();
		var applicationObj = {
			id : $('#application_id').val(),
			name : $('#application_name').val(),
			description : $('#application_description').val(),
			updateOverWifiOnly : _updateOverWifiOnly,
			enabled : _enabled,
			timeUpdatedMs : _date.getTime(),
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var applicationObjString = JSON.stringify(applicationObj, null, 2);
		var jqxhr = $
				.ajax({
					url : "/secured/application",
					type : "PUT",
					data : applicationObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function() {
							$('#application_create').hide();
							location.reload();
							// $('#application_create').hide();
							// getApplications();
							// $('#applications_list').show();
						},
						400 : function(text) {
							try {
								$('#application_errors').html(
										getErrorMessages(text));
								$('#application_errors').show();
							} catch (err) {
								handleError("updateApplication", err);
							}
						},
						503 : function() {
							$('#application_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#application_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#application_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#application_errors').show();
					},
					complete : function(xhr, textStatus) {
						$('.meter').css("width", "100%");
						$('.button').removeClass('disabled');
						log(xhr.status);
					}
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

		displayConfirm(
				"Are you sure you want to delete this Application?",
				function() {
					// Google Analytics
					ga('send', 'event', Category.APPLICATION, Action.DELETE);
					// End Google Analytics

					var url = "/secured/application/" + id + "/"
							+ _timeUpdatedMs + "/"
							+ _timeUpdatedTimeZoneOffsetMs;
					var jqxhr = $
							.ajax({
								url : url,
								type : "DELETE",
								contentType : "application/json",
								statusCode : {
									200 : function() {
										$('#application_create').hide();
										location.reload();
										// getApplications();
									},
									503 : function() {
										$('#application_errors')
												.html(
														'Unable to process the request. Please try again later');
										$('#application_errors').show();
									}
								},
								error : function(xhr, textStatus, errorThrown) {
									log(errorThrown);
									$('#application_errors')
											.html(
													'Unable to process the request. Please try again later');
									$('#application_errors').show();
								}
							});
				});
	} catch (err) {
		handleError("deleteApplication", err);
	} finally {
		log("deleteApplication", "Exiting");
	}

}

/** *End Application***************************************** */
