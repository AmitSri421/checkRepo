import http.client
import base64

# Define the JFrog Artifactory host and base path
JFROG_HOST = "your_jfrog_instance.jfrog.io"
BASE_PATH = "/artifactory"
USERNAME = "your_username"
TOKEN = "your_token"

# List of artifacts to check (example list)
artifacts = [
    "repo1/path/to/artifact1.jar",
    "repo2/path/to/artifact2.jar",
    # Add all 200 artifacts here
]

# Prepare basic auth headers
auth = base64.b64encode(f"{USERNAME}:{TOKEN}".encode()).decode()
headers = {
    "Authorization": f"Basic {auth}"
}

# Function to check if an artifact exists
def check_artifact_existence(artifact_path):
    connection = http.client.HTTPSConnection(JFROG_HOST)
    url = f"{BASE_PATH}/{artifact_path}"
    
    try:
        connection.request("HEAD", url, headers=headers)
        response = connection.getresponse()

        if response.status == 200:
            print(f"Available: {artifact_path}")
        elif response.status == 404:
            print(f"Not available: {artifact_path}")
        else:
            print(f"Error checking {artifact_path}: HTTP {response.status}")
    finally:
        connection.close()

# Loop through each artifact and check its availability
for artifact in artifacts:
    check_artifact_existence(artifact)
