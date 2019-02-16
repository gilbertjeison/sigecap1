package com.develop.perlasoft.scan;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.perlasoft.sigecap.R;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import asia.kanopi.fingerscan.Fingerprint;
import asia.kanopi.fingerscan.Status;

public class ScanActivity extends AppCompatActivity {

    private TextView tvStatus;
    private TextView tvError;
    private Fingerprint fingerprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ActionBar actionBar = getSupportActionBar();

        tvStatus = findViewById(R.id.tvStatus);
        tvError = findViewById(R.id.tvError);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        fingerprint = new Fingerprint();

        try {

            InputStream inputStream = getAssets().open("img2.jpg");
            byte[] proIma = new byte[inputStream.available()];
            inputStream.read(proIma);
            inputStream.close();


            InputStream inputStream2 = getAssets().open("img3.jpg");
            byte[] canIma = new byte[inputStream2.available()];
            inputStream2.read(canIma);
            inputStream2.close();

//            Drawable d = getDrawable(R.drawable.img1);
//            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] bitmapdata = stream.toByteArray();
//
//
//
//            Drawable d2 = getDrawable(R.drawable.img2);
//            Bitmap bitmap2 = ((BitmapDrawable)d2).getBitmap();
//            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
//            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
//            byte[] bitmapdata2 = stream2.toByteArray();

//            FingerprintTemplate probe = new FingerprintTemplate().dpi(500).create(proIma);
//            FingerprintTemplate candidate = new FingerprintTemplate().dpi(500).create(canIma);
//
//            double score = new FingerprintMatcher().index(probe).match(candidate);
//            Toast.makeText(getApplicationContext(), "Puntuación de similitud: "+score, Toast.LENGTH_SHORT).show();
//
//            boolean matches =  score >= 40;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                setResult(RESULT_CANCELED);
                fingerprint.turnOffReader();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        fingerprint.scan(this,printHandler,updateHandler);
        super.onStart();
    }

    @Override
    protected void onStop() {
        fingerprint.turnOffReader();
        super.onStop();
    }

    Handler updateHandler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(Message msg) {

            int status = msg.getData().getInt("status");
            tvError.setText("");
            switch (status)
            {
                case Status.INITIALISED:
                    tvStatus.setText("Setting up reader...");
                    break;
                case Status.SCANNER_POWERED_ON:
                    tvStatus.setText("Reader powered on");
                    break;
                case Status.READY_TO_SCAN:
                    tvStatus.setText("Ready to scan finger");
                    break;
                case Status.FINGER_DETECTED:
                    tvStatus.setText("Finger detected");
                    break;
                case Status.RECEIVING_IMAGE:
                    tvStatus.setText("Receiving image");
                    break;
                case Status.FINGER_LIFTED:
                    tvStatus.setText("Finger has been lifted off reader");
                    break;
                case Status.SCANNER_POWERED_OFF:
                    tvStatus.setText("Reader is off");
                    break;
                case Status.SUCCESS:
                    tvStatus.setText("Fingerprint successfully captured");
                    break;
                case Status.ERROR:
                    tvStatus.setText("Error");
                    tvError.setText(msg.getData().getString("errorMessage"));
                    break;
                default:
                    tvStatus.setText(String.valueOf(status));
                    tvError.setText(msg.getData().getString("errorMessage"));
                    break;
            }
        }
    };

    Handler printHandler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(Message msg) {
            byte[] image;
            String errorMessage = "empty";
            int status = msg.getData().getInt("status");
            Intent intent = new Intent();
            intent.putExtra("status", status);

            if (status == Status.SUCCESS)
            {
                image = msg.getData().getByteArray("img");
                intent.putExtra("img",image);
            }
            else
            {
                errorMessage = msg.getData().getString("errorMessage");
                intent.putExtra("errorMessage", errorMessage);
            }

            setResult(RESULT_OK,intent);
            finish();
        }
    };
}
