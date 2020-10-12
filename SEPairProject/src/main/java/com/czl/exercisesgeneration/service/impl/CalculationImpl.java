package com.czl.exercisesgeneration.service.impl;

import com.czl.exercisesgeneration.service.Calculation;

import java.util.Stack;

public class CalculationImpl implements Calculation {

    /**
     * 计算结果
     * @param RPNString 算式对应的逆波兰表达式
     * @return 算式结果
     */
    @Override
    public String calculate(String RPNString){
        //辅助计算操作的栈
        Stack<String> stack = new Stack<String>();
        //计算结果
        String result = "";
        //将字符串按照空格分割成操作单元数组
        String[] split = RPNString.split("\\s+");
        
        for (int i = 0; i < split.length; i++) {
            //如果是数
            if (split[i].matches("^[0-9]+.*")){
                stack.push(split[i]);
                
                //如果是运算符
            }else {
                String num2 = stack.pop();
                String num1 = stack.pop();
                if (split[i].equals("+")){
                    result = addition(num1,num2);
                }else if(split[i].equals("-")){
                    result = subtraction(num1,num2);
                }else if (split[i].equals("*")) {
                    result = multiplication(num1,num2);
                }else{
                    result = division(num1,num2);
                }
                if (result.equals("invalid") || result == null){
                    return "invalid";
                }
                //把计算结果入栈
                stack.push(result);
            }
        }
        
        result = stack.peek();
        
        //约分
        if (result.contains("/")){
            String[] tran = result.split("/");
            long part1 = Long.parseLong(tran[0]);
            long part2 = Long.parseLong(tran[1]);

            long temp = part1 % part2;
            if (temp == 0){
                return part1/part2+"";
            }else {
                long roundPart = (part1-temp)/part2;
                long fractionalPart = reduce(temp,part2);

                temp = temp/fractionalPart;
                part2 = part2/fractionalPart;

                if (roundPart==0){
                    return temp+"/"+part2;
                }else {
                    return roundPart+"'"+temp+"/"+part2;
                }
            }
        }

        return stack.peek();
    }

    /**
     * 加法
     * @param num1
     * @param num2
     * @return 结果
     */
    public static String addition( String num1 , String num2 ){
        if (num1 == null || num2 == null)
            return "invalid";

        if (num1.contains("/")){
            String[] split1 = num1.split("/");
            long num1z = Long.parseLong(split1[0]);
            long num1m = Long.parseLong(split1[1]);
            if (num2.contains("/")){
                String[] split2 = num2.split("/");
                long num2z = Long.parseLong(split2[0]);
                long num2m = Long.parseLong(split2[1]);

                long temp1 = num1z * num2m + num2z * num1m;
                long temp2 = num1m * num2m;

                //约分
                long reduce = reduce(temp1,temp2);
                temp1 /= reduce;
                temp2 /= reduce;
                return temp1+"/"+temp2;
            }else {
                num1z = num1z + Long.parseLong(num2) * num1m;
                long reduce = reduce(num1z,num1m);
                num1z /= reduce;
                num1m /= reduce;
                return num1z+"/"+num1m;
            }
        }else {
            if (num2.contains("/")){
                String[] split2 = num2.split("/");
                long num2z = Long.parseLong(split2[0]);
                long num2m = Integer.parseInt(split2[1]);

                num2z = num2z + Long.parseLong(num1) * num2m;
                long reduce = reduce(num2z,num2m);
                num2z /= reduce;
                num2m /= reduce;
                return num2z+"/"+num2m;
            }else {
                return (Long.parseLong(num1)+Long.parseLong(num2))+"";
            }
        }
    }

    /**
     * 减法
     * @param num1
     * @param num2
     * @return 结果
     */
    public static String subtraction( String num1 , String num2 ){
        if (num1 == null || num2 == null)
            return "invalid";

        if (num1.contains("/")){
            String[] split1 = num1.split("/");
            int num1z = Integer.parseInt(split1[0]);
            int num1m = Integer.parseInt(split1[1]);

            if (num2.contains("/")){
                String[] split2 = num2.split("/");
                long num2z = Long.parseLong(split2[0]);
                long num2m = Long.parseLong(split2[1]);

                long temp1 = num1z * num2m - num2z * num1m;

                if (temp1 < 0){
                    return "invalid";
                }
                if (temp1 == 0){
                    return "0";
                }

                long temp2 = num1m * num2m;
                long reduce = reduce(temp1,temp2);

                temp1 /= reduce;
                temp2 /= reduce;

                return temp1+"/"+temp2;

            }else {
                num1z = num1z - Integer.parseInt(num2) * num1m;

                if (num1z < 0)
                    return "invalid";
                if (num1z == 0)
                    return "0";

                long reduce = reduce(num1z,num1m);

                num1z /= reduce;
                num1m /= reduce;

                return num1z+"/"+num1m;

            }
        }else {
            if (num2.contains("/")){
                String[] split2 = num2.split("/");
                long num2z = Long.parseLong(split2[0]);
                long num2m = Long.parseLong(split2[1]);

                num2z = Long.parseLong(num1) * num2m - num2z;

                if (num2z < 0)
                    return "invalid";
                if (num2z == 0)
                    return "0";

                long reduce = reduce(num2z,num2m);

                num2z /= reduce;
                num2m /= reduce;

                return num2z+"/"+num2m;

            }else {
                long result = Long.parseLong(num1)-Long.parseLong(num2);
                if (result < 0){
                    return "invalid";
                }
                return result+"";

            }
        }
    }

