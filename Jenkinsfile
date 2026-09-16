pipeline {

    agent any

    tools {
        maven 'maven3'
        jdk 'jdk17'
    }

    environment {
        SCANNER_HOME = tool 'sonar'
        IMAGE_NAME = 'nature-java-app'
        CONTAINER_NAME = 'nature-app'
    }

    stages {

        stage('Checkout') {
            steps {
                echo '📥 Checking out Java Nature Application...'

                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Building Java application...'

                sh '''
                    mvn clean package -DskipTests
                '''
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Running tests...'

                sh '''
                    mvn test
                '''
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo '🔍 Running SonarQube analysis...'

                withSonarQubeEnv('sonar-cred') {
                    sh '''
                        ${SCANNER_HOME}/bin/sonar-scanner \
                        -Dsonar.projectKey=nature-java-app \
                        -Dsonar.projectName=Nature-Java-App \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.java.binaries=target/classes
                    '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo '🐳 Building Docker image...'

                sh '''
                    docker build \
                    -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo '🚀 Deploying application...'

                sh '''
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true

                    docker run -d \
                        --name ${CONTAINER_NAME} \
                        -p 8081:8080 \
                        ${IMAGE_NAME}:latest
                '''
            }
        }

        stage('Verify Application') {
            steps {
                echo '🔎 Checking application...'

                sh '''
                    sleep 10

                    docker ps

                    curl -f http://localhost:8081

                    echo ""
                    echo "🌿 Nature Java Application is running!"
                '''
            }
        }
    }

    post {

        success {
            echo '=========================================='
            echo '🌿 Nature Java Application Deployed!'
            echo '=========================================='

            echo 'Application URL: http://15.206.203.253:8081'
        }

        failure {
            echo '❌ Pipeline failed. Check the Jenkins console output.'
        }
    }
}
