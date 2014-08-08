<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%><html>


<!-- Begin Confirm -->
<div class="reveal-modal small" id="confirm_modal" data-reveal>
	<h3 id="confirmModalLabel">Confirm</h3>
	<div class="row">
		<div id="confirm_message"></div>
		<div>&nbsp;</div>
	</div>
	<button id="confirm_yes_button" class="button">yes</button>
	<a class="close-reveal-modal">&#215;</a>
</div>
<!-- End Confirm -->

<!-- JPlayer -->
<div id="jp_container_1"
	class="reveal-modal full jp-video jp-video-360p" data-reveal>
	<h3 id="view_video_label"></h3>
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
						<li><a href="javascript:;" class="jp-volume-max" tabindex="1"
							title="max volume">max volume</a></li>
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
						<li><a href="javascript:;" class="jp-repeat-off" tabindex="1"
							title="repeat off">repeat off</a></li>
					</ul>
				</div>
				<div class="jp-title">
					<ul>
						<li id="view_video_title"></li>
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
	<div>&nbsp;</div>
	<button id="view_video_done_button" class="button radius">done</button>
</div>
<!-- JPlayer -->

<!-- View Image -->
<div class="reveal-modal medium" id="view_image_container" data-reveal>
	<div class="row">
		<h3 id="view_image_label"></h3>
		<img id="image_widget" class="img-rounded" />
		<div>&nbsp;</div>
		<button id="view_image_done_button" class="button radius">done</button>
	</div>
</div>
<!-- View Image -->