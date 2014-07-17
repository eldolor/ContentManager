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

		$('#user_sign_in_submit_button').unbind();
		$('#user_sign_in_submit_button').bind('click', function() {
			document.loginForm.submit();
			//TODO: make an ajax call
			//login();
		});
		$('#user_sign_in_cancel_button').unbind();
		$('#user_sign_in_cancel_button').bind('click', function() {
			$('#user_sign_in_submit_button').unbind();
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
		$('#breadcrumbs').html(lHtml + "<a href=\"#\">Sign In</a>");

	} catch (err) {
		handleError("setupBreadcrumbs", err);
	} finally {
		log("setupBreadcrumbs", "Exiting");
	}
}

function login() {
	log("login", "Entering");
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
		var jqxhr = $.ajax({
			url : "j_spring_security_check",
			type : "POST",
			data : objString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					window.location.href = '/content';
				},
				400 : function(text) {
					try {
						$('#login_errors').html(getErrorMessages(text));
					} catch (err) {
						handleError("login", err);
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
		handleError("login", err);
	} finally {
		log("login", "Exiting");
	}
}

function createAccount() {
	log("createAccount", "Entering");
	try {
		var _date = new Date();
		var _timeCreated = _date.getTime();
		var obj = {
			userName : $('#username').val(),
			password : $('#password').val(),
			password2 : $('#password2').val(),
			timeCreatedMs : _timeCreated,
			timeCreatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000),
			timeUpdatedMs : _timeCreated,
			timeUpdatedTimeZoneOffsetMs : (_date.getTimezoneOffset() * 60 * 1000)
		};
		var objString = JSON.stringify(obj, null, 2);
		var jqxhr = $.ajax({
			url : "/admin/account",
			type : "POST",
			data : objString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				201 : function() {
					;
					window.location.href = "/secured";
				},
				400 : function(text) {
					try {
						$('#create_account_errors')
								.html(getErrorMessages(text));
					} catch (err) {
						handleError("createAccount", err);
					}
				}
			}
		});

		jqxhr.always(function(msg) {
			clearWait('create_account_wait');
		});

		return false;
	} catch (err) {
		handleError("createAccount", err);
	} finally {
		log("createAccount", "Exiting");
	}
}
