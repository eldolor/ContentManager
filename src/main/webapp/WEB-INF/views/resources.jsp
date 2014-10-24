<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>


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
		SIGN_IN : "sign_in",
		SIGN_UP : "sign_up",
		HOME : "home",
		MESSAGES : "messages",
		FORGOT_PASSWORD : "forgot_password",
		APPLICATIONS : "applications",
		APPLICATION : "application",
		CONTENT_GROUPS : "content_groups",
		CONTENT_GROUP : "content_group",
		CONTENTS : "contents",
		CONTENT : "content",
		CLIENT_KEYS : "client_keys",
		BILLING : "billing",
		ACCOUNT_USAGE : "account_usage",
		CHANGE_PASSWORD : "change_password",
		SEARCH_RESULTS : "search_results",
		PLANS : "plans",
		PRODUCT_TOUR_CONTENT_MANAGEMENT : "product_tour_content_management",
		PRODUCT_TOUR_USAGE_REPORTS : "product_tour_usage_reports",
		OVERVIEW : "overview",
		USAGE_REPORTS_APPLICATIONS : "usage_reports_applications",
		USAGE_REPORTS_CONTENT_GROUPS : "usage_reports_content_groups",
		USAGE_REPORTS_CONTENTS : "usage_reports_contents",
		DOCUMENTS_GETTING_STARTED_ANDROID : "documents_getting_started_android",
		DOCUMENTS_ANDROID_SDK_API_REFERENCE : "documents_android_sdk_api_reference",
		DOWNLOADS_ANDROID_SDK : "downloads_android_sdk"
	};
	var Category = {
		MESSAGES : "messages",
		FORGOT_PASSWORD : "forgot_password",
		APPLICATIONS : "applications",
		APPLICATION : "application",
		CONTENT_GROUPS : "content_group",
		CONTENT_GROUP : "content_group",
		CONTENTS : "contents",
		CONTENT : "content",
		CLIENT_KEYS : "client_keys",
		BILLING : "billing",
		ACCOUNT_USAGE : "account_usage",
		CHANGE_PASSWORD : "change_password",
		SIGN_IN : "sign_in",
		SIGN_OUT : "sign_out",
		SIGN_UP : "sign_up",
		PLANS : "plans",
		PRODUCT_TOUR : "product_tour",
		USAGE_REPORTS : "usage_reports",
		DOCUMENTS : "documents",
		SDK : "sdk",
		SDK_DOCUMENTS : "sdk_documents"
	};
	var Action = {
		CREATE : "create",
		UPDATE : "update",
		CANCEL : "cancel",
		DELETE : "delete",
		VIEW : "view",
		MOVE : "move",
		REQUEST : "request",
		UPLOAD : "upload",
		SIGN_IN : "sign_in",
		SIGN_OUT : "sign_out",
		SIGN_UP : "sign_up",
		PUSH_CHANGES_TO_HANDSETS : "push_changes_to_handsets",
		DOWNLOAD : "download"
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

<!-- Favicon -->
<link rel="shortcut icon" href="/resources/images/cm/favicon.ico" type="image/x-icon">
<link rel="icon" href="/resources/images/cm/favicon.ico" type="image/x-icon">


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
<link rel="stylesheet" type="text/css"
	href="/resources/stylesheets/jquery-plugins/jquery.tagsinput.css" />

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