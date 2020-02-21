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
### eureka自我保护机制
* 某一时刻，某个微服务不可用时，eureka不会立刻清理，依旧会对该微服务的信息进行保存
* 默认情况下，如果eureka server在一定时间内没有接收到每个微服务实例的心跳，eureka server将会注销该实例。
* 当网络分区故障发生时，微服务与eureka server之间无法正常通信，以上行为可能变得非常危险了，因为微服务本身其实是健康的，此时本不应该注销这个微服务。eureka通过“自我保护模式来解决这个问题”。
* 在自我保护模式中，eureka server会保护服务注册表中的信息，不再注销任何服务实例。当它收到的心跳数重新恢复到阀值以上时，该eureka server节点就会自动退出自我保护模式。
* 禁用自我保护模式
  1. 可以在eureka的server端的application.yml中禁用自我保护
  2. eureka.server.enable-self-preservation=false

### 服务发现

服务provider中增加@EnableDiscovery注解

### eureka server的集群配置
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