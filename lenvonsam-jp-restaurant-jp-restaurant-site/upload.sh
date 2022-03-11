tar -czvf build.tar.gz .nuxt static package.json nuxt.config.js server utils
scp build.tar.gz vps:/usr/local/src/restaurant-site/nuxt
rm -rf build.tar.gz