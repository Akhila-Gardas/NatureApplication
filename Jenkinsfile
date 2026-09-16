pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t nature-java-app .'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    docker stop nature-app || true
                    docker rm nature-app || true

                    docker run -d \
                        --name nature-app \
                        -p 8081:8080 \
                        nature-java-app
                '''
            }
        }
    }

    post {
        success {
            echo '🌿 Nature Java App deployed successfully!'
        }

        failure {
            echo '❌ Deployment failed.'
        }
    }
}