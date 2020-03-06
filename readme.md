
> # Eureka
## eureka server
*  pom.xml
```yaml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
```

* application.yml
```yaml
server:
  port: 7001


eureka:
  instance:
    hostname: localhost  #eureka服务端的实例名称
  client:
    register-with-eureka: false # false表示不向注册中心注册自己
    fetch-registry: false # false表示自己端就是注册中心，我的职责是维护服务实例，不需要检索服务
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ # 设置与eureka server交互的查询服务和注册服务的地址(单机）

```

* 主启动类
```java
@SpringBootApplication
/**
 *eureka server服务器端启动类注解，接收其他微服务注册进来
 */
@EnableEurekaServer
public class EureKaServer7001 {

    public static void main(String[] args) {
        SpringApplication.run(EureKaServer7001.class, args);
    }

}
```

## eureka client
* pom.xml
```yaml
        <!--eureka client  将provider服务注册进 eureka server端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```

* application.yml
```yaml
server:
  port: 8001

spring:
  application:
    name: microservice-provider   #微服务名称


eureka:
  client:
    service-url:  #注册中心地址列表
      defaultZone: http://localhost:7001/eureka

```

* 主启动类
```java
@SpringBootApplication
/**
 * eureka client端开启注解，表示本服务启动后会注册到eureka server注册中心中
 */
@EnableEurekaClient
public class Provider8001 {

    public static void main(String[] args) {
        SpringApplication.run(Provider8001.class, args);
    }
}

```

## eureka控制台完善
1. 控制台服务名称修改
   增加eureka.instance.instace-id标签

    ```yaml
    eureka:
      client:
        service-url:  #注册中心地址列表
          defaultZone: http://localhost:7001/eureka
      instance:
        instance-id: micoroserviceprovider8001  # 自定义控制中微服务名称
    ```

2. 控制台请求地址修改
    增加eureka.instance.prefer-ip-address=true
    ```yaml
   eureka:
     client:
       service-url:  #注册中心地址列表
         defaultZone: http://localhost:7001/eureka
     instance:
       instance-id: micoroserviceprovider8001  # 自定义控制台中微服务名称
       prefer-ip-address: true  #eureka控制访问路径显示ip 
    ```
3. 控制微服务Info内容修改
* provider8001的pom.xml
  ```yaml
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-actuator</artifactId>
           </dependency> 
    ```
* 父工程的pom.xml
    ```yaml
        <build>
            <finalName>microservice</finalName>
            <resources>
                <resource>
                    <directory>src/main/resources</directory>
                    <filtering>true</filtering>
                </resource>
            </resources>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-resources-plugin</artifactId>
                    <configuration>
                        <delimiters>
                            <delimiter>$</delimiter>
                        </delimiters>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    ```
* provider8001的application.yml
    ```yaml
      info:
        app.name: com.xyz.microservice
        build.artifactId: $project.artifactId$
        build.version: $project.version$
    ```
## eureka自我保护机制
* 某一时刻，某个微服务不可用时，eureka不会立刻清理，依旧会对该微服务的信息进行保存
* 默认情况下，如果eureka server在一定时间(默认是90s)内,没有接收到每个微服务实例的心跳，eureka server将会注销该实例。
* 当网络分区故障发生时，微服务与eureka server之间无法正常通信，以上行为可能变得非常危险了，因为微服务本身其实是健康的，此时本不应该注销这个微服务。eureka通过“自我保护模式来解决这个问题”。
* Eureka自我保护机制，默认是15分钟内收到的续约低于原来的85%（默认），就会开启自我保护。这期间Eureka不会剔除其列表中的实例，即使过90s也不会。
* 禁用自我保护模式
  1. 可以在eureka的server端的application.yml中禁用自我保护
  2. eureka.server.enable-self-preservation=false

## 服务发现

服务provider中增加@EnableDiscovery注解

