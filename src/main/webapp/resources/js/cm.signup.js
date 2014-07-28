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
		setupLeftNavBar();
		setupBreadcrumbs();
		$('#signup_errors').hide();

		$('#user_sign_up_submit_button').unbind();
		$('#user_sign_up_submit_button').bind('click', function() {
			signup();
		});
		$('#user_sign_up_cancel_button').unbind();
		$('#user_sign_up_cancel_button').bind('click', function() {
			$('#user_sign_up_submit_button').unbind();
			window.location.href = '/';
		});
	} catch (err) {
		handleError("setup", err);
	} finally {
		log("setup", "Exiting");
	}

}

function setupLeftNavBar() {
	log("setupLeftNavBar", "Entering");
	try {
		$('#left_nav_bar')
				.empty()
				.html(
						'<a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Sign Up</a></li>');

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
		$('#breadcrumbs').html(lHtml + "<a href=\"#\">Sign Up</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}

function signup() {
	log("signup", "Entering");
	try {
		// validate the email
		if (!validateEmail($('#userName').val())) {
			$('#signup_errors').html("<p>Please enter a valid email address</p>");
			$('#signup_errors').show();
			return;
		}
		var lDate = new Date();
		var lTimeCreated = lDate.getTime();

		var signupObj = {
			userName : $('#userName').val(),
			password : $('#password').val(),
			timeCreatedMs : lTimeCreated,
			timeCreatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : lTimeCreated,
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)
		};
		var objString = JSON.stringify(signupObj, null, 2);
		// alert(contentgroupObjString);
		// create via sync call
		var jqxhr = $.ajax({
			url : "/signup",
			type : "POST",
			data : objString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					postSignupAutoLogin($('#userName').val(), $('#password')
							.val());
				},
				400 : function(text) {
					try {
						// TODO: how do display error during post signup auto
						// login
						$('#signup_errors').html('<p>'+ getErrorMessages(text)+'</p>');
						$('#signup_errors').show();
					} catch (err) {
						handleError("signup", err);
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
