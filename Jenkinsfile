pipeline {
    agent any

    environment {
        JAVA_HOME = tool 'Java 17'
        PATH = "${JAVA_HOME}/bin;${PATH}"
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
        GIT_COMMIT_SHORT = "${env.GIT_COMMIT.take(7)}"
    }

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

                git branch: 'main',
                    url: 'https://github.com/osztobanyipeter1/SoftwareTestAutomation.git'

                bat '''
                    echo Repository: & git config --get remote.origin.url
                    echo Branch: & git rev-parse --abbrev-ref HEAD
                    echo Commit: & git rev-parse --short HEAD
                '''
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
                    echo Gradle version:
                    gradle --version

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
                echo "Electronics Store must run on 8080!"
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
            echo "Collecting Results..."
            echo "====================================="

            archiveArtifacts artifacts: '''
                build/allure-results/**,
                build/allure-report/**,
                build/test-results/**
            ''',
            allowEmptyArchive: true,
            fingerprint: true

            junit testResults: 'build/test-results/**/*.xml',
                 allowEmptyResults: true
        }

        success {
            echo "====================================="
            echo "BUILD SUCCESSFUL!"
            echo "====================================="
            echo "Build #${BUILD_NUMBER} passed"
        }

        failure {
            echo "====================================="
            echo "BUILD FAILED!"
            echo "====================================="
            echo "Build #${BUILD_NUMBER} failed"
        }
    }
}
