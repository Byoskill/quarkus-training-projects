package com.byoskill;

import com.byoskill.communication.client.CommunicationMessageService;
import com.byoskill.communication.model.WelcomeMessage;
import io.quarkus.test.Mock;

@Mock
public class CommunicationMessageServiceMock implements CommunicationMessageService {
    public static final String DEFAULT_WELCOME_MESSAGE = "Welcome to our website";
    
    public static WelcomeMessage DEFAULT_WELCOME = new WelcomeMessage(DEFAULT_WELCOME_MESSAGE);
    
    @Override public WelcomeMessage getWelcomeMessage() {
        return DEFAULT_WELCOME;
    }
}
