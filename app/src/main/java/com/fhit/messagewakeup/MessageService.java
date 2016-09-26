package com.fhit.messagewakeup;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.fhit.pushmessage.MessageClient;
import com.fhit.pushmessage.util.LogUtil;

public class MessageService extends Service implements MessageClient.HandleReceivePushMsgListener{
    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("MessageService onStartCommand()");
        try{
            MessageClient.getInstance().start(this,MainActivity.SenderUid,"211.157.160.5",34567,this);
        }catch (Exception e){
            LogUtil.e("MessageService onStartCommand() 异常："+e.getMessage());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.d("MessageService onDestroy()");
        super.onDestroy();
        MessageClient.getInstance().stop();
    }

    @Override
    public void handleReceiveMsg(String s) {
        //{"Title":"文字","Content":"木木木木木木诺","MessageType":1,"SenderUid":"11800105015",
        // "SenderName":"刘宝兴","ReceiverUid":"11800105016","ReceiverName":"小白","SendTime":"2016-09-22 15:02:51"}
        LogUtil.d("MessageService handleReceiveMsg():"+s);
    }
}
