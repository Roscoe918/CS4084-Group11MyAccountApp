package com.briup.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.briup.controller.MessageListActivity;
import com.briup.controller.MonthMingxiActivity;
import com.briup.controller.TodayMingxiActivity;
import com.briup.controller.UpdateInfoActivity;
import com.briup.controller.WeekMingxiActivity;
import com.briup.controller.YearMingxiActivity;
import com.briup.model.MyDao;
import com.briup.model.SelectShouzhiDao;
import com.briup.myaccountapp.R;


public class MingxiFragment extends Fragment implements OnClickListener {

    private View view;
    private ImageButton touxiangBtn;
    private ImageView im_message;

    private TextView zongshouru;
    private TextView zongzhichu;
    private TextView jingshouru;

    private String userName;

    private TextView riliToday;

    private String Cdaystr;

    private TextView todayShouru;
    private TextView todayZhichu;
    private TextView weekShouru;
    private TextView weekZhichu;
    private TextView monthShouru;
    private TextView monthZhichu;
    private TextView yearShouru;
    private TextView yearZhichu;

    private RelativeLayout today_mingxi;
    private RelativeLayout week_mingxi;
    private RelativeLayout month_mingxi;
    private RelativeLayout year_mingxi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.mingxifragment, container, false);

        init();

        return view;
    }


    public void init() {
        touxiangBtn = (ImageButton) view.findViewById(R.id.mingxitouxiang);
        im_message = (ImageView) view.findViewById(R.id.im_message);

        zongshouru = (TextView) view.findViewById(R.id.zongjishouru);
        zongzhichu = (TextView) view.findViewById(R.id.zongjizhichu);
        jingshouru = (TextView) view.findViewById(R.id.zongjiyue);

        todayShouru = (TextView) view.findViewById(R.id.today_shouru);
        todayZhichu = (TextView) view.findViewById(R.id.today_zhichu);
        weekShouru = (TextView) view.findViewById(R.id.week_shouru);
        weekZhichu = (TextView) view.findViewById(R.id.week_zhichu);
        monthShouru = (TextView) view.findViewById(R.id.month_shouru);
        monthZhichu = (TextView) view.findViewById(R.id.month_zhichu);
        yearShouru = (TextView) view.findViewById(R.id.year_shouru);
        yearZhichu = (TextView) view.findViewById(R.id.year_zhichu);

        today_mingxi = (RelativeLayout) view.findViewById(R.id.today_mingxi);
        week_mingxi = (RelativeLayout) view.findViewById(R.id.week_mingxi);
        month_mingxi = (RelativeLayout) view.findViewById(R.id.month_mingxi);
        year_mingxi = (RelativeLayout) view.findViewById(R.id.year_mingxi);

        userName = MyDao.getNowUser(getActivity());

        initDate();

        queryAmount();
        queryToday();
        queryWeek();
        queryMonth();
        queryYear();

        touxiangBtn.setOnClickListener(this);
        im_message.setOnClickListener(this);
        today_mingxi.setOnClickListener(this);
        week_mingxi.setOnClickListener(this);
        month_mingxi.setOnClickListener(this);
        year_mingxi.setOnClickListener(this);
    }

    //Initialization date
    private void initDate() {
        String nowDate = MyDao.getCalendarStrs();
        String[] caledars = nowDate.split("[-]");
        Cdaystr = caledars[2];

        riliToday = (TextView) view.findViewById(R.id.rili_todayTv);

        riliToday.setText(Cdaystr);

    }

    //查询总收支
    public void queryAmount() {
        float[] zongshouzhi = SelectShouzhiDao.queryAmount(getActivity());
        zongshouru.setText(Float.toString(zongshouzhi[0]));
        zongzhichu.setText(Float.toString(zongshouzhi[1]));
        jingshouru.setText(Float.toString(zongshouzhi[0] - zongshouzhi[1]));
    }

    //查询今日总收支
    public void queryToday() {
        float[] todayShouzhi = SelectShouzhiDao.queryToday(view.getContext(), userName);
        todayShouru.setText("+" + todayShouzhi[0]);
        todayZhichu.setText("-" + todayShouzhi[1]);
    }

    //查询本周总收支
    public void queryWeek() {
        float[] weekShouzhi = SelectShouzhiDao.queryWeek(view.getContext(), userName);
        weekShouru.setText("+" + weekShouzhi[0]);
        weekZhichu.setText("-" + weekShouzhi[1]);
    }

    //查询本月总收支
    public void queryMonth() {
        float[] monthShouzhi = SelectShouzhiDao.queryMonth(view.getContext(), userName);
        monthShouru.setText("+" + monthShouzhi[0]);
        monthZhichu.setText("-" + monthShouzhi[1]);
    }

    //查询本年总收支
    public void queryYear() {
        float[] yearShouzhi = SelectShouzhiDao.queryYear(view.getContext(), userName);
        yearShouru.setText("+" + yearShouzhi[0]);
        yearZhichu.setText("-" + yearShouzhi[1]);
    }

    //事件监听
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            //左上角头像的事件监听
            case R.id.mingxitouxiang:
                Intent intent = new Intent(getActivity(), UpdateInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.im_message://右上角角的事件监听
                Intent intent0 = new Intent(getActivity(), MessageListActivity.class);
                startActivity(intent0);
                break;
            case R.id.today_mingxi:
                Intent intent2 = new Intent(getActivity(), TodayMingxiActivity.class);
                startActivity(intent2);
                break;
            case R.id.week_mingxi:
                Intent intent3 = new Intent(getActivity(), WeekMingxiActivity.class);
                startActivity(intent3);
                break;
            case R.id.month_mingxi:
                Intent intent4 = new Intent(getActivity(), MonthMingxiActivity.class);
                startActivity(intent4);
                break;
            case R.id.year_mingxi:
                Intent intent5 = new Intent(getActivity(), YearMingxiActivity.class);
                startActivity(intent5);
                break;
            default:
                break;
        }
    }


}
