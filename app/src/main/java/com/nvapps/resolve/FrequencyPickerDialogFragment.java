package com.nvapps.resolve;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrequencyPickerDialogFragment extends DialogFragment {

    private int selected = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_frequency_picker, null);
        ButterKnife.bind(this, view);
        builder.setView(view);

        return builder.create();
    }

    @OnClick({R.id.radio_daily, R.id.radio_weekly, R.id.radio_monthly})
    public void onFrequencySelected(View v) {
        int id = v.getId();
        if (id == R.id.radio_daily) {
            selected = 0;
        } else if (id == R.id.radio_weekly) {
            selected = 1;
        } else if (id == R.id.radio_monthly) {
            selected = 2;
        }
    }

    @OnClick(R.id.set_btn)
    public void setFrequency() {
        FrequencyDialogListener activity = (FrequencyDialogListener) getActivity();
        activity.onFinishedFrequencySelection(selected);
        this.dismiss();
    }

    public interface FrequencyDialogListener {
        void onFinishedFrequencySelection(int selection);
    }

}