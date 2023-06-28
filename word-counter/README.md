### Build your own word counter

https://codingchallenges.substack.com/p/coding-challenge-1

Setting up and testing:
 
 - cd to home: `cd ~`
 - clone the repo
 - run: `vim scalawc`
 - paste the following three lines inside scalawc:

`#!/bin/sh`

`cd ~/build-your-own-stuff/word-counter/target/scala-2.13/`

`scala scalawc.jar $1 $2`
 - save and exit (I  hope you know to exit vim :smile:)
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
