pipeline {
  agent any

  options { 
    timestamps()
    skipDefaultCheckout(true)   // ⬅️ สำคัญ ปิด checkout อัตโนมัติ
  }

  triggers { pollSCM('H/2 * * * *') }

  environment {
    REPO_URL  = 'https://github.com/setthawutK/todolist-project.git'
    BRANCH    = 'main'
    FRONT_DIR = 'todolist-frontend'
    DIST_PATH = 'dist/todo-list-webapp/browser'
    IMAGE_NAME = 'yourdockerhub/todolist-frontend'
    IMAGE_TAG  = 'latest'
  }

  stages {
    stage('Checkout (fresh clone)') {
      steps {
        deleteDir()                           // ล้าง workspace
        git url: "${REPO_URL}", branch: "${BRANCH}"   // ⬅️ clone เองชัดๆ
        sh 'ls -la'
      }
    }

    stage('Build Angular') {
      steps {
        dir("${FRONT_DIR}") {
          sh '''
            docker run --rm -v "$PWD":/app -w /app node:22 bash -lc "
              npm ci --legacy-peer-deps &&
              npm run build --prod
            "
          '''
        }
      }
      post {
        success {
          archiveArtifacts artifacts: "${FRONT_DIR}/${DIST_PATH}/**", allowEmptyArchive: false
        }
      }
    }

    stage('Docker Build (FE only)') {
      steps {
        dir("${FRONT_DIR}") {
          sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
        }
      }
    }
  }
}
