package com.czl.exercisesgeneration.service.myImpl;

import com.czl.exercisesgeneration.RPN.BinaryTreeNode;
import com.czl.exercisesgeneration.RPN.StringArrayToBinaryTree;
import com.czl.exercisesgeneration.RPN.impl.RepetitionJudgeImpl;
import com.czl.exercisesgeneration.RPN.impl.StringArrayToBinaryTreeImpl;
import com.czl.exercisesgeneration.RPN.impl.StringToRPNImpl;
import com.czl.exercisesgeneration.service.impl.Changes;

import com.czl.exercisesgeneration.service.impl.Generation;
import com.czl.exercisesgeneration.service.impl.Operation;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.util.*;

public class GenerateExercisesImpl {

    private static Set<String> set = new HashSet<String>();
    private static LinkedList<String> exerciseList = new LinkedList<String>();
    private static HashMap<String,String> exerciseMap = new HashMap<String,String>();
    private static final String[] on = {" + "," - "," * "," ÷ "};
    private static final String[] bracket = {"( "," )"};

    /**
     *
     * @param map
     * @param value
     * @return
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

    /**
     * 生成题目
     * @param n 生成题目的个数
     * @param r 生成题目中值的范围
     */
    public static void generateFormulas(int n,int r) throws Exception {
        BufferedWriter exercises = FileOperationImpl.getFileOutputStream("./Exercises.txt");
        BufferedWriter answers = FileOperationImpl.getFileOutputStream("./Answers.txt");



        for (int i = 0; i < n; i++) {
            loop:
            while (true){
                String[] val = getVal(r);
                String poland = StringToRPNImpl.tran2RPNinString(val[0]);
                String result = CalculationImpl.calculate(poland);

                if (result.equals("error")){
                    continue;
                }

                if (exerciseMap.containsValue(result)){
                    ArrayList<String> arrayList = getKey(exerciseMap,result);
                    BinaryTreeNode treeNode = StringArrayToBinaryTreeImpl.tran2BinaryTree(val[0]);
                    for ( String exercisedange : arrayList ) {
                        if (RepetitionJudgeImpl.isRepetitive(treeNode,StringArrayToBinaryTreeImpl.tran2BinaryTree(exercisedange))){

                            continue loop;
                        }
                    }
                }
                exerciseMap.put(val[0],result);
                exercises.write(i+1+". "+val[1]);
                exercises.newLine();
                answers.write(i+1+". "+result);
                answers.newLine();
                break;

//                if (set.add(result)){
//                    if (!result.equals("error")){
//                        exercises.write(i+1+". "+val[1]);
//                        exercises.newLine();
//                        answers.write(i+1+". "+result);
//                        answers.newLine();
//                        break;
//                    }
//                }else{
//                    continue;
//                }
            }
        }
        exercises.close();
        answers.close();
    }

