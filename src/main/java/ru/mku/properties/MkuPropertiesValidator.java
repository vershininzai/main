package ru.mku.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class MkuPropertiesValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(MkuPropertiesValidator.class);

    public static final Pattern pattern = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");

    @Override
    public boolean supports(Class<?> type) {
        return type == MkuProperties.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "testHost", "test.host.empty");
        MkuProperties properties = (MkuProperties) o;
//        if (properties.getTestHost() != null && !this.pattern.matcher(properties.getTestHost()).matches()) {
//            errors.rejectValue("testHost", "Invalid host");
//        }
    }

}
