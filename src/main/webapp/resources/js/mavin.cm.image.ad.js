/** Begin Ad****************************************** */
function editImageAd(id) {
	log("editImageAd", "Entering")

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
							$('#image_ad_setup_header').toggle(false);
							$('#image_ad_save_button').html('update ad');
							// reset
							$('#link_video_ad').unbind();
							$('#link_video_ad').bind(
									'click',
									function() {
										var ads = getAdsByType(ad.campaignId,
												'video_ad');
										displayVideoAdsForLinking(ads);

									});
							// reset
							$('#link_survey').unbind();
							$('#link_survey').bind(
									'click',
									function() {
										var ads = getAdsByType(ad.campaignId,
												'survey');
										displaySurveysForLinking(ads);

									});
							// reset
							$('#image_ad_save_button').unbind();
							$('#image_ad_save_button').bind('click',
									updateImageAd);
							$('#image_ad_cancel_button').one('click',
									function() {

										$('#image_ad_save_button').unbind();
										;
									});

							$('#image_ad_id').val(ad.id);
							$('#image_ad_campaign_id').val(ad.campaignId);
							$('#image_ad_adgroup_id').val(ad.adGroupId);
							$('#image_ad_start_date').val(
									getDisplayDate(ad.startDateIso8601));
							$('#image_ad_end_date').val(
									getDisplayDate(ad.endDateIso8601));

							// reset
							$('input:checkbox').removeAttr('checked');
							for (var int = 0; int < ad.targetApplication.length; int++) {
								var targetApp = ad.targetApplication[int];
								log("editTextAd", "outer: " + targetApp);
								$('input[name="image_ad_target_application[]"]')
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

							$("#view_image").hide();
							var _url = "/dropbox/" + ad.uri;
							$("#ad_image").attr("src", _url);

							// reset
							$('#view_ad_image').unbind();
							$('#view_ad_image').show();
							$('#view_ad_image').bind('click', function() {
								$("#view_image").slideToggle();
								// $('#image_ad_div_body').scrollTo('100%');
							});
							$('#ad_image').unbind();
							$('#ad_image').bind('click', function() {
								$("#view_image").slideToggle();
							});

							$("#image_ad_dropbox").hide();
							// reset
							$('#upload_ad_image').unbind();
							$('#upload_ad_image').bind(
									'click',
									function() {
										$("#image_ad_dropbox").slideToggle(
												"slow", function() {
													// $('#image_ad_div').scrollTop($('#image_ad_div')[0].scrollHeight);
												});
									});

							$('#image_ad_name').val(ad.name);
							$('#image_ad_description').val(ad.description);

							$('#image_ad_uri').val(ad.uri);
							$('#image_ad_video_ad_id').val(ad.videoAdId);
							$('#image_ad_survey_id').val(ad.surveyId);
							$('#image_ad_learn_more_uri').val(ad.learnMoreUri);

							$('#image_ad_errors').empty();

							if (ad.hasOwnProperty('bannerSize')) {
								$('#image_ad_banner_size_selection').val(
										ad.bannerSize);
							}

							var dropBoxUrl = getDropboxUrl();

							if ((ad.hasOwnProperty('bannerSize'))
									&& (ad.bannerSize != null)
									&& (ad.bannerSize != "")) {
								setupImageAdDropBox(dropBoxUrl, ad.bannerSize);
							} else {
								// default it to 300x50
								setupImageAdDropBox(dropBoxUrl, '300x50');
							}

							if (ad.hasOwnProperty('enabled')) {
								log("editImageAd", "Ad enabled: " + ad.enabled);
								if (ad.enabled == true) {
									$('#image_ad_enabled').attr('checked',
											'checked');
									$('#image_ad_status').html('Enabled');
								} else {
									$('#image_ad_enabled')
											.removeAttr('checked');
									$('#image_ad_status').html('Disabled');
								}
							}
							$('#image_ad_enabled').bind('click', function() {
								if ($('#image_ad_enabled').is(':checked')) {
									$('#image_ad_status').html('Enabled');
								} else {
									$('#image_ad_status').html('Disabled');
								}
							});
							// served externally
							if (ad.hasOwnProperty('externallyServed')) {
								log("editImageAd", "Ad served externally: "
										+ ad.externallyServed);
								if (ad.externallyServed == true) {
									$('#image_ad_externally_served').attr(
											'checked', 'checked');
									$('#image_ad_externally_served_status')
											.html('Ad Externally Served');
									$('#upload_ad_image').hide();
									$('#image_ad_dropbox').hide();
									$('#image_ad_external_uri_label').show();
									$('#image_ad_external_uri').show();
									$('#image_ad_external_uri').val(
											ad.externalUri);
								} else {
									$('#image_ad_externally_served')
											.removeAttr('checked');
									$('#image_ad_externally_served_status')
											.html('Ad Internally Served');
									$('#upload_ad_image').show();
									$('#image_ad_dropbox').show();

									$('#image_ad_external_uri_label').hide();
									$('#image_ad_external_uri').hide();
									$('#image_ad_external_uri').val('');
								}
							}
							$('#image_ad_externally_served')
									.bind(
											'click',
											function() {
												if ($(
														'#image_ad_externally_served')
														.is(':checked')) {
													$(
															'#image_ad_externally_served_status')
															.html(
																	'Ad Externally Served');
													$('#upload_ad_image')
															.hide();
													$('#image_ad_dropbox')
															.hide();
													$(
															'#image_ad_external_uri_label')
															.show();
													$('#image_ad_external_uri')
															.show();
													$('#image_ad_external_uri')
															.val(ad.externalUri);
												} else {
													$(
															'#image_ad_externally_served_status')
															.html(
																	'Ad Internally Served');
													$('#upload_ad_image')
															.show();
													$('#image_ad_dropbox')
															.show();
													$(
															'#image_ad_external_uri_label')
															.hide();
													$('#image_ad_external_uri')
															.hide();
													$('#image_ad_external_uri')
															.val('');
												}
											});
							// end

							$('#image_ad_frequency_capping').val(
									ad.frequencyCappingPerDay);
							$('#image_ad_cpi').val(ad.cpi);
							$('#image_ad_cpc').val(ad.cpc);

							// unbind click listener to reset
							$('#image_ad_banner_size_selection').unbind();
							$('#image_ad_banner_size_selection').bind(
									'change',
									function() {
										setupImageAdDropBox(dropBoxUrl, $(this)
												.val());
									});

							// unbind click listener to reset
							$('#image_ad_interest_category_targetting')
									.unbind();
							$('#image_ad_interest_category_targetting')
									.bind(
											'click',
											function() {
												$(
														"#image_ad_interest_category_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="image_ad_interest_category_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="image_ad_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayInterestCategoryTargettingSelection = false;
							for (var int = 0; (ad.interestCategoryTargetting != null)
									&& (int < ad.interestCategoryTargetting.length); int++) {
								var country = ad.interestCategoryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#image_ad_interest_category_targetting')
											.attr('checked', 'checked');
									$(
											"#image_ad_interest_category_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="image_ad_interest_category_targetting_selection[]"]')
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
								$('#image_ad_interest_category_targetting')
										.removeAttr('checked');
								$(
										"#image_ad_interest_category_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#image_ad_country_targetting').unbind();
							$('#image_ad_country_targetting')
									.bind(
											'click',
											function() {
												$(
														"#image_ad_country_targetting_selection_div")
														.slideToggle();
												$(
														'input[name="image_ad_country_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="image_ad_country_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayCountryTargettingSelection = false;
							for (var int = 0; (ad.countryTargetting != null)
									&& (int < ad.countryTargetting.length); int++) {
								var country = ad.countryTargetting[int];
								log("editTextAd", "outer: " + country);
								if (country == 'all') {
									$('#image_ad_country_targetting').attr(
											'checked', 'checked');
									$(
											"#image_ad_country_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="image_ad_country_targetting_selection[]"]')
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
								$('#image_ad_country_targetting').removeAttr(
										'checked');
								$("#image_ad_country_targetting_selection_div")
										.show();
							}

							// unbind click listener to reset
							$('#image_ad_age_targetting').unbind();
							$('#image_ad_age_targetting')
									.bind(
											'click',
											function() {
												$(
														"#image_ad_age_targetting_selection_div")
														.slideToggle(); // reset
												$(
														'input[name="image_ad_age_targetting_selection[]"]')
														.removeAttr('checked');

											});

							// reset
							$(
									'input[name="image_ad_age_targetting_selection[]"]')
									.removeAttr('checked');
							var _displayAgeTargettingSelection = false;
							for (var int = 0; (ad.ageTargetting != null)
									&& (int < ad.ageTargetting.length); int++) {
								var age = ad.ageTargetting[int];
								log("editTextAd", "outer: " + age);
								if (age == 'all') {
									$('#image_ad_age_targetting').attr(
											'checked', 'checked');
									$("#image_ad_age_targetting_selection_div")
											.hide();
									break;
								} else {
									$(
											'input[name="image_ad_age_targetting_selection[]"]')
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
								$('#image_ad_age_targetting').removeAttr(
										'checked');
								$("#image_ad_age_targetting_selection_div")
										.show();
							}

							$(
									'input[name="image_ad_traffic_targetting_selection"]')
									.each(
											function() {
												if (ad.trafficTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#image_ad_traffic_targetting').val(
									ad.trafficTargetting);
							$(
									'input[name="image_ad_gender_targetting_selection"]')
									.each(
											function() {
												if (ad.genderTargetting == $(
														this).val()) {
													$(this).attr('checked',
															'checked');
													return;
												}
											});
							$('#image_ad_gender_targetting').val(
									ad.genderTargetting);

							// set value
							if (ad.targettingOverridden == true) {
								$('#image_ad_targetting_overridden').attr('checked',
										'checked');
								$('#image_ad_targetting_div').show();
							} else {
								$('#image_ad_targetting_overridden')
										.removeAttr('checked');
								$('#image_ad_targetting_div').hide();
							}
							$('#image_ad_targetting_overridden').unbind();
							$('#image_ad_targetting_overridden').bind('click', function() {
								$("#image_ad_targetting_div").slideToggle(); // reset
							});

							// close wait div
							;
							openPopup("image_ad_div");
						}
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});
	} catch (err) {
		handleError("editImageAd", err);
		// close wait div
		;
	}
	log("editImageAd", "Exiting")
}

function toggleImageAdTrafficTargetting(target) {

	if (target == 'all') {
		$('#image_ad_traffic_targetting').val('all');
	} else if (target == 'wifi') {
		$('#image_ad_traffic_targetting').val('wifi');
	} else if (target == 'mobile_operator') {
		$('#image_ad_traffic_targetting').val('mobile_operator');
	}
}
function toggleImageAdGenderTargetting(target) {

	if (target == 'all') {
		$('#image_ad_gender_targetting').val('all');
	} else if (target == 'male') {
		$('#image_ad_gender_targetting').val('male');
	} else if (target == 'female') {
		$('#image_ad_gender_targetting').val('female');
	}
}

function validateBannerSizeSelected() {
	try {
		log("validateBannerSizeSelected", "Entering")
		var _targetApplication = $(
				'input[name="image_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _wallpaperOrChargeScreenSelected = false;
		var _mmsOrContactsSelected = false;

		for (var int = 0; int < _targetApplication.length; int++) {
			if (_targetApplication[int] == 'Wallpaper'
					|| _targetApplication[int] == 'ChargeScreen') {
				_wallpaperOrChargeScreenSelected = true;
			} else if (_targetApplication[int] == 'Mms'
					|| _targetApplication[int] == 'Dialer'
					|| _targetApplication[int] == 'Contacts') {
				_mmsOrContactsSelected = true;
			}
		}
		log("validateBannerSizeSelected", "Target Applications Selected: "
				+ _targetApplication);
		log("validateBannerSizeSelected", "Wallpaper/ChargeScreen Selected: "
				+ _wallpaperOrChargeScreenSelected);
		log("validateBannerSizeSelected", "Mms/Dialer/Contacts Selected: "
				+ _mmsOrContactsSelected);

		var _bannerSize = $('#image_ad_banner_size_selection').val();
		log("validateBannerSizeSelected", "Banner Size Selected: "
				+ _bannerSize);

		if (_wallpaperOrChargeScreenSelected && _mmsOrContactsSelected) {
			$('#image_ad_banner_size_message')
					.html(
							'Valid banner size for Wallpaper and/or Charge Screen applications is 480x800. Valid banner size for Mms/Dialer and/or Contacts applications is 300x50');
			return;
		} else if (_wallpaperOrChargeScreenSelected) {
			if (_bannerSize != '480x800')
				$('#image_ad_banner_size_message')
						.html(
								'Valid banner size for Wallpaper and/or Charge Screen applications is 480x800');
			else
				$('#image_ad_banner_size_message').empty();
			return;
		} else if (_mmsOrContactsSelected) {
			if (_bannerSize != '300x50')
				$('#image_ad_banner_size_message')
						.html(
								'Valid banner size for Mms and/or Contacts applications is 300x50');
			else
				$('#image_ad_banner_size_message').empty();
			return;
		} else {
			$('#image_ad_banner_size_message').empty();
			return;
		}

	} finally {
		log("validateBannerSizeSelected", "Exiting");
	}
}

function displayVideoAdsForLinking(ads) {
	log("displayVideoAdsForLinking", "Entering");
	if (ads != null) {
		var innerHtml = "";
		for (var int = 0; int < ads.length; int++) {
			var ad = ads[int];
			if (ad != null) {
				var _innerHtml = "<input type=\"radio\" name=\"linked_video_ad\" onclick=\"setLinkedVideoAd('"
						+ ad.id + "')\"";
				if (($('#image_ad_video_ad_id').val() != null)
						&& ($('#image_ad_video_ad_id').val() != "")
						&& (ad.uri == $('#image_ad_video_ad_id').val())) {
					_innerHtml += " checked=\"checked\" ";
				}
				_innerHtml += "/>" + ad.name + "<br />";
				innerHtml += _innerHtml;
			}
		}

		$('#video_ads_for_linking').html(innerHtml);
		$("#video_ads_for_linking").show();
	}
	log("displayVideoAdsForLinking", "Exiting");
}

function displaySurveysForLinking(ads) {
	log("displaySurveysForLinking", "Entering");
	if (ads != null) {
		var innerHtml = "";
		for (var int = 0; int < ads.length; int++) {
			var ad = ads[int];
			if (ad != null) {
				var _innerHtml = "<input type=\"radio\" name=\"linked_survey\" onclick=\"setLinkedSurvey('"
						+ ad.id + "')\"";
				if (($('#image_ad_survey_id').val() != null)
						&& ($('#image_ad_survey_id').val() != "")
						&& (ad.id == $('#image_ad_survey_id').val())) {
					_innerHtml += " checked=\"checked\" ";
				}
				_innerHtml += "/>" + ad.question + "<br />";
				innerHtml += _innerHtml;
			}
		}

		$('#surveys_for_linking').html(innerHtml);
		$("#surveys_for_linking").show();
	}
	log("displaySurveysForLinking", "Exiting");
}

function setLinkedVideoAd(id) {
	log("setLinkedVideoAd", "Entering");
	log("id", id);
	$('#image_ad_video_ad_id').val(id);
	$("#video_ads_for_linking").hide();
	// clear other fields
	$('#image_ad_learn_more_uri').val('');
	$('#image_ad_survey_id').val('');

	log("setLinkedVideoAd", "Exiting");
}
function setLinkedSurvey(id) {
	log("setLinkedSurvey", "Entering");
	log("id", id);
	$('#image_ad_survey_id').val(id);
	$("#surveys_for_linking").hide();

	// clear other fields
	$('#image_ad_learn_more_uri').val('');
	$('#image_ad_video_ad_id').val('');

	log("setLinkedSurvey", "Exiting");
}

function newImageAd(campaignId, adGroupId) {
	log("newImageAd", "Entering");
	log("campaign id: " + campaignId);
	log("ad group id: " + adGroupId);
	try {
		$('#image_ad_setup_header').toggle(true);
		$('#image_ad_save_button').html('create ad');

		// reset
		$('#link_video_ad').unbind();
		$('#link_video_ad').bind('click', function() {
			var ads = getAdsByType(campaignId, 'video_ad');
			displayVideoAdsForLinking(ads);

		});

		// reset
		$('#link_survey').unbind();
		$('#link_survey').bind('click', function() {
			var ads = getAdsByType(campaignId, 'survey');
			displaySurveysForLinking(ads);

		});

		// reset
		$('input:checkbox').removeAttr('checked');
		// reset
		//
		$('#image_ad_save_button').unbind();
		$('#image_ad_save_button').bind('click', createImageAd);
		$('#image_ad_cancel_button').one('click', function() {

			$('#image_ad_save_button').unbind();
			;
		});
		$('#image_ad_id').val('');
		$('#image_ad_campaign_id').val(campaignId);
		$('#image_ad_adgroup_id').val(adGroupId);
		$('#image_ad_start_date').val(getCurrentDisplayDate());
		$('#image_ad_end_date').val('');

		$('#image_ad_name').val('');
		$('#image_ad_description').val('');

		$('#image_ad_uri').val('');
		$('#image_ad_video_ad_id').val('');
		$('#image_ad_survey_id').val('');
		$('#image_ad_learn_more_uri').val('');

		$('#image_ad_errors').empty();
		var dropBoxUrl = getDropboxUrl();

		// unbind click listener to reset
		$('#image_ad_banner_size_selection').unbind();
		$('#image_ad_banner_size_selection').bind('change', function() {
			setupImageAdDropBox(dropBoxUrl, $(this).val());
		});

		// default it to 360x50
		$('#image_ad_banner_size_selection').val('350x50');
		setupImageAdDropBox(dropBoxUrl, '350x50');
		// set default
		$('#image_ad_enabled').attr('checked', 'checked');

		$('#image_ad_enabled').bind('click', function() {
			if ($('#image_ad_enabled').is(':checked')) {
				$('#image_ad_status').html('Enabled');
			} else {
				$('#image_ad_status').html('Disabled');
			}
		});
		// set default
		$('#image_ad_externally_served').removeAttr('checked');
		$('#image_ad_externally_served').bind(
				'click',
				function() {
					if ($('#image_ad_externally_served').is(':checked')) {
						$('#image_ad_externally_served_status').html(
								'Ad Externally Served');
						$('#upload_ad_image').hide();
						$('#image_ad_dropbox').hide();

						$('#image_ad_external_uri_label').show();
						$('#image_ad_external_uri').show();
					} else {
						$('#image_ad_externally_served_status').html(
								'Ad Internally Served');
						$('#upload_ad_image').show();
						$('#image_ad_dropbox').show();

						$('#image_ad_external_uri_label').hide();
						$('#image_ad_external_uri').hide();
						$('#image_ad_external_uri').val('');
					}
				});

		$('#image_ad_frequency_capping').val('');
		$('#image_ad_cpi').val('');
		$('#image_ad_cpc').val('');

		$('#view_ad_image').hide();
		$('#view_image').hide();
		$("#image_ad_dropbox").hide();
		// reset
		$('#upload_ad_image').unbind();
		$('#upload_ad_image').bind('click', function() {
			$("#image_ad_dropbox").slideToggle();
		});

		// unbind click listener to reset
		$('#image_ad_interest_category_targetting').unbind();
		$('#image_ad_interest_category_targetting')
				.bind(
						'click',
						function() {
							$(
									"#image_ad_interest_category_targetting_selection_div")
									.slideToggle();
							$(
									'input[name="image_ad_interest_category_targetting_selection[]"]')
									.removeAttr('checked');
						});

		// set default
		$('#image_ad_interest_category_targetting').attr('checked', 'checked');
		$("#image_ad_interest_category_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#image_ad_country_targetting').unbind();
		$('#image_ad_country_targetting').bind(
				'click',
				function() {
					$("#image_ad_country_targetting_selection_div")
							.slideToggle();
					$('input[name="image_ad_country_targetting_selection[]"]')
							.removeAttr('checked');
				});

		// set default
		$('#image_ad_country_targetting').attr('checked', 'checked');
		$("#image_ad_country_targetting_selection_div").hide();

		// unbind click listener to reset
		$('#image_ad_age_targetting').unbind();
		$('#image_ad_age_targetting').bind(
				'click',
				function() {
					$("#image_ad_age_targetting_selection_div").slideToggle(); // reset
					$('input[name="image_ad_age_targetting_selection[]"]')
							.removeAttr('checked');
				});
		// set default
		$('#image_ad_age_targetting').attr('checked', 'checked');
		$("#image_ad_age_targetting_selection_div").hide();

		// set default
		$('#image_ad_traffic_targetting').val('all');
		$('input[name="image_ad_traffic_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});
		// set default
		$('#image_ad_gender_targetting').val('all');
		$('input[name="image_ad_gender_targetting_selection"]').each(
				function() {
					if ('all' == $(this).val()) {
						$(this).attr('checked', 'checked');
						return;
					}
				});

		// set default
		$('#image_ad_targetting_overridden').removeAttr('checked');
		$('#image_ad_targetting_overridden').unbind();
		$('#image_ad_targetting_overridden').bind('click', function() {
			$("#image_ad_targetting_div").slideToggle(); // reset
		});
		$('#image_ad_targetting_div').hide();

		openPopup("image_ad_div");
	} catch (err) {
		handleError("newImageAd", err);
	}
	log("newImageAd", "Exiting")
}

function toggleImageAdType(preload) {
	log("toggleImageAdType", "Entering")
	try {
		if (preload) {
			openWait();
			var url = "secured/sampleimagead";
			// make sync call
			var jqxhr = $.ajax({
				url : url,
				type : "GET",
				contentType : "application/json",
				async : false,
				statusCode : {
					200 : function(ad) {
						$('#image_ad_id').val(ad.id);
						$('#image_ad_name').val(ad.name);
						$('#image_ad_description').val(ad.description);
						// do not modify image_ad_uri
						$('#image_ad_video_ad_id').val(ad.videoAdId);
						$('#image_ad_survey_id').val(ad.surveyId);
						$('#image_ad_learn_more_uri').val(ad.learnMoreUri);
						$('#image_ad_errors').empty();
					},
					400 : function(image) {
						try {
							$('#image_ad_errors').html(
									'invalid ad type requested');
						} catch (err) {
							handleError("toggleImageAdType", err);
						}
					}
				}
			});
			jqxhr.always(function() {
				// close wait div
				closeWait();
			});
		} else {
			$('#image_ad_id').val('');
			$('#image_ad_name').val('');
			$('#image_ad_description').val('');
			// do not modify image_ad_uri
			$('#image_ad_video_ad_id').val('');
			$('#image_ad_survey_id').val(ad.surveyId);
			$('#image_ad_learn_more_uri').val('');
			$('#image_ad_errors').empty();
		}
	} catch (err) {
		handleError("toggleImageAdType", err);
	}
	log("toggleImageAdType", "Exiting")
}

function createImageAd() {
	log("createImageAd", "Entering")
	try {
		mSubmitLock = true;
		openWait();
		var _campaignId = $('#image_ad_campaign_id').val();
		var _adGroupId = $('#image_ad_adgroup_id').val();
		var _adStartDate = $('#image_ad_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#image_ad_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "image_ad";
		var _targetApplication = $(
				'input[name="image_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _bannerSize = $('#image_ad_banner_size_selection').val();
		log("createImageAd", "Banner Size=" + _bannerSize);

		var _enabled;
		if ($('#image_ad_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}
		var _externallyServed;
		if ($('#image_ad_externally_served').is(':checked')) {
			_externallyServed = true;
		} else {
			_externallyServed = false;
		}

		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;

		_targettingOverridden = $('#image_ad_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#image_ad_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#image_ad_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="image_ad_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#image_ad_country_targetting').is(':checked')) {
				_countryTargetting
						.push($('#image_ad_country_targetting').val());
			} else {
				_countryTargetting = $(
						'input[name="image_ad_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#image_ad_age_targetting').is(':checked')) {
				_ageTargetting.push($('#image_ad_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="image_ad_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#image_ad_traffic_targetting').val());
			_genderTargetting.push($('#image_ad_gender_targetting').val());
		}
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var adObj = {
			id : $('#image_ad_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			targetApplication : _targetApplication,
			name : $('#image_ad_name').val(),
			description : $('#image_ad_description').val(),
			uri : $('#image_ad_uri').val(),
			externalUri : $('#image_ad_external_uri').val(),
			videoAdId : $('#image_ad_video_ad_id').val(),
			surveyId : $('#image_ad_survey_id').val(),
			learnMoreUri : $('#image_ad_learn_more_uri').val(),
			bannerSize : _bannerSize,
			enabled : _enabled,
			externallyServed : _externallyServed,
			cpi : $('#image_ad_cpi').val(),
			cpc : $('#image_ad_cpc').val(),
			frequencyCappingPerDay : $('#image_ad_frequency_capping').val(),
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
			url : "secured/imagead",
			type : "POST",
			data : adObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#image_ad_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(image) {
					try {
						$('#image_ad_errors').html(getErrorMessages(image));
					} catch (err) {
						handleError("createImageAd", err);
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
		handleError("createImageAd", err);
	}
	log("createImageAd", "Exiting")
}
function updateImageAd() {
	log("updateImageAd", "Entering")
	try {
		openWait();
		var _campaignId = $('#image_ad_campaign_id').val();
		var _adGroupId = $('#image_ad_adgroup_id').val();
		var _adStartDate = $('#image_ad_start_date').val();
		var _startDateIso8601 = getTransferDate(_adStartDate);
		var _adEndDate = $('#image_ad_end_date').val();
		var _endDateIso8601 = getTransferDate(_adEndDate);
		var _type = "image_ad";
		var _targetApplication = $(
				'input[name="image_ad_target_application[]"]:checked').map(
				function() {
					return $(this).val();
				}).get();
		var _bannerSize = $('#image_ad_banner_size_selection').val();
		log("createImageAd", "Banner Size=" + _bannerSize);
		var _enabled;
		if ($('#image_ad_enabled').is(':checked')) {
			_enabled = true;
		} else {
			_enabled = false;
		}
		var _externallyServed;
		if ($('#image_ad_externally_served').is(':checked')) {
			_externallyServed = true;
		} else {
			_externallyServed = false;
		}
		var _date = new Date();

		var _targettingOverridden, _interestCategoryTargetting = [], _countryTargetting = [], _ageTargetting = [], _trafficTargetting = [], _genderTargetting = [], _enabled;
		_targettingOverridden = $('#image_ad_targetting_overridden').is(
				':checked')

		if (_targettingOverridden) {
			if ($('#image_ad_interest_category_targetting').is(':checked')) {
				_interestCategoryTargetting.push($(
						'#image_ad_interest_category_targetting').val());
			} else {
				_interestCategoryTargetting = $(
						'input[name="image_ad_interest_category_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#image_ad_country_targetting').is(':checked')) {
				_countryTargetting
						.push($('#image_ad_country_targetting').val());
			} else {
				_countryTargetting = $(
						'input[name="image_ad_country_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			if ($('#image_ad_age_targetting').is(':checked')) {
				_ageTargetting.push($('#image_ad_age_targetting').val());
			} else {
				_ageTargetting = $(
						'input[name="image_ad_age_targetting_selection[]"]:checked')
						.map(function() {
							return $(this).val();
						}).get();
			}
			_trafficTargetting.push($('#image_ad_traffic_targetting').val());
			_genderTargetting.push($('#image_ad_gender_targetting').val());
		}
		var adObj = {
			id : $('#image_ad_id').val(),
			campaignId : _campaignId,
			adGroupId : _adGroupId,
			startDateIso8601 : _startDateIso8601,
			endDateIso8601 : _endDateIso8601,
			type : _type,
			targetApplication : _targetApplication,
			name : $('#image_ad_name').val(),
			description : $('#image_ad_description').val(),
			uri : $('#image_ad_uri').val(),
			externalUri : $('#image_ad_external_uri').val(),
			videoAdId : $('#image_ad_video_ad_id').val(),
			surveyId : $('#image_ad_survey_id').val(),
			learnMoreUri : $('#image_ad_learn_more_uri').val(),
			bannerSize : _bannerSize,
			enabled : _enabled,
			externallyServed : _externallyServed,
			cpi : $('#image_ad_cpi').val(),
			cpc : $('#image_ad_cpc').val(),
			frequencyCappingPerDay : $('#image_ad_frequency_capping').val(),
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
			url : "secured/imagead",
			type : "PUT",
			data : adObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					$('#image_ad_div').modal('hide');
					getAdsByAdGroup(_adGroupId);
				},
				400 : function(image) {
					try {
						$('#image_ad_errors').html(getErrorMessages(image));
					} catch (err) {
						handleError("updateImageAd", err);
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
		handleError("updateImageAd", err);
	}
	log("updateImageAd", "Exiting")
}

function showImageAd() {
	log("showImageAd", "Entering")
	try {
		openPopup("image_ad_div");
	} catch (err) {
		handleError("showImageAd", err);
	}
	log("showImageAd", "Exiting")
}

/** End Ad****************************************** */
