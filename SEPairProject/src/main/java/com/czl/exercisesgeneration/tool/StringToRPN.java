package com.czl.exercisesgeneration.tool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static com.czl.exercisesgeneration.tool.StringToBinaryTree.*;

/**
 * 将练习题字符串转化成对应逆波兰表达式的类
 */
public class StringToRPN {

    /**
     * 将存储算式的字符串转化成逆波兰表达式队列
     * @param exercisesString 练习题字符串
     * @return 对应的逆波兰表达式队列
     */
    public static LinkedList<String> tran2RPNinQueue(String exercisesString){
        //先将字符串按照空格分割成操作单元数组
        String[] split = exercisesString.split("\\s+");
        //存储算式转换的逆波兰表达式队列
        Queue<String> RPNQueue = new LinkedList<String>();
        //存储运算符的栈
        Stack<String> operatorStack = new Stack<String>();

        //把分割成的操作单元数组传化为逆波兰表达式队列
        for (String string : split) {
            if(string.matches("^[0-9]+.*")){
                //如果是数，入队
                RPNQueue.offer(string);

                //处理运算符
            } else if(isOperator(string)){
                if("(".equals(string)){
                    //如果是"("，入栈
                    operatorStack.push(string);

                } else if(")".equals(string)){
                    //如果是")"，则将距离上一个"("之间的操作符全部弹出进入逆波兰表达式队列中
                    while(!operatorStack.isEmpty()){
                        if(operatorStack.peek().equals("(")){
                            //找到"("，与")"抵消，弹出
                            operatorStack.pop();
                            break;

                        } else{
                            //将栈中距离上一个"("之间的除括号外的操作符弹出进入逆波兰表达式队列
                            RPNQueue.offer(operatorStack.pop());

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
                            RPNQueue.offer(operatorStack.pop());

                        }else if(isPrior(string, operatorStack.peek())){
                            //如果栈顶优先级小于string，入栈
                            operatorStack.push(string);
                            break;

                        }else{
                            //如果栈顶优先级等于string，将栈顶弹出进入逆波兰表达式队列
                            RPNQueue.offer(operatorStack.pop());

                        }
                    }

                    //当栈空时入栈
                    if(operatorStack.isEmpty())
                        operatorStack.push(string);
                }
            }
        }

        //栈中剩余运算符入队
        while(!operatorStack.isEmpty()){
            RPNQueue.offer(operatorStack.pop());
        }

        return (LinkedList<String>) RPNQueue;
    }

    /**
     * 将存储算式的字符串转化成逆波兰表达式字符串
     * @param exercisesString 练习题字符串
     * @return 对应的逆波兰表达式字符串
     */
    public static String tran2RPNinString(String exercisesString){
        LinkedList<String> RPNQueue = tran2RPNinQueue(exercisesString);
        String RPNString = "";
        for ( String string : RPNQueue ) {
            RPNString = RPNString + string + " ";

        }
        return RPNString;
    }

}
