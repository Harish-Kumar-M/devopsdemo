pipeline {
    agent any

    environment {
        IMAGE_NAME = 'springbootdemo'
        IMAGE_TAG = 'latest'
        CONTAINER_NAME = 'springbootdemocontainer'
        PORT = '8080'

        DOCKER_HUB = credentials('dockerhub-creds')
    }

    stages {

        stage('Build JAR') {
            steps {
                sh '''
                    chmod +x mvnw
                    ./mvnw clean package
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t "$IMAGE_NAME:$IMAGE_TAG" .'
            }
        }

        stage('Push Image') {
            steps {
                sh '''
                    echo "$DOCKER_HUB_PSW" | docker login \
                        -u "$DOCKER_HUB_USR" \
                        --password-stdin

                    docker tag "$IMAGE_NAME:$IMAGE_TAG" \
                        "$DOCKER_HUB_USR/$IMAGE_NAME:$IMAGE_TAG"

                    docker push \
                        "$DOCKER_HUB_USR/$IMAGE_NAME:$IMAGE_TAG"

                    docker logout
                '''
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                    docker stop "$CONTAINER_NAME" || true
                    docker rm "$CONTAINER_NAME" || true

                    docker run -d \
                        --name "$CONTAINER_NAME" \
                        -p "$PORT:$PORT" \
                        "$DOCKER_HUB_USR/$IMAGE_NAME:$IMAGE_TAG"
                '''
            }
        }
    }
}