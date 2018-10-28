package unl.cis.tensorflow;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import static android.Manifest.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 200;
    private View view;


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inico para pedir  los permisos de accesso a la camara  en la app
        FloatingActionButton check_permission = (FloatingActionButton) findViewById(R.id.buttonOpenCamera);
        check_permission.setOnClickListener((View.OnClickListener) this);


    }

    //tomar funcion que abre la camara fotos
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /// metodo que esta atento a ver si el botton es clickeado y verifica si tiene los permisos
    //y generata un msj toast que puede tomar fotos caso contario pide permisos de acceso a la camara
    @Override
    public void onClick(View v) {
        view = v;

        int id = v.getId();
        switch (id) {
            case R.id.buttonOpenCamera: {
                if (checkPermission()) {
                    //si el permiso es valido la app abre la camara y sale el toast
                    Toast.makeText(getApplicationContext(), "Ahora si puedes tomar la foto", Toast.LENGTH_SHORT).show();
                    dispatchTakePictureIntent();

                } else {
                    requestPermission();
                }
            }
            break;
        }

    }

    //funcion para virificar si los permisos estan dados
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    //esta funcion envia un peticion para pedir permisos en este caso a la camara
    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }
    //// para poner un dialogo de permisos en la app

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }*/
    // fin de metodos para pedir permisos de ejecucion en appp ///

    public native String stringFromJNI();


}
