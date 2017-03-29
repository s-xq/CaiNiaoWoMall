# CaiNiaoWoMall
菜鸟商城

20170225
进度：菜鸟窝课时8
Banner已经由OKHtpp加载，但未封装。接下来进行封装。

已完成：
FragmentTabHost实现底部导航菜单
使用自定义ToolBar
Banner网络加载已实现-使用开源控件ImageSlider
RecylerView实现HomeFramgment内部首页商品分类，数据为本地数据


20170311
使用了BaseAdapter、SimpleAdapter;
首页、热卖、分类、网络加载已完成;
购物车界面已完成，无网络接口，数据保存在SharedPreference中，通过ShoppingCart类来包装被添加进购物车的Ware;
Tab切换时，通过在MainActivity中保存的CartFragment的引用来调用refreshData()以更新购物车数据，结构混乱；
数字加减控件使用AmountView，大小固定，中间是EditText，还未设置TextChangedListener;



20170319
完成Git提交
