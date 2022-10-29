package com.wanan.webgoat.lessons.csrf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Review {
    private String user;
    private String dateTime;
    private String  text;
    private Integer stars;
}
