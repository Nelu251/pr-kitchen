FROM openjdk:11
ADD target/kitchen.jar kitchen.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/kitchen.jar"]