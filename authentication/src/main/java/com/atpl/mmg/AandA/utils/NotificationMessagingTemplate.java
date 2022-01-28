/*package com.atpl.mmg.AandA.utils;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.ArrayList;import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

import java.math.BigInteger;

import com.atpl.mmg.AandA.config.SpringJdbcApplication;
import com.atpl.mmg.AandA.domain.sms.SMSDomain;
@Component
public class NotificationMessagingTemplate {

	public AmazonSNS amazonSNS;
	
	private static final String MESSAGE_DEFAULT = "Default SMS message.";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationMessagingTemplate.class);
	
	
    @Value("${aws.sns.phoneNumberRegex}")
    private String phoneNumberRegex;
   
public boolean sendSMSMessage(final SMSDomain domain) {
	
    List<BigInteger> phoneNumbers = domain.getTo();
    		List<String> newList = new ArrayList<String>(phoneNumbers.size()) ;
    		for (BigInteger myInt : phoneNumbers) { 
    		  newList.add(String.valueOf(myInt)); 
    		}
    		LOGGER.info("converted into list===0"+newList);
   try {
        if(!CollectionUtils.isEmpty(phoneNumbers)) {

            // Filter valid phone numbers and prevent no duplicated. 
        	newList = newList.stream()
                 .filter(p -> isValidPhoneNumber(p)).distinct().collect(toList());
        	LOGGER.info("converted into list===1"+newList);
            // Prepare for SNS Client environment like MessageAttributeValue default.
            Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
            //Map<String, MessageAttributeValue> smsAttributes = setSmsAttributes(smsDTO);
             
            // Send SMS message for each phone numbers.
            newList.forEach(p -> sendSMSMessage(SpringJdbcApplication.ssnClient(),
                    smsMessageBuilder(domain), p, smsAttributes));
        }
    } catch (Exception e) {
        LOGGER.error("Got error while sending sms {}", e);
        return false;
    }
    return true;
}

private String smsMessageBuilder(final SMSDomain domain) {
    final StringBuilder builder = new StringBuilder();
    String message = domain.getMessage();

    if(Strings.isNullOrEmpty(message)) {
        builder.append(MessageFormat.format("{0}. ", MESSAGE_DEFAULT));
    } else {
        builder.append(MessageFormat.format("{0}.",message));
    }
    //TODO Put more message content here

    return builder.toString();
}

private void sendSMSMessage(AmazonSNS snsClient, String message,
                           String p, Map<String, MessageAttributeValue> smsAttributes) {
	 smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
             .withStringValue("Promotional  ") //Sets the type to promotional.
             .withDataType("String"));
	 LOGGER.info("smsAttributes==="+smsAttributes);
    PublishResult result = snsClient.publish(new PublishRequest()
            .withMessage(message)
            .withPhoneNumber(p)
            .withMessageAttributes(smsAttributes));
	 LOGGER.info("The message ID {}", result);
    LOGGER.debug("The message ID {}", result);
}



*//**
 *  E.164 is a standard for the phone number structure used for international telecommunication.
 *  Phone numbers that follow this format can have a maximum of 15 digits, and they are prefixed
 *  with the plus character (+) and the country code. For example,
 *  a U.S. phone number in E.164 format would appear as +1XXX5550100.
 * @See at http://docs.aws.amazon.com/sns/latest/dg/sms_publish-to-phone.html
 * @see <a href="http://docs.aws.amazon.com/sns/latest/dg/sms_publish-to-phone.html">
 *  Sending an SMS Message</a>
 * @param String.valueOf(val)phoneNumber String
 * @return true if valid, otherwise false.
 *//*
private boolean isValidPhoneNumber(String p) {
    return Pattern.matches(phoneNumberRegex, p);
}


}*/