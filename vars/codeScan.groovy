#!/usr/bin/env groovy
def call(Map args) {
    withSonarQubeEnv('Sonarqube_7.6') {
        sh "mvn sonar:sonar -Dsonar.projectName= ${args.projectname} -Dsonar.projectKey=${args.projectkey} -Dsonar.java.binaries=${args.javabinaries} -Dsonar.language=${args.language} -Dsonar.sourceEncoding=UTF-8"
   } 
}
