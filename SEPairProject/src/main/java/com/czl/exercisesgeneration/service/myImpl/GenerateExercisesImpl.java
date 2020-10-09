package com.czl.exercisesgeneration.service.myImpl;

import com.czl.exercisesgeneration.service.impl.Operation;

public class GenerateExercisesImpl {

    private static final String[] on = {" + "," - "," x "," ÷ "};

    /**
     * 生成式子
     * @param p 生成数的范围
     * @return 返回str[]数组，str[0]是假分数形式，str[1]是正常形式
     */
    public static String[] getVal(int p){

        String[] s = new String[2];

        //操作数
        Operation[] ops = getOps(4,p);

        //操作符
        String[] strings = getOpfu(3);

        int[] n = new int[3];
        for (int i = 0; i < 3; i++) {
            n[i] = (int)(Math.random()*4);
        }
        //假分数形式
        s[0] = ops[0].getValue()+strings[0]+ops[1].getValue()+strings[1]+ops[2].getValue()+strings[2]+ops[3].getValue();
        //正常形式
        s[1] = ops[0].getStr()+strings[0]+ops[1].getStr()+strings[1]+ops[2].getStr()+strings[2]+ops[3].getStr();
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

}
