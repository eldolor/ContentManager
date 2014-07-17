jQuery(function($) {
	log("function($)", "Entering");
	try {
		$('#user_sign_in').bind('click', loginRedirect);
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
