package com.fhit.messagewakeup;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fhit.messagewakeup.util.SelectPhotoUtil;
import com.fhit.messagewakeup.util.UriUtil;
import com.fhit.pushmessage.MessageClient;
import com.fhit.pushmessage.entity.MessageRequest;
import com.fhit.pushmessage.entity.MessageType;
import com.fhit.pushmessage.util.LogUtil;

import org.xutils.common.Callback;

import java.io.File;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public final static String SenderUid = "11800105016";
    public final static String SenderName = "小白";
    public final static String ReceiverUid = "11800105015";
    public final static String ReceiverName = "刘兴";
    String msgUrl   = "https://anhui.ucallapi.ucallclub.com:9023/api/v7/Message";
    String uploadUrl = "https://anhui.ucallapi.ucallclub.com:9023/api/v7/uploadmedia";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.setTag("lbx");
        LogUtil.setIsDebug(true);
        setContentView(R.layout.activity_main);
        this.startService(new Intent(this,MessageService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.stopService(new Intent(this,MessageService.class));
    }

    public void startService(View v){
        this.startService(new Intent(this,MessageService.class));
    }
    public void sendText(View v){
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.MessageType = MessageType.Text;
        messageRequest.Title = "文字";
        messageRequest.Content = "发送的文字内容:"+ UUID.randomUUID().toString();
        messageRequest.SenderUid = SenderUid;
        messageRequest.SenderName = SenderName;
        messageRequest.ReceiverUid = ReceiverUid;
        messageRequest.ReceiverName = ReceiverName;
        MessageClient.getInstance().sendMessage(msgUrl, messageRequest.toString(), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                LogUtil.d("sendText onSuccess:"+s);
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                LogUtil.e("sendText onError:",throwable);
                Toast.makeText(MainActivity.this,throwable.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    public void sendMedia(View v){
        SelectPhotoUtil.openLocalImage(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == SelectPhotoUtil.GET_IMAGE_BY_CAMERA){
                if(SelectPhotoUtil.imageUriFromCamera != null){
                    String photoPath = UriUtil.getImageAbsolutePath(this,SelectPhotoUtil.imageUriFromCamera);
                    handleImageSend(photoPath);
                }else{
                    LogUtil.e("===========onActivityResult[1]=============");
                }
            }else if(requestCode == SelectPhotoUtil.GET_IMAGE_FROM_PHONE){
                if(data.getData() != null){
                    String photoPath = UriUtil.getImageAbsolutePath(this,data.getData());
                    handleImageSend(photoPath);
                }else{
                    LogUtil.e("===========onActivityResult[2]=============");
                }
            }
        }else{
            SelectPhotoUtil.clearData();
        }
    }
    private void handleImageSend(String imagePath){
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.MessageType = MessageType.Image;
        messageRequest.Title = "图片";
        messageRequest.Content = imagePath;
        messageRequest.SenderUid = SenderUid;
        messageRequest.SenderName = SenderName;
        messageRequest.ReceiverUid = ReceiverUid;
        messageRequest.ReceiverName = ReceiverName;
        MessageClient.getInstance().sendMessage(msgUrl, messageRequest.toString(), uploadUrl, new File(imagePath), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                LogUtil.d("sendText onSuccess:"+s);
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                LogUtil.e("sendText onError:",throwable);
                Toast.makeText(MainActivity.this,throwable.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
