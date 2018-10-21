package com.ep.eventparticipant.other;

import android.util.Log;

import com.ep.eventparticipant.Item.All_item;
import com.ep.eventparticipant.Item.Exchangeitem;
import com.ep.eventparticipant.object.EventBean;
import com.ep.eventparticipant.object.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.tablemanager.Generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;
import static com.ep.eventparticipant.fragment.FragmentHome.eventBeanList;
import static com.ep.eventparticipant.fragment.FragmentHome.myCreatedList;
import static com.ep.eventparticipant.fragment.FragmentHome.myJoinedList;
import static com.ep.eventparticipant.fragment.FragmentHome.mySearchList;
import static com.ep.eventparticipant.fragment.FragmentSwap.exchangeitemList;
import static com.ep.eventparticipant.fragment.FragmentUser.curUser;

/**
 * 接口请求工具类
 *
 * @author As_
 * @email apknet@163.com
 * @github https://github.com/asche910
 * @since 2018/07/24
 */

public class AsHttpUtils {
    private static OkHttpClient client = new OkHttpClient();
    private static String cookie;
    public static List<All_item> all_items = new ArrayList<>();


    public static int login(String user, String pass) {
        String url = String.format("http://120.79.137.167:8080/firstProject/user/login.do?userName=%s&password=%s", user, pass);
        Request request = new Request.Builder()
                .url(url)
                .build();
        String result = "";
        try {
            Response response = client.newCall(request).execute();
            //获得返回的主体
            result = response.body().string();
            Log.e(TAG, "login: " + result);
            //获得返回头部信息中的Cookie信息并处理
            String setCookie = response.header("Set-Cookie");
            Log.e(TAG, "login: " + setCookie);
            cookie = setCookie.substring(0, setCookie.indexOf(";")).trim();
            Log.e(TAG, "login: " + cookie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jb = new JSONObject(result).getJSONObject("data");
            curUser.setId(jb.getInt("id"));
            curUser.setName(jb.getString("name"));
            curUser.setUsername(jb.getString("username"));
            curUser.setSignature(jb.getString("signature"));
            curUser.setImageurl(jb.getString("imageurl").replaceAll("\\\\", ""));
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

    public static int logout(){
        String url = "http://120.79.137.167:8080/firstProject/user/logout.do";

        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", cookie)
                .build();
        try {
            Response response = client.newCall(request).execute();

            JSONObject jb = new JSONObject(response.body().string());
            int code = jb.getInt("status");

            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int register(String name, String userName, String pass) {
        Log.e(TAG, "register: ---> name: " + name + " userName: " + userName);
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
    public static String upImage(File file) {
        //  防止大文件上传超时
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

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
            Log.e(TAG, "upImage: " + result);
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
    public static int createActivity(int id, String name, String time, String address, String note, String imgUrl) {
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
            Log.e(TAG, "createActivity " + code);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    //    修改活动
    public static int updateActivity(int id, String name, String time, String address, String note, String imgUrl) {
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
    public static int joinActivity(int id) {
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
    public static int exitActivity(int id) {
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
    public static List<EventBean> allActivity() {
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
            if (code == 0) {
                for (int i = 1; i <= pages; i++) {
                    events.addAll(allActivity(i));
                }
            }
            return events;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //    活动列表(内部方法)
    private static List<EventBean> allActivity(int page) {
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
                Log.e(TAG, "run: ja size -------------------------------->" + ja.length());

                parseEventArray(allEvents, ja);

                Log.e(TAG, "allActivity:  allEvents.size() = " + allEvents.size());
                return allEvents;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //    搜索活动
    public static int searchActivity(String args, boolean isInt) {
        String url_int = null;
        String url_str = null;

        Request.Builder builder = new Request.Builder();
        if (isInt) {
            url_int = String.format("http://120.79.137.167:8080/firstProject/activity/search.do?activityId=%s", args);
            builder.url(url_int);
            builder.header("Cookie", cookie);
        } else {
            url_str = String.format("http://120.79.137.167:8080/firstProject/activity/search.do?activityName=%s", args);
            builder.url(url_str);
            builder.header("Cookie", cookie);
            Log.e(TAG, "searchActivity: " + cookie);
        }

        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());

            Log.e(TAG, "searchActivity: " + jsonObject.toString());
            int code = jsonObject.getInt("status");

            JSONArray ja = jsonObject.getJSONObject("data").getJSONArray("list");
            mySearchList.clear();

            parseEventArray(mySearchList, ja);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    // 我发布的活动
    public static int myCreatedActivity() {
        String url = "http://120.79.137.167:8080/firstProject/user/my_activity_list.do";
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", cookie)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();

            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("status");
            JSONArray ja = jsonObject.getJSONObject("data").getJSONArray("list");

            myCreatedList.clear();
            parseEventArray(myCreatedList, ja);

            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    // 我参加的活动
    public static int myJoinedActivity() {
        String url = "http://120.79.137.167:8080/firstProject/user/activity_list.do";
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", cookie)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();

            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("status");
            JSONArray ja = jsonObject.getJSONObject("data").getJSONArray("list");

            myJoinedList.clear();
            parseEventArray(myJoinedList, ja);

            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    // 解析JSONArray的Object到数组
    private static void parseEventArray(List<EventBean> list, JSONArray ja) {
        for (int i = 0; i < ja.length(); i++) {
            EventBean eventBean = null;
            try {
                JSONObject jb = ja.getJSONObject(i);
                eventBean = new EventBean();
                eventBean.setId(jb.getInt("activityId"));
                eventBean.setName(jb.getString("activityName"));

                try {
                    eventBean.setStartTime(jb.getString("activityTime").substring(0, jb.getString("activityTime").indexOf("-")).trim());
                    eventBean.setEndTime(jb.getString("activityTime").substring(jb.getString("activityTime").indexOf("-") + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "allActivity: illegal time!");
                }

                eventBean.setWhere(jb.getString("address"));
                eventBean.setImgUri(jb.getString("activityImageurl").replaceAll("\\\\", ""));
                eventBean.setNote(jb.getString("introduction"));

//                eventBean.setOrganizerId(0);
                eventBean.setOrganizerName(jb.getString("createrName"));
                eventBean.setOrganizerHeader(jb.getString("createrImageurl").replaceAll("\\\\", ""));
                eventBean.setOrganizerNote(jb.getString("createrSignature"));
                eventBean.setOrganizerTel(jb.getString("createrPhone"));

                eventBean.setPersonCount(jb.getInt("peopleNumber"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(eventBean);
        }
    }

    public static int updateUserInfo(User user) {
        // int id = user.getId();
        String username = user.getUsername();
        // String Password = user.getPassword();
        String Name = user.getName();
        String Signature = user.getSignature();
        String Phone = user.getPhone();
        String Imageurl = user.getImageurl();

        String api = "http://120.79.137.167:8080/firstProject/user/update.do?";
        String url = String.format(api+"username=%s&name=%s&signature=%s&phone=%s&imageurl=%s", username, Name, Signature, Phone, Imageurl);

        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", cookie)
                .build();
        try {
            Response response = client.newCall(request).execute();

            JSONObject jb = new JSONObject(response.body().string());
            Log.e(TAG, "updateUserInfo: " + jb.toString());

            int code = jb.getInt("status");
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * ------------------------------------------>>>  分割线！
     */


    public static int ExchangeList() {
        Request request = new Request.Builder().url("http://120.79.137.167:8080/firstProject/exchange/list.do").header("Cookie", cookie).build();
        try {
            Response response = client.newCall(request).execute();
            //JSONObject jsonObject=new JSONObject(response.body().string());
            JSONObject jsonObject = new JSONObject(response.body().string());

            int code = jsonObject.getInt("status");

            JSONArray ja = jsonObject.getJSONObject("data").getJSONArray("list");
            //int code=jsonObject.getInt("status");
            //JSONArray ja = jsonObject.getJSONObject("data").getJSONArray("list");
            if (code == 0) {
//                exchangeitemList.clear();
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject object = ja.getJSONObject(i);
                    String imageUrl = null;
                    imageUrl = object.getString("imageUrl").replaceAll("\\\\", "");
                    if ("http://sdfsf".equals(imageUrl))
                        imageUrl = "http://120.79.137.167/752275cd-8122-403a-8881-581f9d40d756.png";
                    if (imageUrl == null || imageUrl.charAt(0) != 'h') {
                        imageUrl = "http://120.79.137.167/bd839423-3b9a-4452-972f-d45df92c10da.png";
                    }

                    String name = object.getString("name");
                    String time = object.getString("time");
                    String address = null;
                    try {
                        address = object.getString("address");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        address = "武汉科技大学";
                    }

                    String phone = null;
                    String expect = null;
                    try {
                        phone = object.getString("phone");
                        expect = object.getString("expect");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int price = 100;
                    //int price=object.getInt("price");
                    Random random = new Random();
                    int userID = random.nextInt();
                    Exchangeitem exchangeitem = new Exchangeitem(time, name, imageUrl, address, price, phone, String.valueOf(userID), expect);
                    exchangeitemList.add(exchangeitem);
                }
                return code;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    // 搜索交换
    public static int searchExchange(String args, boolean isInt) {
        String url_int = String.format("http://120.79.137.167:8080/firstProject/exchange/selectLike.do?name=%s", args);
        String url_str = String.format("http://120.79.137.167:8080/firstProject/exchange/selectLike.do?name=%s", args);

        Request.Builder builder = new Request.Builder();
        if (isInt) {
            builder.url(url_int);
        } else {
            builder.url(url_str);
        }
        Request request = builder.header("Cookie", cookie).build();
        try {
            Response response = client.newCall(request).execute();
            // JSONObject jsonObject = new JSONObject(response.body().string());
            String result = response.body().string();
            Log.e(Generator.TAG, "searchExchange: " + result);
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("status");

            //JSONArray ja = jsonObject.getJSONObject("data").getJSONArray("list");
            JSONArray jb = jsonObject.getJSONObject("data").getJSONArray("list");
            all_items.clear();
            if (jb == null) return 1;
            for (int i = 0; i < jb.length(); i++) {
                JSONObject jc = jb.getJSONObject(i);
                All_item all_item = new All_item();
                try {
                    all_item.setName(jc.getString("name"));
                    all_item.setAddress(jc.getString("address"));
                    all_item.setImageurl(jc.getString("imageUrl"));
                    all_item.setPrice(jc.getInt("price"));
                    all_item.setExpect(jc.getString("expect"));
                    all_item.setTime(jc.getString("time"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String imageUrl = all_item.getImageurl();
                if ("http://sdfsf".equals(imageUrl))
                    imageUrl = "http://120.79.137.167/752275cd-8122-403a-8881-581f9d40d756.png";
                if (imageUrl == null || imageUrl.charAt(0) != 'h') {
                    imageUrl = "http://120.79.137.167/bd839423-3b9a-4452-972f-d45df92c10da.png";
                }
                all_item.setImageurl(imageUrl);

                all_items.add(all_item);

            }
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int createExchange(String name, String time, String place, String expect, String price, String url1) {
        String url = String.format("http://120.79.137.167:8080/firstProject/exchange/upload_exchange.do?name=%s&time=%s&address=%s&expect=%s&price=%s&imageurl=%s", name, time, place, expect, price, url1);

        Request request = new Request.Builder().url(url).header("Cookie", cookie).build();

        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            Log.e(TAG, "createExchange: " + result );
            int code = new JSONObject(result).getInt("status");
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
