package com.site.monitor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContentVerifierServiceTest {

    private ContentVerifierService contentVerifierService;

    @BeforeEach
    public void setUp() {
        contentVerifierService = new ContentVerifierService();
    }

    @Test
    public void testIsContentVerified_ContentPresent() {
        String response = "This is a sample response with required content";
        String contentRequirement = "required content";

        boolean result = contentVerifierService.isContentVerified(response, contentRequirement);
        assertTrue(result);
    }

    @Test
    public void testIsContentVerified_ContentNotPresent() {
        String response = "This is a sample response without the required content";
        String contentRequirement = "missing content";

        boolean result = contentVerifierService.isContentVerified(response, contentRequirement);
        assertFalse(result);
    }

    @Test
    public void testIsContentVerified_NullResponse() {
        String response = null;
        String contentRequirement = "required content";

        boolean result = contentVerifierService.isContentVerified(response, contentRequirement);
        assertFalse(result);
    }

    @Test
    public void testIsContentVerified_NullContentRequirement() {
        String response = "This is a sample response";
        String contentRequirement = null;

        boolean result = contentVerifierService.isContentVerified(response, contentRequirement);
        assertFalse(result);
    }

    @Test
    public void testIsContentVerified_EmptyResponse() {
        String response = "";
        String contentRequirement = "required content";

        boolean result = contentVerifierService.isContentVerified(response, contentRequirement);
        assertFalse(result);
    }

    @Test
    public void testIsContentVerified_EmptyContentRequirement() {
        String response = "This is a sample response";
        String contentRequirement = "";

        boolean result = contentVerifierService.isContentVerified(response, contentRequirement);
        assertFalse(result);
    }

    @Test
    public void testIsContentVerified_Empty() {
        String response = "";
        String contentRequirement = "";

        boolean result = contentVerifierService.isContentVerified(response, contentRequirement);
        assertFalse(result);
    }
}