FROM openjdk:17-oracle
RUN microdnf install findutils

WORKDIR ./

COPY ./AirplaneTracer_WebApp_Middleware ./AirplaneTracer_WebApp_Middleware

WORKDIR ./AirplaneTracer_WebApp_Middleware/
CMD ["./gradlew", "init"]
CMD ["./gradlew", "run"]

