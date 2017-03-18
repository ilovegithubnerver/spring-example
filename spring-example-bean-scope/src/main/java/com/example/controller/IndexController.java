package com.example.controller;

import com.example.model.PrototypeObj;
import com.example.model.RequestObj;
import com.example.model.SessionObj;
import com.example.model.SingletonObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Scope("prototype")
public class IndexController {

    @Autowired
    private SingletonObj singletonObj;
    @Autowired
    private SingletonObj singletonObj2;
    @Autowired
    private PrototypeObj prototypeObj;
    @Autowired
    private PrototypeObj prototypeObj2;
    @Autowired
    private RequestObj requestObj;
    @Autowired
    private RequestObj requestObj2;
    @Autowired
    private SessionObj sessionObj;
    @Autowired
    private SessionObj sessionObj2;

    @RequestMapping("/")
    public List<String> index() {
        List<String> list = new ArrayList<>();
        list.add("first  time singleton is :" + singletonObj);
        list.add("second time singleton is :" + singletonObj2);

        list.add("first  time prototype is :" + prototypeObj);
        list.add("second time prototype is :" + prototypeObj2);

        list.add("first  time request is :" + requestObj);
        list.add("second time request is :" + requestObj2);

        list.add("first  time session is :" + sessionObj);
        list.add("second time session is :" + sessionObj2);

        for (String str : list)
            System.out.println(str);
        return list;
    }

}
