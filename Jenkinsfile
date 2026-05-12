pipeline {
  agent any

  stages {
    stage('Clean') {
      steps {
        sh 'mvn --batch-mode clean'
      }
    }

    stage('Compile') {
      steps {
        sh 'mvn --batch-mode compile'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn --batch-mode test -Dmaven.test.failure.ignore=true'
      }
    }

    stage('Install Local Artifacts') {
      steps {
        sh 'mvn --batch-mode install -DskipTests'
      }
    }

    stage('PMD') {
      steps {
        sh 'mvn --batch-mode pmd:pmd'
      }
    }

    stage('JaCoCo') {
      steps {
        sh 'mvn --batch-mode jacoco:report'
      }
    }

    stage('Site') {
      steps {
        sh 'mvn --batch-mode site'
      }
    }

    stage('Package') {
      steps {
        sh 'mvn --batch-mode package -DskipTests'
      }
    }
  }

  post {
    always {
      junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
      archiveArtifacts artifacts: '**/target/site/**/*.*', fingerprint: true, allowEmptyArchive: true
      archiveArtifacts artifacts: '**/target/**/*.jar', fingerprint: true, allowEmptyArchive: true
      archiveArtifacts artifacts: '**/target/**/*.war', fingerprint: true, allowEmptyArchive: true
    }
  }
}
