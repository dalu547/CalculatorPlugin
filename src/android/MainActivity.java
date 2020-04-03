package cordova.plugin.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.notbytes.barcode_reader.BarcodeReaderFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BarcodeReaderFragment.BarcodeReaderListener {
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;

    String package_name = ""; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        package_name = getApplication().getPackageName();
        setContentView(getApplication().getResources().getIdentifier("activity_main", "layout", package_name));

        addBarcodeReaderFragment();
    }

    private void addBarcodeReaderFragment() {
        BarcodeReaderFragment readerFragment = BarcodeReaderFragment.newInstance(true, false, View.VISIBLE);
        readerFragment.setListener(this);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        // fragmentTransaction.replace(R.id.fm_container, readerFragment);
        fragmentTransaction.replace(getApplication().getResources().getIdentifier("fm_container", "id", package_name), readerFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "error in  scanning", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
            Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onScanned(Barcode barcode) {
        Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

        Log.d("SCANNER", "onScannedMultiple: " + barcodes.size());

        String codes = "";
        for (Barcode barcode : barcodes) {
            codes += barcode.displayValue + ", ";
        }

        final String finalCodes = codes;

        Toast.makeText(this, finalCodes, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(this, "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
}
