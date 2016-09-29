package com.example.lkj.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        displayResult(result+"");
        enableLeftParenthesis();
    }

    public void registerKey(View view) throws Exception {
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

    private void displayResult(String result)
    {
        TextView tvResult = (TextView) findViewById(R.id.textResult);
        tvResult.setText(result);
    }

    private void operand(String operand)
    {
        if(value.equals("0") && !operand.equals("."))
            value = operand;
        else
            value += operand;


        enableEqual();

        // if the leftParen is zero, the right paren will still not enable..
        enableRightParenthesis();
        enableOperator(true);
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
        enableLeftParenthesis();
        enableOperator(false);
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
            enableOperator(false);
        }


        else
        {
            leftParenthesis--;
            enableOperator(true);
            if(leftParenthesis == 0)
            {
                disableRightParenthesis();
                enableEqual();
            }

        }


    }

    public void calculate() throws Exception
    {
        try
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
            displayResult(result+"");
            enableOperator(true);
            Log.v(LOG_TAG, result + "");
        }
        catch (Exception e)
        {
            displayResult("Bad Expression");

        }

    }



    private void delete()
    {
        String last = value.substring(value.length()-1);
        Log.d(LOG_TAG, "deleted :" + last);
        if(last.equals("("))
        {
            leftParenthesis--;
            enableLeftParenthesis();
        }
        else if(last.equals(")"))
        {
            leftParenthesis++;
            enableRightParenthesis();
        }

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


    private void enableLeftParenthesis()
    {
        Button left = (Button) findViewById(R.id.buttonLeftParenthesis);
        left.setEnabled(true);

        Button right = (Button) findViewById(R.id.buttonRightParenthesis);
        right.setEnabled(false);

        disableEqual();

    }


    private void enableRightParenthesis()
    {
        Button right = (Button) findViewById(R.id.buttonRightParenthesis);
        if (leftParenthesis != 0)
            right.setEnabled(true);

        Button left = (Button) findViewById(R.id.buttonLeftParenthesis);
        left.setEnabled(false);


    }

    private void disableRightParenthesis()
    {
        Button right = (Button) findViewById(R.id.buttonRightParenthesis);
        right.setEnabled(false);
    }

    private void enableOperator(boolean enable)
    {
        Button add = (Button)findViewById(R.id.buttonAdd);
        Button sub = (Button)findViewById(R.id.buttonSub);
        Button mul = (Button)findViewById(R.id.buttonMul);
        Button div = (Button)findViewById(R.id.buttonDiv);
        add.setEnabled(enable);
        sub.setEnabled(enable);
        mul.setEnabled(enable);
        div.setEnabled(enable);
    }





}