    /**
     * 生成式子
     * @param p 生成数的范围
     * @return 返回str[]数组，str[0]是假分数形式，str[1]是正常形式
     */
    public static String[] getVal(int p){

        int containBracket = (int) Math.round(Math.random());//1you0wu

        String[] s = new String[2];

        int operNum = (int) Math.floor((Math.random()*3)+1.0);//1-3


        //操作数
        Operation[] ops = getOps(operNum+1,p);

        //操作符
        String[] strings = getOpfu(operNum);

        if (operNum == 1){
            //假分数形式
            s[0] = ops[0].getValue()+strings[0]+ops[1].getValue();
            //正常形式
            s[1] = ops[0].getStr()+strings[0]+ops[1].getStr();
        }else {
            if (containBracket == 1){
                if (operNum == 3){
                    int choose = (int) Math.floor(Math.random()*10);
                    switch (choose){
                        case 0:
                            s[0] = bracket[0]+ops[0].getValue()+strings[0]+ops[1].getValue()+strings[1]+ops[2].getValue()+bracket[1]+strings[2]+ops[3].getValue();
                            s[1] = bracket[0]+ops[0].getStr()+strings[0]+ops[1].getStr()+strings[1]+ops[2].getStr()+bracket[1]+strings[2]+ops[3].getStr();
                            break;
                        case 1:
                            s[0] = ops[0].getValue()+strings[0]+bracket[0]+ops[1].getValue()+strings[1]+ops[2].getValue()+strings[2]+ops[3].getValue()+bracket[1];
                            s[1] = ops[0].getStr()+strings[0]+bracket[0]+ops[1].getStr()+strings[1]+ops[2].getStr()+strings[2]+ops[3].getStr()+bracket[1];
                            break;
                        case 2:
                            s[0] = bracket[0]+ops[0].getValue()+strings[0]+ops[1].getValue()+bracket[1]+strings[1]+ops[2].getValue()+strings[2]+ops[3].getValue();
                            s[1] = bracket[0]+ops[0].getStr()+strings[0]+ops[1].getStr()+bracket[1]+strings[1]+ops[2].getStr()+strings[2]+ops[3].getStr();
                            break;
                        case 3:
                            s[0] = ops[0].getValue()+strings[0]+bracket[0]+ops[1].getValue()+strings[1]+ops[2].getValue()+bracket[1]+strings[2]+ops[3].getValue();
                            s[1] = ops[0].getStr()+strings[0]+bracket[0]+ops[1].getStr()+strings[1]+ops[2].getStr()+bracket[1]+strings[2]+ops[3].getStr();
                            break;
                        case 4:
                            s[0] = ops[0].getValue()+strings[0]+ops[1].getValue()+strings[1]+bracket[0]+ops[2].getValue()+strings[2]+ops[3].getValue()+bracket[1];
                            s[1] = ops[0].getStr()+strings[0]+ops[1].getStr()+strings[1]+bracket[0]+ops[2].getStr()+strings[2]+ops[3].getStr()+bracket[1];
                            break;
                        case 5:
                            s[0] = bracket[0]+ops[0].getValue()+strings[0]+ops[1].getValue()+bracket[1]+strings[1]+bracket[0]+ops[2].getValue()+strings[2]+ops[3].getValue()+bracket[1];
                            s[1] = bracket[0]+ops[0].getStr()+strings[0]+ops[1].getStr()+bracket[1]+strings[1]+bracket[0]+ops[2].getStr()+strings[2]+ops[3].getStr()+bracket[1];
                            break;
                        case 6:
                            s[0] = bracket[0]+bracket[0]+ops[0].getValue()+strings[0]+ops[1].getValue()+bracket[1]+strings[1]+ops[2].getValue()+bracket[1]+strings[2]+ops[3].getValue();
                            s[1] = bracket[0]+bracket[0]+ops[0].getStr()+strings[0]+ops[1].getStr()+bracket[1]+strings[1]+ops[2].getStr()+bracket[1]+strings[2]+ops[3].getStr();
                            break;
                        case 7:
                            s[0] = bracket[0]+ops[0].getValue()+strings[0]+bracket[0]+ops[1].getValue()+strings[1]+ops[2].getValue()+bracket[1]+bracket[1]+strings[2]+ops[3].getValue();
                            s[1] = bracket[0]+ops[0].getStr()+strings[0]+bracket[0]+ops[1].getStr()+strings[1]+ops[2].getStr()+bracket[1]+bracket[1]+strings[2]+ops[3].getStr();
                            break;
                        case 8:
                            s[0] = ops[0].getValue()+strings[0]+bracket[0]+bracket[0]+ops[1].getValue()+strings[1]+ops[2].getValue()+bracket[1]+strings[2]+ops[3].getValue()+bracket[1];
                            s[1] = ops[0].getStr()+strings[0]+bracket[0]+bracket[0]+ops[1].getStr()+strings[1]+ops[2].getStr()+bracket[1]+strings[2]+ops[3].getStr()+bracket[1];
                            break;
                        case 9:
                            s[0] = ops[0].getValue()+strings[0]+bracket[0]+ops[1].getValue()+strings[1]+bracket[0]+ops[2].getValue()+strings[2]+ops[3].getValue()+bracket[1]+bracket[1];
                            s[1] = ops[0].getStr()+strings[0]+bracket[0]+ops[1].getStr()+strings[1]+bracket[0]+ops[2].getStr()+strings[2]+ops[3].getStr()+bracket[1]+bracket[1];
                            break;
                    }
                }else {
                    int choose = (int) Math.floor(Math.random()*2);
                    switch (choose){
                        case 0:
                            s[0] = bracket[0]+ops[0].getValue()+strings[0]+ops[1].getValue()+bracket[1]+strings[1]+ops[2].getValue();
                            s[1] = bracket[0]+ops[0].getStr()+strings[0]+ops[1].getStr()+bracket[1]+strings[1]+ops[2].getStr();
                            break;
                        case 1:
                            s[0] = ops[0].getValue()+strings[0]+bracket[0]+ops[1].getValue()+strings[1]+ops[2].getValue()+bracket[1];
                            s[1] = ops[0].getStr()+strings[0]+bracket[0]+ops[1].getStr()+strings[1]+ops[2].getStr()+bracket[1];
                            break;
                    }

                }
            }else {
                if (operNum == 3){
                    //假分数形式
                    s[0] = ops[0].getValue()+strings[0]+ops[1].getValue()+strings[1]+ops[2].getValue()+strings[2]+ops[3].getValue();
                    s[1] = ops[0].getStr()+strings[0]+ops[1].getStr()+strings[1]+ops[2].getStr()+strings[2]+ops[3].getStr();
                }else {
                    //假分数形式
                    s[0] = ops[0].getValue()+strings[0]+ops[1].getValue()+strings[1]+ops[2].getValue();
                    //正常形式
                    s[1] = ops[0].getStr()+strings[0]+ops[1].getStr()+strings[1]+ops[2].getStr();
                }
            }

        }



//        //假分数形式
//        s[0] = ops[0].getValue()+strings[0]+ops[1].getValue()+strings[1]+ops[2].getValue()+strings[2]+ops[3].getValue();
//        //正常形式
//        s[1] = ops[0].getStr()+strings[0]+ops[1].getStr()+strings[1]+ops[2].getStr()+strings[2]+ops[3].getStr();
        return s;
    }


