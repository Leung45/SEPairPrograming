package com.czl.exercisesgeneration.service;

import com.czl.exercisesgeneration.relevantclass.BinaryTreeNode;
import com.czl.exercisesgeneration.service.impl.CalculationImpl;
import com.czl.exercisesgeneration.service.impl.FileOperationImpl;
import com.czl.exercisesgeneration.tool.RepetitionJudge;
import com.czl.exercisesgeneration.tool.StringToBinaryTree;
import com.czl.exercisesgeneration.tool.StringToRPN;
import com.czl.exercisesgeneration.relevantclass.Operand;

import java.io.BufferedWriter;
import java.util.*;

/**
 * 随机生成题目的类
 */
public class GenerateExercises {
    //存储生成的题目及其答案的Map，其中key是题目，value是答案
    private static HashMap<String,String> exerciseMap = new HashMap<String,String>();
    //四则运算符
    private static final String[] operators = {" + "," - "," * "," ÷ "};
    //括号
    private static final String[] bracket = {"( "," )"};

    /**
     * 生成题目，写入到指定文件中
     * @param num 题目数量
     * @param range 数值范围
     * @throws Exception
     */
    public static void generateExercises(int num,int range) throws Exception {
        //获取题目及答案存储文件
        FileOperationImpl fileOperation = new FileOperationImpl();
        BufferedWriter exercises = fileOperation.getBufferedWriter("./Exercises.txt");
        BufferedWriter answers = fileOperation.getBufferedWriter("./Answers.txt");

        for (int i = 0; i < num; i++) {
            loop:
            while (true){
                //生成一道题目
                String[] strings = getOneExercise(range);
                //计算题目结果
                String polandString = StringToRPN.tran2RPNinString(strings[0]);
                String result = new CalculationImpl().calculate(polandString);

                //不符要求的结果，跳过
                if (result.equals("invalid")){
                    continue;
                }

                //判断题目是否重复
                if (exerciseMap.containsValue(result)){
                    //如果在Map中发现答案相同的题目，将所有与当前生成题目答案相同的题目存储到一个集合中
                    ArrayList<String> arrayList = getKey(exerciseMap,result);
                    //将当前生成题目转化为二叉树表示形式
                    BinaryTreeNode treeNode = StringToBinaryTree.tran2BinaryTree(strings[0]);

                    //将当前生成题目与答案相同的题集一一利用二叉树的同构判断题目是否重复
                    for ( String eachExercise : arrayList ) {
                        if (RepetitionJudge.isRepetitive(treeNode,StringToBinaryTree.tran2BinaryTree(eachExercise))){
                            //如果两棵二叉树同构，即两题重复了，跳过当前该生成题目，重新生成题目
                            continue loop;
                        }
                    }
                }

                //题目结果符合要求，且不与之前生成的题目重复，添加到Map集合中
                exerciseMap.put(strings[0],result);

                //写入题目
                exercises.write(i+1+". "+strings[1]);
                exercises.newLine();
                //写入答案
                answers.write(i+1+". "+result);
                answers.newLine();

                break;

            }
        }

        //释放资源
        exercises.close();
        answers.close();
    }

