package com.qbent.enfinsapp.restapi;

import android.content.Context;
import android.os.AsyncTask;

import com.qbent.enfinsapp.global.AuthHelper;
import com.qbent.enfinsapp.global.GlobalSettings;
import com.qbent.enfinsapp.model.ApiRequest;
import com.qbent.enfinsapp.model.Login;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHandler {

    public static class GetAsync extends AsyncTask<String, Void, String> {
        private Context _context;
        private ApiCallback _callback;
        private String _urlPart;
        private AuthHelper _authHelper;

        public GetAsync(Context context) {
            this._context = context;
            this._callback = (ApiCallback) context;
            this._authHelper = AuthHelper.getInstance(context);
        }

        @Override
        protected void onPreExecute() {
            try {
                _callback.onApiRequestStart();
            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = null;
            try {
                _urlPart = strings[0];
                String requestUrl = GlobalSettings.API_BASE_URL.getKey() + _urlPart;

                URL restUrl = new URL(requestUrl);
                HttpURLConnection restConnection = (HttpURLConnection) restUrl.openConnection();
                restConnection.setRequestMethod("GET");
                restConnection.setRequestProperty("Accept", "application/json");
                restConnection.setConnectTimeout(1000 * 60 * 60);
                if (_authHelper.isLoggedIn()) {
                    restConnection.setRequestProperty("Authorization", "Bearer " + _authHelper.getIdToken());
                }

                restConnection.connect();

                if (restConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("Failed: " + restConnection.getResponseCode());
                }

                String output;
                BufferedReader brReader = new BufferedReader(
                        new InputStreamReader(restConnection.getInputStream())
                );
                while ((output = brReader.readLine()) != null) {
                    response = output;
                }
                restConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if (s != null) {
                    if (_urlPart.equals("collection-points")) {
                        _callback.onApiRequestComplete("collection_points", s);
                    }
                } else {
                    _callback.onApiRequestComplete("Invalid", "Data");
                }
            } catch (Exception e) {

            }
        }
    }

    public static class PostAsync extends AsyncTask<ApiRequest, Void, String> {
        private Context _context;
        private ApiCallback _callback;
        private String _urlPart;
        private AuthHelper _authHelper;

        public PostAsync(Context context) {
            this._context = context;
            this._callback = (ApiCallback) context;
            this._authHelper = AuthHelper.getInstance(context);
        }

        @Override
        protected void onPreExecute() {
            try {
                _callback.onApiRequestStart();
            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(ApiRequest... apiRequests) {
            String response = "";
            try {
                _urlPart = apiRequests[0].get_restApiUrl();
                String apiUrl = GlobalSettings.API_BASE_URL.getKey() + _urlPart;

                URL restUrl = new URL(apiUrl);
                HttpURLConnection restConnection = (HttpURLConnection) restUrl.openConnection();
                restConnection.setDoOutput(true);
                restConnection.setRequestMethod("POST");
                restConnection.setRequestProperty("Content-Type", "application/json");
                restConnection.setConnectTimeout(1000 * 60 * 60);

                Login login = (Login) apiRequests[0].get_t();
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userName", login.getUserName());
                jsonObject.accumulate("password", login.getPassword());

                if (_authHelper.isLoggedIn()) {
                    restConnection.setRequestProperty("Authorization", "Bearer " + _authHelper.getIdToken());
                }

                restConnection.connect();

                OutputStream os = restConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();
                os.flush();
                os.close();

                if (restConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("Failed: " + restConnection.getResponseCode());
                }

                String output;
                BufferedReader brReader = new BufferedReader(
                        new InputStreamReader(restConnection.getInputStream())
                );
                while ((output = brReader.readLine()) != null) {
                    response = response + output;
                }
                restConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if (s != null) {
                    if (_urlPart.equals("authenticate")) {
                        _callback.onApiRequestComplete("authenticate", s);
                    }
                } else {
                    _callback.onApiRequestComplete("Invalid", "Data");
                }
            } catch (Exception e) {

            }
        }
    }
}
