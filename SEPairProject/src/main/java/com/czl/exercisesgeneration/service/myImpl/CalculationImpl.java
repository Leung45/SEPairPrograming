package com.czl.exercisesgeneration.service.myImpl;

import com.czl.exercisesgeneration.RPN.impl.StringToRPNImpl;
import com.czl.exercisesgeneration.service.impl.Stack;
import org.junit.jupiter.api.Test;

public class CalculationImpl {

    /**
     * 计算
     * @param poland
     * @return
     */
    public static String calculate(String poland){

        Stack stack = new Stack();
        String result = "";

        String[] split = poland.split("\\s+");
        for (int i = 0; i < split.length; i++) {
            //是操作数
            if (split[i].matches("^[0-9]+.*")){
                stack.push(split[i]);
            }else {
                String e2 = stack.pop();
                String e1 = stack.pop();
                if (split[i].equals("+")){
                    result = add(e1, e2);
                }else if(split[i].equals("-")){
                    result = sub(e1,e2);
                }else if (split[i].equals("*")) {
                    result = mul(e1,e2);
                }else{
                    result = div(e1,e2);
                }
                if (result.equals("error") || result == null){
                    return "error";
                }
                //把计算结果入栈
                stack.push(result);
            }
        }
        result = stack.getTop();
        //约分
        if (result.contains("/")){
            String[] qs = result.split("/");
            long w1 = Long.parseLong(qs[0]);
            long w2 = Long.parseLong(qs[1]);

            long w = w1 % w2;
            if (w == 0){
                return w1/w2+"";
            }else {
                //整数部分
                long a = (w1-w)/w2;
                //分数部分
                long reduce = reduce(w,w2);
                w /= reduce;
                w2 /= reduce;
                if (a==0){
                    return w+"/"+w2;
                }else {
                    return a+"'"+w+"/"+w2;
                }
            }
        }

        return stack.getTop();
    }

    /**
     * 加法
     * @return
     */
    public static String add(String e1 , String e2 ){
        if (e1 == null || e2 == null) return "error";
        if (e1.contains("/")){
            String[] e1s = e1.split("/");
            long e1z = Long.parseLong(e1s[0]);
            long e1m = Long.parseLong(e1s[1]);
            if (e2.contains("/")){
                String[] e2s = e2.split("/");
                //分子
                long e2z = Long.parseLong(e2s[0]);

                //分母
                long e2m = Long.parseLong(e2s[1]);

                //计算
                long ei = e1z * e2m + e2z * e1m;
                long em = e1m * e2m;
                //约分，避免数太大超界
                long reduce = reduce(ei, em);
                ei /= reduce;
                em /= reduce;
                return ei+"/"+em;
            }else {
                e1z = e1z + Long.parseLong(e2) * e1m;
                long reduce = reduce(e1z, e1m);
                e1z /= reduce;
                e1m /= reduce;
                return e1z+"/"+e1m;
            }
        }else {
            if (e2.contains("/")){
                String[] e2s = e2.split("/");
                long e2z = Long.parseLong(e2s[0]);
                long e2m = Integer.parseInt(e2s[1]);

                e2z = e2z + Long.parseLong(e1) * e2m;
                long reduce = reduce(e2z, e2m);
                e2z /= reduce;
                e2m /= reduce;
                return e2z+"/"+e2m;
            }else {
                return (Long.parseLong(e1)+Long.parseLong(e2))+"";
            }
        }
    }

    /**
     * 减法
     * 若中间出现负数，返回 "error"
     * @return
     */
    public static String sub(String e1 , String e2 ){
        if (e1 == null || e2 == null) return "error";
        if (e1.contains("/")){
            String[] e1s = e1.split("/");
            int e1z = Integer.parseInt(e1s[0]);
            int e1m = Integer.parseInt(e1s[1]);
            // e1 e2都是分数
            if (e2.contains("/")){
                String[] e2s = e2.split("/");
                //分子
                long e2z = Long.parseLong(e2s[0]);
                //分母
                long e2m = Long.parseLong(e2s[1]);

                //计算
                long ei = e1z * e2m - e2z * e1m;
                if (ei < 0){
                    return "error";
                }
                if (ei == 0){
                    return "0";
                }
                long em = e1m * e2m;
                long reduce = reduce(ei, em);
                ei /= reduce;
                em /= reduce;
                return ei+"/"+em;
            }else {
                // e1 是分数  e2是整数
                e1z = e1z - Integer.parseInt(e2) * e1m;
                if (e1z < 0) return "error";
                if (e1z == 0) return "0";
                long reduce = reduce(e1z, e1m);
                e1z /= reduce;
                e1m /= reduce;
                return e1z+"/"+e1m;
            }
        }else {
            //e1是整数，e2是分数
            if (e2.contains("/")){
                String[] e2s = e2.split("/");
                long e2z = Long.parseLong(e2s[0]);
                long e2m = Long.parseLong(e2s[1]);

                e2z = Long.parseLong(e1) * e2m - e2z;
                if (e2z < 0) return "error";
                if (e2z == 0) return "0";
                long reduce = reduce(e2z, e2m);
                e2z /= reduce;
                e2m /= reduce;
                return e2z+"/"+e2m;
            }else {
                //e1 e2 都是整数
                long result = Long.parseLong(e1)-Long.parseLong(e2);
                if (result < 0){
                    return "error";
                }
                return result+"";
            }
        }

    }

