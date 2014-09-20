
<!doctype html>
<!--[if IE 9]><html class="lt-ie10" lang="en" > <![endif]-->
<html class="no-js" lang="en"
	data-useragent="Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>

<head>
<jsp:include page="meta_tags.jsp"></jsp:include>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

<title>Content Manager</title>

<jsp:include page="resources.jsp" flush="true"></jsp:include>

<!-- Begin Custom -->
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.index.js"></script>
<!-- End Custom -->

</head>
<body>
	<jsp:include page="top_bar.jsp"></jsp:include>

	<br>
	<section id="services" class="display-table">
		<div class="container va-align">
			<h2 class="text-center">Content Management service for mobile
				applications</h2>
			<p class="text-center page_sub_heading">Manage all your rich
				media (images, videos) mobile content here</p>
			<div class="line">
				<img src="/resources/images/box/line.png" alt="line" />
			</div>
			<br> <br>
			<div class="visible text-center">
				<div class="row full-width">
					<div class="large-4 columns">
						<div id="one">
							<div class="box">
								<i class="fi-closed-caption"></i>
							</div>

							<h5>Lurem Ipsum</h5>
							<div class="service-content">Lorem ipsum dolor sit amet sed
								do eiusmod tempor incididunt ut labore et dolore magna aliqua.</div>
						</div>
					</div>
					<div class="large-4 columns">
						<div id="two">
							<div class="box">
								<i class="fi-elevator"></i>
							</div>
							<h5>Lurem Ipsum</h5>
							Lorem ipsum dolor elit, sed do eiusmod tempor incididunt ut
							labore et dolore magna aliqua.
						</div>
					</div>
					<div class="large-4 columns">
						<div id="three">
							<div class="box">
								<i class="fi-shopping-bag"></i>
							</div>
							<h5>Lurem Ipsum</h5>
							Lorem ipsum dolor sit amet, adipisicing elit tempor incididunt ut
							labore et dolore magna aliqua.
						</div>
					</div>
				</div>
			</div>
		</div>

	</section>
	<br>
	<br>
	<br>
	<section id="message">
		<div class="overlay display-table">
			<div class="container text-center va-align">
				<div class="visible">
					<h2 class="white">Sign Up Now! It's easy and free.</h2>
					<br> <a href="<c:url value="/signup"/>"
						class="button radius btn-default">Sign Up </a>
				</div>
			</div>
		</div>
	</section>
	<section id="portfolio">
		<br> <br>
		<h2 class="text-center">Features</h2>
		<p class="text-center page_sub_heading">Lorem ipsum dolor sit
			amet, consectetur adipisicing elit.</p>
		<div class="line">
			<img src="/resources/images/box/line.png" alt="line" />
		</div>
		<div id="owl-demo" class="owl-carousel owl-theme visible">
			<div class="item">
				<img src="/resources/images/box/owl1.jpeg" alt="owl" />
				<h3 class="gray">Lorem</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>

			<div class="item">
				<img src="/resources/images/box/owl2.jpeg" alt="img" />
				<h3 class="gray">Accusamus</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>

			<div class="item">
				<img src="/resources/images/box/owl3.jpeg" alt="img" />

				<h3 class="gray">Dolores</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>
			<div class="item">
				<img src="/resources/images/box/owl4.jpeg" alt="img" />
				<h3 class="gray">Excepturi</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>

			<div class="item">
				<img src="/resources/images/box/owl5.jpeg" alt="img" />
				<h3 class="gray">Blanditiis</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>
			<div class="item">
				<img src="/resources/images/box/owl6.jpeg" alt="img" />
				<h3 class="gray">Blanditiis</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>
			<div class="item">
				<img src="/resources/images/box/owl7.jpeg" alt="img" />
				<h3 class="gray">Provident</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>
			<div class="item">
				<img src="/resources/images/box/owl2.jpeg" alt="img" />
				<h3 class="gray">Deleniti</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>
			<div class="item">
				<img src="/resources/images/box/owl3.jpeg" alt="img" />
				<h3 class="gray">Voluptatum</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>
			<div class="item">
				<img src="/resources/images/box/owl4.jpeg" alt="img" />
				<h3 class="gray">Qui</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>
			<div class="item">
				<img src="/resources/images/box/owl5.jpeg" alt="img" />
				<h3 class="gray">Odio</h3>
				<div class="gray text_left">At vero eos et accusamus et iusto
					odio dignissimos ducimus qui blanditiis praesentium voluptatum
					deleniti atque corrupti quos dolores et quas molestias excepturi
					sint occaecati cupiditate non provident</div>
			</div>


		</div>

	</section>
	<br>
	<br>

	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>

</body>
</html>