package com.xyz.microservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xyz
 * @date 2020年3月19日
 * @description  API网关本地跳转的服务
 */
@RestController
public class MyController {

   @RequestMapping("/local/provider/list")
   public String myForward() {
      return "local forward";
   }

}
