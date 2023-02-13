package com.example.calculator;

import android.widget.Toast;

import java.util.Locale;

public class MathOperations {
    private static double currentNumber;
    private static Views views;
    private static double argument1;
    private static String operation;

    public static void setViews(Views views) {
        MathOperations.views = views;
    }

    public static void setCurrentNumber(double currentNumber) {
        MathOperations.currentNumber = currentNumber;
    }

    public static String getOperation() {
        return operation;
    }

    public static double getCurrentNumber() {
        return currentNumber;
    }

    public static void pow() {
        generalLogicSetHistory("sqr(%s)");
        removeExtraEqualsSign();

        currentNumber = java.lang.Math.pow(currentNumber, 2);

        String result = generalLogicClear(String.format(Locale.getDefault(), "%.10f", currentNumber));
        views.getTvResult().setText(result);
    }

    /**
     * Sqrt logic
     */
    public static void sqrt() throws Exception {
        if (currentNumber == 0) {
            generalToaster("Can not square root from zero");
            throw new Exception("Can not square root from zero");
        } else if (currentNumber < 0) {
            generalToaster("Can not square root from negative number");
            throw new Exception("Can not square root from negative number");
        } else {
            generalLogicSetHistory("√/(%s)");
            removeExtraEqualsSign();

            currentNumber = java.lang.Math.sqrt(currentNumber);

            String result = generalLogicClear(String.format(Locale.getDefault(), "%.10f", currentNumber));
            views.getTvResult().setText(result);
        }
    }

    /**
     * Invert logic
     */
    public static void invert() throws Exception {
        if (currentNumber == 0) {
            generalToaster("Cannot divide by zero");
            throw new Exception("Cannot divide by zero");
        } else {
            generalLogicSetHistory("1/(%s)");
            removeExtraEqualsSign();

            currentNumber = 1 / currentNumber;

            String result = generalLogicClear(String.format(Locale.getDefault(), "%.10f", currentNumber));
            views.getTvResult().setText(result);
        }
    }

    /**
     * Change +-
     */
    public static void pmLogic() throws Exception {
        if (currentNumber == 0) {
            generalToaster("Cannot be negative zero");
            throw new Exception("Cannot be negative zero");
        } else {
            if (currentNumber > 0) {
                views.getTvResult().setText(CalculatorButtons.signMinus + views.getTvResult().getText().toString());
            } else if (currentNumber < 0) {
                views.getTvResult().setText(
                        views.getTvResult().getText().toString().replace(CalculatorButtons.signMinus, ""));
            }

            currentNumber *= -1;
        }
    }

    private static void generalLogicSetHistory(String formatStringPattern) {
        String currentNumberClear = generalLogicClear(String.format(Locale.getDefault(), "%.10f", currentNumber));
        if (views.getTvHistory().getText().length() == 0) {
            views.getTvHistory().setText(String.format(Locale.getDefault(), formatStringPattern, currentNumberClear));
        } else {
            String history = views.getTvHistory().getText().toString().replaceAll("\\d+", currentNumberClear);
            String result = String.format(Locale.getDefault(), formatStringPattern, history);
            views.getTvHistory().setText(result);
        }
    }

    //endregion

    // region Operation with two parameters
    public static void operationClick(String operation) {
        MathOperations.operation = operation;
        String result = views.getTvResult().getText().toString();
        argument1 = getArgument(result);
        views.getTvHistory().setText(result + " " + operation);
        CalcActivity.needClearRes = true;
    }
    // endregion

    private static double getArgument(String resultText) {
        return Double.parseDouble(resultText.replace(CalculatorButtons.signMinus, "-"));
    }

    public static double equal() throws Exception {
        if (!views.getTvHistory().getText().toString().contains("0 =")) {
            views.getTvHistory().append(" " + generalLogicClear(String.format(Locale.getDefault(), "%.10f", currentNumber)) + " =");
        }
        CalcActivity.needClearAll = true;

        switch (operation) {
            case "+":
                return argument1 + currentNumber;
            case "-":
                return argument1 - currentNumber;
            case "X":
                return argument1 * currentNumber;
            case "÷":
                if (currentNumber == 0) {
                    generalToaster("Can not square root from zero");
                    throw new Exception("Can not square root from zero");
                }
                return argument1 / currentNumber;
            default:
                return 0;
        }
    }

    public static void generalToaster(String message) {
        Toast.makeText(CalcActivity.context, message, Toast.LENGTH_SHORT).show();

        views.getTvHistory().setText(message);
        views.getTvResult().setText("Input error");

        CalcActivity.error = true;
    }

    public static void removeExtraEqualsSign() {
        if (views.getTvHistory().getText().toString().contains("=")) {
            views.getTvHistory().setText(views.getTvHistory().getText().toString().replace("=", ""));
        }
        views.getTvHistory().append("=");
    }

    public static String generalLogicClear(String result) {
        while (result.endsWith("0") || result.endsWith(CalculatorButtons.signComa)) {
            result = result.substring(0, result.length() - 1);
            if (!result.contains(CalculatorButtons.signComa)) break;
            if (result.length() == 1) break;
        }

        return result;
    }
}