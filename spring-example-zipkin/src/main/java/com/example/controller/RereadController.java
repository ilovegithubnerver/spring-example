package com.example.controller;

import com.example.model.User;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RereadController {

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * curl http://localhost:8080/get?name=conanli
     * @param name
     * @return
     */
    @RequestMapping("/get")
    public String get(String name) {
        logger.info("some error[get] " + name);
        throw new NullPointerException("some error[get] " + name);
    }

    /**
     * curl http://localhost:8080/form?name=conanli
     * curl -d "name=conanli" http://localhost:8080/form
     * curl -X POST -d "name=conanli" http://localhost:8080/form
     * @param params
     * @return
     */
    @RequestMapping("/form")
    public String form(User params) {
        logger.info("some error[form] " + params.getName());
        throw new NullPointerException("some error[form] " + params.getName());
    }

    /**
     * curl -X POST -H "Content-type:application/json;charset:utf-8" -d "{\"name\":\"conanli\"}" http://localhost:8080/object
     * @param params
     * @return
     */
    @RequestMapping("/object")
    public String object(@RequestBody User params) {
        logger.info("some error[object] " + params.getName());
        throw new NullPointerException("some error[object] " + params.getName());
    }

    /**
     * curl -F "file=@picture.jpg" http://localhost:8080/file
     * curl -F "file=@picture.jpg" -F "name=conanli" http://localhost:8080/file
     * @param file
     * @return
     */
    @RequestMapping("/file")
    public String file(MultipartFile file, User user) {
        logger.info("some error[file] " + file.getName() + ", " + user.getName());
        throw new NullPointerException("some error[file] " + file.getName() + ", " + user.getName());
    }

}
