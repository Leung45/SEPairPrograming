package com.czl.exercisesgeneration.RPN;

/**
 * 二叉树结点类
 */
public class BinaryTreeNode {
    private String value;
    private BinaryTreeNode lNode;
    private BinaryTreeNode rNode;

    public BinaryTreeNode() {
    }

    public BinaryTreeNode(String value) {
        this.value = value;
        this.lNode = null;
        this.rNode = null;
    }

    public BinaryTreeNode(String value, BinaryTreeNode lNode, BinaryTreeNode rNode) {
        this.value = value;
        this.lNode = lNode;
        this.rNode = rNode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BinaryTreeNode getlNode() {
        return lNode;
    }

    public void setlNode(BinaryTreeNode lNode) {
        this.lNode = lNode;
    }

    public BinaryTreeNode getrNode() {
        return rNode;
    }

    public void setrNode(BinaryTreeNode rNode) {
        this.rNode = rNode;
    }
}
