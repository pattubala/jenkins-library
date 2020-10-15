#!/usr/bin/env groovy
def call(body) {
    withSonarQubeEnv('Sonarqube_7.6') {
        sh "mvn sonar:sonar -Dsonar.projectName= ${SONAR_PROJECT_NAME} -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.java.binaries=${SONAR_JAVA_BINARIES} -Dsonar.language=${SONAR_LANGUAGE} -Dsonar.sourceEncoding=UTF-8"
   } 
}
