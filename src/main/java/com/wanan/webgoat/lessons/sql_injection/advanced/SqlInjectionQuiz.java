package com.wanan.webgoat.lessons.sql_injection.advanced;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class SqlInjectionQuiz extends AssignmentEndpoint {

    String[] solutions = {"Solution 4", "Solution 3", "Solution 2", "Solution 3", "Solution 4"};
    boolean[] guesses = new boolean[solutions.length];

    @PostMapping("/SqlInjectionAdvanced/quiz")
    @ResponseBody
    public AttackResult completed(@RequestParam String[] question_0_solution, @RequestParam String[] question_1_solution, @RequestParam String[] question_2_solution, @RequestParam String[] question_3_solution, @RequestParam String[] question_4_solution) throws IOException {
        int correctAnswers = 0;

        String[] givenAnswers = {question_0_solution[0], question_1_solution[0], question_2_solution[0], question_3_solution[0], question_4_solution[0]};

        for (int i = 0; i < solutions.length; i++) {
            if (givenAnswers[i].contains(solutions[i])) {
                // answer correct
                correctAnswers++;
                guesses[i] = true;
            } else {
                // answer incorrect
                guesses[i] = false;
            }
        }

        if (correctAnswers == solutions.length) {
            return success(this).build();
        } else {
            return failed(this).build();
        }
    }

    @GetMapping("/SqlInjectionAdvanced/quiz")
    @ResponseBody
    public boolean[] getResults() {
        return this.guesses;
    }

}
