pipeline {
    agent any
    stages{
        stage('build gradle service'){
            steps{
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: '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', url: 'https://github.com/Ashish-200/capture-notes.git']])
                sh './gradlew clean build'
            }
        }
  //      stage('build docker image')
  //      {
  //          steps{
  //              script{
  //                  sh 'docker build -t ashish131318/config .'
  //              }
  //          }
  //      }
  //      stage('push to dockerhub')
//{
//    steps{
//        script{
            
//                sh 'docker login -u ashish131318 -p Axelblaze255@'
//                sh 'docker push ashish131318/config'
            //
//        }
//    }
//}

//	stage('kubernetes')
 //       {
 //           steps{
//                script{
                
                  //  sh 'curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64'
                   // sh 'install minikube-linux-amd64 /usr/local/bin/minikube'
                
                //  Start Minikube (if not already running)
 //                   sh 'minikube delete'
//                    sh 'minikube start'
 //                   git 'https://github.com/Ashish-200/config-service.git'
                 // Set the context to Minikube
 //                   sh 'kubectl config use-context minikube'
 //                   sh 'pwd'
 //                   sh 'kubectl apply -f deployment.yaml'
 //                   sh 'kubectl apply -f service.yaml'
 //                   sh 'kubectl get deployments'
 //                   sh 'kubectl get services'
            
           
  //              }
  //          }
    //    }
       
    }
}
