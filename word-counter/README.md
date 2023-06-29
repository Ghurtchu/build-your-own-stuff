### Build your own word counter

https://codingchallenges.substack.com/p/coding-challenge-1

Setting up and testing:
 
 - cd to home: `cd ~`
 - clone the repo
 - run: `vim wc`
 - paste the following three lines inside wc:

`#!/bin/sh`

`cd ~/build-your-own-stuff/word-counter/target/scala-2.13/`

`scala wc.jar $1 $2`
 - save and exit (I  hope you know to exit vim :smile:)
 - run the following commands for testing:
 - `./wc -c ~/input.txt`
 - `./wc -m ~/input.txt`
 - `./wc -w ~/input.txt`
 - `./wc -l ~/input.txt`
 - `./wc ~/input.txt`
 - `cat ${some_file_path} | ./wc -c`
 - `cat ${some_file_path} | ./wc -m`
 - `cat ${some_file_path} | ./wc -w`
 - `cat ${some_file_path} | ./wc -l`
