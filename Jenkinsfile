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
        // พาธงานของคุณคือ /var/jenkins_home/workspace/<job-name>/todolist-frontend
        sh '''
          docker run --rm \
            -v jenkins_home:/var/jenkins_home \
            -w /var/jenkins_home/workspace/todolist-ci-cd/todolist-frontend \
            node:22 bash -lc "
              npm install --legacy-peer-deps &&
              node --max_old_space_size=4096 ./node_modules/@angular/cli/bin/ng build --configuration production
            "
        '''
      }
      post {
        success {
          archiveArtifacts artifacts: "todolist-frontend/dist/todo-list-webapp/browser/**", allowEmptyArchive: false
        }
      }
    }


    stage('Docker Build (FE only)') {
      steps {
        sh 'docker build -t yourdockerhub/todolist-frontend:latest todolist-frontend'
      }
    }

  }
}
