<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>


<link rel="stylesheet"
	href="/resources/stylesheets/foundation/foundation.css" type="text/css" />

<!-- Begin Foundation Related -->
<script type="text/javascript"
	src="/resources/javascripts/modernizr/modernizr.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/jquery-2.1.1/jquery.min.js"></script>
<script src="/resources/javascripts/jquery-ui-1.11.0/jquery-ui.min.js"></script>
<link href="/resources/stylesheets/jquery-ui-1.11.0/jquery-ui.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="/resources/javascripts/foundation/foundation.min.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/foundation/foundation.reveal.js"></script>
<script src="/resources/javascripts/foundation/foundation.tooltip.js"></script>
<script src="/resources/javascripts/foundation/foundation.joyride.js"></script>
<script src="/resources/javascripts/foundation/foundation.dropdown.js"></script>
<script src="/resources/javascripts/foundation/foundation.abide.js"></script>
<script src="/resources/javascripts/jquery-2.1.1/jquery.cookie.js"></script>
<script src="/resources/javascripts/foundation/foundation.alert.js"></script>
<script src="/resources/javascripts/foundation/foundation.magellan.js"></script>
<!-- Begin Foundation Related -->

<script type="text/javascript" src="/resources/javascripts/cm/json2.js"></script>

<!-- Begin Date Related -->
<!-- <script type="text/javascript" src="/resources/javascripts/datejs/date-en-US.js"></script> -->

<script type="text/javascript"
	src="/resources/javascripts/momentjs/moment.2.8.2.js"></script>
<!-- End Date Related -->


<!-- JTable -->
<!-- <script type="text/javascript"
	src="/resources/javascripts/jquery-plugins/jquery.dataTables.min.js"></script>
<link href="/resources/stylesheets/demo_table.css" rel="stylesheet"
	type="text/css" />
 -->
<!-- JTable -->

<!-- Time Picker -->
<script type="text/javascript"
	src="/resources/javascripts/jquery-plugins/jquery-ui-sliderAccess.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/jquery-plugins/jquery-ui-timepicker-addon.js"></script>
<link
	href="/resources/stylesheets/jquery-plugins/jquery-ui-timepicker-addon.css"
	rel="stylesheet" type="text/css" />
<!-- Time Picker -->

<!-- JPlayer -->
<script type="text/javascript"
	src="/resources/javascripts/jquery-plugins/jquery.jplayer.2.2.0.min.js"></script>
<link
	href="/resources/stylesheets/jplayer/skin/blue.monday/jplayer.blue.monday.css"
	rel="stylesheet" type="text/css" />
<!-- JPlayer -->

<!-- Scroll To  -->
<!-- <script type="text/javascript"
	src="/resources/javascripts/jquery-plugins/jquery.scrollto-1.4.5.min.js"></script>
 -->
<!-- Scroll To  -->





<!-- Jquery File drop-->
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.content.file.upload.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/jquery-plugins/jquery.filedrop.js"></script>
<link href="/resources/stylesheets/cm/content.dropbox.css"
	media="screen" rel="stylesheet" type="text/css" />
<!-- Jquery File drop-->

<!-- Custom Scripts -->
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.utilities.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.globals.js"></script>
<link href="/resources/stylesheets/cm/style.css" rel="stylesheet">
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

	var PageTitle = {
		APPLICATIONS : "applications",
		APPLICATION : "application",
		CONTENT_GROUPS : "content_groups",
		CONTENT_GROUP : "content_group",
		CONTENTS : "contents",
		CONTENT : "content",
		ACCOUNT_SETTINGS : "account_settings",
		SEARCH_RESULTS : "search_results"
	};
	var Category = {
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
	};
	var Action = {
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
	};
</script>
<!-- End Google Analytics -->
<script type="text/javascript">
	var CanonicalSearchableType = {
		APPLICATION : "application",
		CONTENT_GROUP : "content_group",
		CONTENT : "content"
	};
</script>


<!-- Theme Related -->
<link href='https://fonts.googleapis.com/css?family=Raleway:400,300,100'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href="/resources/stylesheets/foundation-icons/foundation-icons.css" />
<link href="/resources/stylesheets/box/animate.css" rel="stylesheet" />
<link href="/resources/stylesheets/box/owl.carousel.css"
	rel="stylesheet">
<link href="/resources/stylesheets/box/owl.theme.css" rel="stylesheet">
<link href="/resources/stylesheets/box/style.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/resources/stylesheets/jquery-plugins/jquery.tagsinput.css" />

<script type="text/javascript"
	src="/resources/javascripts/jquery-plugins/jquery.simple-text-rotator.js"></script>
<script src="/resources/javascripts/vendor/waypoints.min.js"></script>
<script src="/resources/javascripts/vendor/owl.carousel.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
<script type="text/javascript"
	src="/resources/javascripts/jquery-plugins/jquery.mixitup.min.js"></script>
<script src="/resources/javascripts/jquery-plugins/jquery.tagsinput.js"></script>
<!-- End Theme Related -->