package com.czl.exercisesgeneration.RPN.impl;

import com.czl.exercisesgeneration.RPN.BinaryTreeNode;
import com.czl.exercisesgeneration.RPN.StringArrayToBinaryTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class StringArrayToBinaryTreeImpl implements StringArrayToBinaryTree {

    /**
     * 将存储算式的String数组转化成二叉树
     * @param exercises 练习题字符串数组
     * @return 二叉树的根结点
     */
    @Override
    public BinaryTreeNode tran2BinaryTree(String[] exercises) {
        //存储算式转换的逆波兰表达式队列
        Queue<String> rePolishQueue = new LinkedList<String>();

        //存储操作符的栈
        Stack<String> operatorStack = new Stack<String>();

        //把输入的String数组传化为逆波兰表达式队列
        for (String string : exercises) {

            //处理数字
            if(isFigure(string)){
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

        //存储二叉树结点的栈
        Stack<BinaryTreeNode> nodeStack = new Stack<BinaryTreeNode>();

        //把逆波兰表达式队列转化为二叉树
        while(!rePolishQueue.isEmpty()){

            String string = rePolishQueue.poll();
            BinaryTreeNode node = new BinaryTreeNode(string);

            if(isFigure(string)){
                //如果是数字结点，入栈
                nodeStack.push(node);

            } else if(isOperator(string)){
                //如果是操作符结点，则弹出栈顶两个结点作为当前结点的左右结点
                //注意栈中先弹出的要赋值给右结点
                BinaryTreeNode rightNode = nodeStack.pop();
                BinaryTreeNode leftNode = nodeStack.pop();
                node.setlNode(leftNode);
                node.setrNode(rightNode);

                //入栈
                nodeStack.push(node);

            }
        }

        //返回二叉树根结点
        return nodeStack.pop();
    }

    /**
     * 数字判定
     * @param string
     * @return
     */
    public static boolean isFigure(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i)))
                return false;
        }
        return true;
    }
    
    /**
     * 运算符判定
     * @param string
     * @return
     */
    public static boolean isOperator(String string) {
        if ("(".equals(string) || ")".equals(string) || "+".equals(string) || "-".equals(string) || "*".equals(string) || "÷".equals(string))
            return true;
        else
            return false;
    }

    /**
     * 运算符优先级判断
     * @param string1
     * @param string2
     * @return 当 string1 > string2 时返回true，否则返回false
     */
    public static boolean isPrior(String string1, String string2) {
        if (ofPriority(string1) > ofPriority(string2)) 
            return true;
        else 
            return false;
    }

    /**
     * 获取运算符优先级
     * @param string
     * @return 运算符优先级
     */
    public static int ofPriority(String string) {
        if ("+".equals(string) || "-".equals(string)) 
            return 1;
        else if ("*".equals(string) || "÷".equals(string))
            return 2;
        else 
            throw new IllegalArgumentException("The exercises contain some undefined operators.");
    }

    /**
     * 利用中序遍历打印出原算式
     * @param binaryTreeNode 算式二叉树根结点
     */
    public static void showOriginExercises(BinaryTreeNode binaryTreeNode){

        if(binaryTreeNode != null){
            if(isOperator(binaryTreeNode.getValue()))
                System.out.print("(");

            showOriginExercises(binaryTreeNode.getlNode());
            System.out.print(binaryTreeNode.getValue());
            showOriginExercises(binaryTreeNode.getrNode());

            if(isOperator(binaryTreeNode.getValue()))
                System.out.print(")");
        }
    }
}