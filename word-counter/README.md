### Build your own word counter

https://codingchallenges.substack.com/p/coding-challenge-1

Setting up and testing:
 
 - cd to home: `cd ~`
 - clone the repo
 - run: `vim scalawc`
 - paste the following inside scalawc:

`#!/bin/sh`

`cd ~/build-your-own-stuff/word-counter/target/scala-2.13/ scala scalawc.jar $1 $2`

`scala scalawc.jar $1 $2`

 - run the following commands for testing:
 - `./scalawc -c input.txt`
 - `./scalawc -m input.txt`
 - `./scalawc -w input.txt`
 - `./scalawc -l input.txt`
 - `./scalawc input.txt`
 - `cat ${some_file_path} | ./scalawc -c`
 - `cat ${some_file_path} | ./scalawc -m`
 - `cat ${some_file_path} | ./scalawc -w`
 - `cat ${some_file_path} | ./scalawc -l`
