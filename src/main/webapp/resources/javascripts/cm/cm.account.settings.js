jQuery(function($) {
	try {
		log("function($)", "Entering");
		setup();
		// call this post setup
		// $(document).foundation('joyride', 'start');
	} catch (err) {
		handleError("function($)", err);
	} finally {
		log("function($)", "Exiting");
	}
});

function setup() {
	try {
		log("setup", "Entering");
		// setupLeftNavBar();
		setupBreadcrumbs();
		getLoggedInUser();

		$(document).foundation();

		var doc = document.documentElement;
		doc.setAttribute('data-useragent', navigator.userAgent);
		// enable abide form validation
		// $(document).foundation('abide', 'events');
		$(document).foundation(
				{
					abide : {
						live_validate : true,
						focus_on_invalid : true,
						error_labels : true, // labels with a for="inputId"
						// will
						// recieve an `error` class
						timeout : 2000,
						validators : {
							validateExpiry : function(el, required, parent) {
								return Stripe.card.validateExpiry($(
										'#card_exp_month').val(), $(
										'#card_exp_year').val());
							}
						}
					}
				});
		$(document).foundation('magellan', 'events');
		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#changePasswordForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			log(invalid_fields);
		}).on('valid', function() {
			log('changePasswordForm: valid!');
			changePassword();
			// Google Analytics
			ga('send', 'event', Category.CHANGE_PASSWORD, Action.UPDATE);
			// End Google Analytics
		});
		$('#cm_errors_container').hide();

		// forgot password
		$('#user_forgot_password_errors').hide();
		$('#user_forgot_password').unbind();
		$('#user_forgot_password').bind('click', function() {
			$('#forgot_password_modal').foundation('reveal', 'open');
		});
		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#forgotPasswordForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			log(invalid_fields);
		}).on('valid', function() {
			submitForgotPasswordRequest();
			// Google Analytics
			ga('send', 'event', Category.FORGOT_PASSWORD, Action.REQUEST);
			// End Google Analytics
		});

		// default behaviour
		// $('#user_billing').show();

		// setupAccountUsage();
		// setupAccountUsageGoogCharts();
		getStripeCustomer();
		$('#updateBillingForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			log(invalid_fields);
		}).on('valid', function() {
			log('updateBillingForm: valid!');
			if (mIsNewCard) {
				newStripeCard();
			} else {
				updateStripeCard();
				// Google Analytics
				ga('send', 'event', Category.BILLING, Action.UPDATE);
				// End Google Analytics
			}
		});
		$('#payment_info_update_errors').hide();

		// defined in Globals
		Stripe.setPublishableKey(mStripeKey);
		$("#cm_errors_container").addClass("fadeInUp animated");
		$("#user_forgot_password_errors_container").addClass(
				"fadeInUp animated");

	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}

function setupLeftNavBar() {
	log("setupLeftNavBar", "Entering");
	try {
		$('#left_nav_bar')
				.empty()
				.html(
						'<li><a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Billing</a></li>'
								+ '<li><a id=\"left_nav_bar_link_2\" href=\"javascript:void(0);\" >Change Password</a></li>');
		$('#left_nav_bar_link_1').unbind();
		$('#left_nav_bar_link_1').click(function() {
			$('#user_billing').show();
			$('#change_password').hide();
		});
		$('#left_nav_bar_link_2').unbind();
		$('#left_nav_bar_link_2').click(function() {
			$('#user_billing').hide();
			$('#change_password').show();
		});

	} catch (err) {
		handleError("setupLeftNavBar", err);
	} finally {
		log("setupLeftNavBar", "Exiting");
	}
}

