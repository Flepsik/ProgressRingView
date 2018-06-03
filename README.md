# ProgressRingView 

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ProgressRingView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5452) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.flepsik/progress-ring-view/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.flepsik/progress-ring-view)

![alt tag](https://user-images.githubusercontent.com/7210975/40885282-234b783c-672c-11e8-8d31-0214111ae92e.png)

## Installation

Gradle:
```
dependencies {
    compile 'com.github.flepsik:progress-ring-view:1.2.1'
}
```

Maven:
```
<dependency>
  <groupId>com.github.flepsik</groupId>
  <artifactId>progress-ring-view</artifactId>
  <version>1.2.1</version>
  <type>pom</type>
</dependency>
```

## Usage
There're a few attributes you can set:
```
ProgressRingView progress = (ProgressRingView) findViewById(R.id.first);
progress.cornerEdges(false);
progress.setProgress(.5f); //in range 0f..1f
progress.setAnimated(false);
progress.setRingWidth(ProgressRingView.DEFAULT_RING_WIDTH);
progress.setAnimationDuration(ProgressRingView.ANIMATION_DURATION);
progress.setBackgroundColor(Color.parseColor("#F29F8E"));
progress.fillInnerProgress(true);
progress.setProgressInnerFillColor(Color.parseColor("#F76156"));
progress.setProgressColor(Color.RED);
progress.setBackgroundProgressColor(Color.LTGRAY);
```
or via xml
```
<flepsik.github.com.progress_ring.ProgressRingView
        android:id="@+id/first"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:background_progress_color="#e4e7d3"
        app:progress_color="#07be7a"
        app:progress=".5" // in range 0f..1f
        app:ring_width="3dp"
        app:animated="false" 
        app:animation_duration="500"
        app:background_color="#7FFFAA"
        app:corner_edges="true"
        app:progress_inner_fill="false"
        app:progress_inner_fill_color="07be7a"/>
```

## Contributing
Create an issue 

or 

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## License
```
Copyright 2017 Bogdanov Philipp

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
```
