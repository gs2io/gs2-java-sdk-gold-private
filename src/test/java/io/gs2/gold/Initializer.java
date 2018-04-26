package io.gs2.gold;

import io.gs2.auth.Gs2AuthClient;

public class Initializer {

    public static void initialize() {
        Gs2AuthClient.ENDPOINT = "auth-dev";
        Gs2GoldClient.ENDPOINT = "gold-dev";
    }

}
