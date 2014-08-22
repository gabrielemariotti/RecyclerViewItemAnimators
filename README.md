This repo provides simple animators for the item views in the `RecyclerView`
This code is cloned from `DefaultItemAnimator` provided by Google customizing the animations.

**SlideInOutLeftItemAnimator** : which applies a slide in/out from/to the left animation
**SlideInOutRightItemAnimator** : which applies a slide in/out from/to the right animation
**SlideInOutTopItemAnimator** : which applies a slide in/out from/to the top animation

![Screen](/demo.gif)

Example:
```java
   mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
```