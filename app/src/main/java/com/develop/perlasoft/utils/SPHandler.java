package com.develop.perlasoft.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.develop.perlasoft.model.Usuarios;
import com.develop.perlasoft.sigecap.LoginActivity;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

public class SPHandler {

    //KEYS
    private static final String KEY_IS_LOGIN = "is_login";
    private static final String KEY_NOMBRES = "nombres";
    private static final String KEY_APELLIDOS = "apellidos";
    private static final String KEY_USUARIO = "usuario";
    private static final String KEY_PASS = "pass";
    private static final String KEY_ID_ROL = "id_rol";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SPHandler(Context context) {
        this.context = context;

        sharedPreferences = getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    //CREAR SESIÓN
    public void CreateLogginSession(Usuarios user)
    {
        editor.putBoolean(KEY_IS_LOGIN, true);
        editor.putString(KEY_NOMBRES, user.getNombres());
        editor.putString(KEY_APELLIDOS, user.getApellidos());
        editor.putString(KEY_USUARIO, user.getUsuario());
        editor.putString(KEY_PASS, user.getPassword());
        editor.putInt(KEY_ID_ROL, user.getId_rol());

        editor.commit();
    }

    public Usuarios getUserDetails()
    {
        return new
                Usuarios(sharedPreferences.getString(KEY_NOMBRES,null),
                            sharedPreferences.getString(KEY_APELLIDOS,null),
                                sharedPreferences.getString(KEY_USUARIO,null),
                                    sharedPreferences.getString(KEY_PASS,null),
                                        sharedPreferences.getInt(KEY_ID_ROL,0));

    }

    public void checkLogin()
    {
        //CHECK ESTADO
        if (!isLoggedIn())
        {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
    }

    //QUITAR SESIÓN
    public void logoutUser()
    {
        // Clearing all data from Shared Preferences
        //editor.clear();
        // Storing login value as TRUE
        editor.putBoolean(KEY_IS_LOGIN, false);
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

    //SABER SI HAY SESIÓN
    public boolean isLoggedIn()
    {
        return sharedPreferences.getBoolean(KEY_IS_LOGIN, false);
    }
}
