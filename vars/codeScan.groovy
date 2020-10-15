#!/usr/bin/env groovy
def call(body) {
    withSonarQubeEnv('Sonarqube_7.6') {
        sh "mvn sonar:sonar -Dsonar.projectName= ${SONAR-PROJECT-NAME} -Dsonar.projectKey=${SONAR-PROJECT-KEY} -Dsonar.java.binaries=${SONAR-JAVA-BINARIES} -Dsonar.language=${SONAR-LANGUAGE} -Dsonar.sourceEncoding=UTF-8"
   } 
}
