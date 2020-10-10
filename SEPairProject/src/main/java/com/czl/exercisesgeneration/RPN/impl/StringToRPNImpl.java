package com.czl.exercisesgeneration.RPN.impl;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.czl.exercisesgeneration.RPN.impl.StringArrayToBinaryTreeImpl.*;

public class StringToRPNImpl {
    public static LinkedList<String> tran2RPNinQueue(String exercisesString){
        String[] exercises = exercisesString.split("\\s+");


        //存储算式转换的逆波兰表达式队列
        Queue<String> rePolishQueue = new LinkedList<String>();

        //存储操作符的栈
        Stack<String> operatorStack = new Stack<String>();

        //把输入的String数组传化为逆波兰表达式队列
        for (String string : exercises) {

            //处理数字
//            if(isFigure(string)){
            if(string.matches("^[0-9]+.*")){
                //如果是数字，入队
                rePolishQueue.offer(string);

                //处理操作符
            } else if(isOperator(string)){
                if("(".equals(string)){
                    //如果是"("，入栈
                    operatorStack.push(string);

                } else if(")".equals(string)){
                    //如果是")"，则将距离上一个"("之间的操作符全部弹出进入逆波兰表达式队列中
                    while(!operatorStack.isEmpty()){
                        String op = operatorStack.peek();

                        if(op.equals("(")){
                            //找到"("，与")"抵消，弹出
                            operatorStack.pop();
                            break;

                        } else{
                            //将栈中距离上一个"("之间的除括号外的操作符弹出进入逆波兰表达式队列
                            rePolishQueue.offer(operatorStack.pop());

                        }
                    }

                    //处理除括号外的操作符
                } else{

                    while(!operatorStack.isEmpty()){
                        if("(".equals(operatorStack.peek())){
                            //如果栈顶是"("，入栈
                            operatorStack.push(string);
                            break;

                        }else if(isPrior(operatorStack.peek(), string)){
                            //如果栈顶优先级大于string，将栈顶弹出进入逆波兰表达式队列
                            rePolishQueue.offer(operatorStack.pop());

                        }else if(isPrior(string, operatorStack.peek())){
                            //如果栈顶优先级小于string，入栈
                            operatorStack.push(string);
                            break;

                        }else{
                            //如果栈顶优先级等于string，将栈顶弹出进入逆波兰表达式队列
                            rePolishQueue.offer(operatorStack.pop());

                        }
                    }

                    //如果栈空，入栈
                    if(operatorStack.isEmpty())
                        operatorStack.push(string);
                }
            }
        }

        //栈中剩余操作符入队
        while(!operatorStack.isEmpty()){
            rePolishQueue.offer(operatorStack.pop());
        }

        return (LinkedList<String>) rePolishQueue;
    }

    public static String tran2RPNinString(String exercisesString){
        LinkedList<String> rePolishQueue = tran2RPNinQueue(exercisesString);
        String rePolishString = "";
        for ( String string : rePolishQueue ) {
            rePolishString = rePolishString + string + " ";

        }
        return rePolishString;
    }

    @Test
    public void testtranrpn(){
        String s = tran2RPNinString("( " + "2/23" + " + " + "5" + " )" + " * " + "( " + "6" + " - " + "1" + " )");
        System.out.println(s);
        String[] strings = s.split("\\s+");
        for (String str:strings) {
            System.out.println(str);
        }
    }

    @Test
    public void showww(){
        System.out.println("hah");
    }
}
