package com.wanan.webgoat.lessons.jwt;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class JWTQuiz extends AssignmentEndpoint {
    private final String[] solutions = {"Solution 1","Solution 2"};
    private final boolean[] guesses = new boolean[solutions.length];

    @PostMapping("/JWT/quiz")
    @ResponseBody
    public AttackResult completed(@RequestParam String[] question_0_solution, @RequestParam String[] question_1_solution){
        int correctAnswers = 0;
        String[] givenAnswers = {question_0_solution[0],question_1_solution[0]};

        for (int i= 0; i< solutions.length;i++){
            if (givenAnswers[i].contains(solutions[i])){
                correctAnswers++;
                guesses[i] = true;

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

    @GetMapping("/JWT/quiz")
    @ResponseBody
    public boolean[] getResults(){
        return this.guesses;
    }
}
