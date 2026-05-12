package com.sismics.docs.rest.util;

import com.sismics.rest.exception.ClientException;
import com.sismics.rest.util.ValidationUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * Additional coverage tests for {@link ValidationUtil}.
 */
public class TestValidationUtilCoverage {
    @Test
    public void testValidateRequired() {
        ValidationUtil.validateRequired("value", "field");

        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateRequired(null, "field"));
    }

    @Test
    public void testValidateLengthBranches() {
        Assertions.assertEquals("abc", ValidationUtil.validateLength(" abc ", "field", 2, 4));
        Assertions.assertEquals("", ValidationUtil.validateLength("   ", "field", 2, 4, true));
        Assertions.assertNull(ValidationUtil.validateLength(null, "field", 2, 4, true));

        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateLength(null, "field", 2, 4));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateLength("a", "field", 2, 4));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateLength("abcde", "field", 2, 4));
    }

    @Test
    public void testValidateStringAndPatterns() {
        Assertions.assertEquals("name", ValidationUtil.validateStringNotBlank(" name ", "name"));
        ValidationUtil.validateEmail("user@example.com", "email");
        ValidationUtil.validateHexColor("#123456", "color", false);
        ValidationUtil.validateHexColor(null, "color", true);
        ValidationUtil.validateTagName("project_docs");
        ValidationUtil.validateAlphanumeric("abc_123", "code");
        ValidationUtil.validateUsername("user.name@example", "username");
        ValidationUtil.validateRegex("ABC-123", "code", "[A-Z]{3}-\\d{3}");

        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateStringNotBlank("   ", "name"));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateEmail("invalid-email", "email"));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateHexColor("#12345", "color", false));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateTagName("project docs"));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateTagName("project:docs"));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateAlphanumeric("abc-123", "code"));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateUsername("user name", "username"));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateRegex("abc-123", "code", "[A-Z]{3}-\\d{3}"));
    }

    @Test
    public void testValidateNumbersAndDates() {
        Assertions.assertEquals(Integer.valueOf(42), ValidationUtil.validateInteger("42", "count"));
        Assertions.assertEquals(Long.valueOf(123456789L), ValidationUtil.validateLong("123456789", "size"));

        Date date = ValidationUtil.validateDate("1000", "date", false);
        Assertions.assertEquals(1000L, date.getTime());
        Assertions.assertNull(ValidationUtil.validateDate("", "date", true));
        Assertions.assertNull(ValidationUtil.validateDate(null, "date", true));

        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateInteger("forty two", "count"));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateLong("large", "size"));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateDate("", "date", false));
        Assertions.assertThrows(ClientException.class, () ->
                ValidationUtil.validateDate("tomorrow", "date", false));
    }
}
