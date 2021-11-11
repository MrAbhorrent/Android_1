package ru.gb.androind1.lesson02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOne, btnTwo, btnTree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine,
            btnZero, btnAdd, btnMinus, btnMul, btnDiv, btnDot, btnEqual;
    Button[] buttons;

    TextView textView;
    final Presenter presenter = Presenter.getInstance();
    static String[] isDigit = {"+", "-", "*", "/"};
    static String isDigitSeq = "+-*/";
    String lastSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastSymbol = "";

        textView = findViewById(R.id.tvOperationArea);
        if (savedInstanceState != null) {
            textView.setText(presenter.getOperation());
        } else {
            textView.setText("");
        }

        btnOne = findViewById(R.id.btnOne); btnTwo = findViewById(R.id.btnTwo);
        btnTree = findViewById(R.id.btnTree); btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive); btnSix = findViewById(R.id.btnSix);
        btnSeven = findViewById(R.id.btnSeven); btnEight = findViewById(R.id.btnEight);
        btnNine = findViewById(R.id.btnNine); btnZero = findViewById(R.id.btnZero);
        btnAdd = findViewById(R.id.btnAdd); btnMinus = findViewById(R.id.btnMinus);
        btnMul = findViewById(R.id.btnMultiply); btnDiv = findViewById(R.id.btnDivide);
        btnDot = findViewById(R.id.btnDot); btnEqual = findViewById(R.id.btnEqual);
        btnDot.setEnabled(false);
        buttons = new Button[] {btnOne, btnTwo, btnTree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine,
                btnZero, btnAdd, btnMinus, btnMul, btnDiv, btnDot, btnEqual};

        for (Button btn: buttons) {
            btn.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnEqual:
                // do parse operation string
                String finalOperationString = textView.getText().toString();
                textView.setText(finalOperationString + CalculateOperation(finalOperationString));
                presenter.clearOperation();
                break;
            default:
                //в текстовое поле добавляем цифру исходя из текста на кнопке
                String addTextString = ((Button) v).getText().toString();
                if ((isDigitSeq.contains(lastSymbol)) && isDigitSeq.contains(addTextString)) {
                    Toast.makeText(getApplicationContext(),"Недопустимая операция", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.addOperation(addTextString);
                    textView.setText(presenter.getOperation());
                }
        }
        lastSymbol = ((Button) v).getText().toString();
    }

    private String CalculateOperation(CharSequence text) {

        String result = "";

        Toast.makeText(getApplicationContext(),"Выполняем вычисление", Toast.LENGTH_SHORT).show();
        return result;
    }
}