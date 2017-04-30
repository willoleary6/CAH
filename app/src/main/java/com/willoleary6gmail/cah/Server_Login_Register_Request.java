package com.willoleary6gmail.cah;

import android.content.Context;
import android.content.SharedPreferences;

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
        super(Method.POST,URL,listener,null);

        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
    }
    public Server_Login_Register_Request(Response.Listener<String> listener, String URL) {
        super(Method.POST,URL,listener,null);
        params = new HashMap<>();
        params.put("username","test");
    }
    public Server_Login_Register_Request(String username, String password,String LobbyName,String LobbyPasscode, boolean swtch, Response.Listener<String> listener, String URL) {
        super(Method.POST,URL,listener,null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
        params.put("GameName",LobbyName);
        if(!swtch){
            params.put("lobbyPassCode", LobbyPasscode);
        }
    }
    public Server_Login_Register_Request(String gameName, String gamePassword,String user_id, Response.Listener<String> listener, String URL) {
        super(Method.POST,URL,listener,null);
        params = new HashMap<>();
        params.put("game",gameName);
        params.put("user_id",user_id);
        params.put("password",gamePassword);
    }
    public Server_Login_Register_Request(String gameId,Response.Listener<String> listener, String URL) {
        super(Method.POST,URL,listener,null);

        params = new HashMap<>();
        params.put("game_id",gameId);
    }

    @Override
    public Map<String, String> getParams() {
        //returns the hash map to the login functions
        return params;
    }
}

