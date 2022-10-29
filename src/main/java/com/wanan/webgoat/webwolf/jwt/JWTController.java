package com.wanan.webgoat.webwolf.jwt;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
//RestController 是@Controller和@ResponseBay 的结合
//@Controller 将当前修饰的类注入到springboot ioc容器,使得从该类所在的项目跑起来的过程中,这个类就被实例化
//@ResponseBody 它的作用简短的说就是指该类中的api即可返回的数据 不管对应方法返回map或者其他的object,它会以json字符串的形式返回给客户端
public class JWTController {
    @GetMapping("/jwt")
    public ModelAndView jwt(){
        return new ModelAndView("jwt");
    }

    @PostMapping(value = "/WebWolf/jwt/decode", consumes = APPLICATION_FORM_URLENCODED_VALUE, produces = APPLICATION_JSON_VALUE)
// 接受form表单数据 返回josn数据
    public JWTToken decode(@RequestBody MultiValueMap<String, String> formData) {
        var jwt = formData.getFirst("token");
        var secretKey = formData.getFirst("secretKey");
        return JWTToken.decode(jwt, secretKey);
    }

    @PostMapping(value = "/WebWolf/jwt/encode", consumes = APPLICATION_FORM_URLENCODED_VALUE, produces = APPLICATION_JSON_VALUE)
    public JWTToken encode(@RequestBody MultiValueMap<String, String> formData) {
        var header = formData.getFirst("header");
        var payload = formData.getFirst("payload");
        var secretKey = formData.getFirst("secretKey");
        return JWTToken.encode(header, payload, secretKey);
    }

}