package com.czl.exercisesgeneration.tool;

import com.czl.exercisesgeneration.relevantclass.BinaryTreeNode;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 将练习题字符串转化成对应二叉树的类
 */
public class StringToBinaryTree {
    
    /**
     * 将存储算式的字符串转化成二叉树
     * @param exercises 练习题字符串
     * @return 对应二叉树的根结点
     */
    public static BinaryTreeNode tran2BinaryTree(String exercises) {
        //用于转化二叉树操作的栈
        Stack<BinaryTreeNode> nodeStack = new Stack<BinaryTreeNode>();
        //获取算式的逆波兰表达式
        LinkedList<String> RPNQueue = StringToRPN.tran2RPNinQueue(exercises);

        //把逆波兰表达式队列转化为二叉树
        while(!RPNQueue.isEmpty()){
            //弹出队首元素新建结点
            String string = RPNQueue.poll();
            BinaryTreeNode node = new BinaryTreeNode(string);

            if (isOperator(string)){
                //如果是操作符结点，则弹出栈顶两个结点作为当前结点的左右结点
                //注意栈中先弹出的要赋值给右结点
                BinaryTreeNode rightNode = nodeStack.pop();
                BinaryTreeNode leftNode = nodeStack.pop();
                node.setlNode(leftNode);
                node.setrNode(rightNode);

                //入栈
                nodeStack.push(node);
            }else {
                //如果是数字结点，入栈
                nodeStack.push(node);
            }
        }

        //返回二叉树根结点
        return nodeStack.pop();
    }
    
    /**
     * 运算符判定
     * @param string
     * @return 判断结果
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

}