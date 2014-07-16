/** Begin Ad****************************************** */

function getAllAds() {
	log("getAllAds", "Entering");
	try {
		// open wait div
		openWait();

		// manage state
		//selectedAdGroup(adGroupId);

		var url = "secured/viewallads";
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(ads) {
					handleViewAllAds_Callback(ads);
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("getAllAds", err);
		// close wait div
		//
	}
	log("getAllAds", "Exiting")
}

function getAdsByAdGroup(adGroupId) {
	log("getAdsByAdGroup(adGroupId)", "Entering");
	try {
		// open wait div
		openWait();
		log("ad group id: " + adGroupId);

		// manage state
		selectedAdGroup(adGroupId);

		var url = "secured/ads/" + adGroupId;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(ads) {
					handleDisplayAds_Callback(adGroupId, ads);
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("getAdsByAdGroup(adGroupId)", err);
		// close wait div
		//
	}
	log("getAdsByAdGroup(adGroupId)", "Exiting")
}
function getAdsByType(campaignId, type) {
	log("getAdsByType(campaignId, type)", "Entering")
	try {
		log("campaign id: " + campaignId);
		log("type: " + type);
		// open wait div
		openWait();
		var _ads = "";
		var url = "secured/ads/" + campaignId + "/" + type;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(ads) {
					// close wait div
					//
					_ads = ads;
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
		return _ads;
	} catch (err) {
		handleError("getAdsByType(campaignId, type)", err);
		// close wait div
		//
	}
	log("getAdsByType(campaignId, type)", "Exiting");
}

function handleDisplayAds_Callback(adGroupId, ads) {
	log("handleDisplayAds_Callback", "Entering")
	try {
		var innerHtml = "<div class=\"accordion\" id=\"adAccordian\"> ";
		if (ads != null) {
			for (var int = 0; int < ads.length; int++) {
				var ad = ads[int];
				var enabled = ad.enabled;
				if (ad != null) {

					var _innerHtml = "<div class=\"accordion-group\" id=\"adAccordianGroup"
							+ ad.id
							+ "\">"
							+ "<div class=\"accordion-heading\">"
							+ "<div class=\"accordion-toggle\"><strong>";
					if (!enabled) {
						_innerHtml += "<span class=\"label label-important\">[Disabled] </span>";
					}
					if (ad.type == 'survey') {
						_innerHtml += "Survey";
					} else {
						_innerHtml += ad.name;
					}
					_innerHtml += "</strong>&nbsp;<small>"
							+ getDisplayDate(ad.startDateIso8601)
							+ "</small></div>" + "<div id=\"" + ad.id
							+ "\" class=\"accordion-body collapse in\">"
							+ "<div class=\"accordion-inner\">";
					if (ad.type == 'survey') {
						_innerHtml += ad.question;
					} else {
						_innerHtml += ad.description;
					}

					_innerHtml += "<p class=\"text-center\">&nbsp;</p>"
							+ "<ul class=\"inline\">";

					if (ad.type == 'video_ad') {
						_innerHtml += "<li><a href=\"javascript:void(0)\""
								+ "onclick=\"viewVideo('"
								+ ad.id
								+ "')\"><i class=\"icon-facetime-video\"></i> view</a>"
								+ "</li>";
					} else if ((ad.type == 'image_ad')
							|| (ad.type == 'reminder_ad')) {
						_innerHtml += "<li><a href=\"javascript:void(0)\""
								+ "onclick=\"viewImage('"
								+ ad.id
								+ "')\"><i class=\"icon-picture\"></i> view</a>"
								+ "</li>";
					} else if (ad.type == 'voice_ad') {
						_innerHtml += "<li><a href=\"javascript:void(0)\""
								+ "onclick=\"listenAudio('"
								+ ad.id
								+ "')\"><i class=\"icon-music\"></i> listen</a>"
								+ "</li>";
					}

					_innerHtml += "<li><a href=\"javascript:void(0)\""
							+ "onclick=\"editAd('"
							+ ad.id
							+ "')\"><i class=\"icon-edit\"></i> edit</a>"
							+ "</li>"
							+ "<li><a id=\"adDelete"
							+ ad.id
							+ "\" href=\"javascript:void(0)\" onclick=\"deleteAd('"
							+ mSelectedAdGroup.id
							+ "','"
							+ ad.id
							+ "')\"><i class=\"icon-trash\"></i> delete</a>"
							+ "</li>"
							+ "<li>"
							+ "<a href=\"javascript:void(0)\" onclick=\"displayAdAdStats('";
					if (ad.type == 'survey') {
						_innerHtml += ad.id + "','" + ad.question;
					} else {
						_innerHtml += ad.id + "','" + ad.name;
					}
					_innerHtml += "')\"><i class=\"icon-book\"></i> statistics</a>"
							+ "</li>"

							+ "<li>"
							+ "<a id=\"adDailyStats2"
							+ ad.id
							+ "\" href=\"javascript:void(0)\" onclick=\"displayDailyAdStats('"
							+ ad.id
							+ "')\"><i class=\"icon-book\"></i> daily stats</a>"
							+ "</li>";
					var lEnabledElementId = ad.id + "_ad_enabled";
					if (!ad.enabled) {
						_innerHtml += "<li>"
								+ "<a href=\"javascript:void(0)\" onclick=\"updateAdEnabled("
								+ ad.id + ", " + true + ", '"
								+ lEnabledElementId + "'" + ")\"  id=\""
								+ lEnabledElementId + "\" name=\""
								+ lEnabledElementId
								+ "\" ><i class=\"icon-ok\"></i>enable</a>"
								+ "</li>";
					} else {
						_innerHtml += "<li>"
								+ "<a href=\"javascript:void(0)\" onclick=\"updateAdEnabled("
								+ ad.id
								+ ", "
								+ false
								+ ", '"
								+ lEnabledElementId
								+ "'"
								+ ")\"  id=\""
								+ lEnabledElementId
								+ "\" name=\""
								+ lEnabledElementId
								+ "\" ><i class=\"icon-remove\"></i>disable</a>"
								+ "</li>";
					}

					_innerHtml += "</ul>" + "</div>" + "</div>" + "</div>";

					innerHtml += _innerHtml
							+ "<p class=\"text-center\">&nbsp;</p>";
				}
			}
		}

		innerHtml += "</div>";

		var createNewAd = "<div class=\"dropdown pull-right\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\" id=\"dLabel\" role=\"button\" >"
				+ " <img alt=\"\" src=\"resources/images/plus-box-16x16.png\" height=\"16\" width=\"16\" />&nbsp;create new ad</a>"
				+ "<ul class=\"dropdown-menu\" role=\"menu\" aria-labelledby=\"dLabel\">"
				+ "<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"#\" onclick=\"newTextAd('"
				+ mSelectedCampaign.id
				+ "','"
				+ mSelectedAdGroup.id
				+ "')\">Text Ad</a></li>" + "</ul></div>";

		var breadCrumbsHtml = "<ul class=\"breadcrumb breadcrumb-fixed-top\"><li><a href=\"javascript:void(0)\" onclick=\"getCampaigns()\">campaigns</a></li>"
				+ "<li><span class=\"divider\"> / </span></li>"
				+ "<li><a href=\"javascript:void(0)\" onclick=\"getAdGroups('"
				+ mSelectedCampaign.id
				+ "')\"><strong>"
				+ mSelectedCampaign.name
				+ "</strong></a></li>"
				+ "<li><span class=\"divider\"> / </span></li>"
				+ "<li class=\"active\"><strong>"
				+ mSelectedAdGroup.name
				+ "</strong><li><span class=\"divider\"> / </span></li>"
				+ "<li><a href=\"javascript:void(0);\" onclick=\"displaySelectAdType('"
				+ mSelectedCampaign.id
				+ "','"
				+ adGroupId
				+ "')\"> <img alt=\"\" src=\"resources/images/plus-box-16x16.png\" height=\"16\" width=\"16\" />&nbsp;create new ad</a></li></ul></div>";

		$('#breadcrumbs').html(breadCrumbsHtml);

		$('#entries').html(innerHtml);
		// $('#titleBoxHeader').html('Ads');

		$('a[href="#TODO"]').click(function() {
			alert('Nothing to see here.');
		});

		$('#entries .entry .expando').click(
				function() {
					$(this).closest('.entry').toggleClass('open').find('.body')
							.slideToggle().end();
				});
	} catch (err) {
		handleError("handleDisplayAds_Callback", err);
	} finally {
		// close wait div
		//
	}
	log("handleDisplayAds_Callback", "Exiting")
}

function handleViewAllAds_Callback(ads) {
	log("handleViewAllAds_Callback", "Entering");
	
	try {
		var innerHtml = "<div id=\"viewAllAdsDiv\"> ";
		innerHtml += "<script type='text/javascript'>" +
				"$(document).ready( " +
					"function () " +
						"{$('#excelDataTable').dataTable({'sScrollX': '150%','sScrollXInner': '200%', 'aoColumnDefs': [ { 'bVisible': false, 'aTargets': [ 5, 6, 12, 13, 14, 15, 16, 17, 18 ] } ] } );" +
				"} )\;" +
				"</script>";
		if (ads != null) {

			var columns = getColumnHeaders(ads);
			innerHtml += "<div id=\"adsTable\"> ";
			
			innerHtml += "<table id=\"excelDataTable\">"; 
			innerHtml += '<thead><tr>';
			for (var c = 0; c < columns.length; c++) {
				innerHtml += '<th>' + columns[c] + '</th>';
			}
			innerHtml += '</tr></thead>';
			innerHtml += '<tbody>';
			
			for (var i = 0; i < ads.length; i++) {
				innerHtml += '<tr>';
		        for (var colIndex = 0 ; colIndex < columns.length ; colIndex++) {
		            var cellValue = ads[i][columns[colIndex]];

		            if (cellValue == null) { cellValue = ""; }
		            innerHtml += "<td>" + cellValue + "</td>";

		        }
		        innerHtml += '</tr>';
			}
			innerHtml += '</tbody>';
			innerHtml += "</table></div></div>";
		}
		$('#breadcrumbs').remove();
		$('#accordion').remove();
		$('#entries').html(innerHtml);
		
	} catch (err) {
		handleError("handleViewAllAds_Callback", err);
	} finally {
		// close wait div
		//
	}
	log("handleViewAllAds_Callback", "Exiting")
}



function getColumnHeaders(ads) {
    var columnSet = [];

    for (var i = 0 ; i < ads.length ; i++) {
        var rowHash = ads[i];
        for (var key in rowHash) {
            if ($.inArray(key, columnSet) == -1){
                columnSet.push(key);
            }
        }
    }
    return columnSet;
}

function displayAdAdStats(id, name) {
	log("displayAdAdStats", "Entering");
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/adadstats/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(adStats) {
					$("#adAccordianGroup" + id).popover(
							{
								selector : '#adAccordianGroup' + id,
								html : true,
								content : 'Clicks: ' + adStats.clicks
										+ ', Impressions: '
										+ adStats.impressions
							});
					$("#adAccordianGroup" + id).popover('toggle');

				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("displayAdAdStats", err);
		// close wait div

	}
	log("displayAdAdStats", "Exiting");
}

function generateDailyAdStats(dailyAdStats) {
	var returnHTML = "<TABLE BORDER=\"1\">";
	returnHTML += "<TR><TD>Date</TD><TD>Impressions</TD><TD>Clicks</TD><TD>Users</TD></TR>";
	for (var i = 0; i < dailyAdStats.length; i++) {
		var d = new Date(0);
		var stat = dailyAdStats[i];
		returnHTML += "<TR>";
		d.setUTCMilliseconds(stat.date);
		returnHTML += "<TD>" + d.toDateString() + "</TD>";
		returnHTML += "<TD>" + stat.impressions + "</TD>";
		returnHTML += "<TD>" + stat.clicks + "</TD>";
		returnHTML += "<TD>" + stat.users + "</TD>";
		returnHTML += "</TR>";
	}
	returnHTML += "</TABLE>";
	return returnHTML;
}

function displayDailyAdStats(id) {
	try {
		openWait();
		var url = "/secured/dailyadstats/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(dailyAdStats) {
							$("#adAccordianGroup" + id).popover({
								selector : '#adAccordianGroup' + id,
								html : true,
								content : generateDailyAdStats(dailyAdStats)
							});
							$("#adAccordianGroup" + id).popover('toggle');
							$("#adAccordianGroup" + id).data("popover").options.content = generateDailyAdStats(dailyAdStats);
						}
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("displayDailyAdStats", err);
	}
}

function editAd(id) {
	log("editAd", "Entering")
	// save state
	selectedAd(id);

	if (mSelectedAd.type == 'text_ad') {
		editTextAd(id);
	} else if (mSelectedAd.type == 'image_ad') {
		editImageAd(id);
	} else if (mSelectedAd.type == 'video_ad') {
		editVideoAd(id);
	} else if (mSelectedAd.type == 'voice_ad') {
		editVoiceAd(id);
	} else if (mSelectedAd.type == 'reminder_ad') {
		editReminderAd(id);
	} else if (mSelectedAd.type == 'survey') {
		editSurvey(id);
	}
	log("editAd", "Exiting")
}
function selectedAd(id) {
	log("selectedAd", "Entering")
	try {
		// load entry info via sync call
		var url = "secured/ad/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(ad) {
					mSelectedAd = ad;
				}
			}
		});
	} catch (err) {
		handleError("selectedAd", err);
	}
	log("selectedAd", "Exiting")
}

