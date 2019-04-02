package qtc.project.aza.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class ReloadDataCheckingOrderEvent {

    public static void post() {
        BusHelper.post(new ReloadDataCheckingOrderEvent());
    }
}
