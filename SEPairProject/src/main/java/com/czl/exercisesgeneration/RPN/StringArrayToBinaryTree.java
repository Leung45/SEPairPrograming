package com.czl.exercisesgeneration.RPN;

/**
 * 提供将练习题字符串数组转化为二叉树的服务的接口
 */
public interface StringArrayToBinaryTree {

    /**
     * 提供将练习题字符串数组转化为二叉树的方法
     * @param exercises 练习题字符串数组
     * @return 二叉树根结点
     */
    public abstract BinaryTreeNode tran2BinaryTree(String[] exercises);
}
