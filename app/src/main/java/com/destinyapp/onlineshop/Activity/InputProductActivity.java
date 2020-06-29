package com.destinyapp.onlineshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.destinyapp.onlineshop.API.ApiRequest;
import com.destinyapp.onlineshop.API.RetroServer;
import com.destinyapp.onlineshop.BuildConfig;
import com.destinyapp.onlineshop.Model.ResponseModel;
import com.destinyapp.onlineshop.R;
import com.destinyapp.onlineshop.SharedPreferance.DB_Helper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputProductActivity extends AppCompatActivity {
    Button browse,submit;
    EditText NamaBarang,HargaBarang,Quantity,Deskripsi;
    TextView tvNamaGambar;
    ImageView ivGambar;
    String gambar = "default.png";

    //Dellaroy Logic
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private Uri mMediaUri;
    private static final int CAMERA_PIC_REQUEST = 1111;

    private static final String TAG =InputProductActivity.class.getSimpleName();

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    private String mediaPath;

    private Button btnCapturePicture;

    private String mImageFileLocation = "";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ProgressDialog pDialog;
    String postGambar = "";
    Boolean Gambar = false;

    DB_Helper dbHelper;
    String username,nama,email,profile,alamat,level;

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_product);
        browse=findViewById(R.id.btnGambar);
        tvNamaGambar=findViewById(R.id.tvGambar);
        ivGambar=findViewById(R.id.ivGambar);
        NamaBarang=findViewById(R.id.etNama);
        HargaBarang=findViewById(R.id.etHarga);
        Quantity=findViewById(R.id.etQuantity);
        Deskripsi=findViewById(R.id.etDeskripsi);
        submit=findViewById(R.id.btnSubmit);
        dbHelper = new DB_Helper(InputProductActivity.this);
        Cursor cursor = dbHelper.checkSession();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                username = cursor.getString(0);
                nama = cursor.getString(1);
                email = cursor.getString(2);
                profile = cursor.getString(3);
                alamat = cursor.getString(4);
                level = cursor.getString(5);
            }
        }
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gambar=true;
                UploadImage();
            }
        });
    }

    private void Logic(){
        if (NamaBarang.getText().toString().isEmpty()){
            Toast.makeText(this, "Nama Barang Harus Diisi", Toast.LENGTH_SHORT).show();
        }else if(HargaBarang.getText().toString().isEmpty()){
            Toast.makeText(this, "Harga Barang Harus Diisi", Toast.LENGTH_SHORT).show();
        }else if(Quantity.getText().toString().isEmpty()){
            Toast.makeText(this, "Quantity Barang Harus Diisi", Toast.LENGTH_SHORT).show();
        }else if(Deskripsi.getText().toString().isEmpty()){
            Toast.makeText(this, "Deskripsi Barang Harus Diisi", Toast.LENGTH_SHORT).show();
        }else{
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Sedang Menyimpan data ke Server");
            pd.setCancelable(false);
            pd.show();
            File fileFoto = new File(postGambar);
            RequestBody fileReqBodyFoto = RequestBody.create(MediaType.parse("image/*"), fileFoto);
            MultipartBody.Part partFoto = MultipartBody.Part.createFormData("fotopemilik", fileFoto.getName(), fileReqBodyFoto);

            ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponseModel> data = api.InputBarang(RequestBody.create(MediaType.parse("text/plain"),username),
                    RequestBody.create(MediaType.parse("text/plain"),NamaBarang.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),HargaBarang.getText().toString()),
                    RequestBody.create(MediaType.parse("text/plain"),Quantity.getText().toString()),
                    partFoto,
                    RequestBody.create(MediaType.parse("text/plain"),Deskripsi.getText().toString())
                    );
            data.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    pd.hide();
                    Toast.makeText(InputProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    pd.hide();
                    Toast.makeText(InputProductActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void UploadImage(){
        new MaterialDialog.Builder(this)
                .title(R.string.uploadImages)
                .items(R.array.uploadImages)
                .itemsIds(R.array.itemIds)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                                break;
//                                    case 1:
//                                        captureImage();
//                                        break;
                            case 1:
                                tvNamaGambar.setText("Tidak Ada File yang Terpilih");
                                break;
                        }
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "Check OnActivity", Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
            if (data != null) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);

                // Set the Image in ImageView for Previewing the Media

//                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

                if (Gambar) {
                    postGambar = mediaPath;
                    String filename = postGambar.substring(postGambar.lastIndexOf("/") + 1);
                    ivGambar.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    tvNamaGambar.setText(filename);
                    Gambar = false;
                }
            }
        }
    }

    //Dellaroy Logic
    private void captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // We give some instruction to the intent to save the image
            File photoFile = null;

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile();
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            Uri outputUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }
    File createImageFile() throws IOException {
        Logger.getAnonymousLogger().info("Generating the image - method started");

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        // Here we specify the environment location and the exact path where we want to save the so-created file
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app");
        Logger.getAnonymousLogger().info("Storage directory set");

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir();

        // Here we create the file using a prefix, a suffix and a directory
        File image = new File(storageDirectory, imageFileName + ".jpg");
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set");

        mImageFileLocation = image.getAbsolutePath();
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image;
    }
}
