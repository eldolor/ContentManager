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
						'<li><a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Create Content Group</a></li>');
		$('#left_nav_bar_link_1').unbind();
		$('#left_nav_bar_link_1').click(function() {
			// $('#content_group_create_modal').foundation('reveal', 'open');
			$('#content_groups_list').hide();
			$('#content_group_create').show();
			newContentGroup();
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
								+ "/contentgroups\">Content Groups</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}
function setSelectedApplication(id) {
	log("setSelectedApplication", "Entering");
	try {
		// load entry info via sync call
		var url = "/secured/application/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(application) {
					mSelectedApplication = application;
				}
			}
		});
	} catch (err) {
		handleError("setSelectedApplication", err);
	} finally {
		log("setSelectedApplication", "Entering");
	}
}

function getContentGroups(pApplicationId) {
	log("getContentGroups", "Entering");
	try {
		// open wait div
		openWait();

		var jqxhr = $.ajax({
			url : "/secured/" + pApplicationId + "/contentgroups",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(contentGroups) {
					handleDisplayContentGroups_Callback(contentGroups);
					// Google Analytics
					ga('send', {
						'hitType' : 'pageview',
						'page' : '/secured/' + pApplicationId
								+ '/contentgroups',
						'title' : PageTitle.CONTENT_GROUPS
					});
					// End Google Analytics
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

	} catch (err) {
		handleError("getContentGroups", err);
		// close wait div
		;
	} finally {
		log("getContentGroups", "Exiting");
	}
}

function handleDisplayContentGroups_Callback(pContentGroups) {
	log("handleDisplayContentGroups_Callback", "Entering");
	try {
		var lInnerHtml = "<div class=\"row\"> <div class=\"large-6 columns\">";
		for (var int = 0; int < pContentGroups.length; int++) {
			var lContentGroup = pContentGroups[int];
			lInnerHtml += "<p><span data-tooltip class=\"has-tip\" title=\"Click here to view the content in this Content Group\"><a href=\"javascript:void(0)\" onclick=\"displayContent(";
			lInnerHtml += lContentGroup.applicationId;
			lInnerHtml += ", ";
			lInnerHtml += lContentGroup.id;
			lInnerHtml += ")\"><strong>";
			lInnerHtml += lContentGroup.name;
			lInnerHtml += "</strong></a></span></p>";

			lInnerHtml += "<ul class=\"inline-list\"> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"editContentGroup("
					+ lContentGroup.id
					+ ")\">edit</a></li> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"deleteContentGroup("
					+ lContentGroup.id + ")\">delete</a></li></ul>";
			lInnerHtml += "<span id=\"content_group_id\" class=\"secondary radius label\">Content Group Id: "
					+ lContentGroup.id + "</span>";
			lInnerHtml += "</div></div><hr>";
		}

		$('#content_groups_list').empty().html(lInnerHtml);
	} catch (err) {
		handleError("handleDisplayContentGroups_Callback", err);
	} finally {

		log("handleDisplayContentGroups_Callback", "Exiting");
	}
}

function displayContent(pApplicationId, pContentGroupId) {
	// load entry info via ajax
	log("displayContent", "Entering");
	try {

		window.location.href = '/' + pApplicationId + '/' + pContentGroupId
				+ '/content';
	} catch (err) {
		handleError("displayContent", err);
	} finally {

		log("displayContent", "Exiting");
	}

}

function updateContentGroupEnabled(pContentGroupId, pContentGroupEnabled,
		pElementName) {
	openWait();
	log("updateContentGroupEnabled", "Entering");
	var lElementName = "#" + pElementName;

	try {
		var lDate = new Date();
		var lContentGroupObj = {
			id : pContentGroupId,
			enabled : pContentGroupEnabled,
			timeUpdatedMs : lDate.getTime(),
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)
		};
		var lContentGroupObjString = JSON.stringify(lContentGroupObj, null, 2);
		var jqxhr = $.ajax({
			url : "/secured/contentgroup/enabled",
			type : "PUT",
			data : lContentGroupObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					getContentGroups(mSelectedApplication.id);
					// window.location.href = '/contentgroups';
				},
				400 : function(text) {
					try {
						$('#contentgroup_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("updateContentGroupEnabled", err);
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
		handleError("updateContentGroupEnabled", err);
	} finally {
		log("updateContentGroupEnabled", "Exiting");
	}
}
function displayContentGroupStats(id, name) {
	log("displayContentGroupStats", "Entering");
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/contentgroupadstats/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(adStats) {
					$("#contentgroupAccordianGroup" + id).popover(
							{
								selector : '#contentgroupAccordianGroup' + id,
								content : 'Clicks: ' + adStats.clicks
										+ ', Impressions: '
										+ adStats.impressions
							});
					$("#contentgroupAccordianGroup" + id).popover('toggle');
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("displayContentGroupStats", err);
		;
	} finally {
		log("displayContentGroupStats", "Exiting");
	}
}

function editContentGroup(id) {
	log("editContentGroup", "Entering");
	try {
		$('#contentgroup_errors').hide();

		$('#contentgroup_cancel_button').unbind();
		$('#contentgroup_cancel_button').click(function() {
			$('#content_group_create').hide();
			$('#content_groups_list').show();
		});
		$('#content_groups_list').hide();
		$('#content_group_create').show();
		// load entry info via ajax
		var url = "/secured/contentgroup/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(contentgroup) {

							$('#application_id')
									.val(contentgroup.applicationId);
							$('#contentgroup_id').val(contentgroup.id);
							$('#contentgroup_name').val(contentgroup.name);
							$('#contentgroup_description').val(
									contentgroup.description);
							$('#contentgroup_start_date')
									.val(
											getDisplayDate(contentgroup.startDateIso8601));
							$('#contentgroup_end_date')
									.val(
											getDisplayDate(contentgroup.endDateIso8601));
							// add more
							$('#contentgroup_userid').val(
									contentgroup.sponsoredUserId);

							log("editContentGroup", "ContentGroup enabled: "
									+ contentgroup.enabled);
							if (contentgroup.enabled == true) {
								$('#contentgroup_enabled').attr('checked',
										'checked');
							} else {
								$('#contentgroup_enabled')
										.removeAttr('checked');
							}
							$('#contentgroup_save_button').html('update');

							// not using valid.fndtn.abide & invalid.fndtn.abide
							// as it
							// causes the form to be submitted twice. Instead
							// use the
							// deprecated valid & invalid
							$('#contentGroupForm').on(
									'invalid',
									function() {
										var invalid_fields = $(this).find(
												'[data-invalid]');
										console.log(invalid_fields);
									}).on('valid', function() {
								updateContentGroup();
							});
							// unbind click listener to reset

							$('#contentgroup_cancel_button').unbind();
							$('#contentgroup_cancel_button').click(function() {
								$('#content_group_create').hide();
								$('#content_groups_list').show();
							});

							$('#contentgroup_errors').empty();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						console.log(errorThrown);
						$('#contentgroup_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#contentgroup_errors').show();
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editContentGroup", err);
	} finally {
		log("editContentGroup", "Exiting");
	}
}

function newContentGroup() {
	log("newContentGroup", "Entering");
	try {
		$('#contentgroup_errors').hide();

		$('#contentgroup_save_button').html('create');
		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#contentGroupForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			console.log(invalid_fields);
		}).on('valid', function() {
			createContentGroup();
		});
		$('#contentgroup_cancel_button').unbind();
		$('#contentgroup_cancel_button').click(function() {
			$('#content_group_create').hide();
			$('#content_groups_list').show();
		});

		$('#application_id').val(mSelectedApplication.id);
		$('#contentgroup_id').val('');
		$('#contentgroup_name').val('');
		$('#contentgroup_description').val('');
		$('#contentgroup_start_date').val(getCurrentDisplayDate());
		$('#contentgroup_end_date').val('');
		$('#contentgroup_userid').val('');

		// set default
		$('#contentgroup_enabled').attr('checked', 'checked');

		$('#contentgroup_errors').empty();
	} catch (err) {
		handleError("newContentGroup", err);
	} finally {
		log("newContentGroup", "Exiting");
	}
}

function createContentGroup() {
	log("createContentGroup", "Entering");
	try {
		$('#progress_bar').show();
		$('.button').addClass('disabled');
		
		var _enabled;
		if ($('#contentgroup_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var _date = new Date();
		var _timeCreated = _date.getTime();

		var contentgroupObj = {
			id : $('#contentgroup_id').val(),
			applicationId : $('#application_id').val(),
			name : $('#contentgroup_name').val(),
			description : $('#contentgroup_description').val(),
			startDateIso8601 : getTransferDate($('#contentgroup_start_date')
					.val()),
			endDateIso8601 : getTransferDate($('#contentgroup_end_date').val()),
			enabled : _enabled,
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)

		};
		var contentgroupObjString = JSON.stringify(contentgroupObj, null, 2);
		// alert(contentgroupObjString);
		// create via sync call
		var jqxhr = $
				.ajax({
					url : "/secured/contentgroup",
					type : "POST",
					data : contentgroupObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						201 : function() {
							$('#content_group_create').hide();
							location.reload();
							// $('#content_group_create').hide();
							// getContentGroups(mSelectedApplication.id);
							// $('#content_groups_list').show();
						},
						400 : function(text) {
							try {
								$('#contentgroup_errors').html(
										getErrorMessages(text));
								$('#contentgroup_errors').show();
							} catch (err) {
								handleError("submitContentGroup", err);
							}
						},
						error : function(xhr, textStatus, errorThrown) {
							console.log(errorThrown);
							$('#contentgroup_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#contentgroup_errors').show();
						},
						complete : function(xhr, textStatus) {
							$('.meter').css("width", "100%");
							$('.button').removeClass('disabled');
							console.log(xhr.status);
						}
					}
				});

		return false;
	} catch (err) {
		handleError("submitContentGroup", err);
	} finally {
		log("createContentGroup", "Exiting");
	}
}

function updateContentGroup() {

	log("updateContentGroup", "Entering");
	$('#progress_bar').show();
	$('.button').addClass('disabled');
	var _enabled;

	if ($('#contentgroup_enabled').is(':checked')) {
		_enabled = true;
	} else {
		_enabled = false;
	}

	try {
		var _date = new Date();
		var contentgroupObj = {
			id : $('#contentgroup_id').val(),
			applicationId : $('#application_id').val(),
			name : $('#contentgroup_name').val(),
			description : $('#contentgroup_description').val(),
			startDateIso8601 : getTransferDate($('#contentgroup_start_date')
					.val()),
			endDateIso8601 : getTransferDate($('#contentgroup_end_date').val()),
			enabled : _enabled,
			timeUpdatedMs : _date.getTime(),
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var contentgroupObjString = JSON.stringify(contentgroupObj, null, 2);
		var jqxhr = $
				.ajax({
					url : "/secured/contentgroup",
					type : "PUT",
					data : contentgroupObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function() {
							$('#content_group_create').hide();
							location.reload();
							// $('#content_group_create').hide();
							// getContentGroups(mSelectedApplication.id);
							// $('#content_groups_list').show();
						},
						400 : function(text) {
							try {
								$('#contentgroup_errors').html(
										getErrorMessages(text));
								$('#contentgroup_errors').show();
							} catch (err) {
								handleError("updateContentGroup", err);
							}
						},
						error : function(xhr, textStatus, errorThrown) {
							console.log(errorThrown);
							$('#contentgroup_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#contentgroup_errors').show();
						},
						complete : function(xhr, textStatus) {
							$('.meter').css("width", "100%");
							$('.button').removeClass('disabled');
							console.log(xhr.status);
						}
					}
				});

		return false;
	} catch (err) {
		handleError("updateContentGroup", err);
	} finally {
		log("updateContentGroup", "Exiting");
	}
}

function deleteContentGroup(id) {
	log("deleteContentGroup", "Entering");
	try {
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayConfirm("Are you sure you want to delete this ContentGroup?",
				function() {
					wait("confirm_wait");
					var url = "/secured/contentgroup/" + id + "/"
							+ _timeUpdatedMs + "/"
							+ _timeUpdatedTimeZoneOffsetMs;
					var jqxhr = $.ajax({
						url : url,
						type : "DELETE",
						contentType : "application/json",
						statusCode : {
							200 : function() {
								$('#content_group_create').hide();
								location.reload();
								// getContentGroups(mSelectedApplication.id);
							}
						}
					});
					jqxhr.always(function(msg) {
						clearWait("confirm_wait");
					});
				});
	} catch (err) {
		handleError("deleteContentGroup", err);
	} finally {
		log("deleteContentGroup", "Exiting");
	}

}

/** *End ContentGroup***************************************** */
