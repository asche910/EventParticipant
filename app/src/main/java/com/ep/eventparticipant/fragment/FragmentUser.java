package com.ep.eventparticipant.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.Constraints;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ep.eventparticipant.activity.EventNewActivity;
import com.ep.eventparticipant.activity.EventResultActivity;
import com.ep.eventparticipant.activity.ExchangeInformation;
import com.ep.eventparticipant.activity.LoginActivity;
import com.ep.eventparticipant.activity.MainActivity;
import com.ep.eventparticipant.object.User;
import com.ep.eventparticipant.other.AsHttpUtils;
import com.ep.eventparticipant.other.OkHttp;
import com.ep.eventparticipant.activity.Personal_information;
import com.ep.eventparticipant.R;
import android.widget.Button;
import android.widget.Toast;

import com.ep.eventparticipant.activity.exchangein;
import com.ep.eventparticipant.activity.exchangeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

import static com.ep.eventparticipant.fragment.FragmentHome.JOINED_LIST;
import static com.ep.eventparticipant.other.AsHttpUtils.myExchangeOut;
import static org.litepal.LitePalBase.TAG;

public class FragmentUser extends Fragment {

    private de.hdodenhof.circleimageview.CircleImageView touxiang;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private TextView Name;
    private TextView Signature;
    private TextView Phone;
    private TextView logout;
    private Button exchange_out;
    private Button exchange_in;
    private Button issue;
    private Button participate;
    private Button information;
//    private RelativeLayout exchange_in_btn;
    private File tempFile;
    private Uri imageUri;

    public static User curUser = new User();

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
//        User curUser = new User();
        View view = inflater.from(container.getContext()).inflate(R.layout.fragment_user, container, false);
        touxiang = (de.hdodenhof.circleimageview.CircleImageView)view.findViewById(R.id.touxiang);
        Name = (TextView)view.findViewById(R.id.Name);
        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Personal_information.class);
                startActivity(intent);
                // getBitmapFromSharedPreferences();
            }
        });
        //个性签名
        Signature = (TextView)view.findViewById(R.id.Signature);
        Signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Personal_information.class);
                startActivity(intent);
//                getBitmapFromSharedPreferences();
            }
        });
        //手机号
        Phone = (TextView)view.findViewById(R.id.Phone);
        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Personal_information.class);
                startActivity(intent);
//                getBitmapFromSharedPreferences();
            }
        });

        logout = view.findViewById(R.id.user_logout);
        logout.setOnClickListener(new View.OnClickListener(){
            int code;
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        code = AsHttpUtils.logout();
                    }
                });
                thread.start();

                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (code == 0){
                    Toast.makeText(getActivity(), "注销成功！", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                    getActivity().finish();
                }
            }
        });

        // 头像加载
        try {
            if(curUser.getImageurl() == null || curUser.getImageurl().charAt(0) != 'h') {
                curUser.setImageurl("http://120.79.137.167/d2a572bb-d968-453b-bcd4-e7837472aa46.png");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AsHttpUtils.updateUserInfo(curUser);
                    }
                }).start();
            }

            Log.e(TAG, "onClick: imgUrl: " + curUser.getImageurl() );


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "头像加载失败！", Toast.LENGTH_SHORT).show();
        }

        //我的换出
        exchange_out = (Button)view.findViewById(R.id.exchange_out);
        exchange_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        myExchangeOut();
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getActivity(), exchangeout.class);
                startActivity(intent);
            }
        });
        //我的换入
        exchange_in = (Button)view.findViewById(R.id.exchange_in);
        exchange_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), exchangein.class);
                startActivity(intent);
            }
        });
        //我的发布
        issue = (Button)view.findViewById(R.id.issue);
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventNewActivity.class);
                startActivity(intent);
            }
        });
        //交换信息
        information = (Button)view.findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExchangeInformation.class);
                startActivity(intent);
            }
        });
        //我参与的
        participate = (Button)view.findViewById(R.id.participate);
        participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thd = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int code = AsHttpUtils.myJoinedActivity();
                        Log.e(Constraints.TAG, "myJoinedActivity: return code: " + code);
                    }
                });
                thd.start();
                try {
                    thd.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent_ = new Intent(getContext(), EventResultActivity.class);
                intent_.putExtra("ListType", JOINED_LIST);
                startActivity(intent_);
            }
        });
//        getBitmapFromSharedPreferences();
        initView();
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Name.setText(curUser.getName());
        Signature.setText(curUser.getSignature());
        Phone.setText(curUser.getPhone());

        Glide.with(getActivity())
                .load(curUser.getImageurl())
                .into(touxiang);
    }

    private boolean hasSdcard() {
        //判断ＳＤ卡是否是安装好的　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private void initView() {
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Personal_information.class);
                startActivity(intent);
//                getBitmapFromSharedPreferences();
            }
        });
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        touxiang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Personal_information.class);
//                startActivity(intent);
//            }
//        });
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
            }
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                /**
                 * 获得图片
                 */
                touxiang.setImageBitmap(bitmap);
                //保存到SharedPreferences
                saveBitmapToSharedPreferences(bitmap);
            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //保存图片到SharedPreferences
    private void saveBitmapToSharedPreferences(Bitmap bitmap) {
        // Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        @SuppressLint({"NewApi", "LocalSuppress"}) String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步:将String保持至SharedPreferences
        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences("testSP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", imageString);
        editor.commit();
        //上传头像
        setImgByStr(imageString,"");
    }

    public  void setImgByStr(String imgStr, String imgName) {
        String url = "http://appserver。。。。。。。";
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "参数值");//
        params.put("data", imgStr);
        OkHttp.postAsync(url, params, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.i("上传失败", "失败" + request.toString() + e.toString());
            }
            @Override
            public void requestSuccess(String result) throws Exception {
                Log.i("上传成功", result);
            }
        });
    }

    private void getBitmapFromSharedPreferences(){
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("testSP", Context.MODE_PRIVATE);
        //第一步:取出字符串形式的Bitmap
        String imageString=sharedPreferences.getString("image", "");
        //第二步:利用Base64将字符串转换为ByteArrayInputStream
        @SuppressLint({"NewApi", "LocalSuppress"}) byte[] byteArray= Base64.decode(imageString, Base64.DEFAULT);
        if(byteArray.length==0){
            touxiang.setImageResource(R.drawable.touxiang);
        }else{
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
            //第三步:利用ByteArrayInputStream生成Bitmap
            Bitmap bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
            touxiang.setImageBitmap(bitmap);
        }
    }
}
