package com.soundcloud.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;


public class TokenTest {
    @Test
    public void shouldInvalidateToken() throws Exception {
        Token t = new Token("1", "2");
        t.invalidate();
        assertNull(t.access);
        assertFalse(t.valid());
    }

    @Test
    public void shouldBeValid() throws Exception {
        Token t = new Token("1", "2");
        assertTrue(t.valid());
    }

    @Test
    public void emptyTokenShouldBeInValid() throws Exception {
        Token invalid = new Token(null, "2");
        assertFalse(invalid.valid());
    }

    @Test
    public void shouldDetectStarScope() throws Exception {
        Token t = new Token(null, null);
        assertFalse(t.starScoped());
        t.scope = "signup *";
        assertTrue(t.starScoped());
    }

    @Test
    public void shouldDetectSignupScope() throws Exception {
        Token t = new Token(null, null);
        assertFalse(t.signupScoped());
        t.scope = "signup";
        assertTrue(t.signupScoped());
    }

    @Test
    public void shouldHaveProperEqualsMethod() throws Exception {
        Token t1 = new Token("1", "2");
        Token t2 = new Token("1", "2");
        assertEquals(t1, t2);

        t1.scope = "foo";
        assertFalse(t1.equals(t2));
        t2.scope = "foo";
        assertEquals(t1, t2);
    }

    @Test
    public void shouldParseJsonResponse() throws Exception {
        Token t = new Token(new JSONObject("{\n" +
                "    \"access_token\": \"1234\",\n" +
                "    \"refresh_token\": \"5678\",\n" +
                "    \"expires_in\":   3600,\n" +
                "    \"scope\":    \"*\"\n" +
                "}"));

        assertThat(t.scope, equalTo("*"));
        assertThat(t.access, equalTo("1234"));
        assertThat(t.refresh, equalTo("5678"));
        assertNotNull(t.getExpiresIn());
    }
}