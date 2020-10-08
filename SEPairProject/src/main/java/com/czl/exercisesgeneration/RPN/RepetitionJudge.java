package com.czl.exercisesgeneration.RPN;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class RepetitionJudge {

    public static int count = 0;

    static class TreeNode {
        TreeNode leftChild;
        TreeNode rightChild;
        String value;
    }

    /**
     * 将算术表达式转化成二叉树
     *
     * @param expression
     *            为了方便，使用字符串数组来存储表达式
     * @return 二叉树的根节点
     */
    public static TreeNode createBinaryTree(String[] expression) {

        // 存储操作符的栈
        Stack<String> opStack = new Stack<String>();
        // 存储转换后的逆波兰式的队列
        Queue<String> reversePolish = new LinkedList<String>();

        for (String s : expression) {

            // 如果是数字
            if(isDigit(s)){

                reversePolish.offer(s);
                // 如果是操作符
            } else if(isOperator(s)){

                //是左括号直接入栈
                if("(".equals(s)){

                    opStack.push(s);
                    // 如果是右括号
                } else if(")".equals(s)){

                    // 把离上一个“（”之间的操作符全部弹出来放入逆波兰式的队列中
                    while(!opStack.isEmpty()){

                        String op = opStack.peek();
                        if(op.equals("(")){

                            opStack.pop();
                            break;

                        } else{//栈中离上一个“（”之间的除括号外的操作符入队列

                            reversePolish.offer(opStack.pop());
                        }
                    }
                } else{//处理除括号外的操作符

                    while(!opStack.isEmpty()){
                        // 如果栈顶元素为"("直接入栈
                        if("(".equals(opStack.peek())){

                            opStack.push(s);
                            break;
                            //如果栈顶元素优先级大于s
                        }else if(isGreat(opStack.peek(), s)){

                            reversePolish.offer(opStack.pop());

                        }else if(isGreat(s, opStack.peek())){

                            opStack.push(s);
                            break;
                        }else{
                            reversePolish.offer(opStack.pop());
                        }

                    }
                    // 如果栈为空，直接入栈
                    if(opStack.isEmpty())

                        opStack.push(s);
                }
            }
        }
        // 将剩余的操作符入队
        while(!opStack.isEmpty()){

            reversePolish.offer(opStack.pop());
        }
        Stack<TreeNode> nodeStack = new Stack<TreeNode>();
        // 将逆波兰式转化成二叉树
        while(!reversePolish.isEmpty()){

            String s = reversePolish.poll();
            // 以当前的元素的值新建一个节点
            TreeNode node = new TreeNode();
            node.value = s;
            // 如果是数字
            if(isDigit(s)){

                nodeStack.push(node);
                // 如果是操作符
            } else if(isOperator(s)){

                //从栈里弹出两个节点作为当前节点的左右子节点
                TreeNode rightNode = nodeStack.pop();
                TreeNode leftNode = nodeStack.pop();
                node.leftChild = leftNode;
                node.rightChild = rightNode;
                // 入栈
                nodeStack.push(node);
            }

        }

        return nodeStack.pop();


    }

    /**
     * 判断是否为运算符（暂时只判断四则运算的运算符）
     *
     * @param s
     * @return
     */
    static boolean isOperator(String s) {

        if ("(".equals(s) || ")".equals(s) || "+".equals(s) || "-".equals(s)
                || "*".equals(s) || "/".equals(s))

            return true;

        else

            return false;
    }

    /**
     * 判断是否为数字
     *
     * @param s
     * @return
     */
    static boolean isDigit(String s) {

        for (int i = 0; i < s.length(); i++) {

            if (!Character.isDigit(s.charAt(i)))

                return false;
        }

        return true;
    }

    /**
     * 判断op1和op2的优先级，如果op1>op2，返回true，如果op1<=op2，返回false
     *
     * @param op1
     * @param op2
     * @return
     */
    static boolean isGreat(String op1, String op2) {

        if (getPriority(op1) > getPriority(op2))

            return true;

        else

            return false;
    }

    /**
     * 获取运算符的优先级
     *
     * @param op
     * @return
     */
    static int getPriority(String op) {

        if ("+".equals(op) || "-".equals(op))

            return 1;

        else if ("*".equals(op) || "/".equals(op))

            return 2;

        else

            throw new IllegalArgumentException("Unsupported operator!");
    }

    /**
     * 打印出还原的算术表达式
     * @param root
     */
    static void printMathExpression(TreeNode root){

        if(root != null){
            if(isOperator(root.value))
                System.out.print("(");
            printMathExpression(root.leftChild);
            System.out.print(root.value);
            printMathExpression(root.rightChild);
            if(isOperator(root.value))
                System.out.print(")");
        }
    }

    public static void main(String[] args) {

        TreeNode root1 = createBinaryTree(new String[]{"1", "+", "2", "+", "3"});

        TreeNode root2 = createBinaryTree(new String[]{"3", "+", "2", "+", "1"});

        printMathExpression(root1);
        printMathExpression(root2);

        System.out.println(cmp2(root1,root2));
    }

    public static boolean cmp2(TreeNode nodea, TreeNode nodeb) {
        if (nodea == null && nodeb == null)
            return true;
        if (nodea == null && nodeb != null) {
            return false;
        }
        if (nodea != null && nodeb == null) {
            return false;
        }
        if (nodea.value != nodeb.value)
            return false;

        return cmp2(nodea.leftChild, nodeb.leftChild) && cmp2(nodea.rightChild, nodeb.rightChild)
                ||cmp2(nodea.leftChild, nodeb.rightChild) && cmp2(nodea.rightChild, nodeb.leftChild);

    }

    public static boolean cmp3(TreeNode nodea,TreeNode nodeb){
        if (nodea == null && nodeb == null)
            return true;
        else if ((nodea != null) && (nodeb != null)){
            if (nodea.value == nodeb.value){
                if ((cmp3(nodea.leftChild,nodeb.rightChild)) && cmp3(nodea.rightChild,nodeb.leftChild) || cmp3(nodea.leftChild,nodeb.leftChild) && cmp3(nodea.rightChild,nodeb.rightChild))
                    return true;
            }
        }
        return false;
    }

}