function displaySelectAdType(campaignId, adGroupId) {
	log("displaySelectAdType", "Entering")
	$('#ad_type_campaign_id').val(campaignId);
	$('#ad_type_adgroup_id').val(adGroupId);
	// reset
	$("input:radio[name=ad_type_preload]").unbind();
	$("input:radio[name=ad_type_preload]").click(function() {
		var value = $(this).val();
		$('#ad_type_selected').val(value);
	});
	// reset
	$('#ad_type_continue_button').unbind();
	$('#ad_type_continue_button').one('click', function() {
		try {
			// adGroupId = $('#ad_type_adgroup_id').val();
			var adTypeSelected = $('#ad_type_selected').val();
			// alert(adTypeSelected);
			if (adTypeSelected == 'text_ad') {
				$('#ad_type_div').modal('hide');
				newTextAd(campaignId, adGroupId);
			} else if (adTypeSelected == 'image_ad') {
				$('#ad_type_div').modal('hide');
				newImageAd(campaignId, adGroupId);
			} else if (adTypeSelected == 'video_ad') {
				$('#ad_type_div').modal('hide');
				newVideoAd(campaignId, adGroupId);
			} else if (adTypeSelected == 'voice_ad') {
				$('#ad_type_div').modal('hide');
				newVoiceAd(campaignId, adGroupId);
			} else if (adTypeSelected == 'reminder_ad') {
				$('#ad_type_div').modal('hide');
				newReminderAd(campaignId, adGroupId);
			} else if (adTypeSelected == 'survey') {
				$('#ad_type_div').modal('hide');
				newSurvey(campaignId, adGroupId);
			}
			$('input:radio[name=ad_type_preload]').unbind();
			return false;
		} catch (err) {
			handleError("displaySelectAdType", err);
		}
	});
	openPopup('ad_type_div');
	log("displaySelectAdType", "Exiting")
}

