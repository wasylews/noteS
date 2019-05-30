package com.genius.wasylews.notes.presentation.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.arellomobile.mvp.MvpDelegate;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

/**
 * Base fragment with AndroidX support
 */
public abstract class BaseFragment extends DaggerFragment {

    private boolean mIsStateSaved;
    private MvpDelegate<? extends BaseFragment> mMvpDelegate;
    private Unbinder unbinder;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBaseActivity().getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        setEnabled(onBackPressed());
                    }
                });

        getMvpDelegate().onCreate(savedInstanceState);
    }

    protected boolean onBackPressed() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        onViewReady(getArguments());
    }

    protected abstract void onViewReady(Bundle args);

    protected abstract @LayoutRes
    int getLayoutResourceId();

    protected void navigate(@IdRes int res) {
        navigate(res, null);
    }

    protected void navigate(@IdRes int res, @Nullable Bundle args) {
        NavHostFragment.findNavController(this).navigate(res, args);
    }

    protected void back() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    protected BaseActivity getBaseActivity() {
        return ((BaseActivity) getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        mIsStateSaved = false;

        getMvpDelegate().onAttach();
    }

    public void onResume() {
        super.onResume();

        mIsStateSaved = false;

        getMvpDelegate().onAttach();
    }

    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);

        mIsStateSaved = true;

        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();

        getMvpDelegate().onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
        getMvpDelegate().onDetach();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //We leave the screen and respectively all fragments will be destroyed
        if (getActivity().isFinishing()) {
            getMvpDelegate().onDestroy();
            return;
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (mIsStateSaved) {
            mIsStateSaved = false;
            return;
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        boolean anyParentIsRemoving = false;
        Fragment parent = getParentFragment();
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving();
            parent = parent.getParentFragment();
        }

        if (isRemoving() || anyParentIsRemoving) {
            getMvpDelegate().onDestroy();
        }
    }

    /**
     * @return The {@link MvpDelegate} being used by this Fragment.
     */
    public MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
        }

        return mMvpDelegate;
    }
}