package com.nvapps.resolve;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FrequencyPickerDialogFragment extends DialogFragment implements RadioButton.OnCheckedChangeListener{

    @Bind(R.id.rd_daily)
    RadioButton daily;
    @Bind(R.id.rd_weekly)
    RadioButton weekly;
    @Bind(R.id.rd_monthly)
    RadioButton monthly;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_frequency_picker, null);
        ButterKnife.bind(this, view);
        builder.setView(view);
        daily.setOnCheckedChangeListener(this);
        monthly.setOnCheckedChangeListener(this);
        weekly.setOnCheckedChangeListener(this);


        return builder.create();
    }

        @Override
        public void onCheckedChanged(CompoundButton v, boolean check) {
            switch(v.getId()) {
                case R.id.rd_daily:
                    CreateActivity.frequency = 0;
                case R.id.rd_weekly:
                    CreateActivity.frequency = 1;
                case R.id.rd_monthly:
                    CreateActivity.frequency = 2;
        }
            this.getDialog().dismiss();
    }


}