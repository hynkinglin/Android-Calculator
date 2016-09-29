package com.example.lkj.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    public static String value = "0";
    public double result = 0;

    private int leftParenthesis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
        value = "0";
        result = 0;
        leftParenthesis = 0;
        displayExpression();
        displayResult(result);
        enableLeftParenthesis();
        disableRightParenthesis();
    }

    public void registerKey(View view)
    {
        int itemId = view.getId();
        if (itemId == R.id.button0)
            operand("0");
        if(itemId == R.id.button1)
            operand("1");
        if(itemId == R.id.button2)
            operand("2");
        if(itemId == R.id.button3)
            operand("3");
        if(itemId == R.id.button4)
            operand("4");
        if (itemId == R.id.button5)
            operand("5");
        if(itemId == R.id.button6)
            operand("6");
        if(itemId == R.id.button7)
            operand("7");
        if (itemId == R.id.button8)
            operand("8");
        if(itemId == R.id.button9)
            operand("9");
        if (itemId == R.id.buttonDot)
            operand(".");
        if (itemId == R.id.buttonAdd)
            operator("+");
        if (itemId == R.id.buttonSub)
            operator("-");
        if(itemId == R.id.buttonMul)
            operator("*");
        if(itemId == R.id.buttonDiv)
            operator("/");
        if (itemId == R.id.buttonDel)
            delete();
        if (itemId == R.id.buttonEql)
            calculate();

        if(itemId == R.id.buttonLeftParenthesis)
            parenthesis("(");
        if (itemId == R.id.buttonRightParenthesis)
            parenthesis(")");

        if (itemId == R.id.buttonClr)
            init();

        displayExpression();
    }


    private void displayExpression()
    {
        TextView expression = (TextView) findViewById(R.id.textExpression);
        expression.setText(value);
    }

    private void displayResult(double result)
    {
        TextView tvResult = (TextView) findViewById(R.id.textResult);
        tvResult.setText(result+"");
    }

    private void operand(String operand)
    {
        if(value.equals("0") && operand != ".")
            value = operand;
        else
            value += operand;


        enableEqual();
        disableLeftParenthesis();
        if (leftParenthesis != 0)
        {
            enableRightParenthesis();
        }
    }

    private void operator(String operator)
    {
        if (endsWithOperator())  // Avoid double operators by replacing operator.
        {
            delete();
            value += operator;
        }
        else  // Safe to place operator right away.
        {
            value += operator;
        }
        disableEqual();
        disableRightParenthesis();
        enableLeftParenthesis();
        // else: Operator by itself is an illegal operation, do not place operator.
    }

    private void parenthesis(String parenthesis)
    {
        if(value.equals("0"))
            value = "(";
        else
            value += parenthesis;
        if(parenthesis.equals("("))
        {
            disableEqual();
            leftParenthesis++;
        }


        else
        {
            enableEqual();
            leftParenthesis--;
            if(leftParenthesis == 0)
                disableRightParenthesis();
        }

    }

    public void calculate()
    {
        if (value.equals("0"))
        {
            return;
        }
        PostfixConverter converter = new PostfixConverter(value);
        for (String s : converter.getPostfix())
        {
            Log.d(LOG_TAG, s);
        }

        PostfixCalculator calculator = new PostfixCalculator(converter.getPostfix());
        // BigDecimal result = calculator.result();
        result = calculator.result();
        int i = (int) result;
        if (result == i)
            displayResult(i);
        else
            displayResult(result);
        Log.v(LOG_TAG, result + "");
    }



    private void delete()
    {
        if (value.length() > 1)
        {
            value = value.substring(0, value.length()-1);
        }

        else if (value.length() == 1)
        {
            value = "0";
        }
    }


    private boolean endsWithOperator()
    {
        return (value.endsWith("+") || value.endsWith("-") || value.endsWith("*") || value.endsWith("/"));

    }

    private void disableEqual()
    {
        Button equal = (Button) findViewById(R.id.buttonEql);
        equal.setEnabled(false);
    }

    private void enableEqual()
    {
        Button equal = (Button) findViewById(R.id.buttonEql);
        equal.setEnabled(true);
    }

    private void disableLeftParenthesis()
    {
        Button left = (Button) findViewById(R.id.buttonLeftParenthesis);
        left.setEnabled(false);

    }

    private void enableLeftParenthesis()
    {
        Button left = (Button) findViewById(R.id.buttonLeftParenthesis);
        left.setEnabled(true);
        disableEqual();

    }

    private void disableRightParenthesis()
    {
        Button right = (Button) findViewById(R.id.buttonRightParenthesis);
        right.setEnabled(false);
    }

    private void enableRightParenthesis()
    {
        Button right = (Button) findViewById(R.id.buttonRightParenthesis);
        right.setEnabled(true);
    }





}
