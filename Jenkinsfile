pipeline {
  agent any
  options { timestamps() }
  triggers { githubPush() }

  environment {
    REGISTRY = "docker.io"
    FRONT = "docker.io/kongsetthawut/todo-frontend"
    COMMIT = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
    TAG = "${COMMIT}"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build Frontend Image') {
      steps {
        sh """
          docker build -t ${FRONT}:${TAG} -f todolist-frontend/Dockerfile todolist-frontend
          docker tag ${FRONT}:${TAG} ${FRONT}:latest
        """
      }
    }

    stage('Push Frontend Image') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'U', passwordVariable: 'P')]) {
          sh "echo \$P | docker login -u \$U --password-stdin ${REGISTRY}"
        }
        sh """
          docker push ${FRONT}:${TAG}
          docker push ${FRONT}:latest
        """
      }
    }
  }

  post {
    success { echo "✅ Frontend build & push complete!" }
    failure { echo "❌ Frontend build failed!" }
    always { cleanWs() }
  }
}
