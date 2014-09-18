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
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>
<head>


<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<link href="../resources/css/main.css" media="screen" rel="stylesheet"
	type="text/css" />
<title>Feed Management</title>


<!-- Popup related includes -->
<script type="text/javascript"
	src="../resources/js/jquery/jquery-1.8.1.min.js"></script>
<script type="text/javascript"
	src="../resources/js/jquery/jquery-ui-1.10.0.custom.min.js"></script>
<script type="text/javascript"
	src="../resources/js/jquery/jquery.simplemodal-1.3.5.min.js"></script>
<link href="../resources/css/jquery-ui-1.10.0.custom.min.css"
	rel="stylesheet" type="text/css" />
<!--  End popup includes -->

<!-- Begin Date Related -->
<!-- <script type="text/javascript" src="../resources/js/datejs/date-en-US.js"></script>
 -->
<script type="text/javascript"
	src="../resources/js/momentjs/moment.1.7.2.min.js"></script>
<!-- End Date Related -->

<script type="text/javascript" src="../resources/js/mavin.user.js"></script>
<script type="text/javascript" src="../resources/js/mavin.globals.js"></script>
<script type="text/javascript" src="../resources/js/mavin.utilities.js"></script>
<script type="text/javascript" src="../resources/js/json2.js"></script>

<script type="text/javascript" src="../resources/js/mavin.fm.campaign.js"></script>
<script type="text/javascript" src="../resources/js/mavin.fm.feed.js"></script>
<script type="text/javascript" src="../resources/js/mavin.fm.survey.feed.js"></script>



<!-- Time Picker -->
<script type="text/javascript"
	src="../resources/js/jquery/jquery-ui-sliderAccess.js"></script>
<script type="text/javascript"
	src="../resources/js/jquery/jquery-ui-timepicker-addon.js"></script>
<link href="../resources/css/jquery-ui-timepicker-addon.css"
	rel="stylesheet" type="text/css" />
<!-- Time Picker -->


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

                //If the user object has an ID it was created so move on to campaign setup
                if (${!usercreated}) {
                    fieldDiv = document.getElementById("field");
                    fieldDiv.style.display ='none';
                    openPopup('create_account_div');
                }
                else {
                    //User was created so open up the form to create the journal
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
                getCampaigns();
            }
        }

        //Initialize the date pickers in the various forms
        // on document ready
         $(document).ready(function() {
            $("#campaign_start_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#campaign_start_date'
            });

            $("#campaign_end_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#campaign_end_date'
            });
            
            $("#survey_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#survey_date'
            });

        });
    </script>
</head>

