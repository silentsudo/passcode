
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Passcode-green.svg?style=true)](https://android-arsenal.com/details/1/2951)

#Passcode
###Android Passcode view

###Usage

###Adding to build.gradle for app level

        compile(group: 'in.ashish29agre.passcode', name: 'passcode', version: '1.0.0', ext: 'aar');

###Adding to project level gradle
        repositories {
            jcenter()
            maven {
                url  "http://dl.bintray.com/ashish29agre/passcode-repo"
            }
        }

###XML
     <in.ashish29agre.passcode.PasscodeView
             android:id="@+id/passcode_view"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:orientation="horizontal"/>

###Java
    PasscodeView passcodeView = (PasscodeView) findViewById(R.id.passcode_view);
            passcodeView.setPassCodeCount(4);
            passcodeView.setDrawableSize(48);
            passcodeView.setPasscodeItemMargin(8);
            passcodeView.clearAndShow();
            passcodeView.setPasscodeCallback(new PasscodeCallback() {
                @Override
                public void onComplete(CharSequence sequence) {
                    if(sequence.equals("4728")) {
                        Toast.makeText(MainActivity.this, "Passcode verified", Toast.LENGTH_SHORT).show();
                        passcodeView.hideSoftKeyboard();
                    } else {
                        Toast.makeText(MainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });


![Image of Yaktocat](ySmiih.gif)


