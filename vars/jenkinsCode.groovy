#!/usr/bin/env groovy
def call(Map args=[:], Closure body={}) {
    node {
        stage('Cleanup') {
            steps {
                dir("${args.PROJECT_WORKSPACE_PATH}"){
                    deleteDir()
                }
            }
        }
	    stage ('SCM Checkout') {
            dir("${args.PROJECT_WORKSPACE_PATH}"){
                git (url: "${args.GITHUB_CLONE_URL}",
                    branch: "${args.BRANCH_NAME}",
                    credentialsId: 'Github')
            }
        }
        stage("SONARQUBE STATIC CODE ANALYSIS") {
                dir("${args.PROJECT_WORKSPACE_PATH}"){
				    script {
                        withSonarQubeEnv('Sonarqube_7.6') {
                            sh "mvn sonar:sonar -Dsonar.projectName=${args.SONAR_PROJECT_NAME} -Dsonar.projectKey=${args.SONAR_PROJECT_KEY} -Dsonar.java.binaries=${args.SONAR_JAVA_BINARIES} -Dsonar.language=${args.SONAR_LANGUAGE} -Dsonar.sourceEncoding=UTF-8"
                        }	
						
                }
            }
        }
        stage("QUALITY GATES CHECK") {
                dir("${args.PROJECT_WORKSPACE_PATH}"){
                    timeout(time: 3, unit: 'MINUTES') {
                        echo "Initializing quality gates..."
                        sh 'sleep 10' //small delay because project quality can still being published on previous stage (specially on bigger projects).  
                        def result = waitForQualityGate() //this is enabled by quality gates plugin: https://wiki.jenkins.io/display/JENKINS/Quality+Gates+Plugin
                        if (result.status != 'OK') {
                             error "Pipeline aborted due to quality gate failure: ${result.status}"
                        } else {
                             echo "Quality gate passed with result: ${result.status}"
                        }
                    } 
            }
        }
		stage ('Maven Build') {
                dir("${args.PROJECT_WORKSPACE_PATH}"){
                    script {
                      sh "mvn clean install"
				}
		    }
		}
		stage ('Nexus Upload') {
                dir("${args.PROJECT_WORKSPACE_PATH}"){
                    script {
			            nexusPublisher nexusInstanceId: 'Nexus_3.x', nexusRepositoryId: "${args.ARTIFACTORY_NAME}", packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "${args.APP_LOCAL_PATH}"]], mavenCoordinate: [artifactId: "${args.ARTIFACT_ID}", groupId: "${args.GROUP_ID}", packaging: "${args.PACKAGING_TYPE}", version: "${args.ARTIFACT_VERSION}"]]]
		            }
		    }
		}
		stage ('Deployment') {
                dir("${args.PROJECT_WORKSPACE_PATH}"){
                    script {
			    sh "cp -r ${args.APP_LOCAL_PATH} /var/lib/tomcat8/webapps/${args.APP_NAME}.war"
		            }
		    }
		}
        body()
    }
}
