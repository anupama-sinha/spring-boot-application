package com.anupama.sinha;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class MyRestController {

    Logger logger = LoggerFactory.getLogger("MyController.class");

    @Autowired
    private MyService myService;

    @Autowired
    private StringUtils stringUtils;

    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public String test(){
        logger.info("Am invoking test(), Anupama. Check if u got response...");
        return "anupama";
    }

    @RequestMapping(path = "/greet/{name}",method = RequestMethod.GET)
    public String hello(@PathVariable("name") String name, ModelMap modelMap){
        logger.info("Am invoking greet(), Anupama. Check if u got response...");
       return "hello";
    }

    @RequestMapping(path = "/testmap",method = RequestMethod.GET, produces = "application/json")
    public Map testMap(){
        Map<String,String> map = new HashMap<>();
        map.put("name","Anupama");
        return map;
    }

    @RequestMapping(path = "/testemp",method = RequestMethod.GET, produces = "application/json")
    public Emp testObject(){
        Emp emp = new Emp();
        emp.setId(1);
        emp.setName("Anu");
        return emp;
    }

    @RequestMapping(path = "/testemp/{id}",method = RequestMethod.GET)
    public String testObjectId(@PathVariable("id") Integer id){
        //Below not used. Instead Cache method called directly
        //return myService.getName(id);
        String name = myService.getNameinDB(id);

        //StringUtils stringUtils = new StringUtils();
        name = stringUtils.upperCase(name);
        logger.info("Controller called for id:{}", id);
        return name;
    }
}