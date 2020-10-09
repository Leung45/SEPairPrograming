package com.czl.exercisesgeneration.RPN.impl;

import com.czl.exercisesgeneration.RPN.BinaryTreeNode;
import com.czl.exercisesgeneration.RPN.RepetitionJudge;

public class RepetitionJudgeImpl implements RepetitionJudge {

    /**
     * 利用树的同构原理，判断两棵树是否同构，即两个题目是否重复
     * @param tree1 题目一的二叉树根结点
     * @param tree2 题目二的二叉树根结点
     * @return 判断结果
     */
    @Override
    public boolean isRepetitive(BinaryTreeNode tree1, BinaryTreeNode tree2) {
        if (tree1 == null && tree2 == null)
            return true;
        if (tree1 == null && tree2 != null) {
            return false;
        }
        if (tree1 != null && tree2 == null) {
            return false;
        }
        if (tree1.getValue() != tree2.getValue())
            return false;

        return isRepetitive(tree1.getlNode(), tree2.getlNode()) && isRepetitive(tree1.getrNode(), tree2.getrNode())
                || isRepetitive(tree1.getlNode(), tree2.getrNode()) && isRepetitive(tree1.getrNode(), tree2.getlNode());
    }
}
