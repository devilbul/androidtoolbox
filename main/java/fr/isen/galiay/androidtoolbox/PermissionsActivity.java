package fr.isen.galiay.androidtoolbox;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PermissionsActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 100;
    private static final int RequestPermissionCode = 69;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    private ListView listContact;
    private TextView lat;
    private TextView lon;
    private TextView phoneInfo;
    private ImageButton button;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        phoneInfo = findViewById(R.id.phoneInfo);
        button = findViewById(R.id.imageButton);
        listContact = findViewById(R.id.listContact);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);
        lat.setText("en cours de localisation");
        lon.setText("en cours de localisation");
        displayPhoneInfo();
        loadImageButton();
        demandPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContactFromPhone();
        showCurrentPosition();
    }

    private void demandPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                !ActivityCompat.shouldShowRequestPermissionRationale(PermissionsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.shouldShowRequestPermissionRationale(PermissionsActivity.this, Manifest.permission.READ_CONTACTS))
            ActivityCompat.requestPermissions(PermissionsActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    RequestPermissionCode);
        else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED &&
                !ActivityCompat.shouldShowRequestPermissionRationale(PermissionsActivity.this, Manifest.permission.READ_CONTACTS) &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.shouldShowRequestPermissionRationale(PermissionsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION))
            ActivityCompat.requestPermissions(PermissionsActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                !ActivityCompat.shouldShowRequestPermissionRationale(PermissionsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED &&
                !ActivityCompat.shouldShowRequestPermissionRationale(PermissionsActivity.this, Manifest.permission.READ_CONTACTS))
            ActivityCompat.requestPermissions(PermissionsActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
    }

    private void showCurrentPosition() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                lat.setText(String.valueOf(location.getLatitude()));
                                lon.setText(String.valueOf(location.getLongitude()));
                            }
                        }
                    }
                );
            }
    }

    private void loadContactFromPhone() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            ArrayList<String> contactList = new ArrayList<>();
            String phoneNumber;
            Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
            String _ID = ContactsContract.Contacts._ID;
            String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
            String HAS_PHoNE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
            Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
            String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
            StringBuilder output;
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
            List<String> numbers;
            int i = 0;

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    output = new StringBuilder();
                    String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                    String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHoNE_NUMBER)));

                    if (hasPhoneNumber > 0) {
                        numbers = new ArrayList<>();
                        output.append("Nom : ").append(name);
                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);

                        while (phoneCursor.moveToNext()) {
                            i = 1;
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            numbers.add(phoneNumber);
                        }

                        Set<String> s = new LinkedHashSet<>(numbers);

                        for (String number : s)
                            output.append("\nnuméro ").append(i++).append(": ").append(number);

                        phoneCursor.close();
                    }
                    contactList.add(output.toString());
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(PermissionsActivity.this, R.layout.list_item_contact, R.id.listItem, contactList);
            listContact.setAdapter(adapter);
        }
    }

    private void loadImageButton() {
        try {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

            if (sharedPreferences.contains("image") && sharedPreferences.contains("width") && sharedPreferences.contains("height")) {
                Uri imageUri = Uri.parse(sharedPreferences.getString("image", null));
                Bitmap image = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                Bitmap resizeImage = Bitmap.createScaledBitmap(image, sharedPreferences.getInt("width", 0),
                        sharedPreferences.getInt("height", 0), false);
                button.setImageBitmap(resizeImage);
            }
        }
        catch (IOException e) {
            Log.i("TAG", "Some exception " + e);
        }
    }

    public void openGallery(View v) {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(gallery, SELECT_PICTURE);
    }

    private void displayPhoneInfo() {
        String infos;

        infos = "os.version : " + System.getProperty("os.version") + "\n" +
                "SDK_INT : " + Build.VERSION.SDK_INT + "\n" +
                "CODENAME : " + Build.VERSION.CODENAME + "\n" +
                "DEVICE : " + Build.DEVICE + "\n" +
                "MODEL : " + Build.MODEL + "\n" +
                "PRODUCT : " + Build.PRODUCT + "\n" +
                "FINGERPRINT : " + Build.FINGERPRINT + "\n" +
                "DISPLAY : " + Build.DISPLAY + "\n" +
                "BOARD : " + Build.BOARD + "\n" +
                "BOOT LOADER : " + Build.BOOTLOADER + "\n" +
                "BRAND : " + Build.BRAND + "\n" +
                "HARDWARE : " + Build.HARDWARE + "\n" +
                "HOST : " + Build.HOST + "\n" +
                "MANUFACTURE : " + Build.MANUFACTURER + "\n" +
                "USER : " + Build.USER + "\n" +
                "ID : " + Build.ID + "\n" +
                "TAGS : " + Build.TAGS + "\n" +
                "TYPE : " + Build.TYPE + "\n";

        phoneInfo.setText(infos);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadContactFromPhone();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap resizeImage, selectedImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImageUri);

                    if (selectedImage.getWidth() > 400) {
                        int newHeight = selectedImage.getHeight() * 400 / selectedImage.getWidth();

                        resizeImage = Bitmap.createScaledBitmap(selectedImage, 400, newHeight, false);
                    }
                    else
                        resizeImage = selectedImage;

                    button.setImageBitmap(resizeImage);
                    sharedPreferences.edit().putString("image", String.valueOf(selectedImageUri))
                            .putInt("width", resizeImage.getWidth())
                            .putInt("height", resizeImage.getHeight()).apply();
                }
                catch (IOException e) {
                    Log.i("TAG", "Some exception " + e);
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_permissions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
        Intent goToCycleDeVie = new Intent(getApplicationContext(), CycleDeVieActivity.class);
        Intent goToSauvegarde = new Intent(getApplicationContext(), SauvegardeActivity.class);
        Intent goToWebService = new Intent(getApplicationContext(), WebServiceActivity.class);

        switch (item.getItemId()) {
            case R.id.action_deconnexion:
                sharedPreferences.edit().remove("lastIdentifiant").remove("lastPassword").apply();
                startActivity(goToLogin);
                finish();
                return true;
            case R.id.action_cycle_de_vie :
                startActivity(goToCycleDeVie);
                return true;
            case R.id.action_sauvergarde :
                startActivity(goToSauvegarde);
                return true;
            case R.id.action_web_service :
                startActivity(goToWebService);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
