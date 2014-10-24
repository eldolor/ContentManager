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

		if (mErrors != null) {
			$('#login_errors').html(getErrorMessage("login_failure", mErrors));
			$('#cm_errors_container').show();
		}
		$('#user_sign_in_cancel_button').unbind();
		$('#user_sign_in_cancel_button').bind('click', function() {
			$('#user_sign_in_submit_button').unbind();
			window.location.href = '/';
		});
		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#loginForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			log(invalid_fields);
		}).on('valid', function() {
			login();
			// Google Analytics
			ga('send', 'event', Category.SIGN_IN, Action.SIGN_IN);
			// End Google Analytics
		});
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

		$("#cm_errors_container").addClass("fadeInUp animated");
		$("#user_forgot_password_errors_container").addClass(
				"fadeInUp animated");
		// Google Analytics
		ga('send', {
			'hitType' : 'pageview',
			'page' : '/login',
			'title' : PageTitle.SIGN_IN
		});
		// End Google Analytics
	} catch (err) {
		handleError("setup", err);
	} finally {
		log("setup", "Exiting");
	}

}

function login() {
	log("login", "Entering");
	try {

		$('#progress_bar').show();
		$('.button').addClass('disabled');
		document.loginForm.submit();

	} catch (err) {
		handleError("login", err);
	} finally {
		log("login", "Exiting");
	}
}
function loginAsync() {
	log("loginAsync", "Entering");
	try {

		var loginObj = {
			j_username : $('#j_username').val(),
			j_password : $('#j_password').val(),
			_spring_security_remember_me : $('#_spring_security_remember_me')
					.val()
		};
		var objString = JSON.stringify(loginObj, null, 2);
		// alert(contentgroupObjString);
		// create via sync call
		var jqxhr = $
				.ajax({
					url : "j_spring_security_check",
					type : "POST",
					data : objString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : false,
					statusCode : {
						200 : function() {
							window.location.href = '/applications';
						},
						400 : function(text) {
							try {
								$('#login_errors').html(getErrorMessages(text));
								$('#cm_errors_container').show();
							} catch (err) {
								handleError("login", err);
							}
						},
						503 : function() {
							$('#login_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#cm_errors_container').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#login_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#cm_errors_container').show();
					},
					complete : function(xhr, textStatus) {
						log(xhr.status);
					}
				});

		return false;
	} catch (err) {
		handleError("loginAsync", err);
	} finally {
		log("loginAsync", "Exiting");
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