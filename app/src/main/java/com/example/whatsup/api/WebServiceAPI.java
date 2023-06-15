package com.example.whatsup.api;

import com.example.whatsup.entities.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WebServiceAPI {

 class UtilsPayload {
  public String username;
  public String password;

  public UtilsPayload(String username, String password) {
   this.username = username;
   this.password = password;
  }
 }

 @POST("api/Tokens")
 Call<String> login(@Body UtilsPayload payload);

 class UtilsRegisterPayload {
  public String username;
  public String password;
  public String displayName;
  public String profilePic;

  public UtilsRegisterPayload(String username, String password) {
   this.username = username;
   this.password = password;
   this.displayName = displayName;
   this.profilePic = profilePic;
  }
 }
 @POST("api/Users")
 Call<String> register(@Body UtilsRegisterPayload payload);


 @GET("api/Chats")
 Call<List<Contact>> getContacts(@Header("Authorization") String token);

 class ContactPayload {
  public String id;
  public String name;
  public String server;

  public ContactPayload(String id, String name, String server) {
   this.id = id;
   this.name = name;
   this.server = server;
  }
 }

 @POST("api/Chats")
 Call<Contact> addContact(@Body ContactPayload payload);


 class RegisterTokenPayload {
  public String username;
  public String token;

  public RegisterTokenPayload(String username, String token) {
   this.username = username;
   this.token = token;
  }
 }

 @POST("api/registertoken")
 Call<Void> registerToken(@Body RegisterTokenPayload payload);
}