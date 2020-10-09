package com.czl.exercisesgeneration.RPN;

/**
 * 提供题目重复判定服务的接口
 */
public interface RepetitionJudge {

    /**
     * 判定两棵树是否同构
     * @param tree1 题目一的二叉树根结点
     * @param tree2 题目二的二叉树根结点
     * @return 判断结果
     */
    public abstract boolean isRepetitive(BinaryTreeNode tree1, BinaryTreeNode tree2);
}
