pipeline {
    agent any

    environment {
        EC2_USER = 'ec2-user'
        EC2_HOST = '52.91.201.217'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/varunkanneganti125/SB-APP.git', branch: 'master'
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent(['ec2-ssh-key']) {
                    sh '''
                    echo "Connecting to EC2 and deploying the Spring Boot app"

                    ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} << 'EOF'

                        echo "Navigating to project directory"
                        cd /home/ec2-user/SB-APP || {
                          echo "Directory not found. Cloning the project..."
                          git clone https://github.com/varunkanneganti125/SB-APP.git SB-APP
                          cd SB-APP
                        }

                        echo "Pulling latest code"
                        git reset --hard
                        git pull origin master

                        echo "Giving executable permissions to mvnw"
                        chmod +x mvnw

                        echo "Building project with Maven"
                        ./mvnw clean package -DskipTests

                        echo "Stopping old application if running"
                        pkill -f 'java -jar' || true

                        echo "Starting new application"
                        nohup java -jar target/*.jar > output.log 2>&1 &

                        echo "Deployment completed"

                    EOF
                    '''
                }
            }
        }
    }
}
