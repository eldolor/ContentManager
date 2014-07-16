/** Begin Ad****************************************** */

function editVoiceAd(id) {
	log("editVoiceAd", "Entering")
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
							$('#voice_ad_setup_header').toggle(false);
							$('#voice_ad_save_button').html('update ad');
							// reset
							$('#voice_ad_save_button').unbind();
							$('#voice_ad_save_button').bind('click',
									updateVoiceAd);
							$('#voice_ad_cancel_button').one('click',
									function() {

										$('#voice_ad_save_button').unbind();
										;
									});
							$('#voice_ad_id').val(ad.id);
							$('#voice_ad_campaign_id').val(ad.campaignId);
							$('#voice_ad_adgroup_id').val(ad.adGroupId);
							$('#voice_ad_start_date').val(
									getDisplayDate(ad.startDateIso8601));
							$('#voice_ad_end_date').val(
									getDisplayDate(ad.endDateIso8601));

							// reset
							$('input:checkbox').removeAttr('checked');
							for (var int = 0; int < ad.targetApplication.length; int++) {
								var targetApp = ad.targetApplication[int];
								log("editTextAd", "outer: " + targetApp);
								$('input[name="voice_ad_target_application[]"]')
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
							if (ad.hasOwnProperty('enabled')) {
								log("editImageAd", "Ad enabled: " + ad.enabled);
								if (ad.enabled == true) {
									$('#voice_ad_enabled').attr('checked',
											'checked');
									$('#voice_ad_status').html('Enabled');
								} else {
									$('#voice_ad_enabled')
											.removeAttr('checked');
									$('#voice_ad_status').html('Disabled');
								}
							}
							$('#voice_ad_enabled').bind('click', function() {
								if ($('#voice_ad_enabled').is(':checked')) {
									$('#voice_ad_status').html('Enabled');
								} else {
									$('#voice_ad_status').html('Disabled');
								}
							});

							$('#voice_ad_frequency_capping').val(
									ad.frequencyCappingPerDay);
							$('#voice_ad_cpi').val(ad.cpi);
							$('#voice_ad_cpc').val(ad.cpc);

							$('#voice_ad_name').val(ad.name);
							$('#voice_ad_description').val(ad.description);

							$('#voice_ad_uri').val(ad.uri);

							$('#voice_ad_errors').empty();

							var dropBoxUrl = getDropboxUrl();
							setupVoiceAdDropBox(dropBoxUrl);

							$("#voice_ad_dropbox").hide();
							// reset
							$('#upload_voice_ad').unbind();
							$('#upload_voice_ad').bind('click', function() {
								$("#voice_ad_dropbox").slideToggle();
							});

							// unbind click listener to reset
							$('#voice_ad_interest_category_targetting')
									.unbind();
							$('#voice_ad_interest_category_targetting')
									.bind(
											'click',
											function() {
												$(
														"#voice_ad_interest_category_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="voice_ad_interest_category_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="voice_ad_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayInterestCategoryTargettingSelection = false;
							for (var int = 0; (ad.interestCategoryTargetting != null)
									&& (int < ad.interestCategoryTargetting.length); int++) {
								var country = ad.interestCategoryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#voice_ad_interest_category_targetting')
											.attr('checked', 'checked');
									$(
											"#voice_ad_interest_category_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="voice_ad_interest_category_targetting_selection[]"]')
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
								$('#voice_ad_interest_category_targetting')
										.removeAttr('checked');
								$(
										"#voice_ad_interest_category_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#voice_ad_country_targetting').unbind();
							$('#voice_ad_country_targetting')
									.bind(
											'click',
											function() {
												$(
														"#voice_ad_country_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="voice_ad_country_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="voice_ad_country_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayCountryTargettingSelection = false;
							for (var int = 0; (ad.countryTargetting != null)
									&& (int < ad.countryTargetting.length); int++) {
								var country = ad.countryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#voice_ad_country_targetting').attr(
											'checked', 'checked');
									$(
											"#voice_ad_country_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="voice_ad_country_targetting_selection[]"]')
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
								$('#voice_ad_country_targetting').removeAttr(
										'checked');
								$("#voice_ad_country_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#voice_ad_age_targetting').unbind();
							$('#voice_ad_age_targetting')
									.bind(
											'click',
											function() {
												$(
														"#voice_ad_age_targetting_selection_div")
														.slideToggle(); // reset
												$(
														'input[name="voice_ad_age_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="voice_ad_age_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayAgeTargettingSelection = false;
							for (var int = 0; (ad.ageTargetting != null)
									&& (int < ad.ageTargetting.length); int++) {
								var age = ad.ageTargetting[int];
								log("editTextAd", "outer: " + age);
								if (age == 'all') {
									$('#voice_ad_age_targetting').attr(
											'checked', 'checked');
									$("#voice_ad_age_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="voice_ad_age_targetting_selection[]"]')
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
								$('#voice_ad_age_targetting').removeAttr(
										'checked');
								$("#voice_ad_age_targetting_selection_div")
										.show();
							}

							$(
									'input[name="voice_ad_traffic_targetting_selection"]')
									.each(
											function() {
												if (ad.trafficTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#voice_ad_traffic_targetting').val(
									ad.trafficTargetting);
							$(
									'input[name="voice_ad_gender_targetting_selection"]')
									.each(
											function() {
												if (ad.genderTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#voice_ad_gender_targetting').val(
									ad.genderTargetting);
							// set value
							if (ad.targettingOverridden == true) {
								$('#voice_ad_targetting_overridden').attr(
										'checked', 'checked');
								$('#voice_ad_targetting_div').show();
							} else {
								$('#voice_ad_targetting_overridden')
										.removeAttr('checked');
								$('#voice_ad_targetting_div').hide();
							}
							$('#voice_ad_targetting_overridden').unbind();
							$('#voice_ad_targetting_overridden').bind(
									'click',
									function() {
										$("#voice_ad_targetting_div")
												.slideToggle(); // reset
									});
							// close wait div
							;
							openPopup("voice_ad_div");

						}
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editVoiceAd", err);
		// close wait div
		;
	}
	log("editVoiceAd", "Exiting")
}

function toggleVoiceAdTrafficTargetting(target) {

	if (target == 'all') {
		$('#voice_ad_traffic_targetting').val('all');
	} else if (target == 'wifi') {
		$('#voice_ad_traffic_targetting').val('wifi');
	} else if (target == 'mobile_operator') {
		$('#voice_ad_traffic_targetting').val('mobile_operator');
	}
}
function toggleVoiceAdGenderTargetting(target) {

	if (target == 'all') {
		$('#voice_ad_gender_targetting').val('all');
	} else if (target == 'male') {
		$('#voice_ad_gender_targetting').val('male');
	} else if (target == 'female') {
		$('#voice_ad_gender_targetting').val('female');
	}
}

function newVoiceAd(campaignId, adGroupId) {
	log("newVoiceAd", "Entering")
	try {
		$('#voice_ad_setup_header').toggle(true);
		$('#voice_ad_save_button').html('create ad');
		// reset
		$('input:checkbox').removeAttr('checked');

		// reset
		$('#voice_ad_save_button').unbind();
		$('#voice_ad_save_button').bind('click', createVoiceAd);
		$('#voice_ad_cancel_button').one('click', function() {

			$('#voice_ad_save_button').unbind();
			;
		});
		$('#voice_ad_enabled').attr('checked', 'checked');
		$('#voice_ad_enabled').bind('click', function() {
			if ($('#voice_ad_enabled').is(':checked')) {
				$('#voice_ad_status').html('Enabled');
			} else {
				$('#voice_ad_status').html('Disabled');
			}
		});
		$('#voice_ad_frequency_capping').val('');
		$('#voice_ad_cpi').val('');
		$('#voice_ad_cpc').val('');

		$('#voice_ad_id').val('');
		$('#voice_ad_campaign_id').val(campaignId);
		$('#voice_ad_adgroup_id').val(adGroupId);
		$('#voice_ad_start_date').val(getCurrentDisplayDate());
		$('#voice_ad_end_date').val('');

		$('#voice_ad_name').val('');
		$('#voice_ad_description').val('');

		$('#voice_ad_uri').val('');

		$('#voice_ad_errors').empty();

		var dropBoxUrl = getDropboxUrl();

		setupVoiceAdDropBox(dropBoxUrl);
		$('#view_ad_voice').hide();
		$('#view_voice').hide();
		$("#voice_ad_dropbox").hide();
		// reset
		$('#upload_voice_ad').unbind();
		$('#upload_voice_ad').bind('click', function() {
			$("#voice_ad_dropbox").slideToggle();
		});
		// unbind click listener to reset
		$('#voice_ad_interest_category_targetting').unbind();
		$('#voice_ad_interest_category_targetting')
				.bind(
						'click',
						function() {
							$(
									"#voice_ad_interest_category_targetting_selection_div")
									.slideToggle();
							$(
									'input[name="voice_ad_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
						});

		// set default
		$('#voice_ad_interest_category_targetting').attr('checked', 'checked');
		$("#voice_ad_interest_category_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#voice_ad_country_targetting').unbind();
		$('#voice_ad_country_targetting').bind(
				'click',
				function() {
					$("#voice_ad_country_targetting_selection_div")
							.slideToggle();
					$('input[name="voice_ad_country_targetting_selection[]"]')
							.removeAttr('checked');
				});

		// set default
		$('#voice_ad_country_targetting').attr('checked', 'checked');
		$("#voice_ad_country_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#voice_ad_age_targetting').unbind();
		$('#voice_ad_age_targetting').bind(
				'click',
				function() {
					$("#voice_ad_age_targetting_selection_div").slideToggle(); // reset
					$('input[name="voice_ad_age_targetting_selection[]"]')
							.removeAttr('checked');
				});
		// set default
		$('#voice_ad_age_targetting').attr('checked', 'checked');
		$("#voice_ad_age_targetting_selection_div").hide();

		// set default
		$('#voice_ad_traffic_targetting').val('all');
		$('input[name="voice_ad_traffic_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});
		// set default
		$('#voice_ad_gender_targetting').val('all');
		$('input[name="voice_ad_gender_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});
		// set default
		$('#voice_ad_targetting_overridden').removeAttr('checked');
		$('#voice_ad_targetting_overridden').unbind();
		$('#voice_ad_targetting_overridden').bind('click', function() {
			$("#voice_ad_targetting_div").slideToggle(); // reset
		});
		$('#voice_ad_targetting_div').hide();
		openPopup("voice_ad_div");
	} catch (err) {
		handleError("newVoiceAd", err);
	}
	log("newVoiceAd", "Exiting")
}

function toggleVoiceAdType(preload) {
	log("toggleVoiceAdType", "Entering")
	try {
		if (preload) {
			openWait();
			var url = "secured/samplevoicead";
			// make sync call
			var jqxhr = $.ajax({
				url : url,
				type : "GET",
				contentType : "application/json",
				async : false,
				statusCode : {
					200 : function(ad) {
						$('#voice_ad_id').val(ad.id);
						$('#voice_ad_name').val(ad.name);
						$('#voice_ad_description').val(ad.description);
						// do not modify voice_ad_uri
						$('#voice_ad_errors').empty();
					},
					400 : function(voice) {
						try {
							$('#voice_ad_errors').html(
									'invalid ad type requested');
						} catch (err) {
							handleError("toggleVoiceAdType", err);
						}
					}
				}
			});
			jqxhr.always(function() {
				// close wait div
				closeWait();
			});
		} else {
			$('#voice_ad_id').val('');
			$('#voice_ad_name').val('');
			$('#voice_ad_description').val('');
			// do not modify voice_ad_uri
			$('#voice_ad_errors').empty();
		}
	} catch (err) {
		handleError("toggleVoiceAdType", err);
	}
	log("toggleVoiceAdType", "Exiting")
}

function createVoiceAd() {
	log("createVoiceAd", "Entering")
	try {
		mSubmitLock = true;
		openWait();
		var _campaignId = $('#voice_ad_campaign_id').val();
		var _adGroupId = $('#voice_ad_adgroup_id').val();
		var _adStartDate = $('#voice_ad_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#voice_ad_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "voice_ad";
		var _targetApplication = $(
				'input[name="voice_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _enabled;
		if ($('#voice_ad_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}
		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#voice_ad_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#voice_ad_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#voice_ad_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="voice_ad_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#voice_ad_country_targetting').is(':checked')) {
				_countryTargetting
						.push($('#voice_ad_country_targetting').val());
			} else {
				_countryTargetting = $(
						'input[name="voice_ad_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#voice_ad_age_targetting').is(':checked')) {
				_ageTargetting.push($('#voice_ad_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="voice_ad_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#voice_ad_traffic_targetting').val());
			_genderTargetting.push($('#voice_ad_gender_targetting').val());
		}
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var adObj = {
			id : $('#voice_ad_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			targetApplication : _targetApplication,
			name : $('#voice_ad_name').val(),
			description : $('#voice_ad_description').val(),
			uri : $('#voice_ad_uri').val(),
			enabled : _enabled,
			cpi : $('#voice_ad_cpi').val(),
			cpc : $('#voice_ad_cpc').val(),
			frequencyCappingPerDay : $('#voice_ad_frequency_capping').val(),
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
		// create via sync call
		var jqxhr = $.ajax({
			url : "secured/voicead",
			type : "POST",
			data : adObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#voice_ad_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(voice) {
					try {
						$('#voice_ad_errors').html(getErrorMessages(voice));
					} catch (err) {
						handleError("createVoiceAd", err);
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
		handleError("createVoiceAd", err);
	}
	log("createVoiceAd", "Exiting")
}
function updateVoiceAd() {
	log("updateVoiceAd", "Entering")
	try {
		openWait();
		var _campaignId = $('#voice_ad_campaign_id').val();
		var _adGroupId = $('#voice_ad_adgroup_id').val();
		var _adStartDate = $('#voice_ad_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#voice_ad_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "voice_ad";
		var _targetApplication = $(
				'input[name="voice_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _enabled;
		if ($('#voice_ad_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}
		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#voice_ad_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#voice_ad_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#voice_ad_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="voice_ad_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#voice_ad_country_targetting').is(':checked')) {
				_countryTargetting
						.push($('#voice_ad_country_targetting').val());
			} else {
				_countryTargetting = $(
						'input[name="voice_ad_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#voice_ad_age_targetting').is(':checked')) {
				_ageTargetting.push($('#voice_ad_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="voice_ad_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#voice_ad_traffic_targetting').val());
			_genderTargetting.push($('#voice_ad_gender_targetting').val());
		}
		var _date = new Date();
		var adObj = {
			id : $('#voice_ad_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			targetApplication : _targetApplication,
			name : $('#voice_ad_name').val(),
			description : $('#voice_ad_description').val(),
			uri : $('#voice_ad_uri').val(),
			enabled : _enabled,
			cpi : $('#voice_ad_cpi').val(),
			cpc : $('#voice_ad_cpc').val(),
			frequencyCappingPerDay : $('#voice_ad_frequency_capping').val(),
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
			url : "secured/voicead",
			type : "PUT",
			data : adObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#voice_ad_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(voice) {
					try {
						$('#voice_ad_errors').html(getErrorMessages(voice));
					} catch (err) {
						handleError("updateVoiceAd", err);
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
		handleError("updateVoiceAd", err);
	}
	log("updateVoiceAd", "Exiting")
}

function showVoiceAd() {
	log("showVoiceAd", "Entering")
	try {
		openPopup("voice_ad_div");
	} catch (err) {
		handleError("showVoiceAd", err);
	}
	log("showVoiceAd", "Exiting")
}

/** End Ad****************************************** */
