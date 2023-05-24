class Constants {

    static final String MASTER_BRANCH = 'master'
    static final String RELEASE_BRANCH = 'release'

    static final String QA_BUILD = 'Debug'
    static final String RELEASE_BUILD = 'Release'

    static final String INTERNAL_TRACK = 'internal'
    static final String RELEASE_TRACK = 'release'
}

def getBuildType() {
    switch (env.BRANCH_NAME) {
        case Constants.MASTER_BRANCH|Constants.RELEASE_BRANCH:
            //release
            return Constants.RELEASE_BUILD
        default:
            //debug
            return Constants.QA_BUILD
    }
}

def getTrackType() {
    switch (env.BRANCH_NAME) {
        case Constants.MASTER_BRANCH:
            return Constants.RELEASE_TRACK
        default:
            return Constants.INTERNAL_TRACK
    }
}

def isDeployCandidate() {
    return ("${env.BRANCH_NAME}" =~ /(release|master)/)
}

pipeline {
    agent { dockerfile true }
    environment {
        appName = 'Yoruba Proverbs'

        KEY_PASSWORD = credentials('keyPassword')
        KEY_ALIAS = credentials('keyAlias')
        KEYSTORE = credentials('keystore')
        STORE_PASSWORD = credentials('storePassword')
    }
    stages {
        stage('Run Tests') {
            steps {
                echo 'Running Tests'
                script {
                    VARIANT = getBuildType()
                    sh "./gradlew test${VARIANT}UnitTest"
                }
            }
        }
        stage('Build Bundle') {
            //when { expression { return isDeployCandidate() } }
            steps {
                echo 'Building'
                script {
                    VARIANT = getBuildType()
                    if (VARIANT == Constants.RELEASE_BRANCH || VARIANT == Constants.MASTER_BRANCH) {
                        sh "./gradlew -PstorePass=${STORE_PASSWORD} -Pkeystore=${KEYSTORE} -Palias=${KEY_ALIAS} -PkeyPass=${KEY_PASSWORD} bundle${VARIANT}"
                    } else {
                        sh "./gradlew assemble${VARIANT}"

                        //sh './gradlew generatePomFileForLibraryPublication'
                    }
                }
            }
        }
        stage('Build apk') {
            when { expression { return !isDeployCandidate() } }
            steps {
                echo 'Building'
                script {
                    VARIANT = getBuildType()
                    // Archive the APKs so that they can be downloaded from Jenkins
                    archiveArtifacts "**/${appName}-${VARIANT}.apk"
                    // Archive the ARR and POM so that they can be downloaded from Jenkins
                    // archiveArtifacts "**/${APP_NAME}-${BUILD_TYPE}.aar, **/*pom-   default.xml*"
                }
            }
        }
        stage('Deploy App to Store') {
            when { expression { return isDeployCandidate() } }
            steps {
                echo 'Deploying'
                script {
                    VARIANT = getBuildType()
                    TRACK = getTrackType()

                    if (TRACK == Constants.RELEASE_TRACK) {
                        timeout(time: 5, unit: 'MINUTES') {
                            input "Proceed with deployment to ${TRACK}?"
                        }
                    }

                    try {
                        CHANGELOG = readFile(file: 'CHANGELOG.txt')
                    } catch (err) {
                        echo "Issue reading CHANGELOG.txt file: ${err.localizedMessage}"
                        CHANGELOG = ''
                    }

                    androidApkUpload googleCredentialsId: 'play-store-credentials',
                            filesPattern: "**/outputs/bundle/${VARIANT.toLowerCase()}/*.aab",
                            trackName: TRACK,
                            recentChangeList: [[language: 'en-US', text: CHANGELOG]]
                }
            }
        }
    }
}