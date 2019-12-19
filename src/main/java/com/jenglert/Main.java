package com.jenglert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

public class Main {
    private static final String URL = "https://en.wikipedia.org/w/api.php";
    private static final String SEARCH = "Cincinnati";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(URL).newBuilder();
        urlBuilder.addQueryParameter("action", "parse")
                .addQueryParameter("section", "0")
                .addQueryParameter("prop", "text")
                .addQueryParameter("format", "json")
                .addQueryParameter("page", "Cincinnati");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String str = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            // Using basic Map deserialization here
            // This could be cleaned up a bit if I had more time and knowledge of Jackson framework
            Map<String, Object> jsonMap = objectMapper.readValue(str, new TypeReference<Map<String,Object>>(){});
            Map<String, Object> parseMap = (Map<String, Object>)jsonMap.get("parse");
            Map<String, Object> textMap = (Map<String, Object>)parseMap.get("text");
            String text = (String)textMap.get("*");

            int matches = StringUtils.countMatches(text, SEARCH);
            System.out.println("Found " + matches + " for " + SEARCH);
        } catch (IOException e) {
            // Normally would use logger here
            System.out.println("HTTP call failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
