#!/bin/bash

FAILED_PREREQUISITES=0

if ! command -v docker >/dev/null 2>&1; then
  echo "docker: MISSING" 1>&2
  echo "docker is required to run the development environment" 1>&2
  echo "Please visit https://docs.docker.com/install/ for instructions on how to install it for your platform" 1>&2
  echo "" 1>&2
  ((FAILED_PREREQUISITES++))
else
  echo "docker: FOUND" 1>&2
fi

if ! command -v jq >/dev/null 2>&1; then
  echo "jq: MISSING" 1>&2
  echo "jq is required to run the smoke tests, it's a lightweight tool that helps to parse json in the terminal." 1>&2
  echo "Please visit https://stedolan.github.io/jq/ to download it" 1>&2
  ((FAILED_PREREQUISITES++))
else
  echo "jq: FOUND" 1>&2
fi

if [[ $FAILED_PREREQUISITES -ne 0 ]]; then
  echo
  echo $FAILED_PREREQUISITES PREREQUISITES MISSING
  echo
  echo Please see the comments above for instructions on how to install the software required for this test.
  exit 1
fi
