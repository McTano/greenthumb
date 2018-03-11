package com.adiga.greenthumbclient;

import com.adiga.greenthumbclient.NetworkUtils.Parser;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by dbajj on 2018-03-10.
 */

public class ParserTest {

    @Test
    public void testParse() throws IOException {
        String url = "https://www.google.ca";

        String contents = Parser.parseResponse(Parser.makeRequest(url));

    }
}
