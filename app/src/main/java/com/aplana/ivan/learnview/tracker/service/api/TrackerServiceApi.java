package com.aplana.ivan.learnview.tracker.service.api;


import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zakha_000 on 11.05.2017.
 */

public class TrackerServiceApi {

    private String _baseUrl;
    private OkHttpClient httpClient;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public TrackerServiceApi(String baseUrl) {
        this._baseUrl = baseUrl;
        httpClient = new OkHttpClient();
    }

    public String getListEntity() throws IOException {
        Request request = new Request.Builder()
                .url(_baseUrl+"api/list")
                .build();

        Response response = httpClient.newCall(request).execute();

        return response.body().string();
    }

    public String getByIdEntity(UUID id) throws IOException {
        Request request = new Request.Builder()
                .url(_baseUrl+"api/getbyid/"+id)
                .build();

        Response response = httpClient.newCall(request).execute();

        return response.body().string();
    }

    public String createEntity(String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(_baseUrl+"api/create")
                .post(body)
                .build();

        Response response = httpClient.newCall(request).execute();

        return response.body().string();
    }

    public String updateEntity(String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(_baseUrl+"api/update")
                .post(body)
                .build();

        Response response = httpClient.newCall(request).execute();

        return response.body().string();
    }

    public String deleteEntity(UUID id) throws IOException {
        Request request = new Request.Builder()
                .url(_baseUrl+"api/delete")
                .build();

        Response response = httpClient.newCall(request).execute();

        return response.body().string();
    }
}
