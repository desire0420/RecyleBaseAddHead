package cn.wangwei;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Jude on 2016/1/6.
 */
public class DataProvider {


    public static List<Ad> getAdList(){
        ArrayList<Ad> arr = new ArrayList<>();
        arr.add(new Ad("http://i2.hdslb.com/u_user/e97a1632329cac1fa6ab3362e7233a08.jpg","http://www.bilibili.com/topic/v2/1004.html"));
        arr.add(new Ad("http://i1.hdslb.com/u_user/80fcc32d0b5d3565377847bd9dd06dc3.jpg","http://www.bilibili.com/topic/1003.html"));
        arr.add(new Ad("http://i2.hdslb.com/u_user/f19f0e44328a4190a48acf503c737c50.png","http://yoo.bilibili.com/html/activity/cq2015/index.html"));
        arr.add(new Ad("http://i1.hdslb.com/u_user/7ee1aeadc8257f43fa6b806717c9c398.png","http://www.bilibili.com/html/activity-acsociety.html"));
        return arr;
    }



}
