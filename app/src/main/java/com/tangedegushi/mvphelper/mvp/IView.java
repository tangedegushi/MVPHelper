package com.tangedegushi.mvphelper.mvp;

/**
 * Created by zq on 2017/8/18.
 */

public interface IView<D> {

    /**
     * 显示进度条
     *
     * @param flag    是否可取消
     * @param message 要显示的信息
     */
    void showProgress(boolean flag, String message);

    /**
     * 显示可取消的进度条
     *
     * @param message 要显示的信息
     */
    void showProgress(String message);

    /**
     * 显示可取消的无文字进度条
     */
    void showProgress();

    /**
     * 显示无文字进度条
     *
     * @param flag 是否可取消
     */
    void showProgress(boolean flag);

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 根据资源文件id弹出toast
     *
     * @param resId 资源文件id
     */
    void showToast(int resId);

    /**
     * 根据字符串弹出toast
     *
     * @param msg 提示内容
     */
    void showToast(String msg);

    /**
     * @param result 返回数据结果
     */
    void onResult(D result);

}
