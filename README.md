# RecyclerViewItemAnimators Library
Travis master: [![Build Status](https://travis-ci.org/gabrielemariotti/RecyclerViewItemAnimators.svg?branch=master)](https://travis-ci.org/gabrielemariotti/RecyclerViewItemAnimators)

This repo provides:
* **Appearance animations**
* **Simple animators** for the item views

![Screen](/demo.gif)

## Quick start

You can now download it from Sonatype's snapshots repo.

1. Add the snapshots repo to your `build.gradle`

    ```groovy
    repositories {
        maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
    }
    ```

2. Add build dependency

    ```groovy
    dependencies {
        compile 'com.github.gabrielemariotti.recyclerview:recyclerview-animators:0.3.0-SNAPSHOT@aar'
    }
    ```

## Feature

These animators work with the `RecyclerView` provided by Google with the support library `com.android.support:recyclerview-v7:23.1.1`.

**Appearance animations:** which work when loading on the screen or when scrolling
* Alpha
* SlideInLeft
* SlideInRight
* SlideInBottom
* ScaleIn

**Simple Animators:** cloned from `DefaultItemAnimator` provided by Google customizing the animations.
*This part is still in beta.*

* **SlideInOutLeftItemAnimator** : which applies a slide in/out from/to the left animation
* **SlideInOutRightItemAnimator** : which applies a slide in/out from/to the right animation
* **SlideInOutTopItemAnimator** : which applies a slide in/out from/to the top animation
* **SlideInOutBottomItemAnimator** : which applies a slide in/out from/to the bottom animation
* **ScaleInOutItemAnimator** : which applies a scale animation
* **SlideScaleInOutRightItemAnimator** : which applies a scale animation with a slide in/out from/to the right animation


## Quick example:

**Appearance animations:**
```java
   mAdapter = new MyAdapter(this);

   AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(mAdapter, mRecyclerView);
   mRecyclerView.setAdapter(animatorAdapter);
```

**Simple Animators:**
```java
   mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
```

**NOTE**: Feedbacks and patches are welcome!

The sample app uses all features available in the widget.


## ChangeLog

* [Changelog:](CHANGELOG.md) A complete changelog

Acknowledgements
--------------------
* Thanks to [Niek Haarman][1] for some ideas and code taken from his [ListViewAnimations][2].

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

    Copyright 2014-2015 Gabriele Mariotti

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

 [1]: https://plus.google.com/+NiekHaarman
 [2]: https://github.com/nhaarman/ListViewAnimations
