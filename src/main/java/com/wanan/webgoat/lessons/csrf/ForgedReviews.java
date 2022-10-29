package com.wanan.webgoat.lessons.csrf;

import com.google.common.collect.Lists;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import com.wanan.webgoat.container.session.WebSession;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.http.MediaType.ALL_VALUE;

@RestController
@AssignmentHints({"csrf-review-hint1", "csrf-review-hint2", "csrf-review-hint3"})
public class ForgedReviews extends AssignmentEndpoint {
    @Autowired
    private WebSession webSession;
    private static DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd, HH:mm:ss");
    private static final Map<String, List<Review>> userReviews = new HashMap<>();
    private static final List<Review> REVIEWS = new ArrayList<>();
    private static final String weakAntiCSRF = "2aa14227b9a13d0bede0388a7fba9aa9";

    static {
        REVIEWS.add(new Review("secUriTy", DateTime.now().toString(fmt), "This is like swiss cheese", 0));
        REVIEWS.add(new Review("webgoat", DateTime.now().toString(fmt), "It works, sorta", 2));
        REVIEWS.add(new Review("guest", DateTime.now().toString(fmt), "Best, App, Ever", 5));
        REVIEWS.add(new Review("guest", DateTime.now().toString(fmt), "This app is so insecure, I didn't even post this review, can you pull that off too?", 1));
    }
    @GetMapping(path = "/csrf/review",produces = MediaType.APPLICATION_JSON_VALUE,consumes = ALL_VALUE)
    @ResponseBody
    public Collection<Review> retrieveReviews(){
        Collection<Review> allReviews = Lists.newArrayList();
        Collection<Review> newReviews = userReviews.get(webSession.getUserName());
        if (newReviews != null){
            allReviews.addAll(newReviews);
        }
        allReviews.addAll(REVIEWS);
        return allReviews;
    }

    @PostMapping("/csrf/review")
    @ResponseBody
    public AttackResult createNewReview(String reviewText, Integer starts , String validateReq, HttpServletRequest request){
        final String host = (request.getHeader("host") ==null) ?"NULL":request.getHeader("host");
        final String referer = (request.getHeader("referer") ==null)?"NULL" :request.getHeader("referer");
        final String[] refererArr =referer.split("/");
        Review review = new Review();
        review.setText(reviewText);
        review.setDateTime(DateTime.now().toString(fmt));
        review.setUser(webSession.getUserName());
        review.setStars(starts);
        var reviews = userReviews.getOrDefault(webSession.getUserName(),new ArrayList<>());
        reviews.add(review);
        userReviews.put(webSession.getUserName(),reviews);
        if (validateReq != "NULL" && refererArr[2].equals(host)){
            return failed(this).feedback("csrf-same-host").build();
        }else {
            return success(this).feedback("csrf-review.success").build();
        }
    }

}