function deleteAd(adGroupId, id) {
	log("deleteAd", "Entering")
	try {
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);
		var _url = "secured/linkedads/" + id + "/" + _timeUpdatedMs + "/"
				+ _timeUpdatedTimeZoneOffsetMs;
		var jqxhr = $
				.ajax({
					url : _url,
					type : "GET",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function(count) {
							if (count.count > 0) {
								// cannot disable this ad, as it is linked to
								// other ads.
								log("deleteAd",
										"Ad cannot be deleted, as it is linked to other ads")
								$("#adDelete" + id)
										.popover(
												{
													selector : '#adDelete' + id,
													content : 'Ad cannot be deleted, as it is linked to '
															+ count.count
															+ ' other ads'
												});
								$("#adDelete" + id).popover('toggle');

							} else {
								displayConfirm(
										"Are you sure you want to delete this Ad?",
										function() {
											wait("confirm_wait");
											var url = "secured/ad/" + id;
											var jqxhr = $
													.ajax({
														url : url,
														type : "DELETE",
														contentType : "application/json",
														async : false,
														statusCode : {
															200 : function() {
																getAdsByAdGroup(adGroupId);
															}
														}
													});
											jqxhr.always(function(msg) {
												clearWait("confirm_wait");
											});
										});

							}
						}
					}
				});
	} catch (err) {
		handleError("deleteAd", err);
	}
	log("deleteAd", "Exiting")
}