    /**
     * 生成一道四则运算题目
     * @param range 数值范围
     * @return 一道四则运算题目
     */
    public static String[] getOneExercise(int range){
        //exercise[0]存储题目假分数形式，便于计算；exercise[1]存储题目真分数形式，是写入文件的要求格式
        String[] exercise = new String[2];
        //随机决定题目是否包含括号，1：有，0：无
        int containBracket = (int) Math.round(Math.random());
        //随机生成1-3个四则运算符
        int operNum = (int) Math.floor((Math.random()*3)+1.0);
        //一道题目的操作数集合
        Operand[] ops = getOperandCollection(operNum+1,range);
        //一道题目的运算符集合
        String[] strings = getOperatorCollection(operNum);

        //如果运算符为1个
        if (operNum == 1){
            //假分数形式
            exercise[0] = ops[0].getImproFrac()+strings[0]+ops[1].getImproFrac();
            //真分数形式
            exercise[1] = ops[0].getProFrac()+strings[0]+ops[1].getProFrac();

            //运算符大于1个
        }else {
            //如果题目包含括号
            if (containBracket == 1){
                //如果运算符为3个
                if (operNum == 3){
                    int choose = (int) Math.floor(Math.random()*10);
                    switch (choose){
                        case 0:
                            exercise[0] = bracket[0]+ops[0].getImproFrac()+strings[0]+ops[1].getImproFrac()+strings[1]+ops[2].getImproFrac()+bracket[1]+strings[2]+ops[3].getImproFrac();
                            exercise[1] = bracket[0]+ops[0].getProFrac()+strings[0]+ops[1].getProFrac()+strings[1]+ops[2].getProFrac()+bracket[1]+strings[2]+ops[3].getProFrac();
                            break;
                        case 1:
                            exercise[0] = ops[0].getImproFrac()+strings[0]+bracket[0]+ops[1].getImproFrac()+strings[1]+ops[2].getImproFrac()+strings[2]+ops[3].getImproFrac()+bracket[1];
                            exercise[1] = ops[0].getProFrac()+strings[0]+bracket[0]+ops[1].getProFrac()+strings[1]+ops[2].getProFrac()+strings[2]+ops[3].getProFrac()+bracket[1];
                            break;
                        case 2:
                            exercise[0] = bracket[0]+ops[0].getImproFrac()+strings[0]+ops[1].getImproFrac()+bracket[1]+strings[1]+ops[2].getImproFrac()+strings[2]+ops[3].getImproFrac();
                            exercise[1] = bracket[0]+ops[0].getProFrac()+strings[0]+ops[1].getProFrac()+bracket[1]+strings[1]+ops[2].getProFrac()+strings[2]+ops[3].getProFrac();
                            break;
                        case 3:
                            exercise[0] = ops[0].getImproFrac()+strings[0]+bracket[0]+ops[1].getImproFrac()+strings[1]+ops[2].getImproFrac()+bracket[1]+strings[2]+ops[3].getImproFrac();
                            exercise[1] = ops[0].getProFrac()+strings[0]+bracket[0]+ops[1].getProFrac()+strings[1]+ops[2].getProFrac()+bracket[1]+strings[2]+ops[3].getProFrac();
                            break;
                        case 4:
                            exercise[0] = ops[0].getImproFrac()+strings[0]+ops[1].getImproFrac()+strings[1]+bracket[0]+ops[2].getImproFrac()+strings[2]+ops[3].getImproFrac()+bracket[1];
                            exercise[1] = ops[0].getProFrac()+strings[0]+ops[1].getProFrac()+strings[1]+bracket[0]+ops[2].getProFrac()+strings[2]+ops[3].getProFrac()+bracket[1];
                            break;
                        case 5:
                            exercise[0] = bracket[0]+ops[0].getImproFrac()+strings[0]+ops[1].getImproFrac()+bracket[1]+strings[1]+bracket[0]+ops[2].getImproFrac()+strings[2]+ops[3].getImproFrac()+bracket[1];
                            exercise[1] = bracket[0]+ops[0].getProFrac()+strings[0]+ops[1].getProFrac()+bracket[1]+strings[1]+bracket[0]+ops[2].getProFrac()+strings[2]+ops[3].getProFrac()+bracket[1];
                            break;
                        case 6:
                            exercise[0] = bracket[0]+bracket[0]+ops[0].getImproFrac()+strings[0]+ops[1].getImproFrac()+bracket[1]+strings[1]+ops[2].getImproFrac()+bracket[1]+strings[2]+ops[3].getImproFrac();
                            exercise[1] = bracket[0]+bracket[0]+ops[0].getProFrac()+strings[0]+ops[1].getProFrac()+bracket[1]+strings[1]+ops[2].getProFrac()+bracket[1]+strings[2]+ops[3].getProFrac();
                            break;
                        case 7:
                            exercise[0] = bracket[0]+ops[0].getImproFrac()+strings[0]+bracket[0]+ops[1].getImproFrac()+strings[1]+ops[2].getImproFrac()+bracket[1]+bracket[1]+strings[2]+ops[3].getImproFrac();
                            exercise[1] = bracket[0]+ops[0].getProFrac()+strings[0]+bracket[0]+ops[1].getProFrac()+strings[1]+ops[2].getProFrac()+bracket[1]+bracket[1]+strings[2]+ops[3].getProFrac();
                            break;
                        case 8:
                            exercise[0] = ops[0].getImproFrac()+strings[0]+bracket[0]+bracket[0]+ops[1].getImproFrac()+strings[1]+ops[2].getImproFrac()+bracket[1]+strings[2]+ops[3].getImproFrac()+bracket[1];
                            exercise[1] = ops[0].getProFrac()+strings[0]+bracket[0]+bracket[0]+ops[1].getProFrac()+strings[1]+ops[2].getProFrac()+bracket[1]+strings[2]+ops[3].getProFrac()+bracket[1];
                            break;
                        case 9:
                            exercise[0] = ops[0].getImproFrac()+strings[0]+bracket[0]+ops[1].getImproFrac()+strings[1]+bracket[0]+ops[2].getImproFrac()+strings[2]+ops[3].getImproFrac()+bracket[1]+bracket[1];
                            exercise[1] = ops[0].getProFrac()+strings[0]+bracket[0]+ops[1].getProFrac()+strings[1]+bracket[0]+ops[2].getProFrac()+strings[2]+ops[3].getProFrac()+bracket[1]+bracket[1];
                            break;
                    }

                    //如果运算符为2个
                }else {
                    int choose = (int) Math.floor(Math.random()*2);
                    switch (choose){
                        case 0:
                            exercise[0] = bracket[0]+ops[0].getImproFrac()+strings[0]+ops[1].getImproFrac()+bracket[1]+strings[1]+ops[2].getImproFrac();
                            exercise[1] = bracket[0]+ops[0].getProFrac()+strings[0]+ops[1].getProFrac()+bracket[1]+strings[1]+ops[2].getProFrac();
                            break;
                        case 1:
                            exercise[0] = ops[0].getImproFrac()+strings[0]+bracket[0]+ops[1].getImproFrac()+strings[1]+ops[2].getImproFrac()+bracket[1];
                            exercise[1] = ops[0].getProFrac()+strings[0]+bracket[0]+ops[1].getProFrac()+strings[1]+ops[2].getProFrac()+bracket[1];
                            break;
                    }

                }

                //题目不包含括号
            }else {
                //如果运算符为3个
                if (operNum == 3){
                    exercise[0] = ops[0].getImproFrac()+strings[0]+ops[1].getImproFrac()+strings[1]+ops[2].getImproFrac()+strings[2]+ops[3].getImproFrac();
                    exercise[1] = ops[0].getProFrac()+strings[0]+ops[1].getProFrac()+strings[1]+ops[2].getProFrac()+strings[2]+ops[3].getProFrac();

                    //如果运算符为2个
                }else {
                    exercise[0] = ops[0].getImproFrac()+strings[0]+ops[1].getImproFrac()+strings[1]+ops[2].getImproFrac();
                    exercise[1] = ops[0].getProFrac()+strings[0]+ops[1].getProFrac()+strings[1]+ops[2].getProFrac();
                }
            }

        }

        return exercise;

    }

