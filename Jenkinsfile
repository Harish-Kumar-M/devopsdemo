pipeline {

    agent any

    environment {
        IMAGE_NAME = 'springbootdemo'
        IMAGE_TAG = 'latest'
        CONTAINER_NAME = 'springbootdemocontainer'
        PORT = '8080'
    }

    stages {

        stage('Build JAR') {
            steps {
                echo 'Starting Stage 1 - Build JAR'

                sh 'chmod +x mvnw'

                sh './mvnw clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Starting Stage 2 - Build Docker Image'

                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG .'
            }
        }

        stage('Push Image to Docker Hub') {
            steps {

                echo 'Starting Stage 3 - Push Image to Docker Hub'

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_HUB_USERNAME',
                        passwordVariable: 'DOCKER_HUB_PASSWORD'
                    )
                ]) {

                    sh '''
                        echo "$DOCKER_HUB_PASSWORD" | docker login \
                            -u "$DOCKER_HUB_USERNAME" \
                            --password-stdin

                        docker tag "$IMAGE_NAME:$IMAGE_TAG" \
                            "$DOCKER_HUB_USERNAME/$IMAGE_NAME:$IMAGE_TAG"

                        docker push \
                            "$DOCKER_HUB_USERNAME/$IMAGE_NAME:$IMAGE_TAG"

                        docker logout

                        docker rmi "$IMAGE_NAME:$IMAGE_TAG" || true
                    '''
                }
            }
        }

        stage('Deploy Container') {
            steps {

                echo 'Starting Stage 4 - Deploy Container'

                sh '''
                    docker stop "$CONTAINER_NAME" || true

                    docker rm "$CONTAINER_NAME" || true

                    docker run -d \
                        --name "$CONTAINER_NAME" \
                        -p "$PORT:$PORT" \
                        "$DOCKER_HUB_USERNAME/$IMAGE_NAME:$IMAGE_TAG"

                    docker image prune -f
                '''
            }
        }
    }

    post {
        success {
            echo 'Jenkins Pipeline Completed Successfully'
        }

        failure {
            echo 'Jenkins Pipeline Failed'
        }
    }
}