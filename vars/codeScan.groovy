#!/usr/bin/env groovy
def call(body) {
    withSonarQubeEnv('Sonarqube_7.6') {
        sh "mvn sonar:sonar -Dsonar.projectName= ${projectname} -Dsonar.projectKey=${projectkey} -Dsonar.java.binaries=${javabinaries} -Dsonar.language=${language} -Dsonar.sourceEncoding=UTF-8"
   } 
}
