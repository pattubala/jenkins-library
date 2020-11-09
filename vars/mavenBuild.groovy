#!/usr/bin/env groovy
def call(Map args=[:], Closure body={}) {
    node {
		stage ('Maven Build') {
                dir("${args.PROJECT_WORKSPACE_PATH}"){
                    script {
                      sh "mvn clean install"
				}
		    }
		}
        body()
    }
}