<body onload="screenSetup();">

	<div id="field">
		<div id="main">
			<div id="header">
				<div id="actions">
					<sec:authorize ifNotGranted="ROLE_ADMIN">
						<div class="addJournal">
							<a href="javascript:void(0)"
								onclick="openPopup('create_account_div')"> Create Account </a>
						</div>
					</sec:authorize>
					<span class="sep"> | </span>
					<div class="signOut">
						<sec:authorize ifNotGranted="ROLE_ADMIN">
							<a href="javascript:void(0)" onclick="openPopup('login_div')">
								Sign in</a>
						</sec:authorize>
						<sec:authorize ifAllGranted="ROLE_ADMIN">
							<a href="<c:url value="/j_spring_security_logout"/>">Sign out</a>
						</sec:authorize>
					</div>
				</div>
			</div>

			<div id="intro">
				<div id="titleBox">
					<h2 id="titleBoxHeader">Feed Management</h2>
				</div>
			</div>
			<div id="breadcrumbs"></div>
			<div id="main_errors" class="error"></div>
			<!-- Begin entries -->
			<div id="entries" />
			<!-- End entries -->

			<div id="footer"></div>
		</div>
	</div>

	<!-- Begin Confirm -->
	<div id='confirm'>
		<img src="../resources/images/wait.gif"
			style="display: none; float: right" id="confirm_wait" />
		<div class='header'>
			<span>Confirm</span>
		</div>
		<div class='message'></div>
		<div class='buttons'>
			<div class='no simplemodal-close'>No</div>
			<div class='yes'>Yes</div>
		</div>
	</div>
	<!-- End Confirm -->

	<sec:authorize ifAllGranted="ROLE_ADMIN">
		<!-- Begin Campaign -->
		<div class="popup" id="campaign_div">
			<form id="campaignForm" name="campaignForm">
				<input type="hidden" id="campaign_id" name="campaign_id" /> <input
					type="hidden" id="campaign_traffic_targetting"
					name="campaign_traffic_targetting" /> <input type="hidden"
					id="campaign_gender_targetting" name="campaign_gender_targetting" />

				<h1>Campaign Setup</h1>
				<div id="campaign_errors" class="error">&nbsp;</div>
				<div id="campaign_setup_header">
					<div class="popupFieldLabel">Start with...</div>
					<div class="popupFieldLabel">
						<input checked="checked" onclick="toggleCampaignType(false)"
							type="radio" name="campaign_preload" value="false" />New
						Campaign<br /> <input onclick="toggleCampaignType(true)"
							type="radio" name="campaign_preload" value="true" />Sample
						Campaign
					</div>
				</div>

				<div class="popupFieldLabel">Name:</div>
				<div class="popupFieldInput">
					<input type="text" id="campaign_name" name="campaign_name" />
				</div>

				<div class="popupFieldLabel">Description:</div>
				<div class="popupFieldInput">
					<textarea rows="5" id="campaign_description"
						name="campaign_description"></textarea>
				</div>

				<div class="popupFieldLabel">Start Date:</div>
				<div class="popupFieldDateInput" id="campaign_start_datepicker">
					<input type="text" id="campaign_start_date"
						name="campaign_start_date" /> <br />
				</div>

				<div class="popupFieldLabel">End Date:</div>
				<div class="popupFieldDateInput" id="campaign_end_datepicker">
					<input type="text" id="campaign_end_date" name="campaign_end_date" />
					<br />
				</div>

				<div class="popupFieldLabel">
					Campaign <span id="campaign_status">Enabled</span>
				</div>
				<div class="popupFieldLabel">
					<input type="checkbox" id="campaign_enabled"
						name="campaign_enabled" checked="checked" />
				</div>

				<div class="popupFieldLabel">&nbsp;</div>

				<div class="popupFieldLabel">Geography:</div>
				<div class="popupFieldLabel">
					<input type="checkbox" id="campaign_country_targetting"
						name="campaign_country_targetting" value="all" />Target all
					geographic locations
				</div>

				<div class="popupFieldLabel"
					id="campaign_country_targetting_selection_div">
					<input type="checkbox" id="campaign_country_targetting_selection[]"
						name="campaign_country_targetting_selection[]"
						value="United States" />United States <br /> <input
						type="checkbox" id="campaign_country_targetting_selection[]"
						name="campaign_country_targetting_selection[]" value="Canada" />Canada
					<br /> <input type="checkbox"
						id="campaign_country_targetting_selection[]"
						name="campaign_country_targetting_selection[]" value="India" />India
					<br /> <input type="checkbox"
						id="campaign_country_targetting_selection[]"
						name="campaign_country_targetting_selection[]" value="Brazil" />Brazil
					<br /> <input type="checkbox"
						id="campaign_country_targetting_selection[]"
						name="campaign_country_targetting_selection[]" value="France" />France
					<br /> <input type="checkbox"
						id="campaign_country_targetting_selection[]"
						name="campaign_country_targetting_selection[]"
						value="United Kingdom" />United Kingdom
				</div>

				<div class="popupFieldLabel">&nbsp;</div>

				<div class="popupFieldLabel">
					<input type="radio" name="campaign_traffic_targetting_selection"
						onclick="toggleCampaignTrafficTargetting('all')" value="all" />Target
					all traffic, including Wi-Fi traffic<br /> <input type="radio"
						onclick="toggleCampaignTrafficTargetting('wifi')"
						name="campaign_traffic_targetting_selection" value="wifi" />Target
					Wi-Fi traffic<br /> <input type="radio"
						onclick="toggleCampaignTrafficTargetting('mobile_operator')"
						"
						name="campaign_traffic_targetting_selection"
						value="mobile_operator" />Target mobile operator traffic
				</div>

				<h3>Demographics:</h3>
				<div class="popupFieldLabel">Gender</div>
				<div class="popupFieldLabel">
					<input type="radio" name="campaign_gender_targetting_selection"
						onclick="toggleCampaignGenderTargetting('all')" value="all" />All
					Users<br /> <input type="radio"
						name="campaign_gender_targetting_selection"
						onclick="toggleCampaignGenderTargetting('male')" value="male" />Male
					users only<br /> <input type="radio"
						name="campaign_gender_targetting_selection"
						onclick="toggleCampaignGenderTargetting('female')" value="female" />Female
					users only
				</div>
				<div class="popupFieldLabel">&nbsp;</div>
				<div class="popupFieldLabel">Age Groups</div>
				<div class="popupFieldLabel">
					<input type="checkbox" id="campaign_age_targetting"
						name="campaign_age_targetting" value="all" />All age groups
				</div>
				<div class="popupFieldLabel"
					id="campaign_age_targetting_selection_div">
					<input type="checkbox" id="campaign_age_targetting_selection[]"
						name="campaign_age_targetting_selection[]" value="18-24" />18-24
					<br /> <input type="checkbox"
						id="campaign_age_targetting_selection[]"
						name="campaign_age_targetting_selection[]" value="25-34" />25-34
					<br /> <input type="checkbox"
						id="campaign_age_targetting_selection[]"
						name="campaign_age_targetting_selection[]" value="35-44" />35-44
					<br /> <input type="checkbox"
						id="campaign_age_targetting_selection[]"
						name="campaign_age_targetting_selection[]" value="45-54" />45-54
					<br /> <input type="checkbox"
						id="campaign_age_targetting_selection[]"
						name="campaign_age_targetting_selection[]" value="55-64" />55-64
					<br /> <input type="checkbox"
						id="campaign_age_targetting_selection[]"
						name="campaign_age_targetting_selection[]" value="65+" />65+
				</div>

				<div class="popupSubmitButtons">
					<img src="../resources/images/wait.gif"
						style="display: none; float: right" id="campaign_wait" /> <a
						class="cancelButton" href="javascript:void(0)"
						id="campaign_cancel_button">cancel</a> <a
						id="campaign_save_button" class="saveButton"
						href="javascript:void(0)">create campaign</a>
				</div>
			</form>
		</div>
		<!-- End Campaign -->
		<!-- Begin Stats-->
		<div class="popup" id="stats_div">
			<h1 id="stats_header">Statistics</h1>
			<div class="popupFieldLabel">&nbsp;</div>
			<div class="popupFieldLabel" id="stats_clicks">Clicks: N/A</div>
			<div class="popupFieldLabel" id="stats_impressions">Impressions:
				N/A</div>
			<div class="popupFieldLabel">&nbsp;</div>

		</div>
		<!-- End Stats -->


		<!-- Begin Feed Type-->
		<div class="popup" id="feed_type_div">
			<form id="feedTypeForm" name="feedTypeForm">
				<input type="hidden" id="feed_type_campaign_id"
					name="feed_type_campaign_id" />
				<!-- Default to text ad -->
				<input type="hidden" id="feed_type_selected"
					name="feed_type_selected" value="survey" />
				<h1>Select Feed Type</h1>
				<div id="feed_type_errors" class="error">&nbsp;</div>
				<div id="feed_type_setup_header">
					<div class="popupFieldLabel">
						<input type="radio" name="feed_type_preload" value="survey" />Survey<br />
						<input type="radio" name="feed_type_preload" value="coupon" />Coupon<br />
						<br />
					</div>
				</div>
				<div class="popupSubmitButtons">
					<img src="../resources/images/wait.gif"
						style="display: none; float: right" id="feed_type_wait" /> <a
						class="cancelButton" href="javascript:void(0)"
						onclick=";">cancel</a> <a
						id="feed_type_continue_button" class="saveButton"
						href="javascript:void(0)">continue</a>
				</div>
			</form>
		</div>
		<!-- End Ad Type-->

		<!-- Begin Survey -->
		<div class="popup" id="survey_div">
			<form id="surveyForm" name="surveyForm">
				<input type="hidden" id="survey_id" name="survey_id" /> <input
					type="hidden" id="survey_campaign_id" name="survey_campaign_id" />
				<h1>Survey Setup</h1>
				<div id="survey_errors" class="error">&nbsp;</div>
				<div id="survey_setup_header">
					<div class="popupFieldLabel">Start with...</div>
					<div class="popupFieldLabel">
						<input checked="checked" onclick="toggleSurveyType(false)"
							type="radio" name="survey_preload" value="false" />New Survey<br />
						<input onclick="toggleSurveyType(true)" type="radio"
							name="survey_preload" value="true" />Sample Survey
					</div>
				</div>

				<div class="popupFieldLabel">&nbsp;</div>
				<div class="popupFieldLabel">Question:</div>
				<div class="popupFieldInput">
					<textarea rows="5" id="survey_question"
						name="survey_question"></textarea>
				</div>

				<div class="popupFieldLabel">&nbsp;</div>
				<div class="popupFieldLabel">Option 1 Description:</div>
				<div class="popupFieldInput">
					<input type="text" id="survey_option_1_description"
						name="survey_option_1_description" />
				</div>
				<div class="popupFieldLabel">Option 1 Value:</div>
				<div class="popupFieldInput">
					<input type="text" id="survey_option_1_value"
						name="survey_option_1_value" />
				</div>
				
				<div class="popupFieldLabel">&nbsp;</div>
				<div class="popupFieldLabel">Option 2 Description:</div>
				<div class="popupFieldInput">
					<input type="text" id="survey_option_2_description"
						name="survey_option_2_description" />
				</div>
				<div class="popupFieldLabel">Option 2 Value:</div>
				<div class="popupFieldInput">
					<input type="text" id="survey_option_2_value"
						name="survey_option_2_value" />
				</div>

				<div class="popupFieldLabel">&nbsp;</div>
				<div class="popupFieldLabel">Option 3 Description:</div>
				<div class="popupFieldInput">
					<input type="text" id="survey_option_3_description"
						name="survey_option_3_description" />
				</div>
				<div class="popupFieldLabel">Option 3 Value:</div>
				<div class="popupFieldInput">
					<input type="text" id="survey_option_3_value"
						name="survey_option_3_value" />
				</div>

				<div class="popupFieldLabel">&nbsp;</div>
				<div class="popupFieldLabel">Option 4 Description:</div>
				<div class="popupFieldInput">
					<input type="text" id="survey_option_4_description"
						name="survey_option_4_description" />
				</div>
				<div class="popupFieldLabel">Option 4 Value:</div>
				<div class="popupFieldInput">
					<input type="text" id="survey_option_4_value"
						name="survey_option_4_value" />
				</div>

				<div class="popupFieldLabel">&nbsp;</div>
				<div class="popupFieldLabel">Option 5 Description:</div>
				<div class="popupFieldInput">
					<input type="text" id="survey_option_5_description"
						name="survey_option_5_description" />
				</div>
				<div class="popupFieldLabel">Option 5 Value:</div>
				<div class="popupFieldInput">
					<input type="text" id="survey_option_5_value"
						name="survey_option_5_value" />
				</div>

				<div class="popupFieldLabel">Date:</div>
				<div class="popupFieldDateInput" id="survey_datepicker">
					<input type="text" id="survey_date" name="survey_date" /> <br />
				</div>

				<div class="popupSubmitButtons">
					<img src="../resources/images/wait.gif"
						style="display: none; float: right" id="survey_wait" /> <a
						class="cancelButton" href="javascript:void(0)"
						onclick=";">cancel</a> <a id="survey_save_button"
						class="saveButton" href="javascript:void(0)">create ad</a>
				</div>
			</form>
		</div>
		<!-- End Text Ad -->

	</sec:authorize>





	<!-- Begin Wait Div -->
	<div id="wait_div" class="popupViewWait">
		<img src="../resources/images/wait_large.gif" id="wait" />
	</div>
	<!-- End Wait Div -->

</body>
</html>