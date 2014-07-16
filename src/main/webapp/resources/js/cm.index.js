jQuery(function($) {
//    $('a[href="#TODO"]').click(function() {
//        alert('Nothing to see here, boss.');
//    });
    
    $('#user_sign_in').bind('click', loginRedirect);
//    $('#user_sign_in_submit_button').bind('click', document.loginForm.submit());
//    $('#user_sign_in_cancel_button').bind('click', function() {
//		$('#user_sign_in_submit_button').unbind();
//		;
//	});
});

function loginRedirect(){
	$('#user_sign_in').unbind();
	window.location.href = "/login";
}
