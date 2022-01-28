package com.atpl.mmg.AandA.utils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.constant.Constants;

@Component
public class EmailValidator implements Constants {
	private Pattern pattern;
	private Matcher matcher;

	@Autowired
	MMGProperties mmgProperties;

	private static final Logger logger = LoggerFactory.getLogger(EmailValidator.class);

	@Autowired
	private TemplateEngine templateEngine;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	/**
	 * Validate hex with regular expression
	 *
	 * @param hex hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public boolean validate(final String email) {

		matcher = pattern.matcher(email);
		return matcher.matches();

	}

	public String generateMailHtml(String templateFileName, Map<String, Object> variables) {
		return templateEngine.process(templateFileName, new Context(Locale.getDefault(), variables));
	}

	public String generateOTP() {
		Random random = new Random();
		return String.format("%04d", random.nextInt(10000));
	}

	public void sendEmail(String subject, String recipient, String body) {
		try {
			System.setProperty("aws.accessKeyId", mmgProperties.getAccessKey());
			System.setProperty("aws.secretKey", mmgProperties.getSecretKey());
			Session session = Session.getDefaultInstance(new Properties());
			// Create a new MimeMessage object.
			MimeMessage messages = new MimeMessage(session);

			// Add subject, from and to lines.
			messages.setSubject(subject, "UTF-8");
			messages.setFrom(new InternetAddress(EMAIL_SENDER));
			messages.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

			// Create a multipart/alternative child container.
			MimeMultipart msg_body = new MimeMultipart("alternative");

			// Create a wrapper for the HTML and text parts.
			MimeBodyPart wrap = new MimeBodyPart();
			// Define the text part.
			MimeBodyPart textPart = new MimeBodyPart();

			textPart.setContent(body, "text/html; charset=UTF-8");

			// Add the text and HTML parts to the child
			// container.
			msg_body.addBodyPart(textPart);
			// Add the child container to the wrapper object.
			wrap.setContent(msg_body);

			// Create a multipart/mixed parent container.
			MimeMultipart msg = new MimeMultipart("mixed");

			// Add the parent container to the message.
			messages.setContent(msg);

			// Add the multipart/alternative part to the
			// message.
			msg.addBodyPart(wrap);
			// Instantiate an Amazon SES client, which will make
			// the
			// service
			// call with the supplied AWS credentials.
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					// Replace US_WEST_2 with the AWS Region
					// you're
					// using for
					// Amazon SES.
					.withRegion(Regions.AP_SOUTH_1).build();

			// Send the email.
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			messages.writeTo(outputStream);
			RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

			SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage)
					.withConfigurationSetName(EMAIL_CONFIGURATION_SET);

			client.sendRawEmail(rawEmailRequest);
		} catch (Exception e) {
			logger.info("Exception in sendEmail:" + e.getMessage());
		}
	}

	public String sendSMSMessage(String phoneNumber, String otp, String message, boolean isOtpRequired) {
//		String otp = generateOTP();
		if ("Yes".equalsIgnoreCase(mmgProperties.getEnableSms())) {
			try {
				AWSCredentials credentials = new BasicAWSCredentials(mmgProperties.getAccessKey(),
						mmgProperties.getSecretKey());

				Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
				smsAttributes.put("AWS.SNS.SMS.SenderID",
						new MessageAttributeValue().withStringValue("mySenderID").withDataType("String"));
				smsAttributes.put("AWS.SNS.SMS.MaxPrice",
						new MessageAttributeValue().withStringValue("0.50").withDataType("Number"));
				smsAttributes.put("AWS.SNS.SMS.SMSType",
						new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));
				AmazonSNSClient snsClient = new AmazonSNSClient(credentials);

				if (isOtpRequired) {
					if (!CommonUtils.isNullCheck(otp))
						message = String.format(message, otp);
				} else
					message = String.format(message);

				PublishResult result = snsClient.publish(new PublishRequest().withMessage(message)
						.withPhoneNumber("91" + phoneNumber).withMessageAttributes(smsAttributes));
			} catch (Exception e) {
				logger.info("Exception in sendSMSMessage:" + e.getMessage());
			}
		}
		return otp;
	}
}