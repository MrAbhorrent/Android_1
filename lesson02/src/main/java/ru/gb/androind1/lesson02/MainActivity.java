package ru.gb.androind1.lesson02;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Constants {

    private static final int REQEST_CODE = 111;
    private LinearLayout layout;

    // Имя настроек
    private static final int MyCoolCodeStyle = 0;
    private static final int AppThemeLightCodeStyle = 1;
    private static final int AppThemeCodeStyle = 2;
    private static final int AppThemeDarkCodeStyle = 3;

    Button btnOne, btnTwo, btnTree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine,
            btnZero, btnAdd, btnMinus, btnMul, btnDiv, btnDot, btnEqual;
    Button[] buttons;

    TextView textView;
    final Presenter presenter = Presenter.getInstance();
    //static String[] isDigit = {"+", "-", "*", "/"};
    static String isDigitSeq = "+-*/";
    String lastSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.AppTheme));

        setContentView(R.layout.activity_main);

        lastSymbol = "";

        textView = findViewById(R.id.tvOperationArea);
        if (savedInstanceState != null) {
            textView.setText(presenter.getOperation());
        } else {
            textView.setText("");
        }


        layout = (LinearLayout) findViewById(R.id.activity_main);

        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnTree = findViewById(R.id.btnTree);
        btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive);
        btnSix = findViewById(R.id.btnSix);
        btnSeven = findViewById(R.id.btnSeven);
        btnEight = findViewById(R.id.btnEight);
        btnNine = findViewById(R.id.btnNine);
        btnZero = findViewById(R.id.btnZero);
        btnAdd = findViewById(R.id.btnAdd);
        btnMinus = findViewById(R.id.btnMinus);
        btnMul = findViewById(R.id.btnMultiply);
        btnDiv = findViewById(R.id.btnDivide);
        btnDot = findViewById(R.id.btnDot);
        btnEqual = findViewById(R.id.btnEqual);
        btnDot.setEnabled(false);

        buttons = new Button[]{btnOne, btnTwo, btnTree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine,
                btnZero, btnAdd, btnMinus, btnMul, btnDiv, btnDot, btnEqual};

        for (Button btn : buttons) {
            btn.setOnClickListener(this);
        }

    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    // Чтение настроек, параметр «тема»
    private int getCodeStyle(int codeStyle) {
        // Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        //Прочитать тему, если настройка не найдена - взять по умолчанию
        return sharedPref.getInt(AppTheme, codeStyle);
    }

    // Сохранение настроек
    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        sharedPref
                .edit()
                .putInt(AppTheme, codeStyle)
                .apply();
    }

    private int codeStyleToStyleId(int codeStyle){
        switch(codeStyle){
            case AppThemeCodeStyle:
                return R.style.AppTheme;
            case AppThemeLightCodeStyle:
                return R.style.AppThemeLight;
            case AppThemeDarkCodeStyle:
                return R.style.AppThemeDark;
            default:
                return R.style.MyCoolStyle;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivityForResult(intent, REQEST_CODE);
        } else if (item.getItemId() == R.id.menu_clear) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.clearDesktop))
                    .setMessage(getString(R.string.textAlertDialogClearText))
                    .setIcon(getResources().getDrawable(R.drawable.ic_baseline_calculate_24))
                    .setPositiveButton(getString(R.string.PositiveButtonText), (dialog, id) -> {
                        textView.setText("");
                        presenter.clearOperation();
                        dialog.dismiss();
                    })
                    .setNegativeButton(getString(R.string.txtCancel), ((dialog, id) -> dialog.dismiss()));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Boolean currTheme;
        if (resultCode == RESULT_OK || requestCode == REQEST_CODE) {
            currTheme = data.getBooleanExtra(selectedTheme,false);
            if (currTheme) {
                setAppTheme(AppThemeDarkCodeStyle);
                setTheme(AppThemeDarkCodeStyle);
                recreate();
            } else {
                setAppTheme(AppThemeLightCodeStyle);
                setTheme(AppThemeLightCodeStyle);
                recreate();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnEqual) {// do parse operation string
            String finalOperationString = textView.getText().toString();
            if (isDigitSeq.contains(lastSymbol) && isDigitSeq.contains(finalOperationString)) {
                Toast.makeText(getApplicationContext(), "Недопустимая операция", Toast.LENGTH_SHORT).show();
            } else {
                textView.setText(finalOperationString + " = " + CalculateOperation(finalOperationString));
                presenter.clearOperation();
            }
        } else {//в текстовое поле добавляем цифру исходя из текста на кнопке
            String addTextString = ((Button) v).getText().toString();
            if ((isDigitSeq.contains(lastSymbol)) && isDigitSeq.contains(addTextString)) {
                Toast.makeText(getApplicationContext(), "Недопустимая операция", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar.make(v, "Фигня", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Close", v1 -> snackbar.dismiss());
                snackbar.show();


            } else {
                presenter.addOperation(addTextString);
                textView.setText(presenter.getOperation());
            }
        }
        lastSymbol = ((Button) v).getText().toString();
    }

    private String CalculateOperation(CharSequence text) {

        //String result = "";
        float result = 0f;
        String operationString = Presenter.getInstance().getOperation();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Строка вычисления")
                .setMessage(operationString)
                .setIcon(getResources().getDrawable(R.drawable.ic_baseline_calculate_24))
                .setPositiveButton(getString(R.string.PositiveButtonText), (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        // Смотрим операции
        // Ищем все операции в строке
        String[] numberOperation = operationString.split("\\*|\\/|\\+|\\-");
        // Ищем все числа в строке
        String[] mathOperation = operationString.split("[^\\*|^\\/|^\\+|^\\-]+");
        if (mathOperation.length > 1) {
            // Получили массив из чисел
            // Теперь надо пробежаться по оригинальной строке и операции деления или умножения
            float[] resultOperation = new float[numberOperation.length];
            String[] tempMathOperation = new String[mathOperation.length];
            for (int i = 1; i < mathOperation.length; i++) {
                if (mathOperation[i].equals("*") | mathOperation[i].equals("/")) {
                    // необходимо произвести операцию "*" или "/"
                    switch (mathOperation[i]) {
                        case "*":
                            resultOperation[i - 1] = Float.valueOf(numberOperation[i - 1]) * Float.valueOf(numberOperation[i]);
                            break;
                        case "/":
                            resultOperation[i - 1] = Float.valueOf(numberOperation[i - 1]) / Float.valueOf(numberOperation[i]);
                    }
                } else {
                    resultOperation[i - 1] = Float.valueOf(numberOperation[i - 1]);
                    tempMathOperation[i] = mathOperation[i];
                }
            }
            resultOperation[mathOperation.length - 1] = Float.valueOf(numberOperation[numberOperation.length - 1]);
            // Пробежали математически операции высшего порядка
            // Теперь необходимо выполнить сложения и вычитания
            result = resultOperation[0];
            for (int i = 1; i < tempMathOperation.length; i++) {
                if (tempMathOperation[i].equals("+")) {
                    result += resultOperation[i];
                } else if (tempMathOperation[i].equals("-")) {
                    result -= resultOperation[i];
                }
            }
            //Snackbar.make(getApplicationContext(), layout, String.format("Результат вычисления = %s", Float.toString(result)), Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getApplicationContext(), layout, "Нечего вычислять", Snackbar.LENGTH_SHORT).show();
        }

        Toast.makeText(getApplicationContext(), "Выполняем вычисление", Toast.LENGTH_SHORT).show();
        return Float.toString(result);
    }
}