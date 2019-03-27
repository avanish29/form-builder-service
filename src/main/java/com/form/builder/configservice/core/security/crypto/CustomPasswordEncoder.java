package com.form.builder.configservice.core.security.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
	private static final Logger logger = LoggerFactory.getLogger(CustomPasswordEncoder.class);
	
	@Override
	public String encode(final CharSequence rawPassword) {
		try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(rawPassword.toString().getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException noSuchAlgoEx) {
        	logger.warn("Exception while trying to get SHA String.", noSuchAlgoEx);
        }
        return null;
	}

	@Override
	public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
		if (StringUtils.isEmpty(encodedPassword)) {
			logger.warn("Empty encoded password");
			return false;
		}

		return encodedPassword.equals(encode(rawPassword));
	}

}
