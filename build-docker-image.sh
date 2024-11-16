version=$(head -n 1 "./project.version")
#echo $version
./gradlew build -x test
docker build -t  ksas-web:latest -t ksas-web:$version .