## eureka server的集群配置
1. 增加2个module，eurekaserver-7002和eurekaserver-7003,并配置到父工程的pom中
2. windows在`C:\Windows\System32\drivers\etc\hosts`文件中增加配置(切记需要增加，否则在7001的控制台看不到7002和7003的集群配置，7002和7003类似)
    ```text
    127.0.0.1 server7001
    127.0.0.1 server7002
    127.0.0.1 server7003
    ```
3. 在eurekaserver-7001的application.yml中修改
   eureka.instance.hostname和eureka.client.service-url.defaultZone标签
    ```yaml
     eureka:
       instance:
         hostname: server7001 #eureka服务端的实例名称
       client:
         register-with-eureka: false # false表示不向注册中心注册自己
         fetch-registry: false # false表示自己端就是注册中心，我的职责是维护服务实例，不需要检索服务
         service-url:
     #      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ # (单机）设置与eureka server交互的查询服务和注册服务的地址
           defaultZone: http://server7002:7002/eureka/,http://server7003:7003/eureka/  #eureka server集群配置
    ```
 
4. 在eurekaserver-7002的application.yml中修改
   eureka.instance.hostname和eureka.client.service-url.defaultZone标签
    ```yaml
    eureka:
      instance:
        hostname: server7002 #eureka服务端的实例名称
      client:
        register-with-eureka: false # false表示不向注册中心注册自己
        fetch-registry: false # false表示自己端就是注册中心，我的职责是维护服务实例，不需要检索服务
        service-url:
    #      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ # (单机）设置与eureka server交互的查询服务和注册服务的地址
          defaultZone: http://server7001:7001/eureka/,http://server7003:7003/eureka/ #eureka server集群配置
    ```
5. 在eurekaserver-7003的application.yml中修改
   eureka.instance.hostname和eureka.client.service-url.defaultZone标签 
   ```yaml
    eureka:
      instance:
        hostname: server7003 #eureka服务端的实例名称
      client:
        register-with-eureka: false # false表示不向注册中心注册自己
        fetch-registry: false # false表示自己端就是注册中心，我的职责是维护服务实例，不需要检索服务
        service-url:
    #      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ # (单机）设置与eureka server交互的查询服务和注册服务的地址
          defaultZone: http://server7001:7001/eureka/,http://server7002:7002/eureka/ #eureka server集群配置
   ```
6. provider8001的application.yml中注册地址改为多个
    eureka.client.service-url.defaultZone标签
    ```yaml
     eureka:
       client:
         service-url:  #注册中心地址列表
     #      defaultZone: http://localhost:7001/eureka/  #单机注册地址
           defaultZone: http://server7001:7001/eureka/,http://server7002:7002/eureka/,http://server7003:7003/eureka/
    ```
    
## eureka与ZK比较
|  |  Eureka |  ZK |
| ---- | ---- | ---
|CAP原则| AP | CP|


> # Ribbon
客户端 负载均衡工具
## Ribbon配置
1. provider8001复制一份provider8002,使provider的微服务有多个实例（需要注意的是，application.yml中的instance_id需要修改)
2. comsumer的pom文件中增加对ribbon的支持
    ```yaml
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
            </dependency>
    
            <!--解决eureka控制台中的info页面错误问题-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-actuator</artifactId>
            </dependency>
    ```
3. consumer的application.yml中增加微服务的配置
   ```yaml
    eureka:
      client:
        service-url:
          defaultZone: http://server7001:7001/eureka/,http://server7002:7002/eureka/,http://server7003:7003/eureka/
      instance:
        instance-id: microserviceconsumer9001
        prefer-ip-address: true
    
    
    spring:
      application:
        name: microservice-consumer
    
    #解决eureka控制台中的Info页面错误问题
    info:
      app.name: com.xyz.microservice
      build.artifactId: $project.artifactId$ #使用maven内置变量project.artifactId和project.version完成对变量的赋值
      build.version: $project.version$
   ```

4.  consumer的restTemplate bean中增加@LoadBalanced注解
> RestTemplate是Spring提供的一个访问Http服务的客户端类

    ```java
    @Configuration
    public class Config {

        @Bean
        @LoadBalanced //ribbon基于客户端的负载均衡工具
        public RestTemplate myRestTemplate() {
            return new RestTemplate();
        }
    }
    ```

