package com.github.hippoom.wiremock.contract.verifier;

import java.io.IOException;

public class NoSuchContractException extends RuntimeException {

    public NoSuchContractException(String message, IOException cause) {
        super(message, cause);
    }
}
