package com.ep.eventparticipant.other;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.ep.eventparticipant.fragment.FragmentUser.curUser;

public class AsHttpUtils {
    private static   OkHttpClient client = new OkHttpClient();
    private static String cookie;

    public static int login(String user, String pass){
        String url = String.format("http://120.79.137.167:8080/firstProject/user/login.do?userName=%s&password=%s", user, pass);
        Request request = new Request.Builder()
                .url(url)
                .build();
        String result = "";
        try {
            Response response = client.newCall(request).execute();
            //获得返回的主体
            result = response.body().string();
            //获得返回头部信息中的Cookie信息并处理
            String setCookie = response.header("Set-Cookie");
            cookie = setCookie.substring(0, setCookie.indexOf(";")).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jb = new JSONObject(result).getJSONObject("data");
            curUser.setId(jb.getInt("id"));
            curUser.setName(jb.getString("name"));
            curUser.setUsername(jb.getString("username"));
            curUser.setSignature(jb.getString("signature"));
            curUser.setImageurl(jb.getString("imageurl"));
            curUser.setPhone(jb.getString("phone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            return new JSONObject(result).getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int register(String name, String userName, String pass){
        String url = String.format("http://120.79.137.167:8080/firstProject/user/register.do?userName=%s&password=%s&name=%s",
                userName, pass, name);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            int code = new JSONObject(response.body().toString()).getInt("status");
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    //图片上传
    public static String upImage(File file){
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("upload_activity_picture_file", "data.png",
                        RequestBody.create(MediaType.parse("image/jpg"), file))
                .build();
        Request request = new Request.Builder()
                .url("http://120.79.137.167:8080/firstProject/activity/uploadActivityPicture.do")
                .header("Cookie", cookie)
                .post(requestBody)
                .build();
        String result = "";
        try {
            Response response = client.newCall(request).execute();
            result = response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imgUrl = "";
        try {
            imgUrl = new JSONObject(result).getJSONObject("data").getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imgUrl;
    }

//    发布活动
    public static int createActivity(int id, String name, String time, String address, String note, String imgUrl){
        String url = String.format(
                "http://120.79.137.167:8080/firstProject/activity/release.do?id=%d&name=%s&time=%s&address=%s&introduction=%s&imageurl=%s",
                id, name, time, address, note, imgUrl);

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            int code = new JSONObject(response.body().string()).getInt("status");
            // 0成功， 1失败
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
//    修改活动
    public static int updateActivity(int id, String name, String time, String address, String note, String imgUrl){
        String url = String.format(
                "http://120.79.137.167:8080/firstProject/activity/release.do?id=%d&name=%s&time=%s&address=%s&introduction=%s&imageurl=%s",
                id, name, time, address, note, imgUrl);

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            int code = new JSONObject(response.body().string()).getInt("status");
            // 0成功， 1失败
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

//    报名活动
    public static int joinActivity(int id){
        String url = String.format("http://120.79.137.167:8080/firstProject/activity_member/sign_up.do?activityId=%d", id);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            int code = new JSONObject(response.body().string()).getInt("status");
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

//    取消报名
    public static int exitActivity(int id){
        String url = String.format("http://120.79.137.167:8080/firstProject/activity_member/cancel_sign_up.do?activityId=%d", id);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            int code = new JSONObject(response.body().string()).getInt("status");
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

//    搜索活动
    public static int searchActivity(String args, boolean isInt){
        String url_int = String.format("http://120.79.137.167:8080/firstProject/search.do?activityId=%d", args);
        String url_str = String.format("http://120.79.137.167:8080/firstProject/search.do?activityName=%s", args);

        Request.Builder builder = new Request.Builder();
        if (isInt) {
            builder.url(url_int);
        }else{
            builder.url(url_str);
        }
        Request request = builder.build();


        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            int code = jsonObject.getInt("status");



        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }



}
