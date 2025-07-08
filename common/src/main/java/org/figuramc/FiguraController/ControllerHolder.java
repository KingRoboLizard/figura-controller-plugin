package org.figuramc.FiguraController;

import org.hid4java.HidDevice;
import org.hid4java.HidServicesListener;
import org.hid4java.event.HidServicesEvent;
import org.hid4java.HidManager;

public class ControllerHolder {
	public static ControllerHolder instance;
	
	public HidDevice hidDevice;
    public boolean connected = false;
    // private Avatar avatar;
	
	private final HidServicesListener controllerListener = new HidServicesListener() {
        @Override
        public void hidDeviceAttached(HidServicesEvent event) {
            if (!connected) {
                if (event.getHidDevice().getUsage() == 5) {
                    hidDevice = event.getHidDevice();
                    connected = true;
                }
            }
        }

        @Override
        public void hidDeviceDetached(HidServicesEvent event) {
            connected = false;
            hidDevice = null;
        }

        @Override
        public void hidFailure(HidServicesEvent event) {

        }

        @Override
        public void hidDataReceived(HidServicesEvent event) {
        }
    };
	
	public static ControllerHolder getInstance() {
		if (instance == null) {
			instance = new ControllerHolder();
		}
		return instance;
	}
	
	public ControllerHolder() {
		for (HidDevice hid : HidManager.getHidServices().getAttachedHidDevices()) {
            if (hid.getUsage() == 5) {
                this.hidDevice = hid;
                connected = true;
                ControllerPlugin.LOGGER.info("" + hidDevice.getVendorId());
                ControllerPlugin.LOGGER.info("" + hidDevice.getProductId());
                break;
            }
        }
        HidManager.getHidServices().addHidServicesListener(controllerListener);
		instance = this;
	}
}