function viewVideo(id, title) {
	log("viewVideo", "Entering")
	try {
		// open wait div
		openWait();
		// m4v : "http://www.jplayer.org/video/m4v/Big_Buck_Bunny_Trailer.m4v",
		// ogv : "http://www.jplayer.org/video/ogv/Big_Buck_Bunny_Trailer.ogv",
		// webmv :
		// "http://www.jplayer.org/video/webm/Big_Buck_Bunny_Trailer.webm",

		// load entry info via ajax
		var url = "/secured/ad/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(ad) {
							var _url = "/dropbox/" + ad.uri;
							log("viewVideo", _url);
							// reset
							$("#jquery_jplayer_1").jPlayer("clearMedia");
							$("#jquery_jplayer_1")
									.jPlayer(
											"setMedia",
											{
												m4v : _url,
												ogv : _url,
												webmv : _url,
												poster : "resources/images/mavin_logo_orange_on_white_480x271.jpg"
											});

							$('#video_title').html(ad.name);
							$('#view_video_modal_label').html(ad.name);

							$('#jp_container_1').modal('show');

							$('#view_video_done_button').unbind();
							$('#view_video_done_button').one(
									'click',
									function() {
										try {
											$("#jquery_jplayer_1").jPlayer(
													"stop");
										} catch (err) {
											handleError("viewVideo", err);
										}
									});
						}
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("viewVideo", err);
		// close wait div

	}
	log("viewVideo", "Exiting")
}

