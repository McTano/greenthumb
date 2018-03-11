package com.adiga.greenthumbclient;

import com.adiga.greenthumbclient.NetworkUtils.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.adiga.greenthumbclient.NetworkUtils.Parser.makePickups;

/**
 * Created by dbajj on 2018-03-10.
 */

public class ParserTest {

    @Test
    public void testParse() throws IOException {
        String url = "https://www.google.ca";

        String contents = Parser.parseResponse(Parser.makeRequest(url));

    }

    @Test
    public void testMakePickups() throws JSONException {
        String stubbed_response = "[[\"6800 Cambie\", \"08:00\"], [\"800 Robson\", \"08:11\"], [\"3780 Arbutus\", \"08:23\"], [\"6133 University\", \"08:42\"]]";
        List<Pickup> actualValue = makePickups(stubbed_response);
        List<Pickup> expectedValue = new ArrayList<>();
        expectedValue.add(new Pickup("6800 Cambie", "08:00"));
        expectedValue.add(new Pickup("800 Robson", "08:11"));
        expectedValue.add(new Pickup("3780 Arbutus", "08:23"));
        expectedValue.add(new Pickup("6133 University", "08:42"));

    }

}
