function setSelectedApplication(id) {
	log("setSelectedApplication", "Entering");
	try {
		// load entry info via sync call
		var url = "/secured/application/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : false,
					statusCode : {
						200 : function(application) {
							mSelectedApplication = application;
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#contentgroup_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
		$('#content_progress_bar').show();

		var jqxhr = $
				.ajax({
					url : "/secured/" + pApplicationId + "/contentgroups",
					type : "GET",
					contentType : "application/json",
					async : true,
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
						},
						503 : function() {
							$('#contentgroup_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#contentgroup_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					}
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
		var lInnerHtml = '';
		for (var int = 0; int < pContentGroups.length; int++) {
			var lContentGroup = pContentGroups[int];
			// lInnerHtml += "<div class=\"row\"> <div class=\"large-6
			// columns\">";
			// lInnerHtml += "<p><span data-tooltip class=\"has-tip\"
			// title=\"Click here to view the content in this Content Group\"><a
			// href=\"javascript:void(0)\" onclick=\"displayContent(";
			// lInnerHtml += lContentGroup.applicationId;
			// lInnerHtml += ", ";
			// lInnerHtml += lContentGroup.id;
			// lInnerHtml += ")\"><strong>";
			// lInnerHtml += lContentGroup.name;
			// lInnerHtml += "</strong></a></span></p>";
			//
			// lInnerHtml += "<ul class=\"inline-list\"> <li><a class=\"small\"
			// href=\"javascript:void(0)\" onclick=\"editContentGroup("
			// + lContentGroup.id
			// + ")\">edit</a></li> <li><a class=\"small\"
			// href=\"javascript:void(0)\" onclick=\"deleteContentGroup("
			// + lContentGroup.id + ")\">delete</a></li></ul>";
			// lInnerHtml += "<span id=\"content_group_id\" class=\"secondary
			// radius label\">Content Group Id: "
			// + lContentGroup.id + "</span>";
			// lInnerHtml += "</div></div><hr>";
			//			

			lInnerHtml += "<div class=\"blog_content\"> ";
			lInnerHtml += " <h3 class=\"gray\">";
			lInnerHtml += lContentGroup.name;
			lInnerHtml += "</h3>";
			lInnerHtml += "<div class=\"blog_content_details float_left\">";
			lInnerHtml += "<ul> <li class=\"green\">Content Group Id: "
					+ lContentGroup.id
					+ " </li><li>|</li><li class=\"light_gray\"><a class=\"small green\" href=\"javascript:void(0)\" onclick=\"displayContent("
			lInnerHtml += lContentGroup.applicationId;
			lInnerHtml += ", ";
			lInnerHtml += lContentGroup.id;
			lInnerHtml += ")\"><i class=\"fi-list-number light_gray\"></i>&nbsp;content</a></li> <li>|</li><li class=\"light_gray\"><a class=\"small\" href=\"javascript:void(0)\" onclick=\"editContentGroup("
					+ lContentGroup.id
					+ ")\"><i class=\"fi-page-edit light_gray\"></i>&nbsp;edit</a></li><li>|</li> <li class=\"light_gray\"><a class=\"small\" href=\"javascript:void(0)\" onclick=\"deleteContentGroup("
					+ lContentGroup.id
					+ ")\"><i class=\"fi-page-delete light_gray\"></i>&nbsp;delete</a></li>";
			lInnerHtml += "</ul>";
			lInnerHtml += "</div>";
			var lEpochDate = (lContentGroup.timeUpdatedMs == null) ? lContentGroup.timeCreatedMs
					: lContentGroup.timeUpdatedMs;
			var lDisplayDate = getFormattedDisplayDate(lEpochDate, "ll");
			var lSplit = lDisplayDate.split(",");
			var lYear = lSplit[1];
			var lSplitM = lSplit[0].split(" ");
			var lMonth = lSplitM[0];
			var lDate = lSplitM[1];

			lInnerHtml += "<div class=\"blog_date green text_center\"> <span>";
			lInnerHtml += lDate;
			lInnerHtml += "</span>";
			lInnerHtml += "<div>";
			lInnerHtml += lMonth + "</div>";
			lInnerHtml += "</div>";

			lInnerHtml += "<div class=\"blog_comments text_center green\">";
			lInnerHtml += lYear;
			lInnerHtml += "</div>";
			lInnerHtml += "<div class=\"blog_content_details float_left\"><p class=\"light_gray\">"
					+ lContentGroup.description + "</p>";
			lInnerHtml += "</div>";
			lInnerHtml += "<div class=\"clearfix\"></div><div class=\"separator\"></div>";
			lInnerHtml += "</div>";
		}

		$('#content_groups_list').empty().html(lInnerHtml);
		// progress bar
		$('#content_progress_bar').css("width", "100%");
		$('#content_progress_bar').hide();
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
		var jqxhr = $
				.ajax({
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
								$('#contentgroup_errors').html(
										getErrorMessages(text));
							} catch (err) {
								handleError("updateContentGroupEnabled", err);
							}
						},
						503 : function() {
							$('#contentgroup_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#contentgroup_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(adStats) {
							$("#contentgroupAccordianGroup" + id)
									.popover(
											{
												selector : '#contentgroupAccordianGroup'
														+ id,
												content : 'Clicks: '
														+ adStats.clicks
														+ ', Impressions: '
														+ adStats.impressions
											});
							$("#contentgroupAccordianGroup" + id).popover(
									'toggle');
						},
						503 : function() {
							$('#contentgroup_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#contentgroup_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
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
		$('#progress_bar_top, #progress_bar_bottom').show();
		$('.button').addClass('disabled');
		// reset the form contents
		$('#contentGroupForm').trigger("reset");
		$('#cm_errors_container').hide();

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
					async : true,
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
										log(invalid_fields);
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
						},
						503 : function() {
							$('#contentgroup_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#contentgroup_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					},
					complete : function(xhr, textStatus) {
						$('#progress_bar_top, #progress_bar_bottom').css(
								"width", "100%");
						$('#progress_bar_top, #progress_bar_bottom').hide();
						$('.button').removeClass('disabled');
						log(xhr.status);
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
		// reset the form contents
		$('#contentGroupForm').trigger("reset");

		$('#cm_errors_container').hide();

		$('#contentgroup_save_button').html('create');
		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#contentGroupForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			log(invalid_fields);
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
		$('#progress_bar_top, #progress_bar_bottom').show();
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
								$('#cm_errors_container').show();
							} catch (err) {
								handleError("submitContentGroup", err);
							}
						},
						503 : function() {
							$('#contentgroup_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#contentgroup_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					},
					complete : function(xhr, textStatus) {
						$('#progress_bar_top, #progress_bar_bottom').css(
								"width", "100%");
						$('#progress_bar_top, #progress_bar_bottom').hide();
						$('.button').removeClass('disabled');
						log(xhr.status);
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
	$('#progress_bar_top, #progress_bar_bottom').show();
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
								$('#cm_errors_container').show();
							} catch (err) {
								handleError("updateContentGroup", err);
							}
						},
						503 : function() {
							$('#contentgroup_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#contentgroup_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					},
					complete : function(xhr, textStatus) {
						$('#progress_bar_top, #progress_bar_bottom').css(
								"width", "100%");
						$('.button').removeClass('disabled');
						log(xhr.status);
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

		displayConfirm(
				"Are you sure you want to delete this ContentGroup?",
				function() {
					wait("confirm_wait");
					var url = "/secured/contentgroup/" + id + "/"
							+ _timeUpdatedMs + "/"
							+ _timeUpdatedTimeZoneOffsetMs;
					var jqxhr = $
							.ajax({
								url : url,
								type : "DELETE",
								contentType : "application/json",
								statusCode : {
									200 : function() {
										$('#content_group_create').hide();
										location.reload();
										// getContentGroups(mSelectedApplication.id);
									},
									503 : function() {
										$('#contentgroup_errors')
												.html(
														'Unable to process the request. Please try again later');
										$('#cm_errors_container').show();
									}
								},
								error : function(xhr, textStatus, errorThrown) {
									log(errorThrown);
									$('#contentgroup_errors')
											.html(
													'Unable to process the request. Please try again later');
									$('#cm_errors_container').show();
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
