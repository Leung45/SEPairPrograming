package com.czl.exercisesgeneration.service;

/**
 * 计算服务接口
 */
public interface Calculation {

    /**
     * 计算算式结果
     * @param string 算式对应的逆波兰表达式
     * @return 算式结果
     */
    public abstract String calculate(String string);
}
