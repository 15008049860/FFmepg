package com.example.ffmepg;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class MainActivity extends AppCompatActivity {
    Button bt;
    ImageView iv;
    EditText et;

    //创建FFmpegMediaMetadataRetriever对象
    FFmpegMediaMetadataRetriever mm=null;

    //文件路径String
    String path=null;

    //ggs
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt=(Button)findViewById(R.id.bt);
        et=(EditText)findViewById(R.id.et);
        iv=(ImageView)findViewById(R.id.iv);

        //初始化FFmpegMediaMetadataRetriever对象
        mm=new FFmpegMediaMetadataRetriever();

        //点击按钮获取网络视频文件缩略图（局域网）
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path=et.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                           /* String newPath= URLDecoder.decode(uri.toString(), "UTF-8");
                            Log.d("========",newPath);*/
                            //获取视频文件数据
                            mm.setDataSource(path);
                            Bitmap bitmap=mm.getFrameAtTime();
                            Message msg=new Message();
                            msg.arg1=0;
                            msg.obj=bitmap;
                            myHandler.sendMessage(msg);
                            Log.d("========","发送Message");
                        }catch (Exception e){
                        }
                        finally {
                            mm.release();
                        }
                    }
                }).start();
            }
        });
    }

    //更新主界面，显示缩略图
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    Bitmap bitmap=(Bitmap) msg.obj;
                    iv.setImageBitmap(bitmap);
                    break;
            }
            super.handleMessage(msg);
        }
    };


}
