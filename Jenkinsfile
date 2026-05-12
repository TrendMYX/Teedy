pipeline {
  agent any

  stages {
    stage('Clean') {
      steps {
        sh 'mvn --batch-mode -Ddocs.home="$WORKSPACE/data/docs" clean'
      }
    }

    stage('Prepare Test Storage') {
      steps {
        sh 'rm -rf "$WORKSPACE/data/docs" && mkdir -p "$WORKSPACE/data/docs/storage"'
      }
    }

    stage('Compile') {
      steps {
        sh 'mvn --batch-mode -Ddocs.home="$WORKSPACE/data/docs" compile'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn --batch-mode -Ddocs.home="$WORKSPACE/data/docs" test -Dmaven.test.failure.ignore=true'
      }
    }

    stage('Install Local Artifacts') {
      steps {
        sh 'mvn --batch-mode -Ddocs.home="$WORKSPACE/data/docs" install -DskipTests'
      }
    }

    stage('PMD') {
      steps {
        sh 'mvn --batch-mode -Ddocs.home="$WORKSPACE/data/docs" pmd:pmd'
      }
    }

    stage('JaCoCo') {
      steps {
        sh 'mvn --batch-mode -Ddocs.home="$WORKSPACE/data/docs" jacoco:report'
      }
    }

    stage('Site') {
      steps {
        sh 'mvn --batch-mode -Ddocs.home="$WORKSPACE/data/docs" site'
      }
    }

    stage('Package') {
      steps {
        sh 'mvn --batch-mode -Ddocs.home="$WORKSPACE/data/docs" package -DskipTests'
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