    /**
     * 乘法
     * @return
     */
    public static String mul( String e1 , String e2){
        if (e1 == null || e2 == null) return "error";
        if (e1.contains("/")){
            String[] e1s = e1.split("/");
            int e1z = Integer.parseInt(e1s[0]);
            int e1m = Integer.parseInt(e1s[1]);
            // e1 e2都是分数
            if (e2.contains("/")){
                String[] e2s = e2.split("/");
                //分子
                long e2z = Long.parseLong(e2s[0]);
                //分母
                long e2m = Long.parseLong(e2s[1]);

                //计算
                long ei = e1z *  e2z;
                if (ei == 0) return "0";
                long em = e1m *  e2m;
                long reduce = reduce(ei, em);
                ei /= reduce;
                em /= reduce;
                return ei+"/"+em;
            }else {
                // e1 是分数  e2是整数
                e1z = e1z * Integer.parseInt(e2);
                if (e1z < 0) return "error";
                if (e1z == 0) return "0";
                long reduce = reduce(e1z, e1m);
                e1z /= reduce;
                e1m /= reduce;
                return e1z+"/"+e1m;
            }
        }else {
            //e1是整数，e2是分数
            if (e2.contains("/")){
                String[] e2s = e2.split("/");
                long e2z = Long.parseLong(e2s[0]);
                long e2m = Long.parseLong(e2s[1]);

                e2z = Long.parseLong(e1) *  e2z;
                if (e2z < 0) return "error";
                if (e2z == 0) return "0";
                long reduce = reduce(e2z, e2m);
                e2z /= reduce;
                e2m /= reduce;
                return e2z+"/"+e2m;
            }else {
                //e1 e2 都是整数
                long result = Long.parseLong(e1)*Long.parseLong(e2);
                if (result < 0){
                    return "error";
                }
                return result+"";
            }
        }
    }

    /**
     * 除法
     * @return
     */
    public static String div ( String e1 , String e2){
        if (e1 == null || e2 == null) return "error";
        if (e1.contains("/")){
            String[] e1s = e1.split("/");
            int e1z = Integer.parseInt(e1s[0]);
            int e1m = Integer.parseInt(e1s[1]);
            // e1 e2都是分数
            if (e2.contains("/")){
                String[] e2s = e2.split("/");
                //分子
                long e2z = Long.parseLong(e2s[0]);
                //分母
                long e2m = Long.parseLong(e2s[1]);

                //计算
                long ei = e1z *  e2m;
                if (ei == 0) return "0";
                long em = e1m *  e2z;

                long reduce = reduce(ei, em);
                ei /= reduce;
                em /= reduce;
                return ei+"/"+em;
            }else {
                // e1 是分数  e2是整数
                e1m = e1m * Integer.parseInt(e2);
                //分母不可为0
                if (e1m <= 0){
                    return "error";
                }

                long reduce = reduce(e1z, e1m);
                e1z /= reduce;
                e1m /= reduce;
                return e1z+"/"+e1m;
            }
        }else {
            //e1是整数，e2是分数
            if (e2.contains("/")){
                String[] e2s = e2.split("/");
                long e2z = Long.parseLong(e2s[0]);
                long e2m = Long.parseLong(e2s[1]);

                e2m = Long.parseLong(e1) *  e2m;
                if (e2m < 0) return "error";
                if (e2m == 0) return "0";

                long reduce = reduce(e2m, e2z);
                e2m /= reduce;
                e2z /= reduce;

                return e2m+"/"+e2z;
            }else {
                //e1 e2 都是整数
                if (Long.parseLong(e1) % Long.parseLong(e2) == 0){
                    return (Long.parseLong(e1)/Long.parseLong(e2))+"";
                }else {
                    long e1z = Long.parseLong(e1);
                    long e2m = Long.parseLong(e2);

                    long reduce = reduce(e1z, e2m);
                    e1z /= reduce;
                    e2m /= reduce;
                    String result = e1z+"/"+e2m;
                    return result;
                }

            }
        }
    }


    /**
     * 约分分数
     * @param a：分子
     * @param b：分母
     * @return
     */
    public static long reduce(long a , long b){
        long min = a < b ? a : b;
        long c,d;

        for( int i = 1;i <= min/2;i ++){
            c = a > b ? a : b;//两个数字的大值
            d = a < b ? a : b;//两个数字的小值
            a = c % d;
            b = d;
            if(a == 0) {
                return d;
            }

        }
        return 1L;
    }

    @Test
    public void testtimu(){
        String string = StringToRPNImpl.tran2RPNinString("( " + "2" + " + " + "5" + " )" + " * " + "( " + "6" + " - " + "1" + " )");
        System.out.println(calculate(string));
    }
}
