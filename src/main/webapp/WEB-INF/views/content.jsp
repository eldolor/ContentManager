
<!doctype html>
<!--[if IE 9]><html class="lt-ie10" lang="en" > <![endif]-->
<html class="no-js" lang="en"
	data-useragent="Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>


<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Content Manager</title>

<link rel="stylesheet" href="resources/css/foundation/foundation.css"
	type="text/css" />

<!-- Begin Foundation Related -->
<script type="text/javascript" src="resources/js/modernizr/modernizr.js"></script>
<script type="text/javascript"
	src="resources/js/jquery-2.1.1/jquery.min.js"></script>
<script src="resources/js/jquery-ui-1.11.0/jquery-ui.min.js"></script>
<link href="resources/css/jquery-ui-1.11.0/jquery-ui.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="resources/js/foundation/foundation.min.js"></script>
<script type="text/javascript"
	src="resources/js/foundation/foundation.reveal.js"></script>
<!-- Begin Foundation Related -->

<!-- Begin Custom -->
<script type="text/javascript" src="resources/js/cm.content.group.js"></script>
<script type="text/javascript" src="resources/js/mavin.user.js"></script>
<script type="text/javascript" src="resources/js/mavin.globals.js"></script>
<script type="text/javascript" src="resources/js/mavin.utilities.js"></script>
<!-- Begin Custom -->

<script type="text/javascript" src="resources/js/json2.js"></script>

<!-- Begin Date Related -->
<!-- <script type="text/javascript" src="resources/js/datejs/date-en-US.js"></script> -->

<script type="text/javascript"
	src="resources/js/momentjs/moment.1.7.2.min.js"></script>
<!-- End Date Related -->

<!-- File Upload -->
<script type="text/javascript"
	src="resources/js/mavin.image.file.upload.js"></script>
<script type="text/javascript"
	src="resources/js/mavin.video.file.upload.js"></script>
<script type="text/javascript"
	src="resources/js/mavin.reminder.file.upload.js"></script>
<script type="text/javascript"
	src="resources/js/mavin.voice.file.upload.js"></script>
<script type="text/javascript"
	src="resources/js/jquery/jquery.filedrop.js"></script>
<!-- File Upload -->

<!-- JTable -->
<script type="text/javascript"
	src="resources/js/jquery/jquery.dataTables.min.js"></script>
<link href="resources/css/demo_table.css" rel="stylesheet"
	type="text/css" />
<!-- JTable -->

<!-- Time Picker -->
<script type="text/javascript"
	src="resources/js/jquery/jquery-ui-sliderAccess.js"></script>
<script type="text/javascript"
	src="resources/js/jquery/jquery-ui-timepicker-addon.js"></script>
<link href="resources/css/jquery-ui-timepicker-addon.css"
	rel="stylesheet" type="text/css" />
<!-- Time Picker -->

<!-- JPlayer -->
<script type="text/javascript"
	src="resources/js/jquery/jquery.jplayer.2.2.0.min.js"></script>
<link href="resources/jplayer/skin/blue.monday/jplayer.blue.monday.css"
	rel="stylesheet" type="text/css" />
<!-- JPlayer -->

<!-- Scroll To  -->
<script type="text/javascript"
	src="resources/js/jquery/jquery.scrollto-1.4.5.min.js"></script>
<!-- Scroll To  -->
<!-- Bootstrap  -->
<!-- <script type="text/javascript"
	src="resources/js/jquery/bootstrap.min.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" /> -->
<!-- Bootstrap  -->


<script>
	$(document).foundation();

	var doc = document.documentElement;
	doc.setAttribute('data-useragent', navigator.userAgent);
</script>

