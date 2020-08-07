package com.smartonet.project.ui.main.fragement.chart;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.smartonet.project.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ChartFragment extends Fragment {
    private View view;//fragment主界面

    //加载哪个界面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer_layout_chart, container, false);
        return view;
    }

    //初始化界面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        lineChart();//线型图
        barChart();//条形图
        pieChart();//饼图
    }

    //折线图
    private LineChart mLineChar;
    private void lineChart() {
        mLineChar = view.findViewById(R.id.mLineChar);
        //设置手势滑动事件
        mLineChar.setOnChartGestureListener(new OnChartGestureListener(){
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }
            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }
            @Override
            public void onChartLongPressed(MotionEvent me) {

            }
            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }
            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }
            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }
            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }
            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
        //设置数值选择监听
        mLineChar.setOnChartValueSelectedListener(new OnChartValueSelectedListener(){
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }
            @Override
            public void onNothingSelected() {

            }
        });
        //后台绘制
        mLineChar.setDrawGridBackground(false);
        //设置描述文本
        mLineChar.getDescription().setEnabled(false);
        //设置支持触控手势
        mLineChar.setTouchEnabled(true);
        //设置缩放
        mLineChar.setDragEnabled(true);
        //设置推动
        mLineChar.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
        mLineChar.setPinchZoom(true);
        //模拟数据
        ArrayList<Entry> values = new ArrayList();
        values.add(new Entry(5, 50));
        values.add(new Entry(10, 66));
        values.add(new Entry(15, 100));
        values.add(new Entry(20, 50));
        values.add(new Entry(35, 80));
        values.add(new Entry(40, 110));
        values.add(new Entry(45, 90));
        values.add(new Entry(50, 110));
        values.add(new Entry(100, 60));
        //设置数据
        setDataLineChart(values);
        //默认动画
        mLineChar.animateX(1500);
        //刷新
        mLineChar.invalidate();
        // 得到lable文字
        Legend l = mLineChar.getLegend();
        // 设置lable
        l.setForm(Legend.LegendForm.LINE);

        //填充效果
        List<ILineDataSet> setsFilled = mLineChar.getData().getDataSets();
        for (ILineDataSet iSet : setsFilled) {
            LineDataSet set = (LineDataSet) iSet;
            if (set.isDrawFilledEnabled())
                set.setDrawFilled(false);//不添加填充
            else
                set.setDrawFilled(true);//填充
        }
        mLineChar.invalidate();
    }
    private void setDataLineChart(ArrayList<Entry> values) {
        LineDataSet set1;
        if (mLineChar.getData() != null && mLineChar.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChar.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChar.getData().notifyDataChanged();
            mLineChar.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values,"折线图");//绘制线条
            // 在这里设置线
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            if (Utils.getSDKInt() >= 18) {
                // 填充背景只支持18以上
                //Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                //set1.setFillDrawable(drawable);
                set1.setFillColor(Color.YELLOW);//填充颜色
            } else {
                set1.setFillColor(Color.BLACK);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            //添加数据集
            dataSets.add(set1);
            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);
            //谁知数据
            mLineChar.setData(data);
        }
    }

    //条形图
    private BarChart mBarChart;
    private void barChart(){
        mBarChart = view.findViewById(R.id.mBarChart);
        //设置表格上的点，被点击的时候，的回调函数
        mBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener(){
            @Override
            public void onValueSelected(Entry e, Highlight h) {
            }
            @Override
            public void onNothingSelected() {
            }
        });
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        // 如果60多个条目显示在图表,drawn没有值
        mBarChart.setMaxVisibleValueCount(60);
        // 扩展现在只能分别在x轴和y轴
        mBarChart.setPinchZoom(false);
        //这里设置为true每一个直方图的值就会显示在直方图的顶部
        mBarChart.setDrawValueAboveBar(false);
        //是否显示表格颜色
        mBarChart.setDrawGridBackground(false);
        //设置每个直方图阴影为false
        mBarChart.setDrawBarShadow(false);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        //格式化x轴坐标显示
        xAxis.setValueFormatter(new ValueFormatter(){
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return Double.valueOf(value).intValue()+"月份";
            }
        });
        //左侧y轴
        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        //这个替换setStartAtZero(true)
        leftAxis.setAxisMinimum(0f);
        //格式化x轴坐标显示
        leftAxis.setValueFormatter(new ValueFormatter(){
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value+"万";
            }
        });
        //右侧y轴
        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);
        //格式化x轴坐标显示
        rightAxis.setValueFormatter(new ValueFormatter(){
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value+"万";
            }
        });
        //模拟数据
        ArrayList<BarEntry> values = new ArrayList();
        values.add(new BarEntry(1, 50));
        values.add(new BarEntry(2, 66));
        values.add(new BarEntry(3, 100));
        values.add(new BarEntry(4, 50));
        values.add(new BarEntry(5, 80));
        values.add(new BarEntry(6, 110));
        values.add(new BarEntry(7, 90));
        values.add(new BarEntry(8, 110));
        values.add(new BarEntry(9, 60));
        //手机屏幕上显示6剩下的滑动直方图然后显示
        float ratio = (float) values.size()/(float) 5;
        //显示的时候是按照多大的比率缩放显示，1f表示不放大缩小
        mBarChart.zoom(ratio,1f,0,0);
        //设置数据
        setDataBarChart(values);
        //显示顶点值
        for (IDataSet set : mBarChart.getData().getDataSets()){
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        //显示边框
//        for (IBarDataSet set : mBarChart.getData().getDataSets()){
//            ((BarDataSet) set)
//                    .setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f
//                            : 1.f);
//        }
        mBarChart.invalidate();
        mBarChart.invalidate();
        //XY轴动画
        mBarChart.animateXY(3000, 3000);
    }
    //条形图设置数据
    private void setDataBarChart(ArrayList yVals1) {
        BarDataSet set1;
        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "2019年公司盈利");
            //设置有四种颜色
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            //设置数据
            mBarChart.setData(data);
        }
    }

    //饼状图
    private PieChart mPieChart ;
    private void pieChart(){
        //饼状图
        mPieChart = view.findViewById(R.id.mPieChart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置圆心文字
        mPieChart.setCenterText("Java开发");
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);
        mPieChart.setDrawCenterText(true);
        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);
        //变化监听
        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener(){
            @Override
            public void onValueSelected(Entry e, Highlight h) {
            }
            @Override
            public void onNothingSelected() {
            }
        });
        //xy轴动画
        mPieChart.animateXY(1400, 1400);
        //旋转动画
//        mPieChart.spin(1000, mPieChart.getRotationAngle(), mPieChart.getRotationAngle() + 360, Easing.EasingOption
//                .EaseInCubic);
        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList();
        entries.add(new PieEntry(40, "帅气"));
        entries.add(new PieEntry(20, "机智"));
        entries.add(new PieEntry(20, "年少"));
        entries.add(new PieEntry(20, "多金"));
        //设置数据
        setmPieChartData(entries);
        mPieChart.animateY(1400,Easing.EaseInOutQuad);
        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);
        //显示百分比
        for (IDataSet<?> set : mPieChart.getData().getDataSets()){
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
        mPieChart.invalidate();
    }
    //设置数据
    private void setmPieChartData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "Java开发");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }


}
