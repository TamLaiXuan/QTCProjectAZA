package qtc.project.aza.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class ReloadDataListOrderEvent {

    public static void post() {
        BusHelper.post(new ReloadDataListOrderEvent());
    }
}
