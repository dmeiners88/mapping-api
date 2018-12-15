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
      steps {
        withMaven() {
          bat 'mvn test'
        }

      }
    }
    stage('Install') {
      steps {
        withMaven() {
          bat 'mvn install'
        }

      }
    }
  }
}