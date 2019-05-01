[![](https://jitpack.io/v/LunarWatcher/KClassUnpacker.svg)](https://jitpack.io/#LunarWatcher/KClassUnpacker)

# Setup

If you haven't already, link Jitpack:
```
repositories {
    // ...
    maven { url 'https://jitpack.io' }
}
```

Add the processor:

```
apply plugin: 'kotlin-kapt'
...

dependencies {
    kapt 'com.github.LunarWatcher:KClassUnpacker:$version'
    compileOnly "com.github.LunarWatcher:KClassUnpacker:$version"
}
```

Where `$version` is either a variable, or the version you want to use in plain text (in which case, replace it with the version you want). The available versions can be seen [in the releases](https://github.com/LunarWatcher/KClassUnpacker/releases)

# Usage

Annotate a data class with `@AutoUnpack`. After that, you can write code like:

```kotlin
for(field in someDataClass) {
    do something with the fields
}
```

# Motivation

[This post on SO.](https://stackoverflow.com/q/47730421/6296561)

# License

Because the code is posted on SO as well, the license is CC-BY-SA 3.0 here too (the same as the SO post is licensed under), to avoid licensing conflicts between the repo and the post. So whether you copy the code or use the dependency, the licenses are identical.
