package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CalcActivity extends AppCompatActivity {
    public static Context context;
    public static boolean error;
    public static boolean needClearRes = false;
    public static boolean needClearAll = false;
    private Views views;
    private CalculatorButtons buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CalcActivity.context = super.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        views = new Views(findViewById(R.id.tvHistory), findViewById(R.id.tvResult));
        views.getTvHistory().setText("");
        views.getTvResult().setText("0");

        View[] serviceButtons = new View[]{
                findViewById(R.id.btnPlusMinus),
                findViewById(R.id.btnComma),
                findViewById(R.id.btnBackspace)};

        buttons = new CalculatorButtons(FindNumbers(), FindOperations(), serviceButtons, views);
    }

    /**
     * Call when destroy activity. Use to save data
     *
     * @param outState to saved object
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("history", views.getTvHistory().getText());
        outState.putCharSequence("result", views.getTvResult().getText());
        Log.d("onSaveInstance", "Data saves");
    }

    /**
     * Call after create activity. Use to change data
     *
     * @param savedInstanceState saved object
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        views.getTvHistory().setText(savedInstanceState.getCharSequence("history"));
        views.getTvResult().setText(savedInstanceState.getCharSequence("result"));
        Log.d("onSaveInstance", "Data was rad");
    }

    private List<View> FindNumbers() {
        return Arrays.asList(findViewById(R.id.btn7), findViewById(R.id.btn8), findViewById(R.id.btn9), findViewById(R.id.btn4), findViewById(R.id.btn5), findViewById(R.id.btn6), findViewById(R.id.btn1), findViewById(R.id.btn2), findViewById(R.id.btn3), findViewById(R.id.btn0));
    }

    private HashMap<View, String> FindOperations() {
        return new HashMap<View, String>() {{
            put(findViewById(R.id.btnPercent), "percent");
            put(findViewById(R.id.btnClearE), "clearE");
            put(findViewById(R.id.btnClearAll), "clearAll");
            put(findViewById(R.id.btnInverse), "inverse");
            put(findViewById(R.id.btnSquare), "square");
            put(findViewById(R.id.btnSqrt), "sqrt");
            put(findViewById(R.id.btnDivide), "divide");
            put(findViewById(R.id.btnMultiplication), "multiply");
            put(findViewById(R.id.btnMinus), "minus");
            put(findViewById(R.id.btnPlus), "plus");
            put(findViewById(R.id.btnSum), "equal");
        }};
    }
}