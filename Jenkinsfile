pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        ws(dir: 'mapping-api Maven Build') {
          build 'mapping-api Maven Build'
        }

      }
    }
  }
}