Ascent
============

Ascent is an Android library that simplifies applying custom fonts to TextViews and subclasses.
Annotation processing is used to remove broilerplate from your code.  See examples.

Build Status
------------
[![Build Status](https://travis-ci.org/desertjim/ascent.svg?branch=master)](https://travis-ci.org/desertjim/ascent)

Examples
--------

```java

// Ascent can be used to pull out fonts by name from assets/fonts folder
// 1) Add the @Font annotation and font key to the TextView, Button etc member variable
// 2) Create an instance of the Ascent class
// 3) inject the class containing annotations

class DemoActivity extends Activity {

  @Font("Lobster.ttf") TextView mHelloWorld;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
        
    mHelloWorld = (TextView)findViewById(R.id.hello_world);
        
    Ascent ascent = new Ascent();
    ascent.setAssetManager(getAssets());
    ascent.inject(this);
  }

}
```

Another slightly different example

```java

// Ascent allows you to add your own custom key Typeface value pair

class CustomView extends View {
  
  @Font("new_key") TextView mHelloWorld;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
        
    mHelloWorld = (TextView)findViewById(R.id.hello_world);
    
    Typeface acquiredTypeface = ... ; // Retreived from wherever
    
    Ascent ascent = new Ascent();
    ascent.add("new_key", acquiredTypeface);
    ascent.inject(this);
  }

}
```

Comments
--------
This library is focused strictly on simplifying the setting of fonts, without having to subclass text views.  This library becomes much more useful when coupled with dagger, and it's less sharp brother butter knife. 


Download
--------

Ascent comes in two libraries: `ascent` and `ascent-processor`.


License
-------

    Copyright 2014 James Baca

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
