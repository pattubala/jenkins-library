#!/usr/bin/env groovy
def call(Map args=[:], Closure body={}) {
    node {
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
