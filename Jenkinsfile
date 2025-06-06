pipeline {
    agent any
    
    tools{
		maven 'Maven'
	}
	
	environment {
        DOCKER_IMAGE = "ashwinibhawar2892/apiautomationframework:${BUILD_NUMBER}"
        DOCKER_CREDENTIALS_ID = 'dockerhub_credentials'
    }
	
    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/AshwiniBhawar/APIFramaework.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %DOCKER_IMAGE% ."
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat '''
                        echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                        docker push %DOCKER_IMAGE%
                       '''
                }
            }
        }
        
        stage("Deploy to Dev") {
            steps {
                echo "Deploying to Dev"
            }
        }
        
        stage('Sanity API Automation Tests For Dev') {
            steps {
                script {
            def status = bat(
                script: """
                    docker run --rm -v "%WORKSPACE%":/app -w /app %DOCKER_IMAGE% \
                    mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/sanity.xml -Denv=prod
                """,
                returnStatus: true
            )
            if (status != 0) {
                currentBuild.result = 'UNSTABLE'
            }
            }
        }
        }
        
        stage('Publish ChainTest Report For Dev') {
			steps {
                    publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: true,
                            reportDir: 'target/chaintest',
                            reportFiles: '*.html',
                            reportName: 'HTML API Sanity ChainTest Report For Dev',
                            reportTitles: ''
                        ])               
				}
		}
			

        stage("Deploy to QA") {
            steps {
                echo "Deploying to QA"
            }
        }
		
        stage('Regression API Automation Tests For QA') {
            steps {
                script {
                    def status = bat(
                        script: """
                  				  docker run --rm -v "%WORKSPACE%":/app -w /app %DOCKER_IMAGE% \
                  				  mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/regression.xml -Denv=prod
               					 """,
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }
        
			
		stage('Publish ChainTest Report For QA') {
			steps {
                    publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: true,
                            reportDir: 'target/chaintest',
                            reportFiles: '*.html',
                            reportName: 'HTML API Regression ChainTest Report For QA',
                            reportTitles: ''
                        ])               
				}
		}
			

        stage("Deploy to Stage") {
            steps {
                echo "Deploying to Stage"
            }
        }
        
        stage('Sanity API Automation Tests For Stage') {
            steps {
                script {
                    def status = bat(
                        script: """
                    			docker run --rm -v "%WORKSPACE%":/app -w /app %DOCKER_IMAGE% \
                    			mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/sanity.xml -Denv=prod
                				""",
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        			
		stage('Publish ChainTest Report For Stage') {
					steps {
                        publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: true,
                            reportDir: 'target/chaintest',
                            reportFiles: '*.html',
                            reportName: 'HTML API Sanity ChainTest Report For Stage',
                            reportTitles: ''
                        ])               
				}
		}
		
        
        stage("Deploy to Prod") {
            steps {
                echo "Deploying to Prod"
            }
        }
        
        stage('Sanity API Automation Tests For Prod') {
            steps {
                script {
                    def status = bat(
                        script: """
                    			docker run --rm -v "%WORKSPACE%":/app -w /app %DOCKER_IMAGE% \
                    			mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/sanity.xml -Denv=prod
               				 """,
                        returnStatus: true
                    )
                    if (status != 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }
        
        stage('Publish Allure Reports For Prod') {
				steps {
                    script {
                        allure([
                            includeProperties: false,
                           	jdk: '',
                            properties: [],
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: '/allure-results']]
                       ])               
				}
			}
		}
			
        stage('Publish ChainTest Report For Prod') {
					steps {
                        publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: true,
                            reportDir: 'target/chaintest',
                            reportFiles: '*.html',
                            reportName: 'HTML API Sanity ChainTest Report For Prod',
                            reportTitles: ''
                        ])               
				}
		}
	}
}