measure_app
===================
measure_app
-------------------
2019毕业设计，基于Android Studio 平台的测量程序设计与实现

# 一级标题
## 二级标题
这是一段普通文本
直接回车不能换行，
可以在上一行文本后面补两个空格，

这样下一行的文本就换行了。

------

******
或者就是在两行文本直接加一个空行。

    开头加一个Tab是单行文本

`文字高亮`
<img src="http://www.baidu.com/img/bdlogo.gif" width="100%" height="100%"/>

|#|效果|
|---|---
|网络图片|[![百度](http://www.baidu.com/img/bdlogo.gif)](http://www.baidu.com)|
|本地图片|![](Screenshot/Screenshot_dadi.png)|
|本地图片|<img src="Screenshot/Screenshot_dadi.png" width="40%" height="30%"/>|

|#|语法|效果|
|---|----|-----|
|2|`[我的博客]`|[我的博客](http://blog.csdn.net/guodongxiaren "悬停显示")|
* 123
    * 123
        * 123
> 数据结构
>> 树
>>> 二叉树
>>>> 平衡二叉树
>>>>> 满二叉树

### 代码效果
```Java
public class Caculate {
    public static double dmstohudu(double dms){//度.分秒数据化为弧度
        double d,m,s;
        int i = 1;
        if (dms < 0)
        {
            i = -1;
            dms = Math.abs(dms);
        }
        d = Math.floor(dms);//向下取整，返回不大于该数的最大整数，返回double类型
        m = Math.floor(100*(dms - d));
        s = 100 * (100 * (dms - d) - m);
        return i * (d + m / 60 + s / 3600)*Math.PI /180;
    } //Java
```





