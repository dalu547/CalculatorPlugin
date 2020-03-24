package cordova.plugin.calculator;

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

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException 
    {
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

    
}