    /**
     * 乘法
     * @param num1
     * @param num2
     * @return 结果
     */
    public static String multiplication( String num1 , String num2 ){
        if (num1 == null || num2 == null)
            return "invalid";

        if (num1.contains("/")){
            String[] split1 = num1.split("/");
            int num1z = Integer.parseInt(split1[0]);
            int num1m = Integer.parseInt(split1[1]);

            if (num2.contains("/")){
                String[] split2 = num2.split("/");
                long num2z = Long.parseLong(split2[0]);
                long num2m = Long.parseLong(split2[1]);

                long temp1 = num1z *  num2z;

                if (temp1 == 0)
                    return "0";

                long temp2 = num1m * num2m;
                long reduce = reduce(temp1,temp2);

                temp1 /= reduce;
                temp2 /= reduce;

                return temp1+"/"+temp2;

            }else {
                num1z = num1z * Integer.parseInt(num2);
                if (num1z < 0)
                    return "invalid";
                if (num1z == 0)
                    return "0";

                long reduce = reduce(num1z, num1m);

                num1z /= reduce;
                num1m /= reduce;

                return num1z+"/"+num1m;

            }
        }else {
            if (num2.contains("/")){
                String[] split2 = num2.split("/");
                long num2z = Long.parseLong(split2[0]);
                long num2m = Long.parseLong(split2[1]);

                num2z = Long.parseLong(num1) * num2z;

                if (num2z < 0)
                    return "invalid";
                if (num2z == 0)
                    return "0";

                long reduce = reduce(num2z,num2m);

                num2z /= reduce;
                num2m /= reduce;

                return num2z+"/"+num2m;

            }else {
                long result = Long.parseLong(num1)*Long.parseLong(num2);
                if (result < 0){
                    return "invalid";
                }
                return result+"";
            }
        }
    }

    /**
     * 除法
     * @param num1
     * @param num2
     * @return 结果
     */
    public static String division( String num1 , String num2 ){
        if (num1 == null || num2 == null || num2.equals("0"))
            return "invalid";

        if (num1.contains("/")){
            String[] split1 = num1.split("/");
            int num1z = Integer.parseInt(split1[0]);
            int num1m = Integer.parseInt(split1[1]);

            if (num2.contains("/")){
                String[] split2 = num2.split("/");
                long num2z = Long.parseLong(split2[0]);
                long num2m = Long.parseLong(split2[1]);

                long temp1 = num1z * num2m;

                if (temp1 == 0)
                    return "0";

                long temp2 = num1m * num2z;

                long reduce = reduce(temp1,temp2);

                temp1 /= reduce;
                temp2 /= reduce;

                return temp1+"/"+temp2;

            }else {
                num1m = num1m * Integer.parseInt(num2);

                if (num1m <= 0){
                    return "invalid";
                }

                long reduce = reduce(num1z,num1m);

                num1z /= reduce;
                num1m /= reduce;

                return num1z+"/"+num1m;
            }
        }else {
            if (num2.contains("/")){
                String[] split2 = num2.split("/");
                long num2z = Long.parseLong(split2[0]);
                long num2m = Long.parseLong(split2[1]);

                num2m = Long.parseLong(num1) * num2m;

                if (num2m < 0)
                    return "invalid";
                if (num2m == 0)
                    return "0";

                long reduce = reduce(num2m,num2z);

                num2m /= reduce;
                num2z /= reduce;

                return num2m+"/"+num2z;

            }else {
                if (Long.parseLong(num1) % Long.parseLong(num2) == 0){
                    return (Long.parseLong(num1)/Long.parseLong(num2))+"";
                }else {
                    long num1z = Long.parseLong(num1);
                    long num2m = Long.parseLong(num2);

                    long reduce = reduce(num1z,num2m);

                    num1z /= reduce;
                    num2m /= reduce;
                    String result = num1z+"/"+num2m;

                    return result;

                }
            }
        }
    }

    /**
     * 约分操作
     * @param numerator 分子
     * @param denominator 分母
     * @return 结果
     */
    public static long reduce(long numerator , long denominator){
        long min = numerator < denominator ? numerator : denominator;
        long tran1,tran2;

        for( int i = 1;i <= min/2;i ++){
            //两数大者
            tran1 = numerator > denominator ? numerator : denominator;
            //两数小者
            tran2 = numerator < denominator ? numerator : denominator;

            numerator = tran1 % tran2;
            denominator = tran2;
            if(numerator == 0) {
                return tran2;
            }
        }
        return 1L;
    }

}
