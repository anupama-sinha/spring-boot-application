package com.anupama.sinha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//@RestController
@Controller
public class MyController {

    Logger logger = LoggerFactory.getLogger("MyController.class");

    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public String test(){
        logger.info("Am invoking test(), Anupama. Check if u got response...");
        //This search. Adds prefix(/WEB-INF/pages/) & Adds suffix(.jsp) when @Controller is used
        return "anupama";
    }

    @RequestMapping(path = "/greet/{name}",method = RequestMethod.GET)
    public String hello(@PathVariable("name") String name, ModelMap modelMap){
        logger.info("Am invoking greet(), Anupama. Check if u got response...");
        modelMap.addAttribute("time",String.valueOf(System.currentTimeMillis()));

        modelMap.addAttribute("name",name);
        //This search. Adds prefix(/WEB-INF/pages/) & Adds suffix(.jsp) when @Controller is used
        return "hello";
    }

}