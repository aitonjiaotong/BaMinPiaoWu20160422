package bamin.com.kepiao.customView;

import android.widget.GridView;

//自定义的gridview，嵌入listview中，会全部显示
public class MyGridView extends GridView
{  
    public MyGridView(android.content.Context context,
                      android.util.AttributeSet attrs)
    {  
        super(context, attrs);  
    }  
  
    /** 
     * 设置不滚动 
     */  
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
  
    }  
  
}  