function setupBreadcrumbs() {
	log("setupBreadcrumbs", "Entering");
	try {
		var lHtml = $('#breadcrumbs').html();
		$('#breadcrumbs')
				.html(
						lHtml
								+ "<a id=\"breadcrumb_applications\" href=\"/account\">Account Settings</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}

function setupAccountUsage() {
	log("setupAccountUsage", "Entering");
	try {
		var url = "/secured/quota";
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function(quota) {
							mQuota = quota;
							// displayMessage(JSON.stringify(mQuota, null, 2));
							var lAccountUsageDetailsHtml = '';
							lAccountUsageDetailsHtml += '<div class="row full-width">';

							// BANDWIDTH
							lAccountUsageDetailsHtml += '<h2 class="gray">Bandwidth</h2>';
							lAccountUsageDetailsHtml += '<h3>'
									+ mQuota.percentageBandwidthUsed + '% OR '
									+ convertBytes(mQuota.bandwidthUsed)
									+ ' of '
									+ convertBytes(mQuota.bandwidthLimit)
									+ ' used';
							lAccountUsageDetailsHtml += '</h3>';

							lAccountUsageDetailsHtml += '<div class="progress radius" >';

							lAccountUsageDetailsHtml += '<span class="meter" ';
							lAccountUsageDetailsHtml += ' style=" width: ';

							if (mQuota.percentageBandwidthUsed < 100) {
								lAccountUsageDetailsHtml += mQuota.percentageBandwidthUsed
										+ '%;';
							} else {
								lAccountUsageDetailsHtml += '100%;';
							}
							lAccountUsageDetailsHtml += ' background-color: #5cb85c;">';

							lAccountUsageDetailsHtml += '</span></div>';

							lAccountUsageDetailsHtml += '<p class="clearfix"></p>';

							// TOTAL STORAGE
							lAccountUsageDetailsHtml += '<h2 class="gray">Storage</h2>';
							lAccountUsageDetailsHtml += '<h3>'
									+ mQuota.percentageStorageUsed + '% OR '
									+ convertBytes(mQuota.storageUsed) + ' of '
									+ convertBytes(mQuota.storageLimit)
									+ ' used';
							lAccountUsageDetailsHtml += '</h3>';

							lAccountUsageDetailsHtml += '<div class="progress radius" >';

							lAccountUsageDetailsHtml += '<span class="meter" ';
							lAccountUsageDetailsHtml += ' style=" width: ';

							if (mQuota.percentageBandwidthUsed < 100) {
								lAccountUsageDetailsHtml += mQuota.percentageBandwidthUsed
										+ '%;';
							} else {
								lAccountUsageDetailsHtml += '100%;';
							}
							lAccountUsageDetailsHtml += ' background-color: #5cb85c;">';

							lAccountUsageDetailsHtml += '</span></div>';

							lAccountUsageDetailsHtml += '<p class="clearfix"></p>';

							// STORAGE Per Application
							var lTable = '<table>';
							lTable += '<thead>';
							lTable += '<tr>';
							lTable += '<th>Application</th>';
							lTable += '<th>Storage</th>';
							lTable += ' </tr>';
							lTable += '</thead>';
							lTable += '<tbody>';
							var lStorageQuota = null;
							for (var int = 0; int < mQuota.storageQuota.length; int++) {
								lStorageQuota = mQuota.storageQuota[int];
								// lAccountUsageDetailsHtml += '<h4
								// class="gray">';
								// lAccountUsageDetailsHtml +=
								// lStorageQuota.trackingId;
								// lAccountUsageDetailsHtml += ': '
								// +
								// convertBytes(lStorageQuota.storageUsedInBytes);
								// lAccountUsageDetailsHtml += '</h4>';
								lTable += '<tr>';
								lTable += '<td>' + lStorageQuota.trackingId
										+ '</td>';
								lTable += '<td>'
										+ convertBytes(lStorageQuota.storageUsedInBytes)
										+ '</td>';
								lTable += '</tr>';
							}
							lTable += ' </tbody>';
							lTable += '</table>';
							lAccountUsageDetailsHtml += lTable;
							lAccountUsageDetailsHtml += '</div>';
							$('#account_usage_details').html(
									lAccountUsageDetailsHtml);
							// Google Analytics
							ga('send', {
								'hitType' : 'pageview',
								'page' : '/account/usage',
								'title' : PageTitle.ACCOUNT_USAGE
							});
							// End Google Analytics
							log("setupAccountUsage", lAccountUsageDetailsHtml);

						},
						503 : function() {
							$('#content_errors').html(
									'Unable to get available storage quota');
							$('#content_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#content_errors').show();
					}
				});
	} catch (err) {
		handleError("setupAccountUsage", err);
	} finally {
		log("setupAccountUsage", "Exiting");
	}
}

function setupAccountUsageGoogCharts() {
	log("setupAccountUsageGoogCharts", "Entering");
	try {
		$('#progress_bar').show();
		var url = "/secured/quota";
		var jqxhr = $
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function(quota) {
							mQuota = quota;
							// displayMessage(JSON.stringify(mQuota, null, 2));
							var lAccountUsageDetailsHtml = '';

							lAccountUsageDetailsHtml += '<section id="charts" class="display-table"><div class="container va-align"><h2 class="text-center">Account Usage</h2><br> <br>';

							lAccountUsageDetailsHtml += '<div class="text-center"><div class="row full-width">';
							// BANDWIDTH CHART
							lAccountUsageDetailsHtml += '<div class="large-4 columns"><div id="one"><div id="bandwidth_chart" style="width: 600px; height: 300px;display: block; margin: 0 auto;"></div>';

							var lPercentageBandwidthUsed = (mQuota.percentageBandwidthUsed == '0.0') ? '<1%'
									: mQuota.percentageBandwidthUsed;

							lAccountUsageDetailsHtml += '<h5>'
									+ lPercentageBandwidthUsed + ' used OR ';

							lAccountUsageDetailsHtml += convertBytes(mQuota.bandwidthUsed)
									+ ' of '
									+ convertBytes(mQuota.bandwidthLimit)
									+ ' used </h5>';
							lAccountUsageDetailsHtml += '</div></div>';// end
							// large
							// 4 columns

							// STORAGE CHART
							lAccountUsageDetailsHtml += '<div class="large-4 columns"><div id="two"><div id="storage_chart" style="width: 600px; height: 300px;display: block; margin: 0 auto;" ></div>';

							var lPercentageStorageUsed = (mQuota.percentageStorageUsed == '0.0') ? '<1%'
									: mQuota.percentageStorageUsed;

							lAccountUsageDetailsHtml += '<h5>'
									+ lPercentageStorageUsed + ' used OR ';

							lAccountUsageDetailsHtml += convertBytes(mQuota.storageUsed)
									+ ' of '
									+ convertBytes(mQuota.storageLimit)
									+ ' used </h5>';
							lAccountUsageDetailsHtml += '</div></div>';// end
							// large
							// 4 columns

							// STORAGE PER APPLICATION CHART
							lAccountUsageDetailsHtml += '<div class="large-4 columns"><div id="three"><div id="storage_per_application_chart" style="width: 600px; height: 300px;display: block; margin: 0 auto;"></div> <h5>Storage Per Application</h5>';

							lAccountUsageDetailsHtml += '</div></div>';// end
							// large
							// 4 columns

							lAccountUsageDetailsHtml += '</div></div></div></section>';

							$('#account_usage_details').html(
									lAccountUsageDetailsHtml);
							// log("setupAccountUsage",
							// lAccountUsageDetailsHtml);

							// GOOG Charts
							var lBandwidthData = google.visualization
									.arrayToDataTable([
											[ 'Label', 'Value' ],
											[
													'Bandwidth',
													Math
															.ceil(mQuota.percentageBandwidthUsed) ] ]);

							var lStorageData = google.visualization
									.arrayToDataTable([
											[ 'Label', 'Value' ],

											[
													'Storage',
													Math
															.ceil(mQuota.percentageStorageUsed) ] ]);
							var options = {
								redFrom : 90,
								redTo : 100,
								yellowFrom : 75,
								yellowTo : 90,
								minorTicks : 5
							};

							var lBandwidthChart = new google.visualization.Gauge(
									document.getElementById('bandwidth_chart'));
							var lStorageChart = new google.visualization.Gauge(
									document.getElementById('storage_chart'));

							lBandwidthChart.draw(lBandwidthData, options);
							lStorageChart.draw(lStorageData, options);

							// Storage per application
							var lStoragePerApplicationData = new google.visualization.DataTable();
							lStoragePerApplicationData.addColumn({
								title : 'Storage per Application',
								type : 'string',
								id : 'Application'
							});
							lStoragePerApplicationData.addColumn({
								type : 'number',
								id : 'Storage'
							});

							for (var i = 0; i < mQuota.storageQuota.length; i++) {
								var lStorageUsedInBytes = (mQuota.storageQuota[i].storageUsedInBytes != 0) ? mQuota.storageQuota[i].storageUsedInBytes
										: 1;
								lStoragePerApplicationData.addRow([
										mQuota.storageQuota[i].trackingId,
										lStorageUsedInBytes ]);
							}

							var lStoragePerApplicationChartOptions = {
								is3D : true,
								legend : {
									position : 'right'
								},
								tooltip : {
									showColorCode : true
								}
							};

							var lStoragePerApplicationChart = new google.visualization.PieChart(
									document
											.getElementById('storage_per_application_chart'));

							lStoragePerApplicationChart.draw(
									lStoragePerApplicationData,
									lStoragePerApplicationChartOptions);
							// Google Analytics
							ga('send', {
								'hitType' : 'pageview',
								'page' : '/account/usage',
								'title' : PageTitle.ACCOUNT_USAGE
							});
							// End Google Analytics
						},
						503 : function() {
							$('#content_errors').html(
									'Unable to get available storage quota');
							$('#content_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#content_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#content_errors').show();
					},
					complete : function(xhr, textStatus) {
						$('#progress_bar').css("width", "100%");
						$('#progress_bar').hide();
						log(xhr.status);
					}
				});
	} catch (err) {
		handleError("setupAccountUsageGoogCharts", err);
	} finally {
		log("setupAccountUsageGoogCharts", "Exiting");
	}
}
function getLoggedInUser() {
	log("getLoggedInUser", "Entering");
	try {

		var jqxhr = $.ajax({
			url : "/secured/loggedinuser",
			type : "GET",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function(user) {
					mLoggedInUser = user;
				}
			}
		});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

	} catch (err) {
		handleError("getLoggedInUser", err);
		// close wait div
		;
	} finally {
		log("getLoggedInUser", "Exiting");
	}
}

