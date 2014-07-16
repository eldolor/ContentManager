/** Begin Ad****************************************** */
function editSurvey(id) {
	log("editSurvey", "Entering")

	try {
		// open wait div
		openWait();
		// load entry info via ajax
		var url = "/secured/ad/" + id;
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					statusCode : {
						200 : function(ad) {
							$('#survey_setup_header').toggle(false);
							$('#survey_save_button').html('update ad');
							// reset
							// reset
							$('#survey_save_button').unbind();
							$('#survey_save_button')
									.bind('click', updateSurvey);
							$('#survey_cancel_button').one('click', function() {

								$('#survey_save_button').unbind();
								;
							});

							$('#survey_id').val(ad.id);
							$('#survey_campaign_id').val(ad.campaignId);
							$('#survey_adgroup_id').val(ad.adGroupId);
							$('#survey_start_date').val(
									getDisplayDate(ad.startDateIso8601));
							$('#survey_end_date').val(
									getDisplayDate(ad.endDateIso8601));

							$('#survey_question').val(ad.question);

							$('#survey_option_1_description').val(
									ad.option1Description);
							$('#survey_option_1_value').val(ad.option1Value);

							$('#survey_option_2_description').val(
									ad.option2Description);
							$('#survey_option_2_value').val(ad.option2Value);

							$('#survey_option_3_description').val(
									ad.option3Description);
							$('#survey_option_3_value').val(ad.option3Value);

							$('#survey_option_4_description').val(
									ad.option4Description);
							$('#survey_option_4_value').val(ad.option4Value);

							$('#survey_option_5_description').val(
									ad.option5Description);
							$('#survey_option_5_value').val(ad.option5Value);

							$('#survey_errors').empty();

							if (ad.hasOwnProperty('enabled')) {
								log("editImageAd", "Ad enabled: " + ad.enabled);
								if (ad.enabled == true) {
									$('#survey_enabled').attr('checked',
											'checked');
									$('#survey_status').html('Enabled');
								} else {
									$('#survey_enabled').removeAttr('checked');
									$('#survey_status').html('Disabled');
								}
								var jqxhr = $
										.ajax({
											url : "secured/linkedads/" + id,
											type : "GET",
											contentType : "application/json",
											async : true,
											statusCode : {
												200 : function(count) {
													if (count.count) {
														// cannot disable this
														// ad, as it is linked
														// to other ads.
														$('#survey_enabled')
																.attr(
																		"disabled",
																		true);
														$('#survey_status')
																.html(
																		'cannot be disabled as it is linked to '
																				+ count.count
																				+ ' other ads.');
													}
												}
											}
										});
							}
							$('#survey_enabled').bind('click', function() {
								if ($('#survey_enabled').is(':checked')) {
									$('#survey_status').html('Enabled');
								} else {
									$('#survey_status').html('Disabled');
								}
							});
							$('#survey_frequency_capping').val(
									ad.frequencyCappingPerDay);
							$('#survey_cpi').val(ad.cpi);
							$('#survey_cpc').val(ad.cpc);

							// unbind click listener to reset
							$('#survey_interest_category_targetting').unbind();
							$('#survey_interest_category_targetting')
									.bind(
											'click',
											function() {
												$(
														"#survey_interest_category_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="survey_interest_category_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="survey_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayInterestCategoryTargettingSelection = false;
							for (var int = 0; (ad.interestCategoryTargetting != null)
									&& (int < ad.interestCategoryTargetting.length); int++) {
								var country = ad.interestCategoryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#survey_interest_category_targetting')
											.attr('checked', 'checked');
									$(
											"#survey_interest_category_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="survey_interest_category_targetting_selection[]"]')
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
								$('#survey_interest_category_targetting')
										.removeAttr('checked');
								$(
										"#survey_interest_category_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#survey_country_targetting').unbind();
							$('#survey_country_targetting')
									.bind(
											'click',
											function() {
												$(
														"#survey_country_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="survey_country_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="survey_country_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayCountryTargettingSelection = false;
							for (var int = 0; (ad.countryTargetting != null)
									&& (int < ad.countryTargetting.length); int++) {
								var country = ad.countryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#survey_country_targetting').attr(
											'checked', 'checked');
									$(
											"#survey_country_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="survey_country_targetting_selection[]"]')
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
								$('#survey_country_targetting').removeAttr(
										'checked');
								$("#survey_country_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#survey_age_targetting').unbind();
							$('#survey_age_targetting')
									.bind(
											'click',
											function() {
												$(
														"#survey_age_targetting_selection_div")
														.slideToggle(); // reset
												$(
														'input[name="survey_age_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$('input[name="survey_age_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayAgeTargettingSelection = false;
							for (var int = 0; (ad.ageTargetting != null)
									&& (int < ad.ageTargetting.length); int++) {
								var age = ad.ageTargetting[int];
								log("editTextAd", "outer: " + age);
								if (age == 'all') {
									$('#survey_age_targetting').attr('checked',
											'checked');
									$("#survey_age_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="survey_age_targetting_selection[]"]')
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
								$('#survey_age_targetting').removeAttr(
										'checked');
								$("#survey_age_targetting_selection_div")
										.show();
							}

							$(
									'input[name="survey_traffic_targetting_selection"]')
									.each(
											function() {
												if (ad.trafficTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#survey_traffic_targetting').val(
									ad.trafficTargetting);
							$(
									'input[name="survey_gender_targetting_selection"]')
									.each(
											function() {
												if (ad.genderTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#survey_gender_targetting').val(
									ad.genderTargetting);
							
							// set value
							if (ad.targettingOverridden == true) {
								$('#survey_targetting_overridden').attr(
										'checked', 'checked');
								$('#survey_targetting_div').show();
							} else {
								$('#survey_targetting_overridden').removeAttr(
										'checked');
								$('#survey_targetting_div').hide();
							}
							$('#survey_targetting_overridden').unbind();
							$('#survey_targetting_overridden').bind(
									'click',
									function() {
										$("#survey_targetting_div")
												.slideToggle(); // reset
									});
							// close wait div
							;
							openPopup("survey_div");
						}
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editSurvey", err);
		// close wait div
		;
	}
	log("editImageAd", "Exiting")
}

function toggleSurveyTrafficTargetting(target) {

	if (target == 'all') {
		$('#survey_traffic_targetting').val('all');
	} else if (target == 'wifi') {
		$('#survey_traffic_targetting').val('wifi');
	} else if (target == 'mobile_operator') {
		$('#survey_traffic_targetting').val('mobile_operator');
	}
}
function toggleSurveyGenderTargetting(target) {

	if (target == 'all') {
		$('#survey_gender_targetting').val('all');
	} else if (target == 'male') {
		$('#survey_gender_targetting').val('male');
	} else if (target == 'female') {
		$('#survey_gender_targetting').val('female');
	}
}

function newSurvey(campaignId, adGroupId) {
	log("newSurvey", "Entering");
	log("campaign id: " + campaignId);
	try {
		$('#survey_setup_header').toggle(true);
		$('#survey_save_button').html('create ad');

		// reset
		// reset
		//
		$('#survey_save_button').unbind();
		$('#survey_save_button').bind('click', createSurvey);
		$('#survey_cancel_button').one('click', function() {

			$('#survey_save_button').unbind();
			;
		});
		$('#survey_id').val('');
		$('#survey_campaign_id').val(campaignId);
		$('#survey_adgroup_id').val(adGroupId);
		$('#survey_start_date').val(getCurrentDisplayDate());
		$('#survey_end_date').val('');
		$('#survey_frequency_capping').val('');
		$('#survey_cpi').val('');
		$('#survey_cpc').val('');

		$('#survey_question').val('');
		$('#survey_option_1_description').val('');
		$('#survey_option_1_value').val('');

		$('#survey_option_2_description').val('');
		$('#survey_option_2_value').val('');

		$('#survey_option_3_description').val('');
		$('#survey_option_3_value').val('');

		$('#survey_option_4_description').val('');
		$('#survey_option_4_value').val('');

		$('#survey_option_5_description').val('');
		$('#survey_option_5_value').val('');

		$('#survey_errors').empty();

		$('#survey_enabled').attr('checked', 'checked');
		$('#survey_enabled').bind('click', function() {
			if ($('#survey_enabled').is(':checked')) {
				$('#survey_status').html('Enabled');
			} else {
				$('#survey_status').html('Disabled');
			}
		});
		// unbind click listener to reset
		$('#survey_interest_category_targetting').unbind();
		$('#survey_interest_category_targetting')
				.bind(
						'click',
						function() {
							$(
									"#survey_interest_category_targetting_selection_div")
									.slideToggle();
							$(
									'input[name="survey_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
						});

		// set default
		$('#survey_interest_category_targetting').attr('checked', 'checked');
		$("#survey_interest_category_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#survey_country_targetting').unbind();
		$('#survey_country_targetting')
				.bind(
						'click',
						function() {
							$("#survey_country_targetting_selection_div")
									.slideToggle();
							$(
									'input[name="survey_country_targetting_selection[]"]')
									.removeAttr('checked');
						});

		// set default
		$('#survey_country_targetting').attr('checked', 'checked');
		$("#survey_country_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#survey_age_targetting').unbind();
		$('#survey_age_targetting').bind(
				'click',
				function() {
					$("#survey_age_targetting_selection_div").slideToggle(); // reset
					$('input[name="survey_age_targetting_selection[]"]')
							.removeAttr('checked');
				});
		// set default
		$('#survey_age_targetting').attr('checked', 'checked');
		$("#survey_age_targetting_selection_div").hide();

		// set default
		$('#survey_traffic_targetting').val('all');
		$('input[name="survey_traffic_targetting_selection"]').each(function() {
			if ('all' == $(this).val()) {
				$(this).attr('checked', 'checked');
				return;
			}
		});
		// set default
		$('#survey_gender_targetting').val('all');
		$('input[name="survey_gender_targetting_selection"]').each(function() {
			if ('all' == $(this).val()) {
				$(this).attr('checked', 'checked');
				return;
			}
		});
		// set default
		$('#survey_targetting_overridden').removeAttr('checked');
		$('#survey_targetting_overridden').unbind();
		$('#survey_targetting_overridden').bind('click', function() {
			$("#survey_targetting_div").slideToggle(); // reset
		});
		$('#survey_targetting_div').hide();

		openPopup("survey_div");
	} catch (err) {
		handleError("newSurvey", err);
	}
	log("newSurvey", "Exiting")
}

function toggleSurveyType(preload) {
	log("toggleSurveyType", "Entering")
	try {
		if (preload) {
			openWait();
			var url = "/secured/samplesurvey";
			// make sync call
			var jqxhr = $.ajax({
				url : url,
				type : "GET",
				contentType : "application/json",
				async : false,
				statusCode : {
					200 : function(ad) {
						$('#survey_id').val(ad.id);

						$('#survey_question').val(ad.question);
						$('#survey_option_1_description').val(
								ad.option1Description);
						$('#survey_option_1_value').val(ad.option1Value);

						$('#survey_option_2_description').val(
								ad.option2Description);
						$('#survey_option_2_value').val(ad.option2Value);

						$('#survey_option_3_description').val(
								ad.option3Description);
						$('#survey_option_3_value').val(ad.option3Value);

						$('#survey_option_4_description').val(
								ad.option4Description);
						$('#survey_option_4_value').val(ad.option4Value);

						$('#survey_option_5_description').val(
								ad.option5Description);
						$('#survey_option_5_value').val(ad.option5Value);
						$('#survey_errors').empty();
					},
					400 : function(image) {
						try {
							$('#survey_errors').html(
									'invalid ad type requested');
						} catch (err) {
							handleError("toggleSurveyType", err);
						}
					}
				}
			});
			jqxhr.always(function() {
				// close wait div
				closeWait();
			});
		} else {
			$('#survey_id').val('');
			$('#survey_question').val('');
			$('#survey_option_1_description').val('');
			$('#survey_option_1_value').val('');

			$('#survey_option_2_description').val('');
			$('#survey_option_2_value').val('');

			$('#survey_option_3_description').val('');
			$('#survey_option_3_value').val('');

			$('#survey_option_4_description').val('');
			$('#survey_option_4_value').val('');

			$('#survey_option_5_description').val('');
			$('#survey_option_5_value').val('');
			$('#survey_errors').empty();
		}
	} catch (err) {
		handleError("toggleImageAdType", err);
	}
	log("toggleImageAdType", "Exiting")
}

function createSurvey() {
	log("createSurvey", "Entering")
	try {
		mSubmitLock = true;
		openWait();
		var _campaignId = $('#survey_campaign_id').val();
		var _adGroupId = $('#survey_adgroup_id').val();

		var _adStartDate = $('#survey_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#survey_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "survey";
		var _enabled;
		if ($('#survey_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#survey_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#survey_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#survey_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="survey_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#survey_country_targetting').is(':checked')) {
				_countryTargetting.push($('#survey_country_targetting').val());
			} else {
				_countryTargetting = $(
						'input[name="survey_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#survey_age_targetting').is(':checked')) {
				_ageTargetting.push($('#survey_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="survey_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#survey_traffic_targetting').val());
			_genderTargetting.push($('#survey_gender_targetting').val());
		}
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var jsonObj = {
			id : $('#survey_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			question : $('#survey_question').val(),
			option1Description : $('#survey_option_1_description').val(),
			option1Value : $('#survey_option_1_value').val(),

			option2Description : $('#survey_option_2_description').val(),
			option2Value : $('#survey_option_2_value').val(),

			option3Description : $('#survey_option_3_description').val(),
			option3Value : $('#survey_option_3_value').val(),

			option4Description : $('#survey_option_4_description').val(),
			option4Value : $('#survey_option_4_value').val(),

			option5Description : $('#survey_option_5_description').val(),
			option5Value : $('#survey_option_5_value').val(),

			enabled : _enabled,
			cpi : $('#survey_cpi').val(),
			cpc : $('#survey_cpc').val(),
			frequencyCappingPerDay : $('#survey_frequency_capping').val(),
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
		var jsonObjString = JSON.stringify(jsonObj, null, 2);

		// create via sync call
		var jqxhr = $.ajax({
			url : "/secured/survey",
			type : "POST",
			data : jsonObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#survey_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(image) {
					try {
						$('#survey_errors').html(getErrorMessages(image));
					} catch (err) {
						handleError("createSurvey", err);
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
		handleError("createSurvey", err);
	}
	log("createSurvey", "Exiting")
}
function updateSurvey() {
	log("updateSurvey", "Entering")
	try {
		openWait();
		var _campaignId = $('#survey_campaign_id').val();
		var _adGroupId = $('#survey_adgroup_id').val();
		var _adStartDate = $('#survey_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#survey_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "survey";
		var _enabled;
		if ($('#survey_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#survey_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#survey_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#survey_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="survey_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#survey_country_targetting').is(':checked')) {
				_countryTargetting.push($('#survey_country_targetting').val());
			} else {
				_countryTargetting = $(
						'input[name="survey_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#survey_age_targetting').is(':checked')) {
				_ageTargetting.push($('#survey_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="survey_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#survey_traffic_targetting').val());
			_genderTargetting.push($('#survey_gender_targetting').val());
		}
		var _date = new Date();

		var jsonObj = {
			id : $('#survey_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			question : $('#survey_question').val(),
			option1Description : $('#survey_option_1_description').val(),
			option1Value : $('#survey_option_1_value').val(),

			option2Description : $('#survey_option_2_description').val(),
			option2Value : $('#survey_option_2_value').val(),

			option3Description : $('#survey_option_3_description').val(),
			option3Value : $('#survey_option_3_value').val(),

			option4Description : $('#survey_option_4_description').val(),
			option4Value : $('#survey_option_4_value').val(),

			option5Description : $('#survey_option_5_description').val(),
			option5Value : $('#survey_option_5_value').val(),

			enabled : _enabled,
			cpi : $('#voice_ad_cpi').val(),
			cpc : $('#voice_ad_cpc').val(),
			frequencyCappingPerDay : $('#survey_frequency_capping').val(),
			targettingOverridden : _targettingOverridden,
			interestCategoryTargetting : _interestCategoryTargetting,
			countryTargetting : _countryTargetting,
			trafficTargetting : _trafficTargetting,
			genderTargetting : _genderTargetting,
			ageTargetting : _ageTargetting,
			timeUpdatedMs : _date.getTime(),
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)

		};

		var jsonObjString = JSON.stringify(jsonObj, null, 2);
		// create via sync call
		var jqxhr = $.ajax({
			url : "/secured/survey",
			type : "PUT",
			data : jsonObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#survey_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(image) {
					try {
						$('#survey_errors').html(getErrorMessages(image));
					} catch (err) {
						handleError("updateSurvey", err);
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
		handleError("updateSurvey", err);
	}
	log("updateImageAd", "Exiting")
}

/** End Ad****************************************** */
