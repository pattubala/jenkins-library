#!/usr/bin/env groovy
def call(Map args=[:], Closure body={}) {
    node {
	    stage ('SCM Checkout') {
            dir("${args.PROJECT_WORKSPACE_PATH}"){
                git (url: "${args.GITHUB_CLONE_URL}",
                    branch: "${args.BRANCH_NAME}",
                    credentialsId: 'Github')
            }
        }
		}
        body()
    }
}