5.  consumer的主配置类上增加@EnableEurakeClient注解
     ```java
     @SpringBootApplication
     @EnableEurekaClient
     public class Consumer9001 {
     
         public static void main(String[] args) {
             SpringApplication.run(Consumer9001.class, args);
         }
     }
     ```

6.  consumer的controller中请求地址从地址+端口改为微服务名称
    ```java
    //    private static final String  REST_URL_PREFIX = "http://localhost:8001";
        /**
         * ribbon使用微服务名称调用
         */
        private static final String  REST_URL_PREFIX = "http://microservice-provider";
    ```
7. consumer的请求地址：`http://localhost:9001/consumer/list`实现consumer请求provider的负载均衡

ribbon与eureka整合后，可以直接调用微服务，而不用关心地址和端口
Ribbon是软负载均衡的客户端组件，可以和其他请求的客户端结合使用，与eureka结合只是其中一个示例

## Ribbon核心组件IRule
ribbon提供的负载均衡算法

| 算法名称|  算法说明|
|---- | ----|
|RoundRibbonRule  | |
|RandomRule  | |
|AvailiabilityFilteringRule  | |
|WeightedResonseTimeRule  | |
|RetryRule  | |
|BestAvailiableRule  | |
|ZoneAdvoidaneRule  | |

## ribbon修改默认算法
```yaml
# 修改ribbon默认算法 spring.application.name + ribbon.NFLoadBalancerRuleClassName
microservice-consumer:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
```    

## ribbon自定义算法
在com.xyz.bean.Config中bean

```java
    /**
     * @author xyz
     * @date 2020年2月23日
     * @decription 修改ribbon默认算法
     */
    @Bean
    public IRule myRule() {
        return new RandomRule();
    }
```

> # Feign
声明式的webservice注解，面向接口开发。feign集成了ribbon
使用过程，创建1个接口，在上面增加注解即可
## Feign配置过程
1. 在common-apis项目中增加com.xyz.service.DeptService接口，采用接口+注解的方式配置feign客户端调用(需要在pom中增加对feign的支持)
    ```java
    package com.xyz.service;
    
    import com.xyz.entity.Dept;
    import org.springframework.cloud.openfeign.FeignClient;
    import org.springframework.stereotype.Service;
    import org.springframework.web.bind.annotation.RequestMapping;
    
    /**
     * @author xyz
     * @date 2020年2月24日
     * @decription feign调用的客户端接口
     */
    @FeignClient(value = "microservice-provider")
    @Service
    public interface DeptService {
    
        @RequestMapping("/provider/list")
        Dept list();
    
    }

    ```
2. 创建consumerfeign-9101 module，创建pom文件

    ```yaml
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <parent>
            <artifactId>XyzSpringCloud-Finchley</artifactId>
            <groupId>com.xyz</groupId>
            <version>1.0-SNAPSHOT</version>
        </parent>
        <modelVersion>4.0.0</modelVersion>
    
        <artifactId>ConsumerFeign-9101</artifactId>
    
        <dependencies>
    
            <dependency>
                <groupId>com.xyz</groupId>
                <artifactId>Common-APIs</artifactId>
                <version>${project.version}</version>
            </dependency>
    
            <!--eureka client-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
    
            <!--feign-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-openfeign</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-actuator</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-devtools</artifactId>
            </dependency>
    
        </dependencies>
    
    </project>
    ```
3. consumerfeign-9101创建application.yml文件
    ```yaml
    server:
        port: 9101
    
    
    eureka:
      client:
        service-url:
          defaultZone: http://server7001:7001/eureka/,http://server7002:7002/eureka/,http://server7003:7003/eureka/
      instance:
        instance-id: microserviceconsumerfeign9101
        prefer-ip-address: true
    
    
    spring:
      application:
        name: microservice-consumerfeign
    
    #解决eureka控制台中的Info页面错误问题
    info:
      app.name: com.xyz.microservice
      build.artifactId: $project.artifactId$ #使用maven内置变量project.artifactId和project.version完成对变量的赋值
      build.version: $project.version$
    ``` 

