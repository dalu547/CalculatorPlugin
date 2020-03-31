package cordova.plugin.calculator;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import javafx.scene.text.Text;
import android.content.pm.PackageManager;



import org.apache.cordova.PluginResult;
import org.apache.cordova.PermissionHelper;



import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import android.provider.Settings;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.FrameLayout;
import android.graphics.Color;

/**
 * This class echoes a string called from JavaScript.
 */
public class Calculator extends CordovaPlugin 
{
    public static final int REQUEST_CODE = 0x0ba7c;

    private static final String SCAN = "scan";
    private static final String ENCODE = "encode";
    private static final String CANCELLED = "cancelled";
    private static final String FORMAT = "format";
    private static final String TEXT = "text";
    private static final String DATA = "data";
    private static final String TYPE = "type";
    private static final String PREFER_FRONTCAMERA = "preferFrontCamera";
    private static final String ORIENTATION = "orientation";
    private static final String SHOW_FLIP_CAMERA_BUTTON = "showFlipCameraButton";
    private static final String RESULTDISPLAY_DURATION = "resultDisplayDuration";
    private static final String SHOW_TORCH_BUTTON = "showTorchButton";
    private static final String TORCH_ON = "torchOn";
    private static final String SAVE_HISTORY = "saveHistory";
    private static final String DISABLE_BEEP = "disableSuccessBeep";
    private static final String FORMATS = "formats";
    private static final String PROMPT = "prompt";
    private static final String TEXT_TYPE = "TEXT_TYPE";
    private static final String EMAIL_TYPE = "EMAIL_TYPE";
    private static final String PHONE_TYPE = "PHONE_TYPE";
    private static final String SMS_TYPE = "SMS_TYPE";

    private static final String LOG_TAG = "ScannerPlugin";
    private CallbackContext callbackContext;

    private String [] permissions = { Manifest.permission.CAMERA };

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        // addView();
        
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException 
    {
        this.callbackContext = callbackContext;
        showToast("execute");
        if (action.equals("add")) 
        {
            this.add(args, callbackContext);
            return true;
        }else if (action.equals("multiply")) 
        {
            this.multiply (args, callbackContext);
            return true;
        }else if (action.equals("divide")) 
        {
            this.divide (args, callbackContext);
            return true;
        }else if (action.equals("substract")) 
        {
            this.substract (args, callbackContext);
            return true;
        }else if (action.equals("flashOnOff")) 
        {
            this.flashOnOff (args, callbackContext);
            return true;
        }else if (action.equals(SCAN)) {
            showToast("scan action in exxcute");
            //android permission auto add
            if(!hasPermisssion()) {
              requestPermissions(0);
              return false;
            } else {
              scan(args,callbackContext);
              return true;
            }

        } else {
            return false;
        }

    }

    

