#!/usr/bin/env groovy
def call(Map args=[:], Closure body={}) {
    node {
        stage("SONARQUBE STATIC CODE ANALYSIS") {
                dir("${args.PROJECT_WORKSPACE_PATH}"){
				    script {
                        withSonarQubeEnv('Sonarqube_7.6') {
                            sh "mvn sonar:sonar -Dsonar.projectName=${args.SONAR_PROJECT_NAME} -Dsonar.projectKey=${args.SONAR_PROJECT_KEY} -Dsonar.java.binaries=${args.SONAR_JAVA_BINARIES} -Dsonar.language=${args.SONAR_LANGUAGE} -Dsonar.sourceEncoding=UTF-8"
                        }	
						
                }
            }
        }
		}
        body()
    }
}
