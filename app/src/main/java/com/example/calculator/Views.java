package com.example.calculator;

import android.widget.TextView;

public class Views {
    private TextView tvHistory;
    private TextView tvResult;

    public TextView getTvHistory() {
        return tvHistory;
    }

    public TextView getTvResult() {
        return tvResult;
    }

    public Views(TextView tvHistory, TextView tvResult) {
        this.tvHistory = tvHistory;
        this.tvResult = tvResult;
    }
}