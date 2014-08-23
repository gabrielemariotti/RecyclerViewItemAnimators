This repo provides simple animators for the item views in the `RecyclerView`
This code is cloned from `DefaultItemAnimator` provided by Google customizing the animations.

## Feature

**SlideInOutLeftItemAnimator** : which applies a slide in/out from/to the left animation
**SlideInOutRightItemAnimator** : which applies a slide in/out from/to the right animation
**SlideInOutTopItemAnimator** : which applies a slide in/out from/to the top animation
**SlideInOutBottomItemAnimator** : which applies a slide in/out from/to the bottom animation

![Screen](/demo.gif)

Quick example:
```java
   mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
```

**NOTE**: These code is under heavy development at the moment.Feedback and patches are very welcome!

## How do I use it?

These animators work with the `RecyclerView` provided by Google with the support library `com.android.support:recyclerview-v7:21.0.0-rc1`.
Currently it requires an Android-L emulator or device.

To use this code you have to:

1. Import `RecyclerViewItemAnimator` as a library to your project.

2. Add a `RecyclerView` to your layout with your custom `Adapter` and `LayoutManager`.

3. Set the `ItemAnimator` to the `RecyclerView`.

```java
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
```

The sample app uses all features available in the widget.

## Download

You can now download it from Sonatype's snapshots repo.

1. Add the snapshots repo to your `build.gradle`

    ```
    repositories {
        maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
    }
    ```

2. Add build dependency

    ```
    dependencies {
        compile 'com.github.gabrielemariotti.recyclerview:recyclerview-animators:0.1.0-SNAPSHOT@aar'
    }
    ```
    
I'll be updating the snapshots regularly.


Credits
-------

Author: Gabriele Mariotti (gabri.mariotti@gmail.com)

<a href="https://plus.google.com/u/0/114432517923423045208">
  <img alt="Follow me on Google+"
       src="/assets/images/g+64.png" />
</a>
<a href="https://twitter.com/GabMarioPower">
  <img alt="Follow me on Twitter"
       src="/assets/images/twitter64.png" />
</a>
<a href="http://it.linkedin.com/in/gabrielemariotti">
  <img alt="Follow me on LinkedIn"
       src="/assets/images/linkedin.png" />
</a>

License
-------

    Copyright 2014 Gabriele Mariotti

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


---
