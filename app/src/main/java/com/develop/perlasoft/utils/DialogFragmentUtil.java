package com.develop.perlasoft.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentUtil extends DialogFragment {

    //PARAMETROS
    public static final String TITLE = "título";
    public static final String MESSAGE = "mensaje";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //SUPLIR LOS PARAMETROS OBTENIDOS
        Bundle messages = getArguments();
        Context context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        return super.onCreateDialog(savedInstanceState);
    }
}