function viewImage(id) {
	log("viewImage", "Entering")
	try {
		// open wait div
		openWait();

		// load entry info via ajax
		var url = "secured/ad/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(ad) {
					var _url = "/dropbox/" + ad.uri;
					log("viewImage", _url);
					$("#ad_image_widget").attr("src", _url);
					// close wait div

					$('#view_image_modal_label').html(ad.name);
					openPopup("view_ad_image_div");
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("viewImage", err);
		// close wait div

	}
	log("viewImage", "Exiting")

}

function listenAudio(id) {
	log("listenAudio", "Entering")
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "secured/ad/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(ad) {
					var _url = "/dropbox/" + ad.uri;
					log("listenAudio", _url);
					// reset
					$("#jquery_jplayer_2").jPlayer("clearMedia");
					$("#jquery_jplayer_2").jPlayer("setMedia", {
						m4a : _url,
						oga : _url
					// mp3:"http://www.jplayer.org/audio/mp3/TSP-01-Cro_magnon_man.mp3",
					// oga:"http://www.jplayer.org/audio/ogg/TSP-01-Cro_magnon_man.ogg"
					});

					$('#audio_title').html(ad.name);
					$('#listen_audio_modal_label').html(ad.name);

					$('#jp_container_2').modal('show');

					$('#listen_audio_done_button').unbind();
					$('#listen_audio_done_button').one('click', function() {
						try {
							$("#jp_container_2").jPlayer("stop");
						} catch (err) {
							handleError("listenAudio", err);
						}
					});

				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

	} catch (err) {
		handleError("listenAudio", err);
	}
	log("listenAudio", "Exiting")
}

function updateAdEnabled(pAdId, pAdEnabled, pElementName) {
	openWait();
	log("updateAdEnabled", "Entering");
	var lElementName = "#" + pElementName;

	try {
		var lDate = new Date();
		var lAdObj = {
			id : pAdId,
			enabled : pAdEnabled,
			timeUpdatedMs : lDate.getTime(),
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)
		};
		var lAdObjString = JSON.stringify(lAdObj, null, 2);
		var jqxhr = $.ajax({
			url : "secured/ad/enabled",
			type : "PUT",
			data : lAdObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					// getAds();
					getAdsByAdGroup(mSelectedAdGroup.id);
				},
				400 : function(text) {
					try {
						$('#campaign_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("updateAdEnabled", err);
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
		handleError("updateAdEnabled", err);
	}
	log("updateAdEnabled", "Exiting")
}
/** End Ad****************************************** */
