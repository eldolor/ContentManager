/** *Begin Ad Group***************************************** */
function getAdGroups(campaignId) {
	log("getAdGroups", "Entering")
	try {
		// open wait div
		openWait();

		// manage state
		selectedCampaign(campaignId);

		var url = "secured/adgroups/" + campaignId;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(adgroups) {
					handleDisplayAdGroups_Callback(adgroups);
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("getAdGroups", err);
		// close wait div
		;
	}
	log("getAdGroups", "Exiting")
}

function handleDisplayAdGroups_Callback(adGroups) {
	log("handleDisplayAdGroups_Callback", "Entering")
	try {
		var innerHtml = "<div class=\"accordion\" id=\"adGroupAccordian\">";
		for (var int = 0; int < adGroups.length; int++) {
			var adGroup = adGroups[int];
			var _innerHtml = "<div class=\"accordion-group\" id=\"adGroupAccordianGroup"
					+ adGroup.id
					+ "\">"
					+ "<div class=\"accordion-heading\">"
					+ "<a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#adGroupAccordianGroup"
					+ adGroup.id
					+ "\" href=\"#"
					+ adGroup.id
					+ "\"><div onclick=\"getAdsByAdGroup('"
					+ adGroup.id
					+ "','"
					+ adGroup.name
					+ "')\"><strong>"
					+ adGroup.name
					+ "</strong>&nbsp;"
					// + getDisplayDate(adGroup.startDateIso8601)
					+ "</div></div>"
					+ "<div id=\""
					+ adGroup.id
					+ "\" class=\"accordion-body collapse in\">"
					+ "<div class=\"accordion-inner\">"
					+ adGroup.description
					+ "<p class=\"text-center\">&nbsp;</p>"
					+ "<ul class=\"inline\">"
					+ "<li><a href=\"javascript:void(0)\""
					+ "onclick=\"editAdGroup('"
					+ adGroup.id
					+ "')\"><i class=\"icon-edit\"></i> edit</a>"
					+ "</li>"
					+ "<li><a href=\"javascript:void(0)\" onclick=\"deleteAdGroup('"
					+ adGroup.campaignId
					+ "','"
					+ adGroup.id
					+ "')\"><i class=\"icon-trash\"></i> delete</a>"
					+ "</li>"
					+ "<li>"
					+ "<a href=\"javascript:void(0)\" onclick=\"getAdsByAdGroup('"
					+ adGroup.id
					+ "','"
					+ adGroup.name
					+ "')\"><i class=\"icon-folder-open\"></i> view ads</a>"
					+ "</li>"
					+ "<li>"
					+ "<a href=\"javascript:void(0)\" onclick=\"displayAdGroupStats('"
					+ adGroup.id
					+ "','"
					+ adGroup.name
					+ "')\"><i class=\"icon-book\"></i> statistics</a>"
					+ "</li>" + "</ul>"

					+ "</div>" + "</div>" + "</div>";

			innerHtml += _innerHtml + "<p class=\"text-center\">&nbsp;</p>";
		}
		innerHtml += "</div>";

		$('#entries').html(innerHtml);
		// $('#titleBoxHeader').html('Ad Groups');

		var breadCrumbsHtml = "<div><ul class=\"breadcrumb breadcrumb-fixed-top\"><li><a href=\"javascript:void(0)\" onclick=\"getCampaigns()\">campaigns</a></li>"
				+ "<li><span class=\"divider\"> / </span></li>"
				+ "<li><strong>"
				+ mSelectedCampaign.name
				+ "</strong></li>"
				+ "<li><span class=\"divider\"> / </span></li>"
				+ "<li><a href=\"javascript:void(0);\" onclick=\"newAdGroup('"
				+ mSelectedCampaign.id
				+ "')\"> <img alt=\"\" src=\"resources/images/plus-box-16x16.png\" height=\"16\" width=\"16\" />&nbsp;create new ad group</a></li></ul></div>";
		$('#breadcrumbs').html(breadCrumbsHtml);

		$('a[href="#TODO"]').click(function() {
			alert('Nothing to see here, boss.');
		});

		$('#entries .entry .expando').click(
				function() {
					$(this).closest('.entry').toggleClass('open').find('.body')
							.slideToggle().end();
				});
	} catch (err) {
		handleError("handleDisplayAdGroups_Callback", err);
	} finally {
		// close wait div
		;
	}
	log("handleDisplayAdGroups_Callback", "Exiting")
}

function displayAdGroupStats(id, name) {
	log("displayAdGroupStats", "Entering")
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/adgroupadstats/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(adStats) {
					$("#adGroupAccordianGroup" + id).popover(
							{
								selector : '#adGroupAccordianGroup' + id,
								content : 'Clicks: ' + adStats.clicks
										+ ', Impressions: '
										+ adStats.impressions
							});
					$("#adGroupAccordianGroup" + id).popover('toggle');

				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("displayAdGroupStats", err);
		// close wait div
		;
	}
	log("displayAdGroupStats", "Exiting")
}

function editAdGroup(id) {
	log("editAdGroup", "Entering")
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "secured/adgroup/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(adGroup) {
					$('#adgroup_setup_header').toggle(false);
					$('#adgroup_save_button').html('update ad group');
					// unbind click listener to reset
					$('#adgroup_save_button').unbind();
					$('#adgroup_save_button').bind('click', updateAdGroup);
					$('#adgroup_cancel_button').click(function() {
						$('#adgroup_save_button').unbind();
						;
					});
					$('#adgroup_id').val(adGroup.id);
					$('#adgroup_campaign_id').val(adGroup.campaignId);
					$('#adgroup_name').val(adGroup.name);
					$('#adgroup_description').val(adGroup.description);

					$('#adgroup_errors').empty();
					// close wait div
					;
					openPopup("adgroup_div");
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editAdGroup", err);
		// close wait div
		;
	}
	log("editAdGroup", "Exiting")
}

