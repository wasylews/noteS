package com.genius.wasylews.notes.data.service;

import com.francescocervone.rxdrive.RxDrive;

import javax.inject.Inject;

public class GoogleDriveService {

    private RxDrive drive;

    @Inject
    public GoogleDriveService(RxDrive drive) {
        this.drive = drive;
    }
}
