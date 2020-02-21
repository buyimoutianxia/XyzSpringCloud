# SpringCloud

## Eureka
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

### eureka client
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

### eureka控制完善
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
                            <delimit>$</delimit>
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
### eureka自我保护机制
