# city56Sdk
城市映急sdk
先在 build.gradle(Project:XXXX) 的 repositories 添加:

    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
然后在 build.gradle(Module:app) 的 dependencies 添加:

    dependencies {
          compile 'com.github.windouxp:newcitysdk:0.2'//必须依赖
    }

作者：dxy
链接：http://www.egaid.com/
來源：github
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
