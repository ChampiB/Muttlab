#!/usr/bin/zsh

echo "==> Execute : mkdir bin bin/plugins bin/project"
mkdir bin bin/plugins bin/project

echo "==> Execute : javac -d bin/project src/main/java/muttlab/**/*.java"
javac -d bin/project src/main/java/muttlab/**/*.java

echo "==> Execute : cd bin/project"
cd bin/project

echo "==> Execute : jar cvf MuttLab.jar ./muttlab/**/*.class"
jar cvf MuttLab.jar muttlab/**/*.class

echo "==> Execute : cd ../../"
cd ../../

echo "==> Execute : javac -d bin/plugins src/main/java/muttlab/**/*.java core/src/core/**/*.java"
javac -d bin/plugins src/main/java/muttlab/**/*.java core/src/core/**/*.java

echo "==> Execute : cd bin/plugins"
cd bin/plugins

echo "==> Execute : jar cvf core.jar core/**/*.class"
jar cvf core.jar core/**/*.class

echo "==> Execute : cd ../../"
cd ../../

echo "==> Execute : mkdir plugins"
mkdir plugins

echo "==> Execute : cp bin/plugins/core.jar plugins"
cp bin/plugins/core.jar plugins

echo "==> Execute : cp bin/project/MuttLab.jar MuttLab.jar"
cp bin/project/MuttLab.jar MuttLab.jar
