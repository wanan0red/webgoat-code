package com.wanan.webgoat.container.service;

import com.wanan.webgoat.container.i18n.Messages;
import com.wanan.webgoat.container.i18n.PluginMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LabelService {
    public static final String URL_LABELS_MVC = "/service/labels.mvc";
    private final Messages messages;
    private final PluginMessages pluginMessages;
    @GetMapping(path = URL_LABELS_MVC,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Properties> fetchLeabels(){
        var allProperties = new Properties();
        allProperties.putAll(messages.getMessages());
        allProperties.putAll(pluginMessages.getMessages());
        return new ResponseEntity<>(allProperties, HttpStatus.OK);
    }
}
