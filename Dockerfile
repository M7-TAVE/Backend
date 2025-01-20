FROM openjdk:17

# 환율 api 인증서 설정
ARG CERT_FILE=./certs/_.koreaexim.go.kr.crt
USER root
COPY ${CERT_FILE} /certs/${CERT_FILE}
RUN keytool -import -noprompt -trustcacerts -alias koreaexim -file /certs/${CERT_FILE} -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit

ARG JAR_FILE=build/libs/travelbag-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]
