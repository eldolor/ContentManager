jQuery(function($) {
	try {
		log("function($)", "Entering");
		setup();
		// call this post setup
		$(document).foundation('joyride', 'start');
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

		$("#content_start_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#content_start_date'
		});
		$("#content_end_date").datepicker({
			altFormat : 'mm/dd/yy',
			altField : '#content_end_date'
		});
		//set content group name
		$('#contentgroup_name').html('Content Group:&nbsp;'+mSelectedContentGroup.name);

		getContent(mSelectedContentGroup.id);
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
						'<a id=\"left_nav_bar_link_1\" href=\"#\" >Create Content</a></li>');
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
								+ "<a id=\"breadcrumb_content_groups\" href=\"/contentgroups\">Content Groups</a>"
								+ "<a id=\"breadcrumb_content\" href=\"#\">Content</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}

function setSelectedContentGroup(id) {
	log("selectedContentGroup", "Entering");
	try {
		// load entry info via sync call
		var url = "/secured/contentgroup/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(contentgroup) {
					mSelectedContentGroup = contentgroup;
				}
			}
		});
	} catch (err) {
		handleError("selectedContentGroup", err);
	} finally {
		log("selectedContentGroup", "Entering");
	}
}

function getContent(pContentGroupId) {
	log("getContent", "Entering");
	try {

		var jqxhr = $.ajax({
			url : "/secured/" + pContentGroupId + "/content/",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(content) {
					handleDisplayContent_Callback(content);
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

	} catch (err) {
		handleError("getContent", err);
		// close wait div
		;
	} finally {
		log("getContent", "Exiting");
	}
}



function handleDisplayContent_Callback(pContent) {
	log("handleDisplayContent_Callback", "Entering");
	try {
		var lInnerHtml = "<div class=\"row\"> <div class=\"large-6 columns\">";
		for (var int = 0; int < pContent.length; int++) {
			var lContent = pContent[int];
			lInnerHtml += "<p><span data-tooltip class=\"has-tip\" title=\"Click here to view the content in this Content\"><a href=\"javascript:void(0)\" onclick=\"viewContent("
					+ lContent.id + ")\"><strong>";
			lInnerHtml += lContent.name;
			lInnerHtml += "</strong></a></span></p>";

			lInnerHtml += "<ul class=\"inline-list\"> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"editContent("
					+ lContent.id
					+ ")\">edit</a></li> <li><a class=\"small\" href=\"javascript:void(0)\" onclick=\"deleteContent("
					+ lContent.id + ")\">delete</a></li></ul>";
			lInnerHtml += "</div></div><hr>";
		}

		$('#content_list').empty().html(lInnerHtml);
	} catch (err) {
		handleError("handleDisplayContent_Callback", err);
	} finally {

		log("handleDisplayContent_Callback", "Exiting");
	}
}

function updateContentEnabled(pContentId, pContentEnabled, pElementName) {
	openWait();
	log("updateContentEnabled", "Entering");
	var lElementName = "#" + pElementName;

	try {
		var lDate = new Date();
		var lContentObj = {
			id : pContentId,
			enabled : pContentEnabled,
			timeUpdatedMs : lDate.getTime(),
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)
		};
		var lContentObjString = JSON.stringify(lContentObj, null, 2);
		var jqxhr = $.ajax({
			url : "/secured/content/enabled",
			type : "PUT",
			data : lContentObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					window.location.href = '/content';
				},
				400 : function(text) {
					try {
						$('#content_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("updateContentEnabled", err);
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
		handleError("updateContentEnabled", err);
	} finally {
		log("updateContentEnabled", "Exiting");
	}
}
function displayContentStats(id, name) {
	log("displayContentStats", "Entering");
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/contentadstats/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(adStats) {
					$("#contentAccordian" + id).popover(
							{
								selector : '#contentAccordian' + id,
								content : 'Clicks: ' + adStats.clicks
										+ ', Impressions: '
										+ adStats.impressions
							});
					$("#contentAccordian" + id).popover('toggle');
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("displayContentStats", err);
		;
	} finally {
		log("displayContentStats", "Exiting");
	}
}

function editContent(id) {
	log("editContent", "Entering");
	try {
		$('#content_errors').hide();
		
		$('#content_cancel_button').unbind();
		$('#content_cancel_button').click(function() {
			$('#content_create').hide();
			$('#content_list').show();
		});
		$('#content_list').hide();
		$('#content_create').show();
		// load entry info via ajax
		var url = "/secured/content/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(content) {

					$('#content_id').val(content.id);
					//set the content group id
					$('#contentgroup_id').val(content.contentGroupId);
					
					$('#content_name').val(content.name);
					$('#content_description').val(content.description);
					$('#content_start_date').val(
							getDisplayDate(content.startDateIso8601));
					$('#content_end_date').val(
							getDisplayDate(content.endDateIso8601));
					// // add more
					// $('#content_userid').val(
					// content.sponsoredUserId);

					if (content.hasOwnProperty('enabled')) {
						log("editContent", "Content enabled: "
								+ content.enabled);
						if (content.enabled == true) {
							$('#content_enabled').attr('checked', 'checked');
							$('#content_status').html('Enabled');
						} else {
							$('#content_enabled').removeAttr('checked');
							$('#content_status').html('Disabled');
						}
					}
					$('#content_enabled').unbind();
					$('#content_enabled').bind('click', function() {
						if ($('#content_enabled').is(':checked')) {
							$('#content_status').html('Enabled');
						} else {
							$('#content_status').html('Disabled');
						}
					});

					$('#content_save_button').html('update');

					// unbind click listener to reset
					$('#content_save_button').unbind();
					$('#content_save_button').bind('click', updateContent);

					$('#content_cancel_button').unbind();
					$('#content_cancel_button').click(function() {
						$('#content_save_button').unbind();
						$('#content_create').hide();
						$('#content_list').show();
					});

					$('#content_errors').empty();
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editContent", err);
	} finally {
		log("editContent", "Exiting");
	}
}

function selectedContent(id) {
	log("selectedContent", "Entering");
	try {
		// load entry info via sync call
		var url = "/secured/content/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(content) {
					mSelectedContent = content;
				}
			}
		});
	} catch (err) {
		handleError("selectedContent", err);
	} finally {
		log("selectedContent", "Entering");
	}
}
function viewContent(pContentId) {
	// load entry info via ajax
	log("viewContent", "Entering");
	try {
		
		alert('view content');
	} catch (err) {
		handleError("viewContent", err);
	} finally {

		log("viewContent", "Exiting");
	}

}
function newContent() {
	log("newContent", "Entering");
	try {

		//set the content group id
		$('#contentgroup_id').val(mSelectedContentGroup.id);
		$('#content_id').val('');
		$('#content_errors').hide();
		
		
		$('#content_name').val('');
		$('#content_description').val('');

		// $('#content_start_date').val(getCurrentDisplayDate());
		// $('#content_end_date').val('');
		// default to the dates set for the content group
		$('#content_start_date').val(
				getDisplayDate(mSelectedContentGroup.startDateIso8601));
		$('#content_end_date').val(
				getDisplayDate(mSelectedContentGroup.endDateIso8601));

		$('#content_userid').val('');

		// set default
		$('#content_enabled').attr('checked', 'checked');
		$('#content_enabled').unbind();
		$('#content_enabled').bind('click', function() {
			if ($('#content_enabled').is(':checked')) {
				$('#content_status').html('Enabled');
			} else {
				$('#content_status').html('Disabled');
			}
		});

		$('#content_errors').empty();
		
		$('#content_save_button').html('create');
		// unbind click listener to reset
		$('#content_save_button').unbind();
		$('#content_save_button').click(createContent);
		$('#content_cancel_button').unbind();
		$('#content_cancel_button').click(function() {
			$('#content_save_button').unbind();
			$('#content_create').hide();
			$('#content_list').show();
		});
		
		$('#content_list').hide();
		$('#content_create').show();

	} catch (err) {
		handleError("newContent", err);
	} finally {
		log("newContent", "Exiting");
	}
}

function createContent() {
	log("createContent", "Entering");
	try {
		openWait();
		var _enabled;
		if ($('#content_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var _date = new Date();
		var _timeCreated = _date.getTime();

		var contentObj = {
			id : $('#content_id').val(),
			contentGroupId : $('#contentgroup_id').val(),
			name : $('#content_name').val(),
			description : $('#content_description').val(),
			startDateIso8601 : getTransferDate($('#content_start_date').val()),
			endDateIso8601 : getTransferDate($('#content_end_date').val()),
			enabled : _enabled,
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)

		};
		var contentObjString = JSON.stringify(contentObj, null, 2);
		// alert(contentObjString);
		// create via sync call
		var jqxhr = $.ajax({
			url : "/secured/content",
			type : "POST",
			data : contentObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#content_create').hide();
					getContent(mSelectedContentGroup.id);
					$('#content_list').show();
				},
				400 : function(text) {
					try {
						$('#content_errors').html(getErrorMessages(text));
						$('#content_errors').show();
					} catch (err) {
						handleError("createContent", err);
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
		handleError("createContent", err);
	} finally {
		log("createContent", "Exiting");
	}
}

function updateContent() {
	openWait();
	log("updateContent", "Entering");
	var _enabled;

	if ($('#content_enabled').is(':checked')) {
		_enabled = true;
	} else {
		_enabled = false;
	}

	try {
		var _date = new Date();
		var contentObj = {
			id : $('#content_id').val(),
			contentGroupId : $('#contentgroup_id').val(),
			name : $('#content_name').val(),
			description : $('#content_description').val(),
			startDateIso8601 : getTransferDate($('#content_start_date').val()),
			endDateIso8601 : getTransferDate($('#content_end_date').val()),
			enabled : _enabled,
			timeUpdatedMs : _date.getTime(),
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var contentObjString = JSON.stringify(contentObj, null, 2);
		var jqxhr = $.ajax({
			url : "/secured/content",
			type : "PUT",
			data : contentObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function() {
					$('#content_create').hide();
					getContent(mSelectedContentGroup.id);
					$('#content_list').show();
				},
				400 : function(text) {
					try {
						$('#content_errors').html(getErrorMessages(text));
						$('#content_errors').show();
					} catch (err) {
						handleError("updateContent", err);
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
		handleError("updateContent", err);
	} finally {
		log("updateContent", "Exiting");
	}
}

function deleteContent(id) {
	log("deleteContent", "Entering");
	try {
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayConfirm("Are you sure you want to delete this Content?",
				function() {
					wait("confirm_wait");
					var url = "/secured/content/" + id + "/" + _timeUpdatedMs
							+ "/" + _timeUpdatedTimeZoneOffsetMs;
					var jqxhr = $.ajax({
						url : url,
						type : "DELETE",
						contentType : "application/json",
						statusCode : {
							200 : function() {
								getContent(mSelectedContentGroup.id);
							}
						}
					});
					jqxhr.always(function(msg) {
						clearWait("confirm_wait");
					});
				});
	} catch (err) {
		handleError("deleteContent", err);
	} finally {
		log("deleteContent", "Exiting");
	}

}

/** *End Content***************************************** */