    /**
     * 生成操作数组
     * @param num
     * @param n
     * @return
     */
    public static Operation[] getOps(int num,int n){
        Operation[] ops = new Operation[num];
        for (int i = 0; i < num; i++) {
            ops[i] = getOp(n);
        }
        return ops;
    }

    /**
     * 生成操作数
     * @Param 生成数的范围
     * @return
     */
    public static Operation getOp(int n){
        Operation op = new Operation();

        int a = (int)(Math.random()*(n)) ;
        if (a == 0){
            a = 1;
        }

        op.setA(a);

        //有分数
        if ((int)(Math.random()*(100)) > 60){
            //分母默认在20以内
            int fenMu = (int) (Math.random()*(20));
            if (fenMu <= 1) fenMu = 5;
            int fenZi = (int) (Math.random()*(100));
            fenZi = fenZi % fenMu;
            if (fenZi == 0){
                fenZi = 1;
            }
            op.setB(fenZi+"/"+fenMu);
        }

        return op;
    }


    /**
     * 生成操作符
     * @param num
     * @return
     */
    public static String[] getOpfu(int num){
        String[] strings = new String[num];
        for (int i = 0; i < num; i++) {
            strings[i] = on[(int)(Math.random()*4)];
        }
        return strings;


    }

    @Test
    public void testThis(){
        String[] strings = getVal(10);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
    }

}
