package com.itesm.labs.rest.service;

import com.itesm.labs.rest.models.History;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by mgradob on 4/12/15.
 */
public interface DetailHistoryService {

    @GET("/detailhistory/")
    ArrayList<History> getDetailHistoryOf(@Query("id_student_fk") String studentId);

    @POST("/detailhistory/")
    void postDetailHistoryItem(@Body History item, Callback<Response> cb);

    @PUT("/detailhistory/{historyId}")
    void putDetailHistoryItem(@Path("historyId") int historyId, @Body History item, Callback<Response> cb);
}
