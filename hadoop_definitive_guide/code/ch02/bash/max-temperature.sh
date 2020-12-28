#!/usr/bin/env bash

FILES="../../../data/weather/*"

for year in $FILES
do
  echo -ne `basename $year .gz`"\t"
  gunzip -c $year| \
    awk '{ temp = substr($0, 88, 5) + 0;
           q = substr($0, 93, 1);
           if (temp !=9999 && q ~ /[01459]/ && temp > max) max = temp }
         END { print max }'
done