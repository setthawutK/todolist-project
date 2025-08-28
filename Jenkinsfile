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
          docker build -t ${BACK}:${TAG} -f todolist-backend/Dockerfile todolist-backend
          docker tag ${BACK}:${TAG} ${BACK}:latest
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

    stage('Deploy to Kubernetes') {
      steps {
          withCredentials([file(credentialsId: 'k8s-config', variable: 'KUBECONFIG_FILE')]) {
              sh '''
                export KUBECONFIG=$KUBECONFIG_FILE
                kubectl delete pvc postgres-pvc -n todolist --ignore-not-found=true
                kubectl delete pv postgres-pv --ignore-not-found=true

                kubectl apply -f todolist-k8s/storageclass-ebs.yaml
                kubectl apply -f todolist-k8s/postgres-pvc.yaml -n todolist

                kubectl apply -f todolist-k8s/ -n todolist

                kubectl rollout status deployment/todo-backend -n todolist
                kubectl rollout status deployment/todo-frontend -n todolist
              '''
          }
      }
  }


  }



  post {
    success { echo "✅ Pushed ${FRONT}:${TAG} และ :latest สำเร็จ" }
    always  { cleanWs() }
  }
}
