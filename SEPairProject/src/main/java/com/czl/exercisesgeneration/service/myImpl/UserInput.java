package com.czl.exercisesgeneration.service.myImpl;

public class UserInput {
    public static void menu(String[] args) throws Exception {

        //生成式子数量参数
        int n = 0;
        //生成式子值范围参数
        int r = 0;
        //读取四则运算式子文件名
        String exercisefile = null;
        //读取答案文件名
        String answerfile = null;
        //标志，为真则表示是生成式子命令，为假则是检查答案命令
        boolean isGernate = false;
        //是否有命令的标志
        boolean hasOrder = false;
        int i =0;
        while(i<args.length){
            String s = args[i];
            switch (s){
                case "-n":
                    if (args[i+1].matches("\\d+")){
                        n = Integer.parseInt(args[i+1]);
                        isGernate = true;
                        hasOrder = true;
                    }
                    break;
                case "-r":
                    if (args[i+1].matches("\\d+")){
                        r = Integer.parseInt(args[i+1]);
                        isGernate = true;
                        hasOrder = true;
                    }
                    break;
                case "-e":
                    exercisefile = args[i+1];
                    isGernate = false;
                    hasOrder = true;
                    break;
                case "-a":
                    answerfile = args[i+1];
                    isGernate = false;
                    hasOrder = true;
                    break;
            }
            i++;
        }
        //是否有命令输入
        if (hasOrder){
            //是生成式子命令
            if (isGernate){
                if (n <= 0||r <= 0){
                    System.out.println("没有输入 -n 命令或输入数值不为正数，正确格式为 -n 生成式子数 -r 生成数范围 ");
                }else{
                    if (r > 150){
                        System.out.println("生成数的范围只能在0-150之间");
                    }else{
                        //命令正确，生成式子
                        System.out.println("命令正确，生成"+n+"道数值在"+r+"范围内的式子存在当前目录文件夹下的Exercises.txt和Answers.txt");
                        GenerateExercisesImpl.generateFormulas(n,r);
                    }
                }
            }else{  //是检查结果命令
                if (exercisefile == null||answerfile == null){
                    //式子文件和答案文件存在空值
                    if (exercisefile == null){
                        System.out.println("没有输入 -e 命令，正确格式为  -e <exercisefile>.txt -a <answerfile>.txt ");
                    }else if (answerfile == null){
                        System.out.println("没有输入 -a 命令，正确格式为  -e <exercisefile>.txt -a <answerfile>.txt ");
                    }
                }else{
                    System.out.println("命令正确，检查式子和答案是否正确的结果在当前目录文件夹下的Grade.txt");
                    FileOperationImpl.checkAnswer(exercisefile,answerfile);
                }
            }
        }else{
            System.out.println("没有输入命令或者命令错误，请输入正确的命令！");
        }
    }
}
