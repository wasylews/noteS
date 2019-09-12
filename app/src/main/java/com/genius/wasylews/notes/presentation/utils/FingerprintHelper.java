package com.genius.wasylews.notes.presentation.utils;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FingerprintHelper {

    private FingerprintManagerCompat fingerprintManager;

    @Inject
    public FingerprintHelper(FingerprintManagerCompat fingerprintManager) {
        this.fingerprintManager = fingerprintManager;
    }

    public boolean canAuthenticate() {
        // TODO: for android Q use BiometricManager#canAuthenticate()
        return fingerprintManager.isHardwareDetected() &&
                fingerprintManager.hasEnrolledFingerprints();
    }
}
