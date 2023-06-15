package com.ai.qrmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Qrmaker extends AppCompatActivity {

    Button btn_generate;
    EditText edt_text;
    ImageView img_qr;
    String enteredvalue;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrmaker);

        btn_generate=(Button)findViewById(R.id.btn_generate);
        edt_text=(EditText)findViewById(R.id.edt_text);
        img_qr=(ImageView)findViewById(R.id.img_qr);

        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredvalue=edt_text.getText().toString();

                try
                {
                    bitmap=Texttoimageencode(enteredvalue);

                    img_qr.setImageBitmap(bitmap);
                }catch (WriterException e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

    Bitmap Texttoimageencode(String value)throws WriterException
    {
        BitMatrix bitMatrix;
        try
        {
            bitMatrix=new MultiFormatWriter().encode(value, BarcodeFormat.DATA_MATRIX.QR_CODE,QRcodeWidth,QRcodeWidth,null);

        }catch (IllegalArgumentException e)
        {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y=0; y<bitMatrixHeight; y++)
        {
            int offset=y*bitMatrixWidth;
            for (int x=0;x<bitMatrixWidth;x++)
            {
                pixels[offset + x]=bitMatrix.get(x,y)?getResources().getColor(R.color.colorAccent):getResources().getColor(R.color.QRWHITECOLOR);
            }
        }
        Bitmap bitmap=Bitmap.createBitmap(bitMatrixWidth,bitMatrixHeight,Bitmap.Config.ARGB_8888);

        bitmap.setPixels(pixels,0,500,0,0,bitMatrixWidth,bitMatrixHeight);
        return bitmap;
    }

}