function changePassword() {
	log("changePassword", "Entering");
	$('#progress_bar').show();
	$('.button').addClass('disabled');
	try {
		var userObj = {
			// get it from global variable
			userId : mLoggedInUser.id,
			oldPassword : $('#user_old_password').val(),
			password : $('#user_new_password').val(),
			password2 : $('#user_confirm_new_password').val()
		};
		var userObjString = JSON.stringify(userObj, null, 2);
		var jqxhr = $
				.ajax({
					url : "/secured/changepassword",
					type : "PUT",
					data : userObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function() {
							$('#user_message').show();
							$('#cm_errors_container').hide();
						},
						400 : function(text) {
							try {
								$('#user_message').hide();
								$('#change_password_errors').html(
										getErrorMessages(text));
								$('#cm_errors_container').show();
							} catch (err) {
								handleError("updateContentEnabled", err);
							}
						},
						503 : function() {
							$('#user_message').hide();
							$('#change_password_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#user_message').hide();
						$('#change_password_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					},
					complete : function(xhr, textStatus) {
						$('.meter').css("width", "100%");
						$('.button').removeClass('disabled');
						$('#changePasswordForm').trigger("reset");
						$('#progress_bar').hide();
						log(xhr.status);
					}

				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

		return false;
	} catch (err) {
		handleError("changePassword", err);
	} finally {
		log("changePassword", "Exiting");
	}
}
function submitForgotPasswordRequest() {
	log("submitForgotPasswordRequest", "Entering");
	try {

		var obj = {
			email : $('#user_forgot_password_email').val()
		};
		var objString = JSON.stringify(obj, null, 2);
		// alert(contentgroupObjString);
		// create via sync call
		var jqxhr = $
				.ajax({
					url : "/forgotpassword",
					type : "POST",
					data : objString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : false,
					statusCode : {
						202 : function() {
							$('#forgot_password_modal').foundation('reveal',
									'close');
							$('#forgot_password_request_submitted_message')
									.show();
						},
						503 : function() {
							$('#forgot_password_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#user_forgot_password_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#forgot_password_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#user_forgot_password_errors_container').show();
					}
				});

		return false;
	} catch (err) {
		handleError("submitForgotPasswordRequest", err);
	} finally {
		log("submitForgotPasswordRequest", "Exiting");
	}
}

function planUpdate(pPlanName, pProgressBarName, pPlanCharge) {
	// subscriptionCurrentPeriodEnd is in seconds
	var lSubscriptionChangeDate = new Date(
			mStripeCustomer.subscriptionCurrentPeriodEnd * 1000);
	displayConfirmPlanChange("Your monthly bill will change to " + pPlanCharge
			+ " on " + lSubscriptionChangeDate.toLocaleDateString()
			+ ". Plan changes are immediate.", function() {

		$("#" + pProgressBarName).show();
		$('.button').addClass('disabled');
		// create via sync call
		var jqxhr = $.ajax({
			url : "/stripe/subscribe/update/" + pPlanName,
			type : "POST",
			async : true,
			statusCode : {
				200 : function() {
					// Google Analytics
					ga('send', 'event', Category.PLANS, Action.UPDATE);
					// End Google Analytics
					location.reload('/account/plans');
				},
				409 : function(errors) {
					log(errors);
				},
				503 : function() {

				}
			},
			error : function(xhr, textStatus, errorThrown) {
				log(errorThrown);
			},
			complete : function(xhr, textStatus) {
				$('.meter').css("width", "100%");
				$('.button').removeClass('disabled');
				log(xhr.status);
			}
		});
	});
	// always
	return false;
}

function getStripeCustomer() {
	log("getStripeCustomer", "Entering");
	try {
		$('#updateBillingForm').trigger("reset");
		$('#user_billing_progress_bar_top, #user_billing_progress_bar_bottom')
				.show();
		$('#payment_info_update_button').addClass('disabled');

		var jqxhr = $
				.ajax({
					url : "/secured/stripe/customer",
					type : "GET",
					async : true,
					statusCode : {
						200 : function(pStripeCustomer) {
							// save it as a global for use elsewhere
							mStripeCustomer = pStripeCustomer;
							// log("Stripe Customer "
							// + JSON.stringify(pStripeCustomer, null, 2));
							var lCardInfoHtml = pStripeCustomer.cardBrand
									+ ' card ending in '
									+ pStripeCustomer.cardLast4;

							$('#existing_card_info').html(lCardInfoHtml);
							// set exp month
							$('[name=card_exp_month]').val(
									pStripeCustomer.cardExpMonth);
							// set exp year
							$('[name=card_exp_year]').val(
									pStripeCustomer.cardExpYear);
							$('#card_address_zip').val(
									pStripeCustomer.cardAddressZip);
							// country selector
							$('#card_address_country').unbind();
							$('#card_address_country').change(function() {
								if ($('#card_address_country').val() == 'USA') {
									$('#card_address_state_container').show();
								} else {
									$('#card_address_state_container').hide();
								}
							});
							// new card configuration
							$('#new_card').unbind();
							$('#new_card').click(function() {
								$('#new_card_container').show();
								$('#new_card_cancel').show();
								$('#new_card').hide();
								$('#existing_card_info').hide();

								$('#card_number').prop('required', true);
								$('#card_cvc').prop('required', true);
								mIsNewCard = true;
							});
							$('#new_card_cancel').unbind();
							$('#new_card_cancel').click(function() {
								$('#new_card_container').hide();

								$('#new_card_cancel').hide();
								$('#new_card').show();
								$('#existing_card_info').show();
								$('#card_number').prop('required', false);
								$('#card_cvc').prop('required', false);
								mIsNewCard = false;
							});

						},
						204 : function() {
							$('#user_billing_container').hide();
							$('#billing_not_enabled_message').show();
						},
						400 : function(errors) {
							log(errors);
							$('#payment_info_update_errors').html(
									getErrorMessages(text));
							$('#payment_info_update_errors').show();
						},
						503 : function() {
							$('#payment_info_update_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#payment_info_update_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#payment_info_update_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#payment_info_update_errors').show();
					},
					complete : function(xhr, textStatus) {
						$(
								'#user_billing_progress_bar_top, #user_billing_progress_bar_bottom')
								.css("width", "100%");
						$(
								'#user_billing_progress_bar_top, #user_billing_progress_bar_bottom')
								.hide();
						$('#payment_info_update_button')
								.removeClass('disabled');
						log(xhr.status);
					}
				});

	} catch (err) {
		handleError("getStripeCustomer", err);
	} finally {
		log("getStripeCustomer", "Exiting");
	}
}

function updateStripeCard() {
	log("updateStripeCard", "Entering");
	try {

		var applicationObj = {
			name : $('#card_name').val(),
			expMonth : $('#card_exp_month').val(),
			expYear : $('#card_exp_year').val(),
			addressLine1 : $('#card_address_line_1').val(),
			addressLine2 : $('#card_address_line_2').val(),
			addressCity : $('#card_address_city').val(),
			addressState : $('#card_address_state').val(),
			addressCountry : $('#card_address_country').val(),
			addressZip : $('#card_address_zip').val()
		};
		processCreditCard("PUT", applicationObj);

	} catch (err) {
		handleError("updateStripeCard", err);
		// close wait div
		;
	} finally {
		log("updateStripeCard", "Exiting");
	}
}

function processCreditCard(pHttpMethod, pObject) {
	log("processCreditCard", "Entering");
	try {

		$('#user_billing_progress_bar_top, #user_billing_progress_bar_bottom')
				.show();
		$('#payment_info_update_button').addClass('disabled');

		var applicationObjString = JSON.stringify(pObject, null, 2);

		var jqxhr = $
				.ajax({
					url : "/secured/stripe/customer/card",
					type : pHttpMethod,
					data : applicationObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function() {
							$('#payment_info_updated_message').show();
							$('#updateBillingForm').trigger("reset");
						},
						409 : function(errors) {
							log(errors);
							$('#payment_info_update_errors').html(
									getErrorMessages(errors));
							$('#payment_info_update_errors').show();
						},
						503 : function() {
							$('#payment_info_update_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#payment_info_update_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(textStatus + "::" + errorThrown);
						$('#payment_info_update_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#payment_info_update_errors').show();
					},
					complete : function(xhr, textStatus) {
						$(
								'#user_billing_progress_bar_top, #user_billing_progress_bar_bottom')
								.css("width", "100%");
						$(
								'#user_billing_progress_bar_top, #user_billing_progress_bar_bottom')
								.hide();
						$('#payment_info_update_button')
								.removeClass('disabled');
						log(xhr.status);
					}
				});

	} catch (err) {
		handleError("processCreditCard", err);
		// close wait div
		;
	} finally {
		log("processCreditCard", "Exiting");
	}
}

function newStripeCard() {
	log("newStripeCard", "Entering");
	try {
		Stripe.card.createToken({
			number : $('#card_number').val(),
			cvc : $('#card_cvc').val(),
			name : $('#card_name').val(),
			exp_month : $('#card_exp_month').val(),
			exp_year : $('#card_exp_year').val(),
			address_line1 : $('#card_address_line_1').val(),
			address_line2 : $('#card_address_line_2').val(),
			address_city : $('#card_address_city').val(),
			address_state : $('#card_address_state').val(),
			address_country : $('#card_address_country').val(),
			address_zip : $('#card_address_zip').val()
		}, processStripeResponse);

	} catch (err) {
		handleError("newStripeCard", err);
		// close wait div
		;
	} finally {
		log("newStripeCard", "Exiting");
	}
}

function processStripeResponse(status, response) {
	log("processStripeResponse", "Entering");
	try {

		$('#user_billing_progress_bar_top, #user_billing_progress_bar_bottom')
				.show();
		$('#payment_info_update_button').addClass('disabled');

		var applicationObj = {
			stripeToken : response.id,
			last4 : response.last4,
			brand : response.brand,
			funding : response.funding,
			expMonth : response.exp_month,
			expYear : response.exp_year,
			addressZip : response.address_zip
		};

		var applicationObjString = JSON.stringify(applicationObj, null, 2);

		var jqxhr = $
				.ajax({
					url : "/secured/stripe/customer/card",
					type : "POST",
					data : applicationObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function() {
							$('#payment_info_updated_message').show();
						},
						409 : function(errors) {
							log(errors);
							$('#payment_info_update_errors').html(
									getErrorMessages(errors));
							$('#payment_info_update_errors').show();
						},
						503 : function() {
							$('#payment_info_update_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#payment_info_update_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(textStatus + "::" + errorThrown);
						$('#payment_info_update_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#payment_info_update_errors').show();
					},
					complete : function(xhr, textStatus) {
						$(
								'#user_billing_progress_bar_top, #user_billing_progress_bar_bottom')
								.css("width", "100%");
						$(
								'#user_billing_progress_bar_top, #user_billing_progress_bar_bottom')
								.hide();
						$('#payment_info_update_button')
								.removeClass('disabled');
						log(xhr.status);
					}
				});

	} catch (err) {
		handleError("processStripeResponse", err);
		// close wait div
		;
	} finally {
		log("processStripeResponse", "Exiting");
	}
}

/** *End***************************************** */
