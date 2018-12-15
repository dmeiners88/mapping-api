pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        withMaven() {
          bat 'mvn -B -DskipTests clean package'
        }

      }
    }
    stage('Test') {
      parallel {
        stage('Unit Tests') {
          steps {
            withMaven() {
              bat 'mvn test'
            }
          }
        }
        stage('Verify') {
          steps {
            withMaven() {
              bat 'mvn -DskipTests verify'
            }

          }
        }
      }
    }
    stage('Install') {
      steps {
        withMaven() {
          bat 'mvn -DskipTests -Drevapi.skip install'
        }

      }
    }
  }
}
