# Docker 이미지의 베이스 이미지를 지정
FROM adoptopenjdk/openjdk11:alpine-slim

# 컨테이너 내부에 애플리케이션을 설치할 디렉토리를 생성
RUN mkdir /app

# 작업 디렉토리를 /app으로 설정
WORKDIR /app

# 호스트 시스템에 있는 빌드된 JAR 파일을 컨테이너 내부의 /app 디렉토리로 복사
COPY target/*.jar /app/app.jar

# 컨테이너가 시작될 때 실행할 명령을 지정
CMD ["java", "-jar", "/app/app.jar"]
