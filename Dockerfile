FROM openjdk:11
ADD target/kitchen.jar kitchen.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/kitchen.jar"]