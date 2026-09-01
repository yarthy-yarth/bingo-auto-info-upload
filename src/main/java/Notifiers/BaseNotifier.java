package Notifiers;

import BingoAutoInfoUpload.BingoAutoInfoUploadConfig;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;

import javax.inject.Inject;

@Slf4j
public abstract class BaseNotifier {
    @Inject
    Client client;

    @Inject
    BingoAutoInfoUploadConfig config;
}
