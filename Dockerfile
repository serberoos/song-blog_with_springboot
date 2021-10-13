FROM openjdk:11-jre
COPY target/springboot-song-blog-*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]