package com.example.travelbag.domain.location.utils;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Component
public class ExchangeRateUtils {

    @Value("${exchange.rate.api.authKey}")
    private static String authKey;

    private static final String dataType = "AP01";

    private static HttpURLConnection connection = null;


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
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            // API 호출
            if (status > 299) { // 실패한 경우 Exception 발생
                throw new RuntimeException("HTTP 요청 실패: 상태 코드 " + status);
            } else {  // 성공했을 경우 환율 정보 추출
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    JSONArray exchangeRateInfoList = new JSONArray(line);

                    if (exchangeRateInfoList.isEmpty()) {
                        // 빈 데이터가 조회된 경우
                        throw new RuntimeException("빈 데이터가 조회된 경우");
                    }

                    for (int i = 0; i < exchangeRateInfoList.length(); i++) {
                        JSONObject exchangeRateInfo = exchangeRateInfoList.getJSONObject(i);

                        if (!exchangeRateInfo.has("cur_unit") || exchangeRateInfo.isNull("cur_unit")) {
                            // 비영업일이거나, 영업 시간 전이라 데이터가 없는 경우
                            throw new RuntimeException("null: 비영업일이거나 영업 시간 전인 경우");
                        }

                        if (exchangeRateInfo.getString("cur_unit").equals(currency_unit)) {
                            // 쉼표가 포함된 String을 Double로 파싱
                            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                            exchangeRate = format.parse(exchangeRateInfo.getString("deal_bas_r")).doubleValue();
                        }
                    }
                }
                reader.close();
            }

        } catch (IOException | java.text.ParseException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }

        return exchangeRate;
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
