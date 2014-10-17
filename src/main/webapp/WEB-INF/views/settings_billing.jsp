
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

<title>Content Manager</title>
<jsp:include page="resources.jsp" flush="true"></jsp:include>

<!-- Begin Custom -->
<script type="text/javascript"
	src="/resources/javascripts/cm/cm.account.settings.js"></script>
<!-- End Custom -->
<script type="text/javascript" src="https://js.stripe.com/v2/"></script>

</head>
<body>


	<jsp:include page="common.jsp"></jsp:include>
	<jsp:include page="top_bar.jsp"></jsp:include>
	<section>
		<div class="row full-width">
			<h2 class="text-center gray">Billing</h2>
			<div class="line">
				<img src="/resources/images/cm/line.png" alt="line" />
			</div>

			<form id="updateBillingForm" name="updateBillingForm"
				data-abide="ajax">
				<div id="billing_not_enabled_message" style="display: none">
					<ul id="vision">
						<li><div>
								<i class="fi-lightbulb"></i>
							</div> <span>Please upgrade your plan to enable billing</span>
							<p class="clearfix"></p></li>
					</ul>
				</div>
				<div id="user_billing_container">
					<div id="user_billing_progress_bar_top" style="display: none">
						<div class="progress radius">
							<span class="meter"
								style="width: 40%; background-color: #5cb85c;">Updating...</span>
						</div>
					</div>
					<div id="payment_info_updated_message" style="display: none">
						<ul id="vision">
							<li><div>
									<i class="fi-lightbulb"></i>
								</div> <span>Your payment information has been updated.</span>
								<p class="clearfix"></p></li>
						</ul>
					</div>
					<div>
						<div class="large-12 columns">
							<dl>
								<dt>
									Credit card number - <a href="javascript:void(0);"
										id="new_card">Enter a new card</a> <a
										href="javascript:void(0);" id="new_card_cancel"
										style="display: none">cancel</a>
								</dt>
								<dd id="existing_card_info"></dd>
							</dl>
						</div>
					</div>
					<div id="new_card_container" style="display: none">
						<div class="large-9 columns">
							<label for="card_number">Credit card number <small>required</small></label>
							<input class="form-control" type="text" id="card_number"
								name="card_number" size="20" pattern="card" />
						</div>

						<div class="large-3 columns">
							<label for="card_cvc">CVV <small>required</small> <span
								data-tooltip class="has-tip"
								title="The CVV Number (&quot; Card
										Verification Value&quot;) on your credit card or debit card is
										a 3 digit number on VISA&reg;, MasterCard&reg; and Discover&reg; branded
										credit and debit cards. On your American Express&reg; branded
										credit or debit card it is a 4 digit numeric code."><i
									class="fi-info light_gray"></i></span><input class="form-control"
								id="card_cvc" name="card_cvc" type="text" size="4" pattern="cvv" /></label>
						</div>
					</div>
					<div>
						<div class="large-4 columns">
							<label for="card_exp_month">Expiration Month <small>required</small>
								<select id="card_exp_month" name="card_exp_month"
								required="required" data-abide-validator="validateExpiry"><option
										value=""></option>
									<option value="1">01</option>
									<option value="2">02</option>
									<option value="3">03</option>
									<option value="4">04</option>
									<option value="5">05</option>
									<option value="6">06</option>
									<option value="7">07</option>
									<option value="8">08</option>
									<option value="9">09</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option></select></label>

						</div>
						<div class="large-4 columns">
							<label for="card_exp_year">Expiration Year <small>required</small>
								<select id="card_exp_year" name="card_exp_year"
								required="required" data-abide-validator="validateExpiry"><option
										value=""></option>
									<option value="2014">2014</option>
									<option value="2015">2015</option>
									<option value="2016">2016</option>
									<option value="2017">2017</option>
									<option value="2018">2018</option>
									<option value="2019">2019</option>
									<option value="2020">2020</option>
									<option value="2021">2021</option>
									<option value="2022">2022</option>
									<option value="2023">2023</option>
									<option value="2024">2024</option></select></label>

						</div>

						<div class="large-4 columns">
							<label for="card_address_zip">Postal Code <small>required</small>
								<input class="form-control" type="text" id="card_address_zip"
								name="card_address_zip" required="required" /></label>
						</div>
					</div>
					<div>
						<div class="large-12 columns">
							<hr>
						</div>
					</div>
					<div>
						<div class="large-12 columns">
							<label for="card_name">Name <input class="form-control"
								type="text" id="card_name" name="card_name" /></label>
						</div>
					</div>

					<div>
						<div class="large-12 columns">
							<label for="card_address_line_1">Address line 1 <input
								class="form-control" type="text" id="card_address_line_1"
								name="card_address_line_1" /></label>
						</div>
					</div>
					<div>
						<div class="large-12 columns">
							<label for="card_address_line_2">Address line 2 <input
								class="form-control" type="text" id="card_address_line_2"
								name="card_address_line_2" /></label>
						</div>
					</div>
					<div>
						<div class="large-12 columns">
							<label for="card_address_city">City <input
								class="form-control" type="text" id="card_address_city"
								name="card_address_city" /></label>
						</div>
					</div>
					<div>
						<div class="large-12 columns">
							<label for="card_address_country">Country<select
								id="card_address_country">
									<option value selected=selected>Choose country</option>
									<option value="CHN">China</option>
									<option value="DEU">Germany</option>
									<option value="GBR">United Kingdom</option>
									<option value="IND">India</option>
									<option value="AFG">Afghanistan</option>
									<option value="ALA">Åland</option>
									<option value="ALB">Albania</option>
									<option value="DZA">Algeria</option>
									<option value="ASM">American Samoa</option>
									<option value="AND">Andorra</option>
									<option value="AGO">Angola</option>
									<option value="AIA">Anguilla</option>
									<option value="ATA">Antarctica</option>
									<option value="ATG">Antigua and Barbuda</option>
									<option value="ARG">Argentina</option>
									<option value="ARM">Armenia</option>
									<option value="ABW">Aruba</option>
									<option value="AUS">Australia</option>
									<option value="AUT">Austria</option>
									<option value="AZE">Azerbaijan</option>
									<option value="BHS">Bahamas</option>
									<option value="BHR">Bahrain</option>
									<option value="BGD">Bangladesh</option>
									<option value="BRB">Barbados</option>
									<option value="BLR">Belarus</option>
									<option value="BEL">Belgium</option>
									<option value="BLZ">Belize</option>
									<option value="BEN">Benin</option>
									<option value="BMU">Bermuda</option>
									<option value="BTN">Bhutan</option>
									<option value="BOL">Bolivia</option>
									<option value="BIH">Bosnia and Herzegovina</option>
									<option value="BWA">Botswana</option>
									<option value="BVT">Bouvet Island</option>
									<option value="BRA">Brazil</option>
									<option value="IOT">British Indian Ocean Territory</option>
									<option value="BRN">Brunei Darussalam</option>
									<option value="BGR">Bulgaria</option>
									<option value="BFA">Burkina Faso</option>
									<option value="BDI">Burundi</option>
									<option value="KHM">Cambodia</option>
									<option value="CMR">Cameroon</option>
									<option value="CAN">Canada</option>
									<option value="CPV">Cape Verde</option>
									<option value="CYM">Cayman Islands</option>
									<option value="CAF">Central African Republic</option>
									<option value="TCD">Chad</option>
									<option value="CHL">Chile</option>
									<option value="CHN">China</option>
									<option value="CXR">Christmas Island</option>
									<option value="CCK">Cocos (Keeling) Islands</option>
									<option value="COL">Colombia</option>
									<option value="COM">Comoros</option>
									<option value="COG">Congo (Brazzaville)</option>
									<option value="COD">Congo (Kinshasa)</option>
									<option value="COK">Cook Islands</option>
									<option value="CRI">Costa Rica</option>
									<option value="CIV">Côte d&#39;Ivoire</option>
									<option value="HRV">Croatia</option>
									<option value="CUB">Cuba</option>
									<option value="CYP">Cyprus</option>
									<option value="CZE">Czech Republic</option>
									<option value="DNK">Denmark</option>
									<option value="DJI">Djibouti</option>
									<option value="DMA">Dominica</option>
									<option value="DOM">Dominican Republic</option>
									<option value="ECU">Ecuador</option>
									<option value="EGY">Egypt</option>
									<option value="SLV">El Salvador</option>
									<option value="GNQ">Equatorial Guinea</option>
									<option value="ERI">Eritrea</option>
									<option value="EST">Estonia</option>
									<option value="ETH">Ethiopia</option>
									<option value="FLK">Falkland Islands</option>
									<option value="FRO">Faroe Islands</option>
									<option value="FJI">Fiji</option>
									<option value="FIN">Finland</option>
									<option value="FRA">France</option>
									<option value="GUF">French Guiana</option>
									<option value="PYF">French Polynesia</option>
									<option value="ATF">French Southern Lands</option>
									<option value="GAB">Gabon</option>
									<option value="GMB">Gambia</option>
									<option value="GEO">Georgia</option>
									<option value="DEU">Germany</option>
									<option value="GHA">Ghana</option>
									<option value="GIB">Gibraltar</option>
									<option value="GRC">Greece</option>
									<option value="GRL">Greenland</option>
									<option value="GRD">Grenada</option>
									<option value="GLP">Guadeloupe</option>
									<option value="GUM">Guam</option>
									<option value="GTM">Guatemala</option>
									<option value="GGY">Guernsey</option>
									<option value="GIN">Guinea</option>
									<option value="GNB">Guinea-Bissau</option>
									<option value="GUY">Guyana</option>
									<option value="HTI">Haiti</option>
									<option value="HMD">Heard and McDonald Islands</option>
									<option value="HND">Honduras</option>
									<option value="HKG">Hong Kong</option>
									<option value="HUN">Hungary</option>
									<option value="ISL">Iceland</option>
									<option value="IND">India</option>
									<option value="IDN">Indonesia</option>
									<option value="IRN">Iran</option>
									<option value="IRQ">Iraq</option>
									<option value="IRL">Ireland</option>
									<option value="IMN">Isle of Man</option>
									<option value="ISR">Israel</option>
									<option value="ITA">Italy</option>
									<option value="JAM">Jamaica</option>
									<option value="JPN">Japan</option>
									<option value="JEY">Jersey</option>
									<option value="JOR">Jordan</option>
									<option value="KAZ">Kazakhstan</option>
									<option value="KEN">Kenya</option>
									<option value="KIR">Kiribati</option>
									<option value="PRK">Korea, North</option>
									<option value="KOR">Korea, South</option>
									<option value="KWT">Kuwait</option>
									<option value="KGZ">Kyrgyzstan</option>
									<option value="LAO">Laos</option>
									<option value="LVA">Latvia</option>
									<option value="LBN">Lebanon</option>
									<option value="LSO">Lesotho</option>
									<option value="LBR">Liberia</option>
									<option value="LBY">Libya</option>
									<option value="LIE">Liechtenstein</option>
									<option value="LTU">Lithuania</option>
									<option value="LUX">Luxembourg</option>
									<option value="MAC">Macau</option>
									<option value="MKD">Macedonia</option>
									<option value="MDG">Madagascar</option>
									<option value="MWI">Malawi</option>
									<option value="MYS">Malaysia</option>
									<option value="MDV">Maldives</option>
									<option value="MLI">Mali</option>
									<option value="MLT">Malta</option>
									<option value="MHL">Marshall Islands</option>
									<option value="MTQ">Martinique</option>
									<option value="MRT">Mauritania</option>
									<option value="MUS">Mauritius</option>
									<option value="MYT">Mayotte</option>
									<option value="MEX">Mexico</option>
									<option value="FSM">Micronesia</option>
									<option value="MDA">Moldova</option>
									<option value="MCO">Monaco</option>
									<option value="MNG">Mongolia</option>
									<option value="MNE">Montenegro</option>
									<option value="MSR">Montserrat</option>
									<option value="MAR">Morocco</option>
									<option value="MOZ">Mozambique</option>
									<option value="MMR">Myanmar</option>
									<option value="NAM">Namibia</option>
									<option value="NRU">Nauru</option>
									<option value="NPL">Nepal</option>
									<option value="NLD">Netherlands</option>
									<option value="ANT">Netherlands Antilles</option>
									<option value="NCL">New Caledonia</option>
									<option value="NZL">New Zealand</option>
									<option value="NIC">Nicaragua</option>
									<option value="NER">Niger</option>
									<option value="NGA">Nigeria</option>
									<option value="NIU">Niue</option>
									<option value="NFK">Norfolk Island</option>
									<option value="MNP">Northern Mariana Islands</option>
									<option value="NOR">Norway</option>
									<option value="OMN">Oman</option>
									<option value="PAK">Pakistan</option>
									<option value="PLW">Palau</option>
									<option value="PSE">Palestine</option>
									<option value="PAN">Panama</option>
									<option value="PNG">Papua New Guinea</option>
									<option value="PRY">Paraguay</option>
									<option value="PER">Peru</option>
									<option value="PHL">Philippines</option>
									<option value="PCN">Pitcairn</option>
									<option value="POL">Poland</option>
									<option value="PRT">Portugal</option>
									<option value="PRI">Puerto Rico</option>
									<option value="QAT">Qatar</option>
									<option value="REU">Reunion</option>
									<option value="ROU">Romania</option>
									<option value="RUS">Russian Federation</option>
									<option value="RWA">Rwanda</option>
									<option value="BLM">Saint Barthélemy</option>
									<option value="SHN">Saint Helena</option>
									<option value="KNA">Saint Kitts and Nevis</option>
									<option value="LCA">Saint Lucia</option>
									<option value="MAF">Saint Martin (French part)</option>
									<option value="SPM">Saint Pierre and Miquelon</option>
									<option value="VCT">Saint Vincent and the Grenadines</option>
									<option value="WSM">Samoa</option>
									<option value="SMR">San Marino</option>
									<option value="STP">Sao Tome and Principe</option>
									<option value="SAU">Saudi Arabia</option>
									<option value="SEN">Senegal</option>
									<option value="SRB">Serbia</option>
									<option value="SYC">Seychelles</option>
									<option value="SLE">Sierra Leone</option>
									<option value="SGP">Singapore</option>
									<option value="SVK">Slovakia</option>
									<option value="SVN">Slovenia</option>
									<option value="SLB">Solomon Islands</option>
									<option value="SOM">Somalia</option>
									<option value="ZAF">South Africa</option>
									<option value="SGS">South Georgia and South Sandwich
										Islands</option>
									<option value="ESP">Spain</option>
									<option value="LKA">Sri Lanka</option>
									<option value="SDN">Sudan</option>
									<option value="SUR">Suriname</option>
									<option value="SJM">Svalbard and Jan Mayen Islands</option>
									<option value="SWZ">Swaziland</option>
									<option value="SWE">Sweden</option>
									<option value="CHE">Switzerland</option>
									<option value="SYR">Syria</option>
									<option value="TWN">Taiwan</option>
									<option value="TJK">Tajikistan</option>
									<option value="TZA">Tanzania</option>
									<option value="THA">Thailand</option>
									<option value="TLS">Timor-Leste</option>
									<option value="TGO">Togo</option>
									<option value="TKL">Tokelau</option>
									<option value="TON">Tonga</option>
									<option value="TTO">Trinidad and Tobago</option>
									<option value="TUN">Tunisia</option>
									<option value="TUR">Turkey</option>
									<option value="TKM">Turkmenistan</option>
									<option value="TCA">Turks and Caicos Islands</option>
									<option value="TUV">Tuvalu</option>
									<option value="UGA">Uganda</option>
									<option value="UKR">Ukraine</option>
									<option value="ARE">United Arab Emirates</option>
									<option value="GBR">United Kingdom</option>
									<option value="UMI">United States Minor Outlying
										Islands</option>
									<option value="USA">United States of America</option>
									<option value="URY">Uruguay</option>
									<option value="UZB">Uzbekistan</option>
									<option value="VUT">Vanuatu</option>
									<option value="VAT">Vatican City</option>
									<option value="VEN">Venezuela</option>
									<option value="VNM">Vietnam</option>
									<option value="VGB">Virgin Islands, British</option>
									<option value="VIR">Virgin Islands, U.S.</option>
									<option value="WLF">Wallis and Futuna Islands</option>
									<option value="ESH">Western Sahara</option>
									<option value="YEM">Yemen</option>
									<option value="ZMB">Zambia</option>
									<option value="ZWE">Zimbabwe</option>
							</select></label>
						</div>
					</div>

					<div id="card_address_state_container">
						<div class="large-12 columns">
							<label for="card_address_state">State<select
								id="card_address_state">
									<option></option>
									<option>Alabama</option>
									<option>Alaska</option>
									<option>Arizona</option>
									<option>Arkansas</option>
									<option>California</option>
									<option>Colorado</option>
									<option>Connecticut</option>
									<option>Delaware</option>
									<option>District of Columbia</option>
									<option>Florida</option>
									<option>Georgia</option>
									<option>Hawaii</option>
									<option>Idaho</option>
									<option>Illinois</option>
									<option>Indiana</option>
									<option>Iowa</option>
									<option>Kansas</option>
									<option>Kentucky</option>
									<option>Louisiana</option>
									<option>Maine</option>
									<option>Maryland</option>
									<option>Massachusetts</option>
									<option>Michigan</option>
									<option>Minnesota</option>
									<option>Mississippi</option>
									<option>Missouri</option>
									<option>Montana</option>
									<option>Nebraska</option>
									<option>Nevada</option>
									<option>New Hampshire</option>
									<option>New Jersey</option>
									<option>New Mexico</option>
									<option>New York</option>
									<option>North Carolina</option>
									<option>North Dakota</option>
									<option>Ohio</option>
									<option>Oklahoma</option>
									<option>Oregon</option>
									<option>Pennsylvania</option>
									<option>Puerto Rico</option>
									<option>Rhode Island</option>
									<option>South Carolina</option>
									<option>South Dakota</option>
									<option>Tennessee</option>
									<option>Texas</option>
									<option>Utah</option>
									<option>Vermont</option>
									<option>Virginia</option>
									<option>Washington</option>
									<option>West Virginia</option>
									<option>Wisconsin</option>
									<option>Wyoming</option>
							</select></label>

						</div>
					</div>

					<div id="cm_errors_container" style="display: none">
						<ul id="vision">
							<li><div>
									<i class="fi-alert"></i>
								</div> <span id="payment_info_update_errors"></span>
								<p class="clearfix"></p></li>
						</ul>
					</div>

					<div>
						<div class="large-12 columns">
							<button id="payment_info_update_button"
								class="button radius btn-default">update</button>
						</div>
					</div>
					<div id="user_billing_progress_bar_bottom" style="display: none">
						<div class="progress radius">
							<span class="meter"
								style="width: 40%; background-color: #5cb85c;">Updating...</span>
						</div>
					</div>
				</div>
			</form>
		</div>
	</section>

	<br>
	<section id="footer">

		<jsp:include page="footer.jsp"></jsp:include>
	</section>


</body>
</html>


