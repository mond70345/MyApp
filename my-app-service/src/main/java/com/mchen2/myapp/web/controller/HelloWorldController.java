package com.mchen2.myapp.web.controller;

import com.eingsoft.emop.dto.RestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/hello-world/")
@RestController
public class HelloWorldController {

    @GetMapping("/say")
    public RestResponse<String> sayHello() {
        return RestResponse.success("Hello World");
    }


}
