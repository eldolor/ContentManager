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
<link href="resources/css/cm-table.css" media="screen" rel="stylesheet"
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

<script type="text/javascript" src="resources/js/mavin.user.js"></script>
<script type="text/javascript" src="resources/js/mavin.globals.js"></script>
<script type="text/javascript" src="resources/js/mavin.utilities.js"></script>
<script type="text/javascript" src="resources/js/json2.js"></script>

<script type="text/javascript" src="resources/js/mavin.cm.campaign.js"></script>
<script type="text/javascript" src="resources/js/mavin.cm.adgroup.js"></script>
<script type="text/javascript" src="resources/js/mavin.cm.ad.js"></script>
<script type="text/javascript" src="resources/js/mavin.cm.text.ad.js"></script>
<script type="text/javascript" src="resources/js/mavin.cm.image.ad.js"></script>
<script type="text/javascript" src="resources/js/mavin.cm.video.ad.js"></script>
<script type="text/javascript" src="resources/js/mavin.cm.reports.js"></script>
<script type="text/javascript"
	src="resources/js/mavin.cm.reminder.ad.js"></script>
<script type="text/javascript" src="resources/js/mavin.cm.voice.ad.js"></script>
<script type="text/javascript" src="resources/js/mavin.cm.survey.ad.js"></script>

<script type="text/javascript" src="resources/js/mavin.um.user.js"></script>
<script type="text/javascript" src="resources/js/mavin.um.account.js"></script>



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
<link href="resources/css/demo_table.css"
	rel="stylesheet" type="text/css" />
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
         $(function() {
             $("#campaign_start_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#campaign_start_date'
            });

            $("#campaign_end_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#campaign_end_date'
            });
            
            $("#text_ad_start_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#text_ad_start_date'
            });
            
            $("#text_ad_end_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#text_ad_end_date'
            });

            $("#image_ad_start_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#image_ad_start_date'
            });
            $("#image_ad_end_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#image_ad_end_date'
            });
            
            $("#video_ad_start_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#video_ad_start_date'
            });
            $("#video_ad_end_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#video_ad_end_date'
            });
            
            $("#reminder_ad_start_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#reminder_ad_start_date'
            });
            $("#reminder_ad_end_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#reminder_ad_end_date'
            });
           
            $("#voice_ad_start_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#voice_ad_start_date'
            });
            $("#voice_ad_end_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#voice_ad_end_date'
            });

            $("#survey_start_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#survey_start_date'
            });
            $("#survey_end_date").datepicker( {
                altFormat : 'mm/dd/yy',
                altField : '#survey_end_date'
            });

    		$('#reminder_ad_event_begin_time').datetimepicker({
    			hourGrid : 4,
    			minuteGrid : 10,
    			timeFormat : "hh:mm tt z",
    			showTimezone: true,
    			timezoneIso8601: true
    		});
    		$('#reminder_ad_event_end_time').datetimepicker({
    			hourGrid : 4,
    			minuteGrid : 10,
    			timeFormat : "hh:mm tt z",
    			showTimezone: true,
    			timezoneIso8601: true
    		});
			$("#jquery_jplayer_1").jPlayer(
					{
						swfPath : "resources/js/jquery",
						supplied : "webmv, ogv, m4v",
						size : {
							width : "640px",
							height : "360px",
							cssClass : "jp-video-360p"
						},
						errorAlerts : true, 
						warningAlerts : true
					});
			$("#jquery_jplayer_2").jPlayer({
				swfPath : "resources/js/jquery",
				supplied: "m4a, oga",
				cssSelectorAncestor: "#jp_container_2",
				wmode: "window"
			});
			
			$( document ).tooltip();
			//destroy the modal after its done hiding
			$('#image_ad_div').on('hidden',  function () {
				  $(this).removeData();
				  $("#image_ad_dropbox").empty();
				  $("#image_ad_dropbox").html("<span class=\"message\">Drop images here to upload</span>");
				});
			$('#video_ad_div').on('hidden',  function () {
				  $(this).removeData();
				  $("#video_ad_dropbox").empty();
				  $("#video_ad_dropbox").html("<span class=\"message\">Drop videos here to upload</span>");
				});
			$('#reminder_ad_div').on('hidden',  function () {
				  $(this).removeData();
				  $("#reminder_ad_dropbox").empty();
				  $("#reminder_ad_dropbox").html("<span class=\"message\">Drop images here to upload</span>");
				});
			$('#audio_ad_div').on('hidden',  function () {
				  $(this).removeData();
				  $("#audio_ad_dropbox").empty();
				  $("#audio_ad_dropbox").html("<span class=\"message\">Drop audio files here to upload</span>");
				});
			$('.nav li').click(function(e) {
        	    $('.nav li.active').removeClass('active');
        	    var $this = $(this);
        	    if (!$this.hasClass('active')) {
        	        $this.addClass('active');
        	    }
        	    e.preventDefault();
        	});

        });
         
         
    </script>
</head>

