tar -czvf build.tar.gz .nuxt static package.json nuxt.config.js server utils
scp -P10022 build.tar.gz haojun@160.251.123.93:/usr/local/src/restaurant-site/nuxt
rm -rf build.tar.gz