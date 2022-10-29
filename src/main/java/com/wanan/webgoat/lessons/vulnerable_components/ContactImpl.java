package com.wanan.webgoat.lessons.vulnerable_components;

import lombok.Data;

@Data
public class ContactImpl  implements Contact{
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
