pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        withMaven() {
          bat 'mvn clean compile -DskipTests'
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