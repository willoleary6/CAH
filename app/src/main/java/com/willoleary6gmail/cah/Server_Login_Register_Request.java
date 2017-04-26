package com.willoleary6gmail.cah;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by William on 20/02/2017.
 */

public class Server_Login_Register_Request extends StringRequest {
    private static String SERVER_REQUEST_URL;
    private Map<String,String> params;

    /*Constructor that  creates a hash map for the register and login functions*/
    public Server_Login_Register_Request(String username, String password, Response.Listener<String> listener, String URL) {
        super(Method.POST, SERVER_REQUEST_URL,listener,null);
        SERVER_REQUEST_URL = URL;
        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
    }
    public Server_Login_Register_Request(Response.Listener<String> listener, String URL) {
        super(Method.POST, SERVER_REQUEST_URL,listener,null);
        SERVER_REQUEST_URL = URL;
        params = new HashMap<>();
        params.put("username","test");
    }

    @Override
    public Map<String, String> getParams() {
        //returns the hash map to the login functions
        return params;
    }
}

