package com.example.myfitnessjourney.Controller;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class App extends Application {

	@Override 
	public void onCreate() { 
        super.onCreate();

        Parse.initialize(this, "kWv63EFDTqkHDZgJN9dSxbwyEKYk16PhhSYhNRvB", "JV2kodkeVrTrAi0N5UpETxrGOT53BDG2Qz9lONTI"); // Your Application ID and Client Key are defined elsewhere
        
        //Test object
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
} 

