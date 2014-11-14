package com.cm.usermanagement.user;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StripeChargeEmailBuilder {
	private static final Logger log = Logger
			.getLogger(StripeChargeEmailBuilder.class.getName());

	static final String HTML_BEGIN = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns=\"http://www.w3.org/1999/xhtml\">";
	static final String HEAD = "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><meta name=\"viewport\" content=\"width=device-width\" /></head>";
	static final String BODY_BEGIN = "<body style=\"width: 100% !important; min-width: 100%; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\"> <style type=\"text/css\"> a:hover { color: #2795b6 !important; }a:active { color: #2795b6 !important; }a:visited { color: #2ba6cb !important; }h1 a:active { color: #2ba6cb !important; }h2 a:active { color: #2ba6cb !important; }h3 a:active { color: #2ba6cb !important; }h4 a:active { color: #2ba6cb !important; }h5 a:active { color: #2ba6cb !important; }h6 a:active { color: #2ba6cb !important; }h1 a:visited { color: #2ba6cb !important; }h2 a:visited { color: #2ba6cb !important; }h3 a:visited { color: #2ba6cb !important; }h4 a:visited { color: #2ba6cb !important; }h5 a:visited { color: #2ba6cb !important; }h6 a:visited { color: #2ba6cb !important; }table.button:hover td { background: #2795b6 !important; }table.button:visited td { background: #2795b6 !important; }table.button:active td { background: #2795b6 !important; }table.button:hover td a { color: #fff !important; }table.button:visited td a { color: #fff !important; }table.button:active td a { color: #fff !important; }table.button:hover td { background: #2795b6 !important; }table.tiny-button:hover td { background: #2795b6 !important; }table.small-button:hover td { background: #2795b6 !important; }table.medium-button:hover td { background: #2795b6 !important; }table.large-button:hover td { background: #2795b6 !important; }table.button:hover td a { color: #ffffff !important; }table.button:active td a { color: #ffffff !important; }table.button td a:visited { color: #ffffff !important; }table.tiny-button:hover td a { color: #ffffff !important; }table.tiny-button:active td a { color: #ffffff !important; }table.tiny-button td a:visited { color: #ffffff !important; }table.small-button:hover td a { color: #ffffff !important; }table.small-button:active td a { color: #ffffff !important; }table.small-button td a:visited { color: #ffffff !important; }table.medium-button:hover td a { color: #ffffff !important; }table.medium-button:active td a { color: #ffffff !important; }table.medium-button td a:visited { color: #ffffff !important; }table.large-button:hover td a { color: #ffffff !important; }table.large-button:active td a { color: #ffffff !important; }table.large-button td a:visited { color: #ffffff !important; }table.secondary:hover td { background: #d0d0d0 !important; color: #555; }table.secondary:hover td a { color: #555 !important; }table.secondary td a:visited { color: #555 !important; }table.secondary:active td a { color: #555 !important; }table.success:hover td { background: #457a1a !important; }table.alert:hover td { background: #970b0e !important; }table.facebook:hover td { background: #2d4473 !important; }table.twitter:hover td { background: #0087bb !important; }table.google-plus:hover td { background: #CC0000 !important; }@media only screen and (max-width: 600px) { table[class=\"body\"] img { width: auto !important; height: auto !important; } table[class=\"body\"] center { min-width: 0 !important; } table[class=\"body\"] .container { width: 95% !important; } table[class=\"body\"] .row { width: 100% !important; display: block !important; } table[class=\"body\"] .wrapper { display: block !important; padding-right: 0 !important; } table[class=\"body\"] .columns { table-layout: fixed !important; float: none !important; width: 100% !important; padding-right: 0px !important; padding-left: 0px !important; display: block !important; } table[class=\"body\"] .column { table-layout: fixed !important; float: none !important; width: 100% !important; padding-right: 0px !important; padding-left: 0px !important; display: block !important; } table[class=\"body\"] .wrapper.first .columns { display: table !important; } table[class=\"body\"] .wrapper.first .column { display: table !important; } table[class=\"body\"] table.columns td { width: 100% !important; } table[class=\"body\"] table.column td { width: 100% !important; } table[class=\"body\"] .columns td.one { width: 8.333333% !important; } table[class=\"body\"] .column td.one { width: 8.333333% !important; } table[class=\"body\"] .columns td.two { width: 16.666666% !important; } table[class=\"body\"] .column td.two { width: 16.666666% !important; } table[class=\"body\"] .columns td.three { width: 25% !important; } table[class=\"body\"] .column td.three { width: 25% !important; } table[class=\"body\"] .columns td.four { width: 33.333333% !important; } table[class=\"body\"] .column td.four { width: 33.333333% !important; } table[class=\"body\"] .columns td.five { width: 41.666666% !important; } table[class=\"body\"] .column td.five { width: 41.666666% !important; } table[class=\"body\"] .columns td.six { width: 50% !important; } table[class=\"body\"] .column td.six { width: 50% !important; } table[class=\"body\"] .columns td.seven { width: 58.333333% !important; } table[class=\"body\"] .column td.seven { width: 58.333333% !important; } table[class=\"body\"] .columns td.eight { width: 66.666666% !important; } table[class=\"body\"] .column td.eight { width: 66.666666% !important; } table[class=\"body\"] .columns td.nine { width: 75% !important; } table[class=\"body\"] .column td.nine { width: 75% !important; } table[class=\"body\"] .columns td.ten { width: 83.333333% !important; } table[class=\"body\"] .column td.ten { width: 83.333333% !important; } table[class=\"body\"] .columns td.eleven { width: 91.666666% !important; } table[class=\"body\"] .column td.eleven { width: 91.666666% !important; } table[class=\"body\"] .columns td.twelve { width: 100% !important; } table[class=\"body\"] .column td.twelve { width: 100% !important; } table[class=\"body\"] td.offset-by-one { padding-left: 0 !important; } table[class=\"body\"] td.offset-by-two { padding-left: 0 !important; } table[class=\"body\"] td.offset-by-three { padding-left: 0 !important; } table[class=\"body\"] td.offset-by-four { padding-left: 0 !important; } table[class=\"body\"] td.offset-by-five { padding-left: 0 !important; } table[class=\"body\"] td.offset-by-six { padding-left: 0 !important; } table[class=\"body\"] td.offset-by-seven { padding-left: 0 !important; } table[class=\"body\"] td.offset-by-eight { padding-left: 0 !important; } table[class=\"body\"] td.offset-by-nine { padding-left: 0 !important; } table[class=\"body\"] td.offset-by-ten { padding-left: 0 !important; } table[class=\"body\"] td.offset-by-eleven { padding-left: 0 !important; } table[class=\"body\"] table.columns td.expander { width: 1px !important; } table[class=\"body\"] .right-text-pad { padding-left: 10px !important; } table[class=\"body\"] .text-pad-right { padding-left: 10px !important; } table[class=\"body\"] .left-text-pad { padding-right: 10px !important; } table[class=\"body\"] .text-pad-left { padding-right: 10px !important; } table[class=\"body\"] .hide-for-small { display: none !important; } table[class=\"body\"] .show-for-desktop { display: none !important; } table[class=\"body\"] .show-for-small { display: inherit !important; } table[class=\"body\"] .hide-for-desktop { display: inherit !important; } table[class=\"body\"] .right-text-pad { padding-left: 10px !important; } table[class=\"body\"] .left-text-pad { padding-right: 10px !important; } } </style>";

	static final String TABLE_BEGIN = "<table class=\"body\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; height: 100%; width: 100%; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"center\" align=\"center\" valign=\"top\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: center; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\"><center style=\"width: 100%; min-width: 580px;\">";
	static final String HEADER = "<table class=\"row header\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 100%; position: relative; background: #1fad9d; padding: 0px;\" bgcolor=\"#1fad9d\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"center\" align=\"center\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: center; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\" valign=\"top\"><center style=\"width: 100%; min-width: 580px;\"> <table class=\"container\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: inherit; width: 580px; margin: 0 auto; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"wrapper last\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; position: relative; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 10px 0px 0px;\" align=\"left\" valign=\"top\"><table class=\"twelve columns\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 580px; margin: 0 auto; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"six sub-columns\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; min-width: 0px; width: 50%; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0px 10px 10px 0px;\" align=\"left\" valign=\"top\"><a href=\"http://skok.co\"><img src=\"http://skok.co/resources/images/cm/logo-72x72.png\" style=\"outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; width: auto; max-width: 100%; float: left; clear: both; display: block;\" align=\"left\" /></a></td> <td class=\"six sub-columns last\" style=\"text-align: right; vertical-align: middle; word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; min-width: 0px; width: 50%; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0px 0px 10px;\" align=\"right\" valign=\"middle\"><span class=\"template-label\" style=\"color: #ffffff; font-weight: bold; font-size: 11px;\">Skok</span></td> <td class=\"expander\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; visibility: hidden; width: 0px; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\" align=\"left\" valign=\"top\"></td> </tr> </table></td> </tr> </table> </center></td> </tr> </table>";
	static final String CONTAINER_BEGIN = "<table class=\"container\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: inherit; width: 580px; margin: 0 auto; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\" align=\"left\" valign=\"top\"> <table class=\"row\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 100%; position: relative; display: block; padding: 0px;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"wrapper last\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; position: relative; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 10px 0px 0px;\" align=\"left\" valign=\"top\">";

	static final String CONTAINER_HEADER_BEGIN = "<table class=\"twelve columns\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 580px; margin: 0 auto; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0px 0px 10px;\" align=\"left\" valign=\"top\">";
	static final String CONTAINER_HEADER_END = "</td> <td class=\"expander\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; visibility: hidden; width: 0px; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\" align=\"left\" valign=\"top\"></td> </tr> </table></td> </tr> </table>";

	static final String CONTAINER_CALLOUT_BEGIN = "<table class=\"row callout\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 100%; position: relative; display: block; padding: 0px;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"wrapper last\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; position: relative; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 10px 0px 20px;\" align=\"left\" valign=\"top\"><table class=\"twelve columns\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 580px; margin: 0 auto; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"panel\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; background: #ECF8FF; margin: 0; padding: 10px; border: 1px solid #b9e5ff;\" align=\"left\" bgcolor=\"#ECF8FF\" valign=\"top\">";
	static final String CONTAINER_CALLOUT_END = "</td> <td class=\"expander\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; visibility: hidden; width: 0px; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\" align=\"left\" valign=\"top\"></td> </tr> </table></td> </tr> </table>";

	static final String CONTAINER_SOCIAL = "<table class=\"row footer\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 100%; position: relative; padding: 0px;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"wrapper\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; position: relative; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; background: #ebebeb; margin: 0; padding: 10px 20px 0px 0px;\" align=\"left\" bgcolor=\"#ebebeb\" valign=\"top\"><table class=\"six columns\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 280px; margin: 0 auto; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"left-text-pad\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0px 0px 10px 10px;\" align=\"left\" valign=\"top\"><h5 style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 1.3; word-break: normal; font-size: 24px; margin: 0; padding: 0 0 10px;\" align=\"left\">Connect With Us:</h5><table class=\"tiny-button facebook\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 100%; overflow: hidden; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: center; color: #ffffff; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; display: block; width: auto !important; background: #3b5998; margin: 0; padding: 5px 0 4px; border: 1px solid #2d4473;\" align=\"center\" bgcolor=\"#3b5998\" valign=\"top\"><a href=\"https://www.facebook.com/skokco\" style=\"color: #ffffff; text-decoration: none; font-weight: normal; font-family: Helvetica, Arial, sans-serif; font-size: 12px;\">Facebook</a> </td> </tr> </table> <br /> <table class=\"tiny-button twitter\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 100%; overflow: hidden; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: center; color: #ffffff; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; display: block; width: auto !important; background: #00acee; margin: 0; padding: 5px 0 4px; border: 1px solid #0087bb;\" align=\"center\" bgcolor=\"#00acee\" valign=\"top\"><a href=\"https://twitter.com/SkokCo\" style=\"color: #ffffff; text-decoration: none; font-weight: normal; font-family: Helvetica, Arial, sans-serif; font-size: 12px;\">Twitter</a> </td> </tr> </table> <br /> <table class=\"tiny-button google-plus\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 100%; overflow: hidden; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: center; color: #ffffff; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; display: block; width: auto !important; background: #DB4A39; margin: 0; padding: 5px 0 4px; border: 1px solid #cc0000;\" align=\"center\" bgcolor=\"#DB4A39\" valign=\"top\"><a href=\"https://plus.google.com/+SkokCoPage\" style=\"color: #ffffff; text-decoration: none; font-weight: normal; font-family: Helvetica, Arial, sans-serif; font-size: 12px;\">Google +</a></td> </tr> </table> </td> <td class=\"expander\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; visibility: hidden; width: 0px; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\" align=\"left\" valign=\"top\"></td> </tr> </table> </td> <td class=\"wrapper last\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; position: relative; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; background: #ebebeb; margin: 0; padding: 10px 0px 0px;\" align=\"left\" bgcolor=\"#ebebeb\" valign=\"top\"><table class=\"six columns\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 280px; margin: 0 auto; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"last right-text-pad\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0px 0px 10px;\" align=\"left\" valign=\"top\"> <h5 style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 1.3; word-break: normal; font-size: 24px; margin: 0; padding: 0 0 10px;\" align=\"left\">Contact Info:</h5> <p style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 19px; font-size: 14px; margin: 0 0 10px; padding: 0;\" align=\"left\"> Email: <a href=\"mailto:support@skok.co\" style=\"color: #2ba6cb; text-decoration: none;\">support@skok.co</a> </p> </td> <td class=\"expander\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; visibility: hidden; width: 0px; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\" align=\"left\" valign=\"top\"></td> </tr> </table> </td> </tr> </table>";
	static final String CONTAINER_FOOTER = "<table class=\"row\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 100%; position: relative; display: block; padding: 0px;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td class=\"wrapper last\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; position: relative; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 10px 0px 0px;\" align=\"left\" valign=\"top\"> <table class=\"twelve columns\" style=\"border-spacing: 0; border-collapse: collapse; vertical-align: top; text-align: left; width: 580px; margin: 0 auto; padding: 0;\"> <tr style=\"vertical-align: top; text-align: left; padding: 0;\" align=\"left\"> <td align=\"center\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0px 0px 10px;\" valign=\"top\"><center style=\"width: 100%; min-width: 580px;\"> <p style=\"text-align: center; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0 0 10px; padding: 0;\" align=\"center\"> <a href=\"http://skok.co/site/terms\" style=\"color: #2ba6cb; text-decoration: none;\">Terms</a> | <a href=\"http://skok.co/site/privacy\" style=\"color: #2ba6cb; text-decoration: none;\">Privacy</a> </p> </center></td> <td class=\"expander\" style=\"word-break: break-word; -webkit-hyphens: auto; -moz-hyphens: auto; hyphens: auto; border-collapse: collapse !important; vertical-align: top; text-align: left; visibility: hidden; width: 0px; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; line-height: 19px; font-size: 14px; margin: 0; padding: 0;\" align=\"left\" valign=\"top\"></td> </tr> </table> </td> </tr> </table>";
	static final String CONTAINER_END = "<!-- container end below --></td></tr></table>";
	static final String TABLE_END = "<!-- container end below --></td> </tr> </table>";
	static final String BODY_END = "</body>";
	static final String HTML_END = "</html>";

	@Autowired
	private UserService userService;

	public String build(String htmlFormattedHeader, String htmlFormattedCallout) {
		log.info("Entering build()");

		StringBuilder email = new StringBuilder();
		// email.append(DOCTYPE);
		email.append(HTML_BEGIN);
		email.append(HEAD);
		email.append(BODY_BEGIN);

		email.append(TABLE_BEGIN);
		email.append(HEADER);
		email.append(CONTAINER_BEGIN);

		email.append(CONTAINER_HEADER_BEGIN);
		email.append(htmlFormattedHeader);
		email.append(CONTAINER_HEADER_END);

		email.append(CONTAINER_CALLOUT_BEGIN);
		email.append(htmlFormattedCallout);
		email.append(CONTAINER_CALLOUT_END);

		email.append(CONTAINER_SOCIAL);
		email.append(CONTAINER_FOOTER);
		email.append(CONTAINER_END);

		email.append(TABLE_END);
		email.append(BODY_END);
		email.append(HTML_END);
		log.info("Exiting build()");
		return email.toString();
	}

	public static void main(String[] args) {
		StringBuilder lHtmlFormattedHeader = new StringBuilder();

		lHtmlFormattedHeader.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">Welcome to Skok.</p>");
		lHtmlFormattedHeader
				.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">Skok is an Advanced Content Management and  Delivery platform for your Mobile Apps. Skok delivers rich content to your Mobile Apps, and stores it locally on mobile devices.</p>");
		lHtmlFormattedHeader
				.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">This elevates user experience of your Mobile Apps. Your content loads much faster, and users can engage with your rich content, even if they lose their data connection.</p>");

		lHtmlFormattedHeader
				.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\">You can find out more at <a href=\"http://skok.co/docs/overview\">Skok</a> </p>");

		StringBuilder lHtmlFormattedCallout = new StringBuilder();
		lHtmlFormattedCallout.append("<p class=\"lead\" style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-weight: normal; text-align: left; line-height: 21px; font-size: 18px; margin: 0 0 10px; padding: 0;\" align=\"left\"><b>Powerful New Features</b></p>");
		lHtmlFormattedCallout.append("<ol>");
		lHtmlFormattedCallout.append("<li>Cloud-driven Architecture</li>");
		lHtmlFormattedCallout
				.append("<li>Advanced Content Management Platform</li>");
		lHtmlFormattedCallout.append("<li>Streamlined Content Delivery</li>");
		lHtmlFormattedCallout.append("<li>Auto-sizing of Images</li>");
		lHtmlFormattedCallout
				.append("<li>Say Goodbye to Google Play APK Expansion Files</li>");
		lHtmlFormattedCallout.append("<li>Continuous Content Updates</li>");
		lHtmlFormattedCallout.append("<li>No Extra Coding Required</li>");
		lHtmlFormattedCallout
				.append("<li>Easily-pluggable &amp; Feature-rich SDK</li>");
		lHtmlFormattedCallout.append("<li>Mobile Device Storage</li>");
		lHtmlFormattedCallout.append("<li>Advanced Caching on Device</li>");
		lHtmlFormattedCallout.append("<li>Non-Blocking Content Downloads</li>");
		lHtmlFormattedCallout
				.append("<li>Manages Content Downloads over Spotty Networks</li>");
		lHtmlFormattedCallout.append("<li>Download Notifications</li>");
		lHtmlFormattedCallout
				.append("<li>Analytics to Track Usage Statistics of your Content</li>");
		lHtmlFormattedCallout.append("</ol>");
		System.out.println(new StripeChargeEmailBuilder().build(
				lHtmlFormattedHeader.toString(),
				lHtmlFormattedCallout.toString()));
	}
}
