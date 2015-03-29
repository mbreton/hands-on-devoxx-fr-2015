#!/bin/bash
export PHANTOMJS_EXECUTABLE=./node_modules/casperjs/node_modules/.bin/phantomjs
export PATH=./node_modules/.bin:$PATH
rm -Rf print/*
casperjs print-casper.js
convert print/slide* -quality 100 typescript.pdf
rm -Rf print/*
