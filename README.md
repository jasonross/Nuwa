![nuwa](http://7fviov.com1.z0.glb.clouddn.com/nuwa.jpg)

## Nuwa
[![Download](https://api.bintray.com/packages/jasonross/maven/nuwa/images/download.svg) ](https://bintray.com/jasonross/maven/nuwa/_latestVersion)


Nuwa is a goddess in ancient Chinese mythology best known for repairing the pillar of heaven. 

With this Nuwa project，you can also have the repairing power, fix your android applicaiton without have to publish a new APK to the appstore.

## Features
* Support both dalvik and art runtime.
* Support productFlavor and buildType.
* Support proguard and multidex.
* Pure java implementation.

## Run the Sample<a name="sample" ></a>
1. in `master` branch, run and install the app.

	it shows『hello world』.
2. copy sample/build/outputs/nuwa dir to somewhere for later use. 

	for example: /Users/jason/Documents/nuwa
3. change to `bugfix` branch, run the following command in terminal to generate patch.jar.

	> ./gradlew clean nuwaQihooDebugPatch -P NuwaDir=/Users/jason/Documents/nuwa

	**java verison must be same using android studio and terminal**
4. run following command to push patch.jar to sdcard.

	>adb push sample/build/outputs/nuwa/qihoo/debug/patch.jar /sdcard/

5. restart your application (kill the process).

	it shows『patch success』.
	

## Integration
### Get Gradle Plugin

1. add following to the build.gradle of your root project.
>classpath 'cn.jiajixin.nuwa:gradle:1.2.2'

	build.gradle maybe look like this:
	
	```
	buildscript {
	    repositories {
	        jcenter()
	    }
	    dependencies {
	        classpath 'com.android.tools.build:gradle:1.2.3'
	        classpath 'cn.jiajixin.nuwa:gradle:1.2.2'
	    }
	}
	```
2. add following to your build.gradle:

	>apply plugin: "cn.jiajixin.nuwa"

### Get Nuwa SDK

* gradle dependency:

	```	
	dependencies {
		compile 'cn.jiajixin.nuwa:nuwa:1.0.0'
	}
	```
	
### Use Nuwa SDK
1. add following to your application class:

	```
	@Override
	protected void attachBaseContext(Context base) {
	    super.attachBaseContext(base);
	    Nuwa.init(this);
	}
	```
2. load the patch file according to your needs:

	```
	Nuwa.loadPatch(this,patchFile)
	```
	**I plan to provide the management of patch file later.**

### ProGuard
* add follwing to you proguardFile if you are using proguard:

	>-keep class cn.jiajixin.nuwa.** { *; }

### Nuwa DSL
1. You can add `nuwa` extension to your build.gradle. Generally, you don't need this.

	```
	nuwa{
	
	}
	```
2. Nuwa extension support following DSL object.
	* includePackage:HashSet\<String>
		
		The internal name of a class is its fully qualified name, where '.' are replaced by '/'. For example:
		>cn/jiajixin/nuwasample/MainActivity.class
		
		Using includePackage，you can only fix those classes whose internal name starts with includePackage.For example: 
	> includePackage = ['cn/jiajixin/nuwasample']
	
		Default, Nuwa can fix classes which not from the android support library.
	* excludeClass:HashSet\<String>
	
		**All Application subclasses should not be injected by Nuwa.** 
	
		Default, Nuwa will automatically exclude the application declared in your manifest file. **You need to exclude others using  excludeClass**
		
		Using excludeClass, you can not fix those classes whose internal name ends with excludeClass.For example:
		> excludeClass = ['BaseApplication.class']
		
	* debugOn:boolean
	
		whether use Nuwa in debug mode, default to`true`.

## Generate Patch
For how to generate patch file, please reference the first three steps of [Run the Sample](#sample).

There are two types of gradle task to generate patch file：

* nuwaPatches

	this task will generate multi patch.jar for all variant.

* nuwa${variant.name.capitalize()}Patch

	this task will generate one patch.jar for specific variant.
	
## How it Works 
Inspired by QZone hotfix solution from this [article](http://bugly.qq.com/blog/?p=781).

### [Nuwa Gradle](https://github.com/jasonross/NuwaGradle) 

* inject into all classes one java bytecode referring the Hack.class from a different dex, which can avoid CLASS_ISPREVERIFIED error when replacing class. 
* generate patch.jar according to mapping.txt and classes hash of last published APK.

### Nuwa
* inject the Hack.class.
* inject the patch.jar into head of BaseDexClassLoader's pathList.

## Later Plan

* provide patch file management 
* improve security of Nuwa

## License
[The MIT License (MIT)](http://opensource.org/licenses/MIT)

Copyright (c) 2015, jiajixin.cn