FROM openjdk:11-jre
EXPOSE 8080
COPY target/springboot-song-blog-*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]