pipeline {
  agent any

  options { timestamps() }

  triggers {
    // ใช้ IP ภายในอยู่ → Poll SCM ไปก่อน (จนกว่าจะทำ webhook)
    pollSCM('H/2 * * * *')
  }

  environment {
    FRONT_DIR  = 'todolist-frontend'                 // แก้ให้ตรงชื่อโฟลเดอร์จริง
    DIST_PATH  = 'dist/todo-list-webapp/browser'     // ✅ ของคุณมี browser
    IMAGE_NAME = 'yourdockerhub/todolist-frontend'   // ถ้ายังไม่ push จะตั้งหรือไม่ตั้งก็ได้
    IMAGE_TAG  = 'latest'
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build Angular') {
      steps {
        dir("${FRONT_DIR}") {
          // ใช้ container Node เพื่อไม่ต้องลง Node บน Jenkins host
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
          // เก็บไฟล์ build เป็น artifact (โหลดจากหน้า Jenkins ได้)
          archiveArtifacts artifacts: "${FRONT_DIR}/${DIST_PATH}/**", allowEmptyArchive: false
        }
      }
    }

    stage('Docker Build (FE only, no Nginx)') {
      steps {
        dir("${FRONT_DIR}") {
          sh """
            docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
          """
        }
      }
    }

    // ถ้าต้องการ push image → เปิดใช้บล็อกนี้ และสร้าง credentials dockerhub-creds ใน Jenkins
    // stage('Docker Push') {
    //   steps {
    //     withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
    //       sh '''
    //         echo "$PASS" | docker login -u "$USER" --password-stdin
    //         docker push ${IMAGE_NAME}:${IMAGE_TAG}
    //       '''
    //     }
    //   }
    // }

    // ถ้าพร้อมไป K8s ค่อยเปิด stage นี้ภายหลัง
    // stage('Deploy to K8s') {
    //   steps {
    //     sh 'kubectl apply -f k8s/frontend.yaml'
    //   }
    // }
  }
}
