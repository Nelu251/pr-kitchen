FROM openjdk:11
ADD target/kitchen.jar kitchen.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/kitchen.jar"]