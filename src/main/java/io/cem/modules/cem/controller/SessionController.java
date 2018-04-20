package io.cem.modules.cem.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("redirect")
public class SessionController {

    @RequestMapping("diagnose")
    public String redirect(){

        return "../diagnoseNow.html";
    }

}
