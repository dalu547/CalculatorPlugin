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

    private static final String LOG_TAG = "Calculator";
    private CallbackContext callbackContext;

    private String [] permissions = { Manifest.permission.CAMERA };

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException 
    {
        this.callbackContext = callbackContext;
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

            //android permission auto add
            if(!hasPermisssion()) {
              requestPermissions(0);
            } else {
              scan(args);
            }
        } else {
            return false;
        }

        
        return false;
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
    {
           try 
           {
              
                cordova.getThreadPool().execute(new Runnable() {
            public void run() {

                Intent intentScan = new Intent(that.cordova.getActivity().getBaseContext(), CaptureActivity.class);
                intentScan.setAction(Intents.Scan.ACTION);
                intentScan.addCategory(Intent.CATEGORY_DEFAULT);

                // add config as intent extras
                // if (args.length() > 0) {

                    // JSONObject obj;
                    // JSONArray names;
                    // String key;
                    // Object value;

                    // for (int i = 0; i < args.length(); i++) {

                        // try {
                        //     obj = args.getJSONObject(i);
                        // } catch (JSONException e) {
                        //     Log.i("CordovaLog", e.getLocalizedMessage());
                        //     continue;
                        // }

                        // names = obj.names();
                        // for (int j = 0; j < names.length(); j++) {
                        //     try {
                        //         key = names.getString(j);
                        //         value = obj.get(key);

                        //         if (value instanceof Integer) {
                        //             intentScan.putExtra(key, (Integer) value);
                        //         } else if (value instanceof String) {
                        //             intentScan.putExtra(key, (String) value);
                        //         }

                        //     } catch (JSONException e) {
                        //         Log.i("CordovaLog", e.getLocalizedMessage());
                        //     }
                        // }

                        // intentScan.putExtra(Intents.Scan.CAMERA_ID, obj.optBoolean(PREFER_FRONTCAMERA, false) ? 1 : 0);
                        // intentScan.putExtra(Intents.Scan.SHOW_FLIP_CAMERA_BUTTON, obj.optBoolean(SHOW_FLIP_CAMERA_BUTTON, false));
                        // intentScan.putExtra(Intents.Scan.SHOW_TORCH_BUTTON, obj.optBoolean(SHOW_TORCH_BUTTON, false));
                        // intentScan.putExtra(Intents.Scan.TORCH_ON, obj.optBoolean(TORCH_ON, false));
                        // intentScan.putExtra(Intents.Scan.SAVE_HISTORY, obj.optBoolean(SAVE_HISTORY, false));

                        // boolean beep = obj.optBoolean(DISABLE_BEEP, false);
                        // intentScan.putExtra(Intents.Scan.BEEP_ON_SCAN, !beep);
                        // if (obj.has(RESULTDISPLAY_DURATION)) {
                        //     intentScan.putExtra(Intents.Scan.RESULT_DISPLAY_DURATION_MS, "" + obj.optLong(RESULTDISPLAY_DURATION));
                        // }
                        // if (obj.has(FORMATS)) {
                        //     intentScan.putExtra(Intents.Scan.FORMATS, obj.optString(FORMATS));
                        // }
                        // if (obj.has(PROMPT)) {
                        //     intentScan.putExtra(Intents.Scan.PROMPT_MESSAGE, obj.optString(PROMPT));
                        // }
                        // if (obj.has(ORIENTATION)) {
                        //     intentScan.putExtra(Intents.Scan.ORIENTATION_LOCK, obj.optString(ORIENTATION));
                        // }
                    // }

                // }

                // avoid calling other phonegap apps
                intentScan.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());

                that.cordova.startActivityForResult(that, intentScan, REQUEST_CODE);
            }
        });

            //  callbackContext.success();
            } catch (Exception e) 
            {
                 callbackContext.error("scan failed");
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
                    Log.d(LOG_TAG, "This should never happen");
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
            }
        }
    }

    
}
