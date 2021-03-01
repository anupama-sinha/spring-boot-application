package com.anupama.sinha;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class MyService {
    Map<Integer,String> empMap;

    public String getName(Integer id){
        return getNameinDB(id);
    }

    @Cacheable(cacheNames = "emp-cache")
    //private will not work for cache
//    private String getNameinDB(Integer id){
    public String getNameinDB(Integer id){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return empMap.get(id);
    }

    @PostConstruct //Post Bean Creation
    void createEmpMap(){
        empMap = new HashMap<>();
        empMap.put(1,"Anu");
        empMap.put(2,"Vivekananda");
        empMap.put(3,"Eckhart");
    }
}