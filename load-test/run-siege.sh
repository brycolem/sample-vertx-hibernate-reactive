#!/bin/bash

# Variables
concurrent_users=255
ramp_up_time=5
total_duration=60

# Generate the URL list
url_list="url_list.txt"
touch $url_list
echo "http://172.21.118.12:8887/api/v1/question" > $url_list
for i in {1..10}; do
  echo "http://172.21.118.12:8887/api/v1/question/$i" >> $url_list
done

# Start Siege ramping up users
siege -f $url_list -c $concurrent_users --delay=0.2s --time=${ramp_up_time}s --internet

# Run Siege with full concurrency
siege -f $url_list -c $concurrent_users --delay=0.2s --time=$(($total_duration - $ramp_up_time))s --internet
