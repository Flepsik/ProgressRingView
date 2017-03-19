# ProgressRingView

![alt tag](https://cloud.githubusercontent.com/assets/7210975/24082528/33614a1c-0cd8-11e7-8174-615fba55f9d9.jpg)

## Installation

###Gradle:
```
repositories {
    maven {
        url 'https://dl.bintray.com/fleps/maven/'
    }
}

...

dependencies {
    compile 'com.github.flepsik:progress-ring-view:1.0.4'
}
```

###Maven:
```
<dependency>
  <groupId>com.github.flepsik</groupId>
  <artifactId>progress-ring-view</artifactId>
  <version>1.0.4</version>
  <type>pom</type>
</dependency>
```

## Usage
There're a few attributes you can set:
```
ProgressRingView progress = (ProgressRingView) findViewById(R.id.first);
progress.setProgress(.5f);
progress.setAnimated(false);
progress.setRingWidth(ProgressRingView.DEFAULT_RING_WIDTH);
progress.setAnimationDuration(ProgressRingView.ANIMATION_DURATION);
progress.setProgressColor(Color.RED);
progress.setBackgroundEmptyColor(Color.LTGRAY);
```
or via xml
```
<flepsik.github.com.progress_ring.ProgressRingView
        android:id="@+id/first"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        app:background_progress_color="#e4e7d3"
        app:progress_color="#07be7a"
        app:progress=".5"
        app:ring_width="3dp"
        app:animated="false"
        app:animation_duration="500"/>
```

## Contributing

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
