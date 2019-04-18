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
    kapt 'com.github.LunarWatcher:KClassUnpacker:v1.0.0'
    compileOnly "com.github.LunarWatcher:KClassUnpacker:v1.0.0"
}
```

# Usage

Annotate a data class with `@AutoUnpack`. After that, you can write code like:

```kotlin
for(field in someDataClass) {
    do something with the fields
}
```

# Motivation

[This](https://stackoverflow.com/q/47730421/6296561) post on SO.

# License

Because the code is posted on SO as well, the license is CC-BY-SA 3.0 here too (the same as the SO post is licensed under), to avoid licensing conflicts between the repo and the post. So whether you copy the code or use the dependency, the licenses are identical.
