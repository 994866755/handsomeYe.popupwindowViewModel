以viewmodel的方式封装popupwindow
======


使用方法
---------

##### 一、创建一个popupwindowViewModel 继承 PopupWindowBaseViewModel

```
public class EditPopWinViewModel extends PopupWindowBaseViewModel<>{
  ..............
}
```

##### 二、初始化popupwindowViewModel

```
pwViewModel = new EditPopWinViewModel(new PopupWindowBuilder(
                        this, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                        .setAnchor(v.getRootView())
                        .setGravityAt(Gravity.BOTTOM)
                        .builder()
                );
```

##### 三、展示popupwindowViewModel

popupwindowViewModel.showAt() 或  popupwindowViewModel.showAs()



关键类
-----------------
1. PopupWindowBaseViewModel //viewmodel的基类
2. PopupWindowBuilder //建造者，初始化数据的辅助类



PopupWindowBaseViewModel介绍
------------------------------

##### 为什么要使用ViewModel的方式封装
从MVVM模式被提出之后，ViewModel就备受欢迎，对我来说，popupwindow也是一个页面（悬浮的不完全覆盖屏幕的页面），虽然没有模板的生命周期，但是用viewmodel我感觉对生命周期并没有过多的依赖。主要的是viewmodel的方式对我来说是一大解耦神器，试着想想，如果你在activity/fragment中直接创建popupwindow并且处理它的一系列逻辑，那你的activity/fragment将会变得非常臃肿，我假如你一个页面需要5、6个popupwindow，你可以试着在你的activity中尝试一下，你会发现你的activity变成了一个大胖子，这时候你用viewmodel各自处理各自的逻辑，代码和页面逻辑将会变得非常的有序。

##### 构造方法

看看这个基类中的构造方法

```
public PopupWindowBaseViewModel(PopupWindowBuilder builder){
        this.builder = builder;
        init();//初始化变量
        if (context instanceof Activity){
            parentActivity = (Activity) context;
        }
        createTemplate();
    }
```
构造方法中依赖两个东西，PopupWindowBuilder和createTemplate（）。

1. PopupWindowBuilder是我打算使用Builder模式来创建popupwindow，首先是因为popupwindow初始化时用到的参数挺多的，如下所示：

```
public Context context;
    public int width;
    public int hight;
    public boolean isSetOut = true;
    public boolean isFocusable = true;
    public int animation;
    public float bgAlpha = 0.4f;
    public View anchor;
    public int xoff = 0;
    public int yoff = 0;
    public int gravityAs;
    public int gravityAt;
```

我封装时一共用到了这些参数，其实还不止，说不定我再某些需求上需要用到新的popupwindow的方法，这样又会新加参数。这么多参数难道你要全部用构造方法传？或者你用很多个Set方法传？那不如用Builder模式。
其次，你用过dialog吧，它的创建就是使用Builder模式，是否觉得dialog和popupwindow很相似，那么popupwindow也应该使用Builder模式。

2、createTemplate（）是打算用模板方法来设置popupwindow的初始化流程，这算是一种习惯吧，我定义Base类时，如果存在特定的步骤，我都喜欢用模板方法去做。


##### PopupWindowBuilder

这个类的代码不是很难懂，不用多解释，关键是调用的时候

```
pwViewModel = new EditPopWinViewModel(new PopupWindowBuilder(
                        this, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                        .setAnchor(v.getRootView())
                        .setGravityAt(Gravity.BOTTOM)
                        .builder()
                );
```

不是直接使用传统的builder模式方法，而是创建builder传入viewmodel的构造函数中。这是因为如果用传统的builder模式，抽象类无法引用静态内部类。所以我就把构造器给抽离出来，我也不知道这样的做法是不是最优，但是耦合度不高是真的，好像也符合开闭原则。


##### createTemplate（）

模板方法的代码如下：

```
public final void createTemplate(){

        if (contentView == null){
            contentView = LayoutInflater.from(context).inflate(getLayoutId(),getRoot());
        }
        ButterKnife.inject(this,contentView);
        width = getPopWidth();
        hight = getPopHight();
        popupwindow = getPopupWindow();
        //在此判断一次popupwindow，如果为空则不进行接下来的操作
        if (popupwindow == null){
            return;
        }
        initPopView();//设置popwin的配置
        initView(); //初始化view

    }
```

1. 创建contentView
2. 设置高和宽
3. 创建popupwindow
4. 初始化popupwindow
5. 初始化contentView

initPopView的方法如下：

```
    protected void initPopView(){
        setSoftInput();
        setOutsideTouchable();
        setFocusable();
        setAnimationStyle();
        setBackgroundDrawable(); //设置背景防止5.0以下的系统出BUG
        backgroundAlpha();
    }
```

主要就是做一些设置popupwindow的流程，如果以后需要改的话直接在这个方法内改，也挺方便的，如果有特殊的popupwindow的话，可以重写这个方法。


demo说明
-----------

demo中有两个例子，第二个是列表，可能会有操作是点击列表的某一项，popupwindow就dismiss，我在demo里没做处理，其实很简单，不管做什么处理，都和activity没关系，因为已经把view抽离到viewmodel了，如果是列表并且item与页面有交互，可以在特定的viewmodel中使用观察者模式去沟通，如果你使用rx或eventbus也可以。


不足之处
------------

要说不足之处，可能比较多，因为popupwindow这个组件本身就存在很多坑。

1. 版本问题，我这里没试过，但是我看别人问过这样的问题，比如5.0以下不设背景点击外部无法消失，再比如展示中的As方法，需要API19以上。所以我觉得一个最大的坑就是版本的坑，我这里因为也没遇到过，我就用7.0以上的没问题。所以在封装中没有做不同特定版本的适配。
2. 和软键盘的冲突，这里的demo第一个popupwindow的做法是软键盘把页面顶上去，而且我也设置了setSoftInput() ，这个方法可以由子类重写，你可以在这个方法中加入你与软键盘冲突的解决方法。
3. 未知性错误，因为也是第一版，而且popupwindow又有很多坑，内存、优化什么的都没做，所以还是会有问题。









