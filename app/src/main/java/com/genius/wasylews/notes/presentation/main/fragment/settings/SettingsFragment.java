package com.genius.wasylews.notes.presentation.main.fragment.settings;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.presentation.base.BaseToolbarFragment;
import com.genius.wasylews.notes.presentation.main.MainActivity;
import com.jakewharton.rxbinding3.widget.RxCompoundButton;

import javax.inject.Inject;

import butterknife.BindView;

public class SettingsFragment extends BaseToolbarFragment implements SettingsView {

    @Inject
    @InjectPresenter
    SettingsPresenter presenter;

    @BindView(R.id.switch_dark_theme) Switch darkThemeSwitch;
    @BindView(R.id.view_divider) View dividerView;

    @BindView(R.id.layout_security) LinearLayout securityLayout;
    @BindView(R.id.switch_fingerprint) Switch fingerprintSwitch;

    @ProvidePresenter
    SettingsPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onViewReady(Bundle args) {
        super.onViewReady(args);
        getBaseActivity().getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        presenter.addDisposable(RxCompoundButton.checkedChanges(darkThemeSwitch)
                .skipInitialValue()
                .subscribe(checked -> presenter.useDarkThemeChanged(checked)));

        presenter.addDisposable(RxCompoundButton.checkedChanges(fingerprintSwitch)
                .skipInitialValue()
                .subscribe(checked -> presenter.useFingerprintChanged(checked)));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_settings;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDarkThemeEnabled(boolean darkThemeEnabled) {
        darkThemeSwitch.setChecked(darkThemeEnabled);
    }

    @Override
    public void showUseFingerprint(boolean useFingerprintUnlock) {
        dividerView.setVisibility(View.VISIBLE);
        securityLayout.setVisibility(View.VISIBLE);
        fingerprintSwitch.setChecked(useFingerprintUnlock);
    }

    @Override
    public void toggleDarkTheme(boolean use) {
        ((MainActivity)getBaseActivity()).toggleDarkMode(use);
    }

    @Override
    protected boolean onBackPressed() {
        back();
        return true;
    }
}
