package com.czl.exercisesgeneration.view;

import com.czl.exercisesgeneration.service.impl.FileOperationImpl;
import com.czl.exercisesgeneration.service.GenerateExercises;

/**
 * 用户输入类
 */
public class UserInput {

    //生成题目的数量
    public static int exercisesNum = 0;
    //生成题目的数值范围
    public static int exercisesRange = 0;
    //题目文件路径
    public static String exercisesFile = null;
    //答题文件路径
    public static String resultFile = null;
    //命令类型
    public static String commandType = null;

    /**
     * 读取命令，执行程序
     * @param args 命令及参数
     * @throws Exception
     */
    public static void menu(String[] args) throws Exception {
        //命令数组下标
        int index = 0;

        //获取命令
        while(index < args.length){
            switch (args[index]){
                case "-n":
                    if (args[index+1].matches("\\d+")){
                        exercisesNum = Integer.parseInt(args[index+1]);
                        commandType = "GenerateExercises";
                    }
                    break;
                case "-r":
                    if (args[index+1].matches("\\d+")){
                        exercisesRange = Integer.parseInt(args[index+1]);
                        commandType = "GenerateExercises";
                    }
                    break;
                case "-e":
                    exercisesFile = args[index+1];
                    commandType = "CheckResult";
                    break;
                case "-a":
                    resultFile = args[index+1];
                    commandType = "CheckResult";
                    break;
            }
            index++;
        }

        //生成命令
        if (commandType.equals("GenerateExercises")){
            if (exercisesNum <= 0 || exercisesRange <= 0){
                System.out.println(">> 命令格式错误或参数范围错误");
                System.out.println("正确的命令格式：-n 生成题目数量 -r 生成数的范围");
                System.out.println("正确的参数范围：生成题目数量和生成数的范围应为适合范围内的正数");

            }else{
                System.out.println(">> 您输入的是生成命令，正在生成题目……");
                GenerateExercises.generateExercises(exercisesNum,exercisesRange);
                System.out.println(">> 题目生成完成，题目文件及答案文件存储于当前路径下的Exercises.txt和Answers.txt中");

            }

            //检查命令
        }else if (commandType.equals("CheckResult")){
            if (exercisesFile == null || resultFile == null){
                System.out.println(">> 命令格式错误");
                System.out.println("正确的命令格式：-e <exercisefile>.txt -a <answerfile>.txt");
                System.out.println("其中<exercisefile>是题目文件路径，<answerfile>是答题文件路径");

            }else{
                System.out.println(">> 您输入的是检查命令，正在检查答题情况……");
                FileOperationImpl.checkResult(exercisesFile,resultFile);
                System.out.println(">> 检查完成，作答情况的文件存储于当前路径下的Grade.txt中");

            }

            //命令格式错误
        }else {
            System.out.println(">> 输入命令有误");
        }

    }

}
