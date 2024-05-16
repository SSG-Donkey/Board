# SHINSEGAE-final
## 1. AWS apiGateway를 통해 통신
## 2. 세션정보AWS Redis이용
### 2-1. gradle에 redis 의존성 추가 
```
build.gradle

implementation 'org.springframework.session:spring-session-data-redis:2.7.0'
```

### 2-2. EKS pod에 Redis service 실행시키는 yaml파일
```
apiVersion: v1
kind: Service
metadata:
  name: redis-service
spec:
  selector:
    app: redis
  ports:
    - protocol: TCP
      port: 6379
      targetPort: 6379
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: redis-deployment
spec:
  replicas: 1
  selector:
    matchLabels:
      app: redis
  template:
    metadata:
      labels:
        app: redis
    spec:
      containers:
        - name: redis
          image: redis:latest
          ports:
            - containerPort: 6379
            
pod에 접속
kubectl exec -it <redis pod 이름> redis-cli
```
### 2-3. application.properties에 Redis 연결정보 추가
```
application.properties
spring.redis.host=<aws_elasticache_cluster의 endpoint 또는 pod의 cluster-ip>
spring.redis.port=6379
spring.redis.password=redisPassword # Redis.tf에 password가 없을경우 output "cluster_password"를 통해 확인

application.yml
spring:
    redis:
    host: <aws_elasticache_cluster의 endpoint 또는 pod의 cluster-ip>
    port: 6379
    password: redisPassword # Redis.tf에 password가 없을경우 output "cluster_password"를 통해 확인
```
### 2-4. Redis 사용을 위해 BackendApplication.java 파일에 @EnableRedisHttpSession 어노테이션추가
```
BackendApplication.java

@SpringBootApplication
@EnableRedisHttpSession
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}       
```
### 2-5. Redis Controller (예시)  git 
```
public class RedisController {

    @GetMapping("api/session")
    public String getSessionId(HttpSession session) {
        log.info("RedisController 진입");
        session.setAttribute("name", "treesick");
        return session.getId();
    }
}
```
### pod에서 Redis cache Cluster 구축하는법
https://tech.kakao.com/2022/02/09/k8s-redis/

### Redis를 session Storage로 사용하는 법
https://escapefromcoding.tistory.com/702