3. consumerfeign-9101 中增加com.xyz.controller.ConsumerFeign方法，实现对feign客户端的调用
    ```java
    package com.xyz.controller;
    
    
    import com.xyz.entity.Dept;
    import com.xyz.service.DeptService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    
    /**
     * @author xyz
     * @date 2020年2月24日
     * @description feign调用的controller层
     */
    @RestController
    public class ConsumerFeign {
    
        @Autowired
        private DeptService deptService;
    
        @RequestMapping("/consumerfeign/list")
        public Dept list() {
            return deptService.list();
        }
    }

    ```
4. consumerfeign-9101主启动类开启对feign的支持
    ```java
    package com.xyz;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
    import org.springframework.cloud.openfeign.EnableFeignClients;
    
    @SpringBootApplication
    @EnableEurekaClient
    /**
     * 开启client端对feign的调用
     */
    @EnableFeignClients
    public class ConsumerFeign9101 {
    
        public static void main(String[] args) {
            SpringApplication.run(ConsumerFeign9101.class, args);
        }
    }

    ```
5. 访问地址
    `http://localhost:9101/consumerfeign/list`    

6. feign集成了ribbon，因此也可以修改默认的算法，代码同ribbon，在
   `com.xyz.bean.Config`类中引用别的算法
 
># Hystrix
扇出  雪崩
较低级别的服务中的服务故障可能导致用户级联故障。当对特定服务的呼叫达到一定阈值时（Hystrix中的默认值为5秒内的20次故障), Hystrix能够保证在一个依赖故障的情况下，不会导致整体服务失败，避免级联故障，提高分布式系统的弹性
当某个服务故障之后，通过断路器的故障监控，向调用方返回一个符合预期的、可处理的响应（fallback)，而不是长时间的等待或者抛出方法无法处理的异常，保证服务调用方的线程不会被长时间、不必要的占用，避免了故障在系统中蔓延，乃至雪崩
服务故障或者异常，当某个异常条件被触发，直接熔断整个服务，而不是一直等到此服务超时

服务降级是在客户端完成的，与服务端无关
当服务被熔断后，服务将不再被调用，客户端准备一个fallback的回调，返回缺省值

##  Ribbon+Hystrix
1. 使用Consumer9001项目改造，pom中引入依赖文件
```xml
        <!--hystrix-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
```
2. 配置熔断方法`com.xyz.controller.ConsumerDeptController`
```java
    @RequestMapping("/consumer/list")
    @HystrixCommand(fallbackMethod = "consumerFallback")
    public Dept list() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/provider/list", Dept.class);
    }

    public Dept consumerFallback() {
        return new Dept().setDeptNo(9001)
                         .setDeptName("ribbon-hystrix-name-9001")
                         .setDeptDesc("ribbon-hystrix-desc-9001");
    }

```
3. 主启动类开启对Hystrix的支持
```java
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class Consumer9001 {

    public static void main(String[] args) {
        SpringApplication.run(Consumer9001.class, args);
    }
}
```
4. 启动注册中心7001、consumer9001，访问`http://localhost:9001/consumer/list`


## Feign+Hystrix
1. 修改com.xyz.service.DeptService接口，支持fallback
    ```java
    @FeignClient(value = "microservice-provider", fallbackFactory = DeptServcieFallBackFactory.class)
    @Service
    public interface DeptService {
    
        @RequestMapping("/provider/list")
        Dept list();
    
    }

    ```
2. 增加fallbackfactory类com.xyz.fallback.DeptServcieFallBackFactory
    ```java
    @Component
    public class DeptServcieFallBackFactory implements FallbackFactory<DeptService> {
    
        @Override
        public DeptService create(Throwable throwable) {
            return new DeptService() {
                @Override
                public Dept list() {
                    return new Dept().setDeptNo(9999)
                                     .setDeptName("fallbackfactory_name")
                                     .setDeptDesc("fackbackfacotry_desc");
                }
            };
        }
    }

    ```
