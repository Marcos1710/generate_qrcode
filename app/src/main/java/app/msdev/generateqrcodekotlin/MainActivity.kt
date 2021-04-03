package app.msdev.generateqrcodekotlin

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class MainActivity : AppCompatActivity() {

    var editQRcode : EditText? = null
    var btnGenerateQRcode : Button? = null
    var imgQRcode : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()

        btnGenerateQRcode!!.setOnClickListener {
            if (TextUtils.isEmpty(editQRcode!!.text.toString())) {
                editQRcode!!.error = "*"
                editQRcode!!.requestFocus()
            } else {
                generateQRcode(editQRcode!!.text.toString())
            }
        }
    }

    fun initComponents() {
        editQRcode = findViewById(R.id.editQRcode)
        btnGenerateQRcode = findViewById(R.id.btnGenerateQRcode)
        imgQRcode = findViewById(R.id.imgQRcode)
    }

    fun generateQRcode(QRcodeContent: String) {
        // zxing-android-embedded:3.5.0

        var qrCode = QRCodeWriter()

        try {
            val bitMatrix = qrCode.encode(QRcodeContent, BarcodeFormat.QR_CODE, 196, 196)

            val width = bitMatrix.width
            val height = bitMatrix.height

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

            imgQRcode!!.setImageBitmap(bitmap)

        } catch (e: WriterException) {
            print(e)
        }
    }
}