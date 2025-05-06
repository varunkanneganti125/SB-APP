pipeline {
    agent any

    environment {
        EC2_USER = 'ec2-user'
        EC2_HOST = '18.205.105.152'
        PROJECT_DIR = '/home/ec2-user/SB-APP'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'master', url: 'https://github.com/varunkanneganti125/SB-APP.git'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent(credentials: ['ec2-ssh-key']) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} << 'EOF'
                        echo "Connecting to EC2 instance..."

                        # Go to project directory or clone it
                        if [ -d "${PROJECT_DIR}" ]; then
                            cd ${PROJECT_DIR}
                            git reset --hard
                            git pull origin master
                        else
                            cd /home/${EC2_USER}
                            git clone https://github.com/varunkanneganti125/SB-APP.git
                            cd SB-APP
                        fi

                        # Ensure mvnw is executable
                        chmod +x mvnw

                        echo "Building project on EC2..."
                        ./mvnw clean package -DskipTests

                        echo "Stopping old Spring Boot app if running..."
                        pkill -f 'java -jar' || true

                        echo "Starting new app in background..."
                        nohup java -jar target/*.jar > app.log 2>&1 &

                        echo "Deployment successful!"
                        EOF
                    """
                }
            }
        }
    }
}
