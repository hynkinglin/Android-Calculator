package com.example.lkj.calculator;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Created by lkj on 9/28/16.
 */
public class PostfixCalculator {
    public String LOG_TAG = PostfixCalculator.class.getSimpleName();
    private Stack<String> expression = new Stack<>();
    private Deque<Double> stack = new ArrayDeque<>();

    public PostfixCalculator(Stack<String> postfix)
    {
        expression = postfix;
    }

    public double result()
    {

        for (int i = 0; i < expression.size(); i++)
        {
            String s = expression.get(i);
            Log.d(LOG_TAG, "expression: " + expression.toString());
            Log.d(LOG_TAG, "position " + i + ": " + s);
            // Determine whether current element is digit or not
            if(Character.isDigit(s.charAt(0)) || s.equals("."))
            {
                 stack.addLast(Double.parseDouble(s));
            }


            else
            {
                double leftRes,rightRes;
                rightRes = stack.removeLast();
                leftRes = stack.removeLast();
                Log.d(LOG_TAG, "left:" + leftRes + " right: " + rightRes);

                double tempResult = 0;
                String operator = expression.get(i);

                if(operator.equals("+"))
                {
                    tempResult = leftRes + rightRes;
                }

                else if (operator.equals("-"))
                {
                    tempResult = leftRes - rightRes;
                }

                else if (operator.equals("*"))
                {
                    tempResult = leftRes * rightRes;
                }

                else if (operator.equals("/"))
                {
                    tempResult = leftRes / rightRes;
                }

                stack.addLast(tempResult);
                Log.d(LOG_TAG, "tempResult: " + tempResult);
            }
        }
        return  stack.pop();
        //return new BigDecimal(stack.removeLast()).setScale(3, BigDecimal.ROUND_HALF_UP);
    }
}
