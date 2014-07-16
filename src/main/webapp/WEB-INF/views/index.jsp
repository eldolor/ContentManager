
<!doctype html>
<!--[if IE 9]><html class="lt-ie10" lang="en" > <![endif]-->
<html class="no-js" lang="en"
	data-useragent="Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Foundation Template | Workspace</title>
<meta name="description"
	content="Documentation and reference library for ZURB Foundation. JavaScript, CSS, components, grid and more." />
<meta name="author"
	content="ZURB, inc. ZURB network also includes zurb.com" />
<meta name="copyright" content="ZURB, inc. Copyright (c) 2013" />
<link rel="stylesheet" href="resources/css/foundation/foundation.css"
	type="text/css" />

<script type="text/javascript" src="resources/js/modernizr/modernizr.js"></script>
<script type="text/javascript" src="resources/js/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="resources/js/foundation/foundation.js"></script>
<script type="text/javascript" src="resources/js/cm.index.js"></script>
<script type="text/javascript" src="resources/js/mavin.utilities.js"></script>

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
	<div class="row">
		<div class="large-12 columns">

			<div class="hide-for-small">
				<div id="featured">
					<img alt="slide image"
						src="http://placehold.it/1000x400&amp;text=Slide%20Image">
				</div>
			</div>

			<div class="row">
				<div class="small-12 show-for-small">
					<br> <img
						src="http://placehold.it/1000x600&amp;text=For%20Small%20Screens">
				</div>
			</div>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="large-12 columns">
			<div class="row">

				<div class="large-3 small-6 columns">
					<img src="http://placehold.it/250x250&amp;text=Thumbnail">
					<h6 class="panel">Description</h6>
				</div>
				<div class="large-3 small-6 columns">
					<img src="http://placehold.it/250x250&amp;text=Thumbnail">
					<h6 class="panel">Description</h6>
				</div>
				<div class="large-3 small-6 columns">
					<img src="http://placehold.it/250x250&amp;text=Thumbnail">
					<h6 class="panel">Description</h6>
				</div>
				<div class="large-3 small-6 columns">
					<img src="http://placehold.it/250x250&amp;text=Thumbnail">
					<h6 class="panel">Description</h6>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="large-12 columns">
			<div class="row">

				<div class="large-8 columns">
					<div class="panel radius">
						<div class="row">
							<div class="large-6 small-6 columns">
								<h4>Content Manager</h4>
								<hr>
								<h5 class="subheader">Risus ligula, aliquam nec fermentum
									vitae, sollicitudin eget urna. Donec dignissim nibh fermentum
									odio ornare sagittis.</h5>
								<div class="show-for-small" style="text-align: center">
									<a class="small radius button" >Sign In!</a><br>
									<a class="small radius button" >Sign Up!</a>
								</div>
							</div>
							<div class="large-6 small-6 columns">
								<p>Suspendisse ultrices ornare tempor. Aenean eget ultricies
									libero. Phasellus non ipsum eros. Vivamus at dignissim massa.
									Aenean dolor libero, blandit quis interdum et, malesuada nec
									ligula. Nullam erat erat, eleifend sed pulvinar ac. Suspendisse
									ultrices ornare tempor. Aenean eget ultricies libero.</p>
							</div>
						</div>
					</div>
				</div>
				<div class="large-4 columns hide-for-small">
					<h4>Get In Touch!</h4>
					<hr>
					<a id="user_sign_in">
						<div class="panel radius callout" style="text-align: center">
							<strong>Sign In!</strong>
						</div>
					</a> <a id="user_sign_up">
						<div class="panel radius callout" style="text-align: center">
							<strong>Sign Up!</strong>
						</div>
					</a>
				</div>
			</div>
		</div>
	</div>
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
</body>
</html>