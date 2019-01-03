package com.jwhh.jim.notekeeper;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.*;

@RunWith(AndroidJUnit4.class)
public class NoteListActivityTest {

    public static DataManager dataManager;

    @BeforeClass
    public static void setUpDataManager() throws Exception{
        dataManager = DataManager.getInstance();
    }
    @Rule
    public ActivityTestRule<NoteListActivity> testRule =
            new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void createNewNote(){
        final CourseInfo courseInfo = dataManager.getCourse("java_lang");
        final String noteTitle = "New Note Title";
        final String noteText = "Sample Note Body";
//        ViewInteraction fabNewNote = onView(withId(R.id.fab));
//        fabNewNote.perform(click());
        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.spinner_courses)).perform(click());
        onData(allOf(instanceOf(CourseInfo.class),equalTo(courseInfo))).perform(click());
        onView(withId(R.id.spinner_courses))
                .check(matches(withSpinnerText(courseInfo.getTitle())));

        onView(withId(R.id.text_note_title)).perform(typeText(noteTitle))
                .check(matches(withText(containsString(noteTitle))));
        onView(withId(R.id.text_note_text)).perform(typeText(noteText),
                closeSoftKeyboard());
        onView(withId(R.id.text_note_text))
                .check(matches(withText(containsString(noteText))));

        pressBack();

        int newNoteId = dataManager.getNotes().size() - 1;
        NoteInfo noteInfo = dataManager.getNotes().get(newNoteId);
        assertEquals(courseInfo,noteInfo.getCourse());
        assertEquals(noteText,noteInfo.getText());
        assertEquals(noteTitle,noteInfo.getTitle());

    }
}