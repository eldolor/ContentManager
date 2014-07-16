<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<html lang="en">
<head>


<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<link href="resources/css/main.css" media="screen" rel="stylesheet"
	type="text/css" />
<link href="resources/css/image.ad.dropbox.css" media="screen"
	rel="stylesheet" type="text/css" />
<link href="resources/css/video.ad.dropbox.css" media="screen"
	rel="stylesheet" type="text/css" />
<link href="resources/css/reminder.ad.dropbox.css" media="screen"
	rel="stylesheet" type="text/css" />
<link href="resources/css/voice.ad.dropbox.css" media="screen"
	rel="stylesheet" type="text/css" />
<title>Campaign Management</title>


<!-- JQuery related includes -->
<script src="resources/js/jquery/jquery-1.8.1.min.js"></script>
<script src="resources/js/jquery/jquery-ui-1.10.0.custom.min.js"></script>
<link href="resources/css/jquery-ui-1.10.0.custom.min.css"
	rel="stylesheet" type="text/css" />
<!--  End JQuery includes -->
<!-- Begin Date Related -->
<!-- <script type="text/javascript" src="resources/js/datejs/date-en-US.js"></script>
 -->
<script type="text/javascript"
	src="resources/js/momentjs/moment.1.7.2.min.js"></script>
<!-- End Date Related -->

<script type="text/javascript" src="resources/js/mavin.globals.js"></script>
<script type="text/javascript" src="resources/js/mavin.utilities.js"></script>
<script type="text/javascript" src="resources/js/json2.js"></script>

<script type="text/javascript" src="resources/js/mavin.am.account.js"></script>


<!-- Time Picker -->
<script type="text/javascript"
	src="resources/js/jquery/jquery-ui-sliderAccess.js"></script>
<script type="text/javascript"
	src="resources/js/jquery/jquery-ui-timepicker-addon.js"></script>
<link href="resources/css/jquery-ui-timepicker-addon.css"
	rel="stylesheet" type="text/css" />
<!-- Time Picker -->


<!-- Scroll To  -->
<script type="text/javascript"
	src="resources/js/jquery/jquery.scrollto-1.4.5.min.js"></script>
<!-- Scroll To  -->
<!-- Bootstrap  -->
<script type="text/javascript"
	src="resources/js/jquery/bootstrap.min.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<!-- Bootstrap  -->

<script type="text/javascript">
        function screenSetup () {
            //If an error occurred on one of the popup forms, we need to redisplay the form.
            //Controller will pass the name of the div and then we reopen it
            if (${popupScreen!=null}) {
                openPopup('${popupScreen}');
                fieldDiv = document.getElementById("field");
                fieldDiv.style.display='block';
            }
            else if (${bootstrap?"true":"false"}) {
                //We're in bootstrap mode to set up a new campaign

                //If the account object has an ID it was created so move on to campaign setup
                if (${!accountcreated}) {
                    fieldDiv = document.getElementById("field");
                    fieldDiv.style.display ='none';
                    openPopup('create_account_div');
                }
                else {
                    //Account was created so open up the form to create the journal
                    fieldDiv = document.getElementById("field");
                    fieldDiv.style.display='none';
                    openPopup('campaign_div');
                }
            }
            else {
                //Not in bootstrap mode or an error state so show the page
                fieldDiv = document.getElementById("field");
                fieldDiv.style.display='block';
                // load campaigns asynchronously
                getAccounts();
            }
        }

        //Initialize the date pickers in the various forms
        // on document ready
         $(function() {


 
			$( document ).tooltip();

        });
        
         
    </script>
</head>

<body onload="screenSetup();">

	<div id="field">
		<div id="main">
			<div class="navbar  navbar-fixed-top ">
				<div class="navbar-inner">
					<div class="container">

						<a class="brand" href="./am">Account Management</a>

						<div class="nav-collapse collapse">
							<ul class="nav">
								<li class="divider-vertical"></li>
								<li class="active"><a href="./am">Home</a></li>
								<sec:authorize
									ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER">

									<li><a href="./secured">Campaigns</a></li>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN, ROLE_ADMIN">
									<li><a href="./um">Users</a></li>
								</sec:authorize>
								
								<li><a href="<c:url value="/j_spring_security_logout"/>">Sign
										out [<sec:authentication property="principal.username" />]
								</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<div id="breadcrumbs"></div>
			<span id="main_errors" class="label label-important"></span>

			<div id="test" />
			<!-- Begin entries -->
			<div id="entries" />
			<!-- End entries -->

			<div id="footer"></div>
		</div>
	</div>

	<!-- Begin Confirm -->
	<div class="modal hide fade" id="confirm" aria-hidden="true">
		<img src="resources/images/wait.gif"
			style="display: none; float: right" id="confirm_wait" />
		<div class="modal-header">
			<h3 id="confirmModalLabel">Confirm</h3>
		</div>
		<div class="modal-body">
			<div id="confirm_message"></div>
		</div>
		<div class="modal-footer">
			<button id="campaign_cancel_button" class="btn btn-primary"
				data-dismiss="modal" aria-hidden="true">No</button>
			<button id="confirm_yes_button" class="btn">Yes</button>
		</div>
	</div>
	<!-- End Confirm -->

	<%-- 	<sec:authorize ifAnyGranted="ROLE_SUPER_ADMIN">
 --%>
	<!-- Begin Create Account -->
	<div class="modal hide fade" id="account_div">

		<div class="modal-header">
			<h3 id="createAccountModalLabel">Account Setup</h3>
		</div>
		<div class="modal-body">
			<form id="createAccountForm" name="createAccountForm">
				<input type="hidden" id="account_id" name="account_id" />
				<div class="popupFieldLabel">Name:</div>
				<div class="popupFieldInput">
					<input type="text" id="account_name" name="account_name" />
				</div>
				<div class="popupFieldLabel">Description:</div>
				<div class="popupFieldInput">
					<textarea rows="5" id="account_description"
						name="account_description"></textarea>
				</div>
				<div class="popupFieldLabel">
					Account <span class="label label-info" id="account_status">Enabled</span>
				</div>
				<div class="popupFieldLabel">
					<label class="checkbox"><input type="checkbox"
						id="account_enabled" name="account_enabled" checked="checked" /></label>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<span id="account_errors" class="label label-important"></span> <img
				src="resources/images/wait.gif" style="display: none; float: right"
				id="account_wait" />
			<button id="account_cancel_button" class="btn" data-dismiss="modal"
				aria-hidden="true">cancel</button>
			<button id="account_save_button" class="btn btn-primary">create
				account</button>
		</div>
	</div>
	<%-- 	</sec:authorize>
 --%>



	<!-- Begin Wait Div -->
	<!-- 	<div id="wait_div" class="popupViewWait">
		<img src="resources/images/wait_large.gif" id="wait" />
	</div>
 -->
	<!-- End Wait Div -->

</body>
</html>