<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>


<link rel="stylesheet" href="/resources/css/foundation/foundation.css"
	type="text/css" />

<!-- Begin Foundation Related -->
<script type="text/javascript"
	src="/resources/js/modernizr/modernizr.js"></script>
<script type="text/javascript"
	src="/resources/js/jquery-2.1.1/jquery.min.js"></script>
<script src="/resources/js/jquery-ui-1.11.0/jquery-ui.min.js"></script>
<link href="/resources/css/jquery-ui-1.11.0/jquery-ui.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="/resources/js/foundation/foundation.min.js"></script>
<script type="text/javascript"
	src="/resources/js/foundation/foundation.reveal.js"></script>
<script src="/resources/js/foundation/foundation.tooltip.js"></script>
<script src="/resources/js/foundation/foundation.joyride.js"></script>
<script src="/resources/js/foundation/foundation.dropdown.js"></script>
<script src="/resources/js/foundation/foundation.abide.js"></script>
<script src="/resources/js/jquery-2.1.1/jquery.cookie.js"></script>
<script src="/resources/js/foundation/foundation.alert.js"></script>
<script src="/resources/js/foundation/foundation.magellan.js"></script>
<!-- Begin Foundation Related -->

<script type="text/javascript" src="/resources/js/json2.js"></script>

<!-- Begin Date Related -->
<!-- <script type="text/javascript" src="/resources/js/datejs/date-en-US.js"></script> -->

<script type="text/javascript"
	src="/resources/js/momentjs/moment.1.7.2.min.js"></script>
<!-- End Date Related -->


<!-- JTable -->
<script type="text/javascript"
	src="/resources/js/jquery/jquery.dataTables.min.js"></script>
<link href="/resources/css/demo_table.css" rel="stylesheet"
	type="text/css" />
<!-- JTable -->

<!-- Time Picker -->
<script type="text/javascript"
	src="/resources/js/jquery/jquery-ui-sliderAccess.js"></script>
<script type="text/javascript"
	src="/resources/js/jquery/jquery-ui-timepicker-addon.js"></script>
<link href="/resources/css/jquery-ui-timepicker-addon.css"
	rel="stylesheet" type="text/css" />
<!-- Time Picker -->

<!-- JPlayer -->
<script type="text/javascript"
	src="/resources/js/jquery/jquery.jplayer.2.2.0.min.js"></script>
<link href="/resources/jplayer/skin/blue.monday/jplayer.blue.monday.css"
	rel="stylesheet" type="text/css" />
<!-- JPlayer -->

<!-- Scroll To  -->
<script type="text/javascript"
	src="/resources/js/jquery/jquery.scrollto-1.4.5.min.js"></script>
<!-- Scroll To  -->





<!-- Jquery File drop-->
<script type="text/javascript"
	src="/resources/js/cm.content.file.upload.js"></script>
<script type="text/javascript"
	src="/resources/js/jquery/jquery.filedrop.js"></script>
<link href="/resources/css/content.dropbox.css" media="screen"
	rel="stylesheet" type="text/css" />
<!-- Jquery File drop-->

<!-- Custom Scripts -->
<script type="text/javascript" src="/resources/js/cm.utilities.js"></script>
<script type="text/javascript" src="/resources/js/cm.globals.js"></script>
<!-- Custom Scripts -->

<!-- Google Analytics -->
<script>
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, 'script', '//www.google-analytics.com/analytics.js',
			'ga');

	ga('create', 'UA-53875097-1', 'auto');

	com.cm.contentmanager.PageTitle = {
		APPLICATIONS : "applications",
		APPLICATION : "application",
		CONTENT_GROUPS : "content_group",
		CONTENT_GROUP : "content_group",
		CONTENTS : "contents",
		CONTENT : "content",
		ACCOUNT_SETTINGS : "account_settings"
	}
	com.cm.contentmanager.Category = {
		APPLICATIONS : "applications",
		APPLICATION : "application",
		CONTENT_GROUPS : "content_group",
		CONTENT_GROUP : "content_group",
		CONTENTS : "contents",
		CONTENT : "content",
		ACCOUNT_SETTINGS : "account_settings",
		SIGN_IN : "sign_in",
		SIGN_OUT : "sign_out",
		SIGN_UP : "sign_up"
	}
	com.cm.contentmanager.Action = {
		CREATE_NEW : "create_new",
		CREATE : "create",
		UPDATE : "update",
		CANCEL : "cancel",
		DELETE : "delete",
		FORGOT_PASSWORD : "forgot_password",
		SIGN_IN : "sign_in",
		SIGN_OUT : "sign_out",
		SIGN_UP : "sign_up",
		ACCOUNT_SETTINGS : "account_settings"
	}
</script>
<!-- End Google Analytics -->