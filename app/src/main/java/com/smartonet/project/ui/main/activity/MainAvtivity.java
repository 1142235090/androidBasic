package com.smartonet.project.ui.main.activity;

import com.google.android.material.navigation.NavigationView;
import com.smartonet.project.R;
import com.smartonet.project.core.basic.BaseActivity;
import com.smartonet.project.core.ioc.AutowaireLayout;
import com.smartonet.project.core.ioc.AutowaireView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * Creat by hanzhao
 * on 2019/12/17
 **/
@AutowaireLayout(value = R.layout.activity_main)
public class MainAvtivity extends BaseActivity {

    @AutowaireView(R.id.drawerLayout)
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;

    @Override
    public void onCreateView() {

    }

    @Override
    public void initView() {
        //1.左侧的抽屉菜单
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //绑定当前的ActionBar，除此之外NavigationUI还能绑定Toolbar和CollapsingToolbarLayout
        //绑定后，系统会默认处理ActionBar左上角区域，为你添加返回按钮，将所切换到的Fragment在导航图里的name属性中的内容显示到Title
        //.setDrawerLayout(drawerLayout)后才会出现菜单按钮
//        drawerLayout = findViewById(R.id.drawerLayout);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setDrawerLayout(drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(MainAvtivity.this, navController, appBarConfiguration);
        //设置左侧菜单
        NavigationView navigationView = findViewById(R.id.navigation_view);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void setFunction() {

    }

    /**
     * 左上角的菜单被点击时调用到
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
