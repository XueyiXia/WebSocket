package com.websocket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.websocket.R;
import com.websocket.bean.WebSocketChatBean;

import java.util.ArrayList;

/**
 * @author: xiaxueyi
 * @date: 2017-02-23
 * @time: 10:35
 * @说明: 聊天适配器
 */
public class WebSocketChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;

    private ArrayList<WebSocketChatBean> dataList=new ArrayList<>();

    private View itemView = null;     //存储每一项的视图

    public WebSocketChatAdapter(Context context,ArrayList<WebSocketChatBean> dataList) {
        this.mContext=context;
        this.dataList=dataList;
    }



    @Override
    public int getItemViewType(int position) {
        WebSocketChatBean bean= dataList.get(position);
        if(bean!=null&&bean.getType().equals("type")){
            return 0;
        }else {
            return 1;
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        if(position==0){
            itemView= LayoutInflater.from(mContext).inflate(R.layout.activity_me_item,null);
            return new ViewHolder(itemView);
        }else {
            itemView= LayoutInflater.from(mContext).inflate(R.layout.activity_other_item,null);
            return new OtherViewHolder(itemView);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        WebSocketChatBean bean=dataList.get(position);
        if(viewHolder instanceof ViewHolder){
            ((ViewHolder) viewHolder).avatar.setBackgroundResource(R.mipmap.ic_launcher);
            ((ViewHolder) viewHolder).content.setText(bean.getContent());
        }else if(viewHolder instanceof OtherViewHolder){
            ((OtherViewHolder) viewHolder).avatar.setBackgroundResource(R.mipmap.ic_launcher);
            ((OtherViewHolder) viewHolder).name.setText(bean.getName());
            ((OtherViewHolder) viewHolder).content.setText(bean.getContent());
        }
    }



    public void addItemView(WebSocketChatBean bean){
        if(bean!=null){
            dataList.add(bean);
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public int position;

        private ImageView avatar;

        private TextView content;

        public ViewHolder(View itemView) {
            super(itemView);

            avatar=(ImageView)itemView.findViewById(R.id.avatar);
            content=(TextView)itemView.findViewById(R.id.content);
        }

        @Override
        public void onClick(View view) {

        }
    }


    public class OtherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public int position;

        private ImageView avatar;

        private TextView name;

        private TextView content;

        public OtherViewHolder(View itemView) {
            super(itemView);

            avatar=(ImageView)itemView.findViewById(R.id.avatar);
            content=(TextView)itemView.findViewById(R.id.content);
            name=(TextView)itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View view) {

        }
    }
}

