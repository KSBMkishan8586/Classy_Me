package com.ksbm.ontu.custom_class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.ksbm.ontu.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraActivity extends Activity {
    private Intent intent;
    private Bitmap bitmap;
    private Context context;
    private File photoFile;
    private String picturePath = "", image = "", cropPicturePath = "";
    private Uri cameraUri = null;
    int GALLERY = 111, CAMERA = 112, ACTIVITY_RESULT = 1001;
    String IMAGE_DIRECTORY = "/DCIM/PICTURES";
    Uri IMAGEURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image_chooser);
        this.setFinishOnTouchOutside(true);
        context = this;
      //  prefrence = SharedPrefrence.getInstance(context);
        // utilities = Utilities.getInstance(context);
        // appSession = new AppSession(context);


        dailogImageChooser(context, "Choose Image");

    }

    public void dailogImageChooser(final Context context, String header) {

        // TextView tvHeader = (TextView) findViewById(R.id.tv_header);
        TextView tvGallery = (TextView) findViewById(R.id.tv_gallery);
        TextView tvCamera = (TextView) findViewById(R.id.tv_camera);
        //  appSession = new AppSession(context);
        //  tvHeader.setText(header);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Ensure that there's a camera activity to handle the intent
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        // Create the File where the photo should go
                        photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            return;
                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {

                            cameraUri = FileProvider.getUriForFile(context, getApplicationContext().getPackageName() + ".provider", photoFile);


                            // appSession.setImageUri(cameraUri);
                            IMAGEURI = cameraUri;
                          //  prefrence.setValue(Consts.IMAGE_URI_CAMERA, IMAGEURI.toString());
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                            startActivityForResult(intent, CAMERA);
                        }
                    }
                } else {

                    intent = new Intent();
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String fileName = "IMAGE_" + System.currentTimeMillis() + ".jpg";
                    cameraUri = Uri.fromFile(getNewFile(IMAGE_DIRECTORY, fileName));
                    //appSession.setImageUri(cameraUri);
                    IMAGEURI = cameraUri;
                  //  prefrence.setValue(Consts.IMAGE_URI_CAMERA, IMAGEURI.toString());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, CAMERA);
                }
            }
        });

        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, ""), GALLERY);
            }
        });

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  appSession = new AppSession(context);
        if (requestCode != ACTIVITY_RESULT && resultCode != RESULT_OK) {
            Intent intentMessage = new Intent();
            // put the message in Intent
            intentMessage.putExtra("image", image);
            // Set The Result in Intent
            setResult(0, intentMessage);
            // finish The activity
            finish();

        }
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == GALLERY) {
                try {
                    Uri uriImage = data.getData();
                    if (uriImage != null) {
                        picturePath = getAbsolutePath(uriImage);
                        if (picturePath == null || picturePath.equals(""))
                            picturePath = uriImage.getPath();
                        // appSession.setImagePath(picturePath);
                        Cursor cursor = context
                                .getContentResolver()
                                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        new String[]{MediaStore.Images.Media._ID},
                                        MediaStore.Images.Media.DATA + "=? ",
                                        new String[]{picturePath}, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                            uriImage = Uri.parse("content://media/external/images/media/" + id);
                        }
                        //  performCrop(uriImage);
                        image = getPath(uriImage);
                        //**************************************
                        Intent intentMessage = new Intent();
                        // put the message in Intent
                        intentMessage.putExtra("image", image);
                        // Set The Result in Intent
                        setResult(100, intentMessage);
                        // finish The activity
                        finish();

                    } else {
                        Toast.makeText(context, getString(R.string.gallery_pick_error), Toast.LENGTH_LONG).show();
                        Intent intentMessage = new Intent();
                        // put the message in Intent
                        intentMessage.putExtra("image", image);
                        // Set The Result in Intent
                        setResult(0, intentMessage);
                        // finish The activity
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,
                            getString(R.string.gallery_pick_error),
                            Toast.LENGTH_LONG).show();
                    Intent intentMessage = new Intent();

                    // put the message in Intent
                    intentMessage.putExtra("image", image);
                    // Set The Result in Intent
                    setResult(0, intentMessage);
                    // finish The activity
                    finish();
                }
            }
            else if (requestCode == CAMERA) {
                try {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    Uri uri = Uri.parse(photoFile.getAbsolutePath());
                        picturePath = photoFile.getAbsolutePath();
                        //   appSession.setImagePath(picturePath);
                        cropPicturePath = picturePath;
                        Log.i(getClass().getName(), "Nougat Path >>>>>>>" + cropPicturePath);

                       /* Intent intentMessage = new Intent();

                        // put the message in Intent
                        intentMessage.putExtra("image", cropPicturePath);
                        // Set The Result in Intent
                        setResult(2, intentMessage);
                        // finish The activity
                        finish();*/

                        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        new String[]{MediaStore.Images.Media._ID},
                                        MediaStore.Images.Media.DATA + "=? ",
                                        new String[]{picturePath}, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            int id = cursor
                                    .getInt(cursor
                                            .getColumnIndex(MediaStore.MediaColumns._ID));
                            cameraUri = Uri
                                    .parse("content://media/external/images/media/"
                                            + id);
                        }
                        // performCrop(cameraUri);
                        image = getPath(cameraUri);
                        Intent intentMessage = new Intent();
                        // put the message in Intent
                          intentMessage.putExtra("image", image);
                        // Set The Result in Intent
                        setResult(100, intentMessage);
                        // finish The activity
                        finish();
                    } else {

                        if (cameraUri == null)
                            cameraUri = IMAGEURI;
                        if (cameraUri != null) {
                            picturePath = getAbsolutePath(cameraUri);
                            if (picturePath == null || picturePath.equals(""))
                                picturePath = cameraUri.getPath();
                            //  appSession.setImagePath(picturePath);

                            Log.i(getClass().getName(), "Simple Path >>>>>>>" + picturePath);
                            Cursor cursor = context
                                    .getContentResolver()
                                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            new String[]{MediaStore.Images.Media._ID},
                                            MediaStore.Images.Media.DATA + "=? ",
                                            new String[]{picturePath}, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                                cameraUri = Uri
                                        .parse("content://media/external/images/media/" + id);
                            }
                            // performCrop(cameraUri);
                            image = getPath(cameraUri);
                            Intent intentMessage = new Intent();
                            // put the message in Intent
                              intentMessage.putExtra("image", image);
                           // intentMessage.putExtra("image", cameraUri.toString());
                            // Set The Result in Intent
                            setResult(100, intentMessage);
                            // finish The activity
                            finish();
                        } else {
                            Log.e("rrrraaaa", "eee");
                            Toast.makeText(context, getString(R.string.camera_capture_error), Toast.LENGTH_LONG).show();
                            Intent intentMessage = new Intent();

                            // put the message in Intent
                            intentMessage.putExtra("image", image);
                            // Set The Result in Intent
                            setResult(0, intentMessage);
                            // finish The activity
                            finish();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("rrrr", "eee");
                    Toast.makeText(context,
                            getString(R.string.camera_capture_error),
                            Toast.LENGTH_LONG).show();
                    Intent intentMessage = new Intent();
                    // put the message in Intent
                    intentMessage.putExtra("image", image);
                    // Set The Result in Intent
                    setResult(0, intentMessage);
                    // finish The activity
                    finish();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intentMessage = new Intent();
        // put the message in Intent
        intentMessage.putExtra("image", image);
        // Set The Result in Intent
        setResult(0, intentMessage);
        // finish The activity
        finish();
    }

    /**
     * This method used to create new file if not exist .
     */
    public File getNewFile(String directoryName, String imageName) {
        String root = Environment.getExternalStorageDirectory()
                + directoryName;
        File file;
        if (isSDCARDMounted()) {
            new File(root).mkdirs();
            file = new File(root, imageName);
        } else {
            file = new File(context.getFilesDir(), imageName);
        }
        return file;
    }

    public boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public Bitmap decodeFile(File f, int REQUIRED_WIDTH,
                             int REQUIRED_HEIGHT) {
        try {
            ExifInterface exif = new ExifInterface(f.getPath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            int angle = 0;

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270;
            }

            Matrix mat = new Matrix();
            mat.postRotate(angle);
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            // Find the correct scale value. It should be the power of 2.
            int REQUIRED_SIZE = 100; // 70
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            if (width_tmp > height_tmp) {
                REQUIRED_SIZE = REQUIRED_HEIGHT;
                REQUIRED_HEIGHT = REQUIRED_WIDTH;
                REQUIRED_WIDTH = REQUIRED_SIZE;
            }
            while (true) {
                if (width_tmp / 2 < REQUIRED_WIDTH
                        && height_tmp / 2 < REQUIRED_HEIGHT)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            o2.inPurgeable = true;
            Bitmap correctBmp = BitmapFactory.decodeStream(new FileInputStream(
                    f), null, o2);
            correctBmp = Bitmap.createBitmap(correctBmp, 0, 0,
                    correctBmp.getWidth(), correctBmp.getHeight(), mat, true);
            return correctBmp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFilePath(Bitmap bitmap, Context context, String path) {
        //  File cacheDir;
        File file;

        try {

            if (bitmap != null) {
                file = new File(path);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bytes);
                FileOutputStream fo;

                fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();

                return file.getAbsolutePath();
            }

        } catch (Exception | Error e1) {
            e1.printStackTrace();

        }

        return "";
    }

    private String getPath(Uri selectedImaeUri) {
        String[] projection = {MediaStore.Images.Media.DATA};

      //  Cursor cursor = managedQuery(selectedImaeUri, projection, null, null, null);
        Cursor cursor = getContentResolver().query(selectedImaeUri, projection, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            return cursor.getString(columnIndex);
        }

        return selectedImaeUri.getPath();

        //****************************************
//        String id = selectedImaeUri.getLastPathSegment().split(":")[0];
//        final String[] imageColumns = {MediaStore.Images.Media.DATA };
//        final String imageOrderBy = null;
//       // Uri tempUri = getUri();
//        Cursor imageCursor = getContentResolver().query(selectedImaeUri, imageColumns,
//                MediaStore.Images.Media._ID + "="+id, null, imageOrderBy);
//        if (imageCursor.moveToFirst()) {
//            return imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
//        }else{
//            return null;
//        }
    }

}
