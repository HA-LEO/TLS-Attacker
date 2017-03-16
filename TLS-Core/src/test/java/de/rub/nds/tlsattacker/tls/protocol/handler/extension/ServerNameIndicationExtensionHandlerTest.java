/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.protocol.handler.extension;

import de.rub.nds.tlsattacker.tls.constants.NameType;
import de.rub.nds.tlsattacker.tls.protocol.message.extension.SNI.SNIEntry;
import de.rub.nds.tlsattacker.tls.protocol.message.extension.SNI.ServerNamePair;
import de.rub.nds.tlsattacker.tls.protocol.message.extension.ServerNameIndicationExtensionMessage;
import de.rub.nds.tlsattacker.tls.protocol.parser.extension.ServerNameIndicationExtensionParser;
import de.rub.nds.tlsattacker.tls.protocol.preparator.extension.ServerNameIndicationExtensionPreparator;
import de.rub.nds.tlsattacker.tls.protocol.serializer.extension.ServerNameIndicationExtensionSerializer;
import de.rub.nds.tlsattacker.tls.workflow.TlsContext;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public class ServerNameIndicationExtensionHandlerTest {

    private TlsContext context;
    private ServerNameIndicationExtensionHandler handler;

    public ServerNameIndicationExtensionHandlerTest() {
    }

    @Before
    public void setUp() {
        context = new TlsContext();
        handler = new ServerNameIndicationExtensionHandler(context);
    }

    /**
     * Test of adjustTLSContext method, of class
     * ServerNameIndicationExtensionHandler.
     */
    @Test
    public void testAdjustTLSContext() {
        ServerNameIndicationExtensionMessage msg = new ServerNameIndicationExtensionMessage();
        List<ServerNamePair> pairList = new LinkedList<>();
        ServerNamePair pair = new ServerNamePair();
        pair.setServerName("localhost".getBytes());
        pair.setServerNameType(NameType.HOST_NAME.getValue());
        pairList.add(pair);
        msg.setServerNameList(pairList);
        handler.adjustTLSContext(msg);
        assertTrue(context.getClientSNIEntryList().size() == 1);
        SNIEntry entry = context.getClientSNIEntryList().get(0);
        assertEquals("localhost", entry.getName());
        assertTrue(entry.getType() == NameType.HOST_NAME);
    }

    @Test
    public void testUndefinedAdjustTLSContext() {
        ServerNameIndicationExtensionMessage msg = new ServerNameIndicationExtensionMessage();
        List<ServerNamePair> pairList = new LinkedList<>();
        ServerNamePair pair = new ServerNamePair();
        pair.setServerName("localhost".getBytes());
        pair.setServerNameType((byte) 99);
        pairList.add(pair);
        msg.setServerNameList(pairList);
        handler.adjustTLSContext(msg);
        assertTrue(context.getClientSNIEntryList().isEmpty());
    }

    /**
     * Test of getParser method, of class ServerNameIndicationExtensionHandler.
     */
    @Test
    public void testGetParser() {
        assertTrue(handler.getParser(new byte[] { 0, 2, 3, }, 0) instanceof ServerNameIndicationExtensionParser);
    }

    /**
     * Test of getPreparator method, of class
     * ServerNameIndicationExtensionHandler.
     */
    @Test
    public void testGetPreparator() {
        assertTrue(handler.getPreparator(new ServerNameIndicationExtensionMessage()) instanceof ServerNameIndicationExtensionPreparator);
    }

    /**
     * Test of getSerializer method, of class
     * ServerNameIndicationExtensionHandler.
     */
    @Test
    public void testGetSerializer() {
        assertTrue(handler.getSerializer(new ServerNameIndicationExtensionMessage()) instanceof ServerNameIndicationExtensionSerializer);
    }

}
