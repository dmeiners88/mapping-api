pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        withMaven() {
          bat 'mvn clean compile -DskipTests'
        }

      }
    }
  }
}