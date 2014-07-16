/** Begin Ad****************************************** */

function editVideoAd(id) {
	log("editVideoAd", "Entering")
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
							$('#video_ad_setup_header').toggle(false);
							$('#video_ad_save_button').html('update ad');
							// reset
							$('#video_ad_save_button').unbind();
							$('#video_ad_save_button').bind('click',
									updateVideoAd);
							$('#video_ad_cancel_button').one('click',
									function() {

										$('#video_ad_save_button').unbind();
										;
									});
							$('#video_ad_id').val(ad.id);
							$('#video_ad_campaign_id').val(ad.campaignId);
							$('#video_ad_adgroup_id').val(ad.adGroupId);
							$('#video_ad_start_date').val(
									getDisplayDate(ad.startDateIso8601));
							$('#video_ad_end_date').val(
									getDisplayDate(ad.endDateIso8601));

							// reset
							$('input:checkbox').removeAttr('checked');
							for (var int = 0; int < ad.targetApplication.length; int++) {
								var targetApp = ad.targetApplication[int];
								log("editTextAd", "outer: " + targetApp);
								$('input[name="video_ad_target_application[]"]')
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

							$('#video_ad_name').val(ad.name);
							$('#video_ad_description').val(ad.description);

							$('#video_ad_uri').val(ad.uri);
							$('#video_ad_learn_more_uri').val(ad.learnMoreUri);

							$('#video_ad_errors').empty();

							var dropBoxUrl = getDropboxUrl();
							setupVideoAdDropBox(dropBoxUrl);

							$("#video_ad_dropbox").hide();
							// reset
							$('#upload_ad_video').unbind();
							$('#upload_ad_video').bind('click', function() {
								$("#video_ad_dropbox").slideToggle();
							});
							if (ad.hasOwnProperty('enabled')) {
								log("editImageAd", "Ad enabled: " + ad.enabled);
								if (ad.enabled == true) {
									$('#video_ad_enabled').attr('checked',
											'checked');
									$('#video_ad_status').html('Enabled');
								} else {
									$('#video_ad_enabled')
											.removeAttr('checked');
									$('#video_ad_status').html('Disabled');
								}
								var jqxhr = $
										.ajax({
											url : "secured/linkedads/" + id,
											type : "GET",
											contentType : "application/json",
											async : true,
											statusCode : {
												200 : function(count) {
													if (count.count > 0) {
														// cannot disable this
														// ad, as it is linked
														// to other ads.
														$('#video_ad_enabled')
																.attr(
																		"disabled",
																		true);
														$('#video_ad_status')
																.html(
																		'cannot be disabled as it is linked to '
																				+ count.count
																				+ ' other ads.');
													}
												}
											}
										});
							}
							$('#video_ad_enabled').bind('click', function() {
								if ($('#video_ad_enabled').is(':checked')) {
									$('#video_ad_status').html('Enabled');
								} else {
									$('#video_ad_status').html('Disabled');
								}
							});
							$('#video_ad_frequency_capping').val(
									ad.frequencyCappingPerDay);
							$('#video_ad_cpi').val(ad.cpi);
							$('#video_ad_cpc').val(ad.cpc);

							// unbind click listener to reset
							$('#video_ad_interest_category_targetting')
									.unbind();
							$('#video_ad_interest_category_targetting')
									.bind(
											'click',
											function() {
												$(
														"#video_ad_interest_category_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="video_ad_interest_category_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="video_ad_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayInterestCategoryTargettingSelection = false;
							for (var int = 0; (ad.interestCategoryTargetting != null)
									&& (int < ad.interestCategoryTargetting.length); int++) {
								var country = ad.interestCategoryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#video_ad_interest_category_targetting')
											.attr('checked', 'checked');
									$(
											"#video_ad_interest_category_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="video_ad_interest_category_targetting_selection[]"]')
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
								$('#video_ad_interest_category_targetting')
										.removeAttr('checked');
								$(
										"#video_ad_interest_category_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#video_ad_country_targetting').unbind();
							$('#video_ad_country_targetting')
									.bind(
											'click',
											function() {
												$(
														"#video_ad_country_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="video_ad_country_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="video_ad_country_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayCountryTargettingSelection = false;
							for (var int = 0; (ad.countryTargetting != null)
									&& (int < ad.countryTargetting.length); int++) {
								var country = ad.countryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#video_ad_country_targetting').attr(
											'checked', 'checked');
									$(
											"#video_ad_country_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="video_ad_country_targetting_selection[]"]')
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
								$('#video_ad_country_targetting').removeAttr(
										'checked');
								$("#video_ad_country_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#video_ad_age_targetting').unbind();
							$('#video_ad_age_targetting')
									.bind(
											'click',
											function() {
												$(
														"#video_ad_age_targetting_selection_div")
														.slideToggle(); // reset
												$(
														'input[name="video_ad_age_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="video_ad_age_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayAgeTargettingSelection = false;
							for (var int = 0; (ad.ageTargetting != null)
									&& (int < ad.ageTargetting.length); int++) {
								var age = ad.ageTargetting[int];
								log("editTextAd", "outer: " + age);
								if (age == 'all') {
									$('#video_ad_age_targetting').attr(
											'checked', 'checked');
									$("#video_ad_age_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="video_ad_age_targetting_selection[]"]')
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
								$('#video_ad_age_targetting').removeAttr(
										'checked');
								$("#video_ad_age_targetting_selection_div")
										.show();
							}

							$(
									'input[name="video_ad_traffic_targetting_selection"]')
									.each(
											function() {
												if (ad.trafficTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#video_ad_traffic_targetting').val(
									ad.trafficTargetting);
							$(
									'input[name="video_ad_gender_targetting_selection"]')
									.each(
											function() {
												if (ad.genderTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#video_ad_gender_targetting').val(
									ad.genderTargetting);
							// set value
							if (ad.targettingOverridden == true) {
								$('#video_ad_targetting_overridden').attr(
										'checked', 'checked');
								$('#video_ad_targetting_div').show();
							} else {
								$('#video_ad_targetting_overridden')
										.removeAttr('checked');
								$('#video_ad_targetting_div').hide();
							}
							$('#video_ad_targetting_overridden').unbind();
							$('#video_ad_targetting_overridden').bind(
									'click',
									function() {
										$("#video_ad_targetting_div")
												.slideToggle(); // reset
									});
							// close wait div
							;
							openPopup("video_ad_div");

						}
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editVideoAd", err);
		// close wait div
		;
	}
	log("editVideoAd", "Exiting")
}

function toggleVideoAdTrafficTargetting(target) {

	if (target == 'all') {
		$('#video_ad_traffic_targetting').val('all');
	} else if (target == 'wifi') {
		$('#video_ad_traffic_targetting').val('wifi');
	} else if (target == 'mobile_operator') {
		$('#video_ad_traffic_targetting').val('mobile_operator');
	}
}
function toggleVideoAdGenderTargetting(target) {

	if (target == 'all') {
		$('#video_ad_gender_targetting').val('all');
	} else if (target == 'male') {
		$('#video_ad_gender_targetting').val('male');
	} else if (target == 'female') {
		$('#video_ad_gender_targetting').val('female');
	}
}

function newVideoAd(campaignId, adGroupId) {
	log("newVideoAd", "Entering")
	try {
		$('#video_ad_setup_header').toggle(true);
		$('#video_ad_save_button').html('create ad');
		// reset
		$('input:checkbox').removeAttr('checked');

		// reset
		$('#video_ad_save_button').unbind();
		$('#video_ad_save_button').bind('click', createVideoAd);
		$('#video_ad_cancel_button').one('click', function() {

			$('#video_ad_save_button').unbind();
			;
		});

		$('#video_ad_id').val('');
		$('#video_ad_campaign_id').val(campaignId);
		$('#video_ad_adgroup_id').val(adGroupId);
		$('#video_ad_start_date').val(getCurrentDisplayDate());
		$('#video_ad_end_date').val('');

		$('#video_ad_name').val('');
		$('#video_ad_description').val('');

		$('#video_ad_uri').val('');
		$('#video_ad_learn_more_uri').val('');

		$('#video_ad_errors').empty();

		$('#video_ad_enabled').attr('checked', 'checked');
		$('#video_ad_enabled').bind('click', function() {
			if ($('#video_ad_enabled').is(':checked')) {
				$('#video_ad_status').html('Enabled');
			} else {
				$('#video_ad_status').html('Disabled');
			}
		});
		$('#video_ad_frequency_capping').val();
		$('#video_ad_cpi').val();
		$('#video_ad_cpc').val();

		var dropBoxUrl = getDropboxUrl();
		setupVideoAdDropBox(dropBoxUrl);
		$('#view_ad_video').hide();
		$('#view_video').hide();
		$("#video_ad_dropbox").hide();
		// reset
		$('#upload_ad_video').unbind();
		$('#upload_ad_video').bind('click', function() {
			$("#video_ad_dropbox").slideToggle();
		});
		// unbind click listener to reset
		$('#video_ad_interest_category_targetting').unbind();
		$('#video_ad_interest_category_targetting')
				.bind(
						'click',
						function() {
							$(
									"#video_ad_interest_category_targetting_selection_div")
									.slideToggle();
							$(
									'input[name="video_ad_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
						});

		// set default
		$('#video_ad_interest_category_targetting').attr('checked', 'checked');
		$("#video_ad_interest_category_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#video_ad_country_targetting').unbind();
		$('#video_ad_country_targetting').bind(
				'click',
				function() {
					$("#video_ad_country_targetting_selection_div")
							.slideToggle();
					$('input[name="video_ad_country_targetting_selection[]"]')
							.removeAttr('checked');
				});

		// set default
		$('#video_ad_country_targetting').attr('checked', 'checked');
		$("#video_ad_country_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#video_ad_age_targetting').unbind();
		$('#video_ad_age_targetting').bind(
				'click',
				function() {
					$("#video_ad_age_targetting_selection_div").slideToggle(); // reset
					$('input[name="video_ad_age_targetting_selection[]"]')
							.removeAttr('checked');
				});
		// set default
		$('#video_ad_age_targetting').attr('checked', 'checked');
		$("#video_ad_age_targetting_selection_div").hide();

		// set default
		$('#video_ad_traffic_targetting').val('all');
		$('input[name="video_ad_traffic_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});
		// set default
		$('#video_ad_gender_targetting').val('all');
		$('input[name="video_ad_gender_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});
		// set default
		$('#video_ad_targetting_overridden').removeAttr('checked');
		$('#video_ad_targetting_overridden').unbind();
		$('#video_ad_targetting_overridden').bind('click', function() {
			$("#video_ad_targetting_div").slideToggle(); // reset
		});
		$('#video_ad_targetting_div').hide();
		openPopup("video_ad_div");
	} catch (err) {
		handleError("newVideoAd", err);
	}
	log("newVideoAd", "Exiting")
}

function toggleVideoAdType(preload) {
	log("toggleVideoAdType", "Entering")
	try {
		if (preload) {
			openWait();
			var url = "secured/samplevideoad";
			// make sync call
			var jqxhr = $.ajax({
				url : url,
				type : "GET",
				contentType : "application/json",
				async : false,
				statusCode : {
					200 : function(ad) {
						$('#video_ad_id').val(ad.id);
						$('#video_ad_name').val(ad.name);
						$('#video_ad_description').val(ad.description);
						// do not modify video_ad_uri
						$('#video_ad_learn_more_uri').val(ad.learnMoreUri);
						$('#video_ad_errors').empty();
					},
					400 : function(video) {
						try {
							$('#video_ad_errors').html(
									'invalid ad type requested');
						} catch (err) {
							handleError("toggleVideoAdType", err);
						}
					}
				}
			});
			jqxhr.always(function() {
				// close wait div
				closeWait();
			});
		} else {
			$('#video_ad_id').val('');
			$('#video_ad_name').val('');
			$('#video_ad_description').val('');
			// do not modify video_ad_uri
			$('#video_ad_learn_more_uri').val('');
			$('#video_ad_errors').empty();
		}
	} catch (err) {
		handleError("toggleVideoAdType", err);
	}
	log("toggleVideoAdType", "Exiting")
}

function createVideoAd() {
	log("createVideoAd", "Entering")
	try {
		mSubmitLock = true;
		openWait();
		var _campaignId = $('#video_ad_campaign_id').val();
		var _adGroupId = $('#video_ad_adgroup_id').val();
		var _adStartDate = $('#video_ad_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#video_ad_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "video_ad";
		var _targetApplication = $(
				'input[name="video_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _enabled;
		if ($('#video_ad_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#video_ad_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#video_ad_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#video_ad_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="video_ad_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#video_ad_country_targetting').is(':checked')) {
				_countryTargetting
						.push($('#video_ad_country_targetting').val());
			} else {
				_countryTargetting = $(
						'input[name="video_ad_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#video_ad_age_targetting').is(':checked')) {
				_ageTargetting.push($('#video_ad_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="video_ad_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#video_ad_traffic_targetting').val());
			_genderTargetting.push($('#video_ad_gender_targetting').val());
		}
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var adObj = {
			id : $('#video_ad_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			targetApplication : _targetApplication,
			name : $('#video_ad_name').val(),
			description : $('#video_ad_description').val(),
			uri : $('#video_ad_uri').val(),
			learnMoreUri : $('#video_ad_learn_more_uri').val(),
			enabled : _enabled,
			cpi : $('#video_ad_cpi').val(),
			cpc : $('#video_ad_cpc').val(),
			frequencyCappingPerDay : $('#video_ad_frequency_capping').val(),
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
			url : "secured/videoad",
			type : "POST",
			data : adObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#video_ad_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(video) {
					try {
						$('#video_ad_errors').html(getErrorMessages(video));
					} catch (err) {
						handleError("createVideoAd", err);
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
		handleError("createVideoAd", err);
	}
	log("createVideoAd", "Exiting")
}
function updateVideoAd() {
	log("updateVideoAd", "Entering")
	try {
		openWait();
		var _campaignId = $('#video_ad_campaign_id').val();
		var _adGroupId = $('#video_ad_adgroup_id').val();
		var _adStartDate = $('#video_ad_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#video_ad_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "video_ad";
		var _targetApplication = $(
				'input[name="video_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _enabled;
		if ($('#video_ad_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}
		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#video_ad_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#video_ad_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#video_ad_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="video_ad_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#video_ad_country_targetting').is(':checked')) {
				_countryTargetting
						.push($('#video_ad_country_targetting').val());
			} else {
				_countryTargetting = $(
						'input[name="video_ad_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#video_ad_age_targetting').is(':checked')) {
				_ageTargetting.push($('#video_ad_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="video_ad_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#video_ad_traffic_targetting').val());
			_genderTargetting.push($('#video_ad_gender_targetting').val());
		}

		var _date = new Date();
		var adObj = {
			id : $('#video_ad_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			targetApplication : _targetApplication,
			name : $('#video_ad_name').val(),
			description : $('#video_ad_description').val(),
			uri : $('#video_ad_uri').val(),
			learnMoreUri : $('#video_ad_learn_more_uri').val(),
			enabled : _enabled,
			cpi : $('#video_ad_cpi').val(),
			cpc : $('#video_ad_cpc').val(),
			frequencyCappingPerDay : $('#video_ad_frequency_capping').val(),
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
			url : "secured/videoad",
			type : "PUT",
			data : adObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#video_ad_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(video) {
					try {
						$('#video_ad_errors').html(getErrorMessages(video));
					} catch (err) {
						handleError("updateVideoAd", err);
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
		handleError("updateVideoAd", err);
	}
	log("updateVideoAd", "Exiting")
}

function showVideoAd() {
	log("showVideoAd", "Entering")
	try {
		openPopup("video_ad_div");
	} catch (err) {
		handleError("showVideoAd", err);
	}
	log("showVideoAd", "Exiting")
}

/** End Ad****************************************** */
