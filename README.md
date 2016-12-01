[![Build Status](https://travis-ci.org/guiguegon/TouchableImageView.svg?branch=master)](https://travis-ci.org/guiguegon/TouchableImageView)

# TouchableImageView
**TouchableImageView** is a custom ViewImage that shows a small circle where the user has touched and fade out it. It works like a normal ImageView if a **View.OnTouchListener** is not set. The color, radius and time of the animation can be configured programmatically or using the xml.

```xml
 <es.guiguegon.touchableimageview.TouchableImageView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:adjustViewBounds="true"
        android:scaleType="fitXY"
        android:src="@drawable/chess_board"
        app:tiv_color="#FF0000"
        app:tiv_radius="8dp" 
        app:tiv_animation_time="200"/>
```

```java
    public void setFeedbackColor(int color) {
        this.feedbackColor = color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setAnimationTime(int animationTime) {
        this.animationTime = animationTime;
    }
```
