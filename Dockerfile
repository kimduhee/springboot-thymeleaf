#FROM openjdk:17
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM tomcat:10.1.17-jdk17
#ENV TZ=Asia/Seoul
#RUN rm -Rf /usr/local/tomcat/webapps/ROOT
COPY build/libs/*.war /usr/local/tomcat/webapps/ROOT.war