3. 修改consumerFeign-9101项目，在feign中开启hystrix
    ```yaml
    #在Feign中开发Hystrix
    feign:
      hystrix:
        enabled: true
    ```
4. 启动注册中心7001、ConsumerFeign9101，验证`http://localhost:9101/consumerfeign/list` 


> # 熔断监控HystrixDashBoard
## Ribbon+HystrixDashboard
1. 使用Consumer9001改造，pom中增加依赖(actuator/hystrix/hystrixdashboard必备)
```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
        </dependency>
```
2. 启动类上开启注解@EnableHystrixDashboard
```java
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class Consumer9001 {

    public static void main(String[] args) {
        SpringApplication.run(Consumer9001.class, args);
    }
}
```
3. application中暴露监控信息
```yaml
#暴露hystrix dashboard的全部监控信息
management:
  endpoints:
    web:
      exposure:
        include: ["health","info","hystrix.stream"]
```
## Feign + HystrixDashboard
1. 使用ConsumerFeign9101项目改造，pom中引入支持文件
```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
```
2. 修改主配置类，增加@EnableCircuitBreaker和@EnableHystrixDashboard注解
```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ConsumerFeign9101 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeign9101.class, args);
    }
}
```
3. application.yml中暴露actuator全部监控信息
```yaml
#暴露hystrix dashboard的全部监控信息
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

> # 熔断监控聚合-Turbine
1. 新建项目turbie-8201
2. pom中增加依赖
```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-turbine</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-netflix-turbine</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```
3. 主启动类增加注解@EnableTurbine开启对Turbine的支持
```java
@SpringBootApplication
@EnableTurbine
public class TurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurbineApplication.class, args);
    }
}
```
3. application.yml中增加对注册中心和turbine的配置
```yaml
spring:
  application:
    name: microservice-turbine

server:
  port: 8201

turbine:
  aggregator:
    cluster-config: default # 指定聚合那些集群，多个使用“,"分隔，模式default
  app-config: microservice-consumer,microservice-consumerfeign  #配置eureka中的serviceID列表，标明监控那些服务
  cluster-name-expression: new String("default")

eureka:
  client:
    service-url:
      defaultZone: http://server7001:7001/eureka/,http://server7002:7002/eureka/,http://server7003:7003/eureka/
  instance:
    instance-id: MicroServiceTurbine
    prefer-ip-address: true

#解决eureka控制台中的Info页面错误问题
info:
  app.name: com.xyz.microservice
  build.artifactId: $project.artifactId$ #使用maven内置变量project.artifactId和project.version完成对变量的赋值
  build.version: $project.version$

```
4. 打开HystrixDashboard的启动界面`http://localhost:9001/hystrix`或者`http://localhost:9101/hystrix·
输入turbine的地址`http://localhost:8201/turbine.stream`


> # Zuul
实现路由+过滤+代理
## Zuul访问基本配置
1. 创建module Zuul-6001
2. 配置application.yml将Zuul注册到eureka
    ```yaml
    server:
      port: 6001
    
    eureka:
      instance:
        instance-id: Zuul-6001
        prefer-ip-address: true
      client:
        service-url:
          defaultZone: http://server7001:7001/eureka/,http://server7002:7002/eureka/,http://server7003:7003/eureka/
    
    
      #解决eureka控制台中的Info页面错误问题
    info:
      app.name: com.xyz.microservice
      build.artifactId: $project.artifactId$ #使用maven内置变量project.artifactId和project.version完成对变量的赋值
      build.version: $project.version$
    
    spring:
      application:
        name: microservicezuul

    ```
2. 在主启动类中增加对Zuul开启的配置
    ```java
    @SpringBootApplication
    @EnableZuulProxy
    public class Zuul6001 {
    
        public static void main(String[] args) {
            SpringApplication.run(Zuul6001.class, args);
        }
    }

    ```
3. 通过路由访问`http://localhost:6001/microservice-provider/provider/list`