function selectedAdGroup(id) {
	log("selectedAdGroup", "Entering")
	try {
		// load entry info via sync call
		var url = "secured/adgroup/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(adGroup) {
					mSelectedAdGroup = adGroup;
				}
			}
		});
	} catch (err) {
		handleError("selectedAdGroup", err);
	}
	log("selectedAdGroup", "Exiting")
}

function newAdGroup(campaignId) {
	log("newAdGroup", "Entering")
	try {
		$('#adgroup_setup_header').toggle(true);
		$('#adgroup_save_button').html('create ad group');
		// unbind click listener to reset
		$('#adgroup_save_button').unbind();
		$('#adgroup_save_button').bind('click', createAdGroup);
		$('#adgroup_cancel_button').click(function() {
			$('#adgroup_save_button').unbind();
			;
		});

		$('#adgroup_id').val('');
		$('#adgroup_campaign_id').val(campaignId);
		$('#adgroup_name').val('');
		$('#adgroup_description').val('');

		$('#adgroup_errors').empty();
		openPopup("adgroup_div");
	} catch (err) {
		handleError("newAdGroup", err);
	}
	log("newAdGroup", "Exiting")
}

function toggleAdGroupType(preload) {
	log("toggleAdGroupType", "Entering")
	try {
		if (preload) {
			wait("adgroup_wait");
			var url = "secured/sampleadgroup";
			var jqxhr = $.ajax({
				url : url,
				type : "GET",
				contentType : "application/json",
				statusCode : {
					200 : function(adGroup) {
						$('#adgroup_id').val(adGroup.id);
						$('#adgroup_name').val(adGroup.name);
						$('#adgroup_description').val(adGroup.description);
						$('#adgroup_errors').empty();
					}
				}
			});
			jqxhr.always(function(msg) {
				clearWait('adgroup_wait');
			});
		} else {
			$('#adgroup_id').val('');
			$('#adgroup_name').val('');
			$('#adgroup_description').val('');
			$('#adgroup_errors').empty();
		}
	} catch (err) {
		handleError("toggleAdGroupType", err);
	}
	log("toggleAdGroupType", "Exiting")
}

function deleteAdGroup(campaignId, id) {
	log("deleteAdGroup", "Entering")
	try {
		displayConfirm("Are you sure you want to delete this Ad Group?",
				function() {
					var _timeUpdatedMs = _date.getTime();
					var _timeUpdatedTimeZoneOffsetMs = (_date
							.getTimezoneOffset() * 60 * 1000);
					wait("confirm_wait");
					var url = "secured/adgroup/" + id + "/" + _timeUpdatedMs
							+ "/" + _timeUpdatedTimeZoneOffsetMs;
					var jqxhr = $.ajax({
						url : url,
						type : "DELETE",
						contentType : "application/json",
						async : false,
						statusCode : {
							200 : function() {
								getAdGroups(campaignId);
							}
						}
					});
					jqxhr.always(function(msg) {
						clearWait("confirm_wait");
					});
				});
	} catch (err) {
		handleError("deleteAdGroup", err);
	}
	log("deleteAdGroup", "Exiting")
}

function createAdGroup() {
	log("createAdGroup", "Entering")
	try {
		openWait();
		var campaignId = $('#adgroup_campaign_id').val();
		var _date = new Date();
		var _timeCreated = _date.getTime();

		var adgroupObj = {
			id : $('#adgroup_id').val(),
			campaignId : campaignId,
			name : $('#adgroup_name').val(),
			description : $('#adgroup_description').val(),
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)			
		};
		var adgroupObjString = JSON.stringify(adgroupObj, null, 2);
		// create via sync call
		var jqxhr = $.ajax({
			url : "secured/adgroup",
			type : "POST",
			data : adgroupObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#adgroup_div').modal('hide');
					getAdGroups(campaignId);
				},
				400 : function(text) {
					try {
						$('#adgroup_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("createAdGroup", err);
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
		handleError("createAdGroup", err);
	}
	log("createAdGroup", "Exiting")
}

function updateAdGroup() {
	log("updateAdGroup", "Entering")
	try {
		openWait();
		var campaignId = $('#adgroup_campaign_id').val();
		var _description = $('#adgroup_description').val();
		var _date = new Date();
		var adgroupObj = {
			id : $('#adgroup_id').val(),
			campaignId : campaignId,
			name : $('#adgroup_name').val(),
			description : _description,
			timeUpdatedMs : _date.getTime(),
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var adgroupObjString = JSON.stringify(adgroupObj, null, 2);
		// alert(adgroupObjString);
		// create via sync call
		var jqxhr = $.ajax({
			url : "secured/adgroup",
			type : "PUT",
			data : adgroupObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#adgroup_div').modal('hide');
					getAdGroups(campaignId);
				},
				400 : function(text) {
					try {
						$('#adgroup_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("updateAdGroup", err);
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
		handleError("updateAdGroup", err);
	}
	log("updateAdGroup", "Exiting")
}

/** End Ad Group****************************************** */
