package com.cm.usermanagement.user;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cm.config.Configuration;

@Component
public class ForgotPasswordEmailBuilder {
	private static final Logger log = Logger
			.getLogger(ForgotPasswordEmailBuilder.class.getName());

	static final String TABLE_STYLE = "width: 100%;border: 1px solid #146eb4;color: #333;background-color: #fff;clear: both;padding: 0;margin: 0 0 1em 0;white-space: normal;";
	static final String INNER_TABLE_STYLE = "width: 100%;color: #333;background-color: #fff;clear: both;padding: 0;margin: 0 0 1em 0;white-space: normal;";
	static final String TR_STYLE = "padding: 4px 4px;vertical-align: top;text-align: left;";
	static final String TR_ALT_ROW_STYLE = TR_STYLE + "background: #f4f4f4;";
	static final String TH_STYLE = "color: #fff;font-size: 100%;background-color: #146eb4;text-align: left;padding: 1px;";

	static final String A_STYLE = "color: #0066CC;background-color: #FFFFFF;font-weight: normal;text-decoration: none;";
	static final String IMG_STYLE = "margin: 2px;border: 2px solid #000000;height: 256px;width: 192px;";
	static final String H1_STYLE = "margin: 0.75em 0;font-size: 167%;";
	static final String FEATURED_IMG_STYLE = "margin: 2px;border: 2px solid #000000;height: auto;width: auto;float: left;";
	static final String BODY_STYLE = "padding: 10px; margin: 10px;color: #333333;background-color: #FFFFFF;font-family: Verdana, Tahoma, Arial, sans-serif;font-size: 100%;margin: 20px auto;";

	static final String DOCTYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";
	static final String HTML_BEGIN = "<html>";
	static final String HEAD = "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>"
			+ Configuration.SITE_NAME.getValue() + "</title></head>";
	static final String BODY_BEGIN = "<body style=\"" + BODY_STYLE + "\">";

	static final String BODY_END = "</body>";
	static final String HTML_END = "</html>";
	static final String SPACER = "<p>&nbsp;</p>";

	@Autowired
	private UserService userService;

	public String build(String pGuid) {
		log.info("Entering build()");

		StringBuilder email = new StringBuilder();
		email.append(DOCTYPE);
		email.append(HTML_BEGIN);
		email.append(HEAD);
		email.append(BODY_BEGIN);

		String dataDiv = this.getDataDiv(pGuid);
		if ((dataDiv != null) && (!dataDiv.equals(""))) {
			email.append(dataDiv);
		}

		email.append(BODY_END);
		email.append(HTML_END);
		log.info("Exiting build()");
		return email.toString();
	}

	private String getDataDiv(String pGuid) {
		log.info("Entering getNewDiv()");
		StringBuilder lContent = new StringBuilder();

		// begin div and table
		lContent.append("<div id=\"new\"><table style=\"" + TABLE_STYLE + "\">");
		// header
		lContent.append("<tr style=\"" + TR_STYLE
				+ "\"><th colspan=\"3\" style=\"" + TH_STYLE
				+ "\">Please reset your password</th></tr>");
		// begin main row
		lContent.append("<tr style=\"" + TR_STYLE + "\">");
		lContent.append("<td style=\"" + TR_STYLE + "\"><table style=\""
				+ INNER_TABLE_STYLE + "\">");
		lContent.append("<tr style=\""
				+ TR_STYLE
				+ "\"><td style=\""
				+ TR_STYLE
				+ "\">We heard that you lost your password. Please use the following link within the next day to reset your password:</td></tr>");
		lContent.append(SPACER);

		String lUrl = getUrl(pGuid);

		lContent.append("<tr style=\"" + TR_STYLE + "\"><td style=\""
				+ TR_STYLE + "\"> <a href=\"" + lUrl + "\">" + lUrl
				+ "</a></td></tr>");

		lContent.append(SPACER);
		lContent.append("<tr style=\""
				+ TR_STYLE
				+ "\"><td style=\""
				+ TR_STYLE
				+ "\">If you don't use this link within 24 hours, it will expire.</td></tr>");
		lContent.append(SPACER);
		lContent.append("<tr style=\"" + TR_STYLE + "\"><td style=\""
				+ TR_STYLE + "\">Thanks,</td></tr>");
		lContent.append("<tr style=\"" + TR_STYLE + "\"><td style=\""
				+ TR_STYLE + "\">Your friends at "
				+ Configuration.SITE_NAME.getValue() + "</td></tr>");
		// end main row
		lContent.append("</table></td></tr>");
		// end div and table
		lContent.append("</table></div>");

		log.info("Exiting getNewDiv()");
		return lContent.toString();
	}

	private String getUrl(String pGuid) {

		return Configuration.FORGOT_PASSWORD_URL.getValue() + "/" + pGuid;
	}
}
