package com.develop.perlasoft.sigecap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.perlasoft.scan.ScanActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import asia.kanopi.fingerscan.Status;

import static android.app.Activity.RESULT_OK;

public class PruebaDispositivoFragment extends Fragment implements View.OnClickListener {

    ImageView ivFinger;
    TextView tvMessage;
    byte[] img;
    Bitmap bmp;
    private static final int SCAN_FINGER = 0;
    Button btnScan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_fragment_prueba_dispositivo, container,false);

        btnScan = rootView.findViewById(R.id.btnScan);
        ivFinger = rootView.findViewById(R.id.ivFingerDisplay);
        tvMessage = rootView.findViewById(R.id.tvMessage);

        btnScan.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnScan:
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivityForResult(intent, SCAN_FINGER);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int status;
        String errorMessage;

        switch (requestCode)
        {
            case (SCAN_FINGER):
            {
                if (resultCode == RESULT_OK)
                {
                    status = data.getIntExtra("status", Status.ERROR);
                    if (status == Status.SUCCESS)
                    {
                        tvMessage.setText("Fingerprint captured");
                        img = data.getByteArrayExtra("img");
                        bmp = BitmapFactory.decodeByteArray(img,0,img.length);
                        ivFinger.setImageBitmap(bmp);
                    }
                    else
                    {
                        errorMessage = data.getStringExtra("errorMessage");
                        tvMessage.setText("-- Error: " +  errorMessage + " --");
                    }
                }

                break;
            }

        }
    }
}
