package com.tangedegushi.mvphelper.mvp.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.orhanobut.logger.Logger;
import com.tangedegushi.mvphelper.mvp.AbsPresenter;
import com.tangedegushi.mvphelper.mvp.IView;

/**
 * Created by zq on 2017/8/26.
 */

public abstract class MvpFragmentActivity<P extends AbsPresenter<IView>> extends MvpActivity implements IView{

    public FragmentManager mFragmentManager;
    //--------------------------Fragment相关--------------------------//
    /**
     * 获取Fragment管理器
     *
     * @return
     */
    public FragmentManager getBaseFragmentManager() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        return mFragmentManager;
    }

    /**
     * 获取Fragment事务管理
     *
     * @return
     */
    public FragmentTransaction getFragmentTransaction() {
        return getBaseFragmentManager().beginTransaction();
    }

    /**
     * 替换一个Fragment
     *
     * @param res
     * @param fragment
     */
//    public void replaceFragment(int res, BaseFragment fragment) {
//        replaceFragment(res, fragment, false);
//    }

    /**
     * 替换一个Fragment并设置是否加入回退栈
     *
     * @param res
     * @param fragment
     * @param isAddToBackStack
     */
//    public void replaceFragment(int res, BaseFragment fragment, boolean isAddToBackStack) {
//        FragmentTransaction fragmentTransaction = getFragmentTransaction();
//        fragmentTransaction.replace(res, fragment);
//        if (isAddToBackStack) {
//            fragmentTransaction.addToBackStack(null);
//        }
//        fragmentTransaction.commit();
//
//    }

    /**
     * 添加一个Fragment
     *
     * @param res
     * @param fragment
     */
    public void addFragment(int res, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(res, fragment);
        fragmentTransaction.commit();
    }
    public void addFragment(int res, Fragment fragment,String tag) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(res, fragment,tag);
        fragmentTransaction.commit();
    }

    /**
     * 移除一个Fragment
     *
     * @param fragment
     */
    public void removeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 显示一个Fragment
     *
     * @param fragment
     */
    public void showFragment(Fragment fragment) {
        Logger.e("状态显示");
        if (fragment.isHidden()) {
            com.orhanobut.logger.Logger.e("恢复状态Fragment");
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * 隐藏一个Fragment
     *
     * @param fragment
     */
    public void hideFragment(Fragment fragment) {
        if (!fragment.isHidden()) {
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commit();
        }
    }

}