</head>
<body>
	<div class="row">
		<div class="large-12 columns">

			<nav class="top-bar" data-topbar>
				<ul class="title-area">

					<li class="name">
						<h1>
							<a href="#">Content Manager</a>
						</h1>
					</li>
					<li class="toggle-topbar menu-icon"><a href="#"><span>menu</span></a>
					</li>
				</ul>
				<section class="top-bar-section">
					<ul class="left">
						<li><a href="#">Link 1</a></li>
						<li><a href="#">Link 2</a></li>
						<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN">
							<li><a id="accounts" href="./am">Accounts</a></li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN">
							<li><a id="users" href="./um">Users</a></li>
						</sec:authorize>
						<li><a id="myAccount" href="javascript:void(0);"
							onclick="editLoggedInUser()">My Account</a></li>
						<li><a href="<c:url value="/j_spring_security_logout"/>">Sign
								out [<sec:authentication property="principal.username" />]
						</a></li>
					</ul>
					<ul class="right">
						<li class="search">
							<form>
								<input type="search">
							</form>
						</li>
						<li class="has-button"><a class="small button" href="#">Search</a>
						</li>
					</ul>
				</section>
			</nav>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="large-12 columns">
			<ul class="breadcrumbs">
				<li><a href="#">Home</a></li>
				<li><a href="#">Features</a></li>
				<li class="unavailable"><a href="#">Gene Splicing</a></li>
				<li class="current"><a href="#">Cloning</a></li>
			</ul>
		</div>
	</div>
	<br>
	<div class="row">


		<div class="large-3 columns ">
			<div class="panel">
				<a href="#"><img src="http://placehold.it/300x240&text=[img]" /></a>
				<h5>
					<a href="#"><sec:authentication property="principal.username" /></a>
				</h5>
				<div class="section-container vertical-nav" data-section
					data-options="deep_linking: false; one_up: true">
					<section class="section">
						<h5 class="title">
							<img src="resources/images/plus-box-16x16.png" height="16"
								width="16" /><a id="cm_action"
								data-reveal-id="content_group_create_modal" href="#"></a>
						</h5>
					</section>
				</div>
			</div>
		</div>
		<div class="large-6 columns">

			<div class="row">
				<div class="large-2 columns small-3">
					<img src="http://placehold.it/80x80&text=[img]" />
				</div>
				<div class="large-10 columns">
					<p>
						<strong>Some Person said:</strong> Bacon ipsum dolor sit amet
						nulla ham qui sint exercitation eiusmod commodo, chuck duis velit.
						Aute in reprehenderit, dolore aliqua non est magna in labore pig
						pork biltong.
					</p>
					<ul class="inline-list">
						<li><a href="">Reply</a></li>
						<li><a href="">Share</a></li>
					</ul>
					<h6>2 Comments</h6>
					<div class="row">
						<div class="large-2 columns small-3">
							<img src="http://placehold.it/50x50&text=[Reply]" />
						</div>
						<div class="large-10 columns">
							<p>Bacon ipsum dolor sit amet nulla ham qui sint exercitation
								eiusmod commodo, chuck duis velit. Aute in reprehenderit</p>
						</div>
					</div>
					<div class="row">
						<div class="large-2 columns small-3">
							<img src="http://placehold.it/50x50&text=[img]" />
						</div>
						<div class="large-10 columns">
							<p>Bacon ipsum dolor sit amet nulla ham qui sint exercitation
								eiusmod commodo, chuck duis velit. Aute in reprehenderit</p>
						</div>
					</div>
				</div>
			</div>

			<hr />

			<div class="row">
				<div class="large-2 columns small-3">
					<img src="http://placehold.it/80x80&text=[img]" />
				</div>
				<div class="large-10 columns">
					<p>
						<strong>Some Person said:</strong> Bacon ipsum dolor sit amet
						nulla ham qui sint exercitation eiusmod commodo, chuck duis velit.
						Aute in reprehenderit, dolore aliqua non est magna in labore pig
						pork biltong.
					</p>
					<ul class="inline-list">
						<li><a href="">Reply</a></li>
						<li><a href="">Share</a></li>
					</ul>
				</div>
			</div>

			<hr />

			<div class="row">
				<div class="large-2 columns small-3">
					<img src="http://placehold.it/80x80&text=[img]" />
				</div>
				<div class="large-10 columns">
					<p>
						<strong>Some Person said:</strong> Bacon ipsum dolor sit amet
						nulla ham qui sint exercitation eiusmod commodo, chuck duis velit.
						Aute in reprehenderit, dolore aliqua non est magna in labore pig
						pork biltong.
					</p>
					<ul class="inline-list">
						<li><a href="">Reply</a></li>
						<li><a href="">Share</a></li>
					</ul>
					<h6>2 Comments</h6>
					<div class="row">
						<div class="large-2 columns small-3">
							<img src="http://placehold.it/50x50&text=[img]" />
						</div>
						<div class="large-10 columns">
							<p>Bacon ipsum dolor sit amet nulla ham qui sint exercitation
								eiusmod commodo, chuck duis velit. Aute in reprehenderit</p>
						</div>
					</div>
				</div>
			</div>

		</div>
		<aside class="large-3 columns hide-for-small">
			<p>
				<img src="http://placehold.it/300x440&text=[ad]" />
			</p>
			<p>
				<img src="http://placehold.it/300x440&text=[ad]" />
			</p>
		</aside>
	</div>
	<br>
	<footer class="row">
		<div class="large-12 columns">
			<hr>
			<div class="row">
				<div class="large-6 columns">
					<p>© Copyright Coconut Martini Inc.</p>
				</div>
				<div class="large-6 columns">
					<ul class="inline-list right">
						<li><a href="#">Link 1</a></li>
						<li><a href="#">Link 2</a></li>
						<li><a href="#">Link 3</a></li>
						<li><a href="#">Link 4</a></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>
	<!-- Begin Content Group -->
	<div class="reveal-modal medium" id="content_group_create_modal"
		data-reveal>
		<h3 id="contentGroupModalLabel">Content Group Setup</h3>
		<div class="modal-body">
			<form id="contentGroupForm" name="contentGroupForm">
				<input type="hidden" id="contentgroup_id" name="contentgroup_id" />
				<input type="hidden" />

				<div class="row">
					<div class="large-12 columns">
						<label>Name: <input type="text" id="contentgroup_name"
							name="contentgroup_name" />
						</label>
					</div>
				</div>
				<div class="row">
					<div class="large-12 columns">
						<label>Description: <textarea rows="5"
								id="contentgroup_description" name="contentgroup_description"></textarea>
						</label>
					</div>
				</div>
				<div class="row">
					<div class="large-12 columns">
						<label>Start Date:
							<div id="contentgroup_start_datepicker">
								<input type="text" id="contentgroup_start_date"
									name="contentgroup_start_date" /> <br />
							</div>
						</label>
					</div>
				</div>

				<div class="row">
					<div class="large-12 columns">
						<label>End Date:
							<div id="contentgroup_end_datepicker">
								<input type="text" id="contentgroup_end_date"
									name="contentgroup_end_date" /> <br />
							</div>
						</label>
					</div>
				</div>

				<div class="row">
					<div class="large-12 columns">
						<label> Content Group <span class="label label-info"
							id="contentgroup_status">Enabled</span>
						</label> <input type="checkbox" id="contentgroup_enabled"
							name="contentgroup_enabled" checked="checked" /> </label>
					</div>
				</div>
				<div>&nbsp;</div>
			</form>
		</div>
		<div class="modal-footer">
			<span id="contentgroup_errors" class="label label-important"></span>
			<button id="contentgroup_save_button" class="button">create</button>
		</div>
		<a class="close-reveal-modal">&#215;</a>
	</div>
	<!-- End Ad Group -->

</body>
</html>