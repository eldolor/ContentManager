jQuery(function($) {
	log("function($)", "Entering");
	try {
		setup();
	} catch (err) {
		handleError("function($)", err);
	} finally {
		log("function($)", "Exiting");
	}

});

function setup() {
	log("setup", "Entering");
	try {
		$(document).foundation();

		var doc = document.documentElement;
		doc.setAttribute('data-useragent', navigator.userAgent);
		// enable abide form validation
		$(document).foundation('abide', 'events');
		$('#cm_errors_container').hide();
		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#signupForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			log(invalid_fields);
		}).on('valid', function() {
			signup();
			// Google Analytics
			ga('send', 'event', Category.SIGN_UP, Action.SIGN_UP);
			// End Google Analytics
		});

		$('#user_sign_up_cancel_button').unbind();
		$('#user_sign_up_cancel_button').bind('click', function() {
			// Google Analytics
			ga('send', 'event', Category.SIGN_UP, Action.CANCEL);
			// End Google Analytics
			$('#user_sign_up_submit_button').unbind();
			window.location.href = '/';
		});

		$("#cm_errors_container").addClass("fadeInUp animated");
		// Google Analytics
		ga('send', {
			'hitType' : 'pageview',
			'page' : '/signup',
			'title' : PageTitle.SIGN_UP
		});
		// End Google Analytics
	} catch (err) {
		handleError("setup", err);
	} finally {
		log("setup", "Exiting");
	}

}

function signup() {
	log("signup", "Entering");
	try {
		// // validate the email
		// if (!validateEmail($('#userName').val())) {
		// $('#signup_errors').html("<p>Please enter a valid email
		// address</p>");
		// $('#signup_errors').show();
		// return;
		// }
		$('#progress_bar_sign_up').show();
		$('.button').addClass('disabled');

		var lDate = new Date();
		var lTimeCreated = lDate.getTime();

		var signupObj = {
			userName : $('#userName').val(),
			password : $('#password').val(),
			promoCode : $('#promoCode').val(),
			timeCreatedMs : lTimeCreated,
			timeCreatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : lTimeCreated,
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)
		};
		var objString = JSON.stringify(signupObj, null, 2);

		// create via sync call
		var jqxhr = $
				.ajax({
					url : "/signup",
					type : "POST",
					data : objString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						201 : function() {
							$('.meter').css("width", "100%");
							$('#progress_bar_sign_up').hide();
							$('#progress_bar_sign_in').show();
							postSignupAutoLogin($('#userName').val(), $(
									'#password').val());
						},
						400 : function(text) {
							try {
								// TODO: how do display error during post signup
								// auto
								// login
								$('#signup_errors')
										.html(getErrorMessages(text));
								$('#cm_errors_container').show();
							} catch (err) {
								handleError("signup", err);
							}
						},
						503 : function() {
							$('#signup_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						// Anshu: commented out for now as it throws 'unexpected
						// end of input error on signup'
						// $('#signup_errors')
						// .html(
						// 'Unable to process the request. Please try again
						// later');
						// $('#cm_errors_container').show();

					},
					complete : function(xhr, textStatus) {
						$('.meter').css("width", "100%");
						$('#progress_bar_sign_up').hide();
						$('.button').removeClass('disabled');
						log(xhr.status);
					}
				});
		jqxhr.always(function() {
			// close wait div
			closeWait();
		});

		return false;
	} catch (err) {
		handleError("signup", err);
	} finally {
		log("signup", "Exiting");
	}
}

function postSignupAutoLogin(pUserName, pPassword) {
	log("postSignupAutoLogin", "Entering");
	try {
		// setup values on the hidden form
		$('#j_username').val(pUserName);
		$('#j_password').val(pPassword);

		document.loginForm.submit();
	} catch (err) {
		handleError("postSignupAutoLogin", err);
	} finally {
		log("postSignupAutoLogin", "Exiting");
	}
}

// function createAccount() {
// log("createAccount", "Entering");
// try {
// var _date = new Date();
// var _timeCreated = _date.getTime();
// var obj = {
// userName : $('#username').val(),
// password : $('#password').val(),
// password2 : $('#password2').val(),
// timeCreatedMs : _timeCreated,
// timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
// timeUpdatedMs : _timeCreated,
// timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
// };
// var objString = JSON.stringify(obj, null, 2);
// var jqxhr = $.ajax({
// url : "/admin/account",
// type : "POST",
// data : objString,
// processData : false,
// dataType : "json",
// contentType : "application/json",
// async : false,
// statusCode : {
// 201 : function() {
// ;
// window.location.href = "/secured";
// },
// 400 : function(text) {
// try {
// $('#create_account_errors')
// .html('<p>'+ getErrorMessages(text)+'</p>');
// $('#create_account_errors').show();
// } catch (err) {
// handleError("createAccount", err);
// }
// }
// }
// });
//
// jqxhr.always(function(msg) {
// clearWait('create_account_wait');
// });
//
// return false;
// } catch (err) {
// handleError("createAccount", err);
// } finally {
// log("createAccount", "Exiting");
// }
// }
