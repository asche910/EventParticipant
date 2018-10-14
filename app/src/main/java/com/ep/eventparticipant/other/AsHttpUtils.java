package com.ep.eventparticipant.other;

import android.util.Log;

import com.ep.eventparticipant.object.EventBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;
import static com.ep.eventparticipant.fragment.FragmentHome.eventBeanList;
import static com.ep.eventparticipant.fragment.FragmentHome.mySearchList;
import static com.ep.eventparticipant.fragment.FragmentUser.curUser;

/**
 * 接口请求工具类
 *
 * @author As_
 * @since 2018/07/24
 * @email apknet@163.com
 * @github https://github.com/apknet
 *
 */

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
            Log.e(TAG, "login: " + result );
            //获得返回头部信息中的Cookie信息并处理
            String setCookie = response.header("Set-Cookie");
            Log.e(TAG, "login: " + setCookie );
            cookie = setCookie.substring(0, setCookie.indexOf(";")).trim();
            Log.e(TAG, "login: " + cookie );
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
        Log.e(TAG, "register: ---> name: "  + name + " userName: " + userName);
        String url = String.format("http://120.79.137.167:8080/firstProject/user/register.do?userName=%s&password=%s&name=%s",
                userName, pass, name);
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            int code = new JSONObject(result).getInt("status");

            Log.e(TAG, "register: ---> result:" + result);
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
            result = response.body().string();
            Log.e(TAG, "upImage: " + result );
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
                .header("Cookie", cookie)
                .build();
        try {
            Response response = client.newCall(request).execute();
            int code = new JSONObject(response.body().string()).getInt("status");
            // 0成功， 1失败
            Log.e(TAG, "createActivity " + code );
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
                .header("Cookie", cookie)
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
                .header("Cookie", cookie)
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
                .header("Cookie", cookie)
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

    //    活动列表
    public static List<EventBean> allActivity(){
        List<EventBean> events = new ArrayList<>();
        Request request = new Request.Builder()
                .url("http://120.79.137.167:8080/firstProject/activity/List.do")
                .header("Cookie", cookie)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            int code = jsonObject.getInt("status");
            int pages = jsonObject.getJSONObject("data").getInt("pages");
            if (code == 0){
                for(int i = 1; i <= pages; i++){
                    events.addAll(allActivity(i));
                }
            }
            return events;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    活动列表(内部方法)
    private static List<EventBean> allActivity(int  page){
        Request request = new Request.Builder()
                .url("http://120.79.137.167:8080/firstProject/activity/List.do?pageNum=" + page)
                .header("Cookie", cookie)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            int code = jsonObject.getInt("status");
            JSONArray ja = jsonObject.getJSONObject("data").getJSONArray("list");
            if (code == 0) {
                List<EventBean> allEvents = new ArrayList<>();
//                eventBeanList.clear();
                Log.e(TAG, "run: ja size -------------------------------->" + ja.length() );

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jb = ja.getJSONObject(i);
                    EventBean eventBean = new EventBean();
                    eventBean.setId(jb.getInt("activityId"));
                    eventBean.setName(jb.getString("activityName"));

                    try {
                        eventBean.setStartTime(jb.getString("activityTime").substring(0, jb.getString("activityTime").indexOf("-")).trim());
                        eventBean.setEndTime(jb.getString("activityTime").substring(jb.getString("activityTime").indexOf("-") + 1));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "allActivity: illegal time!" );
                    }

                    eventBean.setWhere(jb.getString("address"));
                    eventBean.setImgUri(jb.getString("activityImageurl").replaceAll("\\\\", ""));
                    eventBean.setNote(jb.getString("introduction"));

//                eventBean.setOrganizerId(0);
                    eventBean.setOrganizerName(jb.getString("createrName"));
                    eventBean.setOrganizerHeader(jb.getString("createrImageurl"));
                    eventBean.setOrganizerNote(jb.getString("createrSignature"));
                    eventBean.setOrganizerTel(jb.getString("createrPhone"));

                    eventBean.setPersonCount(jb.getInt("peopleNumber"));

                    allEvents.add(eventBean);
                }
                Log.e(TAG, "allActivity:  allEvents.size() = " + allEvents.size() );
                return allEvents;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    搜索活动
    public static int searchActivity(String args, boolean isInt){
        String url_int = null;
        String url_str = null;

        Request.Builder builder = new Request.Builder();
        if (isInt) {
            url_int = String.format("http://120.79.137.167:8080/firstProject/activity/search.do?activityId=%s", args);
            builder.url(url_int);
            builder.header("Cookie", cookie);
        }else{
            url_str = String.format("http://120.79.137.167:8080/firstProject/activity/search.do?activityName=%s", args);
            builder.url(url_str);
            builder.header("Cookie", cookie);
            Log.e(TAG, "searchActivity: " + cookie );
        }

        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());

            Log.e(TAG, "searchActivity: " + jsonObject.toString() );
            int code = jsonObject.getInt("status");

            JSONArray ja = jsonObject.getJSONObject("data").getJSONArray("list");
            mySearchList.clear();
            for (int i = 0; i< ja.length(); i++){
                JSONObject jb = ja.getJSONObject(i);
                EventBean eventBean = new EventBean();
                eventBean.setId(jb.getInt("activityId"));
                eventBean.setName(jb.getString("activityName"));
                eventBean.setStartTime(jb.getString("activityTime").substring(0, jb.getString("activityTime").indexOf("-")).trim());
                eventBean.setEndTime(jb.getString("activityTime").substring(jb.getString("activityTime").indexOf("-") + 1 ));
                eventBean.setWhere(jb.getString("address"));
                eventBean.setImgUri(jb.getString("activityImageurl").replaceAll("\\\\", ""));
                eventBean.setNote(jb.getString("introduction"));

//                eventBean.setOrganizerId(0);
                eventBean.setOrganizerName(jb.getString("createrName"));
                eventBean.setOrganizerHeader(jb.getString("createrImageurl"));
                eventBean.setOrganizerNote(jb.getString("createrSignature"));
                eventBean.setOrganizerTel(jb.getString("createrPhone"));

                eventBean.setPersonCount(jb.getInt("peopleNumber"));
                mySearchList.add(eventBean);
            }
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
