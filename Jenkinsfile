pipeline {
  agent any
  options { timestamps() }
  triggers { githubPush() }   // ให้ Webhook เรียกอัตโนมัติ

  environment {
    REGISTRY = "docker.io"
    FRONT = "docker.io/kongsetthawut/todo-frontend"
    BACK = "docker.io/kongsetthawut/todo-backend"
    COMMIT = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
    TAG = "${COMMIT}"
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build Frontend Image') {
      steps {
        sh """
          docker build -t ${FRONT}:${TAG} -f todolist-frontend/Dockerfile todolist-frontend
          docker tag ${FRONT}:${TAG} ${FRONT}:latest
        """
      }
    }

    stage('Build Backend Image') {
      steps {
        sh """
          docker build -t docker.io/<your_dockerhub>/todo-backend:${TAG} \
            -f todolist-backend/Dockerfile todolist-backend
          docker tag docker.io/<your_dockerhub>/todo-backend:${TAG} \
                    docker.io/<your_dockerhub>/todo-backend:latest
        """
      }
    }

    stage('Push Frontend Image') {
      steps {
        sh 'docker logout || true'
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'U', passwordVariable: 'P')]) {
          sh 'echo $P | docker login -u $U --password-stdin ${REGISTRY}'
        }
        sh """
          docker push ${FRONT}:${TAG}
          docker push ${FRONT}:latest
        """
        
        sh """
          docker push ${BACK}:${TAG}
          docker push ${BACK}:latest
        """
      }
    }
  }

  post {
    success { echo "✅ Pushed ${FRONT}:${TAG} และ :latest สำเร็จ" }
    always  { cleanWs() }
  }
}
