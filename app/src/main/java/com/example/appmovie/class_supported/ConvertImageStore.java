package com.example.appmovie.class_supported;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ConvertImageStore {
    // Convert img to byte arr
    public static byte[] convertImageToByArr (Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    // Convert byte arr to img
    public static Bitmap convertByArrToImage(byte[] arr) {
        return BitmapFactory.decodeByteArray(arr, 0, arr.length);
    }

    // Reduce image size
    public static byte[] imagemTratada(byte... image_img){

        while (image_img.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(image_img, 0, image_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image_img = stream.toByteArray();
        }

        return image_img;
    }


}
