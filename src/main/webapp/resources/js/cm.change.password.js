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
		$(document).foundation();

		var doc = document.documentElement;
		doc.setAttribute('data-useragent', navigator.userAgent);
		// enable abide form validation
		$(document).foundation('abide', 'events');
		// not using valid.fndtn.abide & invalid.fndtn.abide as it
		// causes the form to be submitted twice. Instead use the
		// deprecated valid & invalid
		$('#changePasswordForm').on('invalid', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			log(invalid_fields);
		}).on('valid', function() {
			log('changePasswordForm: valid!');
			changePassword();
		});
		$('#user_errors').hide();

		if (lIsRequestExpired == true) {
			$('#user_change_password').hide();

			var lMessage = "<div data-alert id=\"alert_message\" class=\"alert-box alert radius\">";
			lMessage += "The password change request has expired. Please click <a href=\"/login\">here</a> to submit the request again.";
			lMessage += "<a href=\"#\" class=\"close\">&times;</a></div>";
			$('#message_box').html(lMessagesHtml);
			$('#message_box').show();
		} else {
			$('#user_change_password').show();

		}

	} catch (err) {
		handleError("setup", err);
	} finally {
		log("function($)", "Exiting");
	}
}

function changePassword() {
	log("changePassword", "Entering");
	$('#progress_bar').show();
	$('.button').addClass('disabled');

	try {
		var userObj = {
			// get it from global variable
			guid : $('#user_forgot_password_request_guid').val(),
			password : $('#user_new_password').val(),
			password2 : $('#user_confirm_new_password').val()
		};
		var userObjString = JSON.stringify(userObj, null, 2);
		var jqxhr = $
				.ajax({
					url : "/forgotpassword",
					type : "PUT",
					data : userObjString,
					processData : false,
					dataType : "json",
					contentType : "application/json",
					async : true,
					statusCode : {
						200 : function() {
							$('#user_message').show();
							$('#user_errors').hide();
						},
						503 : function() {
							$('#user_message').hide();
							$('#user_errors')
									.html(
											'Unable to process the request. Please try again later');
							$('#user_errors').show();
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						log(errorThrown);
						$('#user_message').hide();
						$('#user_errors')
								.html(
										'Unable to process the request. Please try again later');
						$('#user_errors').show();
					},
					complete : function(xhr, textStatus) {
						$('.meter').css("width", "100%");
						$('.button').removeClass('disabled');
						$('#changePasswordForm').trigger("reset");

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

/** *End***************************************** */
