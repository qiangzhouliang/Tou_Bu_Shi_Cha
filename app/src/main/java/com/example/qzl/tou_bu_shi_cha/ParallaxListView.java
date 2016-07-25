package com.example.qzl.tou_bu_shi_cha;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Qzl on 2016-07-25.
 */
public class ParallaxListView extends ListView {
    private ImageView iv_layout_head_icon;
    private int maxHeight;
    private int orignalHeight;

    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //拿到imageView
    public void setParallaxImageView(final ImageView iv_layout_head_icon) {
        this.iv_layout_head_icon = iv_layout_head_icon;
        //设定最大图片的高度
        iv_layout_head_icon.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_layout_head_icon.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //获取imageView高度
                orignalHeight = iv_layout_head_icon.getHeight();
                int drawableHeight = iv_layout_head_icon.getDrawable().getIntrinsicHeight();//获取图片的高度
                maxHeight = orignalHeight > drawableHeight ? orignalHeight *2:drawableHeight;//最大高度是图片的高度
            }
        });
    }

    /**
     * 在listView滑动到头的时候执行，可以获取到继续滑动的距离和方向
     * @param deltaX         ：继续滑动到x方向的值
     * @param deltaY         ：继续滑动到Y方向的值 负：表示顶部到头 正：表示底部到头
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX ：x方向最大可以滚动的距离
     * @param maxOverScrollY ：y方向最大可以滚动的距离
     * @param isTouchEvent   ：是否是手指拖动滑动，true:是手指拖动滑动，false:表示fling 靠惯性滑动
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
//        Log.e("tag", "deltaY = " + deltaY + "  isTouchEvent = " + isTouchEvent);
        if (deltaY < 0 && isTouchEvent) {
            //表示顶部到头且手动拖动
            //需要不断增加ImageView的高度
            if (iv_layout_head_icon != null) {
                int newHeight = iv_layout_head_icon.getHeight() - deltaY;
                if (newHeight > maxHeight){
                    newHeight = maxHeight;
                }
                //重新设置高度
                iv_layout_head_icon.getLayoutParams().height = newHeight;
                //使imageView的布局参数生效
                iv_layout_head_icon.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP){
            //抬起，需要将imageView的高度缓慢恢复到最初高度
            ValueAnimator animator = ValueAnimator.ofInt(iv_layout_head_icon.getHeight(),orignalHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //获取动画的值，设置给imageView
                    int animatedValue = (int) valueAnimator.getAnimatedValue();
                    //重新设置高度
                    iv_layout_head_icon.getLayoutParams().height = animatedValue;
                    //使imageView的布局参数生效
                    iv_layout_head_icon.requestLayout();
                }
            });
            animator.setInterpolator(new OvershootInterpolator());//弹性的一个插值器
            animator.setDuration(350);
            animator.start();
        }
        return super.onTouchEvent(ev);
    }
}
