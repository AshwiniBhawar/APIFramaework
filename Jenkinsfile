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
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            
            post{
				success{
					junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
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
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
					git "https://github.com/AshwiniBhawar/APIFramaework.git"
               		bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/sanity.xml -Denv=dev"
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
		
        stage('Sanity API Automation Tests For QA') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
					git "https://github.com/AshwiniBhawar/APIFramaework.git"
               		bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/sanity.xml -Denv=qa"
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
                            reportName: 'HTML API Sanity ChainTest Report For QA',
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
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
					git "https://github.com/AshwiniBhawar/APIFramaework.git"
               		bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/sanity.xml -Denv=stage"
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
        
        stage('Regression API Automation Tests For Prod') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE'){
					git "https://github.com/AshwiniBhawar/APIFramaework.git"
               		bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunner/regression.xml -Denv=prod"
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