    /**
     * 生成一道题目的操作数集合
     * @param num 操作数的数量
     * @param range 操作数的范围
     * @return 操作数集合
     */
    public static Operand[] getOperandCollection(int num,int range){
        Operand[] operands = new Operand[num];
        for (int i = 0; i < num; i++) {
            operands[i] = getOneOperand(range);
        }
        return operands;
    }

    /**
     * 生成一道题目的运算符集合
     * @param num 运算符数量
     * @return 运算符集合
     */
    public static String[] getOperatorCollection(int num){
        String[] strings = new String[num];
        for (int i = 0; i < num; i++) {
            strings[i] = operators[(int)(Math.random()*4)];
        }
        return strings;

    }

    /**
     * 生成一个操作数
     * @param range 操作数的范围
     * @return 操作数
     */
    public static Operand getOneOperand(int range){
        Operand operand = new Operand();

        //整数部分
        int roundPart = (int)(Math.random()*(range)) ;
        if (roundPart == 0){
            roundPart = 1;
        }
        operand.setRoundPart(roundPart);

        //分数部分
        //50%机率操作数是分数
        if (Math.random() > 0.5){
            //分母
            int denominator = (int) (Math.random()*(50));
            //分母不为0和1
            if (denominator <= 1)
                denominator = 10;

            //分子
            int numerator = (int) (Math.random()*(100));
            //分数部分应为真分数，满足操作数的规定范围
            numerator = numerator % denominator;
            //分子不为0
            if (numerator == 0){
                numerator = 1;
            }

            operand.setFractionalPart(numerator+"/"+denominator);
        }

        return operand;
    }

    /**
     * 根据Map中的value获取key集
     * @param map
     * @param value
     * @return 指定value的key集
     */
    public static ArrayList<String> getKey(Map<String,String> map, String value){
        ArrayList<String> keyList = new ArrayList<>();
        for(String key: map.keySet()){
            if(map.get(key).equals(value)){
                keyList.add(key);
            }
        }
        return keyList;
    }

}
