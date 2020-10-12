package com.czl.exercisesgeneration.relevantclass;

/**
 * 操作数类
 */
public class Operand {
    //整数部分
    private Integer roundPart;
    //分数部分
    private String fractionalPart = "none";

    /**
     * 如果操作数是整数，返回操作数本身形式
     * 如果操作数是分数，返回操作数的真分数形式，用于输出到文件上
     * @return 操作数(真分数形式)
     */
    public String getProFrac(){
        //整数
        if (fractionalPart.equals("none")) 
            return roundPart+"";
        
        //真分数
        return roundPart+"'"+fractionalPart;
    }

    /**
     * 如果操作数是整数，返回操作数本身形式
     * 如果操作数是分数，返回操作数的假分数形式，用于计算
     * @return 操作数(假分数形式)
     */
    public String getImproFrac(){
        //整数
        if (fractionalPart.equals("none")) 
            return roundPart+"";
        
        //转换为假分数
        String[] strings = fractionalPart.split("/");
        //分母
        int denominator = Integer.parseInt(strings[1]);
        //分子
        int numerator = Integer.parseInt(strings[0]);

        numerator = (roundPart * denominator) + numerator;
        
        //假分数
        return numerator+"/"+denominator;
    }

    public Integer getRoundPart() {
        return roundPart;
    }

    public void setRoundPart(Integer roundPart) {
        this.roundPart = roundPart;
    }

    public String getFractionalPart() {
        return fractionalPart;
    }

    public void setFractionalPart(String fractionalPart) {
        this.fractionalPart = fractionalPart;
    }
}
