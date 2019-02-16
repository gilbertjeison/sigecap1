package com.develop.perlasoft.network;

import com.develop.perlasoft.model.ServerResponse;
import com.develop.perlasoft.model.Usuarios;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    //CREAR USUARIO
    @FormUrlEncoded
    @POST("users/create")
    Single<ServerResponse> crearUsuario(@Body Usuarios usuario);

    //OBTENER TODOS LOS USUARIOS
    @GET("users/read.php")
    Single<List<Usuarios>> getAllUsers();

}
