package com.example.delitto.myapplication.Tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.example.delitto.myapplication.Activity.ChatActivity;
import com.example.delitto.myapplication.Bean.MessageListData;
import com.example.delitto.myapplication.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Delitto on 2016/12/6.
 */

public class ECTools {
    public static List<EMMessage> messages;
    public static ArrayList<MessageListData> ourMessageList;
    public static int loginFlag=2;
    public static boolean signinFlag=true;
    public static EMCallBack emCallBack = new EMCallBack() {
        @Override
        public void onSuccess() {
            EMClient.getInstance().chatManager().loadAllConversations();
            EMClient.getInstance().groupManager().loadAllGroups();
            loginFlag = 1;
        }
        @Override
        public void onError(int i, String s) {
            loginFlag = 0;
        }
        @Override
        public void onProgress(int i, String s) {
            loginFlag = 2;
        }
    };
    public static EMMessageListener msgListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
          //  Messagerefresh();
        }
        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }
        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            //收到已读回执
        }
        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            //收到已送达回执
        }
        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };
    public static int refreshCallBack(){
        return loginFlag;
    }
    public static boolean refreshSignin(){
        return signinFlag;
    }
    //私信服务器登录
    //异步方法
    public static void ECLogin(String userId,String userPassWord) {

        //本身是异步方法
        EMClient.getInstance().login(userId, userPassWord, emCallBack);

    //    return loginFlag;
    }
    //私信服务器注册
    //同步方法，自己加线程
    public static void ECSignin(final String userId, final String userPassWord){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    EMClient.getInstance().createAccount(userId, userPassWord);

                }catch (com.hyphenate.exceptions.HyphenateException e){
                    signinFlag = false;
                    e.printStackTrace();
                    Log.e("Delitto",e.getErrorCode()+" "+e.getMessage());

                }
            }
        }).start();

    }
//私信服务器退出登陆
    public static void ECLogout(){
        EMClient.getInstance().logout(true);
    }
//跳转聊天窗口
    public static Intent GotoChatActivity(Activity activity, String userId){
        Intent intent = new Intent(activity,ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID,userId);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);

        return intent;

    }
    public static void addMSGListener() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }
    public static void removeMSGListener(){
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
    //返回类的静态变量List<EMMessage>
    public static List<EMMessage> getMessages(){
        return ECTools.messages;
    }
    //把SDK所识别的消息类型转换成我们识别的消息类型
    public static MessageListData MessageTransform(EMMessage msg){
        String ourContent=getContent(msg);
        String ourTime = getTime(msg);

        MessageListData ourMessage = new MessageListData(R.mipmap.logo,msg.getFrom(),ourContent,ourTime);
        return ourMessage;
    }
    //把SDK所识别的消息类型List转换成我们识别的消息类型ArrayList
    public static ArrayList<MessageListData> MessageListTransform(List<EMMessage> msg){
        ArrayList<MessageListData> ourMSG = new ArrayList<MessageListData>();
        for(int i=0;i<msg.size();i++){
            ourMSG.add(MessageTransform(msg.get(i)));
        }
        return ourMSG;
    }
    //获取消息内容
    public static String getContent(EMMessage msg){
        String ourContent;
        String content = ((EMTextMessageBody)msg.getBody()).getMessage();
        if(content.length()>30){
            ourContent = content.substring(0,30);
        }else{
            ourContent = content;
        }
        return ourContent;
    }
    //获取消息时间
    public static String getTime(EMMessage msg){
        String ourTime;
        long time = msg.localTime();
        long localtime = System.currentTimeMillis();
        if(localtime-time<(60*1000)){
            ourTime = new String("刚刚");
        }else if(localtime-time<(300*1000)){
            ourTime = new String("几分钟前");
        }else{
            Date date = new Date(time);
            SimpleDateFormat myFmt=new SimpleDateFormat("HH:mm");
            ourTime = new String(myFmt.format(date));
        }
        return ourTime;
    }
    //得到我们能识别的消息类型ArrayList
    public static ArrayList<MessageListData> getOurMessageList(){
        return ourMessageList;
    }
    //得到我们能识别的消息类型ArrayList的长度
    public static int getOurMessageListLength(){
        return ourMessageList.size();
    }
    //获取所有会话消息
    public static Map<String,EMConversation> getConversation(){
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        return conversations;
    }
    //从所有会话消息中获取所有消息
    //返回到静态ArrayList<MessageDataList>
    public static void getMessagesFromConversations(final Map<String,EMConversation> m) {

        Set<String> keySet = m.keySet();
        final Iterator<String> iterable = keySet.iterator();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (iterable.hasNext()) {
                            String key = iterable.next();
                            EMConversation emConversation = m.get(key);
                            List<EMMessage> message = emConversation.getAllMessages();

                            ourMessageList.add(MessageTransform(message.get(message.size()-1)));
                        }
                    }
                }).start();
    }
    //从会话消息中获取消息
    public static  MessageListData getMessageFromConversation(EMConversation m){
        return MessageTransform(m.getLastMessage());
    }
    //获取本地会话列表
    public static  ArrayList<MessageListData> loadConversationList(){
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        ArrayList<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<MessageListData> list = new ArrayList<MessageListData>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(getMessageFromConversation(sortItem.second));

        }
        return list;
    }
    //
    //
    public static void sortConversationByLastChatTime(ArrayList<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }
    //
    /*
    public static void Messagerefresh(View v){

    }*/
}
