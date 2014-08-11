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
		setupLeftNavBar();
		setupBreadcrumbs();
		// enable abide form validation
		$(document).foundation('abide', 'events');
		getLoggedInUser();

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
						'<a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Change Password</a>'
								+ '<a id=\"left_nav_bar_link_1\" href=\"javascript:void(0);\" >Billing</a>'
								+ '</li>');
		$('#left_nav_bar_link_1').unbind();
		$('#left_nav_bar_link_1').click(function() {
			// $('#user_billing').hide();
			$('#user_update').show();
			newContent();
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
function getLoggedInUser() {
	log("getLoggedInUser", "Entering");
	try {

		var jqxhr = $.ajax({
			url : "/um/loggedinuser",
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

function updatePassword() {
	log("updatePassword", "Entering");

	var lDate = new Date();
	try {
		var userObj = {
			// get it from global variable
			id : mLoggedInUser.id,
			password : $('#user_confirm_new_password').val(),
			timeUpdatedMs : lDate.getTime(),
			timeUpdatedTimeZoneOffsetMs : (lDate.getTimezoneOffset() * 60 * 1000)
		};
		var userObjString = JSON.stringify(userObj, null, 2);
		var jqxhr = $.ajax({
			url : "/um/password",
			type : "PUT",
			data : userObjString,
			processData : false,
			dataType : "json",
			contentType : "application/json",
			async : false,
			statusCode : {
				200 : function() {
					$('#user_message').show();
				},
				400 : function(text) {
					try {
						$('#user_errors').html(
								'<p>' + getErrorMessages(text) + '</p>');
						$('#user_errors').show();
					} catch (err) {
						handleError("updatePassword", err);
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
		handleError("updatePassword", err);
	} finally {
		log("updatePassword", "Exiting");
	}
}

/** *End***************************************** */
