pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                echo "====================================="
                echo "Checking out code from GitHub..."
                echo "====================================="

                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "====================================="
                echo "Building project with Gradle..."
                echo "====================================="

                bat '''
                    echo Java version:
                    java -version

                    echo.
                    echo Building...
                    gradle clean build --refresh-dependencies -x test
                '''
            }
        }

        stage('API Tests') {
            steps {
                echo "====================================="
                echo "Running API Tests (14 tests)..."
                echo "====================================="

                bat 'gradle clean test -Ptag=api --no-build-cache'
            }
        }

        stage('UI Tests') {
            steps {
                echo "====================================="
                echo "Running UI Tests (13 tests)..."
                echo "====================================="

                bat 'gradle clean test -Ptag=ui --no-build-cache'
            }
        }

        stage('Allure Report') {
            steps {
                echo "====================================="
                echo "Generating Allure Report..."
                echo "====================================="

                bat '''
                    if exist "build\\allure-results" (
                        echo Generating Allure report...
                        allure generate build/allure-results --clean -o build/allure-report
                        echo Allure report generated
                    ) else (
                        echo No test results found
                    )
                '''
            }
        }
    }

    post {
        always {
            echo "====================================="
            echo "Collecting results..."
            echo "====================================="

            archiveArtifacts artifacts: '''
                build/allure-results/**,
                build/allure-report/**,
                build/test-results/**
            ''',
            allowEmptyArchive: true

            junit testResults: 'build/test-results/**/*.xml',
                 allowEmptyResults: true
        }

        success {
            echo "BUILD SUCCESSFUL!"
        }

        failure {
            echo "BUILD FAILED!"
        }
    }
}
