pipeline {
    agent any
    stages {
        stage('Clean Env') {
            steps {
                sh './gradlew clean'
            }
        }

        stage('Check code') {
            steps {
                sh './gradlew :app:detekt'
            }
        }

        stage('Unit test') {
            steps {
                sh './gradlew :app:testProdReleaseUnitTest'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew :app:assembleProdRelease'
            }
        }

//         stage('Publish') {
//             steps {
//                script {
//                    sh ''
//                 }
//             }
//         }

    }

    post {
        failure {
            echo 'build fail, send notification'
        }
        success {
            echo 'send success, send notification'
        }
    }
}