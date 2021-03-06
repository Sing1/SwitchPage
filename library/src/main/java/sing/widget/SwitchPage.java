package sing.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.TintTypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sing.switchpage.R;

public class SwitchPage extends LinearLayout {

    private OnClickListener listener;
    private LayoutInflater inflater;
    private LinearLayout parent;
    private LinearLayout view;
    private List<TextView> list = new ArrayList<>();

    private String titles = "1,1";
    private int fillColor = Color.parseColor("#00000000");// 填充颜色
    private int pressColor = Color.parseColor("#FFFFFF");// 按下颜色
    private int radius = 8;//弧度
    private int strokeWidth = 1;//边框宽度
    private int strokeColor = Color.parseColor("#FFFFFF");//边框颜色
    private int textColor = Color.parseColor("#FFFFFF");
    private int textSelectColor = Color.parseColor("#000000");
    private int textSize = 14;

    public SwitchPage(Context context, Builder b) {
        super(context);
        inflater = LayoutInflater.from(context);

        titles = b.titles;
        fillColor = b.fillColor;// 填充颜色
        pressColor = b.pressColor;// 按下颜色
        radius = b.radius;//弧度
        strokeWidth = b.strokeWidth;//边框宽度
        strokeColor = b.strokeColor;//边框颜色
        textColor = b.textColor;
        textSelectColor = b.textSelectColor;
        textSize = b.textSize;

        initView(context);
    }

