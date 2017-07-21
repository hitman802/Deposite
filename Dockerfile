FROM java:8
VOLUME /tmp
ADD ./target/deposites-1.0-SNAPSHOT.war app.war
RUN bash -c "touch /app.war"
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app.war"]