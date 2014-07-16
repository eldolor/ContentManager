/** *Begin Campaign*********************************** */
function getCampaigns() {
	log("getCampaigns", "Entering")
	try {
		//open wait div
		openWait();

		var jqxhr = $.ajax({
			url : "/secured/fm/campaigns",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(campaigns) {
					handleDisplayCampaigns_Callback(campaigns);
				}
			}
		});

	} catch (err) {
		handleError("getCampaigns", err);
		//close wait div
		;
	}
	log("getCampaigns", "Exiting")
}

function handleDisplayCampaigns_Callback(campaigns) {
	log("handleDisplayCampaigns_Callback", "Entering")
	try {
		var innerHtml = '';
		for ( var int = 0; int < campaigns.length; int++) {
			var campaign = campaigns[int];
			var _innerHtml = "<div class=\"entry open\"> "
					+ "<div class=\"liner\">" + "<div class=\"header\">"
					+ "<div class=\"expando\" ></div>" + "<h2>"
					+ campaign.name
					+ "</h2>"
					+ "<div class=\"meta\">"
					+ "<span class=\"last\">"
					+ getDisplayDate(campaign.startDate)
					+ "</span>"
					+ "</div>"
					+ "</div>"
					+ "<div class=\"body\">"
					+ "<div class=\"content\">"
					+ "<div class=\"liner\">"
					+ "<div class=\"synopsis\">"
					+ campaign.description
					+ "</div>"
					+ "<div class=\"actions\">"
					+ "<ul>"
					+ "<li><a href=\"javascript:void(0)\""
					+ "onclick=\"editCampaign('"
					+ campaign.id
					+ "')\">edit campaign</a>"
					+ "</li>"
					+ "<li><a href=\"javascript:void(0)\" onclick=\"deleteCampaign('"
					+ campaign.id
					+ "')\">delete this campaign</a>"
					+ "</li>"
					+ "<li>"
					+ "<a href=\"javascript:void(0)\" onclick=\"getFeeds('"
					+ campaign.id
					+ "')\">view feeds</a>"
					+ "</li>"
					+ "<li class=\"last\">"
					+ "<a href=\"javascript:void(0)\" onclick=\"displayCampaignStats('"
					+ campaign.id
					+ "','"
					+ campaign.name
					+ "')\">statistics</a>"
					+ "</li>"
					+ "</ul>"
					+ "</div>"
					+ "</div>"
					+ "</div>"
					+ "</div>"
					+ "</div>"
					+ "</div>";

			innerHtml += _innerHtml;
		}

		$('#entries').empty().html(innerHtml);
		// $('#titleBoxHeader').html('Campaigns');
		var breadCrumbsHtml = "<div class=\"actions\"><ul><li><a href=\"javascript:void(0)\" onclick=\"getCampaigns()\">campaigns</a></li>"
				+ "<li><span class=\"separator\"> > </span></li>"
				+ "<li><a href=\"javascript:void(0);\" onclick=\"newCampaign()\"> <img alt=\"\" src=\"../resources/images/plus-box-16x16.png\" height=\"16\" width=\"16\" />&nbsp;create new campaign</a></li></ul></div>";

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
		handleError("handleDisplayCampaigns_Callback", err);
	} finally {
		//close wait div
		;		
	}
	log("handleDisplayCampaigns_Callback", "Exiting")
}

function displayCampaignStats(id, name) {
	log("displayCampaignStats", "Entering");
	try {
		//open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/fm/campaignadstats/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(adStats) {
					$('#stats_header').html(name);
					$('#stats_clicks').html('Clicks: ' + adStats.clicks);
					$('#stats_impressions').html('Impressions: '+ adStats.impressions);
					//close wait div
					;
					openPopup("stats_div");
				}
			}
		});
	} catch (err) {
		handleError("displayCampaignStats", err);
		;
	}
	log("displayCampaignStats", "Exiting")
}

