package com.czl.exercisesgenerationtest;

import com.czl.exercisesgeneration.service.ProjectEntry;
import org.junit.jupiter.api.Test;

public class TestAll {
    /**
     * 测试生成10000道题目
     * @throws Exception
     */
    @Test
    public void testGenerate() throws Exception {
        String[] args = {"-n","10000","-r","10"};
        ProjectEntry.main(args);
    }

    /**
     * 测试程序生成的答案是否完全正确，注意需要先生成题目文件和答案文件
     * @throws Exception
     */
    @Test
    public void testCheck() throws Exception {
        String[] args = {"-e","./Exercises.txt","-a","./Answers.txt"};
        ProjectEntry.main(args);
    }

}
