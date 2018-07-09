package com.foodaclic.livraison.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by cyrilleguipie on 1/23/17.
 */

public class PermissionUtils {

  public static final int LOCATION = 0x1;
  public static final int CALL = 0x2;
  public static final int WRITE_EXST = 0x3;
  public static final int READ_EXST = 0x4;


  public static final int GPS_SETTINGS = 0x7;
  public static final int GROUP = 0x100;

  public static void askForPermission(AppCompatActivity context, String permission, Integer requestCode) {
    if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {

        //This is called if user has denied the permission before
        //In this case I am just asking the permission again
        ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);

      } else {

        ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
      }
    } else {
      Toast.makeText(context, "" + permission + " DEJA AUTORISE", Toast.LENGTH_SHORT).show();
    }
  }

  public static void askForPermissions(AppCompatActivity context) {
    int camera = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA);
    int storage = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    int sms = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);
    int loc = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION);
    ArrayList<String> permissions = new ArrayList<>();

    if (storage != PackageManager.PERMISSION_GRANTED) {
      permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    if (loc != PackageManager.PERMISSION_GRANTED) {
      permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
    }

    if(!permissions.isEmpty()){
      ActivityCompat.requestPermissions(context, permissions.toArray(new String[permissions.size()]), GROUP);
    }

  }


}
