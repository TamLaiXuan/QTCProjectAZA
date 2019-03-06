package qtc.project.aza.model;

import android.support.annotation.Nullable;

import b.laixuantam.myaarlibrary.api.BaseApiResponse;

/**
 * Created by laixuantam on 4/2/18.
 */

public class BaseResponseModel implements BaseApiResponse {

    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Nullable
    private String level;

    @Nullable
    public String getLevel() {
        return level;
    }

    public void setLevel(@Nullable String level) {
        this.level = level;
    }
}
