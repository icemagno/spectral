#!/bin/bash

apt-add-repository -y ppa:aims/sagemath
apt-get update
apt-get install sagemath-upstream-binary
