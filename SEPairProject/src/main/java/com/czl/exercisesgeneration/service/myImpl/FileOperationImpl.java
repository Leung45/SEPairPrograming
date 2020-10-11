package com.czl.exercisesgeneration.service.myImpl;

import com.czl.exercisesgeneration.RPN.impl.StringToRPNImpl;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperationImpl {
    /**
     * 根据传入的文件地址获得文件缓冲流
     * @param fileName 文件名
     * @return 返回一个bufferedreader缓冲流对象
     */
    public static BufferedReader getFileInputStream(String fileName) {
        BufferedReader bufferedReader = null;
        File file = new File(fileName);
        if (file.exists()){
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println(fileName+":该路径文件不存在！请输入存在的文件名！");
            }
        }else{
            System.out.println(fileName+":该路径或文件不存在！请输入存在的文件名！");
            return null;
        }
        return bufferedReader;
    }

    /**
     * 生成文件输出流
     * @param fileName 文件名
     * @return 返回一个输出流对象
     */
    public static BufferedWriter getFileOutputStream(String fileName){
        BufferedWriter bufferedWriter = null;
        File file = new File(fileName);
        try {
            //构建对象，若文件不存在则会自动新建
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedWriter;
    }


    /**
     * 把文件的式子转成分数形式
     * @param s：文件的式子
     * @return ：str[0]是文件式子序号，str[1]是分数式子
     * 例子 "22.  15 +  22'7/8 -   5 x 4"
     */
    public static String[] changeFile(String s){
        //去掉序号
        String[] split = s.split("\\."+" ");

        String[] ps = split[1].split("\\s+");
        for (int i = 0; i < ps.length; i++) {
            if (ps[i].contains("'")){
                //es[0]为整数部分 ， es[1]为分数部分
                String[] es = ps[i].split("'");
                //fen[0]为分子，fen[1]为分母
                String[] fen = es[1].split("/");
                long fenzi = Long.parseLong(es[0]) * Long.parseLong(fen[1]) + Long.parseLong(fen[0]);
                ps[i] = fenzi+"/"+fen[1];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ps.length; i++) {
            sb.append(ps[i]+" ");
        }
        split[1] = sb.toString();
//        System.out.println(split[0]);
//        System.out.println(split[1]);
        return split;

    }

    @Test
    public void testcheckanswer(){
        String[] strings = changeFile("3. 3 + 2'1/3 * ( 4 - 1 )");
//        String[] split = strings[1].split("\\s+");

        System.out.println(strings[0]);
        System.out.println(strings[1]);
//        for (String str:split) {
//            System.out.println(str);
//        }
    }

    /**
     * 把答案文件的序号和答案分隔开
     * @param s 文件中式子
     * @return str[0]是序号，str[1]是答案
     */
    public static String[] changeAnswerFile(String s){
        //去掉序号
        String[] split = s.split("\\.");
        split[1] = split[1].trim();
        return split;
    }


    /**
     * 通过输入的两个文件名检查答案是否正确
     * @param exercisefile 式子文件名
     * @param answerfile 答案文件名
     * @throws Exception
     */
    public static void checkAnswer(String exercisefile,String answerfile) throws Exception {
        //读取式子文件和答案文件
        BufferedReader exercise = getFileInputStream(exercisefile);
        BufferedReader answer = getFileInputStream(answerfile);
        //输出正确和错误序号到grade文件
        BufferedWriter grade = getFileOutputStream("./Grade.txt");
        if (exercise!=null&&answer!=null){
            String formula;
            String resultInFile;
            //存储答案正确题目的序号
            List<String> right = new ArrayList<String>();
            //存储答案错误的题目的序号
            List<String> wrong = new ArrayList<String>();
            while ((formula=exercise.readLine())!=null){
                //将此行的序号和式子分割并且将式子的真分数转化为假分数
                String[] split1 = changeFile(formula);
                //通过式子计算结果
                String poland = StringToRPNImpl.tran2RPNinString(split1[1]);//
                String result = CalculationImpl.calculate(poland);//
                //获得答案文件的内容
                resultInFile = answer.readLine();
                String[] split2 = changeAnswerFile(resultInFile);
                if (result == split2[1]||result.equals(split2[1])){
                    //答案正确，添加到正确list中
                    right.add(split1[0]);
                }else{
                    //答案错误，添加到错误list中
                    wrong.add(split1[0]);
                }
            }
            String s1 = "Correct: "+right.size()+" "+right;
            String s2 = "Wrong: "+wrong.size()+" "+wrong;
            grade.write(s1);
            grade.newLine();
            grade.write(s2);
        }
        //关闭流
        exercise.close();
        answer.close();
        grade.close();
    }



}
