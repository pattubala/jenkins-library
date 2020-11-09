#!/usr/bin/env groovy
def call(Map args=[:], Closure body={}) {
    node {
		stage ('Nexus Upload') {
                dir("${args.PROJECT_WORKSPACE_PATH}"){
                    script {
			            nexusPublisher nexusInstanceId: 'Nexus_3.x', nexusRepositoryId: "${args.ARTIFACTORY_NAME}", packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "${args.APP_LOCAL_PATH}"]], mavenCoordinate: [artifactId: "${args.ARTIFACT_ID}", groupId: "${args.GROUP_ID}", packaging: "${args.PACKAGING_TYPE}", version: "${args.ARTIFACT_VERSION}"]]]
		            }
		    }
		}
		}
        body()
    }
}
