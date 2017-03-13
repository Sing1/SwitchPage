package sing.switchpage.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import sing.widget.SwitchPage;

public class MainActivity extends AppCompatActivity {

    private SwitchPage page;
    private boolean isFirst = true;//两种方法

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = (SwitchPage) findViewById(R.id.page);
        if (isFirst){
            page.setVisibility(View.VISIBLE);
        }else{
            page.setVisibility(View.GONE);
            page = new SwitchPage.Builder(this)
                    .setTitles("第一,第二,第三")
                    .setFillColor("#00000000")
                    .setPressColor("#FFFFFF")
                    .setRadius(8)
                    .setStrokeWidth(1)
                    .setStrokeColor("#FFFFFF")
                    .setTextColor("#FFFFFF")
                    .setTextSelectColor("#000000")
                    .setTextSize(32)//转换后的大小 px
                    .build();
            page.setLayoutParams(new LinearLayout.LayoutParams(560,80));
            ((LinearLayout)findViewById(R.id.parent)).addView(page,0);
        }

        page.setOnClickListener(new SwitchPage.OnClickListener() {
            @Override
            public void selected(int position, String txt) {
                Toast.makeText(MainActivity.this,"选择了第"+(position+1)+"个，标题是："+txt,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
