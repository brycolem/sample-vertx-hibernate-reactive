#!/bin/bash

# Parse command line arguments
while [[ $# -gt 0 ]]
do
    key="$1"

    case $key in
        -f|--file)
        FILE="$2"
        shift
        shift
        ;;
        --username)
        USERNAME="$2"
        shift
        shift
        ;;
        --password)
        PASSWORD="$2"
        shift
        shift
        ;;
        *)
        echo "Unknown option: $1"
        exit 1
        ;;
    esac
done

# Set defaults from environment variables
USERNAME="${USERNAME:-$DB_USERNAME}"
PASSWORD="${PASSWORD:-$DB_PASSWORD}"

# Check that a file was specified
if [ -z "$FILE" ]; then
    echo "You must specify a file using the -f or --file option."
    exit 1
fi

# Check that the file exists
if [ ! -f "$FILE" ]; then
    echo "File not found: $FILE"
    exit 1
fi

# Base64 encode the username and password
ENCODED_USERNAME=$(echo -n "$USERNAME" | base64)
ENCODED_PASSWORD=$(echo -n "$PASSWORD" | base64)

# Replace the placeholder values in the file with the encoded values
sed -i "s/\$DB_USERNAME/$ENCODED_USERNAME/g" "$FILE"
sed -i "s/\$DB_PASSWORD/$ENCODED_PASSWORD/g" "$FILE"

echo "Secrets added to $FILE"
