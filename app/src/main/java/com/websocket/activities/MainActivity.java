package com.websocket.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.websocket.R;
import com.websocket.adapter.WebSocketChatAdapter;
import com.websocket.bean.WebSocketChatBean;
import com.websocket.broadcast.WebSocketBroadcastReceiver;
import com.websocket.service.WebSocketService;
import com.websocket.utils.Constants;
import com.websocket.view.RecycleViewDivider;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG="MainActivity";

    private ArrayList<WebSocketChatBean> dataList=new ArrayList<>();

    private RecyclerView mRecyclerView=null;

    private EditText mEditText=null;

    private TextView mTitle =null;

    private Button mSend=null;

    private LinearLayoutManager mLinearLayoutManager=null;

    private WebSocketChatAdapter mWebSocketChatAdapter=null;

    private WebSocketBroadcastReceiver broadcastReceiver=null;  //广播



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==200){
                String content=msg.obj.toString();
                try {
                    JSONObject obj=new JSONObject(content);
                    String type=obj.optString("type");
                    if(type.equals("ping")){
                        WebSocketChatBean bean=new WebSocketChatBean();
                        bean.setContent(type);
                        bean.setType("");
                        dataList.add(bean);
                    }
                    mWebSocketChatAdapter.notifyDataSetChanged();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();

        initAdapter();

        initBinderService();

        initRegisterReceiver();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title:

                break;

            case R.id.send:
                if(mEditText.getText().length()!=0){
                    WebSocketChatBean bean=new WebSocketChatBean();
                    bean.setContent(mEditText.getText().toString().trim());
                    bean.setName("夏学艺");
                    bean.setType("type");
                    mWebSocketChatAdapter.addItemView(bean);
                    mEditText.getText().clear();
                }
                break;
        }
    }



    /**
     * 初始化组件
     */
    private void initWidget(){
        mRecyclerView=(RecyclerView)super.findViewById(R.id.recycler_view);
        mEditText=(EditText)super.findViewById(R.id.edit_query);
        mTitle =(TextView)super.findViewById(R.id.title) ;
        mSend =(Button) super.findViewById(R.id.send) ;
        mLinearLayoutManager=new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());   //设置item动画
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 8, ContextCompat.getColor(this, R.color.divide_gray_color)));
        mTitle.setOnClickListener(this);
        mSend.setOnClickListener(this);
    }


    /**
     * 初始化适配器
     */
    private void initAdapter(){
        mWebSocketChatAdapter=new WebSocketChatAdapter(this,dataList);
        mRecyclerView.setAdapter(mWebSocketChatAdapter);
    }


    /**
     * 开始服务
     */
    private void initBinderService(){
        Intent intent=new Intent();
        intent.setClass(this, WebSocketService.class);
        startService(intent);
    }

    /**
     * 注册广播
     */
    private void initRegisterReceiver(){
        if(broadcastReceiver==null){
            broadcastReceiver=new WebSocketBroadcastReceiver(handler);
        }
        IntentFilter filter=new IntentFilter();
        filter.addAction(Constants.WEB_SOCKET_BROADCAST_ACTION);
        registerReceiver(broadcastReceiver,filter);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
