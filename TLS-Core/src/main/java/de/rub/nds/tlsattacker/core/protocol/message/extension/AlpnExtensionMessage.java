/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.message.extension;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.tlsattacker.core.constants.ExtensionType;
import de.rub.nds.tlsattacker.core.protocol.handler.extension.AlpnExtensionHandler;
import de.rub.nds.tlsattacker.core.workflow.TlsContext;

/**
 *
 * @author Matthias Terlinde <matthias.terlinde@rub.de>
 */
public class AlpnExtensionMessage extends ExtensionMessage {

    @ModifiableVariableProperty
    private ModifiableInteger alpnExtensionLength;
    @ModifiableVariableProperty
    private ModifiableByteArray alpnAnnouncedProtocols;

    public AlpnExtensionMessage() {
        super(ExtensionType.ALPN);
    }

    @Override
    public AlpnExtensionHandler getHandler(TlsContext context) {
        return new AlpnExtensionHandler(context);
    }

    public ModifiableInteger getAlpnExtensionLength() {
        return alpnExtensionLength;
    }

    public void setAlpnExtensionLength(ModifiableInteger alpnExtensionLength) {
        this.alpnExtensionLength = alpnExtensionLength;
    }

    public void setAlpnExtensionLength(int alpnExtensionLength) {
        this.alpnExtensionLength = ModifiableVariableFactory.safelySetValue(this.alpnExtensionLength,
                alpnExtensionLength);
    }

    public ModifiableByteArray getAlpnAnnouncedProtocols() {
        return alpnAnnouncedProtocols;
    }

    public void setAlpnAnnouncedProtocols(ModifiableByteArray alpnAnnouncedProtocols) {
        this.alpnAnnouncedProtocols = alpnAnnouncedProtocols;
    }

    public void setAlpnAnnouncedProtocols(byte[] alpnAnnouncedProtocols) {
        this.alpnAnnouncedProtocols = ModifiableVariableFactory.safelySetValue(this.alpnAnnouncedProtocols,
                alpnAnnouncedProtocols);
    }

}
