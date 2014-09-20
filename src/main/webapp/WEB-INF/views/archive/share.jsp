<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<!-- JQuery related includes -->
<script src="../resources/js/jquery/jquery-1.8.1.min.js"></script>
<script src="../resources/js/jquery/jquery-ui-1.10.0.custom.min.js"></script>
<link href="../resources/css/jquery-ui-1.10.0.custom.min.css"
	rel="stylesheet" type="text/css" />
<!--  End JQuery includes -->

<!-- JPlayer -->
<script type="text/javascript"
	src="../resources/js/jquery/jquery.jplayer.2.2.0.min.js"></script>
<link href="../resources/jplayer/skin/blue.monday/jplayer.blue.monday.css"
	rel="stylesheet" type="text/css" />
<!-- JPlayer -->

<script type="text/javascript" src="../resources/js/mavin.utilities.js"></script>

<script>
function initpage()
{
	var _url = "/contentserver/dropbox/" + "${adurl}";
	var _adtype = "${adtype}";
	
	document.title = "${adtitle}";

	$(".mavin_image_ad").hide();
	$(".jp-video").hide();
	$(".jp-audio").hide();
	$(".debug").hide();
	
	switch (_adtype) {
	case "video_ad":
		$(".jp-video").show();
		$("#jquery_jplayer_1").jPlayer(
				{
					ready: function () {
						$(this).jPlayer("setMedia", {
							m4v: _url,
							ogv: _url,
							webmv: _url,
							poster: "/resources/images/mavin_logo_orange_on_white_480x271.jpg"
						});
					},
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
	break;
	case "voice_ad":
		$(".jp-audio").show();
		$("#jquery_jplayer_2").jPlayer(
				{
					ready: function () {
						$(this).jPlayer("setMedia", {
							m4a : _url,
							oga : _url
						});
					},
					swfPath : "resources/js/jquery",
					supplied: "m4a, oga",
					cssSelectorAncestor: "#jp_container_2",
					wmode: "window"
				});
		break;
	case "image_ad":
	case "reminder_ad":
		$(".mavin_image_ad").show();
		$("#ad_image_widget").attr("src", _url);
	break;
	}
}

</script>

</head>
<body onload="initpage()">

<p class="debug">AdId: ${adid}, ${adtype}, ${adurl}</p>

		<!-- View Ad Image -->
		<div id="view_ad_image_div" class="modal hide fade mavin_image_ad"  aria-hidden="true">
			<div class="modal-body">
				<img id="ad_image_widget" class="img-rounded" />
			</div>
		</div>
		<!-- View Ad Image -->

		<!-- JPlayer -->
		<div id="jp_container_1" class="modal hide fade jp-video jp-video-360p" aria-hidden="true">
			<div class="jp-type-single">
				<div id="jquery_jplayer_1" class="jp-jplayer">
				</div>
				<div class="jp-gui">
					<div class="jp-video-play">
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
		</div>
		<!-- JPlayer -->

		<!-- Audio Player -->

		<!-- The jPlayer div must not be hidden. Keep it at the root of the body element to avoid any such problems. -->
		<div id="jquery_jplayer_2" class="jp-jplayer"></div>

		<!-- The container for the interface can go where you want to display it. Show and hide it as you need. -->
		<div id="jp_container_2" class="modal hide fade jp-audio"
			aria-hidden="true">
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
		</div>
		<!-- Audio Player -->

</body>
</html>