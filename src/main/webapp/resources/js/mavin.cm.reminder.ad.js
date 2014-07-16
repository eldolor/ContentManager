/** Begin Ad****************************************** */
function editReminderAd(id) {
	log("editReminderAd", "Entering")

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
							$('#reminder_ad_setup_header').toggle(false);
							$('#reminder_ad_save_button').html('update ad');

							// reset
							$('#reminder_ad_save_button').unbind();
							$('#reminder_ad_save_button').bind('click',
									updateReminderAd);
							$('#reminder_ad_cancel_button').one('click',
									function() {

										$('#reminder_ad_save_button').unbind();
										;
									});

							$('#reminder_ad_id').val(ad.id);
							$('#reminder_ad_campaign_id').val(ad.campaignId);
							$('#reminder_ad_adgroup_id').val(ad.adGroupId);
							$('#reminder_ad_start_date').val(
									getDisplayDate(ad.startDateIso8601));
							$('#reminder_ad_end_date').val(
									getDisplayDate(ad.endDateIso8601));

							$('#reminder_ad_event_title').val(ad.eventTitle);
							$('#reminder_ad_event_location').val(
									ad.eventLocation);
							$('#reminder_ad_event_description').val(
									ad.eventDescription);
							$('#reminder_ad_event_begin_time')
									.val(
											getDisplayDateAndTime(ad.eventBeginTimeIso8601));
							$('#reminder_ad_event_end_time')
									.val(
											getDisplayDateAndTime(ad.eventEndTimeIso8601));
							// $('#reminder_ad_event_time_zone').val(ad.eventTimeZone);

							// reset
							$('input:checkbox').removeAttr('checked');
							for (var int = 0; int < ad.targetApplication.length; int++) {
								var targetApp = ad.targetApplication[int];
								log("editTextAd", "outer: " + targetApp);
								$(
										'input[name="reminder_ad_target_application[]"]')
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

							$('#reminder_ad_name').val(ad.name);
							$('#reminder_ad_description').val(ad.description);

							$('#reminder_ad_uri').val(ad.uri);

							$("#view_reminder_image").hide();
							var _url = "/dropbox/"
									+ $('#reminder_ad_uri').val();
							$("#reminder_ad_image").attr("src", _url);

							// reset
							$('#view_reminder_ad_image').unbind();
							$('#view_reminder_ad_image').show();
							$('#view_reminder_ad_image')
									.bind(
											'click',
											function() {
												$("#view_reminder_image")
														.slideToggle();
											});
							$('#reminder_ad_image').unbind();
							$('#reminder_ad_image').bind('click', function() {
								$("#view_reminder_image").slideToggle();
							});

							$('#reminder_ad_dropbox').hide();
							// reset
							$('#upload_reminder_ad_image').unbind();
							$('#upload_reminder_ad_image')
									.bind(
											'click',
											function() {
												$("#reminder_ad_dropbox")
														.slideToggle();
											});

							$('#reminder_ad_errors').empty();

							if (ad.hasOwnProperty('bannerSize')) {
								// reset
								$('#reminder_ad_banner_size_selection').val(
										ad.bannerSize);
							}

							if (ad.hasOwnProperty('enabled')) {
								log("editImageAd", "Ad enabled: " + ad.enabled);
								if (ad.enabled == true) {
									$('#reminder_ad_enabled').attr('checked',
											'checked');
									$('#reminder_ad_status').html('Enabled');
								} else {
									$('#reminder_ad_enabled').removeAttr(
											'checked');
									$('#reminder_ad_status').html('Disabled');
								}
							}
							$('#reminder_ad_enabled').bind('click', function() {
								if ($('#reminder_ad_enabled').is(':checked')) {
									$('#reminder_ad_status').html('Enabled');
								} else {
									$('#reminder_ad_status').html('Disabled');
								}
							});
							$('#reminder_ad_frequency_capping').val(
									ad.frequencyCappingPerDay);
							$('#reminder_ad_cpi').val(ad.cpi);
							$('#reminder_ad_cpc').val(ad.cpc);

							var dropBoxUrl = getDropboxUrl();

							if ((ad.hasOwnProperty('bannerSize'))
									&& (ad.bannerSize != null)
									&& (ad.bannerSize != "")) {
								setupReminderAdDropBox(dropBoxUrl,
										ad.bannerSize);
							} else {
								// default it to 300x50
								setupReminderAdDropBox(dropBoxUrl, '300x50');
							}

							// unbind click listener to reset
							$('#reminder_ad_banner_size_selection').unbind();
							$('#reminder_ad_banner_size_selection').bind(
									'change',
									function() {
										setupReminderAdDropBox(dropBoxUrl, $(
												this).val());
									});

							// unbind click listener to reset
							$('#reminder_ad_interest_category_targetting')
									.unbind();
							$('#reminder_ad_interest_category_targetting')
									.bind(
											'click',
											function() {
												$(
														"#reminder_ad_interest_category_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="reminder_ad_interest_category_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="reminder_ad_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayInterestCategoryTargettingSelection = false;
							for (var int = 0; (ad.interestCategoryTargetting != null)
									&& (int < ad.interestCategoryTargetting.length); int++) {
								var country = ad.interestCategoryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$(
											'#reminder_ad_interest_category_targetting')
											.attr('checked', 'checked');
									$(
											"#reminder_ad_interest_category_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="reminder_ad_interest_category_targetting_selection[]"]')
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
								$('#reminder_ad_interest_category_targetting')
										.removeAttr('checked');
								$(
										"#reminder_ad_interest_category_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#reminder_ad_country_targetting').unbind();
							$('#reminder_ad_country_targetting')
									.bind(
											'click',
											function() {
												$(
														"#reminder_ad_country_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="reminder_ad_country_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="reminder_ad_country_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayCountryTargettingSelection = false;
							for (var int = 0; (ad.countryTargetting != null)
									&& (int < ad.countryTargetting.length); int++) {
								var country = ad.countryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#reminder_ad_country_targetting').attr(
											'checked', 'checked');
									$(
											"#reminder_ad_country_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="reminder_ad_country_targetting_selection[]"]')
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
								$('#reminder_ad_country_targetting')
										.removeAttr('checked');
								$(
										"#reminder_ad_country_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#reminder_ad_age_targetting').unbind();
							$('#reminder_ad_age_targetting')
									.bind(
											'click',
											function() {
												$(
														"#reminder_ad_age_targetting_selection_div")
														.slideToggle(); // reset
												$(
														'input[name="reminder_ad_age_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="reminder_ad_age_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayAgeTargettingSelection = false;
							for (var int = 0; (ad.ageTargetting != null)
									&& (int < ad.ageTargetting.length); int++) {
								var age = ad.ageTargetting[int];
								log("editTextAd", "outer: " + age);
								if (age == 'all') {
									$('#reminder_ad_age_targetting').attr(
											'checked', 'checked');
									$(
											"#reminder_ad_age_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="reminder_ad_age_targetting_selection[]"]')
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
								$('#reminder_ad_age_targetting').removeAttr(
										'checked');
								$("#reminder_ad_age_targetting_selection_div")
										.show();
							}

							$(
									'input[name="reminder_ad_traffic_targetting_selection"]')
									.each(
											function() {
												if (ad.trafficTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#reminder_ad_traffic_targetting').val(
									ad.trafficTargetting);
							$(
									'input[name="reminder_ad_gender_targetting_selection"]')
									.each(
											function() {
												if (ad.genderTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#reminder_ad_gender_targetting').val(
									ad.genderTargetting);

							// set value
							if (ad.targettingOverridden == true) {
								$('#reminder_ad_targetting_overridden').attr(
										'checked', 'checked');
								$('#reminder_ad_targetting_div').show();
							} else {
								$('#reminder_ad_targetting_overridden')
										.removeAttr('checked');
								$('#reminder_ad_targetting_div').hide();
							}
							$('#reminder_ad_targetting_overridden').unbind();
							$('#reminder_ad_targetting_overridden').bind(
									'click',
									function() {
										$("#reminder_ad_targetting_div")
												.slideToggle(); // reset
									});

							// close wait div
							;
							openPopup("reminder_ad_div");
						}
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editReminderAd", err);
		// close wait div
		;
	}
	log("editReminderAd", "Exiting")
}

function toggleReminderAdTrafficTargetting(target) {

	if (target == 'all') {
		$('#reminder_ad_traffic_targetting').val('all');
	} else if (target == 'wifi') {
		$('#reminder_ad_traffic_targetting').val('wifi');
	} else if (target == 'mobile_operator') {
		$('#reminder_ad_traffic_targetting').val('mobile_operator');
	}
}
function toggleReminderAdGenderTargetting(target) {

	if (target == 'all') {
		$('#reminder_ad_gender_targetting').val('all');
	} else if (target == 'male') {
		$('#reminder_ad_gender_targetting').val('male');
	} else if (target == 'female') {
		$('#reminder_ad_gender_targetting').val('female');
	}
}

function newReminderAd(campaignId, adGroupId) {
	log("newReminderAd", "Entering");
	log("campaign id: " + campaignId);
	log("ad group id: " + adGroupId);
	try {
		$('#reminder_ad_setup_header').toggle(true);
		$('#reminder_ad_save_button').html('create ad');

		// reset
		$('input:checkbox').removeAttr('checked');
		// reset
		//
		$('#reminder_ad_save_button').unbind();
		$('#reminder_ad_save_button').bind('click', createReminderAd);
		$('#reminder_ad_cancel_button').one('click', function() {

			$('#reminder_ad_save_button').unbind();
			;
		});
		$('#reminder_ad_id').val('');
		$('#reminder_ad_campaign_id').val(campaignId);
		$('#reminder_ad_adgroup_id').val(adGroupId);
		$('#reminder_ad_start_date').val(getCurrentDisplayDate());
		$('#reminder_ad_end_date').val('');

		$('#reminder_ad_name').val('');
		$('#reminder_ad_description').val('');

		$('#reminder_ad_uri').val('');
		$('#reminder_ad_event_title').val('');
		$('#reminder_ad_event_location').val('');
		$('#reminder_ad_event_description').val('');
		$('#reminder_ad_event_begin_time').val('');
		$('#reminder_ad_event_end_time').val('');
		// $('#reminder_ad_event_time_zone').val('');

		$('#reminder_ad_errors').empty();

		// var dropBoxUrl = getDropboxUrl();
		// setupReminderAdDropBox(dropBoxUrl);
		var dropBoxUrl = getDropboxUrl();

		// unbind click listener to reset
		$('#reminder_ad_banner_size_selection').unbind();
		$('#reminder_ad_banner_size_selection').bind('change', function() {
			setupReminderAdDropBox(dropBoxUrl, $(this).val());
		});
		// default it to 360x50
		$('#reminder_ad_banner_size_selection').val('360x50');
		setupReminderAdDropBox(dropBoxUrl, '360x50');

		// set default
		$('#reminder_ad_enabled').attr('checked', 'checked');
		$('#reminder_ad_enabled').bind('click', function() {
			if ($('#reminder_ad_enabled').is(':checked')) {
				$('#reminder_ad_status').html('Enabled');
			} else {
				$('#reminder_ad_status').html('Disabled');
			}
		});
		$('#reminder_ad_frequency_capping').val('');
		$('#reminder_ad_cpi').val('');
		$('#reminder_ad_cpc').val('');

		// reset
		$('#view_reminder_ad_image').hide();
		$('#reminder_ad_image').hide();
		$("#reminder_ad_dropbox").hide();
		// reset
		$('#upload_reminder_ad_image').unbind();
		$('#upload_reminder_ad_image').bind('click', function() {
			$("#reminder_ad_dropbox").slideToggle();
		});
		// unbind click listener to reset
		$('#reminder_ad_interest_category_targetting').unbind();
		$('#reminder_ad_interest_category_targetting')
				.bind(
						'click',
						function() {
							$(
									"#reminder_ad_interest_category_targetting_selection_div")
									.slideToggle();
							$(
									'input[name="reminder_ad_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
						});

		// set default
		$('#reminder_ad_interest_category_targetting').attr('checked',
				'checked');
		$("#reminder_ad_interest_category_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#reminder_ad_country_targetting').unbind();
		$('#reminder_ad_country_targetting')
				.bind(
						'click',
						function() {
							$("#reminder_ad_country_targetting_selection_div")
									.slideToggle();
							$(
									'input[name="reminder_ad_country_targetting_selection[]"]')
									.removeAttr('checked');
						});

		// set default
		$('#reminder_ad_country_targetting').attr('checked', 'checked');
		$("#reminder_ad_country_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#reminder_ad_age_targetting').unbind();
		$('#reminder_ad_age_targetting').bind(
				'click',
				function() {
					$("#reminder_ad_age_targetting_selection_div")
							.slideToggle(); // reset
					$('input[name="reminder_ad_age_targetting_selection[]"]')
							.removeAttr('checked');
				});
		// set default
		$('#reminder_ad_age_targetting').attr('checked', 'checked');
		$("#reminder_ad_age_targetting_selection_div").hide();

		// set default
		$('#reminder_ad_traffic_targetting').val('all');
		$('input[name="reminder_ad_traffic_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});
		// set default
		$('#reminder_ad_gender_targetting').val('all');
		$('input[name="reminder_ad_gender_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});

		// set default
		$('#reminder_ad_targetting_overridden').removeAttr('checked');
		$('#reminder_ad_targetting_overridden').unbind();
		$('#reminder_ad_targetting_overridden').bind('click', function() {
			$("#reminder_ad_targetting_div").slideToggle(); // reset
		});
		$('#reminder_ad_targetting_div').hide();

		openPopup("reminder_ad_div");
	} catch (err) {
		handleError("newReminderAd", err);
	}
	log("newReminderAd", "Exiting")
}

function toggleReminderAdType(preload) {
	log("toggleReminderAdType", "Entering")
	try {
		if (preload) {
			openWait();
			var url = "secured/samplereminderad";
			// make sync call
			var jqxhr = $.ajax({
				url : url,
				type : "GET",
				contentType : "application/json",
				async : false,
				statusCode : {
					200 : function(ad) {
						$('#reminder_ad_id').val(ad.id);
						$('#reminder_ad_name').val(ad.name);
						$('#reminder_ad_description').val(ad.description);
						// do not modify reminder_ad_uri
						$('#reminder_ad_event_title').val(ad.eventTitle);
						$('#reminder_ad_event_description').val(
								ad.eventDescription);
						$('#reminder_ad_event_location').val(ad.eventLocation);
						$('#reminder_ad_event_rrule').val(ad.eventRrule);

						$('#reminder_ad_errors').empty();
					},
					400 : function(reminder) {
						try {
							$('#reminder_ad_errors').html(
									'invalid ad type requested');
						} catch (err) {
							handleError("toggleReminderAdType", err);
						}
					}
				}
			});
			jqxhr.always(function() {
				// close wait div
				closeWait();
			});
		} else {
			$('#reminder_ad_id').val('');
			$('#reminder_ad_name').val('');
			$('#reminder_ad_description').val('');
			// do not modify reminder_ad_uri
			$('#reminder_ad_event_title').val('');
			$('#reminder_ad_event_description').val('');
			$('#reminder_ad_event_location').val('');
			$('#reminder_ad_event_rrule').val('');
			$('#reminder_ad_errors').empty();
		}
	} catch (err) {
		handleError("toggleReminderAdType", err);
	}
	log("toggleReminderAdType", "Exiting")
}

function createReminderAd() {
	log("createReminderAd", "Entering")
	try {
		mSubmitLock = true;
		openWait();
		var _campaignId = $('#reminder_ad_campaign_id').val();
		var _adGroupId = $('#reminder_ad_adgroup_id').val();
		var _adStartDate = $('#reminder_ad_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#reminder_ad_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "reminder_ad";
		var _targetApplication = $(
				'input[name="reminder_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _bannerSize = $('#reminder_ad_banner_size_selection').val();
		log("createReminderAd", "Banner Size=" + _bannerSize);
		var _enabled;
		if ($('#reminder_ad_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#reminder_ad_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#reminder_ad_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#reminder_ad_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="reminder_ad_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#reminder_ad_country_targetting').is(':checked')) {
				_countryTargetting.push($('#reminder_ad_country_targetting')
						.val());
			} else {
				_countryTargetting = $(
						'input[name="reminder_ad_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#reminder_ad_age_targetting').is(':checked')) {
				_ageTargetting.push($('#reminder_ad_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="reminder_ad_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#reminder_ad_traffic_targetting').val());
			_genderTargetting.push($('#reminder_ad_gender_targetting').val());
		}
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var adObj = {
			id : $('#reminder_ad_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			targetApplication : _targetApplication,
			name : $('#reminder_ad_name').val(),
			description : $('#reminder_ad_description').val(),
			uri : $('#reminder_ad_uri').val(),
			eventTitle : $('#reminder_ad_event_title').val(),
			eventLocation : $('#reminder_ad_event_location').val(),
			eventDescription : $('#reminder_ad_event_description').val(),
			eventRrule : $('#reminder_ad_event_rrule').val(),
			eventBeginTimeIso8601 : getTransferDateAndTime($(
					'#reminder_ad_event_begin_time').val()),
			eventEndTimeIso8601 : getTransferDateAndTime($(
					'#reminder_ad_event_end_time').val()),
			bannerSize : _bannerSize,
			enabled : _enabled,
			cpi : $('#reminder_ad_cpi').val(),
			cpc : $('#reminder_ad_cpc').val(),
			frequencyCappingPerDay : $('#reminder_ad_frequency_capping').val(),
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

		// eventTimeZone : getTimeZone($('#reminder_ad_event_end_time').val())
		};
		var adObjString = JSON.stringify(adObj, null, 2);
		// alert(adObjString);
		// create via sync call
		var jqxhr = $.ajax({
			url : "secured/reminderad",
			type : "POST",
			data : adObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#reminder_ad_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(reminder) {
					try {
						$('#reminder_ad_errors').html(
								getErrorMessages(reminder));
					} catch (err) {
						handleError("createReminderAd", err);
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
		handleError("createReminderAd", err);
	}
	log("createReminderAd", "Exiting")
}
function updateReminderAd() {
	log("updateReminderAd", "Entering")
	try {
		openWait();
		var _campaignId = $('#reminder_ad_campaign_id').val();
		var _adGroupId = $('#reminder_ad_adgroup_id').val();
		var _adStartDate = $('#reminder_ad_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#reminder_ad_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "reminder_ad";
		var _targetApplication = $(
				'input[name="reminder_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _bannerSize = $('#reminder_ad_banner_size_selection').val();
		log("createReminderAd", "Banner Size=" + _bannerSize);
		var _enabled;
		if ($('#reminder_ad_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}

		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#reminder_ad_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#reminder_ad_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#reminder_ad_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="reminder_ad_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#reminder_ad_country_targetting').is(':checked')) {
				_countryTargetting.push($('#reminder_ad_country_targetting')
						.val());
			} else {
				_countryTargetting = $(
						'input[name="reminder_ad_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#reminder_ad_age_targetting').is(':checked')) {
				_ageTargetting.push($('#reminder_ad_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="reminder_ad_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#reminder_ad_traffic_targetting').val());
			_genderTargetting.push($('#reminder_ad_gender_targetting').val());
		}
		var _date = new Date();
		var adObj = {
			id : $('#reminder_ad_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			targetApplication : _targetApplication,
			name : $('#reminder_ad_name').val(),
			description : $('#reminder_ad_description').val(),
			uri : $('#reminder_ad_uri').val(),
			eventTitle : $('#reminder_ad_event_title').val(),
			eventLocation : $('#reminder_ad_event_location').val(),
			eventDescription : $('#reminder_ad_event_description').val(),
			eventRrule : $('#reminder_ad_event_rrule').val(),
			eventBeginTimeIso8601 : getTransferDateAndTime($(
					'#reminder_ad_event_begin_time').val()),
			eventEndTimeIso8601 : getTransferDateAndTime($(
					'#reminder_ad_event_end_time').val()),
			bannerSize : _bannerSize,
			enabled : _enabled,
			cpi : $('#reminder_ad_cpi').val(),
			cpc : $('#reminder_ad_cpc').val(),
			frequencyCappingPerDay : $('#reminder_ad_frequency_capping').val(),
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
			url : "secured/reminderad",
			type : "PUT",
			data : adObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#reminder_ad_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(reminder) {
					try {
						$('#reminder_ad_errors').html(
								getErrorMessages(reminder));
					} catch (err) {
						handleError("updateReminderAd", err);
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
		handleError("updateReminderAd", err);
	}
	log("updateReminderAd", "Exiting")
}

function showReminderAd() {
	log("showReminderAd", "Entering")
	try {
		openPopup("reminder_ad_div");
	} catch (err) {
		handleError("showReminderAd", err);
	}
	log("showReminderAd", "Exiting")
}

/** End Ad****************************************** */
