package org.motechproject.carereporting.xml.mapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {

    private String login;

    @XmlAttribute
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
