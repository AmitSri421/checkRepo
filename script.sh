#!/bin/bash

artifactory_url="https://your-artifactory-url.com/artifactory"
username="your-username"
password="your-password"

artifacts=(
  "org/apache/activemq/activemq-camel/5.4.2-fuse-03-09/activemq-camel.jar"
  "org/apache/camel/camel-core/2.23.2.fuse-780036-redhat-00001/camel-core-2.23.2.fuse-780036-redhat-00001.jar"
)

for artifact in "${artifacts[@]}"; do
  repository_name="libs-release-local" # Adjust this as needed
  url="${artifactory_url}/${repository_name}/${artifact}"
  response=$(curl -s -I -u ${username}:${password} ${url})
  status_code=$(echo "$response" | head -n 1 | cut -d ' ' -f2)
  if [ "$status_code" -eq 200 ]; then
    echo "Artifact ${artifact} is available."
  else
    echo "Artifact ${artifact} is not available."
  fi
done
