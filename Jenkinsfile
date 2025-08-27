pipeline {
  agent any
  options { timestamps(); skipDefaultCheckout(true) }
  triggers { pollSCM('H/2 * * * *') }

  environment {
    REPO_URL   = 'https://github.com/setthawutK/todolist-project.git'
    BRANCH     = 'main'
    FRONT_DIR  = 'todolist-frontend'                 // ✅ package.json อยู่ตรงนี้
    DIST_PATH  = 'dist/todo-list-webapp/browser'     // ✅ ของคุณมี browser
    IMAGE_NAME = 'yourdockerhub/todolist-frontend'
    IMAGE_TAG  = 'latest'
  }

  stages {
    stage('Checkout (fresh clone)') {
      steps {
        deleteDir()
        git url: "${REPO_URL}", branch: "${BRANCH}"
        sh 'ls -la'
      }
    }

    stage('Build Angular') {
      steps {
        dir("${FRONT_DIR}") {
          // debug เล็กน้อยให้ชัวร์ว่าเห็น package.json
          sh '''
            echo "PWD on host = $(pwd)"
            test -f package.json || { echo "❌ not found package.json"; ls -la; exit 1; }
          '''
          // build ด้วย Node 22 ใน container
          sh '''
            docker run --rm -v "$PWD":/app -w /app node:22 bash -lc '
              npm install --legacy-peer-deps &&
              node --max_old_space_size=4096 ./node_modules/@angular/cli/bin/ng build --configuration production
            '
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
