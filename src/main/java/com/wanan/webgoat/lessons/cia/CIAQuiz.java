package com.wanan.webgoat.lessons.cia;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class CIAQuiz extends AssignmentEndpoint {
    String[] solutions = {"Solution 3","Solution 1","Solution 4", "Solution 2"};
    boolean[] guesses = new boolean[solutions.length];

    @PostMapping("/cia/quiz")
    @ResponseBody
    public AttackResult completed(@RequestParam String[] question_0_solution,@RequestParam String[] question_1_solution,@RequestParam String[] question_2_solution,@RequestParam String [] question_3_solution){
//        这里可见请求参数这里其实是使用的String [] 数组,那么当我们传入多个相同参数的时候就会全部获取存放到数组之中
        int correctAnswers =0 ;

        String[] givenAnswers = {question_0_solution[0],question_1_solution[0],question_2_solution[0],question_3_solution[0]};
//        获取每个参数中个的第一个值 并存放到givenAnswers中去

        for (int i = 0; i < solutions.length;i++){
            if (givenAnswers[i].contains(solutions[i])){
//                如果第一个参数中包含 Solution 3
                correctAnswers++;
//                正确答案的数量加1
                guesses[i] = true;
//                就猜对了
            }else {
                guesses[i] = false;
            }
        }
        if (correctAnswers == solutions.length){
            return success(this).build();
        }else {
            return failed(this).build();
        }
    }
    @GetMapping("/cia/quiz")
    @ResponseBody
    public boolean[] getResults(){
//        返回这个guesses数组
        return this.guesses;
    }
}
