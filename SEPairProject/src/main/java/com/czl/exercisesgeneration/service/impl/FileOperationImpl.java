package com.czl.exercisesgeneration.service.impl;

import com.czl.exercisesgeneration.service.FileOperation;
import com.czl.exercisesgeneration.tool.StringToRPN;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperationImpl implements FileOperation {

    /**
     * 获得对应的字符缓冲输入流
     * @param filePath 文件路径
     * @return 对应的字符缓冲输入流
     */
    @Override
    public BufferedReader getBufferedReader(String filePath) {
        BufferedReader bufferedReader = null;
        File file = new File(filePath);
        if (file.exists()){
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println(filePath+":File path doesn't exist.");
            }
        }else{
            System.out.println(filePath+":File path doesn't exist.");
            return null;
        }
        return bufferedReader;
    }

    /**
     * 获得对应的字符缓冲输出流
     * @param filePath 文件路径
     * @return 对应的字符缓冲输出流
     */
    @Override
    public BufferedWriter getBufferedWriter(String filePath){
        BufferedWriter bufferedWriter = null;
        File file = new File(filePath);
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedWriter;
    }

    /**
     * 将从文件读取的题目的形式转化成便于计算结果的形式(假分数)
     * @param string 读取的一行文件数据
     * @return string[]，其中string[0]：题号，string[1]：假分数形式的题目
     */
    public static String[] alterExercisesFormat(String string){
        //分割题号题目
        String[] split = string.split("\\."+" ");
        //分割获得题目操作元素
        String[] exercises = split[1].split("\\s+");

        for (int i = 0; i < exercises.length; i++) {
            if (exercises[i].contains("'")){
                //part[0]整数部分，part[1]分数部分
                String[] part = exercises[i].split("'");
                //temp[0]分子，temp[1]分母
                String[] temp = part[1].split("/");
                long numerator = Long.parseLong(part[0]) * Long.parseLong(temp[1]) + Long.parseLong(temp[0]);
                exercises[i] = numerator+"/"+temp[1];
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < exercises.length; i++) {
            stringBuilder.append(exercises[i]+" ");
        }
        split[1] = stringBuilder.toString();

        return split;

    }

    /**
     * 分割题号和题目结果
     * @param string 读取的一行文件数据
     * @return string[]，其中string[0]：题号，string[1]：写入的题目结果
     */
    public static String[] alterResultFormat(String string){
        String[] split = string.split("\\.");
        split[1] = split[1].trim();
        return split;
    }

    /**
     * 检查答题情况
     * @param exerciseFile 题目文件路径
     * @param resultFile 答题文件路径
     * @throws Exception
     */
    public static void checkResult(String exerciseFile,String resultFile) throws Exception {
        //读取题目文件
        BufferedReader exercises = new FileOperationImpl().getBufferedReader(exerciseFile);
        //读取做题结果文件
        BufferedReader inputResult = new FileOperationImpl().getBufferedReader(resultFile);
        //将做题情况写入文件中
        BufferedWriter testResult = new FileOperationImpl().getBufferedWriter("./Grade.txt");

        if (exercises!=null&&inputResult!=null){
            //做对的题号集合
            List<String> correctList = new ArrayList<String>();
            //做错的题号集合
            List<String> wrongList = new ArrayList<String>();
            //一行题目
            String exercise;
            //一行结果
            String eachResult;

            while ((exercise = exercises.readLine())!=null){
                //题目正确结果
                String[] split1 = alterExercisesFormat(exercise);
                String polandString = StringToRPN.tran2RPNinString(split1[1]);
                String result = new CalculationImpl().calculate(polandString);

                //答题结果
                eachResult = inputResult.readLine();
                String[] split2 = alterResultFormat(eachResult);

                if (result == split2[1]||result.equals(split2[1])){
                    //答案正确
                    correctList.add(split1[0]);
                }else{
                    //答案错误
                    wrongList.add(split1[0]);
                }
            }

            //写入文件中
            testResult.write("Correct: "+correctList.size()+" "+correctList);
            testResult.newLine();
            testResult.write("Wrong: "+wrongList.size()+" "+wrongList);
        }

        //释放资源
        exercises.close();
        inputResult.close();
        testResult.close();
    }

}
