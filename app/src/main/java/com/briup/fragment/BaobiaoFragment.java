package com.briup.fragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.text.StaticLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.briup.bean.ShouzhiItemDetails;
import com.briup.controller.UpdateInfoActivity;
import com.briup.model.MyDao;
import com.briup.model.QueryBaobiaoDao;
import com.briup.model.SelectShouzhiDao;
import com.briup.myaccountapp.R;
import com.briup.util.MyShouzhiItemAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.ValueFormatter;

public class BaobiaoFragment extends Fragment {

    private View view;
    private ImageButton baobiaoBtn;

    private static Switch switchShouzhi;
    public static PieChart pieChart;
    public static PieData pieData;
    private static PieDataSet pieDataSet;
    public static RecyclerView recyclerView;
    public static RecyclerView.LayoutManager rvManager;

    //No record picture in report
    private ImageView wujilu;

    //Record the cake
    public int tempPosition;

    private List<String> xValues;
    private List<Entry> yValues;

    private List<Integer> colors = new ArrayList<Integer>();
    //The color of the actual pie chart
    private static List<Integer> pieChartColors = new ArrayList<Integer>();
    private List<ShouzhiItemDetails> shouzhiItemsList;
    private MyShouzhiItemAdapter adapter;
    private static float[] zhichuBili;
    private static float[] shouruBili;
    private int[] shourutypeImages =
            {R.drawable.shourugongzi, R.drawable.shourushenghuofei,
                    R.drawable.hongbao, R.drawable.shouruwaikuai,
                    R.drawable.shourujieru, R.drawable.shourujiangjin,
                    R.drawable.shouruhuankuan, R.drawable.shourubaoxiao,
                    R.drawable.shouruxianjin, R.drawable.qita};
    private int[] zhichutypeImages =
            {R.drawable.zhichufangzu, R.drawable.zhichucanyin,
                    R.drawable.zhichujiaotong, R.drawable.zhichuyifu,
                    R.drawable.zhichushenghuo, R.drawable.zhichuhuafei,
                    R.drawable.hongbao, R.drawable.zhichuhufu,
                    R.drawable.zhichuhuanqian, R.drawable.qita};
    private String[] shourutypeNames = {"wage", "living expenses", "transfer", "part-time",
            "borrow", "bonus", "repayment", "reimbursement", "cash", "other"};
    private String[] zhichutypeNames = {"rent", "food", "transportation", "clothing",
            "Daily necessities", "Phone bill", "transfer", "skin care", "refund", "other"};
    private float[] zongshouzhi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.baobiaofragment, container, false);

        init();

        return view;
    }

    private void init() {
        //Pie chart color
        colors.add(Color.rgb(136, 0, 204));
        colors.add(Color.rgb(255, 0, 0));
        colors.add(Color.rgb(0, 153, 204));
        colors.add(Color.rgb(255, 255, 0));
        colors.add(Color.rgb(0, 204, 0));
        colors.add(Color.rgb(0, 255, 255));
        colors.add(Color.rgb(255, 195, 0));
        colors.add(Color.rgb(255, 59, 198));
        colors.add(Color.rgb(159, 255, 68));
        colors.add(Color.rgb(204, 153, 255));

        baobiaoBtn = (ImageButton) view.findViewById(R.id.baobiaotouxiang);
        baobiaoBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), UpdateInfoActivity.class);
                startActivity(intent);
            }
        });

        switchShouzhi = (Switch) view.findViewById(R.id.shouzhi_Switch);
        pieChart = (PieChart) view.findViewById(R.id.pie_shouzhi);
        recyclerView = (RecyclerView) view.findViewById(R.id.lv_shouzhiitem);
        wujilu = (ImageView) view.findViewById(R.id.baobiaowujilu);

        recyclerView.setHasFixedSize(true);
        rvManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(rvManager);
        recyclerView.addItemDecoration(new ItemDivider(view.getContext(), R.drawable.catline, 10));

        zongshouzhi = SelectShouzhiDao.queryAmount(getActivity());
        final float zongshouru = zongshouzhi[0];
        final float zongzhichu = zongshouzhi[1];

        //Display spending by default
        pieData = getZhichuPieData();
        shouzhiItemsList = getZhichuItems();
        if (shouzhiItemsList.size() != 0) {
            wujilu.setVisibility(View.GONE);
            showChart(pieChart, pieData, "Total Expenditure\n" + zongzhichu);
        } else {
            wujilu.setVisibility(View.VISIBLE);
        }
        pieDataSet.setColors(pieChartColors);
        adapter = new MyShouzhiItemAdapter(view.getContext(), shouzhiItemsList);
        recyclerView.setAdapter(adapter);

        switchShouzhi.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    pieData = getShouruPieData();
                    pieDataSet.setColors(pieChartColors);
                    pieChart.refreshDrawableState();
                    shouzhiItemsList = getShouruItems();
                    if (shouzhiItemsList.size() != 0) {
                        wujilu.setVisibility(View.GONE);
                        showChart(pieChart, pieData, "Total Income\n" + zongshouru);
                    } else {
                        wujilu.setVisibility(View.VISIBLE);
                    }
                    adapter = new MyShouzhiItemAdapter(view.getContext(), shouzhiItemsList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.refreshDrawableState();
                } else {
                    pieData = getZhichuPieData();
                    pieDataSet.setColors(pieChartColors);
                    pieChart.refreshDrawableState();
                    shouzhiItemsList = getZhichuItems();
                    if (shouzhiItemsList.size() != 0) {
                        wujilu.setVisibility(View.GONE);
                        showChart(pieChart, pieData, "Total Expenditure\n" + zongzhichu);
                    } else {
                        wujilu.setVisibility(View.VISIBLE);
                    }
                    adapter = new MyShouzhiItemAdapter(view.getContext(), shouzhiItemsList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.refreshDrawableState();
                }
            }
        });
    }

    private void showChart(final PieChart pieChart, PieData pieData, String centerText) {

        pieChart.setHoleColorTransparent(true);
        //Set radius
        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleRadius(54f);
        //pieChart.setHoleRadius(0);

        /*		pieChart.setDescription("测试饼状图");*/
        //饼状图中间可以添加文字
        pieChart.setDrawCenterText(true);
        //饼图中间设置为空心
        pieChart.setDrawHoleEnabled(true);
        //设置初始旋转角度
        pieChart.setRotationAngle(90);
        //可以手动旋转
        pieChart.setRotationEnabled(true);
        //显示成百分比
        pieChart.setUsePercentValues(true);

        /*pieChart.setDescriptionTextSize(15);*/
        //Set data size in pie chart
        pieData.setValueTextSize(10);
        pieChart.setDescription("");

        //The text in the middle of the pie chart
        pieChart.setCenterText(centerText);
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextColor(Color.rgb(0, 102, 102));
        //set data
        pieChart.setData(pieData);

        //Set scale
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        //legend.setPosition(LegendPosition.BELOW_CHART_CENTER);
        //legend.setForm(LegendForm.LINE);
        //legend.setXEntrySpace(7f);
        //legend.setYEntrySpace(5f);


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry arg0, int arg1, Highlight arg2) {
                recyclerView.scrollToPosition(arg2.getXIndex());
                Log.d("Staff", "position" + arg2.getXIndex());
                tempPosition = arg2.getXIndex();
            }

            @Override
            public void onNothingSelected() {
                pieChart.highlightValue(tempPosition, 0);
            }
        });

        //set Animate
        pieChart.animateXY(1000, 1000);
    }

    //Get data on spending
    private PieData getZhichuPieData() {
        //Used to represent the content on each pie
        xValues = new ArrayList<String>();
        //Used to encapsulate the actual data of each cake
        yValues = new ArrayList<Entry>();

        zhichuBili = QueryBaobiaoDao.queryZhichuBili(getActivity());

        int xIndex = 0;
        for (int i = 0; i < zhichuBili.length; i++) {
            if (zhichuBili[i] > 0) {
                xValues.add(zhichutypeNames[i]);
                yValues.add(new Entry(zhichuBili[i], xIndex));
                xIndex++;
            } else {
                //If the ratio is equal to 0, it is not displayed
            }
        }

        //y-axis collection
        pieDataSet = new PieDataSet(yValues, "Expenditure Item");
        //Set the distance between cake pieces
        pieDataSet.setSliceSpace(0f);
        //Set the color of the pie chart
        //pieDataSet.setColors(colors);
        //Set display percentage
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float baifenbi) {
                BigDecimal b = new BigDecimal(baifenbi);
                float f = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                String str = "";
                if (f >= 0) {
                    str = f + "%";
                }
                return str;
            }
        });

        DisplayMetrics metrics = view.getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px);

        pieData = new PieData(xValues, pieDataSet);
        return pieData;
    }

    //Get income statistics
    private PieData getShouruPieData() {

        xValues = new ArrayList<String>();
        yValues = new ArrayList<Entry>();

        shouruBili = QueryBaobiaoDao.queryShouruBili(getActivity());

        int xIndex = 0;
        for (int i = 0; i < shouruBili.length; i++) {
            if (shouruBili[i] > 0) {
                xValues.add(shourutypeNames[i]);
                yValues.add(new Entry(shouruBili[i], xIndex));
                xIndex++;
            } else {
                //If the ratio is equal to 0, it is not displayed
            }
        }

        //y-axis collection
        pieDataSet = new PieDataSet(yValues, "Income Item");
        //Set the distance between cake pieces
        pieDataSet.setSliceSpace(0f);
        //Set the color of the pie chart
        //pieDataSet.setColors(colors);
        //Set display percentage
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float baifenbi) {
                BigDecimal b = new BigDecimal(baifenbi);
                float f = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                String str = "";
                if (f >= 0) {
                    str = f + "%";
                }
                return str;
            }
        });

        DisplayMetrics metrics = view.getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);

        pieDataSet.setSelectionShift(px);

        pieData = new PieData(xValues, pieDataSet);
        return pieData;
    }

    //Get the content of each expenditure item
    private List<ShouzhiItemDetails> getShouruItems() {
        float[] shouruMoneys = QueryBaobiaoDao.queryShouruItemMoney(getActivity());
        List<ShouzhiItemDetails> list = new ArrayList<ShouzhiItemDetails>();
        pieChartColors.clear();
        for (int i = 0; i < shouruMoneys.length; i++) {
            if (shouruMoneys[i] > 0) {
                ShouzhiItemDetails itemDetails = new ShouzhiItemDetails();
                itemDetails.setItemColor(colors.get(i));
                itemDetails.setItemImage(shourutypeImages[i]);
                itemDetails.setType(shourutypeNames[i]);
                BigDecimal b = new BigDecimal(shouruBili[i] * 100);
                float f = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                itemDetails.setBaifenbi(f + "%");
                itemDetails.setMoney(shouruMoneys[i]);
                list.add(itemDetails);
                pieChartColors.add(itemDetails.getItemColor());
            }
        }
        return list;
    }

    //Get the content of each expenditure item
    private List<ShouzhiItemDetails> getZhichuItems() {
        float[] zhichuMoneys = QueryBaobiaoDao.queryZhichuItemMoney(getActivity());
        List<ShouzhiItemDetails> list = new ArrayList<ShouzhiItemDetails>();
        pieChartColors.clear();
        for (int i = 0; i < zhichuMoneys.length; i++) {
            if (zhichuMoneys[i] > 0) {
                ShouzhiItemDetails itemDetails = new ShouzhiItemDetails();
                itemDetails.setItemColor(colors.get(i));
                pieChartColors.add(itemDetails.getItemColor());
                itemDetails.setItemImage(zhichutypeImages[i]);
                itemDetails.setType(zhichutypeNames[i]);
                BigDecimal b = new BigDecimal(zhichuBili[i] * 100);
                float f = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                itemDetails.setBaifenbi(f + "%");
                itemDetails.setMoney(zhichuMoneys[i]);
                list.add(itemDetails);
            }

        }
        return list;
    }

    //分割线
    class ItemDivider extends ItemDecoration {

        private Drawable drawable;

        private int width;

        public ItemDivider(Context context, int resId, int d) {
            //Here we pass in the Drawable object as Divider
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
                //The following calculation is mainly used to determine the drawing position
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