<body onload="screenSetup();">

	<div id="field">
		<div id="main">
			<div class="navbar  navbar-fixed-top ">
				<div class="navbar-inner">
					<div class="container">

						<a class="brand" href="./secured">Campaign Management</a>

						<div class="nav-collapse collapse">
							<ul class="nav">
								<li class="divider-vertical"></li>
								<li id="home" class="active"><a href="./secured">Home</a></li>
								<!-- 
								<li class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#"> Reports <span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li> <a id="reports1" href="#" onclick="getRegisteredUsersReport(this.id)">Registered Users Report</a></li>
										<li> <a id="reports1" href="#" onclick="">User Retention Report</a></li>
										<li> <a id="reports1" href="#" onclick="">User Impressions Report</a></li>
									</ul>
								</li>
								-->
								<li> <a id="dailymetricsrpt" href="#" onclick="getDailyMetricsReport(this.id)">Daily Metrics Report</a></li>
								<!-- SK - Added the viewallads to menu bar -->
								<li><a id="viewAds" href="javascript:void(0);"
									onclick="getAllAds()">View Ads</a></li>
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
						</div>
					</div>
				</div>
			</div>

			<div id="breadcrumbs"></div>
			<span id="main_errors" class="label label-important"></span>

			<div id="test" style="float: left;" />
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

	<!-- Begin Update User -->
	<div class="modal hide fade" id="user_div">

		<div class="modal-header">
			<h3 id="createUserModalLabel">User Setup</h3>
		</div>
		<div class="modal-body">
			<form id="createUserForm" name="createUserForm">
				<input type="hidden" id="user_id" name="user_id" /><input
					type="hidden" id="user_user_name" name="user_user_name" />
				<div class="popupFieldLabel">First Name:</div>
				<div class="popupFieldInput">
					<input type="text" id="user_first_name" name="user_first_name" />
				</div>
				<div class="popupFieldLabel">Last Name:</div>
				<div class="popupFieldInput">
					<input type="text" id="user_last_name" name="user_last_name" />
				</div>
				<div class="popupFieldLabel">Email:</div>
				<div class="popupFieldInput">
					<input type="text" id="user_email" name="user_email" />
				</div>

				<div class="popupFieldLabel">Password:</div>
				<div class="popupFieldInput">
					<input type="password" id="user_password" name="user_password" />
				</div>

				<div class="popupFieldLabel">Re-Type Password:</div>
				<div class="popupFieldInput">
					<input type="password" id="user_password_2" name="user_password_2" />
				</div>


			</form>
		</div>
		<div class="modal-footer">
			<label id="user_errors" class="label-warning"></label> <img
				src="resources/images/wait.gif" style="display: none; float: right"
				id="user_wait" />
			<button id="user_cancel_button" class="btn" data-dismiss="modal"
				aria-hidden="true">cancel</button>
			<button id="user_save_button" class="btn btn-primary">create
				user</button>
		</div>
	</div>
	<!-- End Update User -->

	<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_USER">


		<!-- Begin Campaign -->
		<div class="modal hide fade" id="campaign_div">
			<form id="campaignForm" name="campaignForm" class="form-horizontal">
				<input type="hidden" id="campaign_id" name="campaign_id" /> <input
					type="hidden" id="campaign_traffic_targetting"
					name="campaign_traffic_targetting" /> <input type="hidden"
					id="campaign_gender_targetting" name="campaign_gender_targetting" />

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="textAdModalLabel">Campaign Setup</h3>
				</div>
				<div class="modal-body">
					<div id="campaign_setup_header">
						<div class="popupFieldLabel">Start with...</div>
						<div class="popupFieldLabel">
							<label class="radio"> <input checked="checked"
								onclick="toggleCampaignType(false)" type="radio"
								name="campaign_preload" value="false" />New Campaign<br />
							</label><label class="radio"><input
								onclick="toggleCampaignType(true)" type="radio"
								name="campaign_preload" value="true" />Sample Campaign</label>
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
						Campaign <span class="label label-info" id="campaign_status">Enabled</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="campaign_enabled" name="campaign_enabled" checked="checked" /></label>
					</div>
					<div class="popupFieldLabel">&nbsp;</div>

					<h3>Targetting:</h3>
					<div class="popupFieldLabel">Interest Categories:</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="campaign_interest_category_targetting"
							name="campaign_interest_category_targetting" value="all" />Target
							all interest categories</label>
					</div>

					<!-- The values are based on standard contract -->
					<div class="popupFieldLabel"
						id="campaign_interest_category_targetting_selection_div">
						<label class="checkbox"><input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="moviesMusicEvents" />Movies, Music &amp; Events </label><label
							class="checkbox"> <input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="restaurantsSpa" />Restaurants &amp; Spa
						</label><label class="checkbox"><input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="education" />Education </label><label class="checkbox"> <input
							type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="newsReports" />News &amp; Reports
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="sports" />Sports
						</label> <label class="checkbox"> <input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="scienceTechnology" />Science &amp; Technology
						</label> <label class="checkbox"> <input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="business" />Business
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="healthDiet" />Health &amp; Diet
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="automobile" />Automobile
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="fashionLifestyle" />Fashion &amp; Lifestyle
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="travel" />Travel
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="foodRecipies" />Food &amp; Recipes
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_interest_category_targetting_selection[]"
							name="campaign_interest_category_targetting_selection[]"
							value="games" />Games
						</label>
					</div>

					<div class="popupFieldLabel">&nbsp;</div>

					<div class="popupFieldLabel">Geography:</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="campaign_country_targetting"
							name="campaign_country_targetting" value="all" />Target all
							geographic locations</label>
					</div>

					<!-- The values are based on ISO 3166-1 alpha-3 standard -->
					<div class="popupFieldLabel"
						id="campaign_country_targetting_selection_div">
						<label class="checkbox"><input type="checkbox"
							id="campaign_country_targetting_selection[]"
							name="campaign_country_targetting_selection[]" value="USA" />United
							States </label><label class="checkbox"> <input type="checkbox"
							id="campaign_country_targetting_selection[]"
							name="campaign_country_targetting_selection[]" value="CAN" />Canada
						</label><label class="checkbox"><input type="checkbox"
							id="campaign_country_targetting_selection[]"
							name="campaign_country_targetting_selection[]" value="IND" />India
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_country_targetting_selection[]"
							name="campaign_country_targetting_selection[]" value="BRA" />Brazil
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_country_targetting_selection[]"
							name="campaign_country_targetting_selection[]" value="FRA" />France
						</label>
					</div>

					<div class="popupFieldLabel">&nbsp;</div>

					<div class="popupFieldLabel">
						<label class="radio"><input type="radio"
							name="campaign_traffic_targetting_selection"
							onclick="toggleCampaignTrafficTargetting('all')" value="all" />Target
							all traffic, including Wi-Fi traffic<br /></label><label class="radio">
							<input type="radio"
							onclick="toggleCampaignTrafficTargetting('wifi')"
							name="campaign_traffic_targetting_selection" value="wifi" />Target
							Wi-Fi traffic
						</label><label class="radio"><input type="radio"
							onclick="toggleCampaignTrafficTargetting('mobile_operator')"
							name="campaign_traffic_targetting_selection"
							value="mobile_operator" />Target mobile operator traffic </label>
					</div>

					<div class="popupFieldLabel">&nbsp;</div>
					<div class="popupFieldLabel">Gender:</div>
					<div class="popupFieldLabel">
						<label class="radio"><input type="radio"
							name="campaign_gender_targetting_selection"
							onclick="toggleCampaignGenderTargetting('all')" value="all" />All
							Users</label><label class="radio"><input type="radio"
							name="campaign_gender_targetting_selection"
							onclick="toggleCampaignGenderTargetting('male')" value="male" />Male
							users only</label><label class="radio"> <input type="radio"
							name="campaign_gender_targetting_selection"
							onclick="toggleCampaignGenderTargetting('female')" value="female" />Female
							users only
						</label>
					</div>
					<div class="popupFieldLabel">&nbsp;</div>
					<div class="popupFieldLabel">Age Groups:</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="campaign_age_targetting" name="campaign_age_targetting"
							value="all" />All age groups</label>
					</div>
					<div class="popupFieldLabel"
						id="campaign_age_targetting_selection_div">
						<label class="checkbox"><input type="checkbox"
							id="campaign_age_targetting_selection[]"
							name="campaign_age_targetting_selection[]" value="18-24" />18-24
						</label><label class="checkbox"><input type="checkbox"
							id="campaign_age_targetting_selection[]"
							name="campaign_age_targetting_selection[]" value="25-34" />25-34
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_age_targetting_selection[]"
							name="campaign_age_targetting_selection[]" value="35-44" />35-44
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_age_targetting_selection[]"
							name="campaign_age_targetting_selection[]" value="45-54" />45-54
						</label><label class="checkbox">> <input type="checkbox"
							id="campaign_age_targetting_selection[]"
							name="campaign_age_targetting_selection[]" value="55-64" />55-64
						</label><label class="checkbox"> <input type="checkbox"
							id="campaign_age_targetting_selection[]"
							name="campaign_age_targetting_selection[]" value="65+" />65+
						</label>
					</div>

					<div class="popupFieldLabel">&nbsp;</div>

				</div>
				<div class="modal-footer">
					<span id="campaign_errors" class="label label-important"></span> <img
						src="resources/images/wait.gif"
						style="display: none; float: right" id="campaign_wait" />
					<button id="campaign_cancel_button" class="btn"
						data-dismiss="modal" aria-hidden="true">cancel</button>
					<button id="campaign_save_button" class="btn btn-primary"
						data-loading-text="Creating...">create campaign</button>
				</div>
			</form>
		</div>
		<!-- End Campaign -->

		<!-- Begin Ad Group -->
		<div class="modal hide fade" id="adgroup_div" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="adGroupModalLabel">Ad Group Setup</h3>
			</div>
			<div class="modal-body">
				<form id="adGroupForm" name="adGroupForm">
					<input type="hidden" id="adgroup_id" name="adgroup_id" /> <input
						type="hidden" id="adgroup_campaign_id" name="adgroup_campaign_id" />


					<div id="adgroup_setup_header">
						<div class="popupFieldLabel">Start with...</div>
						<div class="popupFieldLabel">
							<label class="radio"><input checked="checked"
								onclick="toggleAdGroupType(false)" type="radio"
								name="adgroup_preload" value="false" />New Ad Group</label><label
								class="radio"> <input onclick="toggleAdGroupType(true)"
								type="radio" name="adgroup_preload" value="true" />Sample Ad
								Group
							</label>
						</div>
					</div>

					<div class="popupFieldLabel">Name:</div>
					<div class="popupFieldInput">
						<input type="text" id="adgroup_name" name="adgroup_name" />
					</div>

					<div class="popupFieldLabel">Description:</div>
					<div class="popupFieldInput">
						<textarea rows="5" id="adgroup_description"
							name="adgroup_description"></textarea>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<span id="adgroup_errors" class="label label-important"></span> <img
					src="resources/images/wait.gif" style="display: none; float: right"
					id="adgroup_wait" />
				<button id="adgroup_cancel_button" class="btn" data-dismiss="modal"
					aria-hidden="true">cancel</button>
				<button id="adgroup_save_button" class="btn btn-primary">create
					ad group</button>
			</div>
		</div>
		<!-- End Ad Group -->

		<!-- Begin Ad Type-->
		<div class="modal hide fade" id="ad_type_div" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="adTypeModalLabel">Select Ad Type</h3>
			</div>
			<div class="modal-body">
				<form id="adTypeForm" name="adTypeForm">
					<input type="hidden" id="ad_type_campaign_id"
						name="ad_type_campaign_id" /> <input type="hidden"
						id="ad_type_adgroup_id" name="ad_type_adgroup_id" />
					<!-- Default to text ad -->
					<input type="hidden" id="ad_type_selected" name="ad_type_selected"
						value="text_ad" /> </span>
					<div id="ad_type_setup_header">
						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="ad_type_preload" value="text_ad" />Text Ad </label><label
								class="radio"><input type="radio" name="ad_type_preload"
								value="image_ad" />Image Ad</label><label class="radio"> <input
								type="radio" name="ad_type_preload" value="video_ad" />Video Ad
							</label><label class="radio"><input type="radio"
								name="ad_type_preload" value="reminder_ad" />Reminder Ad</label><label
								class="radio"> <input type="radio"
								name="ad_type_preload" value="voice_ad" />Voice Ad
							</label><label class="radio"><input type="radio"
								name="ad_type_preload" value="survey" />Survey</label>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<span id="ad_type_errors" class="label label-important"></span><img
					src="resources/images/wait.gif" style="display: none; float: right"
					id="ad_type_wait" />
				<button id="ad_type_cancel_button" class="btn" data-dismiss="modal"
					aria-hidden="true">cancel</button>
				<button id="ad_type_continue_button" class="btn btn-primary">continue</button>
			</div>
		</div>
		<!-- End Ad Type-->

		<!-- Begin Text Ad -->
		<div class="modal hide fade" id="text_ad_div" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="textAdModalLabel">Ad Setup</h3>
			</div>
			<div class="modal-body">
				<form id="textAdForm" name="textAdForm">
					<input type="hidden" id="text_ad_id" name="text_ad_id" /> <input
						type="hidden" id="text_ad_campaign_id" name="text_ad_campaign_id" /><input
						type="hidden" id="text_ad_adgroup_id" name="text_ad_adgroup_id" /><input
						type="hidden" id="text_ad_traffic_targetting"
						name="text_ad_traffic_targetting" /> <input type="hidden"
						id="text_ad_gender_targetting" name="text_ad_gender_targetting" />


					<div id="text_ad_setup_header">
						<div class="popupFieldLabel">Start with...</div>
						<div class="popupFieldLabel">
							<label class="radio"><input checked="checked"
								onclick="toggleTextAdType(false)" type="radio"
								name="text_ad_preload" value="false" />New Ad</label><label
								class="radio"> <input onclick="toggleTextAdType(true)"
								type="radio" name="text_ad_preload" value="true" />Sample Ad
							</label>
						</div>
					</div>

					<div class="popupFieldLabel">Name:</div>
					<div class="popupFieldInput">
						<input type="text" id="text_ad_name" name="text_ad_name" />
					</div>

					<div class="popupFieldLabel">Description:</div>
					<div class="popupFieldInput">
						<input type="text" id="text_ad_description"
							name="text_ad_description" />
					</div>

					<div class="popupFieldLabel">Headline:</div>
					<div class="popupFieldInput">
						<input type="text" id="text_ad_headline" name="text_ad_headline" />
					</div>

					<div class="popupFieldLabel">Line 1:</div>
					<div class="popupFieldInput">
						<input type="text" id="text_ad_line1" name="text_ad_line1" />
					</div>

					<div class="popupFieldLabel">Line 2:</div>
					<div class="popupFieldInput">
						<input type="text" id="text_ad_line2" name="text_ad_line2" />
					</div>

					<div class="popupFieldLabel">Display URL:</div>
					<div class="popupFieldInput">
						<input type="text" id="text_ad_display_uri"
							name="text_ad_display_uri" />
					</div>

					<div class="popupFieldLabel">Destination URL:</div>
					<div class="popupFieldInput">
						<input type="text" id="text_ad_destination_uri"
							name="text_ad_destination_uri" />
					</div>

					<div class="popupFieldLabel">Start Date:</div>
					<div class="popupFieldDateInput" id="text_ad_start_datepicker">
						<input type="text" id="text_ad_start_date"
							name="text_ad_start_date" /> <br />
					</div>
					<div class="popupFieldLabel">End Date:</div>
					<div class="popupFieldDateInput" id="text_ad_end_datepicker">
						<input type="text" id="text_ad_end_date" name="text_ad_end_date" />
						<br />
					</div>

					<div class="popupFieldLabel">
						Ad <span class="label label-info" id="text_ad_status">Enabled</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="text_ad_enabled" name="text_ad_enabled" checked="checked" /></label>
					</div>

					<div class="popupFieldLabel">Cost Per Impression:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="text_ad_cpi" name="text_ad_cpi"
							title="Cost per text ad impression" />
					</div>

					<div class="popupFieldLabel">Cost Per Click:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="text_ad_cpc" name="text_ad_cpc"
							title="Cost per text ad click" />
					</div>

					<div class="popupFieldLabel">Frequency Capping:</div>
					<div class="popupFieldInput">
						<input type="text" id="text_ad_frequency_capping"
							name="text_ad_frequency_capping"
							title="The number of ad impressions per day limit" />
					</div>

					<div class="popupFieldLabel">Target Applications:</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="text_ad_target_application[]"
							name="text_ad_target_application[]" value="Contacts" />Contacts
						</label><label class="checkbox"> <input type="checkbox"
							id="text_ad_target_application[]"
							name="text_ad_target_application[]" value="Mms" />Mms
						</label><label class="checkbox"> <input type="checkbox"
							id="text_ad_target_application[]"
							name="text_ad_target_application[]" value="Dialer" />Dialer
						</label><label class="checkbox"> <input type="checkbox"
							id="text_ad_target_application[]"
							name="text_ad_target_application[]" value="ChargeScreen" />Charge
							Screen
						</label><label class="checkbox"> <input type="checkbox"
							id="text_ad_target_application[]"
							name="text_ad_target_application[]" value="Wallpaper" />Wallpaper
						</label>
					</div>

					<div class="popupFieldLabel">&nbsp;</div>
					<h3>Targetting:</h3>
					<div class="popupFieldLabel">
						Override Targetting</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="text_ad_targetting_overridden"
							name="text_ad_targetting_overridden" /></label>
					</div>

					<div id="text_ad_targetting_div">
						<div class="popupFieldLabel">Interest Categories:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="text_ad_interest_category_targetting"
								name="text_ad_interest_category_targetting" value="all" />Target
								all interest categories</label>
						</div>

						<!-- The values are based on standard contract -->
						<div class="popupFieldLabel"
							id="text_ad_interest_category_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="moviesMusicEvents" />Movies, Music &amp; Events </label><label
								class="checkbox"> <input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="restaurantsSpa" />Restaurants &amp; Spa
							</label><label class="checkbox"><input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="education" />Education </label><label class="checkbox">
								<input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="newsReports" />News &amp; Reports
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="sports" />Sports
							</label> <label class="checkbox"> <input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="scienceTechnology" />Science &amp; Technology
							</label> <label class="checkbox"> <input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="business" />Business
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="healthDiet" />Health &amp; Diet
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="automobile" />Automobile
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="fashionLifestyle" />Fashion &amp; Lifestyle
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="travel" />Travel
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="foodRecipies" />Food &amp; Recipes
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_interest_category_targetting_selection[]"
								name="text_ad_interest_category_targetting_selection[]"
								value="games" />Games
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">Geography:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="text_ad_country_targetting"
								name="text_ad_country_targetting" value="all" />Target all
								geographic locations</label>
						</div>

						<!-- The values are based on ISO 3166-1 alpha-3 standard -->
						<div class="popupFieldLabel"
							id="text_ad_country_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="text_ad_country_targetting_selection[]"
								name="text_ad_country_targetting_selection[]" value="USA" />United
								States </label><label class="checkbox"> <input type="checkbox"
								id="text_ad_country_targetting_selection[]"
								name="text_ad_country_targetting_selection[]" value="CAN" />Canada
							</label><label class="checkbox"><input type="checkbox"
								id="text_ad_country_targetting_selection[]"
								name="text_ad_country_targetting_selection[]" value="IND" />India
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_country_targetting_selection[]"
								name="text_ad_country_targetting_selection[]" value="BRA" />Brazil
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_country_targetting_selection[]"
								name="text_ad_country_targetting_selection[]" value="FRA" />France
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="text_ad_traffic_targetting_selection"
								onclick="toggleTextAdTrafficTargetting('all')" value="all" />Target
								all traffic, including Wi-Fi traffic<br /></label><label class="radio">
								<input type="radio"
								onclick="toggleTextAdTrafficTargetting('wifi')"
								name="text_ad_traffic_targetting_selection" value="wifi" />Target
								Wi-Fi traffic
							</label><label class="radio"><input type="radio"
								onclick="toggleTextAdTrafficTargetting('mobile_operator')"
								name="text_ad_traffic_targetting_selection"
								value="mobile_operator" />Target mobile operator traffic </label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Gender:</div>
						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="text_ad_gender_targetting_selection"
								onclick="toggleTextAdGenderTargetting('all')" value="all" />All
								Users</label><label class="radio"><input type="radio"
								name="text_ad_gender_targetting_selection"
								onclick="toggleTextAdGenderTargetting('male')" value="male" />Male
								users only</label><label class="radio"> <input type="radio"
								name="text_ad_gender_targetting_selection"
								onclick="toggleTextAdGenderTargetting('female')" value="female" />Female
								users only
							</label>
						</div>
						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Age Groups:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="text_ad_age_targetting" name="text_ad_age_targetting"
								value="all" />All age groups</label>
						</div>
						<div class="popupFieldLabel"
							id="text_ad_age_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="text_ad_age_targetting_selection[]"
								name="text_ad_age_targetting_selection[]" value="18-24" />18-24
							</label><label class="checkbox"><input type="checkbox"
								id="text_ad_age_targetting_selection[]"
								name="text_ad_age_targetting_selection[]" value="25-34" />25-34
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_age_targetting_selection[]"
								name="text_ad_age_targetting_selection[]" value="35-44" />35-44
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_age_targetting_selection[]"
								name="text_ad_age_targetting_selection[]" value="45-54" />45-54
							</label><label class="checkbox">> <input type="checkbox"
								id="text_ad_age_targetting_selection[]"
								name="text_ad_age_targetting_selection[]" value="55-64" />55-64
							</label><label class="checkbox"> <input type="checkbox"
								id="text_ad_age_targetting_selection[]"
								name="text_ad_age_targetting_selection[]" value="65+" />65+
							</label>
						</div>
					</div>
					<div class="popupFieldLabel">&nbsp;</div>

				</form>
			</div>
			<div class="modal-footer">
				<span id="text_ad_errors" class="label label-important"></span> <img
					src="resources/images/wait.gif" style="display: none; float: right"
					id="text_ad_wait" />
				<button id="text_ad_cancel_button" class="btn" data-dismiss="modal"
					aria-hidden="true">cancel</button>
				<button id="text_ad_save_button" class="btn btn-primary">create
					ad</button>
			</div>
		</div>
		<!-- End Text Ad -->


		<!-- Begin Image Ad -->
		<div class="modal hide fade" id="image_ad_div" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">Ad Setup</h3>
			</div>
			<div id="image_ad_div_body" class="modal-body">
				<form id="imageAdForm" name="imageAdForm">
					<input type="hidden" id="image_ad_id" name="image_ad_id" /><input
						type="hidden" id="image_ad_campaign_id"
						name="image_ad_campaign_id" /> <input type="hidden"
						id="image_ad_adgroup_id" name="image_ad_adgroup_id" /> <input
						type="hidden" id="image_ad_uri" name="image_ad_uri" /><input
						type="hidden" id="image_ad_traffic_targetting"
						name="image_ad_traffic_targetting" /> <input type="hidden"
						id="image_ad_gender_targetting" name="image_ad_gender_targetting" />
					<div id="image_ad_setup_header">
						<div class="popupFieldLabel">Start with...</div>
						<div class="popupFieldLabel">
							<label class="radio"><input checked="checked"
								onclick="toggleImageAdType(false)" type="radio"
								name="image_ad_preload" value="false" />New Ad</label><label
								class="radio"> <input onclick="toggleImageAdType(true)"
								type="radio" name="image_ad_preload" value="true" />Sample Ad
							</label>
						</div>
					</div>

					<div class="popupFieldLabel">Name:</div>
					<div class="popupFieldInput">
						<input type="text" id="image_ad_name" name="image_ad_name" />
					</div>

					<div class="popupFieldLabel">Description:</div>
					<div class="popupFieldInput">
						<textarea rows="5" id="image_ad_description"
							name="image_ad_description"></textarea>
					</div>

					<div class="popupFieldLabel">
						Video URL:&nbsp;&nbsp;<a id="link_video_ad" name="link_video_ad">[Link
							Video Ad]</a>
					</div>
					<div class="popupFieldLabel" id="video_ads_for_linking"></div>

					<div class="popupFieldInput">
						<input type="text" id="image_ad_video_ad_id"
							name="image_ad_video_ad_id" title="Ad click will play this video" />
					</div>

					<div class="popupFieldLabel">Learn More URL:</div>
					<div class="popupFieldInput">
						<input type="text" id="image_ad_learn_more_uri"
							name="image_ad_learn_more_uri"
							title="Ad click will take the user to this website" />
					</div>

					<div class="popupFieldLabel">
						Survey:&nbsp;&nbsp;<a id="link_survey" name="link_survey">[Link
							Survey]</a>
					</div>
					<div class="popupFieldLabel" id="surveys_for_linking"></div>
					<div class="popupFieldInput">
						<input type="text" id="image_ad_survey_id"
							name="image_ad_survey_id"
							title="Ad click will present this survey" />
					</div>

					<div class="popupFieldLabel">&nbsp;</div>

					<div class="popupFieldLabel">Start Date:</div>
					<div class="popupFieldDateInput" id="image_ad_start_datepicker">
						<input type="text" id="image_ad_start_date"
							name="image_ad_start_date" /> <br />
					</div>
					<div class="popupFieldLabel">End Date:</div>
					<div class="popupFieldDateInput" id="image_ad_end_datepicker">
						<input type="text" id="image_ad_end_date" name="image_ad_end_date" />
						<br />
					</div>
					<div class="popupFieldLabel">
						Ad <span class="label label-info" id="image_ad_status">Enabled</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="image_ad_enabled" name="image_ad_enabled" checked="checked" /></label>
					</div>

					<div class="popupFieldLabel">Cost Per Impression:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="image_ad_cpi" name="image_ad_cpi"
							title="Cost per image ad impression" />
					</div>

					<div class="popupFieldLabel">Cost Per Click:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="image_ad_cpc" name="image_ad_cpc"
							title="Cost per image ad click" />
					</div>

					<div class="popupFieldLabel">Frequency Capping:</div>
					<div class="popupFieldInput">
						<input type="text" id="image_ad_frequency_capping"
							name="image_ad_frequency_capping"
							title="The number of ad impressions per day limit" />
					</div>

					<div class="popupFieldLabel">Target Applications:</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="image_ad_target_application[]"
							name="image_ad_target_application[]" value="Contacts"
							title="Valid banner size is 320x50 for phones without HD support, and 640x100 for HD phones" />Contacts
						</label><label class="checkbox"> <input type="checkbox"
							id="image_ad_target_application[]"
							name="image_ad_target_application[]" value="Mms"
							title="Valid banner size is 320x50 for phones without HD support, and 640x100 for HD phones" />Mms
						</label><label class="checkbox"> <input type="checkbox"
							id="image_ad_target_application[]"
							name="image_ad_target_application[]" value="Dialer"
							title="Valid banner size is 320x50 for phones without HD support, and 640x100 for HD phones" />Dialer
						</label><label class="checkbox"> <input type="checkbox"
							id="image_ad_target_application[]"
							name="image_ad_target_application[]" value="ChargeScreen"
							title="Valid banner size is 480x800 for a 4.x&quot; screen" />Charge
							Screen
						</label><label class="checkbox"> <input type="checkbox"
							id="image_ad_target_application[]"
							name="image_ad_target_application[]" value="Wallpaper"
							title="Valid banner size is 480x800 for a 4.x&quot; screen" />Wallpaper
						</label> <label class="checkbox"> <input type="checkbox"
							id="image_ad_target_application[]"
							name="image_ad_target_application[]" value="LockScreen"
							title="Valid banner size is 480x800 for a 4.x&quot; screen" />Lock
							Screen
						</label>
					</div>

					<div class="popupFieldLabel">&nbsp;</div>

					<span id="image_ad_banner_size_message"
						class="label label-important"></span>
					<div class="popupFieldLabel">Banner Size:</div>

					<div class="popupFieldLabel">
						<select id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" tabindex="1">
							<option value="">--Select--</option>
							<option value="480x800">480x800 (Full Screen)</option>
							<option value="300x250">300x250 (Medium Rectangle)</option>
							<option value="320x50">320x50 (Banner)</option>
							<option value="640x100">640x100(HD Banner)</option>
						</select>
					</div>
					<!-- 					<div class="popupFieldLabel">
						default it to 300x50
						<label class="radio inline"> <input type="radio"
							id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" value="1920x1200" />1920x1200
						</label><label class="radio inline"> <input type="radio"
							id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" value="960x720" />960x720
						</label><label class="radio inline"> <input type="radio"
							id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" value="720x1280" />720x1280
						</label><label class="radio inline"> <input type="radio"
							id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" value="640x480" />640x480
						</label><label class="radio inline"> <input type="radio"
							id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" value="480x800" />480x800
						</label><label class="radio inline"> <input type="radio"
							id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" value="480x320" />480x320
						</label><label class="radio inline"> <input type="radio"
							id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" value="320x480" />320x480
						</label><label class="radio inline"> <input type="radio"
							id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" value="300x50" />300x50
						</label><label class="radio inline"> <input type="radio"
							id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" value="216x36" />216x36
						</label><label class="radio inline"> <input type="radio"
							id="image_ad_banner_size_selection"
							name="image_ad_banner_size_selection" value="168x28" />168x28
						</label>
					</div> -->

					<div class="popupFieldLabel">&nbsp;</div>
					<button id="view_ad_image" type="button" class="btn btn-inverse"
						data-loading-text="Loading...">View Ad Image</button>
					<div id="view_image">
						<img class="popupViewImage img-rounded" id="ad_image" />
					</div>
					<div class="popupFieldLabel">&nbsp;</div>
					<div class="popupFieldLabel">
						<span class="label label-info"
							id="image_ad_externally_served_status">Ad Internally
							Served</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="image_ad_externally_served" name="image_ad_externally_served" /></label>
					</div>

					<div class="popupFieldLabel" id="image_ad_external_uri_label">External
						URL:</div>
					<div class="popupFieldInput">
						<input type="text" id="image_ad_external_uri"
							name="image_ad_external_uri"
							title="Ad image will be downloaded from this url" />
					</div>

					<div class="popupFieldLabel">&nbsp;</div>

					<h3>Targetting:</h3>
					<div class="popupFieldLabel">
						Override Targetting</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="image_ad_targetting_overridden"
							name="image_ad_targetting_overridden" /></label>
					</div>

					<div id="image_ad_targetting_div">
						<div class="popupFieldLabel">Interest Categories:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="image_ad_interest_category_targetting"
								name="image_ad_interest_category_targetting" value="all" />Target
								all interest categories</label>
						</div>

						<!-- The values are based on standard contract -->
						<div class="popupFieldLabel"
							id="image_ad_interest_category_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="moviesMusicEvents" />Movies, Music &amp; Events </label><label
								class="checkbox"> <input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="restaurantsSpa" />Restaurants &amp; Spa
							</label><label class="checkbox"><input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="education" />Education </label><label class="checkbox">
								<input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="newsReports" />News &amp; Reports
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="sports" />Sports
							</label> <label class="checkbox"> <input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="scienceTechnology" />Science &amp; Technology
							</label> <label class="checkbox"> <input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="business" />Business
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="healthDiet" />Health &amp; Diet
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="automobile" />Automobile
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="fashionLifestyle" />Fashion &amp; Lifestyle
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="travel" />Travel
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="foodRecipies" />Food &amp; Recipes
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_interest_category_targetting_selection[]"
								name="image_ad_interest_category_targetting_selection[]"
								value="games" />Games
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">Geography:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="image_ad_country_targetting"
								name="image_ad_country_targetting" value="all" />Target all
								geographic locations</label>
						</div>

						<!-- The values are based on ISO 3166-1 alpha-3 standard -->
						<div class="popupFieldLabel"
							id="image_ad_country_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="image_ad_country_targetting_selection[]"
								name="image_ad_country_targetting_selection[]" value="USA" />United
								States </label><label class="checkbox"> <input type="checkbox"
								id="image_ad_country_targetting_selection[]"
								name="image_ad_country_targetting_selection[]" value="CAN" />Canada
							</label><label class="checkbox"><input type="checkbox"
								id="image_ad_country_targetting_selection[]"
								name="image_ad_country_targetting_selection[]" value="IND" />India
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_country_targetting_selection[]"
								name="image_ad_country_targetting_selection[]" value="BRA" />Brazil
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_country_targetting_selection[]"
								name="image_ad_country_targetting_selection[]" value="FRA" />France
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="image_ad_traffic_targetting_selection"
								onclick="toggleImageAdTrafficTargetting('all')" value="all" />Target
								all traffic, including Wi-Fi traffic<br /></label><label class="radio">
								<input type="radio"
								onclick="toggleImageAdTrafficTargetting('wifi')"
								name="image_ad_traffic_targetting_selection" value="wifi" />Target
								Wi-Fi traffic
							</label><label class="radio"><input type="radio"
								onclick="toggleImageAdTrafficTargetting('mobile_operator')"
								name="image_ad_traffic_targetting_selection"
								value="mobile_operator" />Target mobile operator traffic </label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Gender:</div>
						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="image_ad_gender_targetting_selection"
								onclick="toggleImageAdGenderTargetting('all')" value="all" />All
								Users</label><label class="radio"><input type="radio"
								name="image_ad_gender_targetting_selection"
								onclick="toggleImageAdGenderTargetting('male')" value="male" />Male
								users only</label><label class="radio"> <input type="radio"
								name="image_ad_gender_targetting_selection"
								onclick="toggleImageAdGenderTargetting('female')" value="female" />Female
								users only
							</label>
						</div>
						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Age Groups:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="image_ad_age_targetting" name="image_ad_age_targetting"
								value="all" />All age groups</label>
						</div>
						<div class="popupFieldLabel"
							id="image_ad_age_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="image_ad_age_targetting_selection[]"
								name="image_ad_age_targetting_selection[]" value="18-24" />18-24
							</label><label class="checkbox"><input type="checkbox"
								id="image_ad_age_targetting_selection[]"
								name="image_ad_age_targetting_selection[]" value="25-34" />25-34
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_age_targetting_selection[]"
								name="image_ad_age_targetting_selection[]" value="35-44" />35-44
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_age_targetting_selection[]"
								name="image_ad_age_targetting_selection[]" value="45-54" />45-54
							</label><label class="checkbox">> <input type="checkbox"
								id="image_ad_age_targetting_selection[]"
								name="image_ad_age_targetting_selection[]" value="55-64" />55-64
							</label><label class="checkbox"> <input type="checkbox"
								id="image_ad_age_targetting_selection[]"
								name="image_ad_age_targetting_selection[]" value="65+" />65+
							</label>
						</div>
					</div>
					<div class="popupFieldLabel">&nbsp;</div>

					<button id="upload_ad_image" type="button" class="btn btn-inverse"
						data-toggle="button">Upload Image</button>
					<div id="image_ad_dropbox">
						<span class="message">Drop images here to upload</span>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<span id="image_ad_errors" class="label label-important"></span> <img
					src="resources/images/wait.gif" style="display: none; float: right"
					id="image_ad_wait" />
				<button id="image_ad_cancel_button" class="btn" data-dismiss="modal"
					aria-hidden="true">cancel</button>
				<button id="image_ad_save_button" class="btn btn-primary">create
					ad</button>
			</div>


		</div>
		<!-- <div class="popup" id="link_video_ad_div" /> -->

		<!-- End Image Ad -->

		<!-- Begin Video Ad -->
		<div class="modal hide fade" id="video_ad_div" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="videoAdModalLabel">Ad Setup</h3>
			</div>
			<div class="modal-body">
				<form id="videoAdForm" name="videoAdForm">
					<input type="hidden" id="video_ad_id" name="video_ad_id" /> <input
						type="hidden" id="video_ad_campaign_id"
						name="video_ad_campaign_id" /><input type="hidden"
						id="video_ad_adgroup_id" name="video_ad_adgroup_id" /> <input
						type="hidden" id="video_ad_uri" name="video_ad_uri" /><input
						type="hidden" id="video_ad_traffic_targetting"
						name="video_ad_traffic_targetting" /> <input type="hidden"
						id="video_ad_gender_targetting" name="video_ad_gender_targetting" />
					<div id="video_ad_setup_header">
						<div class="popupFieldLabel">Start with...</div>
						<div class="popupFieldLabel">
							<label class="radio"><input checked="checked"
								onclick="toggleVideoAdType(false)" type="radio"
								name="video_ad_preload" value="false" />New Ad</label><label
								class="radio"> <input onclick="toggleVideoAdType(true)"
								type="radio" name="video_ad_preload" value="true" />Sample Ad
							</label>
						</div>
					</div>

					<div class="popupFieldLabel">Name:</div>
					<div class="popupFieldInput">
						<input type="text" id="video_ad_name" name="video_ad_name" />
					</div>

					<div class="popupFieldLabel">Description:</div>
					<div class="popupFieldInput">
						<textarea rows="5" id="video_ad_description"
							name="video_ad_description"></textarea>
					</div>

					<div class="popupFieldLabel">Learn More URL:</div>
					<div class="popupFieldInput">
						<input type="text" id="video_ad_learn_more_uri"
							name="video_ad_learn_more_uri"
							title="Ad click will take the user to this website" />
					</div>

					<div class="popupFieldLabel">Start Date:</div>
					<div class="popupFieldDateInput" id="video_ad_start_datepicker">
						<input type="text" id="video_ad_start_date"
							name="video_ad_start_date" /> <br />
					</div>
					<div class="popupFieldLabel">End Date:</div>
					<div class="popupFieldDateInput" id="video_ad_end_datepicker">
						<input type="text" id="video_ad_end_date" name="video_ad_end_date" />
						<br />
					</div>
					<div class="popupFieldLabel">
						Ad <span class="label label-info" id="video_ad_status">Enabled</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="video_ad_enabled" name="video_ad_enabled" checked="checked" /></label>
					</div>

					<div class="popupFieldLabel">Cost Per Impression:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="video_ad_cpi" name="video_ad_cpi"
							title="Cost per video ad impression" />
					</div>

					<div class="popupFieldLabel">Cost Per Click:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="video_ad_cpc" name="video_ad_cpc"
							title="Cost per video ad click" />
					</div>

					<div class="popupFieldLabel">Frequency Capping:</div>
					<div class="popupFieldInput">
						<input type="text" id="video_ad_frequency_capping"
							name="video_ad_frequency_capping"
							title="The number of ad impressions per day limit" />
					</div>

					<div class="popupFieldLabel">Target Applications:</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="video_ad_target_application[]"
							name="video_ad_target_application[]" value="Contacts" />Contacts
						</label><label class="checkbox"> <input type="checkbox"
							id="video_ad_target_application[]"
							name="video_ad_target_application[]" value="Mms" />Mms
						</label><label class="checkbox"> <input type="checkbox"
							id="video_ad_target_application[]"
							name="video_ad_target_application[]" value="Dialer" />Dialer
						</label><label class="checkbox"> <input type="checkbox"
							id="video_ad_target_application[]"
							name="video_ad_target_application[]" value="ChargeScreen" />Charge
							Screen
						</label><label class="checkbox"> <input type="checkbox"
							id="video_ad_target_application[]"
							name="video_ad_target_application[]" value="Wallpaper" />Wallpaper
						</label>
					</div>

					<div class="popupFieldLabel">&nbsp;</div>

					<button id="upload_ad_video" type="button" class="btn btn-inverse"
						data-toggle="button">Upload Video</button>
					<div id="video_ad_dropbox">
						<span class="message">Drop videos here to upload</span>
					</div>
					<div class="popupFieldLabel">&nbsp;</div>

					<h3>Targetting:</h3>
					<div class="popupFieldLabel">
						Override Targetting</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="video_ad_targetting_overridden"
							name="video_ad_targetting_overridden" /></label>
					</div>

					<div id="video_ad_targetting_div">
						<div class="popupFieldLabel">Interest Categories:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="video_ad_interest_category_targetting"
								name="video_ad_interest_category_targetting" value="all" />Target
								all interest categories</label>
						</div>

						<!-- The values are based on standard contract -->
						<div class="popupFieldLabel"
							id="video_ad_interest_category_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="moviesMusicEvents" />Movies, Music &amp; Events </label><label
								class="checkbox"> <input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="restaurantsSpa" />Restaurants &amp; Spa
							</label><label class="checkbox"><input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="education" />Education </label><label class="checkbox">
								<input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="newsReports" />News &amp; Reports
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="sports" />Sports
							</label> <label class="checkbox"> <input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="scienceTechnology" />Science &amp; Technology
							</label> <label class="checkbox"> <input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="business" />Business
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="healthDiet" />Health &amp; Diet
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="automobile" />Automobile
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="fashionLifestyle" />Fashion &amp; Lifestyle
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="travel" />Travel
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="foodRecipies" />Food &amp; Recipes
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_interest_category_targetting_selection[]"
								name="video_ad_interest_category_targetting_selection[]"
								value="games" />Games
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">Geography:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="video_ad_country_targetting"
								name="video_ad_country_targetting" value="all" />Target all
								geographic locations</label>
						</div>

						<!-- The values are based on ISO 3166-1 alpha-3 standard -->
						<div class="popupFieldLabel"
							id="video_ad_country_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="video_ad_country_targetting_selection[]"
								name="video_ad_country_targetting_selection[]" value="USA" />United
								States </label><label class="checkbox"> <input type="checkbox"
								id="video_ad_country_targetting_selection[]"
								name="video_ad_country_targetting_selection[]" value="CAN" />Canada
							</label><label class="checkbox"><input type="checkbox"
								id="video_ad_country_targetting_selection[]"
								name="video_ad_country_targetting_selection[]" value="IND" />India
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_country_targetting_selection[]"
								name="video_ad_country_targetting_selection[]" value="BRA" />Brazil
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_country_targetting_selection[]"
								name="video_ad_country_targetting_selection[]" value="FRA" />France
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="video_ad_traffic_targetting_selection"
								onclick="toggleVideoAdTrafficTargetting('all')" value="all" />Target
								all traffic, including Wi-Fi traffic<br /></label><label class="radio">
								<input type="radio"
								onclick="toggleVideoAdTrafficTargetting('wifi')"
								name="video_ad_traffic_targetting_selection" value="wifi" />Target
								Wi-Fi traffic
							</label><label class="radio"><input type="radio"
								onclick="toggleVideoAdTrafficTargetting('mobile_operator')"
								name="video_ad_traffic_targetting_selection"
								value="mobile_operator" />Target mobile operator traffic </label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Gender:</div>
						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="video_ad_gender_targetting_selection"
								onclick="toggleVideoAdGenderTargetting('all')" value="all" />All
								Users</label><label class="radio"><input type="radio"
								name="video_ad_gender_targetting_selection"
								onclick="toggleVideoAdGenderTargetting('male')" value="male" />Male
								users only</label><label class="radio"> <input type="radio"
								name="video_ad_gender_targetting_selection"
								onclick="toggleVideoAdGenderTargetting('female')" value="female" />Female
								users only
							</label>
						</div>
						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Age Groups:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="video_ad_age_targetting" name="video_ad_age_targetting"
								value="all" />All age groups</label>
						</div>
						<div class="popupFieldLabel"
							id="video_ad_age_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="video_ad_age_targetting_selection[]"
								name="video_ad_age_targetting_selection[]" value="18-24" />18-24
							</label><label class="checkbox"><input type="checkbox"
								id="video_ad_age_targetting_selection[]"
								name="video_ad_age_targetting_selection[]" value="25-34" />25-34
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_age_targetting_selection[]"
								name="video_ad_age_targetting_selection[]" value="35-44" />35-44
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_age_targetting_selection[]"
								name="video_ad_age_targetting_selection[]" value="45-54" />45-54
							</label><label class="checkbox">> <input type="checkbox"
								id="video_ad_age_targetting_selection[]"
								name="video_ad_age_targetting_selection[]" value="55-64" />55-64
							</label><label class="checkbox"> <input type="checkbox"
								id="video_ad_age_targetting_selection[]"
								name="video_ad_age_targetting_selection[]" value="65+" />65+
							</label>
						</div>
					</div>
					<div class="popupFieldLabel">&nbsp;</div>
				</form>

			</div>
			<div class="modal-footer">
				<span id="video_ad_errors" class="label label-important"></span> <img
					src="resources/images/wait.gif" style="display: none; float: right"
					id="video_ad_wait" />
				<button id="video_ad_cancel_button" class="btn" data-dismiss="modal"
					aria-hidden="true">cancel</button>
				<button id="video_ad_save_button" class="btn btn-primary">create
					ad</button>
			</div>
		</div>
		<!-- End Video Ad -->

		<!-- Begin Reminder Ad -->
		<div class="modal hide fade" id="reminder_ad_div" aria-hidden="true">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="reminderAdModalLabel">Ad Setup</h3>
			</div>
			<div class="modal-body">
				<form id="reminderAdForm" name="reminderAdForm">
					<input type="hidden" id="reminder_ad_id" name="reminder_ad_id" /><input
						type="hidden" id="reminder_ad_campaign_id"
						name="reminder_ad_campaign_id" /> <input type="hidden"
						id="reminder_ad_adgroup_id" name="reminder_ad_adgroup_id" /> <input
						type="hidden" id="reminder_ad_uri" name="reminder_ad_uri" /><input
						type="hidden" id="reminder_ad_event_frequency_selected"
						name="reminder_ad_event_frequency_selected" /> <input
						type="hidden" id="reminder_ad_event_time_zone"
						name="reminder_ad_event_time_zone" /><input type="hidden"
						id="reminder_ad_banner_size" name="reminder_ad_banner_size" /><input
						type="hidden" id="reminder_ad_traffic_targetting"
						name="reminder_ad_traffic_targetting" /> <input type="hidden"
						id="reminder_ad_gender_targetting"
						name="reminder_ad_gender_targetting" />
					<div id="reminder_ad_setup_header">
						<div class="popupFieldLabel">Start with...</div>
						<div class="popupFieldLabel">
							<label class="radio"><input checked="checked"
								onclick="toggleReminderAdType(false)" type="radio"
								name="reminder_ad_preload" value="false" />New Ad</label><label
								class="radio"> <input
								onclick="toggleReminderAdType(true)" type="radio"
								name="reminder_ad_preload" value="true" />Sample Ad
							</label>
						</div>
					</div>

					<div class="popupFieldLabel">Name:</div>
					<div class="popupFieldInput">
						<input type="text" id="reminder_ad_name" name="reminder_ad_name" />
					</div>

					<div class="popupFieldLabel">Description:</div>
					<div class="popupFieldInput">
						<textarea rows="5" id="reminder_ad_description"
							name="reminder_ad_description"></textarea>
					</div>


					<div class="popupFieldLabel">Start Date:</div>
					<div class="popupFieldDateInput" id="reminder_ad_start_datepicker">
						<input type="text" id="reminder_ad_start_date"
							name="reminder_ad_start_date" /> <br />
					</div>
					<div class="popupFieldLabel">End Date:</div>
					<div class="popupFieldDateInput" id="reminder_ad_end_datepicker">
						<input type="text" id="reminder_ad_end_date"
							name="reminder_ad_end_date" /> <br />
					</div>

					<div class="popupFieldLabel">
						Ad <span class="label label-info" id="reminder_ad_status">Enabled</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="reminder_ad_enabled" name="reminder_ad_enabled"
							checked="checked" /></label>
					</div>

					<div class="popupFieldLabel">Cost Per Impression:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="reminder_ad_cpi" name="reminder_ad_cpi"
							title="Cost per reminder ad impression" />
					</div>

					<div class="popupFieldLabel">Cost Per Click:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="reminder_ad_cpc" name="reminder_ad_cpc"
							title="Cost per reminder ad click" />
					</div>

					<div class="popupFieldLabel">Frequency Capping:</div>
					<div class="popupFieldInput">
						<input type="text" id="reminder_ad_frequency_capping"
							name="reminder_ad_frequency_capping"
							title="The number of ad impressions per day limit" />
					</div>

					<div class="popupFieldLabel">Event Title:</div>
					<div class="popupFieldInput">
						<input type="text" id="reminder_ad_event_title"
							name="reminder_ad_event_title"
							title="The title of the calendar event" />
					</div>

					<div class="popupFieldLabel">Event Location:</div>
					<div class="popupFieldInput">
						<input type="text" id="reminder_ad_event_location"
							name="reminder_ad_event_location"
							title="The location of the calendar event" />
					</div>

					<div class="popupFieldLabel">Event Description:</div>
					<div class="popupFieldInput">
						<textarea rows="5" id="reminder_ad_event_description"
							name="reminder_ad_event_description"></textarea>
					</div>

					<div class="popupFieldLabel">Event RRule:</div>
					<div class="popupFieldInput">
						<input type="text" id="reminder_ad_event_rrule"
							name="reminder_ad_event_rrule"
							title="The rule for the pattern that defines a recurring calendar event. The rule is based on the date and time of the first instance. The syntax is defined by the Recurrence Rule section of RFC 2445 (the iCalendar standard)." />
					</div>

					<div class="popupFieldLabel">Event Start Date and Time:</div>
					<div class="popupFieldInput">
						<input type="text" id="reminder_ad_event_begin_time"
							name="reminder_ad_event_begin_time" /> <br />
					</div>
					<div class="popupFieldLabel">Event End Date and Time:</div>
					<div class="popupFieldInput">
						<input type="text" id="reminder_ad_event_end_time"
							name="reminder_ad_event_end_time" /> <br />
					</div>

					<div class="popupFieldLabel">Target Applications:</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="reminder_ad_target_application[]"
							name="reminder_ad_target_application[]" value="Contacts"
							title="Valid banner size is 320x50 for phones without HD support, and 640x100 for HD phones" />Contacts
						</label><label class="checkbox"> <input type="checkbox"
							id="reminder_ad_target_application[]"
							name="reminder_ad_target_application[]" value="Mms"
							title="Valid banner size is 320x50 for phones without HD support, and 640x100 for HD phones" />Mms
						</label><label
							class="checkbox"> <input type="checkbox"
							id="reminder_ad_target_application[]"
							name="reminder_ad_target_application[]" value="Dialer"
							title="Valid banner size is 320x50 for phones without HD support, and 640x100 for HD phones" />Dialer
						</label><label class="checkbox"> <input type="checkbox"
							id="reminder_ad_target_application[]"
							name="reminder_ad_target_application[]" value="Dialer"
							title="Valid banner size is 320x50 for phones without HD support, and 640x100 for HD phones" />Dialer
						</label><label class="checkbox"> <input type="checkbox"
							id="reminder_ad_target_application[]"
							name="reminder_ad_target_application[]" value="Dialer"
							title="Valid banner size is 320x50 for phones without HD support, and 640x100 for HD phones" />Dialer
						</label><label class="checkbox"> <input type="checkbox"
							id="reminder_ad_target_application[]"
							name="reminder_ad_target_application[]" value="ChargeScreen"
							title="Valid banner size is 480x800 for a 4.x&quot; screen" />Charge
							Screen
						</label><label class="checkbox"> <input type="checkbox"
							id="reminder_ad_target_application[]"
							name="reminder_ad_target_application[]" value="Wallpaper"
							title="Valid banner size is 480x800 for a 4.x&quot; screen" />Wallpaper
						</label>
					</div>

					<div class="popupFieldLabel">&nbsp;</div>
					<div class="popupFieldLabel">Banner Size:</div>
					<div class="popupFieldLabel">
						<select id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" tabindex="1">
							<option value="">--Select--</option>
							<option value="480x800">480x800 (Full Screen)</option>
							<option value="300x250">300x250 (Medium Rectangle)</option>
							<option value="320x50">320x50 (Banner)</option>
							<option value="640x100">640x100(HD Banner)</option>
						</select>
						<!-- default it to 300x50 -->
						<!-- 						<label class="radio inline"><input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="1920x1200" />1920x1200</label><label
							class="radio inline"> <input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="720x1280" />720x1280
						</label><label class="radio inline"> <input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="960x720" />960x720
						</label><label class="radio inline"> <input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="640x480" />640x480
						</label><label class="radio inline"> <input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="480x800" />480x800
						</label><label class="radio inline"> <input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="480x320" />480x320
						</label><label class="radio inline"> <input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="320x480" />320x480
						</label><label class="radio inline">;<input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="480x320" />480x320
						</label><label class="radio inline"><input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="300x50" />300x50</label><label
							class="radio inline"> <input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="216x36" />216x36
						</label><label class="radio inline"> <input type="radio"
							id="reminder_ad_banner_size_selection"
							name="reminder_ad_banner_size_selection" value="168x28" />168x28
						</label>
 -->
					</div>

					<div class="popupFieldLabel">&nbsp;</div>
					<button id="view_reminder_ad_image" type="button"
						class="btn btn-inverse" data-loading-text="Loading...">View
						Ad Image</button>
					<div id="view_reminder_image">
						<img class="popupViewImage img-rounded" id="reminder_ad_image" />
					</div>

					<div class="popupFieldLabel">&nbsp;</div>
					<button id="upload_reminder_ad_image" type="button"
						class="btn btn-inverse" data-toggle="button">Upload Image</button>
					<div id="reminder_ad_dropbox">
						<span class="message">Drop images here to upload</span>
					</div>

					<div class="popupFieldLabel">&nbsp;</div>

					<h3>Targetting:</h3>
					<div class="popupFieldLabel">
						Override Targetting</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="reminder_ad_targetting_overridden"
							name="reminder_ad_targetting_overridden" /></label>
					</div>

					<div id="reminder_ad_targetting_div">
						<div class="popupFieldLabel">Interest Categories:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="reminder_ad_interest_category_targetting"
								name="reminder_ad_interest_category_targetting" value="all" />Target
								all interest categories</label>
						</div>

						<!-- The values are based on standard contract -->
						<div class="popupFieldLabel"
							id="reminder_ad_interest_category_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="moviesMusicEvents" />Movies, Music &amp; Events </label><label
								class="checkbox"> <input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="restaurantsSpa" />Restaurants &amp; Spa
							</label><label class="checkbox"><input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="education" />Education </label><label class="checkbox">
								<input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="newsReports" />News &amp; Reports
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="sports" />Sports
							</label> <label class="checkbox"> <input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="scienceTechnology" />Science &amp; Technology
							</label> <label class="checkbox"> <input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="business" />Business
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="healthDiet" />Health &amp; Diet
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="automobile" />Automobile
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="fashionLifestyle" />Fashion &amp; Lifestyle
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="travel" />Travel
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="foodRecipies" />Food &amp; Recipes
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_interest_category_targetting_selection[]"
								name="reminder_ad_interest_category_targetting_selection[]"
								value="games" />Games
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">Geography:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="reminder_ad_country_targetting"
								name="reminder_ad_country_targetting" value="all" />Target all
								geographic locations</label>
						</div>

						<!-- The values are based on ISO 3166-1 alpha-3 standard -->
						<div class="popupFieldLabel"
							id="reminder_ad_country_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="reminder_ad_country_targetting_selection[]"
								name="reminder_ad_country_targetting_selection[]" value="USA" />United
								States </label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_country_targetting_selection[]"
								name="reminder_ad_country_targetting_selection[]" value="CAN" />Canada
							</label><label class="checkbox"><input type="checkbox"
								id="reminder_ad_country_targetting_selection[]"
								name="reminder_ad_country_targetting_selection[]" value="IND" />India
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_country_targetting_selection[]"
								name="reminder_ad_country_targetting_selection[]" value="BRA" />Brazil
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_country_targetting_selection[]"
								name="reminder_ad_country_targetting_selection[]" value="FRA" />France
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="reminder_ad_traffic_targetting_selection"
								onclick="toggleReminderAdTrafficTargetting('all')" value="all" />Target
								all traffic, including Wi-Fi traffic<br /></label><label class="radio">
								<input type="radio"
								onclick="toggleReminderAdTrafficTargetting('wifi')"
								name="reminder_ad_traffic_targetting_selection" value="wifi" />Target
								Wi-Fi traffic
							</label><label class="radio"><input type="radio"
								onclick="toggleReminderAdTrafficTargetting('mobile_operator')"
								name="reminder_ad_traffic_targetting_selection"
								value="mobile_operator" />Target mobile operator traffic </label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Gender:</div>
						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="reminder_ad_gender_targetting_selection"
								onclick="toggleReminderAdGenderTargetting('all')" value="all" />All
								Users</label><label class="radio"><input type="radio"
								name="reminder_ad_gender_targetting_selection"
								onclick="toggleReminderAdGenderTargetting('male')" value="male" />Male
								users only</label><label class="radio"> <input type="radio"
								name="reminder_ad_gender_targetting_selection"
								onclick="toggleReminderAdGenderTargetting('female')"
								value="female" />Female users only
							</label>
						</div>
						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Age Groups:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="reminder_ad_age_targetting"
								name="reminder_ad_age_targetting" value="all" />All age groups</label>
						</div>
						<div class="popupFieldLabel"
							id="reminder_ad_age_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="reminder_ad_age_targetting_selection[]"
								name="reminder_ad_age_targetting_selection[]" value="18-24" />18-24
							</label><label class="checkbox"><input type="checkbox"
								id="reminder_ad_age_targetting_selection[]"
								name="reminder_ad_age_targetting_selection[]" value="25-34" />25-34
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_age_targetting_selection[]"
								name="reminder_ad_age_targetting_selection[]" value="35-44" />35-44
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_age_targetting_selection[]"
								name="reminder_ad_age_targetting_selection[]" value="45-54" />45-54
							</label><label class="checkbox">> <input type="checkbox"
								id="reminder_ad_age_targetting_selection[]"
								name="reminder_ad_age_targetting_selection[]" value="55-64" />55-64
							</label><label class="checkbox"> <input type="checkbox"
								id="reminder_ad_age_targetting_selection[]"
								name="reminder_ad_age_targetting_selection[]" value="65+" />65+
							</label>
						</div>
					</div>
				</form>

			</div>
			<div class="modal-footer">
				<span id="reminder_ad_errors" class="label label-important"></span>
				<img src="resources/images/wait.gif"
					style="display: none; float: right" id="reminder_ad_wait" />
				<button id="reminder_ad_cancel_button" class="btn"
					data-dismiss="modal" aria-hidden="true">cancel</button>
				<button id="reminder_ad_save_button" class="btn btn-primary">create
					ad</button>
			</div>

		</div>
		<!-- End Reminder Ad -->

		<!-- Begin Voice Ad -->
		<div class="modal hide fade" id="voice_ad_div" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="voiceAdModalLabel">Ad Setup</h3>
			</div>
			<div class="modal-body">
				<form id="voiceAdForm" name="voiceAdForm">
					<input type="hidden" id="voice_ad_id" name="voice_ad_id" /> <input
						type="hidden" id="voice_ad_campaign_id"
						name="voice_ad_campaign_id" /><input type="hidden"
						id="voice_ad_adgroup_id" name="voice_ad_adgroup_id" /> <input
						type="hidden" id="voice_ad_uri" name="voice_ad_uri" /><input
						type="hidden" id="voice_ad_traffic_targetting"
						name="voice_ad_traffic_targetting" /> <input type="hidden"
						id="voice_ad_gender_targetting" name="voice_ad_gender_targetting" />
					<div id="voice_ad_setup_header">
						<div class="popupFieldLabel">Start with...</div>
						<div class="popupFieldLabel">
							<label class="radio"><input checked="checked"
								onclick="toggleVoiceAdType(false)" type="radio"
								name="voice_ad_preload" value="false" />New Ad</label><label
								class="radio"> <input onclick="toggleVoiceAdType(true)"
								type="radio" name="voice_ad_preload" value="true" />Sample Ad
							</label>
						</div>
					</div>

					<div class="popupFieldLabel">Name:</div>
					<div class="popupFieldInput">
						<input type="text" id="voice_ad_name" name="voice_ad_name" />
					</div>

					<div class="popupFieldLabel">Description:</div>
					<div class="popupFieldInput">
						<textarea rows="5" id="voice_ad_description"
							name="voice_ad_description"></textarea>
					</div>

					<div class="popupFieldLabel">Start Date:</div>
					<div class="popupFieldDateInput" id="voice_ad_start_datepicker">
						<input type="text" id="voice_ad_start_date"
							name="voice_ad_start_date" /> <br />
					</div>
					<div class="popupFieldLabel">End Date:</div>
					<div class="popupFieldDateInput" id="voice_ad_end_datepicker">
						<input type="text" id="voice_ad_end_date" name="voice_ad_end_date" />
						<br />
					</div>

					<div class="popupFieldLabel">
						Ad <span class="label label-info" id="voice_ad_status">Enabled</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="voice_ad_enabled" name="voice_ad_enabled" checked="checked" /></label>
					</div>

					<div class="popupFieldLabel">Cost Per Impression:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="voice_ad_cpi" name="voice_ad_cpi"
							title="Cost per voice ad impression" />
					</div>

					<div class="popupFieldLabel">Cost Per Click:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="voice_ad_cpc" name="voice_ad_cpc"
							title="Cost per voice ad click" />
					</div>

					<div class="popupFieldLabel">Frequency Capping:</div>
					<div class="popupFieldInput">
						<input type="text" id="voice_ad_frequency_capping"
							name="voice_ad_frequency_capping"
							title="The number of ad impressions per day limit" />
					</div>

					<div class="popupFieldLabel">Target Applications:</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="voice_ad_target_application[]"
							name="voice_ad_target_application[]" value="Contacts" />Contacts
						</label><label class="checkbox"> <input type="checkbox"
							id="voice_ad_target_application[]"
							name="voice_ad_target_application[]" value="Mms" />Mms
						</label><label class="checkbox"> <input type="checkbox"
							id="voice_ad_target_application[]"
							name="voice_ad_target_application[]" value="Dialer" />Dialer
						</label><label class="checkbox"> <input type="checkbox"
							id="voice_ad_target_application[]"
							name="voice_ad_target_application[]" value="ChargeScreen" />Charge
							Screen
						</label><label class="checkbox"> <input type="checkbox"
							id="voice_ad_target_application[]"
							name="voice_ad_target_application[]" value="Wallpaper" />Wallpaper
						</label>
					</div>

					<div class="popupFieldLabel">&nbsp;</div>
					<button id="upload_voice_ad" type="button" class="btn btn-inverse"
						data-toggle="button">Upload Audio</button>
					<div id="voice_ad_dropbox">
						<span class="message">Drop audio files here to upload</span>
					</div>
					<div class="popupFieldLabel">&nbsp;</div>

					<h3>Targetting:</h3>
					<div class="popupFieldLabel">
						Override Targetting</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="voice_ad_targetting_overridden"
							name="voice_ad_targetting_overridden" /></label>
					</div>

					<div id="voice_ad_targetting_div">
						<div class="popupFieldLabel">Interest Categories:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="voice_ad_interest_category_targetting"
								name="voice_ad_interest_category_targetting" value="all" />Target
								all interest categories</label>
						</div>

						<!-- The values are based on standard contract -->
						<div class="popupFieldLabel"
							id="voice_ad_interest_category_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="moviesMusicEvents" />Movies, Music &amp; Events </label><label
								class="checkbox"> <input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="restaurantsSpa" />Restaurants &amp; Spa
							</label><label class="checkbox"><input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="education" />Education </label><label class="checkbox">
								<input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="newsReports" />News &amp; Reports
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="sports" />Sports
							</label> <label class="checkbox"> <input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="scienceTechnology" />Science &amp; Technology
							</label> <label class="checkbox"> <input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="business" />Business
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="healthDiet" />Health &amp; Diet
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="automobile" />Automobile
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="fashionLifestyle" />Fashion &amp; Lifestyle
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="travel" />Travel
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="foodRecipies" />Food &amp; Recipes
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_interest_category_targetting_selection[]"
								name="voice_ad_interest_category_targetting_selection[]"
								value="games" />Games
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">Geography:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="voice_ad_country_targetting"
								name="voice_ad_country_targetting" value="all" />Target all
								geographic locations</label>
						</div>

						<!-- The values are based on ISO 3166-1 alpha-3 standard -->
						<div class="popupFieldLabel"
							id="voice_ad_country_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="voice_ad_country_targetting_selection[]"
								name="voice_ad_country_targetting_selection[]" value="USA" />United
								States </label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_country_targetting_selection[]"
								name="voice_ad_country_targetting_selection[]" value="CAN" />Canada
							</label><label class="checkbox"><input type="checkbox"
								id="voice_ad_country_targetting_selection[]"
								name="voice_ad_country_targetting_selection[]" value="IND" />India
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_country_targetting_selection[]"
								name="voice_ad_country_targetting_selection[]" value="BRA" />Brazil
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_country_targetting_selection[]"
								name="voice_ad_country_targetting_selection[]" value="FRA" />France
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="voice_ad_traffic_targetting_selection"
								onclick="toggleVoiceAdTrafficTargetting('all')" value="all" />Target
								all traffic, including Wi-Fi traffic<br /></label><label class="radio">
								<input type="radio"
								onclick="toggleVoiceAdTrafficTargetting('wifi')"
								name="voice_ad_traffic_targetting_selection" value="wifi" />Target
								Wi-Fi traffic
							</label><label class="radio"><input type="radio"
								onclick="toggleVoiceAdTrafficTargetting('mobile_operator')"
								name="voice_ad_traffic_targetting_selection"
								value="mobile_operator" />Target mobile operator traffic </label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Gender:</div>
						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="voice_ad_gender_targetting_selection"
								onclick="toggleVoiceAdGenderTargetting('all')" value="all" />All
								Users</label><label class="radio"><input type="radio"
								name="voice_ad_gender_targetting_selection"
								onclick="toggleVoiceAdGenderTargetting('male')" value="male" />Male
								users only</label><label class="radio"> <input type="radio"
								name="voice_ad_gender_targetting_selection"
								onclick="toggleVoiceAdGenderTargetting('female')" value="female" />Female
								users only
							</label>
						</div>
						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Age Groups:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="voice_ad_age_targetting" name="voice_ad_age_targetting"
								value="all" />All age groups</label>
						</div>
						<div class="popupFieldLabel"
							id="voice_ad_age_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="voice_ad_age_targetting_selection[]"
								name="voice_ad_age_targetting_selection[]" value="18-24" />18-24
							</label><label class="checkbox"><input type="checkbox"
								id="voice_ad_age_targetting_selection[]"
								name="voice_ad_age_targetting_selection[]" value="25-34" />25-34
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_age_targetting_selection[]"
								name="voice_ad_age_targetting_selection[]" value="35-44" />35-44
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_age_targetting_selection[]"
								name="voice_ad_age_targetting_selection[]" value="45-54" />45-54
							</label><label class="checkbox">> <input type="checkbox"
								id="voice_ad_age_targetting_selection[]"
								name="voice_ad_age_targetting_selection[]" value="55-64" />55-64
							</label><label class="checkbox"> <input type="checkbox"
								id="voice_ad_age_targetting_selection[]"
								name="voice_ad_age_targetting_selection[]" value="65+" />65+
							</label>
						</div>
					</div>
				</form>

			</div>
			<div class="modal-footer">
				<span id="voice_ad_errors" class="label label-important"></span> <img
					src="resources/images/wait.gif" style="display: none; float: right"
					id="voice_ad_wait" />
				<button id="voice_ad_cancel_button" class="btn" data-dismiss="modal"
					aria-hidden="true">cancel</button>
				<button id="voice_ad_save_button" class="btn btn-primary">create
					ad</button>
			</div>
		</div>
		<!-- End Voice Ad -->

		<!-- Begin Survey -->
		<div class="modal hide fade" id="survey_div" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="surveyModalLabel">Survey Setup</h3>
			</div>
			<div class="modal-body">
				<form id="surveyForm" name="surveyForm">
					<input type="hidden" id="survey_id" name="survey_id" /> <input
						type="hidden" id="survey_campaign_id" name="survey_campaign_id" /><input
						type="hidden" id="survey_adgroup_id" name="survey_adgroup_id" /><input
						type="hidden" id="survey_traffic_targetting"
						name="survey_traffic_targetting" /> <input type="hidden"
						id="survey_gender_targetting" name="survey_gender_targetting" />


					<div id="survey_setup_header">
						<div class="popupFieldLabel">Start with...</div>
						<div class="popupFieldLabel">
							<label class="radio"><input checked="checked"
								onclick="toggleSurveyType(false)" type="radio"
								name="survey_preload" value="false" />New Survey</label><label
								class="radio"> <input onclick="toggleSurveyType(true)"
								type="radio" name="survey_preload" value="true" />Sample Survey
							</label>
						</div>
					</div>
					<div class="popupFieldLabel">&nbsp;</div>
					<div class="popupFieldLabel">Start Date:</div>
					<div class="popupFieldDateInput" id="survey_start_datepicker">
						<input type="text" id="survey_start_date" name="survey_start_date" />
						<br />
					</div>
					<div class="popupFieldLabel">End Date:</div>
					<div class="popupFieldDateInput" id="survey_end_datepicker">
						<input type="text" id="survey_end_date" name="survey_end_date" />
						<br />
					</div>

					<div class="popupFieldLabel">
						Survey <span class="label label-info" id="survey_status">Enabled</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="survey_enabled" name="survey_enabled" checked="checked" /></label>
					</div>

					<div class="popupFieldLabel">Cost Per Impression:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="survey_cpi" name="survey_cpi"
							title="Cost per survey impression" />
					</div>

					<div class="popupFieldLabel">Cost Per Click:</div>
					<div class="popupFieldShortInput">
						<input type="text" id="survey_cpc" name="survey_cpc"
							title="Cost per survey click" />
					</div>

					<div class="popupFieldLabel">Frequency Capping:</div>
					<div class="popupFieldInput">
						<input type="text" id="survey_frequency_capping"
							name="survey_frequency_capping"
							title="The number of ad impressions per day limit" />
					</div>

					<div class="popupFieldLabel">&nbsp;</div>
					<div class="popupFieldLabel">Question:</div>
					<div class="popupFieldInput">
						<textarea rows="5" id="survey_question" name="survey_question"></textarea>
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
					<div class="popupFieldLabel">&nbsp;</div>

					<h3>Targetting:</h3>
					<div class="popupFieldLabel">
						Override Targetting</span>
					</div>
					<div class="popupFieldLabel">
						<label class="checkbox"><input type="checkbox"
							id="survey_targetting_overridden"
							name="survey_targetting_overridden" /></label>
					</div>

					<div id="survey_targetting_div">
						<div class="popupFieldLabel">Interest Categories:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="survey_interest_category_targetting"
								name="survey_interest_category_targetting" value="all" />Target
								all interest categories</label>
						</div>

						<!-- The values are based on standard contract -->
						<div class="popupFieldLabel"
							id="survey_interest_category_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="moviesMusicEvents" />Movies, Music &amp; Events </label><label
								class="checkbox"> <input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="restaurantsSpa" />Restaurants &amp; Spa
							</label><label class="checkbox"><input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="education" />Education </label><label class="checkbox">
								<input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="newsReports" />News &amp; Reports
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="sports" />Sports
							</label> <label class="checkbox"> <input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="scienceTechnology" />Science &amp; Technology
							</label> <label class="checkbox"> <input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="business" />Business
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="healthDiet" />Health &amp; Diet
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="automobile" />Automobile
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="fashionLifestyle" />Fashion &amp; Lifestyle
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="travel" />Travel
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="foodRecipies" />Food &amp; Recipes
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_interest_category_targetting_selection[]"
								name="survey_interest_category_targetting_selection[]"
								value="games" />Games
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">Geography:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="survey_country_targetting" name="survey_country_targetting"
								value="all" />Target all geographic locations</label>
						</div>

						<!-- The values are based on ISO 3166-1 alpha-3 standard -->
						<div class="popupFieldLabel"
							id="survey_country_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="survey_country_targetting_selection[]"
								name="survey_country_targetting_selection[]" value="USA" />United
								States </label><label class="checkbox"> <input type="checkbox"
								id="survey_country_targetting_selection[]"
								name="survey_country_targetting_selection[]" value="CAN" />Canada
							</label><label class="checkbox"><input type="checkbox"
								id="survey_country_targetting_selection[]"
								name="survey_country_targetting_selection[]" value="IND" />India
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_country_targetting_selection[]"
								name="survey_country_targetting_selection[]" value="BRA" />Brazil
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_country_targetting_selection[]"
								name="survey_country_targetting_selection[]" value="FRA" />France
							</label>
						</div>

						<div class="popupFieldLabel">&nbsp;</div>

						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="survey_traffic_targetting_selection"
								onclick="toggleSurveyTrafficTargetting('all')" value="all" />Target
								all traffic, including Wi-Fi traffic<br /></label><label class="radio">
								<input type="radio"
								onclick="toggleSurveyTrafficTargetting('wifi')"
								name="survey_traffic_targetting_selection" value="wifi" />Target
								Wi-Fi traffic
							</label><label class="radio"><input type="radio"
								onclick="toggleSurveyTrafficTargetting('mobile_operator')"
								name="survey_traffic_targetting_selection"
								value="mobile_operator" />Target mobile operator traffic </label>
						</div>

						<h3>Demographics:</h3>
						<div class="popupFieldLabel">Gender:</div>
						<div class="popupFieldLabel">
							<label class="radio"><input type="radio"
								name="survey_gender_targetting_selection"
								onclick="toggleSurveyGenderTargetting('all')" value="all" />All
								Users</label><label class="radio"><input type="radio"
								name="survey_gender_targetting_selection"
								onclick="toggleSurveyGenderTargetting('male')" value="male" />Male
								users only</label><label class="radio"> <input type="radio"
								name="survey_gender_targetting_selection"
								onclick="toggleSurveyGenderTargetting('female')" value="female" />Female
								users only
							</label>
						</div>
						<div class="popupFieldLabel">&nbsp;</div>
						<div class="popupFieldLabel">Age Groups:</div>
						<div class="popupFieldLabel">
							<label class="checkbox"><input type="checkbox"
								id="survey_age_targetting" name="survey_age_targetting"
								value="all" />All age groups</label>
						</div>
						<div class="popupFieldLabel"
							id="survey_age_targetting_selection_div">
							<label class="checkbox"><input type="checkbox"
								id="survey_age_targetting_selection[]"
								name="survey_age_targetting_selection[]" value="18-24" />18-24
							</label><label class="checkbox"><input type="checkbox"
								id="survey_age_targetting_selection[]"
								name="survey_age_targetting_selection[]" value="25-34" />25-34
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_age_targetting_selection[]"
								name="survey_age_targetting_selection[]" value="35-44" />35-44
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_age_targetting_selection[]"
								name="survey_age_targetting_selection[]" value="45-54" />45-54
							</label><label class="checkbox">> <input type="checkbox"
								id="survey_age_targetting_selection[]"
								name="survey_age_targetting_selection[]" value="55-64" />55-64
							</label><label class="checkbox"> <input type="checkbox"
								id="survey_age_targetting_selection[]"
								name="survey_age_targetting_selection[]" value="65+" />65+
							</label>
						</div>
					</div>
					<div class="popupFieldLabel">&nbsp;</div>
				</form>
			</div>
			<div class="modal-footer">
				<span id="survey_errors" class="label label-important"></span> <img
					src="resources/images/wait.gif" style="display: none; float: right"
					id="survey_wait" />
				<button id="survey_cancel_button" class="btn" data-dismiss="modal"
					aria-hidden="true">cancel</button>
				<button id="survey_save_button" class="btn btn-primary">create
					ad</button>
			</div>
		</div>
		<!-- End Survey -->


		<!-- View Ad Image -->
		<div class="modal hide fade" id="view_ad_image_div" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="view_image_modal_label"></h3>
			</div>
			<div class="modal-body">
				<img id="ad_image_widget" class="img-rounded" />
			</div>
			<div class="modal-footer">
				<button id="view_image_done_button" class="btn btn-primary"
					data-dismiss="modal" aria-hidden="true">done</button>
			</div>
		</div>
		<!-- View Ad Image -->

		<!-- JPlayer -->
		<div id="jp_container_1"
			class="modal hide fade jp-video jp-video-360p" aria-hidden="true">
			<div class="modal-header">
				<h3 id="view_video_modal_label"></h3>
			</div>
			<div class="jp-type-single">
				<div id="jquery_jplayer_1" class="jp-jplayer"></div>
				<div class="jp-gui">
					<div class="jp-video-play">
						<a href="javascript:;" class="jp-video-play-icon" tabindex="1">play</a>
					</div>
					<div class="jp-interface">
						<div class="jp-progress">
							<div class="jp-seek-bar">
								<div class="jp-play-bar"></div>
							</div>
						</div>
						<div class="jp-current-time"></div>
						<div class="jp-duration"></div>
						<div class="jp-controls-holder">
							<ul class="jp-controls">
								<li><a href="javascript:;" class="jp-play" tabindex="1">play</a></li>
								<li><a href="javascript:;" class="jp-pause" tabindex="1">pause</a></li>
								<li><a href="javascript:;" class="jp-stop" tabindex="1">stop</a></li>
								<li><a href="javascript:;" class="jp-mute" tabindex="1"
									title="mute">mute</a></li>
								<li><a href="javascript:;" class="jp-unmute" tabindex="1"
									title="unmute">unmute</a></li>
								<li><a href="javascript:;" class="jp-volume-max"
									tabindex="1" title="max volume">max volume</a></li>
							</ul>
							<div class="jp-volume-bar">
								<div class="jp-volume-bar-value"></div>
							</div>
							<ul class="jp-toggles">
								<li><a href="javascript:;" class="jp-full-screen"
									tabindex="1" title="full screen">full screen</a></li>
								<li><a href="javascript:;" class="jp-restore-screen"
									tabindex="1" title="restore screen">restore screen</a></li>
								<li><a href="javascript:;" class="jp-repeat" tabindex="1"
									title="repeat">repeat</a></li>
								<li><a href="javascript:;" class="jp-repeat-off"
									tabindex="1" title="repeat off">repeat off</a></li>
							</ul>
						</div>
						<div class="jp-title">
							<ul>
								<li id="video_title"></li>
							</ul>
						</div>

					</div>
				</div>
				<div class="jp-no-solution">
					<span>Update Required</span> To play the media you will need to
					either update your browser to a recent version or update your <a
						href="http://get.adobe.com/flashplayer/" target="_blank">Flash
						plugin</a>.
				</div>
			</div>
			<div class="modal-footer">
				<button id="view_video_done_button" class="btn btn-primary"
					data-dismiss="modal" aria-hidden="true">done</button>
			</div>
		</div>
		<!-- JPlayer -->

		<!-- Audio Player -->

		<!-- The jPlayer div must not be hidden. Keep it at the root of the body element to avoid any such problems. -->
		<div id="jquery_jplayer_2" class="jp-jplayer"></div>

		<!-- The container for the interface can go where you want to display it. Show and hide it as you need. -->
		<div id="jp_container_2" class="modal hide fade jp-audio"
			aria-hidden="true">
			<div class="modal-header">
				<h3 id="listen_audio_modal_label"></h3>
			</div>
			<div class="jp-type-single">
				<div class="jp-gui jp-interface">
					<ul class="jp-controls">
						<li><a href="javascript:;" class="jp-play" tabindex="1">play</a></li>
						<li><a href="javascript:;" class="jp-pause" tabindex="1">pause</a></li>
						<li><a href="javascript:;" class="jp-stop" tabindex="1">stop</a></li>
						<li><a href="javascript:;" class="jp-mute" tabindex="1"
							title="mute">mute</a></li>
						<li><a href="javascript:;" class="jp-unmute" tabindex="1"
							title="unmute">unmute</a></li>
						<li><a href="javascript:;" class="jp-volume-max" tabindex="1"
							title="max volume">max volume</a></li>
					</ul>
					<div class="jp-progress">
						<div class="jp-seek-bar">
							<div class="jp-play-bar"></div>
						</div>
					</div>
					<div class="jp-volume-bar">
						<div class="jp-volume-bar-value"></div>
					</div>
					<div class="jp-time-holder">
						<div class="jp-current-time"></div>
						<div class="jp-duration"></div>

						<ul class="jp-toggles">
							<li><a href="javascript:;" class="jp-repeat" tabindex="1"
								title="repeat">repeat</a></li>
							<li><a href="javascript:;" class="jp-repeat-off"
								tabindex="1" title="repeat off">repeat off</a></li>
						</ul>
					</div>
				</div>
				<div class="jp-title">
					<ul>
						<li id="audio_title"></li>
					</ul>
				</div>
				<div class="jp-no-solution">
					<span>Update Required</span> To play the media you will need to
					either update your browser to a recent version or update your <a
						href="http://get.adobe.com/flashplayer/" target="_blank">Flash
						plugin</a>.
				</div>
			</div>
			<div class="modal-footer">
				<button id="listen_audio_done_button" class="btn btn-primary"
					data-dismiss="modal" aria-hidden="true">done</button>
			</div>
		</div>
		<!-- Audio Player -->
	</sec:authorize>





	<!-- Begin Wait Div -->
	<!-- 	<div id="wait_div" class="popupViewWait">
		<img src="resources/images/wait_large.gif" id="wait" />
	</div>
 -->
	<!-- End Wait Div -->

</body>
</html>