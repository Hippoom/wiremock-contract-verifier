#!/usr/bin/env bash
openssl aes-256-cbc -K $encrypted_95de03115781_key -iv $encrypted_95de03115781_iv -in cd/codesigning.asc.enc -out cd/signingkey.asc -d
gpg --fast-import cd/signingkey.asc
./gradlew uploadArchives -p core \
      -PpublishStage=true \
      -PsonatypeUsername=${OSSRH_JIRA_USERNAME} \
      -PsonatypePassword=${OSSRH_JIRA_PASSWORD} \
      -Psigning.keyId=${GPG_KEY_NAME} \
      -Psigning.password=${GPG_PASSPHRASE}
