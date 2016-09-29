package com.example.lkj.calculator;



import java.util.ArrayDeque;

import java.util.Deque;

import java.util.Stack;

/**
 * Created by lkj on 9/28/16.
 */
public class PostfixConverter {

    private String infix;    // The infix expression to be converted
    private Deque<String> stack = new ArrayDeque<>();
    private Stack<String> postfix = new Stack<>();   // TO hold the postfix expression

    private String LOG_TAG = PostfixConverter.class.getSimpleName();

    public PostfixConverter(String expression)
    {
        infix = expression;
        convertExpression();

    }

    // If it's a number, push it to the postfix list
    // else if it's an operator, push it to the stack
    private void convertExpression()
    {

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < infix.length(); i++)
        {
            char c = infix.charAt(i);

            if (Character.isDigit(c) || c == '.')
            {
                builder.append(c);
                if (i == infix.length()-1 ||
                        !Character.isDigit(infix.charAt(i+1)) && infix.charAt(i+1) != '.')
                {
                    postfix.add(builder.toString());
                    builder.delete(0, builder.length());
                }

            }

            // else, the token is an operator
            else
            {
                inputToStack(c+"");
            }


        }
        clearStack();
    }

    private void inputToStack(String operator)
    {
        if(stack.isEmpty() || operator.equals("("))
            stack.addLast(operator);

        else
        {
            if(operator.equals(")"))
            {
                while (!stack.getLast().equals("("))
                {
                    postfix.add(stack.removeLast());
                }
                stack.removeLast();
            }

            else
            {
                if(stack.getLast().equals("("))
                    stack.addLast(operator);
                else
                {
                    while (!stack.isEmpty() &&
                            !stack.getLast().equals("(") &&
                            getPrecedence(operator) <= getPrecedence(stack.getLast()))
                    {

                        postfix.add(stack.removeLast());
                    }
                    stack.addLast(operator);
                }
            }
        }


    }

    private int getPrecedence(String operator)
    {
        if (operator.equals("+") || operator.equals("-"))
            return 1;
        if (operator.equals("*") || operator.equals("/"))
            return 2;
        if (operator.equals("^"))
            return 3;
        return 0;
    }

    private void clearStack()
    {
        while(!stack.isEmpty())
        {
            postfix.add(stack.removeLast());
        }
    }

    public Stack<String> getPostfix()
    {
        return postfix;
    }
}