    public SwitchPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);

        if (attrs != null) {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.SwitchPage, defStyleAttr, 0);

            titles = a.getString(R.styleable.SwitchPage_sp_titles);
            fillColor = a.getColor(R.styleable.SwitchPage_sp_fill_color, Color.parseColor("#00000000"));
            pressColor = a.getColor(R.styleable.SwitchPage_sp_press_color, Color.parseColor("#FFFFFF"));
            radius = a.getDimensionPixelSize(R.styleable.SwitchPage_sp_radius, 8);
            strokeWidth = a.getDimensionPixelSize(R.styleable.SwitchPage_sp_stroke_width, 1);
            strokeColor = a.getColor(R.styleable.SwitchPage_sp_stroke_color, Color.parseColor("#FFFFFF"));
            textColor = a.getColor(R.styleable.SwitchPage_sp_text_color, Color.parseColor("#FFFFFF"));
            textSelectColor = a.getColor(R.styleable.SwitchPage_sp_text_select_color, Color.parseColor("#000000"));
            textSize = a.getDimensionPixelSize(R.styleable.SwitchPage_sp_text_size, 14);
            a.recycle();

            initView(context);
        }
    }

    // ======== builder 模式 ======================================
    public static class Builder {

        private Activity context;

        public Builder(Activity context) {
            this.context = context;
        }

        private String titles;

        public Builder setTitles(String titles) {
            this.titles = titles;
            return this;
        }

        private int fillColor;// 填充颜色

        public Builder setFillColor(String fillColor) {
            this.fillColor = Color.parseColor(fillColor);
            return this;
        }

        private int pressColor;// 按下颜色

        public Builder setPressColor(String pressColor) {
            this.pressColor = Color.parseColor(pressColor);
            return this;
        }

        private int radius;// 弧度

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        private int strokeWidth;// 边框宽度

        public Builder setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        private int strokeColor;// 边框颜色

        public Builder setStrokeColor(String strokeColor) {
            this.strokeColor = Color.parseColor(strokeColor);
            return this;
        }

        private int textColor;// 文字颜色

        public Builder setTextColor(String textColor) {
            this.textColor = Color.parseColor(textColor);
            return this;
        }

        private int textSelectColor;// 文字选中颜色

        public Builder setTextSelectColor(String textSelectColor) {
            this.textSelectColor = Color.parseColor(textSelectColor);
            return this;
        }

        private int textSize;// 文字大小

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public SwitchPage build() { // 构建，返回一个新对象
            return new SwitchPage(context, this);
        }
    }

    // ========================================================================================
    private void initView(final Context context) {
        view = (LinearLayout) inflater.inflate(R.layout.switch_page, this, true);
        parent = (LinearLayout) view.findViewById(R.id.ll);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                show(context,view.getWidth(), view.getHeight());
            }
        });

        view.setBackground(getParentBackground());
    }

    public void show(Context context,int width, int height) {
        if (TextUtils.isEmpty(titles)) {
            return;
        }

        String[] title = titles.split(",");
        int size = title.length;
        for (int i = 0; i < size; i++) {
            TextView tv = (TextView) inflater.inflate(R.layout.child_view, null);
            tv.setPadding(15, 0, 15, 0);
            tv.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, height));
            tv.setText(title[i]);
            tv.setTextColor(textColor);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            if (i == 0) {
                tv.setBackground(getLeftBackground());
            } else if (i == size - 1) {
                tv.setBackground(getRightBackground());
            } else {
                tv.setBackground(getCenterBackground());
            }
            final int position = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClick(position);
                }
            });
            list.add(tv);
            parent.addView(tv);

            if (i != size-1){
                // 分割线
                View line = new View(context);
                line.setLayoutParams(new ViewGroup.LayoutParams(strokeWidth,ViewGroup.LayoutParams.MATCH_PARENT));
                line.setBackgroundColor(strokeColor);
                parent.addView(line);
            }
        }

        list.get(0).setSelected(true);
        list.get(0).setTextColor(textSelectColor);
    }

    public void setClick(int position) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected(false);
            list.get(i).setTextColor(textColor);
        }
        list.get(position).setSelected(true);
        list.get(position).setTextColor(textSelectColor);
        if (listener != null) {
            listener.selected(position, list.get(position).getText().toString());
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void selected(int position, String txt);
    }

    // == 获取背景颜色选择器 ===================================================================
    private GradientDrawable getParentBackground() {
        GradientDrawable parentBackground = new GradientDrawable();//创建drawable
        parentBackground.setColor(fillColor);
        parentBackground.setCornerRadius(radius);
        parentBackground.setStroke(strokeWidth, strokeColor);
        return parentBackground;
    }

    private StateListDrawable getLeftBackground() {
        GradientDrawable background = new GradientDrawable();//创建drawable
        background.setColor(fillColor);
        background.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius, radius});
        background.setStroke(0, strokeColor);

        GradientDrawable backgroundPress = new GradientDrawable();//创建drawable
        backgroundPress.setColor(pressColor);
        backgroundPress.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius, radius});
        backgroundPress.setStroke(0, strokeColor);

        StateListDrawable leftBackground = new StateListDrawable();
        leftBackground.addState(new int[]{android.R.attr.state_pressed}, backgroundPress);
        leftBackground.addState(new int[]{android.R.attr.state_selected}, backgroundPress);
        leftBackground.addState(new int[]{-android.R.attr.state_selected}, background);
        leftBackground.addState(new int[]{-android.R.attr.state_pressed}, background);
        return leftBackground;
    }

    private StateListDrawable getRightBackground() {
        GradientDrawable background = new GradientDrawable();//创建drawable
        background.setColor(fillColor);
        background.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
        background.setStroke(0, strokeColor);

        GradientDrawable backgroundPress = new GradientDrawable();//创建drawable
        backgroundPress.setColor(pressColor);
        backgroundPress.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
        backgroundPress.setStroke(0, strokeColor);

        StateListDrawable rightBackground = new StateListDrawable();
        rightBackground.addState(new int[]{android.R.attr.state_pressed}, backgroundPress);
        rightBackground.addState(new int[]{android.R.attr.state_selected}, backgroundPress);
        rightBackground.addState(new int[]{-android.R.attr.state_selected}, background);
        rightBackground.addState(new int[]{-android.R.attr.state_pressed}, background);
        return rightBackground;
    }

    private StateListDrawable getCenterBackground() {
        GradientDrawable background = new GradientDrawable();//创建drawable
        background.setColor(fillColor);
        background.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        background.setStroke(0, strokeColor);

        GradientDrawable backgroundPress = new GradientDrawable();//创建drawable
        backgroundPress.setColor(pressColor);
        backgroundPress.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        backgroundPress.setStroke(0, strokeColor);

        StateListDrawable centerBackground = new StateListDrawable();
        centerBackground.addState(new int[]{android.R.attr.state_pressed}, backgroundPress);
        centerBackground.addState(new int[]{android.R.attr.state_selected}, backgroundPress);
        centerBackground.addState(new int[]{-android.R.attr.state_selected}, background);
        centerBackground.addState(new int[]{-android.R.attr.state_pressed}, background);
        return centerBackground;
    }
}