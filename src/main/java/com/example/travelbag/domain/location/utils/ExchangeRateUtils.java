package com.example.travelbag.domain.location.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ExchangeRateUtils {

    private static HttpURLConnection connection = null;

    private static final String dataType = "AP01";
    private static String authKey;
    private static Map<String, String> cookies = new HashMap<>(); // 쿠키 저장소

    @Value("${exchange.rate.api.authKey}")
    public void setAuthKey(String value) {
        authKey = value;
    }

    public static double fetchExchangeRate(String searchDate, String currency_unit) {
        BufferedReader reader;
        String line;
        double exchangeRate = 0.0;

        try {
            // Request URL
            URL url = new URL("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=" + authKey + "&searchdate=" + searchDate + "&data=" + dataType);
            connection = (HttpURLConnection) url.openConnection();

            // Request 초기 세팅
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(false); // 리다이렉션 자동 처리 비활성화
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 쿠키 설정
            if (!cookies.isEmpty()) {
                String cookieHeader = String.join("; ", cookies.entrySet().stream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                        .toArray(String[]::new));
                connection.setRequestProperty("Cookie", cookieHeader);
            }

            int status = connection.getResponseCode();

            // 리다이렉션 처리
            if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
                String newUrl = connection.getHeaderField("Location");
                // 쿠키 저장
                String setCookieHeader = connection.getHeaderField("Set-Cookie");
                if (setCookieHeader != null) {
                    parseCookies(setCookieHeader);
                }
                return fetchExchangeRate(searchDate, currency_unit); // 재귀 호출로 리다이렉션 처리
            }

            // API 호출 성공
            if (status == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // JSON 응답 처리
                JSONArray exchangeRateInfoList = new JSONArray(response.toString());

                for (int i = 0; i < exchangeRateInfoList.length(); i++) {
                    JSONObject exchangeRateInfo = exchangeRateInfoList.getJSONObject(i);

                    if (!exchangeRateInfo.has("cur_unit") || exchangeRateInfo.isNull("cur_unit")) {
                        throw new RuntimeException("null: 비영업일이거나 영업 시간 전인 경우");
                    }

                    if (exchangeRateInfo.getString("cur_unit").equals(currency_unit)) {
                        // 쉼표가 포함된 String을 Double로 파싱
                        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                        exchangeRate = format.parse(exchangeRateInfo.getString("deal_bas_r")).doubleValue();
                        break;
                    }
                }
            } else {
                throw new RuntimeException("HTTP 요청 실패: 상태 코드 " + status);
            }

        } catch (IOException | java.text.ParseException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return exchangeRate;
    }

    private static void parseCookies(String setCookieHeader) {
        String[] cookiePairs = setCookieHeader.split(";");
        for (String pair : cookiePairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                cookies.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
    }

    public static String getPreviousDate(String currentDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = sdf.parse(currentDate);
            long timeInMillis = date.getTime() - (24 * 60 * 60 * 1000); // 하루를 빼기

            return sdf.format(new Date(timeInMillis));
        } catch (Exception e) {
            e.printStackTrace();
            return currentDate;
        }
    }
}
