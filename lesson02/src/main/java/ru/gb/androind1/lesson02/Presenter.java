package ru.gb.androind1.lesson02;

import android.app.Presentation;

public final class Presenter {

    private static final Presenter instance = new Presenter();

    // Храним строку которая была введена в окно калькулятора
    private String operation;

    private Presenter() {
        operation = "";
    }

    public void addOperation(String simbol) {
        operation = operation + simbol;
    }

    public String getOperation() {
        return operation;
    }

    public void clearOperation() {
        operation = "";
    }

    public static Presenter getInstance() { return instance;}
}
