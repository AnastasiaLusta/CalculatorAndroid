package com.example.calculator;

import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CalculatorButtons {
    private Views views;
    private List<View> numbers;
    private HashMap<View, String> operations;
    private View[] servicesButtons;
    int numbersInResult = 0;
    private String lastOperation;

    public static String signMinus = CalcActivity.context.getString(R.string.btn_calc_minus);
    public static String signComa = CalcActivity.context.getString(R.string.btn_calc_comma);

    public CalculatorButtons(List<View> numbers, HashMap<View, String> operations, View[] servicesButtons, Views views) {
        MathOperations.setViews(views);

        this.numbers = numbers;
        this.operations = operations;
        this.views = views;

        this.servicesButtons = servicesButtons;

        InitializeEvents();
    }

    /**
     * Initialize all buttons events
     */
    private void InitializeEvents() {
        // region operation buttons
        for (View numbers_view : numbers) {
            numbers_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    digitClick(view, "number");
                }
            });
        }

        for (View operations_view : operations.keySet()) {
            operations_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    operationClick(operations.get(operations_view));
                }
            });
        }
        //endregion

        // region services buttons
        this.servicesButtons[0].setOnClickListener(this::pmClick);

        this.servicesButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                digitClick(view, "digit");
            }
        });

        this.servicesButtons[2].setOnClickListener(this::BackSpace);
        // endregion
    }

    private void operationClick(String operation) {
        System.out.println(operation);
        try {
            switch (operation) {
                case "inverse":
                    MathOperations.invert();
                    break;
                case "sqrt":
                    MathOperations.sqrt();
                    break;
                case "square":
                    MathOperations.pow();
                    break;
                case "minus":
                    MathOperations.operationClick("-");
                    lastOperation = "minus";
                    break;
                case "plus":
                    MathOperations.operationClick("+");
                    lastOperation = "plus";
                    break;
                case "multiply":
                    MathOperations.operationClick("X");
                    lastOperation = "multiply";
                    break;
                case "divide":
                    MathOperations.operationClick("÷");
                    lastOperation = "divide";
                    break;
                case "equal":
                    if (views.getTvHistory().getText().toString().contains("=")) {
                        System.out.println(lastOperation);
                        operationClick(lastOperation);
                    }

                    double result = MathOperations.equal();
                    Show(result);
                    break;
                case "clearE":
                    ClearE();
                    break;
                case "clearAll":
                    ClearAll();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void ClearAll() {
        if (views.getTvHistory().getText() != "") {
            if (CalcActivity.needClearAll) CalcActivity.needClearAll = false;
            views.getTvHistory().setText("");

            ClearE();
        }
    }

    private void ClearE() {
        if (views.getTvResult().getText() != "0" && MathOperations.getCurrentNumber() != 0) {
            if (CalcActivity.needClearRes) CalcActivity.needClearRes = false;
            MathOperations.setCurrentNumber(0);
            views.getTvResult().setText("0");

            lastOperation = null;
        }
    }

    private void Show(double result) {
        views.getTvResult().setText(MathOperations.generalLogicClear(String.format(Locale.getDefault(), "%.10f", result)));
    }

    // Return values and show method

    /**
     * BackSpace Logic
     *
     * @param v
     */
    private void BackSpace(View v) {
        String result = views.getTvResult().getText().toString();
        int len = result.length();

        if (len <= 1) {
            views.getTvResult().setText("0");
            MathOperations.setCurrentNumber(0);
            return;
        }
        result = result.substring(0, result.length() - 1);
        if (result.equals(signMinus)) {
            result = "0";
        }

        MathOperations.setCurrentNumber(0);
        views.getTvResult().setText(result);
    }

    /**
     * Change +-. Logic event
     */
    private void pmClick(View v) {
        try {
            MathOperations.pmLogic();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Click logic
     */
    private void digitClick(View v, String operation) {
        if (operation == "number") {
            numbersInResult++;
        }

        if (numbersInResult <= 10 || operation == "digit") {
            if (!views.getTvResult().getText().toString().contains(signComa) && operation == "digit" || operation == "number") {
                if (CalcActivity.needClearRes) {
                    CalcActivity.needClearRes = false;
                    views.getTvResult().setText("0");
                    MathOperations.setCurrentNumber(0);
                    lastOperation = null;
                }

                if (CalcActivity.needClearAll) {
                    CalcActivity.needClearAll = false;
                    views.getTvHistory().setText("");
                    views.getTvResult().setText("0");
                    MathOperations.setCurrentNumber(0);
                    lastOperation = null;
                }

                String digit = ((Button) v).getText().toString();
                String result = views.getTvResult().getText().toString();

                if (result.equals("0") && operation != "digit" || CalcActivity.error) {
                    if (CalcActivity.error) {
                        CalcActivity.error = false;
                        views.getTvHistory().setText("");
                    }

                    result = digit;
                } else {
                    result += digit;
                }
                views.getTvResult().setText(result);

                if (operation == "number") {
                    MathOperations.setCurrentNumber(Double.parseDouble(result));
                }
            }
        }
    }
}