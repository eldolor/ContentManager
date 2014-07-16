/** Begin Ad****************************************** */

function editTextAd(id) {
	log("editTextAd", "Entering");
	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "secured/ad/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(ad) {
							$('#text_ad_setup_header').toggle(false);
							$('#text_ad_save_button').html('update ad');
							// reset
							$('#text_ad_save_button').unbind();
							$('#text_ad_save_button').bind('click',
									updateTextAd);
							$('#text_ad_cancel_button').one('click',
									function() {
										$('#text_ad_save_button').unbind();

										;
									});
							$('#text_ad_id').val(ad.id);
							$('#text_ad_campaign_id').val(ad.campaignId);
							$('#text_ad_adgroup_id').val(ad.adGroupId);
							$('#text_ad_start_date').val(
									getDisplayDate(ad.startDateIso8601));
							$('#text_ad_end_date').val(
									getDisplayDate(ad.endDateIso8601));
							// alert(ad.targetApplication);
							// reset
							$('input:checkbox').removeAttr('checked');
							for (var int = 0; int < ad.targetApplication.length; int++) {
								var targetApp = ad.targetApplication[int];
								log("editTextAd", "outer: " + targetApp);
								$('input[name="text_ad_target_application[]"]')
										.each(
												function() {
													if (targetApp == $(this)
															.val()) {
														$(this).attr('checked',
																'checked');
														return;
													}
												});
							}

							$('#text_ad_name').val(ad.name);
							$('#text_ad_description').val(ad.description);

							$('#text_ad_headline').val(ad.headline);
							$('#text_ad_line1').val(ad.line1);
							$('#text_ad_line2').val(ad.line2);
							$('#text_ad_display_uri').val(ad.displayUrl);
							$('#text_ad_destination_uri')
									.val(ad.destinationUrl);

							$('#text_ad_errors').empty();
							if (ad.enabled == true) {
								$('#text_ad_enabled')
										.attr('checked', 'checked');
								$('#text_ad_status').html('Enabled');
							} else {
								$('#text_ad_enabled').removeAttr('checked');
								$('#text_ad_status').html('Disabled');
							}
							$('#text_ad_enabled').bind('click', function() {
								if ($('#text_ad_enabled').is(':checked')) {
									$('#text_ad_status').html('Enabled');
								} else {
									$('#text_ad_status').html('Disabled');
								}
							});

							$('#text_ad_cpi').val(ad.cpi);
							$('#text_ad_cpc').val(ad.cpc);

							$('#text_ad_frequency_capping').val(
									ad.frequencyCappingPerDay);

							// unbind click listener to reset
							$('#text_ad_interest_category_targetting').unbind();
							$('#text_ad_interest_category_targetting')
									.bind(
											'click',
											function() {
												$(
														"#text_ad_interest_category_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="text_ad_interest_category_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="text_ad_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayInterestCategoryTargettingSelection = false;
							for (var int = 0; (ad.interestCategoryTargetting != null)
									&& (int < ad.interestCategoryTargetting.length); int++) {
								var country = ad.interestCategoryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#text_ad_interest_category_targetting')
											.attr('checked', 'checked');
									$(
											"#text_ad_interest_category_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="text_ad_interest_category_targetting_selection[]"]')
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
								$('#text_ad_interest_category_targetting')
										.removeAttr('checked');
								$(
										"#text_ad_interest_category_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#text_ad_country_targetting').unbind();
							$('#text_ad_country_targetting')
									.bind(
											'click',
											function() {
												$(
														"#text_ad_country_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="text_ad_country_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="text_ad_country_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayCountryTargettingSelection = false;
							for (var int = 0; (ad.countryTargetting != null)
									&& (int < ad.countryTargetting.length); int++) {
								var country = ad.countryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#text_ad_country_targetting').attr(
											'checked', 'checked');
									$(
											"#text_ad_country_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="text_ad_country_targetting_selection[]"]')
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
								$('#text_ad_country_targetting').removeAttr(
										'checked');
								$("#text_ad_country_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#text_ad_age_targetting').unbind();
							$('#text_ad_age_targetting')
									.bind(
											'click',
											function() {
												$(
														"#text_ad_age_targetting_selection_div")
														.slideToggle(); // reset
												$(
														'input[name="text_ad_age_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="text_ad_age_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayAgeTargettingSelection = false;
							for (var int = 0; (ad.ageTargetting != null)
									&& (int < ad.ageTargetting.length); int++) {
								var age = ad.ageTargetting[int];
								log("editTextAd", "outer: " + age);
								if (age == 'all') {
									$('#text_ad_age_targetting').attr(
											'checked', 'checked');
									$("#text_ad_age_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="text_ad_age_targetting_selection[]"]')
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
								$('#text_ad_age_targetting').removeAttr(
										'checked');
								$("#text_ad_age_targetting_selection_div")
										.show();
							}

							$(
									'input[name="text_ad_traffic_targetting_selection"]')
									.each(
											function() {
												if (ad.trafficTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#text_ad_traffic_targetting').val(
									ad.trafficTargetting);
							$(
									'input[name="text_ad_gender_targetting_selection"]')
									.each(
											function() {
												if (ad.genderTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#text_ad_gender_targetting').val(
									ad.genderTargetting);

							// set value
							if (ad.targettingOverridden == true) {
								$('#text_ad_targetting_overridden').attr(
										'checked', 'checked');
								$('#text_ad_targetting_div').show();
							} else {
								$('#text_ad_targetting_overridden').removeAttr(
										'checked');
								$('#text_ad_targetting_div').hide();
							}
							$('#text_ad_targetting_overridden').unbind();
							$('#text_ad_targetting_overridden').bind(
									'click',
									function() {
										$("#text_ad_targetting_div")
												.slideToggle(); // reset
									});
							// close wait div
							;
							openPopup("text_ad_div");
						}
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editAd", err);
		// close wait div
		;
	}
	log("editTextAd", "Exiting");
}

function toggleTextAdTrafficTargetting(target) {

	if (target == 'all') {
		$('#text_ad_traffic_targetting').val('all');
	} else if (target == 'wifi') {
		$('#text_ad_traffic_targetting').val('wifi');
	} else if (target == 'mobile_operator') {
		$('#text_ad_traffic_targetting').val('mobile_operator');
	}
}
function toggleTextAdGenderTargetting(target) {

	if (target == 'all') {
		$('#text_ad_gender_targetting').val('all');
	} else if (target == 'male') {
		$('#text_ad_gender_targetting').val('male');
	} else if (target == 'female') {
		$('#text_ad_gender_targetting').val('female');
	}
}

function newTextAd(campaignId, adGroupId) {
	log("newTextAd", "Entering");
	try {

		$('#text_ad_setup_header').toggle(true);
		$('#text_ad_save_button').html('create ad');

		// reset
		$('input:checkbox').removeAttr('checked');
		// reset
		$('#text_ad_save_button').unbind();
		$('#text_ad_save_button').bind('click', createTextAd);
		$('#text_ad_cancel_button').one('click', function() {
			$('#text_ad_save_button').unbind();

			;
		});

		$('#text_ad_id').val('');
		$('#text_ad_campaign_id').val(campaignId);
		$('#text_ad_adgroup_id').val(adGroupId);
		$('#text_ad_start_date').val(getCurrentDisplayDate());
		$('#text_ad_end_date').val('');

		$('#text_ad_name').val('');
		$('#text_ad_description').val('');

		$('#text_ad_headline').val('');
		$('#text_ad_line1').val('');
		$('#text_ad_line2').val('');
		$('#text_ad_display_uri').val('');
		$('#text_ad_destination_uri').val('');

		$('#text_ad_errors').empty();

		$('#text_ad_enabled').prop('checked', 'checked');
		$('#text_ad_enabled').bind('click', function() {
			if ($('#text_ad_enabled').is(':checked')) {
				$('#text_ad_status').html('Enabled');
			} else {
				$('#text_ad_status').html('Disabled');
			}
		});
		$('#text_ad_frequency_capping').val('');
		$('#text_ad_cpi').val('');
		$('#text_ad_cpc').val('');

		// unbind click listener to reset
		$('#text_ad_interest_category_targetting').unbind();
		$('#text_ad_interest_category_targetting')
				.bind(
						'click',
						function() {
							$(
									"#text_ad_interest_category_targetting_selection_div")
									.slideToggle();
							$(
									'input[name="text_ad_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
						});

		// set default
		$('#text_ad_interest_category_targetting').attr('checked', 'checked');
		$("#text_ad_interest_category_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#text_ad_country_targetting').unbind();
		$('#text_ad_country_targetting').bind(
				'click',
				function() {
					$("#text_ad_country_targetting_selection_div")
							.slideToggle();
					$('input[name="text_ad_country_targetting_selection[]"]')
							.removeAttr('checked');
				});

		// set default
		$('#text_ad_country_targetting').attr('checked', 'checked');
		$("#text_ad_country_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#text_ad_age_targetting').unbind();
		$('#text_ad_age_targetting').bind(
				'click',
				function() {
					$("#text_ad_age_targetting_selection_div").slideToggle(); // reset
					$('input[name="text_ad_age_targetting_selection[]"]')
							.removeAttr('checked');
				});
		// set default
		$('#text_ad_age_targetting').attr('checked', 'checked');
		$("#text_ad_age_targetting_selection_div").hide();

		// set default
		$('#text_ad_traffic_targetting').val('all');
		$('input[name="text_ad_traffic_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});
		// set default
		$('#text_ad_gender_targetting').val('all');
		$('input[name="text_ad_gender_targetting_selection"]').each(function() {
			if ('all' == $(this).val()) {
				$(this).attr('checked', 'checked');
				return;
			}
		});
		// set default
		$('#text_ad_targetting_overridden').removeAttr('checked');
		$('#text_ad_targetting_overridden').unbind();
		$('#text_ad_targetting_overridden').bind('click', function() {
			$("#text_ad_targetting_div").slideToggle(); // reset
		});
		$('#text_ad_targetting_div').hide();
		openPopup("text_ad_div");
	} catch (err) {
		handleError("newTextAd", err);
	}
	log("newTextAd", "Exiting");
}

function toggleTextAdType(preload) {
	log("toggleTextAdType", "Entering");
	try {
		if (preload) {
			openWait();
			var url = "secured/sampletextad";
			// make sync call
			var jqxhr = $.ajax({
				url : url,
				type : "GET",
				contentType : "application/json",
				async : false,
				statusCode : {
					200 : function(ad) {
						$('#text_ad_id').val(ad.id);
						$('#text_ad_name').val(ad.name);
						$('#text_ad_description').val(ad.description);
						$('#text_ad_headline').val(ad.headline);
						$('#text_ad_line1').val(ad.line1);
						$('#text_ad_line2').val(ad.line2);
						$('#text_ad_display_uri').val(ad.displayUrl);
						$('#text_ad_destination_uri').val(ad.destinationUrl);
						$('#text_ad_errors').empty();
					},
					400 : function(text) {
						try {
							$('#text_ad_errors').html(
									'invalid ad type requested');
						} catch (err) {
							handleError("toggleTextAdType", err);
						}
					}
				}
			});
			jqxhr.always(function() {
				// close wait div
				closeWait();
			});
		} else {
			$('#text_ad_id').val('');
			$('#text_ad_name').val('');
			$('#text_ad_description').val('');
			$('#text_ad_headline').val('');
			$('#text_ad_line1').val('');
			$('#text_ad_line2').val('');
			$('#text_ad_display_uri').val('');
			$('#text_ad_destination_uri').val('');
			$('#text_ad_errors').empty();
		}
	} catch (err) {
		handleError("toggleTextAdType", err);
	}
	log("toggleTextAdType", "Exiting");

}

function createTextAd() {
	log("createTextAd", "Entering");
	try {
		mSubmitLock = true;
		openWait();
		var _campaignId = $('#text_ad_campaign_id').val();
		var _adGroupId = $('#text_ad_adgroup_id').val();
		var _adStartDate = $('#text_ad_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#text_ad_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "text_ad";
		var _targetApplication = $(
				'input[name="text_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _enabled;
		if ($('#text_ad_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#text_ad_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#text_ad_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#text_ad_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="text_ad_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#text_ad_country_targetting').is(':checked')) {
				_countryTargetting.push($('#text_ad_country_targetting').val());
			} else {
				_countryTargetting = $(
						'input[name="text_ad_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#text_ad_age_targetting').is(':checked')) {
				_ageTargetting.push($('#text_ad_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="text_ad_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#text_ad_traffic_targetting').val());
			_genderTargetting.push($('#text_ad_gender_targetting').val());

		}
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var adObj = {
			id : $('#text_ad_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			targetApplication : _targetApplication,
			name : $('#text_ad_name').val(),
			description : $('#text_ad_description').val(),
			headline : $('#text_ad_headline').val(),
			line1 : $('#text_ad_line1').val(),
			line2 : $('#text_ad_line2').val(),
			displayUrl : $('#text_ad_display_uri').val(),
			destinationUrl : $('#text_ad_destination_uri').val(),
			enabled : _enabled,
			frequencyCappingPerDay : $('#text_ad_frequency_capping').val(),
			cpi : $('#text_ad_cpi').val(),
			cpc : $('#text_ad_cpc').val(),
			targettingOverridden : _targettingOverridden,
			interestCategoryTargetting : _interestCategoryTargetting,
			countryTargetting : _countryTargetting,
			trafficTargetting : _trafficTargetting,
			genderTargetting : _genderTargetting,
			ageTargetting : _ageTargetting,
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var adObjString = JSON.stringify(adObj, null, 2);
		// alert(adObjString);
		// create via sync call
		var jqxhr = $.ajax({
			url : "secured/textad",
			type : "POST",
			data : adObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#text_ad_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(text) {
					try {
						$('#text_ad_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("createTextAd", err);
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
		handleError("createTextAd", err);
	}
	log("createTextAd", "Exiting");
}
function updateTextAd() {
	log("updateTextAd", "Entering");
	try {
		openWait();
		var _campaignId = $('#text_ad_campaign_id').val();
		var _adGroupId = $('#text_ad_adgroup_id').val();
		var _adStartDate = $('#text_ad_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#text_ad_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "text_ad";
		var _targetApplication = $(
				'input[name="text_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _enabled;
		if ($('#text_ad_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#text_ad_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#text_ad_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#text_ad_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="text_ad_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#text_ad_country_targetting').is(':checked')) {
				_countryTargetting.push($('#text_ad_country_targetting').val());
			} else {
				_countryTargetting = $(
						'input[name="text_ad_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#text_ad_age_targetting').is(':checked')) {
				_ageTargetting.push($('#text_ad_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="text_ad_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#text_ad_traffic_targetting').val());
			_genderTargetting.push($('#text_ad_gender_targetting').val());
		}
		var _date = new Date();
		var adObj = {
			id : $('#text_ad_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			targetApplication : _targetApplication,
			name : $('#text_ad_name').val(),
			description : $('#text_ad_description').val(),
			headline : $('#text_ad_headline').val(),
			line1 : $('#text_ad_line1').val(),
			line2 : $('#text_ad_line2').val(),
			displayUrl : $('#text_ad_display_uri').val(),
			destinationUrl : $('#text_ad_destination_uri').val(),
			enabled : _enabled,
			frequencyCappingPerDay : $('#text_ad_frequency_capping').val(),
			cpi : $('#text_ad_cpi').val(),
			cpc : $('#text_ad_cpc').val(),
			targettingOverridden : _targettingOverridden,
			interestCategoryTargetting : _interestCategoryTargetting,
			countryTargetting : _countryTargetting,
			trafficTargetting : _trafficTargetting,
			genderTargetting : _genderTargetting,
			ageTargetting : _ageTargetting,
			timeUpdatedMs : _date.getTime(),
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var adObjString = JSON.stringify(adObj, null, 2);
		// create via sync call
		var jqxhr = $.ajax({
			url : "secured/textad",
			type : "PUT",
			data : adObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#text_ad_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(text) {
					try {
						$('#text_ad_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("updateTextAd", err);
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
		handleError("updateTextAd", err);
	}
	log("updateTextAd", "Exiting");
}

function showTextAd() {
	log("showTextAd", "Entering");
	try {
		openPopup("text_ad_div");
	} catch (err) {
		handleError("showTextAd", err);
	}
	log("showTextAd", "Exiting");
}

/** End Ad****************************************** */