function editCampaign(id) {
	log("editCampaign", "Entering")
	try {
		//open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/fm/campaign/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(campaign) {
							$('#campaign_setup_header').toggle(false);
							$('#campaign_id').val(campaign.id);
							$('#campaign_name').val(campaign.name);
							$('#campaign_description')
									.val(campaign.description);
							$('#campaign_start_date').val(
									getDisplayDate(campaign.startDate));
							$('#campaign_end_date').val(
									getDisplayDate(campaign.endDate));
							// add more
							// unbind click listener to reset
							$('#campaign_country_targetting').unbind();
							$('#campaign_country_targetting')
									.bind(
											'click',
											function() {
												$(
														"#campaign_country_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="campaign_country_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="campaign_country_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayCountryTargettingSelection = false;
							for ( var int = 0; (campaign.countryTargetting != null)
									&& (int < campaign.countryTargetting.length); int++) {
								var country = campaign.countryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#campaign_country_targetting').attr(
											'checked', 'checked');
									$(
											"#campaign_country_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="campaign_country_targetting_selection[]"]')
											.each(
													function() {
														if (country == $(this)
																.val()) {
															$(this).attr(
																	'checked',
																	'checked');
															_displayCountryTargettingSelection = true;
															return;
														}
													});
								}
							}
							// if specific countries are selected
							if (_displayCountryTargettingSelection) {
								$('#campaign_country_targetting').removeAttr(
										'checked');
								$("#campaign_country_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#campaign_age_targetting').unbind();
							$('#campaign_age_targetting')
									.bind(
											'click',
											function() {
												$(
														"#campaign_age_targetting_selection_div")
														.slideToggle(); // reset
												$(
														'input[name="campaign_age_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="campaign_age_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayAgeTargettingSelection = false;
							for ( var int = 0; (campaign.ageTargetting != null)
									&& (int < campaign.ageTargetting.length); int++) {
								var age = campaign.ageTargetting[int];
								log("editTextAd", "outer: " + age);
								if (age == 'all') {
									$('#campaign_age_targetting').attr(
											'checked', 'checked');
									$("#campaign_age_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="campaign_age_targetting_selection[]"]')
											.each(
													function() {
														if (age == $(this)
																.val()) {
															$(this).attr(
																	'checked',
																	'checked');
															_displayAgeTargettingSelection = true;
															return;
														}
													});
								}
							}
							// if specific age groups are selected
							if (_displayAgeTargettingSelection) {
								$('#campaign_age_targetting').removeAttr(
										'checked');
								$("#campaign_age_targetting_selection_div")
										.show();
							}

							$(
									'input[name="campaign_traffic_targetting_selection"]')
									.each(
											function() {
												if (campaign.trafficTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#campaign_traffic_targetting').val(
									campaign.trafficTargetting);
							$(
									'input[name="campaign_gender_targetting_selection"]')
									.each(
											function() {
												if (campaign.genderTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#campaign_gender_targetting').val(
									campaign.genderTargetting);

							if (campaign.hasOwnProperty('enabled')) {
								log("editTextAd", "Campaign enabled: "
										+ campaign.enabled);
								if (campaign.enabled == true) {
									$('#campaign_enabled').attr('checked',
											'checked');
									$('#campaign_status').html('Enabled');
								} else {
									$('#campaign_enabled')
											.removeAttr('checked');
									$('#campaign_status').html('Disabled');
								}
							}
							$('#campaign_enabled').bind('click', function() {
								if ($('#campaign_enabled').is(':checked')) {
									$('#campaign_status').html('Enabled');
								} else {
									$('#campaign_status').html('Disabled');
								}
							});

							$('#campaign_save_button').html('update campaign');

							// unbind click listener to reset
							$('#campaign_save_button').unbind();
							$('#campaign_save_button').bind('click',
									updateCampaign);

							$('#campaign_cancel_button').one('click',
									function() {
										$('#campaign_save_button').unbind();
										;
									});

							$('#campaign_errors').empty();
							//close wait div
							;
							openPopup("campaign_div");
						}
					}
				});
	} catch (err) {
		handleError("editCampaign", err);
		//close wait div
		;
	}
	log("editCampaign", "Exiting")
}

function toggleCampaignTrafficTargetting(target) {

	if (target == 'all') {
		$('#campaign_traffic_targetting').val('all');
	} else if (target == 'wifi') {
		$('#campaign_traffic_targetting').val('wifi');
	} else if (target == 'mobile_operator') {
		$('#campaign_traffic_targetting').val('mobile_operator');
	}
}
function toggleCampaignGenderTargetting(target) {

	if (target == 'all') {
		$('#campaign_gender_targetting').val('all');
	} else if (target == 'male') {
		$('#campaign_gender_targetting').val('male');
	} else if (target == 'female') {
		$('#campaign_gender_targetting').val('female');
	}
}

function selectedCampaign(id) {
	log("selectedCampaign", "Entering")
	try {
		// load entry info via sync call
		var url = "/secured/fm/campaign/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(campaign) {
					mSelectedFeedCampaign = campaign;
				}
			}
		});
	} catch (err) {
		handleError("selectedCampaign", err);
	}
	log("selectedCampaign", "Entering")
}

function newCampaign() {
	log("newCampaign", "Entering")
	try {
		$('#campaign_setup_header').toggle(true);
		$('#campaign_save_button').html('create campaign');
		// unbind click listener to reset
		$('#campaign_save_button').unbind();
		$('#campaign_save_button').bind('click', createCampaign);
		$('#campaign_cancel_button').one('click', function() {
			$('#campaign_save_button').unbind();
			;
		});
		$('#campaign_id').val('');
		$('#campaign_name').val('');
		$('#campaign_description').val('');
		$('#campaign_start_date').val(getCurrentDisplayDate());
		$('#campaign_end_date').val('');

		// unbind click listener to reset
		$('#campaign_country_targetting').unbind();
		$('#campaign_country_targetting').bind(
				'click',
				function() {
					$("#campaign_country_targetting_selection_div")
							.slideToggle();
					$('input[name="campaign_country_targetting_selection[]"]')
							.removeAttr('checked');
				});

		// set default
		$('#campaign_country_targetting').attr('checked', 'checked');
		$("#campaign_country_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#campaign_age_targetting').unbind();
		$('#campaign_age_targetting').bind(
				'click',
				function() {
					$("#campaign_age_targetting_selection_div").slideToggle(); // reset
					$('input[name="campaign_age_targetting_selection[]"]')
							.removeAttr('checked');
				});
		// set default
		$('#campaign_age_targetting').attr('checked', 'checked');
		$("#campaign_age_targetting_selection_div").hide();

		// set default
		$('#campaign_traffic_targetting').val('all');
		$('input[name="campaign_traffic_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});
		// set default
		$('#campaign_gender_targetting').val('all');
		$('input[name="campaign_gender_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});

		$('#campaign_enabled').bind('click', function() {
			if ($('#campaign_enabled').is(':checked')) {
				$('#campaign_status').html('Enabled');
			} else {
				$('#campaign_status').html('Disabled');
			}
		});

		$('#campaign_errors').empty();
		openPopup("campaign_div");
	} catch (err) {
		handleError("newCampaign", err);
	}
	log("newCampaign", "Exiting")
}

function createCampaign() {
	log("createCampaign", "Entering")
	try {
		wait('campaign_wait');
		var _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		if ($('#campaign_country_targetting').is(':checked')) {
			_countryTargetting.push($('#campaign_country_targetting').val());
		} else {
			_countryTargetting = $(
					'input[name="campaign_country_targetting_selection[]"]:checked')
					.map(function() {
						return $(this).val();
					}).get();
		}
		if ($('#campaign_age_targetting').is(':checked')) {
			_ageTargetting.push($('#campaign_age_targetting').val());
		} else {
			_ageTargetting = $(
					'input[name="campaign_age_targetting_selection[]"]:checked')
					.map(function() {
						return $(this).val();
					}).get();
		}
		_trafficTargetting.push($('#campaign_traffic_targetting').val());
		_genderTargetting.push($('#campaign_gender_targetting').val());
		if ($('#campaign_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var campaignObj = {
			id : $('#campaign_id').val(),
			name : $('#campaign_name').val(),
			description : $('#campaign_description').val(),
			startDate : getTransferDate($('#campaign_start_date').val()),
			endDate : getTransferDate($('#campaign_end_date').val()),
			countryTargetting : _countryTargetting,
			trafficTargetting : _trafficTargetting,
			genderTargetting : _genderTargetting,
			ageTargetting : _ageTargetting,
			enabled : _enabled
		};
		var campaignObjString = JSON.stringify(campaignObj, null, 2);
		// alert(campaignObjString);
		// create via sync call
		var jqxhr = $.ajax({
			url : "/secured/fm/campaign",
			type : "POST",
			data : campaignObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					;
					getCampaigns();
				},
				400 : function(text) {
					try {
						$('#campaign_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("submitCampaign", err);
					}
				}
			}
		});
		jqxhr.always(function(msg) {
			// alert('Status: ' +jqxhr.status +' Status Text: '
			// +jqxhr.statusText +
			// 'Response Text: ' +jqxhr.responseText);
			clearWait('campaign_wait');
		});

		return false;
	} catch (err) {
		handleError("submitCampaign", err);
	}
	log("createCampaign", "Exiting")
}

function updateCampaign() {
	log("updateCampaign", "Entering")
	var _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
	if ($('#campaign_country_targetting').is(':checked')) {
		_countryTargetting.push($('#campaign_country_targetting').val());
	} else {
		_countryTargetting = $(
				'input[name="campaign_country_targetting_selection[]"]:checked')
				.map(function() {
					return $(this).val();
				}).get();
	}
	if ($('#campaign_age_targetting').is(':checked')) {
		_ageTargetting.push($('#campaign_age_targetting').val());
	} else {
		_ageTargetting = $(
				'input[name="campaign_age_targetting_selection[]"]:checked')
				.map(function() {
					return $(this).val();
				}).get();
	}
	_trafficTargetting.push($('#campaign_traffic_targetting').val());
	_genderTargetting.push($('#campaign_gender_targetting').val());

	if ($('#campaign_enabled').is(':checked')) {
		_enabled = true;
	} else {
		_enabled = false;
	}

	try {
		wait('campaign_wait');
		var campaignObj = {
			id : $('#campaign_id').val(),
			name : $('#campaign_name').val(),
			description : $('#campaign_description').val(),
			startDate : getTransferDate($('#campaign_start_date').val()),
			endDate : getTransferDate($('#campaign_end_date').val()),
			countryTargetting : _countryTargetting,
			trafficTargetting : _trafficTargetting,
			genderTargetting : _genderTargetting,
			ageTargetting : _ageTargetting,
			enabled : _enabled
		};
		var campaignObjString = JSON.stringify(campaignObj, null, 2);
		var jqxhr = $.ajax({
			url : "/secured/fm/campaign",
			type : "PUT",
			data : campaignObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					;
					getCampaigns();
				},
				400 : function(text) {
					try {
						$('#campaign_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("updateCampaign", err);
					}
				}
			}
		});
		jqxhr.always(function(msg) {
			// alert('Status: ' +jqxhr.status +' Status Text: '
			// +jqxhr.statusText +
			// 'Response Text: ' +jqxhr.responseText);
			clearWait('campaign_wait');
		});

		return false;
	} catch (err) {
		handleError("updateCampaign", err);
	}
	log("updateCampaign", "Exiting")
}

function toggleCampaignType(preload) {
	log("toggleCampaignType", "Entering")
	try {
		if (preload) {
			wait("campaign_wait");
			var url = "/secured/fm/samplecampaign";
			var jqxhr = $.ajax({
				url : url,
				type : "GET",
				contentType : "application/json",
				statusCode : {
					200 : function(campaign) {
						try {
							$('#campaign_id').val(campaign.id);
							$('#campaign_name').val(campaign.name);
							$('#campaign_description')
									.val(campaign.description);
							$('#campaign_errors').empty();
						} catch (err) {
							handleError("toggleCampaignType", err);
						}
					}
				}
			});
			jqxhr.always(function(msg) {
				clearWait('campaign_wait');
			});
		} else {
			$('#campaign_id').val('');
			$('#campaign_name').val('');
			$('#campaign_description').val('');
			$('#campaign_errors').empty();
		}
	} catch (err) {
		handleError("toggleCampaignType", err);
	}
	log("toggleCampaignType", "Exiting")
}

function deleteCampaign(id) {
	log("deleteCampaign", "Entering")
	try {
		displayConfirm("Are you sure you want to delete this Campaign?",
				function() {
					wait("confirm_wait");
					var url = "/secured/fm/campaign/" + id;
					var jqxhr = $.ajax({
						url : url,
						type : "DELETE",
						contentType : "application/json",
						statusCode : {
							200 : function() {
								getCampaigns();
							}
						}
					});
					jqxhr.always(function(msg) {
						clearWait("confirm_wait");
					});
				});
	} catch (err) {
		handleError("deleteCampaign", err);
	}
	log("deleteCampaign", "Exiting")

}

/** *End Campaign***************************************** */
