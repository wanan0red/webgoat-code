package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.session.LabelDebugger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.util.Map;

@Controller
@Slf4j
@AllArgsConstructor
public class LabelDebugService {
    private static final String URL_DEBUG_LABELS_MVC = "/service/debug/labels.mvc";
    private static final String KEY_ENABLED = "enabled";
    private static final String KEY_SUCCESS = "success";

    private LabelDebugger labelDebugger;

    @RequestMapping(path = URL_DEBUG_LABELS_MVC,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Map<String,Object>> checkDebuggingStatus(){
        log.debug("Checking label debugging,it is {}",labelDebugger.isEnabled());
        Map<String,Object> result = createResponse(labelDebugger.isEnabled());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @RequestMapping(value = URL_DEBUG_LABELS_MVC,produces = MediaType.APPLICATION_JSON_VALUE,params = KEY_ENABLED)
    public @ResponseBody ResponseEntity<Map<String,Object>> setDebuggingStatus(@RequestParam("enabled") Boolean enabled){
        log.debug("Setting label debugging to {}",labelDebugger.isEnabled());
        Map<String,Object> result =  createResponse(enabled);
        labelDebugger.setEnabled(enabled);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    private Map<String,Object> createResponse(Boolean enabled) {
        return Map.of(KEY_SUCCESS,Boolean.TRUE,KEY_ENABLED,enabled);
    }
}
