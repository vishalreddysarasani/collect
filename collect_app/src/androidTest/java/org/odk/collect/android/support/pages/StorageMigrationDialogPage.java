package org.odk.collect.android.support.pages;

import androidx.test.rule.ActivityTestRule;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.WebViewActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class StorageMigrationDialogPage extends Page<StorageMigrationDialogPage>  {

    public StorageMigrationDialogPage(ActivityTestRule rule) {
        super(rule);
    }

    @Override
    public StorageMigrationDialogPage assertOnPage() {
        checkIsStringDisplayed(R.string.storage_migration_dialog_title);
        return this;
    }

    public StorageMigrationDialogPage clickMoreDetails() {
        onView(withId(R.id.moreDetailsButton)).perform(click());
        return this;
    }

    public MainMenuPage clickCancel() {
        onView(withId(R.id.cancelButton)).perform(click());
        return new MainMenuPage(rule).assertOnPage();
    }

    public MainMenuPage clickMigrate() {
        onView(withId(R.id.migrateButton)).perform(click());
        return new MainMenuPage(rule).assertOnPage();
    }

    public StorageMigrationDialogPage assertWebViewOpen() {
        intended(allOf(
                hasComponent(WebViewActivity.class.getName()),
                hasExtra("url", "https://forum.opendatakit.org/t/24159")
        ));
        return this;
    }

    public StorageMigrationDialogPage assertStorageMigrationContentWithSavedFormsIsVisible() {
        onView(withText(R.string.storage_migration_dialog_title)).check(matches(isDisplayed()));
        onView(withText(R.string.storage_migration_dialog_message1)).check(matches(isDisplayed()));
        onView(withText(getTranslatedString(R.string.storage_migration_dialog_message2, 1))).check(matches(isDisplayed()));
        onView(withText(R.string.storage_migration_dialog_message3)).check(matches(isDisplayed()));
        onView(withText(R.string.storage_migration_more_details)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel)).check(matches(isDisplayed()));
        onView(withText(R.string.migrate)).check(matches(isDisplayed()));

        return this;
    }

    public StorageMigrationDialogPage assertStorageMigrationContentWithoutSavedFormsIsVisible() {
        onView(withText(R.string.storage_migration_dialog_title)).check(matches(isDisplayed()));
        onView(withText(R.string.storage_migration_dialog_message1)).check(matches(isDisplayed()));
        onView(withText(R.string.storage_migration_dialog_message2)).check(matches(not(isDisplayed())));
        onView(withText(R.string.storage_migration_dialog_message3)).check(matches(isDisplayed()));
        onView(withText(R.string.storage_migration_more_details)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel)).check(matches(isDisplayed()));
        onView(withText(R.string.migrate)).check(matches(isDisplayed()));

        return this;
    }
}