    private void add(JSONArray args, CallbackContext callbackContext) 
    {
        if (args != null) 
        {
           try 
           {
                int p1 =  Integer.parseInt(args.getJSONObject(0).getString("param1"));
                int p2 = Integer.parseInt(args.getJSONObject(0).getString("param2"));
                callbackContext.success(""+ (p1+p2));
            } catch (Exception e) 
            {
                 callbackContext.error("Invalid add operation");
            }
           
        } else 
        {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    private void multiply(JSONArray args, CallbackContext callbackContext) 
    {
        if (args != null) 
        {
           try 
           {
                int p1 =  Integer.parseInt(args.getJSONObject(0).getString("param1"));
                int p2 = Integer.parseInt(args.getJSONObject(0).getString("param2"));
                callbackContext.success(""+ (p1*10));
            } catch (Exception e) 
            {
                callbackContext.error("Invalid multiply operation");
            }
           
        } else 
        {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    private void substract(JSONArray args, CallbackContext callbackContext) 
    {
        if (args != null) 
        {
           try 
           {
                int p1 =  Integer.parseInt(args.getJSONObject(0).getString("param1"));
                int p2 = Integer.parseInt(args.getJSONObject(0).getString("param2"));
                callbackContext.success(""+ (p1-p2));
            } catch (Exception e) 
            {
                callbackContext.error("Invalid substract operation");
            }
           
        } else 
        {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void divide(JSONArray args, CallbackContext callbackContext) 
    {
        if (args != null) 
        {
           try 
           {
                int p1 =  Integer.parseInt(args.getJSONObject(0).getString("param1"));
                int p2 = Integer.parseInt(args.getJSONObject(0).getString("param2"));
                callbackContext.success(""+ (p1/p2));
            } catch (Exception e) 
            {
                 callbackContext.error("Invalid divide operation");
            }
           
        } else 
        {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

   

    private void flashOnOff(JSONArray args, CallbackContext callbackContext) 
    {
        if (args != null) 
        {
           try 
           {
                int p1 =  Integer.parseInt(args.getJSONObject(0).getString("param1"));
                int p2 = Integer.parseInt(args.getJSONObject(0).getString("param2"));
                
                Log.d("PluginTest","Hello, Iam from plugin");
                
                
                CameraManager cameraManager = (CameraManager)this.cordova.getActivity().getSystemService(Context.CAMERA_SERVICE);
                String cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, true);
                
                
                String uuid = Settings.Secure.getString(this.cordova.getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                
                String model = android.os.Build.MODEL;
                callbackContext.success("Uuid: "+uuid+" model: "+model);
                
           } catch (Exception e) {
               e.printStackTrace();
            }
       
        } else 
        {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void scan(JSONArray args, CallbackContext callbackContext) 
    {      showToast("scan start");
           final CordovaPlugin that = this;
           try 
           {
                cordova.getThreadPool().execute(new Runnable() {
                public void run() 
                {
                    Intent intentScan = new Intent(that.cordova.getActivity().getBaseContext(), CaptureActivity.class);
                    intentScan.setAction(Intents.Scan.ACTION);
                    intentScan.addCategory(Intent.CATEGORY_DEFAULT);
                    // avoid calling other phonegap apps
                    intentScan.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
                    that.cordova.startActivityForResult(that, intentScan, REQUEST_CODE);

                    addView();
                }
        });

            //  callbackContext.success();
            } catch (Exception e) 
            {
                 callbackContext.error("scan failed");
                 showToast("scan exception");
            }
                
    }

    public boolean hasPermisssion() {
        for(String p : permissions)
        {
            if(!PermissionHelper.hasPermission(this, p))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        showToast("onActivityResult");
        if (requestCode == REQUEST_CODE && this.callbackContext != null) {
            if (resultCode == Activity.RESULT_OK) {
                String text = "";
                JSONObject obj = new JSONObject();
                try {
                    text = intent.getStringExtra("SCAN_RESULT");
                    obj.put(TEXT, intent.getStringExtra("SCAN_RESULT"));
                    obj.put(FORMAT, intent.getStringExtra("SCAN_RESULT_FORMAT"));
                    obj.put(CANCELLED, false);
                } catch (JSONException e) {
                    showToast("onActivityResult exception:");
                }
                //this.success(new PluginResult(PluginResult.Status.OK, obj), this.callback);
                this.callbackContext.success("sucess code is:"+text);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                String text = "";
                JSONObject obj = new JSONObject();
                try {
                    obj.put(TEXT, "");
                    obj.put(FORMAT, "");
                    obj.put(CANCELLED, true);
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "This should never happen");
                }
                //this.success(new PluginResult(PluginResult.Status.OK, obj), this.callback);
                this.callbackContext.success("sucess code cancelled is: empty");
            } else {
                //this.error(new PluginResult(PluginResult.Status.ERROR), this.callback);
                this.callbackContext.error("Unexpected error");
                showToast("onActivityResult: Unexpected error");

            }
        }
    }

    public void showToast(String toLog){
        Context context = cordova.getActivity();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, toLog, duration);
        toast.show();

        showLog(toLog);
    }

    public void showLog(String logMessage){
        
        Log.d(LOG_TAG, logMessage);
    }

    // public void addUI(){

    //     LinearLayout ll = new LinearLayout(this);
    //     ll.setOrientation(LinearLayout.HORIZONTAL);
        
    //     // Configuring the width and height of the linear layout.
    //     LayoutParams llLP = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
    //             LayoutParams.WRAP_CONTENT);
    //     ll.setLayoutParams(llLP);

    //     Button btnConsume = new Button(this);
    //     LayoutParams lp = new LayoutParams(
    //             LayoutParams.WRAP_CONTENT,
    //             LinearLayout.LayoutParams.WRAP_CONTENT);

    //     btnConsume.setLayoutParams(lp);

    //     btnConsume.setText("consume");

    //     btnConsume.setPadding(8, 8, 8, 8);
    //     ll.addView(btnConsume);

    //     Button btnDelay = new Button(this);

    //     btnDelay.setLayoutParams(lp);

    //     btnDelay.setText("delay");

    //     btnDelay.setPadding(8, 8, 8, 8);
    //     ll.addView(btnDelay);

    //     Button btnSkip = new Button(this);

    //     btnSkip.setLayoutParams(lp);

    //     btnSkip.setText("skip");

    //     btnSkip.setPadding(8, 8, 8, 8);
    //     ll.addView(btnSkip);


    //     //Now finally attach the Linear layout to the current Activity.
    //     setContentView(ll);

    //     this.cordova.getActivity().getWindow().addContentView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
    //     ViewGroup.LayoutParams.WRAP_CONTENT));

    // }
       
    public void addView(){

    
        FrameLayout layout = (FrameLayout) webView.getView().getParent();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        
        Button btnConsume = new Button(layout.getContext());
        btnConsume.setLayoutParams(params);
        btnConsume.setText("Consume");
        btnConsume.setTextColor(Color.WHITE);
        layout.addView(btnConsume);

        Button btnSkip = new Button(layout.getContext());
        btnSkip.setLayoutParams(params);
        btnSkip.setText("Skip");
        btnSkip.setTextColor(Color.WHITE);
        layout.addView(btnSkip);
        
        Button btnDelay = new Button(layout.getContext());
        btnDelay.setLayoutParams(params);
        btnDelay.setText("Delay");
        btnDelay.setTextColor(Color.WHITE);
        layout.addView(btnDelay);
    }
}
