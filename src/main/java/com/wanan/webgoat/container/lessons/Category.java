package com.wanan.webgoat.container.lessons;

import lombok.Getter;

public enum Category {
    INTRODUCTION("Introduction",5),
    GENERAL("General",100),


    A1("(A1) Broken Access Control", 301),
    A2("(A2) Cryptographic Failures", 302),
    A3("(A3) Injection", 303),

    A5("(A5) Security Misconfiguration", 305),
    A6("(A6) Vuln & Outdated Components", 306),
    A7("(A7) Identity & Auth Failure", 307),
    A8("(A8) Software & Data Integrity", 308),
    A9("(A9) Security Logging Failures", 309),
    A10("(A10) Server-side Request Forgery", 310),

    CLIENT_SIDE("Client side", 1700),

    CHALLENGE("Challenges", 3000);

    @Getter
    private String name;
    @Getter
    private Integer ranking;
    Category(String name,Integer ranking){
        this.name= name;
        this.ranking = ranking;
    }

    @Override
    public String toString(){return getName();}

}