## Zuul路由访问映射规则(application.yml中增加配置)
1. 对微服务名称增加映射地址`http://loclalhost:6001/mydept/provider/list`
2. 禁止原微服务地址访问，只能通过映射地址访问
3. 增加访问前缀`http://localhost:6001/xyz/mydept/provider/list`
    ```yaml
    zuul:
      routes:
        mydept.serviceId: microservice-provider  #原服务名称
        mydept.path: /mydept/**   #映射后的路径
      ignored-services: "*" #禁止所有的服务通过原路径访问
      #  ignored-services: microservice-provider #禁止通过原路径访问
      prefix: /xyz #增加统一的访问前缀
    ```

> # Config
## Config Server
1. 在github中创建配置文件
    ```yaml
    spring:
      profiles:
        active:
        - dev
    
    ---
    server:
      port: 8888
    spring:
      profiles: dev
      application:
        name: microserviceconfig-dev
    my:
      desc: desc-dev
    ---
    server:
      port: 9999
    spring:
      profiles: test
      application:
        name: microserviceconfig-test
    my:
      desc: desc-test
    ```
    重点说明：不允许中在已有的标签下面自定义标签，如：`spring.application.desc`
             但可以从顶级开始自定义标签，如：`my.desc`
2. 创建module ConfigServer-5001
3. 新建application.yml,配置git中的配置文件信息
    ```yaml
    server:
      port: 5001
    spring:
      application:
        name: microserver-config
      cloud:
        config:
          server:
            git:
              uri: https://github.com/buyimoutianxia/microservicerconfig.git
    ```
 
4. pom中增加对ConfigServer的jar包支持
    ```xml
        <dependencies>
    
            <!--SpringCloud 配置中心-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-config-server</artifactId>
            </dependency>
    
            <!--热启动-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-devtools</artifactId>
                <optional>true</optional>
            </dependency>
    
        </dependencies>
    ``` 
5. 新建主启动类,开启对配置中心的支持注解@EnableConfigServer
    ```java
    @SpringBootApplication
    @EnableConfigServer
    public class ConfigServer5001 {
    
        public static void main(String[] args) {
            SpringApplication.run(ConfigServer5001.class, args);
        }
    }

    ```
6. 启动服务，访问路径获取配置信息`http://localhost:5001/application-dev.yml`或者`http://localhost:5001/application/dev`
访问方式如下：

| 序号| 访问路径|
| ----| ---- |
|1 | /{application}/{profile}[/{label}] | 
| 2 |/{application}-{profile}.yml |
|3 | /{label}/{application}-{profile}.yml |
|4 |/{application}-{profile}.properties |
|5 |/{label}/{application}-{profile}.properties |

## Config Client

1. 新建 module ConfigClient
2. pom中增加对配置中心client端的jar支持
    ```xml
        <dependencies>
    
            <!--SpringCloud Config 客户端-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-config</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
    
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-devtools</artifactId>
                <optional>true</optional>
            </dependency>
        </dependencies>
    ```
3. 新建bootstrap.yml（bootstrap.yml 系统级的配置文件，优先级高; application.yml 是用户级别的配置文件）
    ```yaml
    spring:
      cloud:
        config:
          name: application   #从github上获取的资源名称，没有yml后缀
          profile: test #访问的配置项
          label: master  #访问的分支
          uri: http://localhost:5001   #SpringCloud Config Server端的地址

    ```
4. 创建rest访问类`com.xyz.configclient.controller.ClientController`
    ```java
    @RestController
    public class ClientController {
    
        @Value("${server.port}")
        private int port;
    
        @Value("${spring.application.name}")
        private String name;
    
    //    @Value("${spring.application.desc}")
    //    private String desc;
    
        @RequestMapping("/config/client")
        public String rest() {
            return  "port:" + port + " ,name:" + name ;
        }
    
    
    }

    ```
5. 启动类
    ```java
    @SpringBootApplication
    public class ConfigClient5002 {
    
        public static void main(String[] args) {
            SpringApplication.run(ConfigClient5002.class, args);
        }
    }
    ```
6. dev访问路径:`http://localhost:8888/config/client`
   test访问路径:`http://localhost:9999/config/client`

7. 项目只有在启动的时候才会获取配置文件的值，修改github信息后，client端并没有在次去获取