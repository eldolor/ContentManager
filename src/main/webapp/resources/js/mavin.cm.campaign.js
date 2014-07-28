/** *Begin Campaign*********************************** */
function getCampaigns() {
	log("getCampaigns", "Entering")
	try {
		// open wait div
		openWait();

		var jqxhr = $.ajax({
			url : "secured/campaigns",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(campaigns) {
					handleDisplayCampaigns_Callback(campaigns);
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

	} catch (err) {
		handleError("getCampaigns", err);
		// close wait div
		;
	}
	log("getCampaigns", "Exiting")
}

function handleDisplayCampaigns_Callback(pCampaigns) {
	log("handleDisplayCampaigns_Callback", "Entering")
	try {
		var lInnerHtml = "<div class=\"accordion\" id=\"campaignAccordian\"> ";
		for (var int = 0; int < pCampaigns.length; int++) {
			var lCampaign = pCampaigns[int];
			var _innerHtml = "<div class=\"accordion-group\" id=\"campaignAccordianGroup"
					+ lCampaign.id
					+ "\">"
					+ "<div class=\"accordion-heading\">"
					+ "<a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#campaignAccordianGroup"
					+ lCampaign.id
					+ "\" href=\"#"
					+ lCampaign.id
					+ "\"> <div onclick=\"getAdGroups('"
					+ lCampaign.id
					+ "')\"> ";
			if (!lCampaign.enabled) {
				_innerHtml += "<span class=\"label label-important\">[Disabled] </span>";
			}
			_innerHtml += "<strong>"
					+ lCampaign.name
					+ "</strong>&nbsp;<small>"
					+ getDisplayDate(lCampaign.startDateIso8601)
					+ "</small></div></div>"
					+ "<div id=\""
					+ lCampaign.id
					+ "\" class=\"accordion-body collapse in\">"
					+ "<div class=\"accordion-inner\">"
					+ lCampaign.description
					+ "<p class=\"text-center\">&nbsp;</p>"
					+ "<ul class=\"inline\">"
					+ "<li><a href=\"javascript:void(0)\""
					+ "onclick=\"editCampaign('"
					+ lCampaign.id
					+ "')\"><i class=\"icon-edit\"></i> edit</a>"
					+ "</li>"
					+ "<li><a href=\"javascript:void(0)\" onclick=\"deleteCampaign('"
					+ lCampaign.id
					+ "')\"><i class=\"icon-trash\"></i> delete</a>"
					+ "</li>"
					+ "<li>"
					+ "<a href=\"javascript:void(0)\" onclick=\"getAdGroups('"
					+ lCampaign.id
					+ "')\"><i class=\"icon-folder-open\"></i> view ad groups</a>"
					+ "</li>"
					+ "<li>"
					+ "<a href=\"javascript:void(0)\"  onclick=\"displayCampaignStats('"
					+ lCampaign.id + "','" + lCampaign.name
					+ "')\"><i class=\"icon-book\"></i> statistics</a>"
					+ "</li>";
			var lEnabledElementId = lCampaign.id + "_campaign_enabled";
			if (!lCampaign.enabled) {
				_innerHtml += "<li>"
						+ "<a href=\"javascript:void(0)\" onclick=\"updateCampaignEnabled("
						+  lCampaign.id + ", " + true + ", '"
						+ lEnabledElementId + "'" + ")\"  id=\""
						+ lEnabledElementId + "\" name=\"" + lEnabledElementId
						+ "\" ><i class=\"icon-ok\"></i>enable</a>" + "</li>";
			} else {
				_innerHtml += "<li>"
						+ "<a href=\"javascript:void(0)\" onclick=\"updateCampaignEnabled("
						+ lCampaign.id + ", " + false + ", '"
						+ lEnabledElementId + "'" + ")\"  id=\""
						+ lEnabledElementId + "\" name=\"" + lEnabledElementId
						+ "\" ><i class=\"icon-remove\"></i>disable</a>"
						+ "</li>";
			}

			_innerHtml += "</ul>" + "</div>" + "</div>" + "</div>";

			lInnerHtml += _innerHtml + "<p class=\"text-center\">&nbsp;</p>";
		}
		lInnerHtml += "</div>";

		$('#entries').empty().html(lInnerHtml);
		// $('#titleBoxHeader').html('Campaigns');
		var breadCrumbsHtml = "<div ><ul class=\"breadcrumb breadcrumb-fixed-top\"><li><a href=\"javascript:void(0)\" onclick=\"getCampaigns()\"><strong>campaigns</strong></a></li>"
				+ "<li><span class=\"divider\"> / </span></li>"
				+ "<li><a href=\"javascript:void(0);\" onclick=\"newCampaign()\"> <img alt=\"\" src=\"resources/images/plus-box-16x16.png\" height=\"16\" width=\"16\" />&nbsp;create new campaign</a></li></ul></div>";

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
	}
	log("handleDisplayCampaigns_Callback", "Exiting")
}

function updateCampaignEnabled(pCampaignId, pCampaignEnabled, pElementName) {
	openWait();
	log("updateCampaignEnabled", "Entering");
	var lElementName = "#" + pElementName;

	try {
		var lDate = new Date();
		var lCampaignObj = {
			id : pCampaignId,
			enabled : pCampaignEnabled,
			timeUpdatedMs : lDate.getTime(),
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)
		};
		var lCampaignObjString = JSON.stringify(lCampaignObj, null, 2);
		var jqxhr = $.ajax({
			url : "secured/campaign/enabled",
			type : "PUT",
			data : lCampaignObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					getCampaigns();
				},
				400 : function(text) {
					try {
						$('#campaign_errors').html('<p>'+ getErrorMessages(text)+'</p>');
					} catch (err) {
						handleError("updateCampaignEnabled", err);
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
		handleError("updateCampaignEnabled", err);
	}
	log("updateCampaignEnabled", "Exiting")
}
function displayCampaignStats(id, name) {
	log("displayCampaignStats", "Entering");
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/campaignadstats/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			statusCode : {
				200 : function(adStats) {
					$("#campaignAccordianGroup" + id).popover(
							{
								selector : '#campaignAccordianGroup' + id,
								content : 'Clicks: ' + adStats.clicks
										+ ', Impressions: '
										+ adStats.impressions
							});
					$("#campaignAccordianGroup" + id).popover('toggle');
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
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
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "secured/campaign/" + id;
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
									getDisplayDate(campaign.startDateIso8601));
							$('#campaign_end_date').val(
									getDisplayDate(campaign.endDateIso8601));
							// add more
							$('#campaign_userid').val(campaign.sponsoredUserId);

							// unbind click listener to reset
							$('#campaign_interest_category_targetting')
									.unbind();
							$('#campaign_interest_category_targetting')
									.bind(
											'click',
											function() {
												$(
														"#campaign_interest_category_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="campaign_interest_category_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="campaign_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayInterestCategoryTargettingSelection = false;
							for (var int = 0; (campaign.interestCategoryTargetting != null)
									&& (int < campaign.interestCategoryTargetting.length); int++) {
								var country = campaign.interestCategoryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#campaign_interest_category_targetting')
											.attr('checked', 'checked');
									$(
											"#campaign_interest_category_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="campaign_interest_category_targetting_selection[]"]')
											.each(
													function() {
														if (country == $(this)
																.val()) {
															$(this).attr(
																	'checked',
																	'checked');
															_displayinterestCategoryTargettingSelection = true;
															return;
														}
													});
								}
							}
							// if specific categories are selected
							if (_displayInterestCategoryTargettingSelection) {
								$('#campaign_interest_category_targetting')
										.removeAttr('checked');
								$(
										"#campaign_interest_category_targetting_selection_div")
										.show();
							}

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
							for (var int = 0; (campaign.countryTargetting != null)
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
							for (var int = 0; (campaign.ageTargetting != null)
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
								log("editCampaign", "Campaign enabled: "
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
							// close wait div
							;
							openPopup("campaign_div");
						}
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editCampaign", err);
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
		var url = "secured/campaign/" + id;
		var jqxhr = $.ajax({
			url : url,
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(campaign) {
					mSelectedCampaign = campaign;
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
		$('#campaign_userid').val('');

		// unbind click listener to reset
		$('#campaign_interest_category_targetting').unbind();
		$('#campaign_interest_category_targetting')
				.bind(
						'click',
						function() {
							$(
									"#campaign_interest_category_targetting_selection_div")
									.slideToggle();
							$(
									'input[name="campaign_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
						});

		// set default
		$('#campaign_interest_category_targetting').attr('checked', 'checked');
		$("#campaign_interest_category_targetting_selection_div").hide();

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
		// set default
		$('#campaign_enabled').attr('checked', 'checked');
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
		openWait();
		var _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		if ($('#campaign_interest_category_targetting').is(':checked')) {
			_interestCategoryTargetting.push($(
					'#campaign_interest_category_targetting').val());
		} else {
			_interestCategoryTargetting = $(
					'input[name="campaign_interest_category_targetting_selection[]"]:checked')
					.map(function() {
						return $(this).val();
					}).get();
		}
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

		var _date = new Date();
		var _timeCreated = _date.getTime();

		var campaignObj = {
			id : $('#campaign_id').val(),
			name : $('#campaign_name').val(),
			description : $('#campaign_description').val(),
			startDateIso8601 : getTransferDate($('#campaign_start_date').val()),
			endDateIso8601 : getTransferDate($('#campaign_end_date').val()),
			sponsoredUserId : $('#campaign_userid').val(),
			interestCategoryTargetting : _interestCategoryTargetting,
			countryTargetting : _countryTargetting,
			trafficTargetting : _trafficTargetting,
			genderTargetting : _genderTargetting,
			ageTargetting : _ageTargetting,
			enabled : _enabled,
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)

		};
		var campaignObjString = JSON.stringify(campaignObj, null, 2);
		// alert(campaignObjString);
		// create via sync call
		var jqxhr = $.ajax({
			url : "secured/campaign",
			type : "POST",
			data : campaignObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#campaign_div').modal('hide');
					getCampaigns();
				},
				400 : function(text) {
					try {
						$('#campaign_errors').html('<p>'+ getErrorMessages(text)+'</p>');
					} catch (err) {
						handleError("submitCampaign", err);
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
		handleError("submitCampaign", err);
	}
	log("createCampaign", "Exiting")
}

function updateCampaign() {
	openWait();
	log("updateCampaign", "Entering")
	var _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
	if ($('#campaign_interest_category_targetting').is(':checked')) {
		_interestCategoryTargetting.push($(
				'#campaign_interest_category_targetting').val());
	} else {
		_interestCategoryTargetting = $(
				'input[name="campaign_interest_category_targetting_selection[]"]:checked')
				.map(function() {
					return $(this).val();
				}).get();
	}
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
		var _date = new Date();
		var campaignObj = {
			id : $('#campaign_id').val(),
			name : $('#campaign_name').val(),
			description : $('#campaign_description').val(),
			startDateIso8601 : getTransferDate($('#campaign_start_date').val()),
			endDateIso8601 : getTransferDate($('#campaign_end_date').val()),
			sponsoredUserId : $('#campaign_userid').val(),
			interestCategoryTargetting : _interestCategoryTargetting,
			countryTargetting : _countryTargetting,
			trafficTargetting : _trafficTargetting,
			genderTargetting : _genderTargetting,
			ageTargetting : _ageTargetting,
			enabled : _enabled,
			timeUpdatedMs : _date.getTime(),
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var campaignObjString = JSON.stringify(campaignObj, null, 2);
		var jqxhr = $.ajax({
			url : "secured/campaign",
			type : "PUT",
			data : campaignObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#campaign_div').modal('hide');
					getCampaigns();
				},
				400 : function(text) {
					try {
						$('#campaign_errors').html('<p>'+ getErrorMessages(text)+'</p>');
					} catch (err) {
						handleError("updateCampaign", err);
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
		handleError("updateCampaign", err);
	}
	log("updateCampaign", "Exiting")
}

function toggleCampaignType(preload) {
	log("toggleCampaignType", "Entering")
	try {
		if (preload) {
			wait("campaign_wait");
			var url = "secured/samplecampaign";
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
		var _date = new Date();
		var _timeUpdatedMs = _date.getTime();
		var _timeUpdatedTimeZoneOffsetMs = (_date.getTimezoneOffset() * 60 * 1000);

		displayConfirm("Are you sure you want to delete this Campaign?",
				function() {
					wait("confirm_wait");
					var url = "secured/campaign/" + id + "/" + _timeUpdatedMs
							+ "/" + _timeUpdatedTimeZoneOffsetMs;
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
