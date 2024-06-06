package com.myorg;

import software.amazon.awscdk.App;

public final class CdkQueueApp {
    public static void main(final String[] args) {
        App app = new App();

        new CdkQueueStack(app, "CdkQueueStack");

        app.synth();
    }
}
