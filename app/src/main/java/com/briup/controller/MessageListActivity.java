package com.briup.controller;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.briup.bean.Message;
import com.briup.model.MessageDao;
import com.briup.model.MyDao;
import com.briup.myaccountapp.R;
import com.briup.util.ActivityCollector;
import com.briup.util.MessageAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends Activity {

    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager rvManager;

    List<Message> allData = new ArrayList<>();

    Context mContext;
    MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_message);
        ActivityCollector.addActivity(this);
        mContext = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        rvManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(rvManager);
        mRecyclerView.addItemDecoration(new ItemDivider(this, R.drawable.catline, 10));
        mAdapter = new MessageAdapter(this, allData);
        mRecyclerView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        List<Message> messages = MessageDao.queryMessageList(mContext);
        allData.clear();
        allData.addAll(messages);
        mAdapter.notifyDataSetChanged();
    }

    public void submitMessage(View view) {

        new XPopup.Builder(this).asInputConfirm("Publish", null, "Please enter your consumption sharing content", new OnInputConfirmListener() {
            @Override
            public void onConfirm(String text) {
                if (!text.isEmpty()) {
                    String nowUser = MyDao.getNowUser(MessageListActivity.this);
                    MessageDao.insertMessage(mContext, text, nowUser);
                    getData();
                } else {
                    Toast.makeText(mContext, "Please enter your consumption sharing content first", Toast.LENGTH_SHORT).show();
                }
            }
        }).show();


    }



    public void updateBack(View view) {
        finish();
    }


    class ItemDivider extends RecyclerView.ItemDecoration {

        private Drawable drawable;

        private int width;

        public ItemDivider(Context context, int resId, int d) {
            //在这里我们传入作为Divider的Drawable对象
            drawable = context.getResources().getDrawable(resId);
            width = d;
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft() + width;
            final int right = parent.getWidth() - parent.getPaddingRight() - width * 2;

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();

                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + drawable.getIntrinsicHeight();
                drawable.setBounds(left, top, right, bottom);
                drawable.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int position, RecyclerView parent) {
            outRect.set(0, 0, 0, drawable.getIntrinsicWidth());
        }
    }

}
