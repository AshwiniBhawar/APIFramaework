pipeline {
    agent any
    
    tools{
		maven 'Maven'
	}
    stages {
	
		stage('Clean Workspace') {
            steps {
                script {
                    // Clean the workspace before running the job
                    cleanWs()
                }
            }
        }
        stage('Build') {
            steps {
                git "https://github.com/jglick/simple-maven-project-with-tests.git"
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post{
				sucess{
					junit '**/target/surefire-report/Test-*.xml'
					archiveArtifacts 'target/*.jar'
				}
			}
        }

        stage("Deploy to QA") {
            steps {
                echo "Deploying to QA"
            }
        }
		
        stage('Regression API Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
					git "https://github.com/AshwiniBhawar/APIFramaework.git"
               		bat "mvn clean test -Dsurefire.suiteXmlFile=/src/test/resources/testrunner/regression.xml"
				}
            }
        }
        
        stage('Publish Allure Reports') {
				stage('Publish GoRest Report') {
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
			
		stage('Publish ChainTest Report') {
					steps {
                        publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: true,
                            reportDir: 'target/chaintest',
                            reportFiles: '*.html',
                            reportName: 'HTML API Regression ChainTest Report',
                            reportTitles: ''
                        ])               
					}
				}
			}
        }

        stage("Deploy to Stage") {
            steps {
                echo "Deploying to Stage"
            }
        }
        
        stage('Sanity API Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
					git "https://github.com/AshwiniBhawar/APIFramaework.git"
               		bat "mvn clean test -Dsurefire.suiteXmlFile=/src/test/resources/testrunner/sanity.xml"
				}
            }
        }
        			
		stage('Publish ChainTest Report') {
					steps {
                        publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: true,
                            reportDir: 'target/chaintest',
                            reportFiles: '*.html',
                            reportName: 'HTML API Regression ChainTest Report',
                            reportTitles: ''
                        ])               
				}
		}
		
        
        stage("Deploy to Prod") {
            steps {
                echo "Deploying to Stage"
            }
        }

}