def call(Map stageParams) {
    withSonarQubeEnv('Sonarqube_7.6') {
       sh "mvn sonar:sonar -Dsonar.projectName=stageParams.sonar_project_name -Dsonar.projectKey=stageParams.sonar_project_key -Dsonar.java.binaries=stageParams.sonar_java_binaries -Dsonar.language=stageParams.sonar_language -Dsonar.sourceEncoding=UTF-8"
   } 
}
