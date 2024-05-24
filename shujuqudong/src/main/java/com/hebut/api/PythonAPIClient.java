package com.hebut.api;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class PythonAPIClient {

    public static JSONArray API(JSONArray recordsArray, String api) throws JSONException {
        // 设置 API 端点 URL
        String apiUrl = "http://127.0.0.1:5000/" + api;

        try {
            // 创建 URL 对象
            URL url = new URL(apiUrl);

            // 打开 HTTP 连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // 写入请求数据
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = recordsArray.toString().getBytes("utf-8");
//                System.out.println(recordsArray.toString());
                os.write(input, 0, input.length);
            }

            // 获取响应状态码
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 读取响应内容
            String contentType = connection.getContentType();
            System.out.println(contentType);
//            if (contentType != null && contentType.startsWith("application/json")) {
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String inputLine;
//                StringBuilder response = new StringBuilder();
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // 解析响应 JSON 数据
//                JSONArray responseArray = new JSONArray(response.toString());
//                for (int i = 0; i < responseArray.length(); i++) {
//                    JSONObject itemJson = responseArray.getJSONObject(i);
//                    System.out.println(itemJson);
////                    System.out.println(itemJson);
////                    String XH = itemJson.getString("XH");
////                    double prediction = itemJson.getDouble("prediction");
////
////                    System.out.println("XH: " + XH);
////                    System.out.println("Prediction: " + prediction);
//                }
//            } else {
//                System.out.println("Response is not in JSON format: " + contentType);
//            }
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
            if (contentType != null && (contentType.startsWith("text/html") || contentType.startsWith("application/json"))) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 解析和处理响应内容
                String responseBody = response.toString();
//                System.out.println("Response Body: " + responseBody);

                // 假设响应内容是JSON格式的字符串
                try {
                    JSONArray responseArray = new JSONArray(responseBody);
                    return responseArray;
//                    for (int i = 0; i < responseArray.length(); i++) {
//                        JSONArray subArray = responseArray.getJSONArray(i);
//                        System.out.println(subArray.toString());
//                        // 根据需求处理子数组
//                    }
                } catch (JSONException e) {
                    System.out.println("Error parsing JSON from response body");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Response is not in expected format: " + contentType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recordsArray;
    }
}