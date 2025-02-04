#! /bin/bash

cd /home/bgold/gpt4all/gpt4all-backend/src/main/java/com/github/mavenautoprimer
for f in *_fixed.java; do
    mv "${f}" "${f/_fixed.java/.java}"
done
