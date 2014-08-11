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
		$('#login_errors').hide();

		if (mErrors != null) {
			$('#login_errors').html(getErrorMessage("login_failure", mErrors));
			$('#login_errors').show();
		}
		$('#user_forgot_password_button').unbind();
		$('#user_forgot_password_button').bind('click', function() {
			$('#forgot_password_modal').foundation('reveal', 'open');
		});
		// now using foundation abide
		// $('#user_sign_in_submit_button').unbind();
		// $('#user_sign_in_submit_button').bind('click', function() {
		// document.loginForm.submit();
		// // TODO: make an ajax call
		// // login();
		// });
		$('#user_sign_in_cancel_button').unbind();
		$('#user_sign_in_cancel_button').bind('click', function() {
			$('#user_sign_in_submit_button').unbind();
			window.location.href = '/';
		});
		// enable abide form validation
		$(document).foundation('abide','events'); 
		$('#loginForm').on('invalid.fndtn.abide', function() {
			var invalid_fields = $(this).find('[data-invalid]');
			console.log(invalid_fields);
		}).on('valid.fndtn.abide', function() {
			document.loginForm.submit();
			// login();
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
						'<a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Sign In</a></li>');

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
				200 : function() {
					window.location.href = '/applications';
				},
				400 : function(text) {
					try {
						// $('#login_errors').html(
						// '<p>' + getErrorMessages(text) + '</p>');
						$('#login_errors').show();
					} catch (err) {
						handleError("login", err);
					}
				}
			},
			complete : function(xhr, textStatus) {
				console.log(xhr.status);
			}
		});

		return false;
	} catch (err) {
		handleError("login", err);
	} finally {
		log("login", "Exiting");
	}
}
