package org.odk.collect.android.regression;

import android.Manifest;

import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.odk.collect.android.R;
import org.odk.collect.android.support.pages.AdminSettingsPage;
import org.odk.collect.android.support.pages.GeneralSettingsPage;
import org.odk.collect.android.support.pages.MainMenuPage;
import org.odk.collect.android.support.CopyFormRule;
import org.odk.collect.android.support.ResetStateRule;
import org.odk.collect.android.support.pages.ResetApplicationDialog;

//Issue NODK-240
public class ResetApplicationTest extends BaseRegressionTest {
    @Rule
    public RuleChain ruleChain = RuleChain
            .outerRule(new ResetStateRule());

    @Rule
    public RuleChain copyFormChain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE)
            )
            .around(new ResetStateRule())
            .around(new CopyFormRule("All_widgets.xml"));

    @Test
    public void when_rotateScreen_should_resetDialogNotDisappear() {
        //TestCase1
        new MainMenuPage(rule)
                .clickOnMenu()
                .clickAdminSettings()
                .clickOnResetApplication()
                .checkIsStringDisplayed(R.string.reset_settings_dialog_title)
                .checkIfOptionIsDisabled(R.string.reset_settings_button_reset)
                .rotateToLandscape(new ResetApplicationDialog(rule))
                .checkIsStringDisplayed(R.string.reset_settings_dialog_title)
                .checkIfOptionIsDisabled(R.string.reset_settings_button_reset)
                .rotateToPortrait(new ResetApplicationDialog(rule))
                .checkIsStringDisplayed(R.string.reset_settings_dialog_title)
                .checkIfOptionIsDisabled(R.string.reset_settings_button_reset);
    }

    @Test
    public void savedAndBlankForms_shouldBeReset() {
        //TestCase1,4
        new MainMenuPage(rule)
                .startBlankForm("All widgets")
                .clickGoToArrow()
                .clickJumpEndButton()
                .clickSaveAndExit()
                .clickEditSavedForm()
                .assertText("All widgets")
                .pressBack(new MainMenuPage(rule))
                .clickOnMenu()
                .clickAdminSettings()
                .clickOnResetApplication()
                .checkIfOptionIsDisabled(R.string.reset_settings_button_reset)
                .clickOnString(R.string.reset_saved_forms)
                .clickOnString(R.string.reset_blank_forms)
                .clickOnString(R.string.reset_settings_button_reset)
                .clickOKOnDialog();
        new MainMenuPage(rule)
                .clickFillBlankForm()
                .checkIfTextDoesNotExist("All widgets")
                .pressBack(new MainMenuPage(rule))
                .clickEditSavedForm()
                .checkIfTextDoesNotExist("All widgets");
    }

    @Test
    public void adminSettings_shouldBeReset() {
        //TestCase2
        new MainMenuPage(rule)
                .clickOnMenu()
                .clickAdminSettings()
                .openUserSettings()
                .uncheckServerOption()
                .pressBack(new AdminSettingsPage(rule))
                .pressBack(new MainMenuPage(rule))
                .clickOnMenu()
                .clickGeneralSettings()
                .checkIfServerOptionIsNotDisplayed()
                .pressBack(new MainMenuPage(rule))
                .clickOnMenu()
                .clickAdminSettings()
                .clickOnResetApplication()
                .clickOnString(R.string.reset_settings)
                .clickOnString(R.string.reset_settings_button_reset)
                .clickOKOnDialog();
        new MainMenuPage(rule)
                .clickOnMenu()
                .clickGeneralSettings()
                .checkIfServerOptionIsDisplayed();
    }

    @Test
    public void userInterfaceSettings_shouldBeReset() {
        //TestCase3
        new MainMenuPage(rule)
                .clickOnMenu()
                .clickGeneralSettings()
                .clickOnUserInterface()
                .checkIsStringDisplayed(R.string.theme_light)
                .clickOnTheme()
                .clickOnString(R.string.theme_dark);
        new MainMenuPage(rule)
                .clickOnMenu()
                .clickGeneralSettings()
                .clickOnUserInterface()
                .checkIsStringDisplayed(R.string.theme_dark)
                .clickOnLanguage()
                .clickOnSelectedLanguage("español");
        new MainMenuPage(rule)
                .clickOnMenu()
                .clickGeneralSettings()
                .clickOnUserInterface()
                .assertText("español")
                .pressBack(new GeneralSettingsPage(rule))
                .pressBack(new MainMenuPage(rule))
                .clickOnMenu()
                .clickAdminSettings()
                .clickOnResetApplication()
                .clickOnString(R.string.reset_settings)
                .clickOnString(R.string.reset_settings_button_reset)
                .clickOKOnDialog();
        new MainMenuPage(rule)
                .clickOnMenu()
                .clickGeneralSettings()
                .clickOnUserInterface()
                .checkIsStringDisplayed(R.string.theme_light)
                .checkIfTextDoesNotExist(R.string.theme_dark)
                .checkIsStringDisplayed(R.string.use_device_language)
                .checkIfTextDoesNotExist("español");
    }

    @Test
    public void formManagementSettings_shouldBeReset() {
        //TestCase3
        new MainMenuPage(rule)
                .clickOnMenu()
                .clickGeneralSettings()
                .openFormManagement()
                .clickOnAutoSend()
                .clickOnString(R.string.wifi_autosend)
                .checkIsStringDisplayed(R.string.wifi_autosend)
                .clickOnDefaultToFinalized()
                .pressBack(new GeneralSettingsPage(rule))
                .pressBack(new MainMenuPage(rule))
                .startBlankForm("All widgets")
                .clickGoToArrow()
                .clickJumpEndButton()
                .assertMarkFinishedIsNotSelected()
                .clickSaveAndExit()
                .clickOnMenu()
                .clickAdminSettings()
                .clickOnResetApplication()
                .clickOnString(R.string.reset_settings)
                .clickOnString(R.string.reset_settings_button_reset)
                .clickOKOnDialog();
        new MainMenuPage(rule)
                .clickOnMenu()
                .clickGeneralSettings()
                .openFormManagement()
                .checkIsStringDisplayed(R.string.off)
                .pressBack(new GeneralSettingsPage(rule))
                .pressBack(new MainMenuPage(rule))
                .startBlankForm("All widgets")
                .clickGoToArrow()
                .clickJumpEndButton()
                .assertMarkFinishedIsSelected()
                .clickSaveAndExit();
    }

}
