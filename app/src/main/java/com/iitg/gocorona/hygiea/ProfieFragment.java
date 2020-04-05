package com.iitg.gocorona.hygiea;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iitg.gocorona.R;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfieFragment extends Fragment {
    ImageView record1;
    Bitmap bmp;
    private int REQ_CODE_PICK_IMAGE = 1;


    public ProfieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profie_hygiea, container, false);
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("signin", Context.MODE_PRIVATE);
        ImageView i = view.findViewById(R.id.imageView2);
        record1 = view.findViewById(R.id.record1);
        if (sharedpreferences.getString("medical", "").equals("")) {

        } else {
            byte[] encodeByte = Base64.decode(sharedpreferences.getString("medical", ""), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            record1.setImageBitmap(bitmap);
        }
        TextView t = view.findViewById(R.id.namewa);
        TextView q = view.findViewById(R.id.emailwa);
        q.setText(sharedpreferences.getString("email", ""));
        t.setText(sharedpreferences.getString("name", ""));
        i.setScaleType(ImageView.ScaleType.FIT_XY);
        de.hdodenhof.circleimageview.CircleImageView image = view.findViewById(R.id.profile);
        Glide.with(this).load(sharedpreferences.getString("photo", "")).into(image);
        return view;
    }

    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    record1.setImageBitmap(yourSelectedImage);
                }
        }
    }
}

