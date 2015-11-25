# WatchFaceView
1.半圆形表盘，表盘图片可更换
2.使用 在布局文件中加入 xmlns:wfv="http://schemas.android.com/apk/res-auto"
       可设属性：  <com.holy.library.watchfaceview.WatchFaceView
                      android:id="@+id/watchface_view"      
                      android:layout_width="150dp"
                      android:layout_height="75dp"
                      wfv:pointer_color="@color/line_color"
                      wfv:pointer_circle_color="@color/circle_color"
                      />
        wfv:back_pic 指定表盘图片,表盘图片宽高比2：1
        wfv:pointer_color 指针颜色
        wfv:pointer_circle_color  指针基座颜色
        wfv:total_value  表盘量程，与表盘图片显示量程一致

3.可在WatchFaceView项目中bin文件夹下找到jar文件直接引入工程，或者将整个项目做为library引入工程
