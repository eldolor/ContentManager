jQuery(function($) {
	log("function($)", "Entering");
	try {
		$('#user_sign_in').unbind();
		$('#user_sign_in').bind('click', loginRedirect);
		$('#user_sign_up').unbind();
		$('#user_sign_up').bind('click', signupRedirect);
	} catch (err) {
		handleError("function($)", err);
	} finally {
		log("function($)", "Exiting");
	}
});

function loginRedirect() {
	log("loginRedirect", "Entering");
	try {
		$('#user_sign_in').unbind();
		window.location.href = "/login";
	} catch (err) {
		handleError("loginRedirect", err);
	} finally {
		log("loginRedirect", "Exiting");
	}

}

function signupRedirect() {
	log("signupRedirect", "Entering");
	try {
		$('#user_sign_up').unbind();
		window.location.href = "/signup";
	} catch (err) {
		handleError("signupRedirect", err);
	} finally {
		log("signupRedirect", "Exiting");
	}

}
