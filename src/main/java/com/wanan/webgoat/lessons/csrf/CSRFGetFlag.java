package com.wanan.webgoat.lessons.csrf;

import com.wanan.webgoat.container.i18n.PluginMessages;
import com.wanan.webgoat.container.session.UserSessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class CSRFGetFlag {
    @Autowired
    UserSessionData userSessionData;
    @Autowired
    private PluginMessages pluginMessages;

    @RequestMapping(path = "/csrf/basic-get-flag",produces = {"application/json"},method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> invoke(HttpServletRequest req){
        Map<String,Object> response = new HashMap<>();
        String host = (req.getHeader("host") == null) ? "NULL": req.getHeader("host");
        String referer = (req.getHeader("referer") == null) ? "NULL" : req.getHeader("referer");
        String[] refererArr = referer.split("/");

        if (referer.equals("NULL")){
            if ("true".equals(req.getParameter("csrf"))){
                Random random = new Random();
                userSessionData.setValue("csrf-get-success",random.nextInt(65536));
                response.put("success",true);
                response.put("message",pluginMessages.getMessage("csrf-get-null-referer.success"));
                response.put("flag",userSessionData.getValue("csrf-get-success"));

            }
        }else if (refererArr[2].equals(host)){
            response.put("success",false);
            response.put("message","Appears the request came from the original host");
            response.put("flag",null);
        }else {
            Random random = new Random();
            userSessionData.setValue("csrf-get-success",random.nextInt(65536));
            response.put("success",null);
            response.put("message",pluginMessages.getMessage("csrf-get-other-referer.success"));
            response.put("flag",userSessionData.getValue("csrf-get-success"));
        }
        return response;
    }
}
