package net.oschina.app.bingley;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/24.
 */
public abstract class BaseFragment extends Fragment {
    protected View mRoot;  // 代表加载在fragment中的一个View
    private Bundle mBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 未接收前面fragment过来的参数
        mBundle = getArguments();
        initBundle(mBundle);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null) {
                parent.removeView(mRoot);
            }
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mRoot);
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 将资源释放掉
        mRoot = null;
        mBundle = null;
    }

    protected abstract int getLayoutId();    // 这个方法得设置成抽象，子类必须得实现（因为fragment得依托与VIew）

    protected void initWidget(View root) {}

    protected void initData() {}

    protected void initBundle(Bundle bundle) {}
}
