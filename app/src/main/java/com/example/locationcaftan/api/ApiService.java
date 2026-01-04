package com.example.locationcaftan.api;

import com.example.locationcaftan.model.ApiResponse;
import com.example.locationcaftan.model.Caftan;
import com.example.locationcaftan.model.LoginRequest;
import com.example.locationcaftan.model.ReservationRequest;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    String BASE_URL = "http://10.0.2.2:8000/api/";

    // -------------------------------
    //  LISTE DES CAFTANS
    // -------------------------------
    @GET("caftans")
    Call<List<Caftan>> getCaftans();

    // -------------------------------
    //  LOGIN ADMIN
    // -------------------------------
    @POST("admin/login")
    Call<ApiResponse> loginAdmin(@Body LoginRequest request);

    // -------------------------------
    //  AJOUTER CAFTAN (MULTIPART)
    @Multipart
    @POST("caftans")
    Call<ApiResponse> addCaftan(
            @Part("nom") RequestBody nom,
            @Part("prix") RequestBody prix,
            @Part("couleur") RequestBody couleur,
            @Part("taille") RequestBody taille,
            @Part("statut") RequestBody statut,
            @Part MultipartBody.Part image
    );

    // -------------------------------
    //  RÉSERVER

    @POST("reservations")
    Call<ApiResponse> envoyerReservation(@Body ReservationRequest request);
}
