package com.wanan.webgoat.lessons.vulnerable_components;

import com.thoughtworks.xstream.XStream;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"vulnerable.hint"})
public class VulnerableComponentsLesson extends AssignmentEndpoint {

    @PostMapping("/VulnerableComponents/attack1")
    public @ResponseBody
    AttackResult completed(@RequestParam String payload) {
        XStream xstream = new XStream();
        xstream.setClassLoader(Contact.class.getClassLoader());
        xstream.alias("contact", ContactImpl.class);
        xstream.ignoreUnknownElements();
        Contact contact = null;

        try {
            if (!StringUtils.isEmpty(payload)) {
                payload = payload.replace("+", "").replace("\r", "").replace("\n", "").replace("> ", ">").replace(" <", "<");
            }
            contact = (Contact) xstream.fromXML(payload);
        } catch (Exception ex) {
            return failed(this).feedback("vulnerable-components.close").output(ex.getMessage()).build();
        }

        try {
            if (null != contact) {
                contact.getFirstName();
            }
            if (!(contact instanceof ContactImpl)) {
                return success(this).feedback("vulnerable-components.success").build();
            }
        } catch (Exception e) {
            return success(this).feedback("vulnerable-components.success").output(e.getMessage()).build();
        }
        return failed(this).feedback("vulnerable-components.fromXML").feedbackArgs(contact).build();
    }
}
