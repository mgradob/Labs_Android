package com.itesm.labs.rest.services;

import com.itesm.labs.rest.models.History;

import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by mgradob on 4/12/15.
 */
public interface DetailHistoryService {

    @GET("/{lab}/detailhistory/")
    ArrayList<History> getDetailHistoryOf(@Header("Authorization") String token,
                                          @Path("lab") String lab,
                                          @Query("id_student_fk") String studentId);

    @POST("/{lab}/detailhistory/")
    Response postDetailHistoryItem(@Header("Authorization") String token,
                               @Path("lab") String lab,
                               @Body History item);

    @PUT("/{lab}/detailhistory/{historyId}")
    Response putDetailHistoryItem(@Header("Authorization") String token,
                              @Path("lab") String lab,
                              @Path("historyId") int historyId,
                              @Body History item);
}
