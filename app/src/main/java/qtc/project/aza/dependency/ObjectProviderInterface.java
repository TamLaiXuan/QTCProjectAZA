package qtc.project.aza.dependency;

import b.laixuantam.myaarlibrary.api.ApiManagement;
import b.laixuantam.myaarlibrary.helper.AppCleanerHelper;
import b.laixuantam.myaarlibrary.helper.AuthHelper;
import b.laixuantam.myaarlibrary.helper.ConnectivityHelper;
import b.laixuantam.myaarlibrary.helper.FileHelper;
import b.laixuantam.myaarlibrary.helper.ImageHelper;
import b.laixuantam.myaarlibrary.helper.InstallationHelper;
import b.laixuantam.myaarlibrary.helper.LanguageHelper;
//import qtc.project.aza.database.DatabaseHelper;
import qtc.project.aza.helper.SharePrefs;

public interface ObjectProviderInterface
{
    SharePrefs getPreferences();

//    DatabaseHelper getDatabaseHelper();

    ImageHelper getImageHelper();

    ConnectivityHelper getConnectivityHelper();

    InstallationHelper getInstallationHelper();

    AppCleanerHelper getAppCleanerHelper();

    FileHelper getFileHelper();

    ApiManagement getApiManagement();

    LanguageHelper getLanguageHelper();

    AuthHelper getAuthHelper();

}

