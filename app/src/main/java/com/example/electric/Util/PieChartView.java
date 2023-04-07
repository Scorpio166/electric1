package com.example.electric.Util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
//https://blog.csdn.net/qq_41008818/article/details/128472059?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522168083533316800215031288%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=168083533316800215031288&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~times_rank-1-128472059-null-null.142^v81^insert_down38,201^v4^add_ask,239^v2^insert_chatgpt&utm_term=android%20%E9%A5%BC%E5%9B%BE&spm=1018.2226.3001.4187
public class PieChartView extends View {

    public PieChartView(Context context) {
        this(context,null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        colors=new ArrayList<>();
        colors.add("#FF5722");
        colors.add("#FF9800");
        colors.add("#00BCD4");
        colors.add("#03A9F4");
        colors.add("#FFC107");
        colors.add("#2196F3");
        colors.add("#CDDC39");
        colors.add("#3F51B5");
        colors.add("#8BC34A");
        colors.add("#673AB7");
        colors.add("#9C27B0");
        colors.add("#8BC34A");
        colors.add("#F44336");
        colors.add("#009688");
        colors.add("#B1B1B1");

    }

    private int widthMode;
    private int heightMode;
    private int widthSize;
    private int heightSize;
    private Paint piePaint;
    private Paint linePaint;
    private Paint textPaint;
    private Paint titlePaint;
    private RectF mRectF; //饼图矩阵
    private float CENTER_X = 0; //中心点横坐标
    private float CENTER_Y = 0; //中心点纵坐标
    private float RADIUS = 0; //圆半径
    private List<String> colors;//饼图的颜色列表
    private List<String> items;//饼图功能项
    private List<Integer> dataList;//数据列表
    private String unit="";//数据单位
    private float step=0;//进率步距，360的角度怎么分配给数据
    private String titleStr="";
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize=MeasureSpec.getSize(widthMeasureSpec);
        heightSize=MeasureSpec.getSize(heightMeasureSpec);
        widthMode=MeasureSpec.getMode(widthMeasureSpec);
        heightMode=MeasureSpec.getMode(heightMeasureSpec);

        CENTER_X = widthSize/2f;
        CENTER_Y = heightSize/2f;
        if (widthSize >= heightSize){
            RADIUS = heightSize/2f-250;
        }else {
            RADIUS = widthSize/2f-250;
        }

        initPaint();
    }

    private void initPaint() {
        piePaint =new Paint();
        piePaint.setStrokeWidth(6);
        piePaint.setAntiAlias(true);//抗锯齿功能

        linePaint =new Paint();
        linePaint.setStrokeWidth(4);
        linePaint.setColor(Color.parseColor("#858585"));
        linePaint.setAntiAlias(true);//抗锯齿功能
        /*PathEffect effects = new DashPathEffect(new float[]{5, 10}, 0);//设置绘制虚线
        linePaint.setPathEffect(effects);*/

        textPaint =new Paint();
        textPaint.setColor(Color.parseColor("#858585"));
        textPaint.setAntiAlias(true);//抗锯齿功能
        textPaint.setTextSize(40);

        titlePaint =new Paint();
        titlePaint.setColor(Color.parseColor("#858585"));
        titlePaint.setAntiAlias(true);//抗锯齿功能
        titlePaint.setTextSize(40);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (items==null || items.size()==0)return;
        if (dataList==null || dataList.size()==0)return;
        if (colors==null || colors.size()==0)return;
        if (items.size() != dataList.size())return;//要求 功能项长度 等于 数据项长度

        float dataSum=0;
        for (int k=0;k<dataList.size();k++){
            dataSum=dataList.get(k)+dataSum;
        }
        step=360/dataSum;

        float startAngle=0;//开始绘制的角度
        for (int k=0;k<dataList.size();k++){

            if (k<colors.size()){
                //按照顺序取颜色
                piePaint.setColor(Color.parseColor(colors.get(k)));
            }else {
                //超出颜色列表的数据 取最后一种颜色
                piePaint.setColor(Color.parseColor(colors.get(colors.size()-1)));
            }

            float thisAngle=dataList.get(k)*step;//本次次循环需要绘制的角度

//            if (k==dataList.size()-1){
//                //最后一块饼图跳出
//                //绘制扇形
//                canvas.drawArc(CENTER_X - RADIUS+14, CENTER_Y - RADIUS-10, CENTER_X + RADIUS+14, CENTER_Y + RADIUS-10, startAngle, thisAngle, true, piePaint);
//            }else {
                //绘制扇形
                canvas.drawArc(CENTER_X - RADIUS, CENTER_Y - RADIUS, CENTER_X + RADIUS, CENTER_Y + RADIUS, startAngle, thisAngle, true, piePaint);
//            }

            float lineAngle = startAngle + thisAngle/2f; //获取线条的角度

            startAngle=thisAngle+startAngle;//叠加起步角度

            //分别求线条的横坐标起点、纵坐标起点、横坐标终点、纵坐标终点
            float lineStartX = CENTER_X + RADIUS * (float) Math.cos(lineAngle / 180 * Math.PI);
            float lineStartY = CENTER_Y + RADIUS * (float) Math.sin(lineAngle / 180 * Math.PI);
            float lineEndX = CENTER_X + (RADIUS + 20) * (float) Math.cos(lineAngle / 180 * Math.PI);
            float lineEndY = CENTER_Y + (RADIUS + 20) * (float) Math.sin(lineAngle / 180 * Math.PI);

            if (k==dataList.size()-1){
                //最后一条线跳出
                lineStartX += 14;
                lineStartY -= 10;
                lineEndX += 14;
                lineEndY -= 10;
            }
            canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, linePaint); //绘制指示线条

            if (lineEndX>CENTER_X){
                //在圆心右侧   向右延长
                canvas.drawLine(lineEndX, lineEndY, lineEndX+200, lineEndY, linePaint); //绘制延长线
                canvas.drawText(items.get(k)+": "+dataList.get(k)+unit,lineEndX+200-100,lineEndY-10,textPaint);//绘制数据值
            }else {
                //在圆心左侧   向左延长
                canvas.drawLine(lineEndX, lineEndY, lineEndX-200, lineEndY, linePaint); //绘制延长线
                canvas.drawText(items.get(k)+": "+dataList.get(k)+unit,lineEndX-200,lineEndY-10,textPaint);//绘制数据值
            }


        }

        //画中圆
        piePaint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawCircle(CENTER_X,CENTER_Y,RADIUS/2, piePaint);

        canvas.drawText(titleStr,CENTER_X-RADIUS/2+20,CENTER_Y+10,textPaint);//绘制数据值
    }

    /**
     * 设置数据
     * @param dataList
     */
    public synchronized void setData(List<Integer> dataList, String unit){
        this.dataList=dataList;
        this.unit=unit;
        postInvalidate();
    }

    /**
     * 设置对比模块项
     * @param items
     */
    public void setItemList(List<String> items){
        this.items=items;
        postInvalidate();
    }

    /**
     * 设置标题
     * @param titleStr
     */
    public void setTitle(String titleStr){
        this.titleStr=titleStr;
        postInvalidate();
    }

    /**
     * 设置标题大小
     * @param sizePx
     */
    public void setTitleSize(float sizePx){
        titlePaint.setTextSize(sizePx);
    }

}
